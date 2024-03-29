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

package controllers

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.test.FakeRequest
import play.api.test.Helpers._
import utils.ParameterEncoder
import views.html.NuanceTagElementView

class WebChatPartialsControllerSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {
  private val fakeRequest = FakeRequest("GET", "/")

  private val controller = app.injector.instanceOf[WebChatPartialsController]
  private val nuanceTagElementView = app.injector.instanceOf[NuanceTagElementView]

  "GET engagement-platform-partials/partials/..." should {
    "return the partials for the requested ids" in {
      val encodedIds = ParameterEncoder.encodeStringList(Seq("tag1", "tag2", "tag3"))
      val result = controller.getPartials(encodedIds)(fakeRequest)

      status(result) shouldBe Status.OK

      val resultMap = contentAsJson(result).as[Map[String, String]]

      resultMap("tag1") shouldBe nuanceTagElementView("tag1").toString
      resultMap("tag2") shouldBe nuanceTagElementView("tag2").toString
      resultMap("tag3") shouldBe nuanceTagElementView("tag3").toString
      resultMap("REQUIRED").contains("nuanceData") shouldBe true
      resultMap("REQUIRED").contains("mdtpdfSessionID") shouldBe true
      resultMap("REQUIRED").contains("mdtpSessionID") shouldBe true
      resultMap("REQUIRED").contains("deviceID") shouldBe true
      resultMap("HMRCPOPUPCHATSKIN").contains("hmrc-webchat-tag") shouldBe true
      resultMap("HMRCEMBEDDEDCHATSKIN").contains("hmrc-webchat-tag") shouldBe true
    }
  }
}
