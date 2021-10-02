package shortestPath.view

import shortestPath.MainApp
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.stage.Stage

/** Controller for RootLayout.fxml, InformationDialog.fxml and GuideDialog.fxml */
@sfxml
class RootLayoutController() {
    var         dialogStage : Stage  = null
    var         okClicked            = false

    /** triggers when user clicks on the Information under the About in menu bar and pop up the 
     *  InformationDialog.fxml by calling showInformationDialog method in MainApp
     *
     *  @param action the action of user clicks on Ok button
     */
    def handleInformation(action: ActionEvent) {
        val okClicked = MainApp.showInformationDialog()
    }

    /** triggers when user clicks on the Guide under the Help in menu bar and pop up the 
     *  GuideDialog.fxml by calling showGuideDialog method in MainApp
     *
     *  @param action the action of user clicks on Ok button
     */
    def handleGuide(action: ActionEvent) {
        val okClicked = MainApp.showGuideDialog()
    }

    /** triggers when user clicks on the Ok button in InformationDialog.fxml or GuideDialog.fxml
     *
     *  @param action the action of user clicks on Ok button
     */
    def handleOk(action: ActionEvent) {
        dialogStage.close()
    }
}