package app.gui.threads;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import app.algorithms.matlab.RLSR;
import app.file.io.Reader;
import app.gui.frames.ProgressBar;

public class RLSRTrainMyModelForGUI extends Thread {
	private ProgressBar frame;
	private JFrame mainFrame;

	private double x[][];
	private double y[][];
	private String modelFolder;
	private String time;
	private String matlabPath;
	private int noTime;
	private int training;
	private int maxIter;
	private int noOfNodes;
	private int validation;
	private int noX;
	private int lfSize;
	private String lambda;
	private int test;
	private int iterNN;
	private int hidden;
	private int iterSSE;
	private int iterLs;

	public RLSRTrainMyModelForGUI(String matlabPath, String modelFolder,
			ProgressBar frame, JFrame mainFrame, double[][] r, double[][] y,
			int noTime, int training, int maxIter, int noOfNodes,
			int validation, int noX, int lfSize, String lambda, int test,
			int iterNN, int hidden, int iterSSE, int iterLs) {
		super();
		this.frame = frame;
		this.mainFrame = mainFrame;
		this.x = r;
		this.y = y;
		this.modelFolder = modelFolder;
		this.matlabPath = matlabPath;
		this.noTime = noTime;
		this.training = training;
		this.maxIter = maxIter;
		this.noOfNodes = noOfNodes;
		this.validation = validation;
		this.noX = noX;
		this.lambda = lambda;
		this.lfSize = lfSize;
		this.test = test;
		this.iterNN = iterNN;
		this.hidden = hidden;
		this.iterSSE = iterSSE;
		this.iterLs = iterLs;
		time = "* Time in seconds: ";
	}

	public void run() {
		if (Reader.checkFile(matlabPath) == false) {
			JOptionPane
					.showMessageDialog(
							mainFrame,
							"Path to MATLAB.exe is not good. Please change path in Settings->Configuration",
							"Results", JOptionPane.ERROR_MESSAGE);
			frame.setVisible(false);
			return;
		}
		mainFrame.setEnabled(false);
		frame.setTitle("Please wait: RLSR is in progress ");
		long start = System.currentTimeMillis();

		String message = RLSR.train(matlabPath, y, x, noTime, training,
				maxIter, noOfNodes, validation, noX, lfSize, test, iterNN,
				hidden, iterSSE, iterLs, lambda, frame, modelFolder);

		long elapsedTime = System.currentTimeMillis() - start;
		time += Math.round(elapsedTime/1000);
		if (message.contains("R^2")) {
			message += "\n" + time;
			JOptionPane.showMessageDialog(mainFrame, message, "Results",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(mainFrame, message, "Results",
					JOptionPane.ERROR_MESSAGE);
		}
		mainFrame.setEnabled(true);
		frame.setVisible(false);
	}
}
