package Pages.ReportPage.Model

case class FilterType(typeName: String)
object FilterType {
  val INT_FILTER_TYPE = FilterType("Int")
  val DOUBLE_FILTER_TYPE = FilterType("Double")
  val DATE_FILTER_TYPE = FilterType("Date")
  val DROPDOWN_FILTER_TYPE = FilterType("Dropdown")
}

