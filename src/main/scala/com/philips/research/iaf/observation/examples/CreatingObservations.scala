package com.philips.research.iaf.observation.examples

import com.philips.research.iaf.observation.Observation

object CreatingObservations extends App{

  import com.philips.research.iaf.observation.Observation._

  //Example 1

  //Create an observation by calling the constructor
  val observation1 = Observation(3.42224f)

  println(s"Created observation 1: $observation1")

  //Example 2

  //Create an observation by using pure
  val observation2 = pure(3.42224f)

  println(s"Created observation 2: $observation2")

  //The observations are equal to each other
  println(s"Observation 1 is equal to observation 2: ${observation1.equals(observation2)}")

}
