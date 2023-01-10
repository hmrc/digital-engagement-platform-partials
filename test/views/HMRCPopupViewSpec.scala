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

package views

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.FakeRequest
import views.html.HMRCPopupView

class HMRCPopupViewSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {
    private val fakeRequest = FakeRequest("GET", "/")
    private val view = app.injector.instanceOf[HMRCPopupView]

    "HMRC ChatSkin response" when {
        val loadedView = view()(fakeRequest)
        val document : Document = Jsoup.parse(loadedView.toString())

        "successfully rendered" should {
            "include hmrc-webchat-tag element" in {
                document.getElementById("hmrc-webchat-tag") should not be null
            }
            "include javascript url in hmrc-webchat-tag" in {
                document.getElementById("hmrc-webchat-tag").toString should include ("http://localhost:9193/engagement-platform-skin/assets/javascripts/hmrcChatSkinBundle.js")
            }
            "include Popup CSS url" in {
                document.html().contains("/engagement-platform-skin/assets/stylesheets/chat-ui-popup.css") shouldBe true
            }
            "does not include Embedded CSS url" in {
                document.html().contains("/engagement-platform-skin/assets/stylesheets/chat-ui-embedded.css") shouldBe false
            }
        }
    }
}