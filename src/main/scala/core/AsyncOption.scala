package core

sealed abstract class AsyncOption[+T] {

}

final case class AsyncSuccess[+T](value: T) extends AsyncOption[T]
case object Uninitialized extends AsyncOption[Nothing]
case object Waiting extends AsyncOption[Nothing]
case object AsyncFailure extends AsyncOption[Nothing]