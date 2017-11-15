package Pages.AsyncPage

import core.ApiEndpointFacade.JpTeam
import core.{AsyncOption, AsyncSuccess, Model}

import scala.scalajs.js


case class AsyncPageModel(result: AsyncOption[js.Array[JpTeam]]) extends Model {
  def get: js.Array[JpTeam] = result match {
    case AsyncSuccess(teams: js.Array[JpTeam]) => teams
    case _ => js.Array[JpTeam]()
  }
}