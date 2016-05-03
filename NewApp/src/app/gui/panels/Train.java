package app.gui.panels;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import app.algorithms.asymmetric.AlgorithmAsymmetric;
import app.algorithms.asymmetric.GradientDescentAsymmetric;
import app.gui.frames.ProgressBar;
import app.gui.style.Style;

public class Train extends Thread {
	private ProgressBar frame;
	private JFrame mainFrame;

	private double s[][];
	private double r[];
	private double y[];
	private double alpha;
	private double beta;
	private double lr;
	private int maxIter;
	private JPanel panel;

	public Train(ProgressBar frame, JFrame mainFrame, double[][] s, double[] r,
			double[] y, double alpha, double beta, double lr, int maxIter,
			JPanel panel) {
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
		this.panel = panel;
	}

	public void run() {
		mainFrame.setEnabled(false);
		GradientDescentAsymmetric gda = new GradientDescentAsymmetric(alpha,
				beta, lr, s, r, y);
		double[] res = gda.learn(maxIter, false, frame.getCurrent());

		AlgorithmAsymmetric alg = new AlgorithmAsymmetric(res[0], res[1], s, r,
				y);
		double r2 = alg.rSquared();
		createTable(res, r2);
		mainFrame.setEnabled(true);
		frame.setVisible(false);
	}

	public JTable createTable(double[] res, double r2) {
		String[] columnNames = { "Alpha", "Beta", "R^2 (with same data)" };

		Object[][] data = { { res[0], res[1], r2 } };

		JTable table = new JTable(data, columnNames);
	
		table.setBackground(new Color(240, 240, 240));
		JScrollPane scrollPane = new JScrollPane(table);
		Style.resultTable(table);
		panel.add(scrollPane);
		scrollPane.setBounds(50, 420, 700, 50);
		return table;
	}
}
