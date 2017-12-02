package VNode

import org.scalajs.dom.{Element, Text}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.annotation.JSImport.Namespace
import scala.scalajs.js.|

object SnabbdomFacade {
  @JSImport("snabbdom", Namespace)
  @js.native
  object snabbdom extends js.Object {
    def init(modules: js.Array[js.Object]): js.Function2[VNode | Element, VNode, VNode] = js.native
    object h extends js.Function3[String, js.UndefOr[js.Any], js.UndefOr[js.Any], VNode] {
      def apply(selector: String, b: js.UndefOr[js.Any] = js.undefined, c: js.UndefOr[js.Any] = js.undefined): VNode = js.native
    }
  }

  val patch: js.Function2[VNode | Element, VNode, VNode] = snabbdom.init(scala.scalajs.js.Array(
    `class`.default,
    props.default,
    style.default,
    eventlisteners.default
  ))

  @js.native
  class VNode(
     selector: js.UndefOr[String],
     var data: js.UndefOr[VNodeData],
     var children: js.UndefOr[js.Array[VNode | String]],
     text: js.UndefOr[String],
     elm: js.UndefOr[Element | Text],
     key: js.UndefOr[String | Double]
   ) extends js.Object

  @js.native
  class VNodeData extends js.Object

  @JSImport("snabbdom/modules/class", Namespace)
  @js.native
  object `class` extends js.Object {
    val default: js.Object = js.native
  }

  @JSImport("snabbdom/modules/props", Namespace)
  @js.native
  object props extends js.Object {
    val default: js.Object = js.native
  }

  @JSImport("snabbdom/modules/style", Namespace)
  @js.native
  object style extends js.Object {
    val default: js.Object = js.native
  }

  @JSImport("snabbdom/modules/eventlisteners", Namespace)
  @js.native
  object eventlisteners extends js.Object {
    val default: js.Object = js.native
  }

  @JSImport("snabbdom/tovnode", Namespace)
  @js.native
  private object tovnode extends js.Object {
    object default extends js.Function1[Element, VNode] {
      def apply(e: Element): VNode = js.native
    }
  }

  val toVNode: js.Function1[Element, VNode] = tovnode.default
}
