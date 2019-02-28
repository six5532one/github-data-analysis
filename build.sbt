enablePlugins(
  BuildInfoPlugin,
  CommonSettingsPlugin,
  CoverallsWrapperPro,
  DockerPackagePlugin,
  NewRelic
)

name := "indexer"

organization := "com.meetup.pro"

scalaVersion := "2.11.8"

coverallsPublishPrReport := true

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.7.0",
  "io.reactivex" % "rxnetty" % "0.4.16",
  "org.json4s" %% "json4s-native" % "3.4.0",
  "com.meetup" %% "json4s-java-time" % "0.0.9",
  "org.json4s" %% "json4s-native" % "3.4.0",
  "org.dispatchhttp" %% "dispatch-core" % "0.14.0",
  "org.dispatchhttp" %% "dispatch-json4s-native" % "0.14.0",
  "org.dispatchhttp" %% "dispatch-json4s-jackson" % "0.14.0",
  "org.mockito" % "mockito-core" % "1.10.19"  % "test"
)

(basePackage in openapiConfig) := "com.meetup"

buildInfoPackage := Seq(organization.value, name.value).mkString(".")

buildInfoKeys := Seq[BuildInfoKey](name, organization, version, scalaVersion, sbtVersion)

resolvers += Resolver.bintrayRepo("meetup", "maven")
