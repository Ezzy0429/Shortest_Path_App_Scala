name := "shortestPathApp"
version := "1.0"
//for compability to import library by downgrading to version "2.12.11"
scalaVersion := "2.12.11"

// https://mvnrepository.com/artifact/org.scalafx/scalafx
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.192-R14"

//library to process scalafxml, standard UI to describe scalafx, a markup language
// https://mvnrepository.com/artifact/org.scalafx/scalafxml-core-sfx8
libraryDependencies += "org.scalafx" %% "scalafxml-core-sfx8" % "0.5"

//add plugin scala compiler, paradise
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

fork := true