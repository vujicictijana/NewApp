package app.main;

import app.algorithms.asymmetric.CalculationsAsymmetric;
import app.algorithms.test.*;
import app.data.generators.ArrayGenerator;
import app.data.generators.GraphGenerator;

public class Main {

	public static void main(String[] args) {
		// TestAsymmetric t = new TestAsymmetric();

		// // fully connected asymmetric
		// t.testTrainAndTestMore(200, 10000, 5, true);
		// t.testTrainAndTestMore(200, 10000, 5, false);

		// // asymmetric with probability 0.5
		// t.testTrainAndTestMoreWithProbability(200, 10000, 5, true, 0.5);
		// t.testTrainAndTestMoreWithProbability(200, 10000, 5, false, 0.5);
		//
		// // asymmetric with probability 0.2
		// t.testTrainAndTestMoreWithProbability(200, 10000, 5, true, 0.2);
		// t.testTrainAndTestMoreWithProbability(200, 10000, 5, false, 0.2);
		//
		// // asymmetric with probability 0.05
		// t.testTrainAndTestMoreWithProbability(200, 10000, 5, true, 0.05);
		// t.testTrainAndTestMoreWithProbability(200, 10000, 5, false, 0.05);

		// TestGenerators tg = new TestGenerators();
		// tg.test();

		// TestAcyclic ta = new TestAcyclic();
		// ta.testGCRF(200, 1000);
		// ta.testTrainAndTestMore(200, 1000, 5, "resultsAcyclic.txt", true);
		// ta.testTrainAndTestMore(200, 1000, 5, "resultsAcyclicNotSame.txt",
		// false);

		// TestSymmetric ts = new TestSymmetric();
		// fully connected asymmetric
		// ts.testTrainAndTestMore(200, 10000, 5, "AsymmetricSame", true);

		// Test tt = new Test();
		// tt.runAsymmetric();
		// tt.testAsymmetric();
		// tt.runSymmetric();
		// tt.testSymmetric();

		TestConvexity tc = new TestConvexity();

		int nodes = 200;
		int iterations = 100;
		double rate = 0.01;
		// int times = 30;

		double s[][] = GraphGenerator.generateDirectedGraphWithEdgeProbability(
				nodes, 0.5);
		double[] r = ArrayGenerator.generateArray(nodes, 5);
		CalculationsAsymmetric c = new CalculationsAsymmetric(s, r);
		double[] y = c.y(5, 1, 0.05);

		double s1[][] = GraphGenerator
				.generateDirectedGraphWithEdgeProbability(nodes, 0.5);
		double[] r1 = ArrayGenerator.generateArray(nodes, 5);
		CalculationsAsymmetric c1 = new CalculationsAsymmetric(s1, r1);
		double[] y1 = c1.y(5, 1, 0.05);

		// tc.test(times, iterations, rate, s, r, y, s1, r1, y1);
		tc.testNew(iterations, rate, s, r, y, s1, r1, y1);

	}

}
