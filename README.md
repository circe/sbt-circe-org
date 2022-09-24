# sbt-circe-io

This project is intended to set up sensible defaults and standards for projects under `circe.io`. If it's useful
outside `circe.io`, it probably should be pushed further upstream
into [sbt-typelevel](https://github.com/typelevel/sbt-typelevel).

## Installing it

The plugin is deployed to Sonatype.

```scala
addSbtPlugin("io.circe" % "sbt-circe-io" % "@VERSION@")
```

## What does it do?

### `CirceIoPlugin`

Triggers automatically. Use on all published `circe` modules.

* Activates the sensible defaults of [sbt-typelevel](https://github.com/typelevel/sbt-typelevel/)
* Sets the organization info
* Enables automated license headers with appropriate years
* Enables [sbt-github-actions](https://github.com/djspiewak/sbt-github-actions) with settings appropriate to our
  workflow

### `CirceIoSitePlugin`

Must be manually triggered. Extends the `TypelevelSitePlugin` with the `circe.io` theme. See
the [sbt-typelevel-site docs](https://typelevel.org/sbt-typelevel/site.html) for usage.

#### Provided images

You can refer to the following images in customizing the `laika` theme:

- `Path.Root / "images" / "circe_dark.svg"`
- `Path.Root / "images" / "circe_light_no_border.svg"`
- `Path.Root / "images" / "circe_light_no_border_146x173.png"`
