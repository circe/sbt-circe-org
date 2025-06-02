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

import org.typelevel.sbt._
import org.typelevel.sbt.gha._
import sbt.Keys._
import sbt._
import scalafix.sbt.ScalafixPlugin.autoImport._
import scoverage.ScoverageSbtPlugin.autoImport._
import scoverage._
import xerial.sbt.Sonatype.autoImport._

import GenerativeKeys._
import TypelevelSettingsPlugin.autoImport._
import TypelevelCiPlugin.autoImport._

object CirceOrgPlugin extends AutoPlugin {
  object autoImport {
    lazy val circeRootOfCodeCoverage = settingKey[Option[String]](
      "None if code coverage is disabled. Defined with name of the root project from which to run code coverage."
    )
  }

  import autoImport._

  override def trigger = allRequirements

  override def requires: Plugins = TypelevelPlugin && ScoverageSbtPlugin

  override def buildSettings: Seq[Setting[_]] =
    codeCoverageSettings ++ publishSettings ++ organizationSettings ++ scalafixSettings ++ githubActionsSettings

  override def projectSettings: Seq[Setting[_]] = Seq.empty

  lazy val codeCoverageSettings: Seq[Setting[_]] = Seq(
    coverageHighlighting := true,
    coverageExcludedPackages := "io.circe.examples.*",
    circeRootOfCodeCoverage := None
  )

  lazy val publishSettings: Seq[Setting[_]] =
    Seq(
      sonatypeCredentialHost := xerial.sbt.Sonatype.sonatypeCentralHost,
      tlJdkRelease := Some(8)
    )

  lazy val organizationSettings: Seq[Setting[_]] =
    Seq(
      organization := "io.circe",
      organizationName := "circe"
    )

  @scala.annotation.nowarn("msg=possible missing interpolator")
  lazy val githubActionsSettings: Seq[Setting[_]] = Seq(
    githubWorkflowJavaVersions := List("11", "17").map(JavaSpec.temurin(_)),
    tlCiScalafixCheck := true, // yolo, let's see how it works on scala 3 :D
    tlCiScalafmtCheck := true,
    githubWorkflowAddedJobs ++=
      (circeRootOfCodeCoverage.value match {
        case None           => List.empty
        case Some(rootProj) =>
          List(
            WorkflowJob(
              id = "coverage",
              name = "Generate coverage report",
              scalas = githubWorkflowScalaVersions.value.toList,
              steps = List(
                WorkflowStep.Use(
                  UseRef.Public("sbt", "setup-sbt", "v1"),
                  name = Some("Install sbt")
                ),
                WorkflowStep.Checkout
              ) ++ WorkflowStep.SetupJava(
                List(githubWorkflowJavaVersions.value.last)
              ) ++ githubWorkflowGeneratedCacheSteps.value ++ List(
                WorkflowStep.Sbt(List("coverage", s"$rootProj/test", "coverageAggregate")),
                WorkflowStep.Use(
                  UseRef.Public(
                    "codecov",
                    "codecov-action",
                    "v4"
                  ),
                  params = Map(
                    "flags" -> List("${{matrix.scala}}").mkString(",")
                  )
                )
              )
            )
          )
      })
  )

  lazy val scalafixSettings: Seq[Setting[_]] =
    Seq(
      scalafixDependencies ++= Seq.empty
    )

}
