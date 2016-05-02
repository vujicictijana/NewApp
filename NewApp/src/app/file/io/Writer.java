package app.file.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Writer {

	public static void writeGraph(double[][] matrix, String fileName) {
		String[] edges = new String[edges(matrix)];
		int edge = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] != 0) {
					edges[edge] = (i + 1) + "," + (j + 1) + "," + matrix[i][j];
					edge++;
				}
			}
		}
		write(fileName, edges);
	}

	public static void writeR(double[] r, String fileName) {
		String[] rText = new String[r.length];
		for (int i = 0; i < r.length; i++) {
			rText[i] = r[i] + "";
		}
		write(fileName, rText);
	}

	public static int edges(double[][] matrix) {
		int edges = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] != 0) {
					edges++;
				}
			}
		}
		return edges;
	}

	public static void write(String fileName, String[] text) {
		File file = new File(fileName);
		try {
			PrintStream print = new PrintStream(file);

			for (int i = 0; i < text.length; i++) {
				print.println(text[i]);
			}
			print.flush();
			print.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void createFolder(String name) {
		File dir = new File(name);
		if (!dir.exists()) {
			dir.mkdir();
		}
	}

}
