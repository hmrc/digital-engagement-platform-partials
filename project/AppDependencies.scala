import sbt._

object AppDependencies {

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"             %% "bootstrap-backend-play-28"  % "5.24.0"
  )

  val test: Seq[ModuleID] = Seq(
    "org.scalatest"           %% "scalatest"                % "3.2.12"  % Test,
    "com.vladsch.flexmark"    %  "flexmark-all"             % "0.62.2"  % Test,
    "org.scalatestplus.play"  %% "scalatestplus-play"       % "5.1.0"   % Test
  )
}
