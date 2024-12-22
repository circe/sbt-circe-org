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

import laika.ast.Path.Root
import laika.ast._
import laika.helium.config.ColorQuintet
import laika.helium.config.Favicon
import laika.helium.config.HeliumIcon
import laika.helium.config.IconLink
import laika.helium.config.ImageLink
import laika.helium.config.TextLink
import laika.helium.config.ThemeNavigationSection
import laika.sbt.LaikaPlugin
import laika.theme.config.Color
import org.typelevel.sbt.TypelevelGitHubPlugin
import org.typelevel.sbt.TypelevelSitePlugin
import sbt._

object CirceOrgSitePlugin extends AutoPlugin {

  override def requires = TypelevelSitePlugin && LaikaPlugin

  import TypelevelGitHubPlugin.autoImport._
  import TypelevelSitePlugin.autoImport._

  private val relatedProjects: Def.Initialize[ThemeNavigationSection] = Def.setting {
    val mappings = List(
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
    }.map {
      case (name, url) =>
        TextLink.external(url.toString, name)
    }

    ThemeNavigationSection(
      "Related Projects",
      mappings.head,
      mappings.tail *
    )
  }

  override lazy val projectSettings = Seq(
    tlSiteHelium := {
      tlSiteHelium.value
        .extendWith(site.CirceOrgHeliumExtensions)
        .site
        /* See scaladoc on laika.helium.config.CommonConfigOps#themeColors()
         * old circe website color theme reference point:
         * https://github.com/circe/circe/blob/c666f32f3bd02644a927b624f7534fdeccbd62a6/build.sbt#L151
         */
        .themeColors(
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
          Favicon.internal(Root / "images" / "circe_light_no_border_146x173.png", "32x32")
        )
        .site
        .darkMode
        // format: off
        .themeColors(
          primary       = Color.hex("A6A6FF"), // #A6A6FF
          secondary     = Color.hex("DDD9FF"), // #DDD9FF
          primaryMedium = Color.hex("8A8AD4"), // #8A8AD4
          primaryLight  = Color.hex("2F2F47"), // #2F2F47
          text          = Color.hex("D2E3FA"), // #D2E3FA
          background    = Color.hex("10101C"), // #10101C
          // gradient not used in current configuraiton
          bgGradient = (
            Color.hex("F4F3F4"),
            Color.hex("E5E5E6")
          )
        )
        // format: on
        .site
        .darkMode
        .syntaxHighlightingColors(
          ColorQuintet(
            // format: off
            Color.hex("292933"), // #292933
            Color.hex("737380"), // #737380
            Color.hex("6B6BB5"), // #6B6BB5
            Color.hex("E6E6FF"), // #E6E6FF
            Color.hex("F2F2FF")  // #F2F2FF
            // format: on
          ),
          ColorQuintet(
            // format: off
            Color.hex("FF6B57"), // #FF6B57
            Color.hex("FFAD54"), // #FFAD54
            Color.hex("FFE957"), // #FFE957
            Color.hex("7EE6B4"), // #7EE6B4
            Color.hex("6D57FF")  // #6D57FF
            // format: on
          )
        )
        .site
        .mainNavigation(appendLinks = Seq(relatedProjects.value))
        .site
        .topNavigationBar(
          homeLink = ImageLink.external(
            "https://github.com/circe/circe",
            Image.internal(Root / "images" / "circe_light_no_border_146x173.png")
          ),
          navLinks = Seq(
            IconLink.external("https://discord.gg/XF3CXcMzqD", HeliumIcon.chat)
          )
        )
    }
  )

}
