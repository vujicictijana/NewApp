package app.algorithms.matlab;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;

import app.algorithms.asymmetric.CalculationsAsymmetric;
import app.algorithms.basic.BasicCalcs;
import app.data.generators.ArrayGenerator;
import app.data.generators.GraphGenerator;
import app.file.io.Writer;
import app.gui.frames.MainFrame;
import app.gui.frames.ProgressBar;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;
import matlabcontrol.extensions.MatlabNumericArray;
import matlabcontrol.extensions.MatlabTypeConverter;

public class MGCRF {

	public static String train(String matlabPath, double[][] s, double[][] y,
			double[][] r, int noTime, int training, int maxIter, int regAlpha,
			int regBeta, ProgressBar frame, String modelFolder) {
		MatlabProxyFactoryOptions options = new MatlabProxyFactoryOptions.Builder()
				.setHidden(true).setProxyTimeout(30000L)
				.setMatlabLocation(matlabPath).build();
		MatlabProxyFactory factory = new MatlabProxyFactory(options);
		MatlabProxy proxy;
		try {
			proxy = factory.getProxy();

			URL location = MainFrame.class.getProtectionDomain()
					.getCodeSource().getLocation();
			String path = location.getFile();
			path = path.substring(1, path.lastIndexOf("/"));
			path = path.substring(0, path.lastIndexOf("/")) + "/matlab/mGCRF";
			proxy.eval("addpath('" + path + "')");

			MatlabTypeConverter processor = new MatlabTypeConverter(proxy);
			processor.setNumericArray("similarity", new MatlabNumericArray(s,
					null));
			processor.setNumericArray("R", new MatlabNumericArray(r, null));
			processor.setNumericArray("y", new MatlabNumericArray(y, null));

			proxy.setVariable("numTimeSteps", noTime);

			proxy.setVariable("training", training);
			proxy.setVariable("maxiter", maxIter);
			proxy.setVariable("regAlpha", regAlpha);
			proxy.setVariable("regBeta", regBeta);
			// proxy.setVariable("r", r);
			proxy.eval("RR{1} = R");

			// run train

			String message = null;
			try {
				proxy.eval("[Data,predictionCRF] = mGCRF(numTimeSteps,training,maxiter,regAlpha,regBeta,RR,y,similarity);");

				proxy.eval("rmpath('" + path + "')");

				// double theta = ((double[]) proxy.getVariable("theta"))[0];
				double[] output = ((double[]) proxy
						.getVariable("predictionCRF"));

				// Writer.createFolder(modelFolder + "/parameters");
				// String fileName = modelFolder + "/parameters/UmGCRF.txt";
				// String[] text = { "Theta=" + theta };
				// Writer.write(text, fileName);
				double r2 = 0;
				// double r2 = BasicCalcs.rSquared(output, y);
				DecimalFormat df = new DecimalFormat("#.####");
				message = "Testing with same data:\n* R^2 value for standard UmGCRF is: "
						+ df.format(r2);
				proxy.disconnect();

			} catch (Exception e) {
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
			e.printStackTrace();
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
		}
		frame.setVisible(false);
		return "Connection with MATLAB cannot be established.";
	}
}
