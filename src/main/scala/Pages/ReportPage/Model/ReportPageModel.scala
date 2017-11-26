package Pages.ReportPage.Model

import Pages.ReportPage.Messages.DeleteFilter
import Pages.ReportPage.Messages.DeleteFilter.DeleteFilterPayload
import Pages.ReportPage.Model.FilterState.{CompositeFilter, FilterExpression, SingleFilter}
import core.{AsyncOption, AsyncSuccess, Model}

import scala.scalajs.js

case class ReportPageModel(
  options: AsyncOption[js.Array[ReportableEntity]],
  selectedEntity: Option[ReportableEntity],
  filters: Option[CompositeFilter],
  fields: Option[Set[ReportField]]
) extends Model {
  def getOptions: js.Array[ReportableEntity] = options match {
    case AsyncSuccess(a: js.Array[ReportableEntity]) => a
    case _ => js.Array()
  }

  def findFilterTypeDefinition(filterName: String): Option[ReportFilterDefinition] = {
    selectedEntity.get.filterData.find(d => d.filterName == filterName)
  }

  def getSpecString: String = {
    def printFilterExpression(fe: FilterExpression): String = fe match {
      case sf: SingleFilter => sf.filter.definition.filterName + ":" + sf.filter.value.mkString(",")
      case cf: CompositeFilter => "(" + cf.filters.map(printFilterExpression).mkString(cf.comparator.getSymbol) + ")"
    }
    printFilterExpression(filters.get)
  }

  // TODO: replace with single clone function that takes callbacks or something
  def cloneAndUpdateType(hashCode: String, value: String): ReportPageModel = {
    def recurseThroughFilters[T <: FilterExpression](filter: T): T = filter match {
      case sf: SingleFilter => {
        if (sf.hashCode().toString == hashCode) {
          val typeDef = findFilterTypeDefinition(value)
          new SingleFilter(ReportFilterValue(typeDef.get, typeDef.get.default.split(",").toList)).asInstanceOf[T]
        } else sf.asInstanceOf[T]
      }
      case cf: CompositeFilter => new CompositeFilter(cf.comparator, cf.filters.map(recurseThroughFilters)).asInstanceOf[T]
    }

    ReportPageModel(
      options,
      selectedEntity,
      filters match {
        case None => None
        case Some(e: FilterExpression) => Some(recurseThroughFilters(e))
      },
      fields
    )
  }

  def cloneAndUpdateValue(hashCode: String, valueIndex: Int, value: String): ReportPageModel = {
    def recurseThroughFilters[T <: FilterExpression](filter: T): T = filter match {
      case sf: SingleFilter => {
        if (sf.hashCode().toString == hashCode) {
          val newValue = sf.filter.value.zip(0 to sf.filter.value.length).map(t => {
            val newVal = if (t._2 == valueIndex) value else t._1
            newVal
          })
          new SingleFilter(ReportFilterValue(sf.filter.definition, newValue)).asInstanceOf[T]
        } else sf.asInstanceOf[T]
      }
      case cf: CompositeFilter => new CompositeFilter(cf.comparator, cf.filters.map(recurseThroughFilters)).asInstanceOf[T]
    }

    ReportPageModel(
      options,
      selectedEntity,
      filters match {
        case None => None
        case Some(e: FilterExpression) => Some(recurseThroughFilters(e))
      },
      fields
    )
  }

  def cloneAndAddSingleFilter(hashCode: Int): ReportPageModel = {
    def recurseThroughFilters[T <: FilterExpression](filter: T): T = filter match {
      case sf: SingleFilter => sf.asInstanceOf[T]
      case cf: CompositeFilter => {
        if (cf.hashCode() == hashCode) {
          val defaultFilterDef = selectedEntity.get.filterData.head
          val defaultFilterValue = defaultFilterDef.default
          val newFilters: List[FilterExpression] = cf.filters ++ List(new SingleFilter(ReportFilterValue(defaultFilterDef, defaultFilterValue.split(",").toList)))

          new CompositeFilter(cf.comparator, newFilters).asInstanceOf[T]
        } else {
          new CompositeFilter(cf.comparator, cf.filters.map(recurseThroughFilters)).asInstanceOf[T]
        }
      }
    }

    ReportPageModel(
      options,
      selectedEntity,
      filters match {
        case None => None
        case Some(e: FilterExpression) => Some(recurseThroughFilters(e))
      },
      fields
    )
  }

  def cloneAndAddCompositeFilter(hashCode: Int): ReportPageModel = {
    def recurseThroughFilters[T <: FilterExpression](filter: T): T = filter match {
      case sf: SingleFilter => sf.asInstanceOf[T]
      case cf: CompositeFilter => {
        if (cf.hashCode() == hashCode) {
          val defaultFilterDef = selectedEntity.get.filterData.head
          val defaultFilterValue = defaultFilterDef.default
          val newFilters: List[FilterExpression] = cf.filters ++ List({
            val newComparator = cf.comparator.getOtherOne
            new CompositeFilter(
              newComparator,
              List(new SingleFilter(ReportFilterValue(defaultFilterDef, defaultFilterValue.split(",").toList)))
            )
          })
          new CompositeFilter(cf.comparator, newFilters).asInstanceOf[T]
        } else {
          new CompositeFilter(cf.comparator, cf.filters.map(recurseThroughFilters)).asInstanceOf[T]
        }
      }
    }

    ReportPageModel(
      options,
      selectedEntity,
      filters match {
        case None => None
        case Some(e: FilterExpression) => Some(recurseThroughFilters(e))
      },
      fields
    )
  }

  def cloneAndDropExpression(payload: DeleteFilterPayload): ReportPageModel = {
    def recurseAndDropTarget[T <: FilterExpression](filter: T): T = filter match {
      case sf: SingleFilter => sf.asInstanceOf[T]
      case cf: CompositeFilter => new CompositeFilter(cf.comparator, cf.filters.filter(f => payload.deleteType match {
        case DeleteFilter.DELETE_TYPE_SINGLE => !f.isInstanceOf[SingleFilter] || f.hashCode() != payload.deleteHash
        case DeleteFilter.DELETE_TYPE_COMPOSITE => !f.isInstanceOf[CompositeFilter] || f.hashCode() != payload.deleteHash
      }).map(recurseAndDropTarget)).asInstanceOf[T]
    }

    def recurseAndDropEmptyComposites[T <: FilterExpression](filter: T): T = filter match {
      case sf: SingleFilter => sf.asInstanceOf[T]
      case cf: CompositeFilter => new CompositeFilter(cf.comparator, cf.filters.filter({
        case _: SingleFilter => true
        case cf1: CompositeFilter => cf1.filters.nonEmpty
      }).map(recurseAndDropEmptyComposites)).asInstanceOf[T]
    }

    ReportPageModel(
      options,
      selectedEntity,
      Some(recurseAndDropEmptyComposites(recurseAndDropTarget(filters.get))),
      fields
    )
  }

  def cloneAndSetFilters(fieldIDs: js.Array[js.Any]): ReportPageModel = {
    val fieldIDSet = fieldIDs.toSet
    ReportPageModel(
      options,
      selectedEntity,
      filters,
      Some(selectedEntity.get.fieldData.filter(f => fieldIDSet contains f.fieldName).toSet)
    )
  }
}

