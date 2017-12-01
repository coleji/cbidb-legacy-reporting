package Util

import core.SnabbdomFacade.VNode

import scala.scalajs.js

sealed abstract class VNodeContents[T](v: T) {
  def toJs: js.Any = v.asInstanceOf[js.Any]
}

case class Text(s: String) extends VNodeContents[String](s)
case class Children(ns: js.Array[VNode]) extends VNodeContents[js.Array[VNode]](ns)
