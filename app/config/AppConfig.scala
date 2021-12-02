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

package config

import com.google.inject.ImplementedBy
import javax.inject.{Inject, Singleton}
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig


@ImplementedBy(classOf[AppConfigImpl])
trait AppConfig {
  val preProdMode: Boolean
  val nuanceUrl: String
  val depSkinBaseUrl: String
  val hmrcSkinJSUrl: String
  val hmrcSkinEmbeddedCSSUrl: String
}

class AppConfigImpl @Inject()(config: ServicesConfig) extends AppConfig {
  override val preProdMode: Boolean = config.getBoolean("pre-prod.mode")
  override val depSkinBaseUrl: String = config.baseUrl("digital-engagement-platform-skin")

  override val nuanceUrl: String = if (preProdMode) {
    config.getString("urls.pre-production.nuance")
  } else {
    config.getString("urls.production.nuance")
  }

  override val hmrcSkinJSUrl: String = s"$depSkinBaseUrl/engagement-platform-skin/assets/javascripts/hmrcChatSkinBundle.js"
  override val hmrcSkinEmbeddedCSSUrl: String = s"$depSkinBaseUrl/engagement-platform-skin/assets/stylesheets/chat-ui-embedded.css"
}
