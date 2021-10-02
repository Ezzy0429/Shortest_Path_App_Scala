package shortestPath.model

import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty}

/** An edge to link one node to another node
 *  
 *  @constructor create a new edge with a node of Node type and an adjacentNode of Node type
 *               and cost 
 *  @param node the node which is having the directed edge
 *  @param adjacentNode the node which the first parameter node is directed to
 *  @param cost the cost of edge in distance
 */

case class Edge (nodeT: Node, var adjacentNode: Node, costI: Int) {
    var node = ObjectProperty[Node](nodeT)
    // var adjacentNode = ObjectProperty[Node](adjacentNodeT)
    var cost = ObjectProperty[Int](costI)

}
