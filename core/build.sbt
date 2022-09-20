val sbtTypelevelVersion = "0.4.14" // https://github.com/typelevel/sbt-typelevel/releases

addSbtPlugin("org.typelevel" % "sbt-typelevel" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-scalafix" % sbtTypelevelVersion)
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.4") // https://github.com/scoverage/sbt-scoverage/releases
// add everything needed for the website too in some future update
