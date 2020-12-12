ThisBuild / scalaVersion := "2.13.4"
ThisBuild / organization := "alexandre.sikiaridis"

lazy val adventOfCode = (project in file("."))
  .settings(
    name := "AdventOfCode20/10",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % Test
  )