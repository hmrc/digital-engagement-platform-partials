/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.digitalengagementplatformpartials.views

import play.api.test.FakeRequest
import uk.gov.hmrc.digitalengagementplatformpartials.views.html.Nuance
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.mvc.Cookie
import play.api.i18n.Messages
import org.jsoup.nodes.Document
import play.api.i18n.MessagesApi
import org.jsoup.Jsoup
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class NuanceSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {
    val fakeRequest = FakeRequest("GET", "/").withCookies(Cookie("mdtp", "12345"))
    val view = app.injector.instanceOf[Nuance]
    def messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]
    def messages: Messages = messagesApi.preferred(fakeRequest)

    "Nuance response" when {
        val loadedView = view()(fakeRequest);
        val queriableView : Document = Jsoup.parse(loadedView.toString())

        "successfully rendered" should {
            "include div HMRC anchored" in {
                queriableView.getElementById("HMRC_Anchored_1") should not be null
            }

            "include webchat-tag element" in {
                queriableView.getElementById("webchat-tag") should not be null
            }

            "include all encryption properties" in {
                loadedView.toString() should include ("mdtpdfSessionID")
                loadedView.toString() should include ("mdtpSessionID")
                loadedView.toString() should include ("deviceID")
            }
        }

        "rendered in pre-prod mode" should {
            "include pre-prod url in webchat-tag" in {
                val loadedView = view(true)(fakeRequest);
                val queriableView : Document = Jsoup.parse(loadedView.toString())
                val webchatTag = queriableView.getElementById("webchat-tag")

                webchatTag.toString() should include ("https://hmrc-uk-preprod.digital.nuance.com/chatskins/launch/inqChatLaunch10006719.js")
            }
        }

        "not rendered in pre-prod mode" should {
            "include pre-prod url in webchat-tag" in {
                val webchatTag = queriableView.getElementById("webchat-tag")

                webchatTag.toString() should include ("https://hmrc-uk.digital.nuance.com/chatskins/launch/inqChatLaunch10006719.js")
            }
        }
    }
}