/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package config

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder


class AppConfigSpec extends PlaySpec with GuiceOneAppPerSuite {

  override def fakeApplication(): Application =
    GuiceApplicationBuilder()
      .configure(
        "urls.digital-engagement-platform-skin" -> "http://skinHost:12345"
      )
      .build()

  lazy val appConfig = app.injector.instanceOf[AppConfig]

  "AppConfig" must {
    "return correct url for dep skin service js file" in {
      appConfig.hmrcSkinJSUrl mustBe "http://skinHost:12345/engagement-platform-skin/assets/javascripts/hmrcChatSkinBundle.js"
    }

    "return correct url for dep skin service embedded css file" in {
      appConfig.hmrcSkinEmbeddedCSSUrl mustBe "http://skinHost:12345/engagement-platform-skin/assets/stylesheets/chat-ui-embedded.css"
    }

    "return correct url for dep skin service popup css file" in {
      appConfig.hmrcSkinPopupCSSUrl mustBe "http://skinHost:12345/engagement-platform-skin/assets/stylesheets/chat-ui-popup.css"
    }
  }

}