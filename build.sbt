lazy val `sbt-circe-org` =
  project.in(file(".")).enablePlugins(NoPublishPlugin).aggregate(core)

// we're not doing code coverage on this plugin. Set to Some("sbt-circe-org") if you wish to run it.
ThisBuild / circeRootOfCodeCoverage := None
ThisBuild / tlCiScalafixCheck := true

lazy val docs = project.in(file("site")).enablePlugins(CirceOrgSitePlugin)

lazy val core = project
  .in(file("core"))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-circe-org"
  )

ThisBuild / tlBaseVersion := "0.2"
ThisBuild / crossScalaVersions := Seq("2.12.18")
ThisBuild / developers := List(
  tlGitHubDev("lorandszakacs", "Loránd Szakács")
)
ThisBuild / startYear := Some(2022)
