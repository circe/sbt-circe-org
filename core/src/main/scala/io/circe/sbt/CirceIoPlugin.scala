package io.circe.sbt

import org.typelevel.sbt._
import org.typelevel.sbt.gha._
import sbt.Keys._
import sbt._

import ExplicitDepsPlugin.autoImport._
import GenerativeKeys._
import TypelevelKernelPlugin._
import autoImport._
import TypelevelCiPlugin.autoImport._
import TypelevelSonatypePlugin.autoImport._
import ScalastylePlugin.autoImport._
import ScoverageSbtPlugin.autoImport._

object CirceIoPlugin extends AutoPlugin {
  object autoImport

  override def trigger = allRequirements

  override def requires = TypelevelCiPlugin && TypelevelSonatypePlugin && ScalastylePlugin && ScoverageSbtPlugin

  override def buildSettings =
    publishSettings ++ organizationSettings ++ githubActionsSettings

  override def projectSettings = explicitDepsSettings

  lazy val publishSettings: Seq[Setting[_]] =
    Seq(
      tlSonatypeUseLegacyHost := true
    )

  lazy val organizationSettings: Seq[Setting[_]] =
    Seq(
      organization := "io.circe",
      organizationName := "io.circe"
    )

  lazy val githubActionsSettings: Seq[Setting[_]] =
    Seq(
      githubWorkflowJavaVersions := List("8", "11", "17").map(JavaSpec.temurin(_)),
      tlCiScalafixCheck := false,
      githubWorkflowBuildPostamble ++= Seq(
        WorkflowJob(
          id = "scalafmt",
          name = "Scalafmt and Scalastyle",
          scalas = List(crossScalaVersions.value.last),
          steps = List(WorkflowStep.Checkout) ++ WorkflowStep.SetupJava(
            List(githubWorkflowJavaVersions.value.last)
          ) ++ githubWorkflowGeneratedCacheSteps.value ++ List(
            WorkflowStep.Sbt(
              List("+scalafmtCheckAll", "scalafmtSbtCheck", "scalastyle"),
              name = Some("Scalafmt and Scalastyle tests")
            )
          )
        ),
        WorkflowJob(
          id = "coverage",
          name = "Generate coverage report",
          scalas = crossScalaVersions.value.filterNot(_.startsWith("3.")).toList,
          steps = List(WorkflowStep.Checkout) ++ WorkflowStep.SetupJava(
            List(githubWorkflowJavaVersions.value.last)
          ) ++ githubWorkflowGeneratedCacheSteps.value ++ List(
            WorkflowStep.Sbt(List("coverage", "rootJVM/test", "coverageAggregate")),
            WorkflowStep.Use(
              UseRef.Public(
                "codecov",
                "codecov-action",
                "v2"
              ),
              params = Map(
                "flags" -> List("${{matrix.scala}}", "${{matrix.java}}").mkString(",")
              )
            )
          )
        )
      ).githubWorkflowBuildMatrixFailFast(:=).Some(false)
    )

  lazy val explicitDepsSettings: Seq[Setting[_]] =
    Seq(
      unusedCompileDependenciesTest := {
        if (tlSkipIrrelevantScalas.value && (unusedCompileDependenciesTest / skip).value)
          ()
        else unusedCompileDependenciesTest.value
      },
      skipIfIrrelevant(unusedCompileDependenciesTest)
    )

}
