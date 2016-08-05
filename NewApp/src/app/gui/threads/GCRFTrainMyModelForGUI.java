package app.gui.threads;

import java.awt.Color;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import app.algorithms.asymmetric.AlgorithmAsymmetric;
import app.algorithms.asymmetric.GradientDescentAsymmetric;
import app.algorithms.symmetric.AlgorithmSymmetric;
import app.algorithms.symmetric.GradientDescentSymmetric;
import app.data.generators.GraphGenerator;
import app.file.io.Writer;
import app.gui.frames.ProgressBar;

public class GCRFTrainMyModelForGUI extends Thread {
	private ProgressBar frame;
	private JFrame mainFrame;

	private double s[][];
	private double r[];
	private double y[];
	private double alpha;
	private double beta;
	private double lr;
	private int maxIter;
	private String modelFolder;
	private String time;

	public GCRFTrainMyModelForGUI(String modelFolder, ProgressBar frame,
			JFrame mainFrame, double[][] s, double[] r, double[] y,
			double alpha, double beta, double lr, int maxIter) {
		super();
		this.frame = frame;
		this.mainFrame = mainFrame;
		this.s = s;
		this.r = r;
		this.y = y;
		this.alpha = alpha;
		this.beta = beta;
		this.lr = lr;
		this.maxIter = maxIter;
		this.modelFolder = modelFolder;
		time = "Time in milis: ";
	}

	public void run() {
		mainFrame.setEnabled(false);

		frame.getCurrent().setValue(0);
		frame.setTitle("Progress standard GCRF");
		long start = System.currentTimeMillis();
		GradientDescentSymmetric gdS = new GradientDescentSymmetric(alpha,
				beta, lr, s, r, y);
		double[] resS = gdS.learn(maxIter, false, frame.getCurrent());

		AlgorithmSymmetric algS = new AlgorithmSymmetric(resS[0], resS[1], s,
				r, y);
		double r2S = algS.rSquared();
		long elapsedTime = System.currentTimeMillis() - start;
		time += "\n* GCRF: " + elapsedTime;

		createFile("GCRF.txt", resS);

		DecimalFormat df = new DecimalFormat("#.####");
		String message = "Testing with same data:\n R^2 value for standard GCRF is: "
				+ df.format(r2S);
		message += "\n" + time;
		JOptionPane.showMessageDialog(mainFrame, message, "Results",
				JOptionPane.INFORMATION_MESSAGE);
		mainFrame.setEnabled(true);
		frame.setVisible(false);
	}

	public void createFile(String symmetric, double[] results) {
		Writer.createFolder(modelFolder);
		String fileName = modelFolder + "/" + symmetric;
		String[] resultsS = { results[0] + "", results[1] + "" };
		Writer.write(resultsS, fileName);
	}

}
