package app.predictors.neuralnetwork;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

public class Helper {

	public static double normalize(int x, int max, int min) {
		DecimalFormat df = new DecimalFormat("#.##");
		double res = (double) (x - min) / (double) (max - min);
		return Double.parseDouble(df.format(res));
	}

	public static double round(double d) {
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.parseDouble(df.format(d));
	}

	public static double average(double[] array) {
		double sum = 0;
		for (int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		return sum / array.length;
	}

	public static double rootMeanSquaredError(double[] expectedY,
			double[] outputs) {
		double firstSum = 0;
		for (int i = 0; i < outputs.length; i++) {
			firstSum += Math.pow(outputs[i] - expectedY[i], 2);
		}
		return Math.sqrt(firstSum / (double) expectedY.length);
	}

	public static int[] getInts(String l) {
		String[] line = l.split("-");
		int[] ints = new int[line.length];
		for (int i = 0; i < ints.length; i++) {
			ints[i] = Integer.parseInt(line[i]);
		}
		return ints;
	}

	public static double rSquared(double[] expectedY, double[] outputs) {
		double avg = average(outputs);
		double firstSum = 0;
		double secondSum = 0;
		for (int i = 0; i < outputs.length; i++) {
			firstSum += Math.pow(expectedY[i] - outputs[i], 2);
			secondSum += Math.pow(expectedY[i] - avg, 2);
		}
		// System.out.println(firstSum);
		// System.out.println(secondSum);
		// System.out.println(firstSum / secondSum);
		return 1 - (firstSum / secondSum);
	}

	public static void showMatrix(double[][] s) {
		DecimalFormat df = new DecimalFormat("#.##");
		for (int i = 0; i < s.length; i++) {
			double[] s1 = s[i];
			for (int j = 0; j < s1.length; j++) {
				System.out.print(df.format(s[i][j]) + "\t");
			}
			System.out.println();
		}
	}

	public static void showArray(double[] array) {
		DecimalFormat df = new DecimalFormat("#.##");

		for (int j = 0; j < array.length; j++) {
			System.out.print(df.format(array[j]) + "\t");
		}
	}

	public static double[][] connectMatrices(double[][] m1, double[][] m2) {
		double[][] finalMatrix = new double[m1.length][m1.length];
		double avg = 0;
		for (int j = 0; j < finalMatrix.length; j++) {
			for (int i = 0; i < finalMatrix.length; i++) {
				avg = (m1[i][j] + m2[i][j]) / 2;
				finalMatrix[i][j] = avg;
			}
		}
		return finalMatrix;
	}

	public static DataSet prepareDataForNN(String[] data, double[] y){
		int no = data[0].split(",").length;
		DataSet d = new DataSet(no, 1);
		double[] x = null;
		String[] values = null;
		for (int i = 0; i < data.length; i++) {
			x = new double[no];
			values = data[0].split(",");
			if(values.length!=no){
				return null;
			}else{
				for (int j = 0; j < values.length; j++) {
					x[j] = Double.parseDouble(values[j]);
				}
			}
			d.addRow(new DataSetRow(x,new double[]{y[i]}));
		}
		return d;
	}
}
