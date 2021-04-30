import play.core.PlayVersion.current
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"             %% "bootstrap-backend-play-28"  % "5.0.0"
  )

  val test: Seq[ModuleID] = Seq(
    "org.scalatest"           %% "scalatest"                % "3.1.2"                 % Test,
    "com.vladsch.flexmark"    %  "flexmark-all"             % "0.35.10"               % Test,
    "org.scalatestplus.play"  %% "scalatestplus-play"       % "5.0.0"                 % Test
  )
}
