package com.philips.research.iaf.observation.examples

object MappingObservations extends App {

  import com.philips.research.iaf.observation.Observation._

  //Generate some string data
  val data = "hello!"

  //Create an observation that holds the string data
  val observation = pure(data)

  println(s"Created observation: $observation with type ${observation.value.getClass}")

  //Generate a new observation of type int holding the length of the string data, obtained by mapping from the previous observation
  val lengthObservation = observation.map(_.length)

  println(s"Generated length observation: $lengthObservation with type ${lengthObservation.value.getClass}")

}
