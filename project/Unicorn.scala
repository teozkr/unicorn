import sbt._
import sbt.Keys._

object Unicorn extends Build {



  val core = Project(
    id="unicorn-core",
    base=file("core"))
  .settings(
    libraryDependencies ++= Dependencies.core,
    scalaVersion := "2.10.3"
  )

  val play = Project(
    id="unicorn-play",
    base=file("play")
  ).settings(
    libraryDependencies ++= (Dependencies.core ++ Dependencies.play),
    scalaVersion := "2.10.3"
  ).dependsOn(
    core
  )

  val root = project.in(file(".")).aggregate(core, play).settings(
    organization := "org.virtuslab",

    name := "unicorn",

    version := "0.5.0-SNAPSHOT",

    scalaVersion := "2.10.3",

    resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
    resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

  )
}