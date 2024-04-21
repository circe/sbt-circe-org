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

/**
 * automatically loaded for all projects that are cross-compiled to ScalaJS, no need too reference it explicitly in builds
 */

import org.scalajs.sbtplugin.ScalaJSPlugin
import sbt._
import scoverage.ScoverageSbtPlugin.autoImport._

object CirceOrgScalaJSPlugin extends AutoPlugin {

  override def trigger = allRequirements

  override def requires: Plugins = CirceOrgPlugin && ScalaJSPlugin

  override def projectSettings: Seq[Setting[_]] = Seq(
    coverageEnabled := false
  )

}
