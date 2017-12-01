package Util

import core.SnabbdomFacade.VNode
import core.SnabbdomFacade.snabbdom.h

import scala.scalajs.js

sealed abstract class VNodeConstructor(tag: String) {
  def apply(
    id: String = "",
    classes: List[String] = Nil,
    props: Map[String, String] = Map.empty,
    style: Map[String, String] = Map.empty,
    events: Map[String, js.Any] = Map.empty,
    contents: VNodeContents[_] = Children(js.Array())
  ): VNode = {
    // E.g. tag#id.class1.class2.class3
    val firstArg = List(
      tag,
      if (id != "") "#" + id else "",
      classes.map("." + _).mkString("")
    ).mkString("")

    val propsObject: js.Object = js.Object(props)
    val styleObject: js.Object = js.Object(style)
    val eventsObject: js.Object = js.Object(events)

    val unifiedProps: js.Object = js.Dynamic.literal(
      "props" -> propsObject,
      "style" -> styleObject,
      "events" -> eventsObject
    )

    h(firstArg, unifiedProps, contents.toJs)
  }
}

case object br extends VNodeConstructor("br")
case object button extends VNodeConstructor("button")
case object div extends VNodeConstructor("div")
case object form extends VNodeConstructor("form")
case object input extends VNodeConstructor("input")
case object li extends VNodeConstructor("li")
case object option extends VNodeConstructor("option")
case object select extends VNodeConstructor("select")
case object span extends VNodeConstructor("span")
case object table extends VNodeConstructor("table")
case object tbody extends VNodeConstructor("tbody")
case object td extends VNodeConstructor("td")
case object thead extends VNodeConstructor("thead")
case object tr extends VNodeConstructor("tr")
case object ul extends VNodeConstructor("ul")