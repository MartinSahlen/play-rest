name := """simple-rest-scala"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  evolutions,
  "com.typesafe.play" %% "anorm" % "2.4.0",
  "org.scalatestplus" %% "play" % "1.4.0-M3" % "test"
)

lazy val root = project.in(file(".")).enablePlugins(PlayScala)
