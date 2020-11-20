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
import models.EncryptedNuanceData
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import services.NuanceEncryptionService
import uk.gov.hmrc.play.HeaderCarrierConverter
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import utils.ParameterEncoder
import views.html.{NuanceTagElementView, NuanceView}

import scala.concurrent.Future

@Singleton()
class WebChatPartialsController @Inject()(cc: ControllerComponents,
                                          nuanceView: NuanceView,
                                          nuanceTagElementView: NuanceTagElementView,
                                          nuanceEncryptionService: NuanceEncryptionService)
  extends BackendController(cc) {

  def load(): Action[AnyContent] = Action.async { implicit request =>

    val nuanceData = EncryptedNuanceData.create(
        nuanceEncryptionService,
        HeaderCarrierConverter.fromHeadersAndSessionAndRequest(request.headers, Some(request.session), Some(request))
      )

    Future.successful(Ok(nuanceView(nuanceData)))
  }

  def loadTagElement(id: Option[String] = None): Action[AnyContent] = Action.async {
    Future.successful(Ok(id.fold(nuanceTagElementView())(nuanceTagElementView(_))))
  }

  def getPartials(ids: String): Action[AnyContent] = {
    Action.async {
      val decryptedIdList: Seq[String] = ParameterEncoder.decodeStringList(ids)

      val mappedIds = decryptedIdList.groupBy(identity).map {
        case (id, _) => id -> nuanceTagElementView(id).toString()
      }

      val result = Json.toJson(mappedIds)
      Future.successful(Ok(result.toString()))
    }
  }
}
