/*
 * Copyright 2022 HM Revenue & Customs
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

package models

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.Configuration
import services.NuanceEncryptionService
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.http.SessionId

case class EncryptedNuanceDataSpec() extends AnyWordSpec with Matchers {

  "Encrypted Nuance Data" when {
    "Creating a new Encrypted Nuance Data Object" should {
      val configuration = Configuration.from(Map(
            "request-body-encryption.hashing-key" -> "yNhI04vHs9<_HWbC`]20u`37=NGLGYY5:0Tg5?y`W<NoJnXWqmjcgZBec@rOxb^G",
            "request-body-encryption.key" -> "QmFyMTIzNDVCYXIxMjM0NQ==",
            "request-body-encryption.previousKeys" -> List.empty))
      val service = NuanceEncryptionService(configuration)
      val encryptedNuanceData = EncryptedNuanceData.create(
        service,
        HeaderCarrier(sessionId = Some(SessionId("x")), deviceID = Some("y")))


      "allow retrieval of Nuance JSON fields" in {
        encryptedNuanceData.mdtpSessionId should startWith("ENCRYPTED-")
        encryptedNuanceData.deviceId should startWith("ENCRYPTED-")
      }

      "produce different cipher text on each encryption" in {
        val encryptedNuanceData2 = EncryptedNuanceData.create(service,HeaderCarrier(sessionId = Some(SessionId("x")), deviceID = Some("y")))

        encryptedNuanceData2.deviceId should startWith("ENCRYPTED-")
        encryptedNuanceData.deviceId should not be encryptedNuanceData2.deviceId
      }

      "not change if the plain text does not change and not contain non-alpha characters" in {
        val encryptedNuanceData2 = EncryptedNuanceData.create(service,HeaderCarrier(sessionId = Some(SessionId("x")), deviceID = Some("y")))

        encryptedNuanceData.nuanceSessionId should be (encryptedNuanceData2.nuanceSessionId)
        encryptedNuanceData.nuanceSessionId should fullyMatch regex "[0-9A-Za-z]+"
        encryptedNuanceData2.nuanceSessionId should fullyMatch regex "[0-9A-Za-z]+"
      }
    }
  }
}