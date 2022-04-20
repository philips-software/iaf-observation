[![Build Status](https://travis-ci.com/philips-software/iaf-observation.svg?branch=master)](https://travis-ci.com/philips-software/iaf-observation)

⚠️ This project is no longer supported.

The Observation Monad
======

The observation monad allows for the functional composition and manipulation of data points of various data types in a type-safe manner. The monad is written in Scala and is compatible with the [Cats](https://typelevel.org/cats) framework, which provides a plethora of other useful functional programming concepts. The observation monad can be used as a component in other software projects for low-level data-processing in a functional manner.

Before using the observation monad, we recommend some familiarity with monadic programming. To learn more about functional programming concepts in the context of the Cats framework, please refer to the [Cats documentation](https://typelevel.org/cats/typeclasses/monad.html).

## Dependencies

This software requires the [scala build tool (SBT) version 1.1.1 or higher](http://www.scala-sbt.org/) to be installed on your system. Installation instructions for various platforms can be found [here](https://www.scala-sbt.org/1.0/docs/Setup.html).

## Installation

We are currently taking steps to make this project available on Maven Central.

## Usage

To run the examples, execute `sbt run` in the root project directory. This will return a list of all examples. Select an example to be executed by entering the corresponding number. To run a specific example, execute `sbt runMain` followed by the class to run (e.g. `sbt runMain com.philips.research.iaf.observation.examples.CreatingObservations`). Examples can be viewed [here](src/main/scala/com/philips/research/iaf/observation/examples).

### Example 1: combining observations

The example below shows the creation of two observations length and weight, containing a value of type `double` and `int`, respectively. The observations are subsequently combined in a for-comprehension to create a new observation, containing a `double` value, indicating the BMI computed from the length and weight values.

```scala
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
//Outputs "BMI: Observation(30.189590629151066)"
```

### Example 2: transforming future observations

The example below shows how the `string` value of a future observation (i.e. `Future[Observation[String]]`) is changed by mapping over the future observation. This is done by using the [observation transformer](src/main/scala/com/philips/research/iaf/observation/ObservationT.scala).

```scala
//Create a future observation with a string value, wrapping it in the observation transformer
val futureStringObservation = ObservationT(Future.successful(Observation.pure("hi!")))

//Map the string value of this future observation to an int value, while preserving the context of the future observation
val futureIntObservation = futureStringObservation.map(_.length)

//Print the result of the future on completion
futureIntObservation.value.onComplete{case Success(o) => println(s"The future observation completed with result: $o")}
```

### Example 3: using the observation monad with Cats

The examples below show how observations can be used within the Cats framework.

```scala
//Create an Observation[Int] using Cats' Monad type class
val o1 = Monad[Observation].pure(5)
println(s"Created observation $o1")
//Outputs "Created observation Observation(5)"

//Map the observation
val o2 = Monad[Observation].map(o1)(x => x * 2)
println(s"Mapped to observation $o2")
//Outputs "Mapped to observation Observation(10)"

//Use the observation monad in Cats' Option transformer
val observationOption = OptionT.pure[Observation](2)
println(s"Created an OptionT[Observation]: $observationOption with value ${observationOption.value}")
//Outputs "Created an OptionT[Observation]: OptionT(Observation(Some(2))) with value Observation(Some(2))"

val transformedObservationOption = observationOption.map(integer => integer + 5)
println(s"Transformed the value of the Observation[Option[Int]] to ${transformedObservationOption.value}")
//Outputs "Transformed the value of the Observation[Option[Int]] to Observation(Some(7))"
```

## How to test the software

To run the test suite, execute `sbt test` in the root project directory. This will resolve all dependencies and perform all tests, subsequently providing a report of the test results.

## Known issues

- None

## Contact / Getting help

Contact one of the official maintainers listed [here](MAINTAINERS.md). Issues and bugs can be reported as explained in our [contributing guidelines](CONTRIBUTING.md).

## License

Provided [here](LICENSE.md).

## Credits and references

Credits to [Cats](https://typelevel.org/cats/) for providing the fundamental concepts on which this project is built.
