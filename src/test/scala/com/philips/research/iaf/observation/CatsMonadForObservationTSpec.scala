package com.philips.research.iaf.observation

import cats.kernel.Eq
import cats.laws.discipline.MonadTests
import cats.tests.CatsSuite
import org.scalacheck.Arbitrary

class CatsMonadForObservationTSpec extends CatsSuite {

  implicit def arbitraryObservationT[A: Arbitrary]: Arbitrary[ObservationT[Option, A]] = {
    Arbitrary(
      for{
        value <- Arbitrary.arbitrary[A]
      } yield ObservationT.pure[Option, A](value)
    )
  }

  implicit def eqObservation[A: Eq]: Eq[ObservationT[Option, A]] = Eq.fromUniversalEquals

  checkAll("ObservationT.MonadLaws", MonadTests[ObservationT[Option, ?]].monad[Int, String, Double])
}
