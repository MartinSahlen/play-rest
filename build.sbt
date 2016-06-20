name := """simple-rest-scala"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

resolvers += "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/maven-releases/"

libraryDependencies ++= Seq(
  jdbc,
  evolutions,
  filters,
  "com.typesafe.play" %% "anorm" % "2.4.0",
  "io.swagger" %% "swagger-play2" % "1.5.2",
  "org.scalatestplus" %% "play" % "1.5.0-SNAP1" % "test",
  "com.kenshoo" %% "metrics-play" % "2.4.0_0.4.1"
)

lazy val root = project.in(file(".")).enablePlugins(PlayScala)
