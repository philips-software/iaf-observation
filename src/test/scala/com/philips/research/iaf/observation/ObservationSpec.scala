package com.philips.research.iaf.observation

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class ObservationSpec extends FlatSpec with Matchers {

  import Observation._

  "An observation" should
  "return its value with the correct data type" in{

    val o1 = pure(5)
    val o2 = pure(4.3f)
    val o3 = pure("5")
    val o4 = pure(5.4d)

    o1.get shouldBe 5
    o2.get shouldBe 4.3f
    o3.get shouldBe "5"
    o4.get shouldBe 5.4d
  }

  it should
  "equal another observation with equal value" in{

    val o1 = pure(5)
    val o2 = pure(5)
    val o3 = pure("5")
    val o4 = pure(5.4)

    o1 shouldEqual o2
    o1 shouldNot equal(o3)
    o1 shouldNot equal(o4)
  }

  def f(value: Int): Observation[Int] = pure(value + 1)
  def g(value: Int): Observation[Int] = pure(value * 3)

  it should
  "obey the left-identity monad law" in{

    pure(1).flatMap(f) shouldBe f(1)
  }

  it should
  "obey the right-identity monad law" in{

    val m = pure(1)

    m.flatMap(pure) shouldBe m
  }

  it should
  "obey the associative monad law" in{

    val m = pure(1)

    m.flatMap(f).flatMap(g) shouldBe m.flatMap(f(_).flatMap(g))
  }
}
