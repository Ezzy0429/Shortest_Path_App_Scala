package shortestPath.view

import shortestPath.model.{Edge, Node, Graph}
import shortestPath.MainApp
import scalafx.scene.control.{TableView, TableColumn}
import scalafxml.core.macros.sfxml
import scalafx.beans.property.{StringProperty, ObjectProperty} 
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.collections.ObservableBuffer
import scala.collection.mutable.ArrayBuffer

/** Controller for NodeOverview.fxml
 *  
 *  @param nodeTable TableView which contains Node instances 
 *  @param nameColumn TableColumn in nodeTable which shows the node's name in each row
 *  @param detailsTable TableView which contains the Edge instances of selected node
 *  @param adjacentNodeColumn TableColumn in detailsTable which shows the adjacentNode's name
 *  @param costColumn TableColumn in detailsTable which shows the edge's cost
 */
@sfxml
class NodeOverviewController(
  private val nodeTable: TableView[Node],
  private val nameColumn: TableColumn[Node, String],
  private val detailsTable: TableView[Edge],
  private val adjacentNodeColumn: TableColumn[Edge, String],
  private val costColumn: TableColumn[Edge, Int]
  ) {

  nodeTable.items = MainApp.nodeData
  nameColumn.cellValueFactory = {_.value.name}

  /** shows the node's details with match case, if the node argument passed in match Some case
   *  the details of Edge instances in edgeData of given node will be displayed in 
   *  adjacentNodeColumn and costColumn. else, empty ObservableBuffer will assign to items in
   *  detailsTable
   *
   *  @param node optional values of Node instance
   */

  private def showNodeDetails (node: Option[Node]) = {
    // val edgeData = new ObservableBuffer[Edge]()
      node match {
          case Some(node) =>
          detailsTable.items = node.edgeData
          adjacentNodeColumn.cellValueFactory = {_.value.adjacentNode.name}
          costColumn.cellValueFactory = {_.value.cost}

          case None =>
          detailsTable.items = ObservableBuffer()
          adjacentNodeColumn.cellValueFactory = {_.value.adjacentNode.name}
          costColumn.cellValueFactory = {_.value.cost}

      }
  }

  showNodeDetails(None)

  /** When user selects on the node instance in nodeTable, newValue will pass to 
      showNodeDetails to display the details of the selected node */
  nodeTable.selectionModel().selectedItem.onChange(
    (_, _, newValue) => showNodeDetails(Option(newValue))
  )

  /** Called when the user clicks on the delete button in nodeTable, if an item is selected 
   *  the item will be removed, else a dialog will pop up to alert the user that no item is 
   *  selected
   *
   *  @param action the action of user clicks on Delete button under nodeTable
   */
  def handleDeleteNode(action : ActionEvent) = {
  val selectedIndex = nodeTable.selectionModel().selectedIndex.value
  if (selectedIndex >= 0) {
      nodeTable.items().remove(selectedIndex);
  } else {
      // Nothing selected. Then create a alert warning to the user
      // alert is a new Alert window
      val alert = new Alert(AlertType.Warning){
        // initialize the owner who own the alert window
        initOwner(MainApp.stage)
        title       = "No Selection"
        headerText  = "No Node Selected"
        contentText = "Please select a node in the table."
      }.showAndWait()
    }
  }

  /** Called when the user clicks on the New button in nodeTable,  the NodeEditDialog will pop 
   *  up and user can input the details of the new node. If ok is clicked the node instance 
   *  will be added into the nodeData
   *
   *  @param action the action of user clicks on New button under nodeTable
   */
  def handleNewNode(action : ActionEvent) = {
    val node = new Node("", ArrayBuffer(), 0, false, ObservableBuffer())
    val okClicked = MainApp.showNodeEditDialog(node);
        if (okClicked) {
            MainApp.nodeData += node
        }
  }

  /** Called when the user clicks on the Edit button in nodeTable, if user selected an item
   *  in nodeTable the NodeEditDialog will pop up and user can edit the details of the node.
   *  Else, a warning dialog will pop up to alert the user to select an item in nodeTable. 
   *
   *  @param action the action of user clicks on Edit button under nodeTable
   */
  def handleEditNode(action : ActionEvent) = {
    val selectedNode = nodeTable.selectionModel().selectedItem.value
    if (selectedNode != null) {
        val okClicked = MainApp.showNodeEditDialog(selectedNode)

        if (okClicked) showNodeDetails(Some(selectedNode))

    } else {
        // Nothing selected.
        val alert = new Alert(Alert.AlertType.Warning){
          initOwner(MainApp.stage)
          title       = "No Selection"
          headerText  = "No Node Selected"
          contentText = "Please select a node in the table."
        }.showAndWait()
    }
  } 

  /** Called when the user clicks on the delete button in detailsTable, if an item is selected 
   *  the item will be removed, else a dialog will pop up to alert the user that no item is 
   *  selected
   *
   *  @param action the action of user clicks on Delete button under detailsTable
   */
  def handleDeleteEdge(action : ActionEvent) = {
  val selectedIndex = detailsTable.selectionModel().selectedIndex.value
  if (selectedIndex >= 0) {
      detailsTable.items().remove(selectedIndex);
  } else {
      // Nothing selected. Then create a alert warning to the user
      // alert is a new Alert window
      val alert = new Alert(AlertType.Warning){
        // initialize the owner who own the alert window
        initOwner(MainApp.stage)
        title       = "No Selection"
        headerText  = "No Edge Selected"
        contentText = "Please select a edge in the table."
      }.showAndWait()
    }
  }

  /** Called when the user clicks on the New button in detailsTable, if user selected an item
   *  in nodeTable the EdgeEditDialog will pop up and user can input the details of the new 
   *  edge. Else, a warning dialog will pop up to alert the user to select an item in nodeTable 
   *
   *  @param action the action of user clicks on New button under detailsTable
   */
  def handleNewEdge(action : ActionEvent) = {
    // val graph = new Graph(6, ArrayBuffer())
    val selectedNode = nodeTable.selectionModel().selectedItem.value
    if (selectedNode != null) {
      val edge = new Edge(new Node("", ArrayBuffer(), 0, false, ObservableBuffer()), new Node("", 
      ArrayBuffer(), 0, false, ObservableBuffer()), 0)
      val okClicked = MainApp.showEdgeEditDialog(edge);
        if (okClicked) {
            selectedNode.edgeData += edge
        }
    } else {
      val alert = new Alert(Alert.AlertType.Warning){
        initOwner(MainApp.stage)
        title       = "No Selection"
        headerText  = "No Node Selected"
        contentText = "Please select an node in the table."
      }.showAndWait()
    }
  } 

  /** Called when the user clicks on the Edit button in detailsTable, if user selected an item
   *  in detailsTable the EdgeEditDialog will pop up and user can edit the details of the edge.
   *  Else, a warning dialog will pop up to alert the user to select an item in detailsTable 
   *
   *  @param action the action of user clicks on Edit button under detailsTable
   */
  def handleEditEdge(action: ActionEvent) = {
    val selectedNode = nodeTable.selectionModel().selectedItem.value
    val selectedEdge = detailsTable.selectionModel().selectedItem.value

    if (selectedEdge != null) {
      val okClicked = MainApp.showEdgeEditDialog(selectedEdge)

      if (okClicked) showNodeDetails(Some(selectedNode))
    
    } else {
      // Nothing selected.
      val alert = new Alert(Alert.AlertType.Warning){
        initOwner(MainApp.stage)
        title       = "No Selection"
        headerText  = "No Edge Selected"
        contentText = "Please select an edge in the table."
      }.showAndWait()
    }
  }
}


