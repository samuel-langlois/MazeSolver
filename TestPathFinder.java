package assign08;

/****
 *  This class shows an example of how to run your pathfinder.
 * 
 * @author Daniel Kopta, Noah Garff, Samuel Langlois.
 */
public class TestPathFinder {

	public static void main(String[] args) {
		/*
		 * The below code assumes you have a folder called assignment8_files in your
		 * project folder, and in that folder is a file called bigMaze.txt. If your
		 * solution is implemented correctly, it will produce a file called
		 * testOutput.txt which has the solution to the maze. You will have to browse to
		 * that folder to view the output, it will not automatically show up in Eclipse.
		 * testOutput1.txt - solution maze with shortest path to closest goal
		 * testOutput2.txt - solution maze with any path to any goal
		 */


		// Run the pathfinder with a final argument of true to find the shortest path to
		// the closest goal
//		MazeGen.randomMaze("src/assign08/bigMaze.txt", 5, .0, 2);
//		PathFinder.solveMaze("src/assign08/bigMaze.txt", "src/assign08/testOutput.txt", true);
//		PathFinder.solveMaze("src/assign08/bigMaze.txt", "src/assign08/testOutput5.txt", false);
		
		
		int shortestPathNodeCounter, randomPathNodeCounter;
		int[] shortestPathNodeArray, randomPathNodeArray;

		for (int i = 0; i < 1000; i++)
			MazeGen.randomMaze("src/assign08/assign08_files/" + i + "random.txt", 50, .3, 5);
		int shortestCount = 0, randomCount = 0;

		for (int i = 0; i < 1000; i++) {
			Graph g = null, g2 = null;
			try {
				g = new Graph("src/assign08/assign08_files/" + i + "random.txt");
				g2 = new Graph("src/assign08/assign08_files/" + i + "random.txt");
			} catch (Exception e) {
				e.printStackTrace();
			}

			g.CalculateShortestPath();
			shortestCount += g.getTouchedNodes();
			g2.CalculateAPath();
			randomCount += g2.getTouchedNodes();

			g.printGraph("src/assign08/assign08_files/" + i + "randomOutputShortest.txt");
			g2.printGraph("src/assign08/assign08_files/" + i + "randomOutput.txt");
		}
		System.out.println("\n"+ (shortestCount / 10) + " shortest "+ (randomCount / 10) +" random ");
//		PathFinder.solveMaze("src/assign08/" + i + "random.txt", "src/assign08/" + i + "randomOutput.txt", false);

//		PathFinder.solveMaze("src/assign08/" + i + "random.txt", "src/assign08/" + i + "randomOutputShortest.txt", true);

	}
}
