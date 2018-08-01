package com.philips.research.iaf.observation

import cats.{Applicative, Functor, Monad}

object ObservationT{

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