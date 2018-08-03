package com.philips.research.iaf.observation

class ObservationInstances{

  import cats.Monad

  import scala.annotation.tailrec

  implicit val observationMonad: Monad[Observation] = new Monad[Observation] {

    /**
      * Wraps a value into an Observation.
      * @param x
      * @tparam A
      * @return
      */
    override def pure[A](x: A): Observation[A] = Observation.pure(x)

    /**
      * Applies the function f: A => Observation[B] to the value of the Observation[A], yielding Observation[B].
      * @param fa
      * @param f
      * @tparam A
      * @tparam B
      * @return
      */
    override def flatMap[A, B](fa: Observation[A])(f: A => Observation[B]): Observation[B] = fa.flatMap(f)

    @tailrec
    override def tailRecM[A, B](a: A)(f: A => Observation[Either[A, B]]): Observation[B] = f(a) match{
      case Observation(Right(b)) => pure(b)
      case Observation(Left(a2)) => tailRecM(a2)(f)
    }
  }
}
