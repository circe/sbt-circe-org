val sbtTypelevelVersion = "0.7.3" // https://github.com/typelevel/sbt-typelevel/releases
val sbtScoverageVersion = "2.2.1" // https://github.com/scoverage/sbt-scoverage/releases

addSbtPlugin("org.typelevel" % "sbt-typelevel" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-site" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-scalafix" % sbtTypelevelVersion)
addSbtPlugin("org.scoverage" % "sbt-scoverage" % sbtScoverageVersion)
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.13.0")
