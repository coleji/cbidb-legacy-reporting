package Pages.ReportPage.Model

case class FilterType(typeName: String)
object FilterType {
  val INT_FILTER_TYPE = FilterType("Int")
  val DROPDOWN_FILTER_TYPE = FilterType("Dropdown")
}

