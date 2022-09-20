val sbtTypelevelVersion = "0.4.14" // https://github.com/typelevel/sbt-typelevel/releases

addSbtPlugin("org.typelevel" % "sbt-typelevel-ci-release" % "0.4.14")
addSbtPlugin("org.typelevel" % "sbt-typelevel-settings" % "0.4.14")
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0") // https://github.com/scalastyle/scalastyle-sbt-plugin/tags
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.9.3") // https://github.com/scoverage/sbt-scoverage/releases
// add everything needed for the website too in some future update
