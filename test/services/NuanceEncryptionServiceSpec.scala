/*
 * Copyright 2021 HM Revenue & Customs
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

package services

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.crypto.Crypted
import play.api.Configuration

class NuanceEncryptionServiceSpec extends AnyWordSpec with Matchers {
    "Crypto service" when {
      "encrypting plain text"  should {
          "encrypt correctly" in {
            val crypted: String = service.encryptField(fieldValue)

            crypted should startWith("ENCRYPTED-")
            crypted.stripPrefix("ENCRYPTED-") should not be fieldValue
          }
      }
      "plain text has been encrypted" should {
          "decrypt back to original text using the same secret" in {
            val crypted: String = service.encryptField(fieldValue)
            val expectedHash: String = service.hashField(fieldValue)
            val (outputHash, outputRaw) = decryptField(crypted)

            outputHash should be(expectedHash)
            outputRaw should be(fieldValue)
          }
      }
    }

    val fieldValue = "sessionf5119029a05a4b0d9d5a6b6e08e6c526"
    val configuration = Configuration.from(
        Map(
        "request-body-encryption.hashing-key" -> "yNhI04vHs9<_HWbC`]20u`37=NGLGYY5:0Tg5?y`W<NoJnXWqmjcgZBec@rOxb^G",
        "request-body-encryption.key" -> "QmFyMTIzNDVCYXIxMjM0NQ==",
        "request-body-encryption.previousKeys" -> List.empty
        )
    )
    val service = new NuanceEncryptionService(configuration)

    def decryptField(cipherText: String): (String, String) = {
      val stripped: String = cipherText.stripPrefix("ENCRYPTED-")
      val plainText: String = service.crypto.decrypt(Crypted(stripped)).value

      plainText.split("-").toList match {
        case ::(hashed, ::(raw, Nil)) => (hashed, raw)
        case _ => throw new RuntimeException(s"Unable to decrypt cipherText: $cipherText")
      }
    }
}