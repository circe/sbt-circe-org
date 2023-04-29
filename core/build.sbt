val sbtTypelevelVersion = "0.4.20" // https://github.com/typelevel/sbt-typelevel/releases
val sbtScoverageVersion = "2.0.7" // https://github.com/scoverage/sbt-scoverage/releases
val sbtScalaNativeVersion = "0.4.10" // https://github.com/scala-native/scala-native/releases

addSbtPlugin("org.typelevel" % "sbt-typelevel" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-site" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-scalafix" % sbtTypelevelVersion)
addSbtPlugin("org.scala-native" % "sbt-scala-native" % sbtScalaNativeVersion)
addSbtPlugin("org.scoverage" % "sbt-scoverage" % sbtScoverageVersion)
