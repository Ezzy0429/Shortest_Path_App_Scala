package shortestPath.view

import shortestPath.model.Node
import shortestPath.MainApp
import scalafx.scene.control.{TextField, TableColumn, Label, Alert}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.Includes._
import scalafx.event.ActionEvent

/** Controller for NodeEditDialog.fxml
 *  
 *  @param nameField TextField which shows the selected node's name 
 */
@sfxml
class NodeEditDialogController (

    private val  nameField : TextField

){
  var         dialogStage : Stage  = null
  private var _node     : Node = null
  var         okClicked            = false
  
  /** getter for node */
  def node = _node

  /** setter for node
    *
    *  @param x a Node instance
    */ 
  def node_=(x : Node) {
        _node = x

        nameField.text = _node.name.value
        
  }

  /** triggers when user clicks the Ok button, checks on the input's validity, if validation
   *  is successful set the selected node's name with the user input name and close the dialog
   *
   *  @param action the action of user clicks on Ok button
   */
  def handleOk(action :ActionEvent){

     if (isInputValid()) {
        _node.name.value = nameField.text()

        okClicked = true;
        dialogStage.close()
    }
  }

  /** triggers when user clicks the Cancel button, close the dialog
   *
   *  @param action the action of user clicks on Cancel button
   */
  def handleCancel(action :ActionEvent) {
        dialogStage.close();
  }

  /** checking if the input is null 
   *  
   *  @param x a String instance
   */
  def nullChecking (x : String) = x == null || x.length == 0

  /** Checks the validity of input on nameField, if validation is successful returns true, 
   *  else pop up a invalid fields dialog to user and return false
   *
   *  @return Boolean value
   */
  private def isInputValid() : Boolean = {
    var errorMessage = ""

    if (nullChecking(nameField.text.value))
      errorMessage += "No valid name!\n"

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
