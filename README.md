## SBT require.js 

A plugin to run r.js from sbt which hooks into the compile task.

At this stage the plugin shells out to node.js so it does require node.js to be installed on the machine compiling the application. 

[Installing Node.js](https://github.com/joyent/node/wiki/Installing-Node.js-via-package-manager)

[RequireJS optimization docs](http://requirejs.org/docs/optimization.html)

[RequireJS documentation](http://requirejs.org/)

Adding the plugin:

```scala
addSbtPlugin("org.scalatra.requirejs" % "sbt-requirejs" % "0.0.1")
```

Using the plugin:

*build.sbt*

```scala
import RequireJsKeys._

seq(requireJsSettings: _*)

buildProfile in (Compile, requireJs) := (
  ("uglify" -> ("ascii_only" -> true)) ~
  ("pragmasOnSave" -> ("excludeCoffeeScript" -> true) ~ ("excludeJade" -> true)) ~
  ("paths" -> ("jquery" -> "empty:")) ~
  ("stubModules" -> List("cs", "jade")) ~
  ("modules" -> List[JValue](("name" -> "main") ~ ("exclude" -> List("coffee-script", "jade"))))
)

baseUrl in (Compile, requireJs) := "js"

mainConfigFile in (Compile, requireJs) <<=
  (sourceDirectory in (Compile, requireJs), baseUrl in (Compile, requireJs))((a, b) => Some(a / b / "main.js"))
```

The `buildProfile` key allows you to configure the r.js tool.
All of the possible configuration options are discussed in this [example.build.js](https://github.com/jrburke/r.js/blob/master/build/example.build.js).
Be sure to read the [require.js optimization docs](http://requirejs.org/docs/optimization.html) too.

In addition to the buildProfile key the appDir, mainConfigFile, dir and baseUrl are configurable through sbt too.
To configure the appDir you have to set the `sourceDirectory` key (defaults to `src/main/requirejs`).
To configure the dir you have to set the `target` key, the dir is an intermediate step for this plugin.
To configure the mainConfigFile you have to set the `mainConfigFile` key.
To configure the baseUrl you have to set the `baseUrl` key.

*Defaults*

```scala
// The location to put the compiled requirejs application
webApp in requireJs <<= (sourceDirectory in c)(_ / "webapp")

// The location of the source files for the require.js app
sourceDirectory in requireJs <<= (sourceDirectory in c)(_ / "requirejs")

// The location of the r.js file, when none is specified it uses the bundled version of r.js
rjs in requireJs := None

// The location of the node.js binary
nodeBin in requireJs := ("which node" !!).trim

// A JSON file to use as source for the require js build profile
buildProfileFile in requireJs <<= (baseDirectory in c)(_ / "project" / "requirejs.build.json")

// A lift-json JValue object to use as r.js build profile (overrides settings defined in buildProfileFile)
buildProfile in requireJs := JNothing

// The generated profile file for r.js to actually use when running the optimizer
buildProfileGenerated in requireJs <<= (target in c)(_ / "requirejs.build.js")

// The working directory for the optimizer to do its work.
target in requireJs <<= (target in c)(_ / "requirejs")

// The baseUrl to use in the build profile
baseUrl in requireJs := "scripts"

// The main config file to use with the path definitions (this  comes from your app)
mainConfigFile in requireJs := None

// files to include when copying from the optimization step to the webapp
includeFilter in requireJs := "*"

// files to exclude when copying from the optimization step to the webapp
excludeFilter in requireJs := "build.txt" || (".*" - ".") || "_*" || HiddenFileFilter
```

