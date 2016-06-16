package app.algorithms.test;

import java.text.DecimalFormat;
import java.util.Random;

import app.algorithms.asymmetric.AlgorithmAsymmetric;
import app.algorithms.asymmetric.GradientDescentAsymmetric;

public class TestConvexity {

	public void test(int no, int maxIter, double lr, double[][] s, double[] r, double[] y, double[][] s1, double[] r1,
			double[] y1) {
		double alpha = 0;
		double beta = 0;
		DecimalFormat df = new DecimalFormat("#.##");
		for (int i = 0; i < no; i++) {
			alpha = Math.random() * 5;
			alpha = Double.parseDouble(df.format(alpha));
			beta = Math.random() * 5;
			beta = Double.parseDouble(df.format(beta));
			GradientDescentAsymmetric gda = new GradientDescentAsymmetric(alpha, beta, lr, s, r, y);
			double[] res = gda.learn(maxIter, false, null);

			AlgorithmAsymmetric alg = new AlgorithmAsymmetric(res[0], res[1], s, r, y);
			double r2 = alg.rSquared();

			AlgorithmAsymmetric alg1 = new AlgorithmAsymmetric(res[0], res[1], s1, r1, y1);
			double r2Test = alg1.rSquared();
			System.out.println(alpha + "," + beta + "," + r2 + "," + r2Test);
		}

	}
}
