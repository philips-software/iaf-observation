package com.philips.research.iaf.observation

import cats.kernel.Eq
import cats.laws.discipline.MonadTests
import cats.tests.CatsSuite
import org.scalacheck.{Arbitrary, Gen}

class CatsMonadForObservationSpec extends CatsSuite {

  implicit def arbitraryObservation[A: Arbitrary]: Arbitrary[Observation[A]] = {
    Arbitrary(
      for{
        value <- Arbitrary.arbitrary[A]
      } yield Observation(value)
    )
  }

  implicit def eqObservation[A: Eq]: Eq[Observation[A]] = Eq.fromUniversalEquals

  checkAll("Observation.MonadLaws", MonadTests[Observation].monad[Int, String, Double])
}
