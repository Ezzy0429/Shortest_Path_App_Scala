package shortestPath.model

import scala.collection.mutable.ArrayBuffer
import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty}
import scalafx.collections.ObservableBuffer

/** A node in graph.
 *
 *  @constructor create a new node with a name, adjacencyArray, costFromSouce, visited, edgeData.
 *  @param name the node's name
 *  @param adjacencyArray the node's adjacencyArray which contains edge 
 *  @param costFromSource the cost of node from source in distance
 *  @param visited the status of node whether it is visited (return true) or not visited (return false)
 *  @param edgeData the node's edgeData which contains details of edge in ObservableBuffer
 */
class Node (nameT: String, var adjacencyArray: ArrayBuffer[Edge], var costFromSource: Int, 
var visited: Boolean, var edgeData: ObservableBuffer[Edge]) {
    var name: StringProperty = new StringProperty(nameT)


}

/** A source node used to indicate the starting point in the graph for Dijkstra's algorithm implementation
 * 
 *  @constructor create a new source node with a name, adjacencyArray, costFromSouce = 0, visited, edgeData.
 *  @param source_nameT the source node's name
 *  @param source_adjacencyArray the source node's adjacencyArray which contains edge 
 *  @param source_costFromSource the cost of source node which is 0
 *  @param source_visited the status of node whether it is visited (return true) or not visited (return false)
 *  @param source_edgeData the node's edgeData which contains details of edge in ObservableBuffer
 */
class SourceNode (source_nameT: String, source_adjacencyArray: ArrayBuffer[Edge], 
    source_costFromSource: Int = 0, source_visited: Boolean, source_edgeData: ObservableBuffer[Edge]) 
    extends Node(source_nameT, source_adjacencyArray, source_costFromSource, source_visited, source_edgeData) {

}




