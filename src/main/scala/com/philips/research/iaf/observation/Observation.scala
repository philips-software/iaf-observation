package com.philips.research.iaf.observation

object Observation{

  /**
    * Wraps a value into an Observation.
    * @param x
    * @tparam A
    * @return
    */
  def pure[A](x: A): Observation[A] = new Observation[A](x)

}

/**
  * The observation monad allows for the functional composition and transformation of data points of various data types in a type-safe manner.
  * @param value The value contained by the observation monad.
  * @tparam A
  */
final case class Observation[A](value: A){

  import Observation._

  /**
    * Applies the function f: A => Observation[B] to the value of the Observation[A], yielding Observation[B].
    * @param f
    * @tparam B
    * @return
    */
  def flatMap[B](f: A => Observation[B]): Observation[B] ={
    f(value)
  }

  /**
    * Applies function f: A => B to the value of the Observation[A], yielding Observation[B].
    * @param f
    * @tparam B
    * @return
    */
  def map[B](f: A => B): Observation[B] ={
    pure(f(value))
  }

  /**
    * Retrieves the value of this observation.
    * @return
    */
  def get: A = value

  /**
    * Indicates whether some other object is "equal to" this Observation.
    * Returns true if and only if other is an Observation with a value that equals this Observation's value.
    * @param other
    * @return
    */
  override def equals(other: Any): Boolean = other match{
    case o: Observation[A] => get equals o.get
    case _ => false
  }

  /**
    * Returns a hash code value for the Observation.
    * @return
    */
  override def hashCode(): Int = super.hashCode()

  /**
    * Returns a string representation of the Observation.
    * @return
    */
  override def toString: String = s"Observation($value)"
}






