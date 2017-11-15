package core.ApiEndpointFacade

import core.{ApiResult, RowObject}

import scala.scalajs.js

case class User(
  userId: Int,
  userName: String,
  firstName: String,
  lastName: String,
  email: String,
  active: Boolean,
  hideFromClose: Boolean
)

object User {
  val fields: Array[(String, String, Class[_])] = Array(
    ("USER_ID", "User ID", classOf[Int]),
    ("USER_NAME", "User Name", classOf[String]),
    ("NAME_FIRST", "First Name", classOf[String]),
    ("NAME_LAST", "Last Name", classOf[String]),
    ("EMAIL", "Email", classOf[String]),
    ("ACTIVE", "Active", classOf[Boolean]),
    ("HIDE_FROM_CLOSE", "Hide From Close", classOf[Boolean])
  )
  def parse(r: ApiResult): js.Array[User] = {
    def getIndexOfField(fieldName: String): Int = r.data.metaData.indexWhere(_.name == fieldName)
    val parseSingle: (RowObject => User) = {
      val indexes: Array[Int] = fields.map(f => getIndexOfField(f._1))
      (row: RowObject) => User(
        row(indexes(0)).asInstanceOf[Int],
        row(indexes(1)).asInstanceOf[String],
        row(indexes(2)).asInstanceOf[String],
        row(indexes(3)).asInstanceOf[String],
        row(indexes(4)).asInstanceOf[String],
        row(indexes(5)).asInstanceOf[Boolean],
        row(indexes(6)).asInstanceOf[Boolean],
      )
    }
    r.data.rows.map(parseSingle)
  }
}
