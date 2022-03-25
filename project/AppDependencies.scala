import sbt._

object AppDependencies {

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"             %% "bootstrap-backend-play-28"  % "5.21.0"
  )

  val test: Seq[ModuleID] = Seq(
    "org.scalatest"           %% "scalatest"                % "3.2.3"                 % Test,
    "com.vladsch.flexmark"    %  "flexmark-all"             % "0.35.10"               % Test,
    "org.scalatestplus.play"  %% "scalatestplus-play"       % "5.1.0"                 % Test
  )
}
