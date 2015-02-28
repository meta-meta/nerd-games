name := """nerd-games"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "org.webjars" % "bootstrap" % "3.0.2"
)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

