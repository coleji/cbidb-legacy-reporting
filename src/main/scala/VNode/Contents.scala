package VNode

import VNode.SnabbdomFacade.VNode

import scala.scalajs.js

case class Contents(v: js.Any) {
  def this(s: String){
    this(s.asInstanceOf[js.Any])
  }

  def this(ns: js.Array[VNode]){
    this(ns.asInstanceOf[js.Any])
  }

  def this(n: VNode){
    this(n.asInstanceOf[js.Any])
  }

  def toJs: js.Any = v
}