/*
 * Copyright 2022 circe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.circe.sbt

import scoverage._
import org.typelevel.sbt._
import org.typelevel.sbt.gha._
import sbt.Keys._
import sbt._

import GenerativeKeys._
import TypelevelSettingsPlugin.autoImport._
import TypelevelCiPlugin.autoImport._
import TypelevelSonatypePlugin.autoImport._
import scalafix.sbt.ScalafixPlugin.autoImport._

object CirceIoPlugin extends AutoPlugin {
  object autoImport

  override def trigger = allRequirements

  override def requires = TypelevelPlugin && ScoverageSbtPlugin

  override def buildSettings =
    publishSettings ++ organizationSettings ++ scalafixSettings ++ githubActionsSettings

  override def projectSettings = Seq(
    // TODO: look over projects and see what's in common
  )

  lazy val publishSettings: Seq[Setting[_]] =
    Seq(
      tlSonatypeUseLegacyHost := true,
      tlJdkRelease := Some(8)
    )

  lazy val organizationSettings: Seq[Setting[_]] =
    Seq(
      organization := "io.circe",
      organizationName := "circe"
    )

  @scala.annotation.nowarn
  lazy val githubActionsSettings: Seq[Setting[_]] = Seq(
    githubWorkflowJavaVersions := List("11", "17").map(JavaSpec.temurin(_)),
    tlCiScalafixCheck := true, // yolo, let's see how it works on scala 3 :D
    tlCiScalafmtCheck := true,
    githubWorkflowAddedJobs ++= Seq(
      WorkflowJob(
        id = "coverage",
        name = "Generate coverage report",
        scalas = crossScalaVersions.value.filterNot(_.startsWith("3.")).toList,
        steps = List(WorkflowStep.Checkout) ++ WorkflowStep.SetupJava(
          List(githubWorkflowJavaVersions.value.last)
        ) ++ githubWorkflowGeneratedCacheSteps.value ++ List(
          // TODO: read rootJVM from a property. TODO: see how those are defined
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
    )
  )

  lazy val scalafixSettings: Seq[Setting[_]] =
    Seq(
      scalafixScalaBinaryVersion := (LocalRootProject / scalaBinaryVersion).value,
      scalafixDependencies ++= Seq(
        "org.typelevel" %% "typelevel-scalafix-cats" % "0.1.5", // https://github.com/typelevel/typelevel-scalafix/releases
        "com.github.liancheng" %% "organize-imports" % "0.6.0" // https://github.com/liancheng/scalafix-organize-imports/tags
      )
    )

}
