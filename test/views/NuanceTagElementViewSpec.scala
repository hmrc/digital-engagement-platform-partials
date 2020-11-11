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

package views

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import views.html.NuanceTagElementView

class NuanceTagElementViewSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {
    private val view = app.injector.instanceOf[NuanceTagElementView]

    "Nuance tag element" when {
        "successfully rendered" should {
            "include div tag element with default ID" in {
                val loadedView = view();
                val document : Document = Jsoup.parse(loadedView.toString())

                document.getElementById("HMRC_Fixed_1") should not be null
            }

            "include div tag element with custom ID" in {
                val loadedView = view("myTest");
                val document : Document = Jsoup.parse(loadedView.toString())

                document.getElementById("HMRC_Fixed_1") shouldBe null
                document.getElementById("myTest") should not be null
            }
        }
    }
}