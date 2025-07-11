val sbtTypelevelVersion = "0.8.0" // https://github.com/typelevel/sbt-typelevel/releases
val sbtScoverageVersion = "2.3.1" // https://github.com/scoverage/sbt-scoverage/releases

addSbtPlugin("org.typelevel" % "sbt-typelevel" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-site" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-scalafix" % sbtTypelevelVersion)
addSbtPlugin("org.scoverage" % "sbt-scoverage" % sbtScoverageVersion)
