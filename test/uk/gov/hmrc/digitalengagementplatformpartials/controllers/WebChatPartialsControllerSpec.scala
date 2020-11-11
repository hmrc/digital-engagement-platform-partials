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

package uk.gov.hmrc.digitalengagementplatformpartials.controllers

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}
import play.api.{Configuration, Environment}
import uk.gov.hmrc.digitalengagementplatformpartials.config.AppConfig
import uk.gov.hmrc.digitalengagementplatformpartials.models.EncryptedNuanceData
import uk.gov.hmrc.digitalengagementplatformpartials.services.NuanceEncryptionService
import uk.gov.hmrc.digitalengagementplatformpartials.views.html.{NuanceTagElementView, NuanceView}
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.http.logging.SessionId
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

class WebChatPartialsControllerSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {
  private val fakeRequest = FakeRequest("GET", "/")
  private val configuration = Configuration.load(Environment.simple())
  private val serviceConfig = new ServicesConfig(configuration)
  private val svcConfig = Configuration.from(Map(
    "request-body-encryption.hashing-key" -> "yNhI04vHs9<_HWbC`]20u`37=NGLGYY5:0Tg5?y`W<NoJnXWqmjcgZBec@rOxb^G",
    "request-body-encryption.key" -> "QmFyMTIzNDVCYXIxMjM0NQ==",
    "request-body-encryption.previousKeys" -> List.empty))
  private val service = NuanceEncryptionService(svcConfig)
  private val appConfig = new AppConfig(configuration, serviceConfig,service)
  private val view = new NuanceView(appConfig)
  private val tagView = new NuanceTagElementView()

  private val controller = new WebChatPartialsController(appConfig, Helpers.stubControllerComponents(),view,tagView)

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
}
