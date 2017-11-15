package core

import scala.scalajs.js


abstract class Message[T <: Model, U] {
  def update: (T => U => T)

  def apply(view: View[T])(startingModel: T): scalajs.js.Function1[U, Unit] =
    (payload: U) => view.render(view(update(startingModel)(payload)))
}

abstract class NoArgMessage[T <: Model] {
  def update: (T => T)

  def apply(view: View[T])(startingModel: T): scalajs.js.Function0[Unit] =
    () => view.render(view(update(startingModel)))
}

object Message {
  type SpecificPageMessage[T] = js.Function1[T, Unit]
  type SpecificPageNoArgMessage = js.Function0[Unit]
}