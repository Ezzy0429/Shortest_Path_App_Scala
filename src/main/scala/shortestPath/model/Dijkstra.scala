package shortestPath.model

import scala.collection.mutable.ArrayBuffer
import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty}


/** a class created to perform dijkstra algorithm on the given graph
 *  
 *  @constructor create a new dijkstra with graph of Graph type
 *  @param graph a graph of Graph type which contains all the nodes linked with edge
 */
class Dijkstra (val graph: Graph) {
    var startNodeIndex: Int = 0

    /** calculate and compare the cost from source of each node and gets the node which has 
     *  the minimum cost
     *  
     *  @return a node of Node type
     */

    def getMinCostNode: Node = {

        val nodes: ArrayBuffer[Node] = graph.nodes
        var storedNode: Node = nodes(startNodeIndex)
        var storedCost:Int = Int.MaxValue
        
        for (index <- 0 to  graph.totalNodes-1) {
            val currentNodeCost = nodes(index).costFromSource

            if (nodes(index).visited != true && currentNodeCost < storedCost) {
                storedCost = currentNodeCost
                storedNode = nodes(index)
            }
        }
        return storedNode
    }
    
    /** perform dijkstra algorithm with the startNode to calculate the shortest path from the
     *  startNode to every node in the graph
     *
     *  @param startNode the source node
     *  @return a Graph instance with every node having the calculated costFromSource
     */
    def dijkstraAlgorithm (startNode: Node): Graph = {

        val nodes: ArrayBuffer[Node] = graph.nodes

        val startNodeIndex: Int = findNode(nodes, startNode)
        nodes(startNodeIndex).costFromSource = 0

        var nextNode: Node = nodes(startNodeIndex)
        var currentNodeEdges: ArrayBuffer[Edge] = nextNode.adjacencyArray

        for (index <- 0 to graph.totalNodes-1) {

            currentNodeEdges = nextNode.adjacencyArray

            if (currentNodeEdges.size == 0) {
                println("Current node " + nextNode.name.value + " doesn't have directed edge")
            }

            for (joinedEdge <- 0 to currentNodeEdges.size-1) {
                val neighbourNode = currentNodeEdges(joinedEdge).adjacentNode

                if (neighbourNode.visited != true) {
                    val temp1: Int = nextNode.costFromSource
                    val temp2: Int = currentNodeEdges(joinedEdge).costI

                    val totalCost = temp1 + temp2

                    if (totalCost < neighbourNode.costFromSource) {
                        neighbourNode.costFromSource = totalCost
                    }
                }
            }

            nextNode.visited = true

            if (currentNodeEdges.size != 0) {
                nextNode = getMinCostNode
            }
            else {
                println("\nAll the nodes have been visited")
            }
        }
        return graph
    }

    /** finds the index of the given node in nodes by checking if the node passed in is an instance of SourceNode
     *
     *  @param nodes a mutable ArrayBuffer which contains all the node objects in the graph
     *  @param node Node instance
     */

    def findNode(nodes: ArrayBuffer[Node], node: Node): Int = {
        var nodeIndex = -1

        for (index <- 0 to graph.totalNodes-1) {
            if ( nodes(index).isInstanceOf[SourceNode]) {
                nodeIndex = index
            }
        }
        return nodeIndex
    }

    /** prints the result of dijkstra algorithm where the minimum cost of each node from source
     *  is calculated
     *
     *  @return a StringProperty instance
     */

    def printResult: String = {
        val nodes: ArrayBuffer[Node] = graph.nodes
        val infinity: Int = Int.MaxValue
        var totalString: String = ""

        for(index <- 0 to graph.totalNodes-1) {
            var result: String = ""
            var cost:String = ""
            if (nodes(index).costFromSource == infinity) {
                cost = "infinity"
            } else {
                cost = (nodes(index).costFromSource).toString
            }
            
            result += "\nMinimum cost from node "
            result += nodes(startNodeIndex).name.value 
            result += " to node " 
            result += nodes(index).name.value
            result += " is "
            result += cost

            totalString += result
        }
        return totalString
    }
}