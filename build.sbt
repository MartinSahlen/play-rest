name := """simple-rest-scala"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  evolutions,
  filters,
  "com.typesafe.play" %% "anorm" % "2.4.0",
  "io.swagger" %% "swagger-play2" % "1.5.1",
  "org.scalatestplus" %% "play" % "1.4.0-M3" % "test",
  "com.kenshoo" %% "metrics-play" % "2.4.0_0.4.1"
)

lazy val root = project.in(file(".")).enablePlugins(PlayScala)
