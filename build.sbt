name := """simple-rest-scala"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

resolvers += "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/maven-releases/"

routesGenerator := StaticRoutesGenerator