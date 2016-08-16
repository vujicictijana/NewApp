package app.gui.threads;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import app.algorithms.matlab.MGCRF;
import app.algorithms.matlab.UpGCRF;
import app.file.io.Reader;
import app.gui.frames.ProgressBar;

public class UpGCRFTrainMyModelForGUI extends Thread {
	private ProgressBar frame;
	private JFrame mainFrame;

	private double s[][];
	private double x[][];
	private double y[][];
	private String modelFolder;
	private String time;
	private String matlabPath;
	private int noTime;
	private int training;
	private int maxIter;
	private int noOfNodes;
	private int lag;
	private int noX;
	private boolean useX;

	public UpGCRFTrainMyModelForGUI(String matlabPath, String modelFolder,
			ProgressBar frame, JFrame mainFrame, double[][] s, double[][] r,
			double[][] y, int noTime, int training, int maxIter, int noOfNodes,
			int lag, int noX, boolean useX) {
		super();
		this.frame = frame;
		this.mainFrame = mainFrame;
		this.s = s;
		this.x = r;
		this.y = y;
		this.modelFolder = modelFolder;
		this.matlabPath = matlabPath;
		this.noTime = noTime;
		this.training = training;
		this.maxIter = maxIter;
		this.noOfNodes = noOfNodes;
		this.lag = lag;
		this.noX = noX;
		this.useX = useX;
		time = "* Time in milis: ";
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
		frame.setTitle("Please wait: up-GCRF is in progress ");
		long start = System.currentTimeMillis();

		String message = UpGCRF.train(matlabPath, s, y, x, noTime, training,
				maxIter, noOfNodes, lag, noX, useX, frame, modelFolder);

		long elapsedTime = System.currentTimeMillis() - start;
		time += elapsedTime;
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
