package Pages.ReportPage.Messages

import Pages.ReportPage.Model.FilterState.{Comparator, CompositeFilter, SingleFilter}
import Pages.ReportPage.Model.{ReportFilterDefinition, ReportFilterValue, ReportPageModel, ReportableEntity}
import core.Main.dropdownNull
import core.Message

object SetEntity extends Message[ReportPageModel, String] {
  def update: ReportPageModel => String => ReportPageModel =
    model => selectedEntityName => {
      selectedEntityName match {
        case `dropdownNull` => ReportPageModel(model.options, None, None, None)
        case name => {
          val selectedEntity: ReportableEntity = model.getOptions.filter(_.entityName == name).head
          ReportPageModel(
            model.options,
            Some(selectedEntity),
            Some(new CompositeFilter(Comparator.AND, List.empty)),
            Some(selectedEntity.fieldData.filter(_.isDefault).toSet)
          )
        }
      }
    }
}
