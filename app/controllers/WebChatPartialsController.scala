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

import javax.inject.{Inject, Singleton}
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import config.AppConfig
import models.EncryptedNuanceData
import views.html.{NuanceTagElementView, NuanceView}
import uk.gov.hmrc.play.HeaderCarrierConverter
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import scala.concurrent.Future

@Singleton()
class WebChatPartialsController @Inject()(appConfig: AppConfig,
                                          cc: ControllerComponents,
                                          nuanceView: NuanceView,
                                          nuanceTagElementView: NuanceTagElementView)
  extends BackendController(cc) {

  implicit val config: AppConfig = appConfig

  def load(): Action[AnyContent] = Action.async { implicit request =>

    val nuanceData = EncryptedNuanceData.create(
        appConfig.nuanceEncryptionService,
        HeaderCarrierConverter.fromHeadersAndSessionAndRequest(request.headers, Some(request.session), Some(request))
      )

    Future.successful(Ok(nuanceView(nuanceData)))
  }

  def loadTagElement(id: Option[String] = None): Action[AnyContent] = Action.async {
    Future.successful(Ok(id.fold(nuanceTagElementView())(nuanceTagElementView(_))))
  }
}
