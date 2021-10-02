package shortestPath.model

import scala.collection.mutable.ArrayBuffer

/** an abstract class called Graph which will be further implemented in a concrete class
 *  
 *  @constructor create a new graph with totalNodes in Int and an ArrayBuffer, nodes
 *  @param totalNodes the total no. of nodes in graph
 *  @param nodes a mutable ArrayBuffer which contains all the node objects in the graph
 */
abstract class Graph (var totalNodes: Int, var nodes: ArrayBuffer[Node]) {
    def addNode(node: Node) 

    def addEdge(node: Node, adjacentNode: Node, cost: Int) 
}

/** a directed graph which contains nodes linked with directed edge to perform dijkstra algorithm
 *  
 *  @constructor create a new graph with totalNodes in Int and an ArrayBuffer, nodes
 *  @param totalNodes the total no. of nodes in directed graph
 *  @param nodes a mutable ArrayBuffer which contains all the node objects in the directed graph
 */
class DirectedGraph (DG_TotalNodes: Int, DG_Nodes: ArrayBuffer[Node]) extends Graph (DG_TotalNodes, DG_Nodes) {

    /** adds node into nodes
     *  
     *  @param node a node of Node type 
     */
    
    def addNode(node: Node) {
        nodes.append(node)
    }

    /** creates edge and append the edge into the given node's adjacencyArray and edgeData
     *  
     *  @param node a node of Node type 
     *  @param adjacentNode the node which the first parameter node is directed to
     *  @param cost the cost of edge in distance
     */
    def addEdge(node: Node, adjacentNode: Node, cost: Int) {
        val edge: Edge = new Edge(node, adjacentNode, cost)
        node.adjacencyArray.append(edge)
        node.edgeData += edge
    }
}