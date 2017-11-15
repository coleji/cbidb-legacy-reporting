package Pages.ReportPage.Model

import scala.scalajs.js

case class ReportableEntity(
  entityName: String,
  displayName: String,
  fieldData: js.Array[ReportField],
  filterData: js.Array[ReportFilterDefinition]
)
