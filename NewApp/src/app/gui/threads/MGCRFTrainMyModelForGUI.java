package app.gui.threads;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import app.algorithms.matlab.MGCRF;
import app.file.io.Reader;
import app.gui.frames.ProgressBar;

public class MGCRFTrainMyModelForGUI extends Thread {
	private ProgressBar frame;
	private JFrame mainFrame;

	private double s[][];
	private double r[][];
	private double y[][];
	private String modelFolder;
	private String time;
	private String matlabPath;
	private int noTime;
	private int training;
	private int maxIter;
	private int regAlpha;
	private int regBeta;
	private long proxyTime;

	public MGCRFTrainMyModelForGUI(String matlabPath, String modelFolder,
			ProgressBar frame, JFrame mainFrame, double[][] s, double[][] r,
			double[][] y, int noTime, int training, int maxIter, int regAlpha,
			int regBeta, long proxyTime) {
		super();
		this.frame = frame;
		this.mainFrame = mainFrame;
		this.s = s;
		this.r = r;
		this.y = y;
		this.modelFolder = modelFolder;
		this.matlabPath = matlabPath;
		this.noTime = noTime;
		this.training = training;
		this.maxIter = maxIter;
		this.regAlpha = regAlpha;
		this.regBeta = regBeta;
		this.proxyTime = proxyTime;
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
		frame.setTitle("Please wait: m-GCRF is in progress ");
		long start = System.currentTimeMillis();

		String message = MGCRF.train(matlabPath, s, y, r, noTime, training,
				maxIter, regAlpha, regBeta, frame, modelFolder,proxyTime);

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
