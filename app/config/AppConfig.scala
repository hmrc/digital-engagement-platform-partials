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

package config

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig
import services.NuanceEncryptionService

@Singleton
class AppConfig @Inject()(config: Configuration, servicesConfig: ServicesConfig, val nuanceEncryptionService: NuanceEncryptionService) {
  private val preProdMode: Boolean = config.get[Boolean]("pre-prod.mode")
  val nuanceUrl: String = if (preProdMode) {
    config.get[String]("urls.pre-production.nuance")
  } else {
    config.get[String]("urls.production.nuance")
  }
}
