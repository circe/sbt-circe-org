val sbtTypelevelVersion = "0.5.0" // https://github.com/typelevel/sbt-typelevel/releases
val sbtScoverageVersion = "2.0.8" // https://github.com/scoverage/sbt-scoverage/releases
val sbtScalaNativeVersion = "0.4.12" // https://github.com/scala-native/scala-native/releases

addSbtPlugin("org.typelevel" % "sbt-typelevel" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-site" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-scalafix" % sbtTypelevelVersion)
addSbtPlugin("org.scala-native" % "sbt-scala-native" % sbtScalaNativeVersion)
addSbtPlugin("org.scoverage" % "sbt-scoverage" % sbtScoverageVersion)
