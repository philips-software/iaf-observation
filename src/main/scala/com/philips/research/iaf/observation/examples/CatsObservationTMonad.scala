package com.philips.research.iaf.observation.examples

import cats.Monad
import cats.implicits._
import com.philips.research.iaf.observation.ObservationT

object CatsObservationTMonad extends App{

  //Example 1: Creating an ObservationT[Option, String] using Cats' Monad type class
  val o1 = Monad[ObservationT[Option, ?]].pure("hello!")
  println(s"Created an ObservationT containing value ${o1.value} of type Option[Observation[String]")

  //Example 2: Mapping the ObservationT[Option, String] to an ObservationT[Option, Int]
  val o2: ObservationT[Option, Int] = Monad[ObservationT[Option, ?]].map(o1)(text => text.length)
  println(s"Transformed to an ObservationT containing value ${o2.value} of type Option[Observation[Int]")
}
