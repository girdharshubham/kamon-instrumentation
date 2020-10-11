name := "kamon-instrumentation"

version := "0.1"

scalaVersion := "2.12.12"
val kamonVersion = "2.1.4"
val configVersion = "1.4.0"
val akkaVersion = "2.6.10"

// Kamon Instrumentation
lazy val `kamon-bundle` = "io.kamon" %% "kamon-bundle" % kamonVersion
lazy val `kamon-prometheus` = "io.kamon" %% "kamon-prometheus" % kamonVersion

// LightBend
lazy val `typesafe-config` = "com.typesafe" % "config" % configVersion

// Akka
lazy val `akka-streams` = "com.typesafe.akka" %% "akka-stream" % akkaVersion

lazy val root = (project in file("."))
  .aggregate(
    plain,
    akka
  )

lazy val plain = (project in file("plain"))
  .settings(
    libraryDependencies ++= Seq(
      `kamon-bundle`,
      `kamon-prometheus`,
      `typesafe-config`
    )
  )

lazy val `akka` = (project in file("akka"))
  .settings(
    libraryDependencies ++= Seq(
      `kamon-bundle`,
      `kamon-prometheus`,
      `akka-streams`,
      `typesafe-config`
    )
  )
