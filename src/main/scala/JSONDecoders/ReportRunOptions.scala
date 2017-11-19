package JSONDecoders

import scala.scalajs.js

/*

{  // --------------------------------------------------------------------------------- ReportableEntityResultWrapper
    "data": {  // --------------------------------------------------------------------- ReportableEntityResultWrapperData
        "runOptions": [{ // ----------------------------------------------------------- ReportableEntityNative
            "entityName": "ApClassInstance",
            "displayName": "AP Class Instances",
            "fieldData": [{ // -------------------------------------------------------- ReportFieldNative
                "fieldName": "TypeId",
                "fieldDisplayName": "Type ID"
            }, {
                "fieldName": "InstanceId",
                "fieldDisplayName": "Instance ID"
            }],
            "filterData": [{ // ------------------------------------------------------- ReportFilterNative
                "filterName": "ApClassInstanceFilterYear",
                "displayName": "By Season",
                "filterType": "Int",
                "values": []
            }, {
                "filterName": "ApClassInstanceFilterType",
                "displayName": "By Class Type",
                "filterType": "dropdown",
                "values": [{ // ------------------------------------------------------- ReportFilterDropdownOptionsNative
                    "display": "Jib I",
                    "return": "7"
                }, {
                    "display": "Jib II",
                    "return": "8"
                }]
            }]
        }],
        "$cacheExpires": "2017-11-07T14:27:06+0000"
    }
}

 */

@js.native
trait ReportableEntityResultWrapper extends js.Object {
  val data: ReportableEntityResultWrapperData = js.native
}

@js.native
trait ReportableEntityResultWrapperData extends js.Object {
  val runOptions: js.Array[ReportableEntityNative] = js.native
}

@js.native
trait ReportableEntityNative extends js.Object {
  val entityName: String = js.native
  val displayName: String = js.native
  val fieldData: js.Array[ReportFieldNative] = js.native
  val filterData: js.Array[ReportFilterNative] = js.native
}

@js.native
trait ReportFieldNative extends js.Object{
  val fieldName: String = js.native
  val fieldDisplayName: String = js.native
  val isDefault: Boolean = js.native
}

@js.native
trait ReportFilterNative extends js.Object {
  val filterName: String = js.native
  val displayName: String = js.native
  val filterType: String = js.native
  val default: String = js.native
  val values: js.Array[js.Array[ReportFilterDropdownOptionsNative]] = js.native
}

@js.native
trait ReportFilterDropdownOptionsNative extends js.Object {
  val display: String = js.native
  val `return`: String = js.native
}