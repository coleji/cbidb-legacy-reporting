package Pages.ReportPage.Model

import scala.scalajs.js

// native version of the raw output from the get options api call
case class ReportFilterDefinition(
  filterName: String,
  displayName: String,
  filterType: js.Array[FilterType],
  default: String,
  dropdownValues: js.Array[js.Array[ReportFilterDropdownValue]]
)
