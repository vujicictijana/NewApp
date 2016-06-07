package app.gui.threads;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import app.algorithms.asymmetric.AlgorithmAsymmetric;
import app.algorithms.asymmetric.GradientDescentAsymmetric;
import app.algorithms.symmetric.AlgorithmSymmetric;
import app.algorithms.symmetric.CalculationsSymmetric;
import app.algorithms.symmetric.GradientDescentSymmetric;
import app.data.generators.GraphGenerator;
import app.file.io.Writer;
import app.gui.frames.ProgressBar;
import app.gui.style.Style;

public class TrainMyModelForGUI extends Thread {
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
	private boolean both;
	private String modelFolder;
	private int xTable;
	private int yTable;

	public TrainMyModelForGUI(String modelFolder, ProgressBar frame, JFrame mainFrame,
			double[][] s, double[] r, double[] y, double alpha, double beta,
			double lr, int maxIter, JPanel panel, boolean both, int xTable,
			int yTable) {
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
		this.both = both;
		this.modelFolder = modelFolder;
		this.xTable = xTable;
		this.yTable = yTable;
	}

	public void run() {
		mainFrame.setEnabled(false);
		frame.setTitle("Progress Asymmetric");
		GradientDescentAsymmetric gda = new GradientDescentAsymmetric(alpha,
				beta, lr, s, r, y);
		double[] res = gda.learn(maxIter, false, frame.getCurrent());

		AlgorithmAsymmetric alg = new AlgorithmAsymmetric(res[0], res[1], s, r,
				y);
		double r2 = alg.rSquared();
		double[] resS = null;
		double r2S = -1;
		if (both) {
			frame.getCurrent().setValue(0);
			frame.setTitle("Progress Symmetric");
			double[][] sS = GraphGenerator.converteGraphToUndirected(s);
			GradientDescentSymmetric gdS = new GradientDescentSymmetric(alpha,
					beta, lr, sS, r, y);
			resS = gdS.learn(maxIter, false, frame.getCurrent());

			AlgorithmSymmetric algS = new AlgorithmSymmetric(resS[0], resS[1],
					sS, r, y);
			r2S = algS.rSquared();
		}
		createTable(res, r2, resS, r2S);
		createFile("Asymmetric.txt", res);
		if (resS != null) {
			createFile("Symmetric.txt", resS);
		}
		mainFrame.setEnabled(true);
		frame.setVisible(false);
	}

	public JTable createTable(double[] res, double r2, double[] resS, double r2S) {
		String[] columnNames = { "Alg. ", "Alpha", "Beta",
				"R^2 (with same data)" };
		Object[][] data = fillData(res, r2, resS, r2S);

		JTable table = new JTable(data, columnNames);

		table.setBackground(new Color(240, 240, 240));
		panel.removeAll();
		panel.repaint();
		panel.revalidate();
		JScrollPane scrollPane = new JScrollPane(table);
		Style.resultTable(table, -1);
		panel.add(scrollPane);
		if (resS == null) {
			scrollPane.setBounds(xTable, yTable, 700, 50);
		} else {
			scrollPane.setBounds(xTable, yTable, 700, 75);
		}
		return table;
	}

	public void createFile(String symmetric, double[] results) {
		Writer.createFolder(modelFolder);
		String fileName = modelFolder + "/" + symmetric;
		String[] resultsS = { results[0] + "", results[1] + "" };
		Writer.write(fileName, resultsS);
	}

	public Object[][] fillData(double[] res, double r2, double[] resS,
			double r2S) {
		Object[][] data = null;
		if (resS == null) {
			data = new Object[1][4];
			data[0][0] = "Asymmetric";
			data[0][1] = res[0];
			data[0][2] = res[1];
			data[0][3] = r2;
		} else {
			data = new Object[2][4];
			data[0][0] = "Asymmetric";
			data[0][1] = res[0];
			data[0][2] = res[1];
			data[0][3] = r2;
			data[1][0] = "Symmetric";
			data[1][1] = resS[0];
			data[1][2] = resS[1];
			data[1][3] = r2S;
		}
		return data;
	}
}
