// give the user a nice default project!
//ThisBuild / organization := "com.example"
//ThisBuild / scalaVersion := "2.12.8"

def specsVersion = "4.8.3"

lazy val root = (project in file(".")).
  settings(
    name := "$name;format="normalize"$"
  )

lazy val web = (project in file("web"))
  .enablePlugins(ScalaJSBundlerPlugin)
  .settings(commonSettings: _*)
  .settings(

    // TODO: upgrade to 2.13 when akka.js will support it
    scalaVersion := "2.12.10",
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",

    Global / onChangedBuildSource := ReloadOnSourceChanges,
    name := "$name;format="normalize"$-web",

    version in webpack := "4.43.0",
    version in startWebpackDevServer := "3.10.3",
    webpackConfigFile in fastOptJS := Some(
      baseDirectory.value / "webpack" / "dev.webpack.config.js"
    ),
    //    webpackConfigFile in fullOptJS := Some(
    //      baseDirectory.value / "webpack" / "prod.webpack.config.js"
    //    ),
    webpackMonitoredDirectories += (resourceDirectory in Compile).value,
    webpackResources := (baseDirectory.value / "webpack") * "*.js",
    includeFilter in webpackMonitoredFiles := "*",

    webpackExtraArgs := Seq("--progress"),
    useYarn := true,
    webpackBundlingMode in fastOptJS := BundlingMode.LibraryOnly(),
    //    webpackBundlingMode in fullOptJS := BundlingMode.Application,
    webpackEmitSourceMaps := false,
    npmDevDependencies in Compile ++= Seq(
      "expose-loader" -> "0.7.5",
      "url-loader" -> "3.0.0",
      "file-loader" -> "5.1.0",
      "css-loader" -> "3.4.2",
      "style-loader" -> "1.1.3",
      "webpack-merge" -> "4.2.2",
      "mini-css-extract-plugin" -> "0.9.0",
      "webpack-dev-server-status-bar" -> "1.1.2",
      "html-webpack-plugin" -> "3.2.0",
    ),
    npmDependencies in Compile ++= Seq(
      "jquery" -> "3.4.1",
      "semantic-ui-css" -> "2.4.1"
    ),
    libraryDependencies ++= Seq(
      "com.lihaoyi"    %%% "scalatags"        % "0.8.6",
      "org.specs2"     %%% "specs2-core"      % specsVersion  % Test,
      "org.scalamock"  %%% "scalamock"        % "4.4.0"       % Test
    )
  )

lazy val bdd = (project in file("bdd"))
  .enablePlugins(CucumberPlugin)
  .settings(commonSettings: _*)
  .settings(
    name := "$name;format="normalize"$-bdd",
    CucumberPlugin.glues := List("$organization;format="package-dir"$/bdd/"),
    libraryDependencies ++= Seq(
      "io.cucumber" %% "cucumber-scala" % "4.7.1" % Test,
      "org.hamcrest" % "hamcrest" % "2.2" % Test,
      "org.seleniumhq.selenium" % "selenium-java" % "3.141.59" % Test
    )
  )

lazy val commonSettings = Seq(
  scalaVersion := "2.13.1",
  scalacOptions ++= Seq("-deprecation", "-feature"),
  organization := "$organization$",
  version := "0.1",
)
