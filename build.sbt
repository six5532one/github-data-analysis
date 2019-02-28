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


val elastic4sVersion = "6.2.3"
val circeVersion = "0.9.1"

libraryDependencies ++= Seq(
  "io.reactivex" %% "rxscala" % "0.26.0",
  "com.google.cloud" % "gcloud-java-datastore" % "0.2.1",
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.7.0",
  "io.reactivex" % "rxnetty" % "0.4.16",
  "com.meetup" %% "cupboard" % "17.0.0",
  "org.json4s" %% "json4s-native" % "3.4.0",
  "com.meetup" %% "json4s-java-time" % "0.0.9",
  "com.netflix.hystrix" % "hystrix-rx-netty-metrics-stream" % "1.4.23"
    exclude("io.reactivex", "rxnetty")
    exclude("com.netflix.archaius", "archaius-core"),
  "com.meetup" %% "scala-logger" % "0.2.22",
  "com.meetup" %% "event-logger" % "2.0.35",
  "com.amazonaws" % "aws-java-sdk-sqs" % "1.11.24",
  "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-http" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-json4s" % elastic4sVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "org.scalikejdbc" %% "scalikejdbc" % "3.2.+",
  "org.scalikejdbc" %% "scalikejdbc-interpolation" % "3.2.+",
  "org.scalikejdbc" %% "scalikejdbc-config"  % "3.2.+",
  "mysql" % "mysql-connector-java" % "6.0.6",
  "ch.qos.logback"  %  "logback-classic"   % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided",
  "org.json4s" %% "json4s-native" % "3.4.0",
  "org.dispatchhttp" %% "dispatch-core" % "0.14.0",
  "org.dispatchhttp" %% "dispatch-json4s-native" % "0.14.0",
  "org.dispatchhttp" %% "dispatch-json4s-jackson" % "0.14.0",
  "org.mockito" % "mockito-core" % "1.10.19"  % "test",
  "com.launchdarkly" % "launchdarkly-client" % "4.4.1"
)

(basePackage in openapiConfig) := "com.meetup"

buildInfoPackage := Seq(organization.value, name.value).mkString(".")

buildInfoKeys := Seq[BuildInfoKey](name, organization, version, scalaVersion, sbtVersion)

resolvers += Resolver.bintrayRepo("meetup", "maven")
