package assign08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

/**
 * @author Samuel Langlois and Noah Garff
 **/
public class BFSandDFSTiming {
		private static Random rand;

	public static void main(String[] args) {
		rand = new Random();
		rand.setSeed(System.currentTimeMillis());

		// Do 10000 lookups and use the average running time.
		int timesToLoop = 100;
		for (int n = 1000; n <= 20000; n += 1000) {

			long startTime, midpointTime, stopTime;
			MazeGen.randomMaze("src/assign08/bigMaze.txt", n/200, .3, 5);
			Graph g = null;
			try {
				g = new Graph("src/assign08/bigMaze.txt");
			} catch (Exception e) {
				e.printStackTrace();
			}
//			PathFinder.solveMaze("assignment8_files/bigMaze.txt", "assignment8_files/testOutput1.txt", true);
			
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) {

			}
			

			startTime = System.nanoTime();

			for (int i = 0; i < timesToLoop; i++) {
//				MazeGen.randomMaze("src/assign08/bigMaze.txt", 100, .3, 5);
//				Graph g = null;
//				try {
//					g = new Graph("src/assign08/bigMaze.txt");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
//			startTime = System.nanoTime();
//
//			for (int i = 0; i < timesToLoop; i++) {
//				MazeGen.randomMaze("src/assign08/bigMaze.txt", 100, .3, 5);
//				Graph g = null;
//				try {
//					g = new Graph("src/assign08/bigMaze.txt");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}

			midpointTime = System.nanoTime();

			for (int i = 0; i < timesToLoop; i++) {
				
//				g.CalculateShortestPath();
				g.CalculateAPath();
			}

			stopTime = System.nanoTime();

			double firstTime = (midpointTime - startTime);
			double secondTime = (stopTime - midpointTime);
			double finalTime = secondTime - firstTime;
			System.out.println(n + "\t   " + secondTime);

		}
		// / (n * Math.log(n))
	}



	public static ArrayList<Integer> addedInOrder(int maxVal) {
		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < maxVal; i++) {
			list.add(i + 1);
		}

		return list;
	}


}