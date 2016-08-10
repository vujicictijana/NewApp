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

public class DirGCRFTestMyModelForGUI extends Thread {
	private JFrame mainFrame;
	private JPanel panel;
	private String modelFolder;
	private double[][] s;
	private double[] r;
	private double[] y;
	public double[] outputs;
	DecimalFormat df = new DecimalFormat("#.######");

	public DirGCRFTestMyModelForGUI(JFrame mainFrame, JPanel panel,
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
		double[] param = read(modelFolder + "/DirGCRF.txt");
		double result = resultAsymmetric(param[0], param[1]);
		// System.out.println("A " + param[0] + " " + param[1]);
		double[] paramS = read(modelFolder + "/GCRF.txt");
		double resultS = -1;
		if (paramS != null) {
			resultS = resultSymmetric(paramS[0], paramS[1]);
			// System.out.println("S " + paramS[0] + " " + paramS[1]);
			createTable(result, resultS);
		} else {
			createTable(result, -1);
		}
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

	public double resultAsymmetric(double alpha, double beta) {
		AlgorithmAsymmetric alg = new AlgorithmAsymmetric(alpha, beta, s, r, y);
		// System.out.println(alg.rSquared());
		outputs = alg.outputs();
		return alg.rSquared();
	}

	public double resultSymmetric(double alpha, double beta) {
		AlgorithmSymmetric alg = new AlgorithmSymmetric(alpha, beta, s, r, y);
		// System.out.println(alg.rSquared());
		return alg.rSquared();
	}

	public JTable createTable(double result, double resultS) {
		JTable table = null;
		if (resultS != -1) {
			String[] columnNames = { "R^2 DirGCRF", "R^2 GCRF" };
			Object[][] data = fillData(result, resultS);
			table = new JTable(data, columnNames);
		} else {
			String[] columnNames = { "R^2 DirGCRF" };
			Object[][] data = fillData(result, resultS);
			table = new JTable(data, columnNames);
		}
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
				String[] text = exportTxt(result, resultS);
				Writer.write(text, fileName);
				JOptionPane.showMessageDialog(mainFrame,
						"Export successfully completed.");
			}
		});
		return table;
	}

	public String[] exportTxt(double result, double resultS) {
		String[] txt = null;
		
		if (resultS != -1) {
			txt = new String[outputs.length + 2];
		} else {
			txt = new String[outputs.length + 1];
		}
		for (int i = 0; i < outputs.length; i++) {
			txt[i] = outputs[i]+"";
		}
		
		if (resultS != -1) {
			txt[outputs.length] = "R^2 DirGCRF: " + df.format(result);
			txt[outputs.length+1] = "R^2 GCRF: " + df.format(resultS);
		} else {
			txt[outputs.length] = "R^2 DirGCRF: " + df.format(result);
		}
		return txt;
	}

	public Object[][] fillData(double result, double resultS) {
		Object[][] data;
		if (resultS != -1) {
			data = new Object[1][2];
			data[0][0] = df.format(result);
			data[0][1] = df.format(resultS);

		} else {
			data = new Object[1][1];
			data[0][0] = df.format(result);
		}
		return data;
	}
}