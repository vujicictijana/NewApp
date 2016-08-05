package app.gui.threads;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import app.algorithms.asymmetric.AlgorithmAsymmetric;
import app.algorithms.symmetric.AlgorithmSymmetric;
import app.file.io.Reader;
import app.file.io.Writer;
import app.gui.style.Style;

public class GCRFTestMyModelForGUI extends Thread {
	private JFrame mainFrame;
	private JPanel panel;
	private String modelFolder;
	private double[][] s;
	private double[] r;
	private double[] y;
	public double[] outputs;
	DecimalFormat df = new DecimalFormat("#.######");

	public GCRFTestMyModelForGUI(JFrame mainFrame, JPanel panel,
			String modelFolder, double[][] s, double[] r, double[] y) {
		super();
		this.mainFrame = mainFrame;
		this.panel = panel;
		this.modelFolder = modelFolder;
		this.s = s;
		this.r = r;
		this.y = y;
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
	}

	public void run() {
		mainFrame.setEnabled(false);
		double[] paramS = read(modelFolder + "/GCRF.txt");

		double resultS = resultSymmetric(paramS[0], paramS[1]);

		createTable(resultS);

		mainFrame.setEnabled(true);
	}

	public double[] read(String file) {
		String[] txt = Reader.read(file);
		if (txt != null) {
			double[] params = new double[txt.length];
			for (int i = 0; i < txt.length; i++) {
				params[i] = Double.parseDouble(txt[i]);
			}
			return params;
		}
		return null;
	}

	public double resultSymmetric(double alpha, double beta) {
		AlgorithmSymmetric alg = new AlgorithmSymmetric(alpha, beta, s, r, y);
		outputs = alg.outputs();
		return alg.rSquared();
	}

	public JTable createTable(double resultS) {
		JTable table = null;
		String[] columnNames = { "R^2 GCRF" };
		Object[][] data = new Object[1][1];
		data[0][0] = df.format(resultS);
		table = new JTable(data, columnNames);

		table.setBackground(new Color(240, 240, 240));
		JScrollPane scrollPane = new JScrollPane(table);
		Style.resultTable(table, -1);
		panel.add(scrollPane);
		scrollPane.setBounds(10, 10, 700, 200);
		JButton export = new JButton();
		panel.add(export);
		export.setBounds(720, 10, 80, 30);
		export.setText("Export");
		Style.buttonStyle(export);
		export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Writer.createFolder(modelFolder + "/Test");
				String fileName = modelFolder + "/Test/results.txt";
				String[] text = exportTxt(resultS);
				Writer.write(text, fileName);
				JOptionPane.showMessageDialog(mainFrame,
						"Export successfully completed.");
			}
		});
		return table;
	}

	public String[] exportTxt(double resultS) {
		String[] txt = new String[outputs.length + 1];

		for (int i = 0; i < outputs.length; i++) {
			txt[i] = outputs[i] + "";
		}

		txt[outputs.length] = "R^2 GCRF: " + df.format(resultS);

		return txt;
	}

}
