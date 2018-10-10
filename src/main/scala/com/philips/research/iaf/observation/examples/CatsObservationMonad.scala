package com.philips.research.iaf.observation.examples

import cats.Monad
import cats.data.OptionT
import com.philips.research.iaf.observation.Observation

object CatsObservationMonad extends App{

  //Example 1: Creating an Observation from Cats' Monad type class
  val o1 = Monad[Observation].pure(5)
  println(s"Created observation $o1")

  val o2 = Monad[Observation].map(o1)(x => x * 2)
  println(s"Mapped to observation $o2")

  //Example 2: Using the observation monad in Cats' Option transformer
  val observationOption = OptionT.pure[Observation](2)
  println(s"Created an OptionT[Observation]: $observationOption with value ${observationOption.value}")

  val transformedObservationOption = observationOption.map(integer => integer + 5)
  println(s"Transformed the value of the Observation[Option[Int]] to ${transformedObservationOption.value}")

}
