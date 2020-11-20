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

package controllers

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import utils.ParameterEncoder
import views.html.{NuanceTagElementView, NuanceView}

class WebChatPartialsControllerSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {
  private val fakeRequest = FakeRequest("GET", "/")

  private val controller = app.injector.instanceOf[WebChatPartialsController]
  private val nuanceTagElementView = app.injector.instanceOf[NuanceTagElementView]
  private val nuanceRequiredElementsView = app.injector.instanceOf[NuanceView]

  "GET engagement-platform-partials/webchat" should {
    "return 200" in {
      val result = controller.load()(fakeRequest)
      status(result) shouldBe Status.OK
    }
  }

  "GET engagement-platform-partials/tagElement" should {
    "return 200 when there is no id" in {
      val result = controller.loadTagElement(None)(fakeRequest)
      status(result) shouldBe Status.OK
    }

    "return 200 if a custom id has been specified" in {
      val result = controller.loadTagElement(Some("test"))(fakeRequest)
      status(result) shouldBe Status.OK
    }
  }

  "GET engagement-platform-partials/partials/..." should {
    "return the partials for the requested ids" in {
      val encodedIds = ParameterEncoder.encodeStringList(Seq("tag1", "tag2", "tag3"))
      val result = controller.getPartials(encodedIds)(fakeRequest)

      val expectedJson = Json.obj(
        "tag1" -> nuanceTagElementView("tag1").toString(),
        "tag2" -> nuanceTagElementView("tag2").toString(),
        "tag3" -> nuanceTagElementView("tag3").toString(),
        "REQUIRED" -> nuanceRequiredElementsView.toString
      )

      contentAsJson(result) shouldBe expectedJson
      status(result) shouldBe Status.OK
    }
  }
}
