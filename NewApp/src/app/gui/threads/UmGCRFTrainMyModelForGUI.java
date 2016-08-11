package app.gui.threads;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import app.algorithms.matlab.UmGCRF;
import app.gui.frames.ProgressBar;

public class UmGCRFTrainMyModelForGUI extends Thread {
	private ProgressBar frame;
	private JFrame mainFrame;

	private double s[][];
	private double r[];
	private double y[];
	private String modelFolder;
	private String time;

	public UmGCRFTrainMyModelForGUI(String modelFolder, ProgressBar frame,
			JFrame mainFrame, double[][] s, double[] r, double[] y) {
		super();
		this.frame = frame;
		this.mainFrame = mainFrame;
		this.s = s;
		this.r = r;
		this.y = y;
		this.modelFolder = modelFolder;
		time = "Time in milis: ";
	}

	public void run() {
		mainFrame.setEnabled(false);
		frame.setTitle("Please wait - UmGCRF is in progress ");
		long start = System.currentTimeMillis();

		String message = UmGCRF.train(s, y, r, frame);

		long elapsedTime = System.currentTimeMillis() - start;
		time += "\n* UmGCRF: " + elapsedTime;
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
