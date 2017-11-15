package Pages.UsersPage

import core.ApiEndpointFacade.User
import core.{AsyncOption, AsyncSuccess, Model}

import scala.scalajs.js

case class UsersPageModel(result: AsyncOption[js.Array[User]]) extends Model {
  def get: js.Array[User] = result match {
    case AsyncSuccess(users: js.Array[User]) => users
    case _ => js.Array[User]()
  }
}