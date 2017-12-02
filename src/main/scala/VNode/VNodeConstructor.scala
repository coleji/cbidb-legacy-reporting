package VNode

import VNode.SnabbdomFacade.VNode
import _root_.VNode.SnabbdomFacade.snabbdom.h

import scala.scalajs.js

sealed abstract class VNodeConstructor(tag: String) {
  def apply[T](c: T)(implicit tc: VNodeContents[T]): VNode = apply(contents = c)
  def apply[T](
    id: String = "",
    classes: List[String] = Nil,
    props: Map[String, String] = Map.empty,
    style: Map[String, String] = Map.empty,
    events: Map[String, js.Any] = Map.empty,
    contents: T = null
  )(implicit tc: VNodeContents[T]): VNode = {
    // E.g. tag#id.class1.class2.class3
    val firstArg = List(
      tag,
      if (id != "") "#" + id else "",
      classes.map("." + _).mkString("")
    ).mkString("")

    val unifiedProps: js.Object = js.Dynamic.literal(
      "props" -> VNodeConstructor.MapToJsDictionary(props),
      "style" -> VNodeConstructor.MapToJsDictionary(style),
      "on" -> VNodeConstructor.MapToJsDictionary(events)
    )

    h(firstArg, unifiedProps, tc.asJs(contents))
  }
}

object VNodeConstructor {
  def MapToJsDictionary(map: Map[String, _]): js.Object = {
    val result = js.Dictionary.empty[Any]
    for (pair <- map) {
      result(pair._1) = pair._2.asInstanceOf[js.Any]
    }
    result.asInstanceOf[js.Object]
  }
}

case object br extends VNodeConstructor("br")
case object button extends VNodeConstructor("button")
case object div extends VNodeConstructor("div")
case object form extends VNodeConstructor("form")
case object input extends VNodeConstructor("input")
case object img extends VNodeConstructor("img")
case object li extends VNodeConstructor("li")
case object option extends VNodeConstructor("option")
case object select extends VNodeConstructor("select")
case object span extends VNodeConstructor("span")
case object table extends VNodeConstructor("table")
case object tbody extends VNodeConstructor("tbody")
case object td extends VNodeConstructor("td")
case object th extends VNodeConstructor("th")
case object thead extends VNodeConstructor("thead")
case object tr extends VNodeConstructor("tr")
case object ul extends VNodeConstructor("ul")