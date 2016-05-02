package app.graph.generators;

import java.util.Random;

public class Generator {

	public static int randomGraph(int n, int m, long seed, boolean simple,
			boolean directed, boolean acyclic, boolean weighted, int minweight,
			int maxweight, int nodei[], int nodej[], double weight[]) {

		int maxedges, nodea, nodeb, numedges, temp;
		int dagpermute[] = new int[n + 1];
		boolean adj[][] = new boolean[n + 1][n + 1];
		Random ran = new Random(seed);
		// initialize the adjacency matrix
		for (nodea = 1; nodea <= n; nodea++)
			for (nodeb = 1; nodeb <= n; nodeb++)
				adj[nodea][nodeb] = false;
		numedges = 0;
		// check for valid input data
		if (simple) {
			maxedges = n * (n - 1);
			if (!directed)
				maxedges /= 2;
			if (m > maxedges)
				return 1;
		}
		if (acyclic) {
			maxedges = (n * (n - 1)) / 2;
			if (m > maxedges)
				return 1;
			randomPermutation(n, ran, dagpermute);
		}
		while (numedges < m) {

			nodea = ran.nextInt(n) + 1;
			nodeb = ran.nextInt(n) + 1;
			if (simple || acyclic)
				if (nodea == nodeb)
					continue;
			if ((simple && (!directed)) || acyclic)
				if (nodea > nodeb) {
					temp = nodea;
					nodea = nodeb;
					nodeb = temp;
				}
			if (acyclic) {
				nodea = dagpermute[nodea];
				nodeb = dagpermute[nodeb];
			}
			if ((!simple) || (simple && (!adj[nodea][nodeb]))) {
				numedges++;
				nodei[numedges] = nodea;
				nodej[numedges] = nodeb;
				adj[nodea][nodeb] = true;
				if (weighted)
					weight[numedges] = Math.random();
			}
		}
		return 0;
	}

	private static void randomPermutation(int n, Random ran, int perm[]) {
		int i, j, k;
		for (i = 1; i <= n; i++)
			perm[i] = i;
		for (i = 1; i <= n; i++) {
			j = (int) (i + ran.nextDouble() * (n + 1 - i));
			k = perm[i];
			perm[i] = perm[j];
			perm[j] = k;
		}
	}

	public static double[][] getSMatrixAcyclic(int n) {
		int m = (n * (n - 1)) / 2;
		long seed = 1;
		boolean simple = true, directed = true, acyclic = true, weighted = true;
		int minweight = 0;
		int maxweight = 1;
		int nodei[] = new int[m + 1];
		int nodej[] = new int[m + 1];
		double weight[] = null;
		weight = new double[m + 1];

		int k = Generator.randomGraph(n, m, seed, simple, directed, acyclic,
				weighted, minweight, maxweight, nodei, nodej, weight);
		if (k != 0)
			System.out.println("Invalid input data, error code = " + k);
		else {
			// System.out.println("List of edges:\n from to weight");
			// for (k = 1; k <= m; k++)
			// System.out.println(" " + nodei[k] + " " + nodej[k] + " "
			// + weight[k]);

			double[][] matrix = new double[n][n];
			for (int i = 1; i <= m; i++) {
				int indexI = nodei[i] - 1;
				int indexJ = nodej[i] - 1;
				matrix[indexI][indexJ] = weight[i];
			}

			return matrix;
		}
		return null;

	}
	
	
}
