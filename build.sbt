
lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.philips.research.iaf.observation",
      scalaVersion := "2.12.6",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "IAF-Observation",
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.7"),
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % "test",
      "org.typelevel" %% "cats-core" % "1.0.1",
      "org.typelevel" %% "cats-testkit" % "1.0.1"
    ),
    //Cats relies on improved type inference via the fix for SI-2712, which is not enabled by default.
    scalacOptions += "-Ypartial-unification"
  )
