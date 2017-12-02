package Pages.CounterPage

import Components.Counter
import VNode.SnabbdomFacade.VNode
import _root_.VNode.div
import core.Message.SpecificPageNoArgMessage
import core.{NoArgMessage, View}

import scala.scalajs.js

class CounterPageView(render: VNode => Unit) extends View[CounterPageModel](render) {
  private val view = this

  object IncrementFirst extends NoArgMessage[CounterPageModel] {
    def update: CounterPageModel => CounterPageModel =
      model => CounterPageModel(model.firstCount + 1, model.secondCount)
  }

  object DecrementFirst extends NoArgMessage[CounterPageModel] {
    def update: CounterPageModel => CounterPageModel =
      model => CounterPageModel(model.firstCount - 1, model.secondCount)
  }

  object IncrementSecond extends NoArgMessage[CounterPageModel] {
    def update: CounterPageModel => CounterPageModel =
      model => CounterPageModel(model.firstCount, model.secondCount + 1)
  }

  object DecrementSecond extends NoArgMessage[CounterPageModel] {
    def update: CounterPageModel => CounterPageModel =
      model => CounterPageModel(model.firstCount, model.secondCount - 1)
  }

  def apply(model: CounterPageModel): VNode = {
    val firstCounter: Counter = {
      val increment: SpecificPageNoArgMessage = IncrementFirst(view)(model)
      val decrement: SpecificPageNoArgMessage = DecrementFirst(view)(model)
      new Counter(increment, decrement)(model.firstCount)
    }

    val secondCounter: Counter = {
      val increment: SpecificPageNoArgMessage = IncrementSecond(view)(model)
      val decrement: SpecificPageNoArgMessage = DecrementSecond(view)(model)
      new Counter(increment, decrement)(model.secondCount)
    }

    div(id = "page", contents = js.Array(
      firstCounter.vnode,
      secondCounter.vnode
    ))
  }
}
