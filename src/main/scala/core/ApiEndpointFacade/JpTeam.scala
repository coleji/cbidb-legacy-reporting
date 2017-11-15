package core.ApiEndpointFacade

import core.{ApiResult, RowObject}

import scala.scalajs.js

case class JpTeam(teamName: String, points: Int)

object JpTeam {
  def parse(r: ApiResult): js.Array[JpTeam] = {
    def getIndexOfField(fieldName: String): Int = r.data.metaData.indexWhere(_.name == fieldName)
    val parseSingle: (RowObject => JpTeam) = {
      val teamNameIndex = getIndexOfField("TEAM_NAME")
      val pointsIndex = getIndexOfField("POINTS")
      (row: RowObject) => JpTeam(
        row(teamNameIndex).asInstanceOf[String],
        row(pointsIndex).asInstanceOf[Int]
      )
    }
    r.data.rows.map(parseSingle)
  }
}
