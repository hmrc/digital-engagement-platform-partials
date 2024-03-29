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

import javax.inject.{Inject, Singleton}
import models.EncryptedNuanceData
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, ControllerComponents, Request}
import services.NuanceEncryptionService
import uk.gov.hmrc.play.http.HeaderCarrierConverter
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import utils.ParameterEncoder
import views.html.{NuanceTagElementView, NuanceView, HMRCPopupView, HMRCEmbeddedView}

import scala.concurrent.Future

@Singleton()
class WebChatPartialsController @Inject()(cc: ControllerComponents,
                                          nuanceView: NuanceView,
                                          hmrcPopupView: HMRCPopupView,
                                          hmrcEmbeddedView: HMRCEmbeddedView,
                                          nuanceTagElementView: NuanceTagElementView,
                                          nuanceEncryptionService: NuanceEncryptionService)
  extends BackendController(cc) {

  def getPartials(ids: String): Action[AnyContent] = {
    Action.async { implicit request =>
      val decryptedIdList: Seq[String] = ParameterEncoder.decodeStringList(ids)

      val mappedIds = decryptedIdList.foldLeft[Map[String, String]](
        Map.empty
      )(
        (cur, id) => cur + (id -> nuanceTagElementView(id).toString)
      ) + ("REQUIRED" -> nuanceView(encryptedNuanceData).toString) + ("HMRCPOPUPCHATSKIN" -> hmrcPopupView().toString) + ("HMRCEMBEDDEDCHATSKIN" -> hmrcEmbeddedView().toString)


      Future.successful(Ok(Json.toJson(mappedIds).toString()))
    }
  }

  private def encryptedNuanceData(implicit request: Request[AnyContent]) =
    EncryptedNuanceData.create(
      nuanceEncryptionService,
      HeaderCarrierConverter.fromRequestAndSession(request, request.session)
    )
}
