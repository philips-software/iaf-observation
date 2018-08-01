package com.philips.research.iaf.observation.examples

import com.philips.research.iaf.observation.Observation

object CombiningObservations extends App{

  import Observation._

  //Create a length (double) observation and weight (int) observation
  val lengthObservation = pure(1.82d)
  val weightObservation = pure(100)

  //Derive a new observation 'bmi' (double) from combining the values contained in the previous two observations
  val bmi = for{
    length <- lengthObservation
    weight <- weightObservation
  } yield weight / Math.pow(length, 2)

  //Print the result
  println(s"BMI: $bmi")

}
