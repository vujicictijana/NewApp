package app.algorithms.test;

import java.text.DecimalFormat;

import app.graph.generators.Generator;

public class TestGenerators {

	public void test() {
		DecimalFormat df = new DecimalFormat("#.##");

		double[][] matrix = Generator.getSMatrixAcyclic(5);
		System.out.println("Matrix\n\t");
		
		int n = matrix.length;
		
		for (int i = 0; i < n; i++) {
			System.out.print("\t" + (i + 1));
		}

		System.out.println();

		for (int i = 0; i < n; i++) {
			System.out.print((i + 1) + "\t");
			for (int j = 0; j < matrix.length; j++) {
				System.out
						.print(Double.valueOf(df.format(matrix[i][j])) + "\t");
			}
			System.out.println();
		}

	}

}
