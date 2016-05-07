package app.file.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Reader {

	public static double[][] readGraph(String path, int noOfNodes) {
		double[][] matrix = new double[noOfNodes][noOfNodes];
		String[] text = Reader.read(path);
		String[] line;
		int i = 0;
		int j = 0;
		double weigth = 0;
		for (int k = 0; k < text.length; k++) {
			line = text[k].split(",");
			i = Integer.parseInt(line[0]);
			j = Integer.parseInt(line[1]);
			weigth = Double.parseDouble(line[2]);
			matrix[i - 1][j - 1] = weigth;
		}
		return matrix;
	}

	public static double[] readR(String path, int noOfNodes) {
		double[] r = new double[noOfNodes];

		String[] text = Reader.read(path);
		for (int k = 0; k < text.length; k++) {
			r[k] = Double.parseDouble(text[k]);
		}
		return r;
	}

	public static String[] read(String fileName) {
		File f = new File(fileName);
		try {
			BufferedReader b = new BufferedReader(new FileReader(f));
			ArrayList<String> text = new ArrayList<>();
			String s = b.readLine();
			while (s != null) {
				text.add(s);
				s = b.readLine();
			}
			b.close();
			return text.toArray(new String[text.size()]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] getAllFiles(String folder) {
		try {
			ArrayList<String> files = new ArrayList<>();
			Files.walk(Paths.get(folder)).forEach(
					filePath -> {
						if (Files.isRegularFile(filePath)) {
							if (filePath.getFileName().toString()
									.equalsIgnoreCase("Asymmetric")) {
								String[] folders = filePath.getParent().toString().split("\\\\");
								files.add(folders[1] + " - " + folders[2]);
							}
						}
					});
			return files.toArray(new String[files.size()]);
		} catch (IOException e) {
			return null;
		}
	}

}
