/**
 * Graph.java
 * Undirected, unweighted simple graph data type
 * <p>
 *  Notes:
 *  <ul>
 *   <li> Parallel edges are not allowed
 *   <li> Self loops are allowed
 *  </ul>
 *  <p>
 *  This Graph class was adapted from 
 *  <a href="http://www.cs.princeton.edu/introcs/45graph/Graph.java">Graph.java</a> by 
 *  by Robert Sedgewick and Kevin Wayne
 *  Valorie Barr 
 */

import java.util.*;

public class Graph {
	private static HashMap<Vertex, HashSet<Vertex>> myAdjList;
	private static HashMap<String, Vertex> myVertices;
	private static final HashSet<Vertex> EMPTY_SET = new HashSet<Vertex>();
	private int myNumVertices;
	private int myNumEdges;

	/**
	 * Construct empty Graph
	 */
	public Graph() {
		myAdjList = new HashMap<Vertex, HashSet<Vertex>>();
		myVertices = new HashMap<String, Vertex>();
		myNumVertices = myNumEdges = 0;
	}

	/**
	 * Add a new vertex name with no neighbors (if vertex does not yet exist)
	 * 
	 * @param name vertex to be added
	 */
	public Vertex addVertex(String name) {
		Vertex v;
		v = myVertices.get(name);
		if (v == null) {
			v = new Vertex(name);
			myVertices.put(name, v);
			myAdjList.put(v, new HashSet<Vertex>());
			myNumVertices += 1;
		}
		return v;
	}

	/**
	 * Returns the Vertex matching v
	 * @param name a String name of a Vertex that may be in
	 * this Graph
	 * @return the Vertex with a name that matches v or null
	 * if no such Vertex exists in this Graph
	 */
	public Vertex getVertex(String name) {
		return myVertices.get(name);
	}

	/**
	 * Returns true iff v is in this Graph, false otherwise
	 * @param name a String name of a Vertex that may be in
	 * this Graph
	 * @return true iff v is in this Graph
	 */
	public boolean hasVertex(String name) {
		return myVertices.containsKey(name);
	}

	/**
	 * Is from-to, an edge in this Graph. The graph is 
	 * undirected so the order of from and to does not
	 * matter.
	 * @param from the name of the first Vertex
	 * @param to the name of the second Vertex
	 * @return true iff from-to exists in this Graph
	 */
	public boolean hasEdge(String from, String to) {

		if (!hasVertex(from) || !hasVertex(to))
			return false;
		return myAdjList.get(myVertices.get(from)).contains(myVertices.get(to));
	}
	
	/**
	 * Add to to from's set of neighbors, and add from to to's
	 * set of neighbors. Does not add an edge if another edge
	 * already exists
	 * @param from the name of the first Vertex
	 * @param to the name of the second Vertex
	 */
	public void addEdge(String from, String to) {
		Vertex v, w;
		if (hasEdge(from, to))
			return;
		myNumEdges += 1;
		if ((v = getVertex(from)) == null)
			v = addVertex(from);
		if ((w = getVertex(to)) == null)
			w = addVertex(to);
		myAdjList.get(v).add(w);
		myAdjList.get(w).add(v);
	}

	/**
	 * Return an iterator over the neighbors of Vertex named v
	 * @param name the String name of a Vertex
	 * @return an Iterator over Vertices that are adjacent
	 * to the Vertex named v, empty set if v is not in graph
	 */
	public Iterable<Vertex> adjacentTo(String name) {
		if (!hasVertex(name))
			return EMPTY_SET;
		return myAdjList.get(getVertex(name));
	}

	/**
	 * Return an iterator over the neighbors of Vertex v
	 * @param v the Vertex
	 * @return an Iterator over Vertices that are adjacent
	 * to the Vertex v, empty set if v is not in graph
	 */
	public Iterable<Vertex> adjacentTo(Vertex v) {
		if (!myAdjList.containsKey(v))
			return EMPTY_SET;
		return myAdjList.get(v);
	}

	/**
	 * Returns an Iterator over all Vertices in this Graph
	 * @return an Iterator over all Vertices in this Graph
	 */
	public Iterable<Vertex> getVertices() {
		return myVertices.values();
	}

	public int numVertices()
	{
		return myNumVertices;
	}
	
	public int numEdges()
	{
		return myNumEdges;
	}
	
	/**
	 * Returns adjacency-list representation of graph
	 */
	public String toString() {
		String s = "";
		for (Vertex v : myVertices.values()) {
			s += v + ": ";
			for (Vertex w : myAdjList.get(v)) {
				s += w + " ";
			}
			s += "\n";
		}
		return s;
	}
	
	/**
	 * given two vertices and returns an ArrayList containing the vertices v and u
	 * that comprise the shortest path.
	 * Written by Pooja Kundaje
	 */
	public static ArrayList shortestPath (Graph G, Vertex v, Vertex u) {
		//creates an arraylist of vertices 
		ArrayList<Vertex> array= new ArrayList<Vertex>();
		//since its the first node being tested, the predecessor is null and the color is set to gray
		v.setGray();
		v.setPred(null);
		//creates a queue and adds the vertex to the queue
		Queue<Vertex> queue= new LinkedList<Vertex>();
		queue.add(v);
		
		//while the queue isn't empty
		while(!queue.isEmpty()) {
			//dequeue the element in the queue and save it as vertex s 
			Vertex s =queue.remove();
			
			//for each vertex that is adjacent to s 
			for(Vertex w: G.myAdjList.get(s)) {
				//if the vertex hasn't been visited 
				if(w.getColor()=="white") {
					//set it's color to gray and set it's predecessor to s 
					w.setGray();
					w.setPred(s.toString());
					//add it to the queue so that it's adjacent vertices can be tested
					queue.add(w);
				}
				
			}
			//if the vertices adjacent vertices have been looked at then the vertex is set to black
			s.setBlack();
			
		}
		//creates a stack of vertices
		Stack<Vertex> stack = new Stack<Vertex>();
		//until the 2 vertices that have been given into the method are the same
		while(!u.toString().equals(v.toString())) {
			//the vertex and it's predecessors to the stack
			stack.add(u);
			u=G.getVertex(u.getPred());
		}
		//add v to the stack
		stack.add(v);
		//this method flips the shortest path so it goes from vertex v to vertex u
		//pops vertex from the stack and outs them into an array
		while(!stack.isEmpty()) {
			array.add(stack.pop());
		}
		return array;
	}
	

	/**
	 * returns the Vertex that has the highest total degree
	 * @param G
	 * @return
	 * Written by Pooja Kundaje 
	 */
	public static Vertex maxDegree(Graph G) {
		int maxDeg= 0;
		int temp;
		String value=null;
		for(Vertex a: G.myVertices.values()) {
			temp= myAdjList.get(a).size();
			
			if(temp>maxDeg) {
				maxDeg=temp;
				value = a.toString();
			}
		}
		return G.getVertex(value);
		
	}
	
	public static void main(String[] args) {
		Graph myGraph = new Graph();
		myGraph.addEdge("A", "B");
		myGraph.addEdge("A", "C");
		myGraph.addEdge("C", "D");
		myGraph.addEdge("D", "E");
		myGraph.addEdge("D", "G");
		myGraph.addEdge("E", "G");
		myGraph.addVertex("H");
		System.out.println(shortestPath(myGraph, myGraph.getVertex("A"), myGraph.getVertex("G")));
		// print out graph
		//System.out.println(myGraph);
		System.out.println(maxDegree(myGraph));
		System.out.println(cyclic(myGraph));
		System.out.print("adjList:"+myGraph.toString());

	}
}
