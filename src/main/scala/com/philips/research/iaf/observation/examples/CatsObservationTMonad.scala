package com.philips.research.iaf.observation.examples

import cats.Monad
import com.philips.research.iaf.observation.ObservationT

object CatsObservationTMonad extends App{

  //Example 1: Creating an Observation from Cats' Monad type class
  ObservationT.catsMonadForObservationT(Monad[Option])

  val o = Monad[ObservationT[Option, ?]].pure(5)
  println(s"Created Option[Observation[Int] $o")

//  val o2: ObservationT[Option, Int] = Monad[ObservationT[Option, ?]].map(o)(x => x * 2)
//  println(s"Transformed to Option[Observation[Int] $o2")
}
