package app.algorithms.matlab;

import java.net.URL;
import java.text.DecimalFormat;

import app.algorithms.basic.BasicCalcs;
import app.data.generators.GraphGenerator;
import app.file.io.Writer;
import app.gui.frames.MainFrame;
import app.gui.frames.ProgressBar;
import app.predictors.helper.Helper;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;
import matlabcontrol.extensions.MatlabNumericArray;
import matlabcontrol.extensions.MatlabTypeConverter;

public class RLSR {

	public static String train(String matlabPath, double[][] s, double[][] y,
			double[][] x, int noTime, int training, int maxIter, int noOfNodes,
			int validation, int noX, int lfSize, String lambda,
			ProgressBar frame, String modelFolder) {
		MatlabProxyFactoryOptions options = new MatlabProxyFactoryOptions.Builder()
				.setHidden(true).setProxyTimeout(300000L)
				.setMatlabLocation(matlabPath).build();
		MatlabProxyFactory factory = new MatlabProxyFactory(options);
		MatlabProxy proxy;
		try {
			proxy = factory.getProxy();

			URL location = MainFrame.class.getProtectionDomain()
					.getCodeSource().getLocation();
			String path = location.getFile();
			path = path.substring(1, path.lastIndexOf("/"));
			String mainPath = path.substring(0, path.lastIndexOf("/"));
			path = path.substring(0, path.lastIndexOf("/")) + "/matlab/RLSR";
			proxy.eval("addpath('" + path + "')");

			proxy.eval("addpath(genpath('" + path + "/codes'))");

			// neural network parameters
			proxy.eval("parameters;");
			proxy.eval("nn_params = params;");
			proxy.eval("nn_params.Nh = 20;");
			proxy.eval("nn_params.nIter = 200;");
			proxy.eval("nn_params.save = 0;");
			proxy.eval("nn_params.func = {'sigm', 'none'};");
			proxy.eval("nn_params.early.use = 1;");
			proxy.eval("nn_params.nFig = 3;");

			// Structure learning parameters
			proxy.eval("sse_params = struct;");
			proxy.eval("sse_params.quiet = 1;");
			proxy.eval("sse_params.max_iters = 1000;");
			proxy.eval("sse_params.ls_max_iters = 1000;");
			proxy.eval("sse_params.tol = 1e-6;");
			proxy.eval("sse_params.col_tol = 1e-6;");
			proxy.eval("sse_params.alpha = 1;");

			// csv data
			proxy.eval("trainValidX = csvread('" + path
					+ "/data/trainValidX.csv');");
			proxy.eval("trainValidY = csvread('" + path
					+ "/data/trainValidY.csv');");
			proxy.eval("testX = csvread('" + path + "/data/testX.csv');");
			proxy.eval("testY = csvread('" + path + "/data/testY.csv');");

			// Training parameters
			proxy.setVariable("maxIter", maxIter);
			proxy.setVariable("trainSize", training);
			proxy.setVariable("validSize", validation);
			proxy.setVariable("testSize", noTime - (training + validation));
			proxy.setVariable("LFSize", lfSize);
			proxy.eval("lambda_set = [" + lambda + "];");

			// run train

			String message = null;
			try {
				proxy.eval("[best_layer, best_Lambda, best_Theta, best_lambda, time_spent] = "
						+ "RLSR_train_valid(trainValidX, trainValidY, trainSize, validSize, lambda_set, maxIter, LFSize, nn_params, sse_params);");

				proxy.eval("rmpath('" + path + "')");

				Writer.createFolder(modelFolder + "/parameters");
				String fileName = mainPath + "/" + modelFolder
						+ "/parameters/RLSR.mat";
				proxy.setVariable("fileName", fileName);

				proxy.eval("save(fileName,'best_layer','-v7.3')");
				message = "RLSR results: \n* ";
				proxy.disconnect();

			} catch (Exception e) {
				// e.printStackTrace();
				message = "An internal MATLAB exception occurred. Please check your data.";
			}

			// close matlab
			// Runtime rt = Runtime.getRuntime();
			// try {
			// rt.exec("taskkill /F /IM MATLAB.exe");
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			frame.setVisible(false);
			return message;

		} catch (MatlabConnectionException e) {
			// e.printStackTrace();
		} catch (MatlabInvocationException e) {
			// e.printStackTrace();
		}
		frame.setVisible(false);
		return "Connection with MATLAB cannot be established.";
	}

	private static String exportResults(double[][] y, double[][] outputs,
			String folder) {
		Writer.createFolder(folder + "/test");
		String fileName = folder + "/test/results";
		int cols = outputs[0].length;
		double[] array = null;
		double[] arrayY = null;
		double sum = 0;
		double r2 = 0;
		DecimalFormat df = new DecimalFormat("#.####");
		for (int i = 0; i < cols; i++) {
			array = new double[outputs.length];
			arrayY = new double[outputs.length];
			for (int j = 0; j < array.length; j++) {
				array[j] = outputs[j][i];
				arrayY[j] = y[j][i];
			}

			r2 = BasicCalcs.rSquaredWitNaN(array, arrayY);
			sum += r2;
			String[] text = exportTxt(r2, array);
			Writer.write(text, fileName + "T" + (i + 1) + ".txt");
		}
		double avg = sum / cols;
		return "\n* Average R^2 value: " + df.format(avg)
				+ "\n* Results are successfully exported. \n* File location: "
				+ folder + "/test";
	}

	private static String[] exportTxt(double r2, double[] outputs) {
		DecimalFormat df = new DecimalFormat("#.######");
		String[] txt = new String[outputs.length + 1];

		for (int i = 0; i < outputs.length; i++) {
			txt[i] = outputs[i] + "";
		}

		txt[outputs.length] = "R^2 UmGCRF: " + df.format(r2);

		return txt;
	}
}
