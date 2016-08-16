package app.gui.panels;

import javax.imageio.ImageIO;
import javax.jws.WebParam.Mode;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.SwingConstants;

import app.algorithms.basic.BasicCalcs;
import app.algorithms.matlab.UmGCRF;
import app.data.generators.GraphGenerator;
import app.exceptions.ConfigurationParameterseException;
import app.file.io.Reader;
import app.file.io.Writer;
import app.gui.frames.ProgressBar;
import app.gui.style.Style;
import app.gui.threads.DirGCRFTrainMyModelForGUI;
import app.gui.threads.GCRFTrainMyModelForGUI;
import app.gui.threads.MGCRFTrainMyModelForGUI;
import app.gui.threads.UmGCRFTrainMyModelForGUI;
import app.gui.threads.UpGCRFTrainMyModelForGUI;
import app.predictors.helper.Helper;
import app.predictors.linearregression.LinearRegression;
import app.predictors.linearregression.MultivariateLinearRegression;
import app.predictors.linearregression.MyLR;
import app.predictors.linearregression.TemporalData;
import app.predictors.neuralnetwork.MyNN;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JSeparator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComboBox;

import org.neuroph.core.data.DataSet;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class TrainTemporalPanel extends JPanel {

	private static final long serialVersionUID = 7542338256284881226L;
	private JTextField txtMatrixFile;
	private JLabel lblSFile;
	private JButton btnBrowseS;
	private JLabel lblModelName;
	private JTextField txtModelName;
	private JLabel lblRArrayFile;
	private JTextField txtXFile;
	private JButton btnBrowseX;
	private JLabel lblAlpha;
	private JLabel lblBeta;
	private JTextField txtAlpha;
	private JTextField txtBeta;
	private JButton btnQuestionX;
	private JLabel lblYArrayFile;
	private JTextField txtYFile;
	private JButton btnBrowseY;
	private JButton btnQuestionY;
	private JButton btnTrain;
	private JLabel lblMaxIterations;
	private JTextField txtIter;
	private JFileChooser fc;
	private JPanel panel;
	private JFrame mainFrame;
	private JLabel label;
	private JTextField txtNoOfNodes;
	// private JPanel panelForTable;
	// private JLabel lblTime;

	// params

	private int alphaReg;
	private int betaReg;
	private int iterMGCRF;
	private int hidden;
	private int iterNN;
	private String matlabPath;
	private boolean useMatlab;

	private JLabel lblData;
	private JLabel lblModel;
	private JLabel lblPredictor_1;
	private JComboBox<String> cmbPredictor;
	private JLabel lblNoOfHidden;
	private JTextField txtHidden;
	private JLabel lblNoOfIterations;
	private JTextField txtIterNN;
	private JComboBox<String> cmbMethod;
	private JLabel lblMethod;
	private JLabel lblNoOfTime;
	private JTextField txtNoTime;
	private JTextField txtNoTimeTrain;
	private JLabel lblNoOfTime_1;
	private JCheckBox chkLearn;
	private JButton btnQuestionS;
	private JButton btnQuestionRegAlpha;
	private JLabel lblNoOfAttributres;
	private JTextField txtNoX;
	private JLabel lblProvideTrainAn;
	private JLabel lblLag;
	private JTextField txtLag;
	private JButton btnQuestionLag;
	private JCheckBox chckUseX;

	public TrainTemporalPanel(JFrame mainFrame) {
		setBounds(new Rectangle(0, 0, 900, 650));
		setMinimumSize(new Dimension(500, 500));
		readParametersFromCfg();
		// if (Reader.checkFile("cfg.txt")) {
		// String result = readParametersFromCfg();
		// if (result != null) {
		// JOptionPane
		// .showMessageDialog(
		// mainFrame,
		// result
		// + " Please configure parameters values in Settings->Configuration.",
		// "Error", JOptionPane.ERROR_MESSAGE);
		// } else {
		setBackground(UIManager.getColor("Button.background"));
		setLayout(null);
		add(getTxtMatrixFile());
		add(getLblSFile());
		add(getBtnBrowseS());
		add(getLblModelName());
		add(getTxtModelName());
		add(getLblRArrayFile());
		add(getTxtXFile());
		add(getBtnBrowseX());
		add(getLblAlpha());
		add(getLblBeta());
		add(getTxtAlpha());
		add(getTxtBeta());
		add(getBtnQuestionX());
		add(getLblYArrayFile());
		add(getTxtYFile());
		add(getBtnBrowseY());
		add(getBtnQuestionY());
		add(getBtnTrain());
		add(getLblMaxIterations());
		add(getTxtMaxIter());
		fc = new JFileChooser();
		panel = this;
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"TEXT FILES", "txt", "text");
		fc.setFileFilter(filter);
		this.mainFrame = mainFrame;
		// add(getLblTime());
		add(getLabel());
		add(getTxtNoOfNodes());
		add(getLblData());
		add(getLblModel());
		add(getLblPredictor_1());
		add(getLblNoOfHidden());
		add(getTxtHidden());

		add(getLblNoOfIterations());
		add(getTxtIterNN());
		setTxtValues();
		add(getCmbPredictor());
		add(getCmbMethod());
		add(getLblMethod());
		add(getLblNoOfTime());
		add(getTxtNoTime());
		add(getTxtNoTimeTrain());
		add(getLblNoOfTime_1());
		add(getChkLearn());
		add(getBtnQuestionS());
		add(getBtnQuestionRegAlpha());
		add(getLblNoOfAttributres());
		add(getTxtNoX());
		add(getLblProvideTrainAn());
		add(getLblLag());
		add(getTxtLag());
		add(getBtnQuestionLag());
		add(getChckUseX());
		// }
		// } else {
		// JOptionPane
		// .showMessageDialog(
		// mainFrame,
		// "Please configure parameters values in Settings->Configuration.",
		// "Error", JOptionPane.ERROR_MESSAGE);
		// }
	}

	private JTextField getTxtMatrixFile() {
		if (txtMatrixFile == null) {
			txtMatrixFile = new JTextField();
			txtMatrixFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtMatrixFile.setBounds(237, 285, 315, 30);
			txtMatrixFile.setColumns(10);
		}
		return txtMatrixFile;
	}

	private JLabel getLblSFile() {
		if (lblSFile == null) {
			lblSFile = new JLabel("File with edges:");
			lblSFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblSFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblSFile.setBounds(104, 285, 120, 30);
		}
		return lblSFile;
	}

	private JButton getBtnBrowseS() {
		if (btnBrowseS == null) {
			btnBrowseS = new JButton("Browse");
			btnBrowseS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					chooseFile(txtMatrixFile);
				}
			});
			Style.buttonStyle(btnBrowseS);
			btnBrowseS.setBounds(569, 285, 100, 30);

		}
		return btnBrowseS;
	}

	private JLabel getLblModelName() {
		if (lblModelName == null) {
			lblModelName = new JLabel("Model name:");
			lblModelName.setHorizontalAlignment(SwingConstants.RIGHT);
			lblModelName.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblModelName.setBounds(124, 326, 100, 30);
		}
		return lblModelName;
	}

	private JTextField getTxtModelName() {
		if (txtModelName == null) {
			txtModelName = new JTextField();
			txtModelName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtModelName.setColumns(10);
			txtModelName.setBounds(237, 326, 315, 30);
		}
		return txtModelName;
	}

	private JLabel getLblRArrayFile() {
		if (lblRArrayFile == null) {
			lblRArrayFile = new JLabel("File with attributes:");
			lblRArrayFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblRArrayFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblRArrayFile.setBounds(84, 205, 140, 30);
		}
		return lblRArrayFile;
	}

	private JTextField getTxtXFile() {
		if (txtXFile == null) {
			txtXFile = new JTextField();
			txtXFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtXFile.setColumns(10);
			txtXFile.setBounds(237, 207, 315, 30);
		}
		return txtXFile;
	}

	private JButton getBtnBrowseX() {
		if (btnBrowseX == null) {
			btnBrowseX = new JButton("Browse");
			btnBrowseX.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					chooseFile(txtXFile);
				}
			});
			Style.buttonStyle(btnBrowseX);
			btnBrowseX.setBounds(569, 207, 100, 30);
		}
		return btnBrowseX;
	}

	private JLabel getLblAlpha() {
		if (lblAlpha == null) {
			lblAlpha = new JLabel("Regularization alpha:");
			lblAlpha.setVisible(false);
			lblAlpha.setHorizontalAlignment(SwingConstants.RIGHT);
			lblAlpha.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblAlpha.setBounds(31, 448, 197, 30);
		}
		return lblAlpha;
	}

	private JLabel getLblBeta() {
		if (lblBeta == null) {
			lblBeta = new JLabel("Regularization beta:");
			lblBeta.setVisible(false);
			lblBeta.setHorizontalAlignment(SwingConstants.RIGHT);
			lblBeta.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblBeta.setBounds(21, 488, 207, 30);
		}
		return lblBeta;
	}

	private JTextField getTxtAlpha() {
		if (txtAlpha == null) {
			txtAlpha = new JTextField();
			txtAlpha.setVisible(false);
			txtAlpha.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAlpha.setColumns(10);
			txtAlpha.setBounds(244, 449, 91, 30);
		}
		return txtAlpha;
	}

	private JTextField getTxtBeta() {
		if (txtBeta == null) {
			txtBeta = new JTextField();
			txtBeta.setVisible(false);
			txtBeta.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtBeta.setColumns(10);
			txtBeta.setBounds(244, 490, 91, 30);
		}
		return txtBeta;
	}

	private JButton getBtnQuestionX() {
		if (btnQuestionX == null) {
			btnQuestionX = new JButton("");
			btnQuestionX.setBounds(688, 207, 30, 30);
			Style.questionButtonStyle(btnQuestionX);
			btnQuestionX.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// "Text file (.txt) that contains output predicted by unstructured predictor for each node."
					// + "\nEach output should be in a separate line. "
					// +
					// "\nOrder of outputs should be consistent with ordinal numbers of nodes in the file with edges (S)."
					JOptionPane
							.showMessageDialog(
									mainFrame,
									"Text file (.txt) that contains value of each atribute for each node for each time point."
											+ "\nEach line should contain attributes for one node for all time points (comma separated). "
											+ "\nProvide all attributes for first time point, then for second time point, and so on. "
											+ "\nFor example if there are 3 attributes and 2 time points, one line should look like:"
											+ " a1.1, a2.1, a3.1, a2.1, a2.2, a2.3"
											+ "\nAll atributes should be numbers. ",
									"Help", JOptionPane.QUESTION_MESSAGE,
									Style.questionIcon());
				}
			});
		}
		return btnQuestionX;
	}

	private JLabel getLblYArrayFile() {
		if (lblYArrayFile == null) {
			lblYArrayFile = new JLabel("File with outputs:");
			lblYArrayFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblYArrayFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblYArrayFile.setBounds(94, 247, 130, 30);
		}
		return lblYArrayFile;
	}

	private JTextField getTxtYFile() {
		if (txtYFile == null) {
			txtYFile = new JTextField();
			txtYFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtYFile.setColumns(10);
			txtYFile.setBounds(237, 247, 315, 30);
		}
		return txtYFile;
	}

	private JButton getBtnBrowseY() {
		if (btnBrowseY == null) {
			btnBrowseY = new JButton("Browse");
			btnBrowseY.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					chooseFile(txtYFile);
				}
			});
			Style.buttonStyle(btnBrowseY);
			btnBrowseY.setBounds(569, 247, 100, 30);
		}
		return btnBrowseY;
	}

	private JButton getBtnQuestionY() {
		if (btnQuestionY == null) {
			btnQuestionY = new JButton("");
			btnQuestionY.setBounds(688, 247, 30, 30);
			Style.questionButtonStyle(btnQuestionY);
			btnQuestionY.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JOptionPane
							.showMessageDialog(
									mainFrame,
									"Text file (.txt) that contains actual output for each node for each time point."
											+ "\nEach line should contain outputs for one node for all time points (comma separated). "
											+ "\nOrder of arrays of outputs should be consistent with ordinal numbers of nodes in the file with attributes."
											+ "\nFor missing values put -9999.",
									"Help", JOptionPane.QUESTION_MESSAGE,
									Style.questionIcon());
				}
			});
		}
		return btnQuestionY;
	}

	private JButton getBtnTrain() {
		if (btnTrain == null) {
			btnTrain = new JButton("TRAIN");
			btnTrain.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					String message = validateData();
					if (message != null) {
						JOptionPane.showMessageDialog(mainFrame, message,
								"Error", JOptionPane.ERROR_MESSAGE);
					} else {

						int noOfNodes = Integer.parseInt(txtNoOfNodes.getText());
						int noOfTime = Integer.parseInt(txtNoTime.getText());
						int noOfTimeTrain = Integer.parseInt(txtNoTimeTrain
								.getText());
						int noOfX = Integer.parseInt(txtNoX.getText());

						String[] x = Reader.read(txtXFile.getText());
						String[] y = Reader.read(txtYFile.getText());
						double[][] s = null;
						if (!chkLearn.isSelected()) {
							s = Reader.readGraph(txtMatrixFile.getText(),
									noOfNodes);
						}
//						String message1 = checkFiles(noOfNodes, noOfTime,
//								noOfX, x, y, s);
//
//						if (message1 != null) {
//							JOptionPane.showMessageDialog(mainFrame, message1,
//									"Error", JOptionPane.ERROR_MESSAGE);
//							return;
//						}

						String path = createFolderAndSaveData();
						if (isMGCRF()) {
							trainTestMGCRF(noOfNodes, noOfTime, noOfTimeTrain,
									noOfX, x, y, s, path);
						} else {
							int maxIter = Integer.parseInt(txtIter.getText());
							int lag = Integer.parseInt(txtLag.getText());
							double[][] x1 = new double[1000][1000];
							double[][] yMatrix = Reader.readMatrix(path + "/data/y.txt",
									noOfNodes, noOfTime);
							trainUpGCRF(matlabPath, path, x1, yMatrix, s, noOfTime,
									noOfTimeTrain, maxIter, noOfNodes, lag);

						}

					}
				}

			});
			Style.buttonStyle(btnTrain);
			btnTrain.setBounds(349, 594, 112, 45);
		}
		return btnTrain;
	}

	public void trainMGCRF(String matlabPath, String modelFolder, double[][] r,
			double[][] y, double[][] s, int noTime, int training, int maxIter,
			int regAlpha, int regBeta) {
		ProgressBar frame = new ProgressBar("Training");
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		MGCRFTrainMyModelForGUI t = new MGCRFTrainMyModelForGUI(matlabPath,
				modelFolder, frame, frame, s, r, y, noTime, training, maxIter,
				regAlpha, regBeta);

		t.start();
	}

	public void trainUpGCRF(String matlabPath, String modelFolder,
			double[][] r, double[][] y, double[][] s, int noTime, int training,
			int maxIter, int noOfNodes, int lag) {
		ProgressBar frame = new ProgressBar("Training");
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		UpGCRFTrainMyModelForGUI t = new UpGCRFTrainMyModelForGUI(matlabPath,
				modelFolder, frame, frame, s, r, y, noTime, training, maxIter,
				noOfNodes, lag);

		t.start();
	}

	private String checkFiles(int noOfNodes, int noOfTime, int noOfX,
			String[] x, String[] y, double[][] s) {
		int totalX = noOfTime * noOfX;
		if (x == null) {
			return "Error while reading file with attributes.";
		}

		if (x.length != noOfNodes) {
			return "Number of lines in the file with attributes should be "
					+ noOfNodes + ".";
		}
		for (int i = 0; i < x.length; i++) {
			if (x[i].split(",").length != totalX) {
				return "Number of values in each line in the file with attributes should be equal to no. of attributes * no. of time points: "
						+ totalX;
			}
		}
		if (y == null) {
			return "Error while reading file with attributes.";
		}
		if (y.length != noOfNodes) {
			return "Number of lines in the file with outputs should be "
					+ noOfNodes + ".";
		}
		for (int i = 0; i < x.length; i++) {
			if (y[i].split(",").length != noOfTime) {
				return "Number of values in each line in the file with outputs should be equal to no. of time points: "
						+ noOfTime;
			}
		}
		if (s == null) {
			return "Ordinal number of node can be between 1 and " + noOfNodes
					+ ".";
		}
		return null;
	}

	private void trainTestMGCRF(int noOfNodes, int noOfTime, int noOfTimeTrain,
			int noOfX, String[] x, String[] y, double[][] s, String path) {
		double result = callPredictor(path, x, y, noOfX, noOfTime,
				noOfTimeTrain, noOfNodes);

		if (result == -7000) {
			JOptionPane.showMessageDialog(mainFrame, "Unknown predictor.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (result == -3000) {
			JOptionPane
					.showMessageDialog(
							mainFrame,
							cmbPredictor.getSelectedItem().toString()
									+ " cannot be applied to your data. Choose different predictor.",
							"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (result == -5000) {
			JOptionPane.showMessageDialog(mainFrame,
					"Files are not in correct format.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		if (result == -10000) {
			JOptionPane
					.showMessageDialog(
							mainFrame,
							"Values should be normalized (range from 0 to 1) for neural network.",
							"Error", JOptionPane.ERROR_MESSAGE);
		} else {
			double[][] r = Reader.readMatrixTwoFiles(path + "/data/r.txt", path
					+ "/data/rTest.txt", noOfNodes, noOfTime, noOfTimeTrain);
			double[][] yMatrix = Reader.readMatrix(path + "/data/y.txt",
					noOfNodes, noOfTime);
			if (BasicCalcs.isSymmetric(s)) {
				int maxIter = Integer.parseInt(txtIter.getText());
				int regAlpha = Integer.parseInt(txtAlpha.getText());
				int regBeta = Integer.parseInt(txtBeta.getText());
				trainMGCRF(matlabPath, path, r, yMatrix, s, noOfTime,
						noOfTimeTrain, maxIter, regAlpha, regBeta);
			} else {
				JOptionPane.showMessageDialog(mainFrame,
						"For m-GCRF method matrix should be symmetric.",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private String createFolderAndSaveData() {
		File matrixFile = null;
		if (!chkLearn.isSelected()) {
			matrixFile = new File(txtMatrixFile.getText());
		}
		File xFile = new File(txtXFile.getText());
		File yFile = new File(txtYFile.getText());
		String method = cmbMethod.getSelectedItem().toString();
		Writer.createFolder("MyModels" + method);
		String path = "MyModels" + method + "/" + txtModelName.getText();
		Writer.createFolder(path);
		String dataPath = "MyModels" + method + "/" + txtModelName.getText()
				+ "/data";
		Writer.createFolder(dataPath);
		Writer.copyFile(xFile, dataPath + "/x.txt");
		Writer.copyFile(yFile, dataPath + "/y.txt");
		if (matrixFile != null) {
			Writer.copyFile(matrixFile, dataPath + "/matrix.txt");
		}
		return path;
	}

	private double callPredictor(String path, String[] x, String[] y, int noX,
			int noT, int noTrain, int nodes) {
		if (cmbPredictor.getSelectedItem().toString().contains("neural")) {
			int noOfHidden = Integer.parseInt(txtHidden.getText());
			int noOfIter = Integer.parseInt(txtIterNN.getText());
			DataSet trainingSet = Helper.prepareTemporalDataForNN(x, y, noX,
					noT, true);
			if (trainingSet == null) {
				return -10000;
			}
			return MyNN.learnAndTest(noOfHidden, trainingSet, 0.003, noOfIter,
					path, noT, noTrain);
		}
		if (cmbPredictor.getSelectedItem().toString().contains("linear")) {
			TemporalData t = Helper.prepareTemporalDataForLR(x, y, noX, noT,
					nodes, noTrain);
			MyLR.learn(t.getxTrain(), t.getyTrain(), path);
			if (Writer.checkFolder(path + "/mlr")) {
				MultivariateLinearRegression m = (MultivariateLinearRegression) Helper
						.deserilazie(path + "/mlr/lr.txt");
				return m.test(t.getyTest(), t.getxTest(), path, true);
			}
			if (Writer.checkFolder(path + "/lr")) {
				double[][] xMlr = t.getxTest();
				double[] xOne = new double[xMlr.length];
				for (int i = 0; i < xOne.length; i++) {
					xOne[i] = xMlr[i][0];
				}
				LinearRegression lr = (LinearRegression) Helper
						.deserilazie(path + "/lr/lr.txt");
				return LinearRegression
						.test(t.getyTest(), xOne, path, lr, true);
			}
		}
		return -7000;

	}

	private JLabel getLblMaxIterations() {
		if (lblMaxIterations == null) {
			lblMaxIterations = new JLabel("Max. iterations:");
			lblMaxIterations.setVisible(false);
			lblMaxIterations.setHorizontalAlignment(SwingConstants.RIGHT);
			lblMaxIterations.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblMaxIterations.setBounds(96, 529, 132, 30);
		}
		return lblMaxIterations;
	}

	public String validateData() {

		try {
			int a = Integer.parseInt(txtNoOfNodes.getText());
			if (a <= 0) {
				return "No. of nodes should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "No. of nodes should be integer.";
		}

		int t = 0;
		try {
			t = Integer.parseInt(txtNoTime.getText());
			if (t <= 0) {
				return "No. of time points should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "No. of time points should be integer.";
		}
		try {
			int a = Integer.parseInt(txtNoTimeTrain.getText());
			if (a <= 0) {
				return "No. of time points for training should be greater than 0.";
			}
			if (a >= t) {
				return "No. of time points for training should be lower than total no. of time points.";
			}

		} catch (NumberFormatException e) {
			return "No. of time points for training should be integer.";
		}

		try {
			int a = Integer.parseInt(txtNoX.getText());
			if (a <= 0) {
				return "No. of attributes pre node should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "No. of attributes pre node should be integer.";
		}

		if (txtMatrixFile.getText().equals("") && !chkLearn.isSelected()) {
			return "Choose matrix file.";
		}
		if (txtXFile.getText().equals("")) {
			return "Choose file with attributes values.";
		}
		if (txtYFile.getText().equals("")) {
			return "Choose file with output values.";
		}

		if (txtModelName.getText().equals("")) {
			return "Insert model name.";
		}

		if (cmbMethod.getSelectedIndex() == 0) {
			return "Choose method.";
		}

		if (isMGCRF()) {
			String validate = validateDataForMGCRF();

			if (validate != null) {
				return validate;
			}
		}

		if (isUpGCRF()) {
			String validate = validateDataForUpGCRF();

			if (validate != null) {
				return validate;
			}
		}

		String method = cmbMethod.getSelectedItem().toString();
		if (Writer.checkFolder("MyModels" + method + "/"
				+ txtModelName.getText())) {
			return "Model with name " + txtModelName.getText()
					+ " already exists.";
		}

		return null;
	}

	private String validateDataForMGCRF() {
		try {
			int a = Integer.parseInt(txtAlpha.getText());
			if (a <= 0) {
				return "Alpha for regularization should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "Alpha for regularization should be integer.";
		}
		try {
			int b = Integer.parseInt(txtBeta.getText());
			if (b <= 0) {
				return "Beta for regularization should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "Beta for regularization should be integer.";
		}

		try {
			int b = Integer.parseInt(txtIter.getText());
			if (b <= 0) {
				return "No. of iteration for m-GCRF training should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "No. of iteration for m-GCRF training should be integer.";
		}
		if (cmbPredictor.getSelectedIndex() == 0) {

			return "Choose predictor.";
		}
		if (cmbPredictor.getSelectedItem().toString().contains("neural")) {

			try {
				Integer.parseInt(txtHidden.getText());
			} catch (NumberFormatException e) {
				return "No. of hidden neurons should be integer.";
			}

			try {
				Integer.parseInt(txtIterNN.getText());
			} catch (NumberFormatException e) {
				return "No. of iterations should be integer.";
			}
		}
		return null;
	}

	private String validateDataForUpGCRF() {
		try {
			int a = Integer.parseInt(txtLag.getText());
			if (a <= 0) {
				return "Lag should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "Lag should be integer.";
		}
		try {
			int b = Integer.parseInt(txtIter.getText());
			if (b <= 0) {
				return "No. of iteration for m-GCRF training should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "No. of iteration for m-GCRF training should be integer.";
		}
		return null;
	}

	private boolean isMGCRF() {
		return cmbMethod.getSelectedItem().toString().contains("m-GCRF");
	}

	private boolean isUpGCRF() {
		return cmbMethod.getSelectedItem().toString().contains("up-GCRF");
	}

	public String validateDataForTestPredictor() {

		if (txtMatrixFile.getText().equals("")) {
			return "Choose file with edges.";
		}
		if (txtXFile.getText().equals("")) {
			return "Choose file with attributes values.";
		}
		if (txtYFile.getText().equals("")) {
			return "Choose file with output values.";
		}
		try {
			Integer.parseInt(txtNoOfNodes.getText());
		} catch (NumberFormatException e) {
			return "No. of nodes should be integer.";
		}

		if (cmbPredictor.getSelectedIndex() == 0) {
			return "Choose predictor.";
		}
		if (cmbPredictor.getSelectedItem().toString().contains("neural")) {

			try {
				Integer.parseInt(txtHidden.getText());
			} catch (NumberFormatException e) {
				return "No. of hidden neurons should be integer.";
			}

			try {
				Integer.parseInt(txtIterNN.getText());
			} catch (NumberFormatException e) {
				return "No. of iterations should be integer.";
			}
		}

		return null;
	}

	private JTextField getTxtMaxIter() {
		if (txtIter == null) {
			txtIter = new JTextField();
			txtIter.setVisible(false);
			txtIter.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtIter.setColumns(10);
			txtIter.setBounds(244, 529, 91, 30);
		}
		return txtIter;
	}

	public void chooseFile(JTextField txt) {
		int returnVal = fc.showOpenDialog(panel);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			txt.setText(fc.getSelectedFile().getPath());
		}
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("No. of nodes:");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label.setBounds(124, 57, 100, 30);
		}
		return label;
	}

	private JTextField getTxtNoOfNodes() {
		if (txtNoOfNodes == null) {
			txtNoOfNodes = new JTextField();
			txtNoOfNodes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNoOfNodes.setColumns(10);
			txtNoOfNodes.setBounds(237, 56, 91, 30);
		}
		return txtNoOfNodes;
	}

	// private JPanel getPanelForTable() {
	// if (panelForTable == null) {
	// panelForTable = new JPanel();
	// panelForTable.setLayout(null);
	// panelForTable.setBounds(35, 496, 850, 114);
	// }
	// return panelForTable;
	// }

	// private JLabel getLblTime() {
	// if (lblTime == null) {
	// lblTime = new JLabel("Time:");
	// lblTime.setVisible(false);
	// lblTime.setHorizontalAlignment(SwingConstants.LEFT);
	// lblTime.setFont(new Font("Segoe UI", Font.BOLD, 15));
	// lblTime.setBounds(35, 610, 421, 30);
	// }
	// return lblTime;
	// }

	public String readParametersFromCfg() {
		try {
			Map<String, String> params = Reader.readCfg();
			try {
				alphaReg = Integer.parseInt(params.get("AlphaReg").toString());
				betaReg = Integer.parseInt(params.get("BetaReg").toString());
				iterMGCRF = Integer.parseInt(params.get("Iterations m-GCRF")
						.toString());
				hidden = Integer.parseInt(params.get("NN hidden").toString());
				iterNN = Integer.parseInt(params.get("Iterations NN")
						.toString());
			} catch (NumberFormatException e) {
				return "Configuration file reading failed. File has wrong format.";
			}
			if (params.get("Use MATLAB").toString().contains("true")) {
				useMatlab = true;
			} else {
				useMatlab = false;
			}
			matlabPath = params.get("Path").toString();
		} catch (ConfigurationParameterseException e) {
			return e.getMessage();
		}
		return null;
	}

	public void setTxtValues() {
		txtAlpha.setText(alphaReg + "");
		txtBeta.setText(betaReg + "");
		txtIter.setText(iterMGCRF + "");
		txtIterNN.setText(iterNN + "");
		txtHidden.setText(hidden + "");
	}

	private JLabel getLblData() {
		if (lblData == null) {
			lblData = new JLabel("DATA");
			lblData.setForeground(Color.WHITE);
			lblData.setBackground(Color.GRAY);
			lblData.setHorizontalAlignment(SwingConstants.CENTER);
			lblData.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblData.setBounds(0, 0, 901, 23);
			lblData.setOpaque(true);
		}
		return lblData;
	}

	private JLabel getLblModel() {
		if (lblModel == null) {
			lblModel = new JLabel("METHOD:");
			lblModel.setOpaque(true);
			lblModel.setHorizontalAlignment(SwingConstants.CENTER);
			lblModel.setForeground(Color.WHITE);
			lblModel.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblModel.setBackground(Color.GRAY);
			lblModel.setBounds(0, 367, 901, 30);
		}
		return lblModel;
	}

	private JLabel getLblPredictor_1() {
		if (lblPredictor_1 == null) {
			lblPredictor_1 = new JLabel("Unstructured predictor:");
			lblPredictor_1.setVisible(false);
			lblPredictor_1.setHorizontalAlignment(SwingConstants.RIGHT);
			lblPredictor_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblPredictor_1.setBounds(420, 448, 188, 30);
		}
		return lblPredictor_1;
	}

	private JComboBox<String> getCmbPredictor() {
		if (cmbPredictor == null) {
			cmbPredictor = new JComboBox<String>();
			cmbPredictor.setVisible(false);
			cmbPredictor.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if (cmbPredictor.getSelectedItem().toString()
							.contains("neural")) {
						lblNoOfHidden.setVisible(true);
						txtHidden.setVisible(true);
						lblNoOfIterations.setVisible(true);
						txtIterNN.setVisible(true);
					} else {
						lblNoOfHidden.setVisible(false);
						txtHidden.setVisible(false);
						lblNoOfIterations.setVisible(false);
						txtIterNN.setVisible(false);
					}
				}
			});
			cmbPredictor.setBounds(618, 451, 149, 30);
			cmbPredictor.addItem("choose predictor");
			cmbPredictor.addItem("neural network");
			cmbPredictor.addItem("linear regression");
		}
		return cmbPredictor;
	}

	private JLabel getLblNoOfHidden() {
		if (lblNoOfHidden == null) {
			lblNoOfHidden = new JLabel("No. of hidden neurons:");
			lblNoOfHidden.setVisible(false);
			lblNoOfHidden.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNoOfHidden.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNoOfHidden.setBounds(445, 488, 164, 30);
		}
		return lblNoOfHidden;
	}

	private JTextField getTxtHidden() {
		if (txtHidden == null) {
			txtHidden = new JTextField();
			txtHidden.setVisible(false);
			txtHidden.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtHidden.setColumns(10);
			txtHidden.setBounds(619, 490, 91, 30);
		}
		return txtHidden;
	}

	private JLabel getLblNoOfIterations() {
		if (lblNoOfIterations == null) {
			lblNoOfIterations = new JLabel("No. of iterations:");
			lblNoOfIterations.setVisible(false);
			lblNoOfIterations.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNoOfIterations.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNoOfIterations.setBounds(474, 529, 130, 30);
		}
		return lblNoOfIterations;
	}

	private JTextField getTxtIterNN() {
		if (txtIterNN == null) {
			txtIterNN = new JTextField();
			txtIterNN.setVisible(false);
			txtIterNN.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtIterNN.setColumns(10);
			txtIterNN.setBounds(619, 531, 91, 30);
		}
		return txtIterNN;
	}

	private JComboBox<String> getCmbMethod() {
		if (cmbMethod == null) {
			cmbMethod = new JComboBox<String>();
			cmbMethod.setBounds(244, 408, 315, 30);

			if (useMatlab) {
				cmbMethod.addItem("choose method");
				cmbMethod.addItem("up-GCRF");
				cmbMethod.addItem("Chao");
				cmbMethod.addItem("m-GCRF");
			} else {
				cmbMethod.addItem("no available methods without MATLAB");
			}
			cmbMethod.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					String method = cmbMethod.getSelectedItem().toString();
					if (isMGCRF()) {
						hideParamsUpGCRF();
						showParamsMGCRF();
					} else {
						hideParamsMGCRF();
						if (isUpGCRF()) {
							showParamsUpGCRF();
						} else {
							hideParamsUpGCRF();
						}
					}

					// if (isUpGCRF() || method.contains("Chao")) {
					// chkLearn.setSelected(true);
					// } else {
					// chkLearn.setSelected(false);
					// }
				}
			});
		}
		return cmbMethod;
	}

	public void showParamsMGCRF() {
		lblAlpha.setVisible(true);
		txtAlpha.setVisible(true);
		lblBeta.setVisible(true);
		txtBeta.setVisible(true);
		lblMaxIterations.setVisible(true);
		txtIter.setVisible(true);
		lblPredictor_1.setVisible(true);
		cmbPredictor.setVisible(true);
		btnQuestionRegAlpha.setVisible(true);
	}

	public void showParamsUpGCRF() {
		lblLag.setVisible(true);
		txtLag.setVisible(true);
		btnQuestionLag.setVisible(true);
		chckUseX.setVisible(true);
		lblMaxIterations.setVisible(true);
		txtIter.setVisible(true);
	}

	public void hideParamsMGCRF() {
		lblAlpha.setVisible(false);
		txtAlpha.setVisible(false);
		lblBeta.setVisible(false);
		txtBeta.setVisible(false);
		lblMaxIterations.setVisible(false);
		txtIter.setVisible(false);
		lblPredictor_1.setVisible(false);
		cmbPredictor.setVisible(false);
		btnQuestionRegAlpha.setVisible(false);
	}

	public void hideParamsUpGCRF() {
		lblLag.setVisible(false);
		txtLag.setVisible(false);
		btnQuestionLag.setVisible(false);
		chckUseX.setVisible(false);
		lblMaxIterations.setVisible(false);
		txtIter.setVisible(false);
	}

	private JLabel getLblMethod() {
		if (lblMethod == null) {
			lblMethod = new JLabel("Method:");
			lblMethod.setHorizontalAlignment(SwingConstants.RIGHT);
			lblMethod.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblMethod.setBounds(128, 408, 100, 30);
		}
		return lblMethod;
	}

	private JLabel getLblNoOfTime() {
		if (lblNoOfTime == null) {
			lblNoOfTime = new JLabel("No. of time points:");
			lblNoOfTime.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNoOfTime.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNoOfTime.setBounds(84, 90, 143, 30);
		}
		return lblNoOfTime;
	}

	private JTextField getTxtNoTime() {
		if (txtNoTime == null) {
			txtNoTime = new JTextField();
			txtNoTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNoTime.setColumns(10);
			txtNoTime.setBounds(237, 92, 91, 30);
		}
		return txtNoTime;
	}

	private JTextField getTxtNoTimeTrain() {
		if (txtNoTimeTrain == null) {
			txtNoTimeTrain = new JTextField();
			txtNoTimeTrain.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNoTimeTrain.setColumns(10);
			txtNoTimeTrain.setBounds(237, 130, 91, 30);
		}
		return txtNoTimeTrain;
	}

	private JLabel getLblNoOfTime_1() {
		if (lblNoOfTime_1 == null) {
			lblNoOfTime_1 = new JLabel("No. of time points for train:");
			lblNoOfTime_1.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNoOfTime_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNoOfTime_1.setBounds(0, 128, 228, 30);
		}
		return lblNoOfTime_1;
	}

	private JCheckBox getChkLearn() {
		if (chkLearn == null) {
			chkLearn = new JCheckBox("Learn similarity");
			chkLearn.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if (chkLearn.isSelected()) {
						txtMatrixFile.setEditable(false);
						txtMatrixFile.setEnabled(false);
						btnBrowseS.setEnabled(false);
						lblSFile.setEnabled(false);
						removeMethod("m-GCRF");
					} else {
						txtMatrixFile.setEditable(true);
						txtMatrixFile.setEnabled(true);
						btnBrowseS.setEnabled(true);
						lblSFile.setEnabled(true);
						addMethod("m-GCRF");
					}
				}
			});
			chkLearn.setBounds(731, 290, 140, 23);
		}
		return chkLearn;
	}

	public void removeMethod(String name) {
		for (int i = 0; i < cmbMethod.getItemCount(); i++) {
			if (cmbMethod.getItemAt(i).toString().contains(name)) {
				if (isMGCRF()) {
					cmbMethod.setSelectedIndex(0);
				}
				cmbMethod.removeItemAt(i);
				panel.repaint();
				panel.revalidate();

				return;
			}
		}
	}

	public void addMethod(String name) {
		cmbMethod.addItem(name);
		panel.repaint();
		panel.revalidate();
	}

	private JButton getBtnQuestionS() {
		if (btnQuestionS == null) {
			btnQuestionS = new JButton("");
			Style.questionButtonStyle(btnQuestionS);
			btnQuestionS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					JOptionPane
							.showMessageDialog(
									mainFrame,
									"Text file (.txt) that contains data about connections between nodes."
											+ "\nThis file contains data about all edges in following format: "
											+ "from node, to node, weight\n"
											+ "For example an edge from node 1 to node 2 with weight 10 will be presented as: "
											+ "1,2,10"
											+ "\nEach edge should be in a separate line."
											+ "\nNodes are represented by ordinal numbers.",
									"Help", JOptionPane.QUESTION_MESSAGE,
									Style.questionIcon());
				}
			});
			btnQuestionS.setBounds(688, 285, 30, 30);
		}
		return btnQuestionS;
	}

	private JButton getBtnQuestionRegAlpha() {
		if (btnQuestionRegAlpha == null) {
			btnQuestionRegAlpha = new JButton("");
			btnQuestionRegAlpha.setVisible(false);
			Style.questionButtonStyle(btnQuestionRegAlpha);
			btnQuestionRegAlpha.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane
							.showMessageDialog(
									mainFrame,
									"For higher percentage of missing values put higher value regularization parameter.",
									"Help", JOptionPane.QUESTION_MESSAGE,
									Style.questionIcon());
				}
			});
			btnQuestionRegAlpha.setBounds(345, 448, 30, 30);
		}
		return btnQuestionRegAlpha;
	}

	private JLabel getLblNoOfAttributres() {
		if (lblNoOfAttributres == null) {
			lblNoOfAttributres = new JLabel("No. of attributres per node:");
			lblNoOfAttributres.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNoOfAttributres.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNoOfAttributres.setBounds(0, 164, 228, 30);
		}
		return lblNoOfAttributres;
	}

	private JTextField getTxtNoX() {
		if (txtNoX == null) {
			txtNoX = new JTextField();
			txtNoX.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNoX.setColumns(10);
			txtNoX.setBounds(237, 166, 91, 30);
		}
		return txtNoX;
	}

	private JLabel getLblProvideTrainAn() {
		if (lblProvideTrainAn == null) {
			lblProvideTrainAn = new JLabel(
					"Provide train and test data together");
			lblProvideTrainAn.setOpaque(true);
			lblProvideTrainAn.setHorizontalAlignment(SwingConstants.CENTER);
			lblProvideTrainAn.setForeground(Color.WHITE);
			lblProvideTrainAn.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblProvideTrainAn.setBackground(Color.GRAY);
			lblProvideTrainAn.setBounds(0, 22, 901, 23);
		}
		return lblProvideTrainAn;
	}

	private JLabel getLblLag() {
		if (lblLag == null) {
			lblLag = new JLabel("Lag:");
			lblLag.setVisible(false);
			lblLag.setHorizontalAlignment(SwingConstants.RIGHT);
			lblLag.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblLag.setBounds(104, 448, 120, 30);
		}
		return lblLag;
	}

	private JTextField getTxtLag() {
		if (txtLag == null) {
			txtLag = new JTextField();
			txtLag.setVisible(false);
			txtLag.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtLag.setColumns(10);
			txtLag.setBounds(244, 448, 91, 30);
		}
		return txtLag;
	}

	private JButton getBtnQuestionLag() {
		if (btnQuestionLag == null) {
			btnQuestionLag = new JButton("");
			btnQuestionLag.setVisible(false);
			btnQuestionLag.setBounds(345, 448, 30, 30);
			Style.questionButtonStyle(btnQuestionLag);
			btnQuestionLag.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane
							.showMessageDialog(
									mainFrame,
									"Number of previous time step values that will be used as inputs",
									"Help", JOptionPane.QUESTION_MESSAGE,
									Style.questionIcon());
				}
			});
		}
		return btnQuestionLag;
	}

	private JCheckBox getChckUseX() {
		if (chckUseX == null) {
			chckUseX = new JCheckBox("Use attributes");
			chckUseX.setSelected(true);
			chckUseX.setVisible(false);
			chckUseX.setBounds(244, 495, 140, 23);
		}
		return chckUseX;
	}
}
