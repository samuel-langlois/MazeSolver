package assign08;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This Graph class acts as a starting point for your maze path finder. Add to
 * this class as needed.
 * 
 * @author Daniel Kopta, Noah Garff, Samuel Langlois.
 * @version November 9, 2021
 */
public class Graph {

	// The graph itself is just a 2D array of nodes
	private Node[][] nodes;

	// The node to start the path finding from
	private Node startNode;

	// if goal was reached in DFS
	private boolean goalReached;

	// The size of the maze
	private int width, height, size, touchedNodes;

	/**
	 * Constructs a maze graph from the given text file.
	 * 
	 * @param filename - the file containing the maze
	 * @throws Exception
	 */
	public Graph(String filename) throws Exception {
		BufferedReader input;
		input = new BufferedReader(new FileReader(filename));

		if (!input.ready()) {
			input.close();
			throw new FileNotFoundException();
		}

		// read the maze size from the file
		String[] dimensions = input.readLine().split(" ");
		height = Integer.parseInt(dimensions[0]);
		width = Integer.parseInt(dimensions[1]);

		// instantiate and populate the nodes
		nodes = new Node[height][width];
		for (int x = 0; x < height; x++) {
			String row = input.readLine().trim();

			for (int y = 0; y < row.length(); y++)
				switch (row.charAt(y)) {
				case 'X':
					nodes[x][y] = new Node(x, y);
					nodes[x][y].isWall = true;
					break;
				case ' ':
					nodes[x][y] = new Node(x, y);
					break;
				case 'S':
					nodes[x][y] = new Node(x, y);
					nodes[x][y].isStart = true;
					startNode = nodes[x][y];
					break;
				case 'G':
					nodes[x][y] = new Node(x, y);
					nodes[x][y].isGoal = true;
					break;
				default:
					throw new IllegalArgumentException("maze contains unknown character: \'" + row.charAt(y) + "\'");
				}
		}
		input.close();
	}

	/**
	 * Outputs this graph to the specified file. Use this method after you have
	 * found a path to one of the goals. Before using this method, for the nodes on
	 * the path, you will need to set their isOnPath value to true.
	 * 
	 * @param filename - the file to write to
	 */
	public void printGraph(String filename) {
		try {
			PrintWriter output = new PrintWriter(new FileWriter(filename));
			output.println(height + " " + width);
			for (int x = 0; x < height; x++) {
				for (int y = 0; y < width; y++) {
					output.print(nodes[x][y]);
				}
				output.println();
			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Traverse the graph with BFS (shortest path to closest goal) (might be a goal
	 * not closest)? A side-effect of this method should be that the nodes on the
	 * path have had their isOnPath member set to true.
	 * 
	 * @return - the length of the path
	 */
	public int CalculateShortestPath() {
		touchedNodes = 0;
		// use Queue
		Queue<Node> currentPath = new LinkedList<Node>(), // every currently available path
				shortestPath = new LinkedList<Node>();
		currentPath.offer(startNode);
		Node curN = null;
		int x = startNode.x, y = startNode.y;

		// adding and removing from the current path queue
		while (!currentPath.isEmpty()) {
			curN = currentPath.poll();
			touchedNodes++;
			x = curN.x;
			y = curN.y;
			// if it finds the goal, adds everything to shortest path queue and sets
			// isOnPath to true
			if (curN.isGoal) {
				shortestPath.offer(curN);
				curN = curN.prev;
				while (curN.prev != null) {
					shortestPath.offer(curN);
					curN.isOnPath = true;
					curN = curN.prev;
				}
				shortestPath.offer(curN);
				System.out.println(touchedNodes + " shortest path " + "\n" +"------");
				return shortestPath.size();
			}
			// checks for available paths. If available, adds to the current path queue
			if (nodes[x + 1][y].isAvailablePath()) {// down
				currentPath.offer(nodes[x + 1][y]);
				nodes[x + 1][y].prev = curN;
				nodes[x + 1][y].wasOnPath = true;
			}
			if (nodes[x][y + 1].isAvailablePath()) {//right
				currentPath.offer(nodes[x][y + 1]);
				nodes[x][y + 1].prev = curN;
				nodes[x][y + 1].wasOnPath = true;
			}
			if (nodes[x - 1][y].isAvailablePath()) {//up
				currentPath.offer(nodes[x - 1][y]);
				nodes[x - 1][y].prev = curN;
				nodes[x - 1][y].wasOnPath = true;
			}
			if (nodes[x][y - 1].isAvailablePath()) {// left
				currentPath.offer(nodes[x][y - 1]);
				nodes[x][y - 1].prev = curN;
				nodes[x][y - 1].wasOnPath = true;
			}
		}
//		System.out.println(touchedNodes + " shortest path " + "\n" +"------");
		return 0;

	}

	/**
	 * Traverse the graph with DFS (any path to any goal)? A side-effect of this
	 * method should be that the nodes on the path have had their isOnPath member
	 * set to true. This is the driver method for CalculateAPath.
	 * 
	 * @return - the length of the path, if no path exists length equals -1
	 */
	public int CalculateAPath() {
		touchedNodes = 0;
		goalReached = false;
		int pathLength = CalculateAPath(startNode, startNode);
		System.out.println(touchedNodes + " random path ");
		return pathLength;
	}

	/**
	 * Recursive CalculateAPath method. Sets nodes isOnPath to true if they are on
	 * the path to the goal
	 * 
	 * @return - the length of the path, if no path exists length equals -1
	 */
	public int CalculateAPath(Node curNode, Node prevNode) {
		int x = curNode.x, y = curNode.y;
		touchedNodes++;

		// if it find the goal, returns size and recurses back
		if (curNode.isGoal) {
			goalReached = true;
			return size + 1;
		}

		curNode.isOnPath = true;
		
		// checks each available path to find the direction to go
		
		if (nodes[x][y + 1].isAvailablePath() && !goalReached) {// right
			size = 1 + CalculateAPath(nodes[x][y + 1], curNode);
		}
		
		if (nodes[x - 1][y].isAvailablePath() && !goalReached) {// up
			size = 1 + CalculateAPath(nodes[x - 1][y], curNode);
		}
		if (nodes[x][y - 1].isAvailablePath() && !goalReached) {// left
			size = 1 + CalculateAPath(nodes[x][y - 1], curNode);
		}
		
		if (nodes[x + 1][y].isAvailablePath() && !goalReached) {// down
			size = 1 + CalculateAPath(nodes[x + 1][y], curNode);
		}
		
		
		// if the goal isn't reached, resets isOnPath to false, sets wasOnPath to true
		// and return
		if (!goalReached) {
			curNode.isOnPath = false;
			curNode.wasOnPath = true;
			return -1;
		}
//		System.out.println("touched nodes " + touchedNodes);
		return size;

	}

	public int getTouchedNodes() {
		return this.touchedNodes;
	}

	/**
	 * @author Daniel Kopta A node class to assist in the implementation of the
	 *         graph. You will need to add additional functionality to this class.
	 */
	private static class Node {
		// The node's position in the maze
		private int x, y;

		// The type of the node
		private boolean isStart, isGoal, isOnPath, isWall, wasOnPath;

		private Node prev;

		public Node(int _x, int _y) {
			isStart = false;
			isGoal = false;
			isOnPath = false;
			isWall = false;
			wasOnPath = false;
			x = _x;
			y = _y;
			prev = null;
		}

		public boolean isAvailablePath() {

			if (isStart || isWall || isOnPath || wasOnPath)
				return false;

			return true;
		}

		@Override
		public String toString() {
			if (isWall)
				return "X";
			if (isStart)
				return "S";
			if (isGoal)
				return "G";
			if (isOnPath)
				return ".";
			if(wasOnPath)
				return "v";
			return " ";
		}
	}

}