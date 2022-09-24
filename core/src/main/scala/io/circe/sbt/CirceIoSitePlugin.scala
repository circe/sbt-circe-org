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

import laika.ast.LengthUnit._
import laika.ast.Path.Root
import laika.ast._
import laika.helium.Helium
import laika.helium.config.Favicon
import laika.helium.config.HeliumIcon
import laika.helium.config.IconLink
import laika.helium.config.ImageLink
import laika.sbt.LaikaPlugin
import laika.theme.config.Color
import org.typelevel.sbt.TypelevelGitHubPlugin
import org.typelevel.sbt.TypelevelSitePlugin
import sbt.Keys._
import sbt._

object CirceIoSitePlugin extends AutoPlugin {

  override def requires = TypelevelSitePlugin && LaikaPlugin

  import TypelevelGitHubPlugin.autoImport._
  import TypelevelSitePlugin.autoImport._
  import LaikaPlugin.autoImport._

  override def projectSettings: Seq[Setting[_]] = Seq(
    tlSiteRelatedProjects := {
      Seq(
        "circe" -> url("https://github.com/circe/circe"),
        // streaming helpers
        "circe-fs2" -> url("https://github.com/circe/circe-fs2"),
        "circe-iteratee" -> url("https://github.com/circe/circe-iteratee"),
        // derivation helpers
        "circe-generic-extras" -> url("https://github.com/circe/circe-generic-extras"),
        "circe-derivation" -> url("https://github.com/circe/circe-derivation"),
        // end-user utils
        "circe-optics" -> url("https://github.com/circe/circe-optics"),
        // 3rd-party integrations
        "circe-droste" -> url("https://github.com/circe/circe-droste"),
        "circe-spire" -> url("https://github.com/circe/circe-spire"),
        "circe-config" -> url("https://github.com/circe/circe-config"),
        // other formats
        "circe-yaml" -> url("https://github.com/circe/circe-yaml"),
        "circe-bson" -> url("https://github.com/circe/circe-bson"),
        // schemas
        "circe-schema" -> url("https://github.com/circe/circe-schema"),
        "circe-golden" -> url("https://github.com/circe/circe-golden"),
        "circe-json-schema" -> url("https://github.com/circe/circe-json-schema"),
        // other backends
        "circe-jackson" -> url("https://github.com/circe/circe-jackson"),
        "circe-argus" -> url("https://github.com/circe/circe-argus")
      ).filterNot {
        case (repo, _) =>
          tlGitHubRepo.value.contains(repo) // omit ourselves!
      }
    },
    laikaTheme ~= { _.extend(site.CirceIoHeliumExtensions) },
    tlSiteHeliumConfig := {
      Helium.defaults.all
        .metadata(
          title = tlGitHubRepo.value,
          authors = developers.value.map(_.name),
          language = Some("en"),
          version = Some(version.value)
        )
        .site
        .layout(
          contentWidth = px(860),
          navigationWidth = px(275),
          topBarHeight = px(35),
          defaultBlockSpacing = px(10),
          defaultLineHeight = 1.5,
          anchorPlacement = laika.helium.config.AnchorPlacement.Right
        )
        .site
        /** See scaladoc on [[laika.helium.config.CommonConfigOps#themeColors()]] */
        .themeColors(
          // copy and adapt from old circe microsite pallete https://github.com/circe/circe/blob/series/0.14.x/build.sbt#L151
          primary = Color.hex("222749"),
          secondary = Color.hex("222749"),
          primaryMedium = Color.hex("292E53"),
          primaryLight = Color.hex("5B5988"),
          text = Color.hex("5f5f5f"),
          background = Color.hex("F4F3F4"),
          bgGradient = (Color.hex("F4F3F4"), Color.hex("E5E5E6"))
        )
        .site
        .favIcons(
          Favicon.internal(Root / "images" / "navbar_brand.png", "32x32")
        )
        .site
        .darkMode
        .disabled
        .site
        .topNavigationBar(
          homeLink = ImageLink.external(
            "https://github.com/circe/circe",
            Image.internal(
              Root / "images" / "navbar_brand.png",
              width = Length(32, LengthUnit.px).some,
              height = Length(32, LengthUnit.px).some
            )
          ),
          navLinks = tlSiteApiUrl.value.toList.map { url =>
            IconLink.external(
              url.toString,
              HeliumIcon.api,
              options = Styles("svg-link")
            )
          } ++ Seq(
            IconLink.external(
              scmInfo.value.fold("https://github.com/circe")(_.browseUrl.toString),
              HeliumIcon.github,
              options = Styles("svg-link")
            ),
            IconLink.external("https://discord.gg/XF3CXcMzqD", HeliumIcon.chat)
          )
        )
    }
  )

}
