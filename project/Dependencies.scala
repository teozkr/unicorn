import sbt._
import sbt.Keys._

object Dependencies {
  val mainCore = Seq(
    "com.typesafe.slick" %% "slick" % "2.0.0"
  )

  val testCore = Seq(
    "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
    "com.h2database" % "h2" % "1.3.174" % "test"
  )

  val core = mainCore ++ testCore

  val mainPlay = Seq(
    "com.typesafe.play" %% "play-slick" % "0.6.0.1"
  )

  val testPlay = Seq(
    "com.typesafe.play" %% "play-test" % "2.2.0" % "test"
  )

  val play = mainPlay ++ testPlay



}