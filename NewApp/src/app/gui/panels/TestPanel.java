package app.gui.panels;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import app.algorithms.basic.BasicCalcs;
import app.exceptions.ConfigurationParameterseException;
import app.file.io.Reader;
import app.file.io.Writer;
import app.gui.frames.MainFrame;
import app.gui.frames.ProgressBar;
import app.gui.style.Style;
import app.gui.threads.GCRFTestMyModelForGUI;
import app.gui.threads.DirGCRFTestMyModelForGUI;
import app.gui.threads.UmGCRFTestMyModelForGUI;
import app.predictors.helper.Helper;
import app.predictors.linearregression.LinearRegression;
import app.predictors.linearregression.MultivariateLinearRegression;
import app.predictors.neuralnetwork.MyNN;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.Map;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JComboBox;

import org.neuroph.core.data.DataSet;

public class TestPanel extends JPanel {

	private static final long serialVersionUID = 356011421979477981L;
	private JButton btnTrain;
	private JPanel panel;
	private JFrame mainFrame;
	private JPanel panelForTable;
	private JLabel label_1;
	private JTextField txtModelName;
	private JFileChooser fc;
	private JLabel lblMethod;
	private JComboBox<String> cmbMethod;

	// params
	private String matlabPath;
	private boolean useMatlab;
	private long proxy;
	private JLabel label;
	private JComboBox<String> cmbDataset;
	

	private String xPath = "";
	private String yPath = "";
	private String sPath = "";

	/**
	 * Create the panel.
	 */
	public TestPanel(JFrame mainFrame) {
		readParametersFromCfg();
		setBackground(UIManager.getColor("Button.background"));
		setLayout(null);
		add(getBtnTrain());
		panel = this;
		this.mainFrame = mainFrame;
		add(getPanelForTable());
		add(getLabel_1_1());
		add(getTxtModelName());
		panel = this;
		add(getLblMethod());
		add(getCmbMethod());
		add(getLabel());
		add(getCmbDataset());
		fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		fc.setFileFilter(filter);
	}

	private JButton getBtnTrain() {
		if (btnTrain == null) {
			btnTrain = new JButton("TEST");
			btnTrain.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String message = validateData();
					if (message != null) {
						JOptionPane.showMessageDialog(mainFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						String mainPathDatasets = Reader.jarFile() 
								+ "/Datasets/Networks/";

						xPath = mainPathDatasets + "/"
								+ cmbDataset.getSelectedItem().toString()
								+ "/xTest.txt";
						yPath = mainPathDatasets + "/"
								+ cmbDataset.getSelectedItem().toString()
								+ "/yTest.txt";
						sPath = mainPathDatasets + "/"
								+ cmbDataset.getSelectedItem().toString()
								+ "/sTest.txt";

						String readme = mainPathDatasets + "/"
								+ cmbDataset.getSelectedItem().toString()
								+ "/readme.txt";
						int noOfNodes = 0;
						try {
							String nodesTrain = Reader.read(readme)[1];
							noOfNodes = Integer.parseInt(nodesTrain
									.substring(nodesTrain.indexOf(":") + 2));
							if (noOfNodes <= 0) {
								JOptionPane
										.showMessageDialog(
												mainFrame,
												"No. of nodes should be greater than 0.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
								return;
							}
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(mainFrame,
									"Reading dataset error.", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						String method = cmbMethod.getSelectedItem().toString();

						Writer.createFolder(Reader.jarFile() + "/MyModels" + method);
						String dataPath = Reader.jarFile()  + "/MyModels" + method + "/" + txtModelName.getText();
						File matrixFile = new File(sPath);
						Writer.copyFile(matrixFile, dataPath + "/data/matrixTest.txt");
						File xFile = new File(xPath);
						Writer.copyFile(xFile, dataPath + "/data/xTest.txt");
						File yFile = new File(yPath);
						Writer.copyFile(yFile, dataPath + "/data/yTest.txt");

						String[] x = Reader.read(xPath);
						if (x == null) {
							JOptionPane.showMessageDialog(mainFrame, "Reading file error.", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}

						if (x.length != noOfNodes) {
							JOptionPane.showMessageDialog(mainFrame,
									"Number of lines in the file with attributes should be " + noOfNodes + ".", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						double[] y = Reader.readArray(yPath, noOfNodes);
						if (y == null) {
							JOptionPane.showMessageDialog(mainFrame,
									"Number of lines in in the file with outputs should be " + noOfNodes + ".", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						double result = callPredictor(dataPath, x, y);
						if (result == -9000) {
							JOptionPane.showMessageDialog(mainFrame, "Selected dataset is not suitable for this model.", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (result == -7000) {
							JOptionPane.showMessageDialog(mainFrame, "Unknown predictor.", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (result == -3000) {
							JOptionPane.showMessageDialog(mainFrame,
									"Predictor cannot be applied to your data. Choose different predictor.", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (result == -5000) {
							JOptionPane.showMessageDialog(mainFrame, "File with attributes is not in correct format.",
									"Error", JOptionPane.ERROR_MESSAGE);
						} else {
							double[] r = Reader.readArray(dataPath + "/data/rTest.txt", noOfNodes);
							double[][] s = Reader.readGraph(sPath, noOfNodes);

							if (s == null) {
								JOptionPane.showMessageDialog(mainFrame,
										"Ordinal number of node can be between 1 and " + noOfNodes + ".", "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}

							callMethod(noOfNodes, method, dataPath, y, r, s);

						}

					}
				}

			});

			Style.buttonStyle(btnTrain);
			btnTrain.setBounds(328, 175, 112, 45);
		}
		return btnTrain;
	}

	private void testDirGCRF(int noOfNodes, String modelFolder, double[] r, double[] y, double[][] s) {
		DirGCRFTestMyModelForGUI test = new DirGCRFTestMyModelForGUI(mainFrame, panelForTable, modelFolder, s, r, y);
		test.start();
	}

	private void testGCRF(int noOfNodes, String modelFolder, double[] r, double[] y, double[][] s) {
		GCRFTestMyModelForGUI test = new GCRFTestMyModelForGUI(mainFrame, panelForTable, modelFolder, s, r, y);
		test.start();
	}

	private void testUmGCRF(String modelFolder, double[] r, double[] y, double[][] s) {
		ProgressBar frame = new ProgressBar("Testing");
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		UmGCRFTestMyModelForGUI test = new UmGCRFTestMyModelForGUI(matlabPath, mainFrame, panelForTable, modelFolder, s,
				r, y, frame, proxy);
		test.start();
	}

	private void callMethod(int noOfNodes, String method, String dataPath, double[] y, double[] r, double[][] s) {

		switch (method) {
		case "DirGCRF":
			testDirGCRF(noOfNodes, dataPath, r, y, s);
			break;
		case "GCRF":
			if (BasicCalcs.isSymmetric(s)) {
				testGCRF(noOfNodes, dataPath, r, y, s);
			} else {
				JOptionPane.showMessageDialog(mainFrame, "For GCRF method matrix should be symmetric.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "UmGCRF":
			if (BasicCalcs.isSymmetric(s)) {
				testUmGCRF(dataPath, r, y, s);
			} else {
				JOptionPane.showMessageDialog(mainFrame, "For UmGCRF method matrix should be symmetric.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			break;
		default:
			JOptionPane.showMessageDialog(mainFrame, "Unknown method.", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	public String validateData() {
		if (cmbDataset.getSelectedIndex() == 0) {
			return "Choose dataset.";
		}
		if (txtModelName.getText().equals("")) {
			return "Insert model name.";
		}
		String method = cmbMethod.getSelectedItem().toString();

		if (!Writer.checkFolder(Reader.jarFile()  + "/MyModels"  + method + "/" + txtModelName.getText())) {
			return "Model with name " + txtModelName.getText() + " does not exist.";
		}
		return null;
	}

	public boolean checkModel(String path) {
		return Writer.checkFolder(path);
	}

	private JPanel getPanelForTable() {
		if (panelForTable == null) {
			panelForTable = new JPanel();
			panelForTable.setBounds(28, 347, 850, 63);
			panelForTable.setLayout(null);
		}
		return panelForTable;
	}

	private JLabel getLabel_1_1() {
		if (label_1 == null) {
			label_1 = new JLabel("Model name:");
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);
			label_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_1.setBounds(61, 109, 100, 30);
		}
		return label_1;
	}

	private JTextField getTxtModelName() {
		if (txtModelName == null) {
			txtModelName = new JTextField();
			txtModelName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtModelName.setColumns(10);
			txtModelName.setBounds(177, 109, 315, 30);
		}
		return txtModelName;
	}

	private double callPredictor(String path, String[] x, double[] y) {

		if (Writer.checkFolder(path + "/nn")) {

			DataSet testSet = Helper.prepareDataForNN(x, y);
			
			return MyNN.test(path, testSet);

		}
		if (Writer.checkFolder(path + "/mlr")) {
			double[][] xMlr = Helper.prepareDataForLR(x);
			MultivariateLinearRegression m = (MultivariateLinearRegression) Helper.deserilazie(path + "/mlr/lr.txt");
			return m.test(y, xMlr, path, true);
		}
		if (Writer.checkFolder(path + "/lr")) {
			double[][] xMlr = Helper.prepareDataForLR(x);
			double[] xOne = new double[xMlr.length];
			for (int i = 0; i < xOne.length; i++) {
				xOne[i] = xMlr[i][0];
			}
			LinearRegression lr = (LinearRegression) Helper.deserilazie(path + "/lr/lr.txt");
			return LinearRegression.test(y, xOne, path, lr, true);
		}
		return -7000;

	}

	private JLabel getLblMethod() {
		if (lblMethod == null) {
			lblMethod = new JLabel("Method:");
			lblMethod.setHorizontalAlignment(SwingConstants.RIGHT);
			lblMethod.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblMethod.setBounds(62, 27, 100, 30);
		}
		return lblMethod;
	}

	public String readParametersFromCfg() {
		try {
			Map<String, String> params = Reader.readCfg();

			if (params.get("Use MATLAB").toString().contains("true")) {
				useMatlab = true;
			} else {
				useMatlab = false;
			}
			matlabPath = params.get("Path").toString();
			proxy = Integer.parseInt(params.get("Proxy").toString());
		} catch (ConfigurationParameterseException e) {
			return e.getMessage();
		}
		return null;
	}

	private JComboBox<String> getCmbMethod() {
		if (cmbMethod == null) {
			cmbMethod = new JComboBox<String>();
			cmbMethod.setBounds(178, 27, 314, 30);
			cmbMethod.addItem("choose method");
			cmbMethod.addItem("GCRF");
			cmbMethod.addItem("DirGCRF");
			if (useMatlab) {
				cmbMethod.addItem("UmGCRF");
			}
		}
		return cmbMethod;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("Dataset:");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label.setBounds(41, 68, 120, 30);
		}
		return label;
	}
	private JComboBox<String> getCmbDataset() {
		if (cmbDataset == null) {
			cmbDataset = new JComboBox();
			cmbDataset.setBounds(177, 71, 315, 30);
			cmbDataset.addItem("choose dataset");
			String[] files = Reader.getAllFolders(Reader.jarFile()  + "/Datasets/Networks");
			for (int i = 0; i < files.length; i++) {
				cmbDataset.addItem(files[i]);
			}
		}
		return cmbDataset;
	}
}
