name := """Talentica Reads"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin) //enable plugin

scalaVersion := "2.11.7"

swaggerDomainNameSpaces := Seq("models")

routesGenerator := InjectedRoutesGenerator

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-slick" % "2.0.0",
    "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
    "com.h2database" % "h2" % "1.4.187",
    "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test",
    "org.webjars" % "swagger-ui" % "2.2.0",
    specs2 % Test
)

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

fork in run := true

fork in run := true