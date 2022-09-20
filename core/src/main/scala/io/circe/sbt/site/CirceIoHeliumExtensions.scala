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
