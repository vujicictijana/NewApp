package app.main;

import app.algorithms.test.*;

public class Main {

	public static void main(String[] args) {
		TestAsymmetric t = new TestAsymmetric();

		// // fully connected asymmetric
		// t.testTrainAndTestMore(200, 10000, 5, true);
		t.testTrainAndTestMore(200, 10000, 5, false);

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

	}

}
