package shortestPath.view

import shortestPath.model.{Edge, Node}
import shortestPath.MainApp
import scalafx.scene.control.{TextField, TableColumn, Label, Alert}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.Includes._
import scalafx.event.ActionEvent


/** Controller for EdgeEditDialog.fxml
 *  
 *  @param adjacentNodeField TextField which shows the adjacentNode's name of selected edge
 *  @param costField TextField which shows the cost of selected edge
 */
@sfxml
class EdgeEditDialogController (

    private val adjacentNodeField : TextField,
    private val costField : TextField

){
    var dialogStage: Stage = null
    private var _edge: Edge = null
    var okClicked = false

    /** getter for edge */
    def edge = _edge

    /** setter for edge
     *
     *  @param x an Edge instance
     */ 
    def edge_=(x: Edge) {
        //set the node using the passed in argument x
        _edge = x

        //set all the text field corresponding to the edited value
        adjacentNodeField.text = _edge.adjacentNode.name.value
        costField.text = _edge.cost.value.toString
    }

    /** triggers when user clicks the Ok button, checks on the input's validity, if validation
     *  is successful set the edge's properties with the data input by user and close the dialog
     *
     *  @param action the action of user clicks on Ok button
     */
    def handleOk (action: ActionEvent) {
        if (isInputValid()) {
            _edge.adjacentNode.name.value = adjacentNodeField.text()
            _edge.cost.value = costField.text().toInt

            okClicked = true
            dialogStage.close()
        }
    }

    /** triggers when user clicks the Cancel button, close the dialog
     *
     *  @param action the action of user clicks on Cancel button
     */
    def handleCancel(action: ActionEvent) {
        dialogStage.close()
    }

    /** checking if the input is null 
     *  
     *  @param x a String instance
     */
    def nullChecking (x : String) = x == null || x.length == 0

    def checkValidStatus (x: String) = x != "Complete" && x != "Incomplete"


    /** Checks the validity of input on adjacentNodeField and costField, if validation is 
     *  successful returns true, else pop up a invalid fields dialog to user and return false
     *
     *  @return Boolean value
     */
    private def isInputValid() : Boolean = {
        var errorMessage = ""

        if (nullChecking(adjacentNodeField.text.value))
        errorMessage += "No valid adjacent node!\n"
        else {
        try {
            Integer.parseInt(costField.getText());
        } catch {
            case e : NumberFormatException =>
                errorMessage += "No valid cost (must be an integer)!\n"
        }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            val alert = new Alert(Alert.AlertType.Error){
            initOwner(dialogStage)
            title = "Invalid Fields"
            headerText = "Please correct invalid fields"
            contentText = errorMessage
            }.showAndWait()

            return false;
        }
    }
}