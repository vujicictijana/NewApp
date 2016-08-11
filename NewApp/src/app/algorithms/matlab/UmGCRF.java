package app.algorithms.matlab;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;

import app.algorithms.asymmetric.CalculationsAsymmetric;
import app.algorithms.basic.BasicCalcs;
import app.data.generators.ArrayGenerator;
import app.data.generators.GraphGenerator;
import app.gui.frames.MainFrame;
import app.gui.frames.ProgressBar;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;
import matlabcontrol.extensions.MatlabNumericArray;
import matlabcontrol.extensions.MatlabTypeConverter;

public class UmGCRF {

	public static String train(double[][] s, double[] y, double[] r,
			ProgressBar frame) {
		MatlabProxyFactoryOptions options = new MatlabProxyFactoryOptions.Builder()
				.setHidden(true)
				.setProxyTimeout(30000L)
				.setMatlabLocation(
						"C:/Program Files/MATLAB/R2016a/bin/matlab.exe")
				.build();
		MatlabProxyFactory factory = new MatlabProxyFactory(options);
		MatlabProxy proxy;
		try {
			proxy = factory.getProxy();

			URL location = MainFrame.class.getProtectionDomain()
					.getCodeSource().getLocation();
			String path = location.getFile();
			path = path.substring(1, path.lastIndexOf("/"));
			path = path.substring(0, path.lastIndexOf("/")) + "/matlab";
			proxy.eval("addpath('" + path + "')");

			// random data from matlab

			// proxy.setVariable("n", 100);
			// proxy.setVariable("a", 1);
			// proxy.eval("W = rand(n);");
			// proxy.eval("S = (W + W')/2");
			// MatlabTypeConverter processor = new MatlabTypeConverter(proxy);
			// MatlabNumericArray array = processor.getNumericArray("S");
			// double[][] matrix = array.getRealArray2D();
			// proxy.eval("Rtrain = 100*rand(n,a);");
			// GraphGenerator.showMatrix(matrix);
			// proxy.eval("alpha_true = 10*rand(a,1); beta_true = 5*rand;");
			// proxy.eval("L = diag(sum(S)) - S;");
			// proxy.eval("Q = sum(alpha_true)*eye(n) + beta_true*L;");
			// proxy.eval("Ytrain = ( Q \\ (Rtrain*alpha_true) ) + normrnd(0,10,[n,1]);");

			// random data from Java

			// double[][] s = GraphGenerator
			// .converteGraphToUndirected(GraphGenerator
			// .generateDirectedGraph(100));
			// double[] r = ArrayGenerator.generateArray(100, 5);
			// CalculationsAsymmetric c = new CalculationsAsymmetric(s, r);
			// double[] y = c.y(5, 1, 0.05);

			MatlabTypeConverter processor = new MatlabTypeConverter(proxy);
			processor.setNumericArray("S", new MatlabNumericArray(s, null));

			proxy.setVariable("Rtrain", r);
			proxy.eval("Rtrain = transpose(Rtrain)");

			proxy.setVariable("Ytrain", y);
			proxy.eval("Ytrain = transpose(Ytrain)");

			// run train

			String message = null;
			try {
				proxy.eval("[theta, MSE_train_UMGCRF, mu] = UMtrain(Ytrain,S,Rtrain);");

				proxy.eval("rmpath('" + path + "')");

				double[] output = ((double[]) proxy.getVariable("mu"));

				double r2 = BasicCalcs.rSquared(output, y);
				DecimalFormat df = new DecimalFormat("#.####");
				message = "Testing with same data:\n* R^2 value for standard UmGCRF is: "
						+ df.format(r2);
				proxy.disconnect();

			} catch (Exception e) {
				message = "An internal MATLAB exception occurred. Please check your data.";
			}

			// close matlab
			Runtime rt = Runtime.getRuntime();
			try {
				rt.exec("taskkill /F /IM MATLAB.exe");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
