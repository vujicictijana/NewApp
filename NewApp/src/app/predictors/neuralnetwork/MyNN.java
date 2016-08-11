package app.predictors.neuralnetwork;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;

import app.algorithms.basic.BasicCalcs;
import app.file.io.Writer;
import app.predictors.helper.Helper;

public class MyNN {

	public static double learn(int hidden, String[] x, double[] y,
			double maxError, int maxIter, String folder) {

		BackPropagation b = new BackPropagation();
		b.setMaxError(maxError);
		b.setMaxIterations(maxIter);
		DataSet trainingSet = Helper.prepareDataForNN(x, y);

		if (trainingSet != null) {
			MultiLayerPerceptron neuralNetwork = new MultiLayerPerceptron(
					TransferFunctionType.TANH, trainingSet.getRowAt(0)
							.getInput().length, hidden, 1);
			neuralNetwork.learn(trainingSet, b);
			double[] outputs = new double[trainingSet.getRows().size()];
			String[] rArray = new String[outputs.length];

			int i = 0;
			for (DataSetRow row : trainingSet.getRows()) {
				neuralNetwork.setInput(row.getInput());
				neuralNetwork.calculate();
				outputs[i] = Helper.round(neuralNetwork.getOutput()[0]);
				rArray[i] = outputs[i] + "";
				i++;
			}
			if (folder != null) {
				Writer.createFolder(folder + "/nn");
				neuralNetwork.save(folder + "/nn/nn.nnet");
				Writer.write(rArray, folder + "/data/r.txt");
			}
			return testWithSame(trainingSet, neuralNetwork);
		} else {
			return -5000;
		}
	}

	private static double testWithSame(DataSet trainingSet,
			MultiLayerPerceptron neuralNetwork) {
		double[] outputs = new double[trainingSet.getRows().size()];
		double[] expectedY = new double[trainingSet.getRows().size()];
		String[] rArray = new String[outputs.length];

		int i = 0;
		for (DataSetRow row : trainingSet.getRows()) {
			neuralNetwork.setInput(row.getInput());
			neuralNetwork.calculate();
			outputs[i] = Helper.round(neuralNetwork.getOutput()[0]);
			expectedY[i] = row.getDesiredOutput()[0];
			rArray[i] = outputs[i] + "";
			i++;
		}
		return BasicCalcs.rSquared(outputs,expectedY);
	}

	public static double test(String folder, String[] x, double[] y) {
		MultiLayerPerceptron neuralNetwork = (MultiLayerPerceptron) NeuralNetwork
				.createFromFile(folder+"/nn/nn.nnet");
		DataSet testSet = Helper.prepareDataForNN(x, y);
		if (testSet == null) {
			return -5000;
		}
		double[] outputs = new double[testSet.getRows().size()];
		double[] expectedY = new double[testSet.getRows().size()];
		String[] rArray = new String[outputs.length];
		int i = 0;
		for (DataSetRow row : testSet.getRows()) {
			neuralNetwork.setInput(row.getInput());
			neuralNetwork.calculate();
			outputs[i] = neuralNetwork.getOutput()[0];
			expectedY[i] = row.getDesiredOutput()[0];
			rArray[i] = outputs[i] + "";

			i++;
		}
		if (folder != null) {
			Writer.write(rArray, folder + "/data/rTest.txt");
		}
		return BasicCalcs.rSquared(outputs,expectedY);
	}

}
