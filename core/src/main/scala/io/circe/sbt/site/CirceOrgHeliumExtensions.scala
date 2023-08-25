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
import cats.effect.kernel.Async
import laika.ast.Path
import laika.io.model.InputTree
import laika.theme.Theme
import laika.theme.ThemeBuilder
import laika.theme.ThemeProvider

object CirceOrgHeliumExtensions extends ThemeProvider {

  private val imageFileNames = Seq(
    "circe_dark.svg",
    "circe_light_no_border.svg",
    "circe_light_no_border_146x173.png"
  )

  override def build[F[_]](implicit F: Async[F]): Resource[F, Theme[F]] = {

    val inputs = imageFileNames.foldLeft(InputTree[F]) {
      case (inputTree, fileName) =>
        inputTree.addClassResource[this.type](s"images/$fileName", Path.Root / "images" / fileName)
    }

    ThemeBuilder[F]("circe Images").addInputs(inputs).build
  }

}
