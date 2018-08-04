package com.philips.research.iaf.observation

import cats.Monad
import scala.annotation.tailrec

/**
  * Provides the implementation of Cats' Monad type class Monad[Observation].
  */
object ObservationInstances{

  implicit val catsObservationMonad: Monad[Observation] = new Monad[Observation] {

    override def pure[A](x: A): Observation[A] = Observation.pure(x)

    override def flatMap[A, B](fa: Observation[A])(f: A => Observation[B]): Observation[B] = fa.flatMap(f)

    @tailrec
    override def tailRecM[A, B](a: A)(f: A => Observation[Either[A, B]]): Observation[B] = f(a) match{
      case Observation(Right(b)) => pure(b)
      case Observation(Left(a2)) => tailRecM(a2)(f)
    }
  }
}
