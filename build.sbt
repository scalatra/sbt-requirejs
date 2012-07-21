import xml.Group

sbtPlugin := true

name := "sbt-requirejs"

organization := "org.scalatra.requirejs"

version := "0.0.4-SNAPSHOT"

libraryDependencies ++= Seq(
  "net.liftweb" %% "lift-json" % "2.4"
)

publishMavenStyle := false

publishTo <<= (version) { version: String =>
   val scalasbt = "http://scalasbt.artifactoryonline.com/scalasbt/"
   val (name, url) = if (version.contains("-SNAPSHOT"))
                       ("sbt-plugin-snapshots", scalasbt+"sbt-plugin-snapshots")
                     else
                       ("sbt-plugin-releases", scalasbt+"sbt-plugin-releases")
   Some(Resolver.url(name, new URL(url))(Resolver.ivyStylePatterns))
}

publishArtifact in Test := false

pomIncludeRepository := { x => false }

packageOptions <<= (packageOptions, name, version, organization) map {
  (opts, title, version, vendor) =>
     opts :+ Package.ManifestAttributes(
      "Created-By" -> "Simple Build Tool",
      "Built-By" -> System.getProperty("user.name"),
      "Build-Jdk" -> System.getProperty("java.version"),
      "Specification-Title" -> title,
      "Specification-Vendor" -> "Scalatra",
      "Specification-Version" -> version,
      "Implementation-Title" -> title,
      "Implementation-Version" -> version,
      "Implementation-Vendor-Id" -> vendor,
      "Implementation-Vendor" -> "Scalatra",
      "Implementation-Url" -> "https://github.com/scalatra/sbt-requirejs"
     )
}

homepage := Some(url("https://github.com/scalatra/sbt-requirejs"))

startYear := Some(2012)

licenses := Seq(("MIT", url("http://github.com/scalatra/sbt-requirejs/raw/HEAD/LICENSE")))

pomExtra <<= (pomExtra, name, description) {(pom, name, desc) => pom ++ Group(
  <scm>
    <connection>scm:git:git://github.com/scalatra/sbt-requirejs.git</connection>
    <developerConnection>scm:git:git@github.com:scalatra/sbt-requirejs.git</developerConnection>
    <url>https://github.com/scalatra/sbt-requirejs</url>
  </scm>
  <developers>
    <developer>
      <id>casualjim</id>
      <name>Ivan Porto Carrero</name>
      <url>http://flanders.co.nz/</url>
    </developer>
  </developers>
)}


