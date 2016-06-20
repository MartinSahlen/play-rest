libraryDependencies ++= Seq(
  jdbc,
  evolutions,
  filters,
  "com.typesafe.play" %% "anorm" % "2.5.1",
  //"io.swagger" %% "swagger-play2" % "1.5.2",
  "org.scalatestplus" %% "play" % "1.5.0-SNAP1" % "test"
  //"com.kenshoo" %% "metrics-play" % "2.4.0_0.4.1",
)

lazy val root = project.in(file(".")).enablePlugins(PlayScala).dependsOn(swagger).dependsOn(metrics)
lazy val swagger = RootProject(uri("ssh://git@github.com/CreditCardsCom/swagger-play.git"))
lazy val metrics = RootProject(uri("ssh://git@github.com/7thsense/metrics-play.git"))


