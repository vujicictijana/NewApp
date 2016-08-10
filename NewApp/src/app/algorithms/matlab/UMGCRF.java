package app.algorithms.matlab;

import java.io.IOException;
import java.net.URL;

import app.data.generators.GraphGenerator;
import app.gui.frames.MainFrame;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;
import matlabcontrol.extensions.MatlabNumericArray;
import matlabcontrol.extensions.MatlabTypeConverter;

public class UMGCRF {

	public static void main(String[] args) {
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
			
			//random data
			proxy.setVariable("n", 100);
			proxy.setVariable("a", 4);
			proxy.eval("W = rand(n);");
			proxy.eval("S = (W + W')/2");
			// MatlabTypeConverter processor = new MatlabTypeConverter(proxy);
			// MatlabNumericArray array = processor.getNumericArray("S");
			// double[][] matrix = array.getRealArray2D();
			// GraphGenerator.showMatrix(matrix);
			proxy.eval("Rtrain = 100*rand(n,a);");
			proxy.eval("alpha_true = 10*rand(a,1); beta_true = 5*rand;");
			proxy.eval("L = diag(sum(S)) - S;");
			proxy.eval("Q = sum(alpha_true)*eye(n) + beta_true*L;");
			proxy.eval("Ytrain = ( Q \\ (Rtrain*alpha_true) ) + normrnd(0,10,[n,1]);");
			
			//run train
			proxy.eval("[theta, MSE_train_UMGCRF, mu] = UMtrain(Ytrain,S,Rtrain);");
			
			proxy.eval("rmpath('" + path + "')");
			
			double[] result =  ((double[]) proxy.getVariable("mu"));
			
			for (int i = 0; i < result.length; i++) {
				System.out.println(result[i] );
			}
			proxy.disconnect();
			
			// close matlab
			// Runtime rt = Runtime.getRuntime();
			// try {
			// rt.exec("taskkill /F /IM MATLAB.exe");
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		} catch (MatlabConnectionException e) {
			e.printStackTrace();
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
		}
	}
}
