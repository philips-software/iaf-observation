package com.philips.research.iaf.observation

import cats.{Applicative, Functor, Monad}

/**
  * The observation Monad transformer. Wraps an Observation into any outer Monad F.
  */
final case class ObservationT[F[_], A](value: F[Observation[A]]) {

  /**
    * Applies function f: A => B to the value of the ObservationT[F, A], yielding ObservationT[F, B].
    * @param f
    * @param F
    * @tparam B
    * @return
    */
  def map[B](f: A => B)(implicit F: Functor[F]): ObservationT[F, B] ={

    ObservationT(F.map(value)(_.map(f)))
  }

  /**
    * Applies the function f: A => F[Observation[B]] to the value of the Observation[A], yielding ObservationT[F, B].
    * @param f
    * @param F
    * @tparam B
    * @return
    */
  def flatMapF[B](f: A => F[Observation[B]])(implicit F: Monad[F]): ObservationT[F, B] ={

    ObservationT(F.flatMap(value)(observation => f(observation.value)))
  }

  /**
    * Applies the function f: A => ObservationT[F, B] to the value of the Observation[A], yielding ObservationT[F, B].
    * @param f
    * @param F
    * @tparam B
    * @return
    */
  def flatMap[B](f: A => ObservationT[F, B])(implicit F: Monad[F]): ObservationT[F, B] ={
    ObservationT(F.flatMap(value)(observation => f(observation.value).value))
  }
}

object ObservationT extends ObservationTInstances {

  /**
    * Wraps a value into an F[Observation[_].
    * @param value The value contained by the Observation.
    * @param F The outer Monad.
    * @tparam F
    * @tparam A
    * @return
    */
  def pure[F[_], A](value: A)(implicit F: Applicative[F]): ObservationT[F, A] = ObservationT(F.pure(Observation.pure(value)))

  /**
    * Constructs an ObservationT from an Observation.
    * @param observation
    * @param F
    * @tparam F
    * @tparam A
    * @return
    */
  def fromObservation[F[_], A](observation: Observation[A])(implicit F: Applicative[F]): ObservationT[F, A] = ObservationT(F.pure(observation))
}

/**
  * Provides the ObservationT type class instance of Cats' Monad type class (i.e. Monad[ObservationT[F, A]\]).
  */
trait ObservationTInstances{

  implicit def catsMonadForObservationT[F[_], A](implicit F0: Monad[F]): ObservationTMonad[F] = {
    new ObservationTMonad[F] {
      override implicit def mon: Monad[F] = F0
    }
  }
}

/**
  * Implementation of Monad[ObservationT[F, A]\] for any outer Monad F.
  * @tparam F The outer Monad F.
  */
trait ObservationTMonad[F[_]] extends Monad[ObservationT[F, ?]]{

  implicit def mon: Monad[F]

  override def pure[A](x: A): ObservationT[F, A] = ObservationT.pure(x)

  override def flatMap[A, B](fa: ObservationT[F, A])(f: A => ObservationT[F, B]): ObservationT[F, B] = fa.flatMap(f)

  override def map[A, B](fa: ObservationT[F, A])(f: A => B): ObservationT[F, B] = fa.map(f)

  override def tailRecM[A, B](a: A)(f: A => ObservationT[F, Either[A, B]]): ObservationT[F, B] = {

    ObservationT(
      mon.tailRecM(a)(a0 => mon.map(f(a0).value) {
        case Observation(Left(a2)) => Left[A, Observation[B]](a2)
        case Observation(Right(b)) => Right[A, Observation[B]](Observation(b))
      }
      )
    )
  }
}

