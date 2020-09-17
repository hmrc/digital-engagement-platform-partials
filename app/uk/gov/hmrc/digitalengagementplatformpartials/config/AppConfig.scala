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

package uk.gov.hmrc.digitalengagementplatformpartials.config

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig
import uk.gov.hmrc.digitalengagementplatformpartials.services.NuanceEncryptionService

@Singleton
class AppConfig @Inject()(config: Configuration, servicesConfig: ServicesConfig, val nuanceEncryptionService: NuanceEncryptionService) {

  val authBaseUrl: String = servicesConfig.baseUrl("auth")

  val auditingEnabled: Boolean = config.get[Boolean]("auditing.enabled")
  val graphiteHost: String     = config.get[String]("microservice.metrics.graphite.host")
  val preProdMode: Boolean = config.get[Boolean](s"pre-prod.mode")
  val nuanceUrl: String =
    "https://hmrc-uk.digital.nuance.com/chatskins/launch/inqChatLaunch10006719.js"
  val nuancePreProdUrl: String =
    "https://hmrc-uk-preprod.digital.nuance.com/chatskins/launch/inqChatLaunch10006719.js"
}
