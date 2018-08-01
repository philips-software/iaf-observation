package com.philips.research.iaf.observation.examples

import cats.implicits._
import com.philips.research.iaf.observation.{Observation, ObservationT}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success

object TransformingObservations extends App{

  //Example 1

  //Create a future observation with a string value, wrapping it in the observation transformer
  val futureStringObservation = ObservationT(Future.successful(Observation.pure("hi!")))

  //Map the string value of this future observation to an int value, while preserving the context of the future observation
  val futureIntObservation = futureStringObservation.map(_.length)

  //Print the result of the future on completion
  futureIntObservation.value.onComplete{case Success(o) => println(s"The future observation completed with result: $o")}

  //Example 2

  //Create an optional observation with pure
  val optionalIntObservation = ObservationT.pure[Option, Int](42)

  //Retrieve the value of the optional observation, if available
  optionalIntObservation.map(_ + 1).value match{
    case Some(Observation(number)) => println(s"The optional observation contains the number $number")
    case None => println("This never happens.")
  }

}
