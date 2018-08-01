package com.philips.research.iaf.observation

import cats.implicits._
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Future
//Import execution context to support in finding an implicit value for Cat's Future applicative
import scala.concurrent.ExecutionContext.Implicits.global

class ObservationTSpec extends FlatSpec with Matchers {

  import ObservationT._

  "An observationT" should
  "construct an observation from any value wrapped in an outer monad" in{

    val optionalDoubleObservation = pure[Option, Double](5d)
    optionalDoubleObservation shouldBe ObservationT(Some(Observation(5d)))

    val futureStringObservation = pure[Future, String]("hi")
    for(value <- futureStringObservation) yield value.equals("hi") shouldBe true

  }

  it should
  "wrap any Observation in any outer Monad" in{

    fromObservation[Option, Double](Observation(5d)) shouldBe ObservationT(Some(Observation(5d)))

    val futureStringObservation = fromObservation[Future, String](Observation("hi"))
    for(value <- futureStringObservation) yield value.equals("hi") shouldBe true
  }

  it should
  "map over the inner Observation's value" in{

    val someObservation = ObservationT(Option(Observation.pure(Seq(1, 2, 3))))
    someObservation.map(b => b.sum) shouldBe ObservationT(Some(Observation.pure(6)))

    val noneObservation = ObservationT(Option.empty[Observation[Seq[Int]]])
    noneObservation.map(b => b.sum) shouldBe ObservationT(Option.empty[Observation[Seq[Int]]])

  }

  it should
    "flatMap over the inner Observation's value" in{

    val listOfObservations = ObservationT(List(Observation(1), Observation(2), Observation(3)))
    val result = listOfObservations.flatMap(value => ObservationT(List(Observation(value), Observation(value + 1))))

    result shouldBe ObservationT(List(
      Observation(1),
      Observation(2),
      Observation(2),
      Observation(3),
      Observation(3),
      Observation(4)
    ))

  }

  it should
  "flatMapF over the inner Observation's value" in{

    val listOfObservations = ObservationT(List(Observation(1), Observation(2), Observation(3)))
    val result = listOfObservations.flatMapF(value => List(Observation(value), Observation(value + 1)))

    result shouldBe ObservationT(List(
      Observation(1),
      Observation(2),
      Observation(2),
      Observation(3),
      Observation(3),
      Observation(4)
    ))
  }

  def f(value: Int): ObservationT[Option, Int] = pure[Option, Int](value + 1)
  def g(value: Int): ObservationT[Option, Int] = pure[Option, Int](value * 3)

  it should
    "obey the left-identity monad law" in{

    pure[Option, Int](1).flatMap(f) shouldBe f(1)
  }

  it should
    "obey the right-identity monad law" in{

    val m = pure[Option, Int](1)

    m.flatMap(pure[Option, Int]) shouldBe m
  }

  it should
    "obey the associative monad law" in{

    val m = pure[Option, Int](1)

    m.flatMap(f).flatMap(g) shouldBe m.flatMap(f(_).flatMap(g))
  }
}
