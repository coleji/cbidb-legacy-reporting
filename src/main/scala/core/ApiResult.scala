package core

import scala.scalajs.js

@js.native
trait ApiResult extends js.Object {
  @js.native
  val data: ApiResultData = js.native
}

@js.native
trait ApiResultData extends js.Object {
  val rows: js.Array[RowObject] = js.native
  val metaData: js.Array[MetaDataObject] = js.native
}

@js.native
trait MetaDataObject extends js.Object {
  val name: String = js.native
}

@js.native
trait RowObject extends js.Array[js.Any]