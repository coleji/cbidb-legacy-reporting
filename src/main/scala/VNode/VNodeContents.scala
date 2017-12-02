package VNode

import VNode.SnabbdomFacade.VNode

import scala.scalajs.js

trait VNodeContents[T] {
  def asJs(t: T): js.Any = t.asInstanceOf[js.Any]
}

object VNodeContents {
  implicit object StringAsContents extends VNodeContents[String]
  implicit object VNodeAsContents extends VNodeContents[VNode]
  implicit object VNodeArrayAsContents extends VNodeContents[js.Array[VNode]]
  implicit object NullAsContents extends VNodeContents[Null]
}