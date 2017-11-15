package Pages.ReportPage.Messages

import Pages.ReportPage.Model.FilterState.{CompositeFilter, SingleFilter}
import Pages.ReportPage.Model.ReportPageModel
import core.Message

object SearchModelForHash extends Message[ReportPageModel, Int] {
  def update: ReportPageModel => Int => ReportPageModel =
    model => hashCode => {
      println("click!")
      def getSingleFiltersFromComposite(cf: CompositeFilter): List[SingleFilter] = {
        cf.filters.flatMap({
          case s: SingleFilter => List(s)
          case cf1: CompositeFilter => getSingleFiltersFromComposite(cf1)
        })
      }
      val allSingleFilters: List[SingleFilter] = model.filters match {
        case None => List.empty
        case Some(cf: CompositeFilter) => getSingleFiltersFromComposite(cf)
      }
      val matchingFilters = allSingleFilters.filter(f => f.hashCode == hashCode)
      println(matchingFilters)
      model
    }
}
