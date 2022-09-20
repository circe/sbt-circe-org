lazy val `sbt-circe-io` =
  project.in(file(".")).enablePlugins(NoPublishPlugin).aggregate(core)

lazy val docs = project
  .enablePlugins(CirceIoSitePlugin)
  .settings(
    tlSiteRelatedProjects += "sbt-typelevel" -> url("https://typelevel.org/sbt-typelevel/")
  )

lazy val core = project
  .in(file("core"))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-circe-io"
  )

ThisBuild / tlBaseVersion := "0.1"
ThisBuild / crossScalaVersions := Seq("2.12.17")
ThisBuild / developers := List(
  tlGitHubDev("lorandszakacs", "Loránd Szakács")
)
ThisBuild / startYear := Some(2022)
