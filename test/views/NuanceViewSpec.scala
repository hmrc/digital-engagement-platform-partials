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
import models.EncryptedNuanceData
import views.html.NuanceView

class NuanceViewSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {
    private val view = app.injector.instanceOf[NuanceView]
    private val encryptedNuanceData = EncryptedNuanceData("nuanceSessionId", "mtdpSessionId", "deviceId")

    "Nuance response" when {
        val loadedView = view(encryptedNuanceData)
        val document : Document = Jsoup.parse(loadedView.toString())

        "successfully rendered" should {
            "include webchat-tag element" in {
                document.getElementById("webchat-tag") should not be null
            }

            "include all encryption properties" in {
                loadedView.toString() should include ("mdtpdfSessionID")
                loadedView.toString() should include ("mdtpSessionID")
                loadedView.toString() should include ("deviceID")
            }
        }

        "rendered in pre-prod mode" should {
            "include pre-prod url in webchat-tag" in {
                document.getElementById("webchat-tag").toString should include ("https://hmrc-uk-preprod.lle.digital.nuance.com/chatskins/launch/inqChatLaunch10006719.js")
            }
        }
    }
}