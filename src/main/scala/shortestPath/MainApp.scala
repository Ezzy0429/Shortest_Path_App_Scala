package shortestPath
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import javafx.{scene => jfxs}
import shortestPath.model.{Edge, Node, DirectedGraph, Dijkstra, SourceNode}
import scala.collection.mutable.ArrayBuffer
import scalafx.collections.ObservableBuffer
import scalafx.stage.{Stage, Modality}
import shortestPath.view.{EdgeEditDialogController, NodeEditDialogController,
                          RootLayoutController}
import java.io.File
import scalafx.scene.image.{Image, ImageView}

object MainApp extends JFXApp {
  val nodeData = new ObservableBuffer[Node]()

  val A:Node = new SourceNode("A", ArrayBuffer(), 0, false, ObservableBuffer())
  val B:Node = new Node("B", ArrayBuffer(), Int.MaxValue, false, ObservableBuffer())
  val C:Node = new Node("C", ArrayBuffer(), Int.MaxValue, false, ObservableBuffer())
  val D:Node = new Node("D", ArrayBuffer(), Int.MaxValue, false, ObservableBuffer())
  val E:Node = new Node("E", ArrayBuffer(), Int.MaxValue, false, ObservableBuffer())
  val F:Node = new Node("F", ArrayBuffer(), Int.MaxValue, false, ObservableBuffer())

  nodeData += A
  nodeData += B
  nodeData += C
  nodeData += D
  nodeData += E
  nodeData += F

  val graph = new DirectedGraph(nodeData.length, ArrayBuffer())

  for (node <- nodeData) {
      graph.addNode(node)
  }

  graph.addEdge(A, B, 10)
  graph.addEdge(A, C, 15)
  graph.addEdge(B, D, 12)
  graph.addEdge(B, F, 15)
  graph.addEdge(C, E, 10)
  graph.addEdge(D, E, 2)
  graph.addEdge(D, F, 1)
  graph.addEdge(F, E, 5)
  
  // To show the implemetation of dijkstra algorithm
  // val dijkstra: Dijkstra = new Dijkstra(graph)
  
  // dijkstra.dijkstraAlgorithm(nodeData(0))
  // println(dijkstra.printResult)

  // transform path of RootLayout.fxml to URI for resource location.
  val rootResource = getClass.getResource("/shortestPath/view/RootLayout.fxml")
  // initialize the loader object.
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  // Load root layout from fxml file.
  loader.load();
  // retrieve the root component BorderPane from the FXML 
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  // initialize stage
  stage = new PrimaryStage {
    title = "Shortest Path App"
    icons += new Image(getClass.getResourceAsStream("/Images/logo3.png"))
    scene = new Scene {
      root = roots
      val cssFile = new File("theme.css")
      stylesheets = Array(cssFile.toURI().toURL().toString())
    }
  }

  // actions for display person overview window 
  /** Called when the app is started, the FXMLLoader will get the NodeOverview.fxml and load. */
  def showNodeOverview() {
    val resource = getClass.getResource("/shortestPath/view/NodeOverview.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  } 

  /** Called when user clicks on New or Edit buttons under detailsTable, the FXMLLoader will
   *  get the EdgeEditDialog.fxml and load. The dialog will pop up to the user and wait for 
   *  user's action. 
   *
   *  @param edge Edge instance selected by user in detailsTable
   *  @return Boolean value
   */
  def showEdgeEditDialog(edge: Edge): Boolean = {
    val resource = getClass.getResourceAsStream("/shortestPath/view/EdgeEditDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[EdgeEditDialogController#Controller]

    // create new window
    val dialog = new Stage() {
      // user has to response to the dialog, if click outside it won't be closed
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      title = "Edit Edge"
      scene = new Scene {
        root = roots2
        val cssFile = new File("theme.css")
        stylesheets = Array(cssFile.toURI().toURL().toString())
      }
    }
    // initialize the dialog stage member of controller
    control.dialogStage = dialog
    // initialize the edge object in controller
    control.edge = edge
    // pop up the dialog (window object) which can show and wait
    dialog.showAndWait()
    // execute once the user click Ok
    control.okClicked
  }

  /** Called when user clicks on New or Edit buttons under nodeTable, the FXMLLoader will
   *  get the NodeEditDialog.fxml and load. The dialog will pop up to the user and wait for 
   *  user's action. 
   *
   *  @param node Node instance selected by user in nodeTable
   *  @return Boolean value
   */
  def showNodeEditDialog(node: Node): Boolean = {
    val resource = getClass.getResourceAsStream("view/NodeEditDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots3  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[NodeEditDialogController#Controller]

    val dialog = new Stage() {
      // window is set on top of other window
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      title = "Edit Node"
      scene = new Scene {
        root = roots3
        val cssFile = new File("theme.css")
        stylesheets = Array(cssFile.toURI().toURL().toString())
      }
    }
    // initialize the controller with the value created 
    control.dialogStage = dialog
    control.node = node
    dialog.showAndWait()
    control.okClicked
  } 

  /** Called when user clicks on Information under About in menu bar, the FXMLLoader will
   *  get the InformationDialog.fxml and load. The dialog will pop up to the user and wait for 
   *  user's action. 
   *
   *  @return Boolean value
   */
  def showInformationDialog(): Boolean = {
    val resource = getClass.getResourceAsStream("view/InformationDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[RootLayoutController#Controller]

    val dialog = new Stage() {
      // window is set on top of other window
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      title = "Information"
      scene = new Scene {
        root = roots2
        val cssFile = new File("theme.css")
        stylesheets = Array(cssFile.toURI().toURL().toString())
      }
    }
    // initialize the controller with the value created 
    control.dialogStage = dialog
    dialog.showAndWait()
    control.okClicked
  }

  /** Called when user clicks on Guide under Help in menu bar, the FXMLLoader will
   *  get the GuideDialog.fxml and load. The dialog will pop up to the user and wait for 
   *  user's action. 
   *
   *  @return Boolean value
   */
  def showGuideDialog(): Boolean = {
    val resource = getClass.getResourceAsStream("view/GuideDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[RootLayoutController#Controller]

    val dialog = new Stage() {
      // window is set on top of other window
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      title = "Guide"
      scene = new Scene {
        root = roots2
        val cssFile = new File("theme.css")
        stylesheets = Array(cssFile.toURI().toURL().toString())
      }
    }
    // initialize the controller with the value created 
    control.dialogStage = dialog
    dialog.showAndWait()
    control.okClicked
  }

  // called to display PersonOverview when app start
  showNodeOverview()
}
