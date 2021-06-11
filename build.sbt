lazy val akkaHttpVersion = "10.2.4"
lazy val akkaVersion    = "2.6.14"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.example",
      scalaVersion    := "2.13.4"
    )),
    name := "clearCounter",
    libraryDependencies ++= Seq(
      "com.typesafe.akka"   %% "akka-http"                % akkaHttpVersion
      , "com.typesafe.akka" %% "akka-http-spray-json"     % akkaHttpVersion
      , "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion
      , "com.typesafe.akka" %% "akka-stream"              % akkaVersion
      , "ch.qos.logback"    %  "logback-classic"          % "1.2.3"
      , "org.json4s"        %% "json4s-jackson"           % "3.7.0-RC1"
      , "com.lihaoyi"       %% "os-lib"                   % "0.7.7"
      , "com.chuusai"       %% "shapeless"                % "2.3.3"
      , "eu.timepit"        %% "refined"                  % "0.9.9"
      , "eu.timepit"        %% "refined-eval"             % "0.9.9"
      , "eu.timepit"        %% "refined-jsonpath"         % "0.9.9"
      , "eu.timepit"        %% "refined-pureconfig"       % "0.9.9"
      , "eu.timepit"        %% "refined-scalacheck"       % "0.9.9"
      , "eu.timepit"        %% "refined-scalaz"           % "0.9.9"
      , "eu.timepit"        %% "refined-scodec"           % "0.9.9"
      , "eu.timepit"        %% "refined-scopt"            % "0.9.9"

      , "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test
      , "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion     % Test
      , "org.scalatest"     %% "scalatest"                % "3.1.4"         % Test
      , "org.mongodb.scala" %% "mongo-scala-driver"       % "2.9.0"
    )
  )
