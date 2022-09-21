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

package io.circe.sbt.site

import cats.effect.Resource
import cats.effect.kernel.Sync
import laika.ast.Path
import laika.io.model.InputTree
import laika.theme.Theme
import laika.theme.ThemeBuilder
import laika.theme.ThemeProvider

object CirceIoHeliumExtensions extends ThemeProvider {

  // TODO: rename images, currently copied from https://github.com/circe/circe/tree/series/0.14.x/docs/src/main/resources/microsite/img
  override def build[F[_]](implicit F: Sync[F]): Resource[F, Theme[F]] =
    ThemeBuilder[F]("Circe Helium Extensions")
      .addInputs(
        InputTree[F].addStream(
          F.blocking(getClass.getResourceAsStream("site/styles.css")),
          Path.Root / "site" / "styles.css"
        )
      )
      .addInputs(
        InputTree[F].addStream(
          F.blocking(getClass.getResourceAsStream("images/circe_alt.svg")),
          Path.Root / "images" / "circe_alt.svg"
        )
      )
      .addInputs(
        InputTree[F].addStream(
          F.blocking(getClass.getResourceAsStream("images/circe_navbar_brand.svg")),
          Path.Root / "images" / "circe_navbar_brand.svg"
        )
      )
      .addInputs(
        InputTree[F].addStream(
          F.blocking(getClass.getResourceAsStream("images/circe.svg")),
          Path.Root / "images" / "circe.svg"
        )
      )
      .addInputs(
        InputTree[F].addStream(
          F.blocking(getClass.getResourceAsStream("images/navbar_brand.png")),
          Path.Root / "images" / "navbar_brand.png"
        )
      )
      .addInputs(
        InputTree[F].addStream(
          F.blocking(getClass.getResourceAsStream("images/navbar_brand2x.png")),
          Path.Root / "images" / "navbar_brand2x.png"
        )
      )
      .addInputs(
        InputTree[F].addStream(
          F.blocking(getClass.getResourceAsStream("images/sidebar_brand.png")),
          Path.Root / "images" / "sidebar_brand.png"
        )
      )
      .addInputs(
        InputTree[F].addStream(
          F.blocking(getClass.getResourceAsStream("images/sidebar_brand2x.png")),
          Path.Root / "images" / "sidebar_brand2x.png"
        )
      )
      .build

}
