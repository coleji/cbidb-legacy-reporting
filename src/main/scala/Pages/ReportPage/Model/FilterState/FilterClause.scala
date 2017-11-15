package Pages.ReportPage.Model.FilterState

import Pages.ReportPage.Model.ReportFilterValue


abstract class FilterExpression

class CompositeFilter(val comparator: Comparator, val filters: List[FilterExpression]) extends FilterExpression

class SingleFilter(val filter: ReportFilterValue) extends FilterExpression
