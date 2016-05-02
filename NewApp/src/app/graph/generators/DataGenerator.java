package app.graph.generators;

import java.util.Random;

public class DataGenerator {

	public static double[][] generateS(int noOfNodes) {
		double[][] s = new double[noOfNodes][noOfNodes];
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s.length; j++) {
				if (i != j) {
					s[i][j] = Math.random();
				}
				// else {
				// s[i][j] = 1;
				// }
			}
		}
		return s;
	}

	public static double[][] generateSWithProbability(int noOfNodes,
			double probability) {
		double prob = 1-probability;
		double[][] s = new double[noOfNodes][noOfNodes];
		double p = 0;
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s.length; j++) {
				if (i != j) {
					p = Math.random();
					if (p >= prob) {
						s[i][j] = Math.random();
					}
				}
			}
		}
		return s;
	}

	public static double[][] converteSMatrixToSymmetric(double[][] s) {
		double[][] sSymmetric = new double[s.length][s.length];
		double first = 0;
		double second = 0;
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s.length; j++) {
				if (sSymmetric[i][j] == 0) {
					if (i != j) {
						first = s[i][j];
						second = s[j][i];
						sSymmetric[i][j] = (first + second) / 2;
						sSymmetric[j][i] = (first + second) / 2;
					}
					// else {
					// sSymmetric[i][j] = 1;
					// }
				}
			}
		}
		return sSymmetric;
	}

	public static double[] generateR(int noOfNodes) {
		double[] r = new double[noOfNodes];
		Random rand = new Random();
		for (int i = 0; i < r.length; i++) {
			r[i] = rand.nextInt(5) + 1 + Math.random();
		}
		return r;
	}

}
