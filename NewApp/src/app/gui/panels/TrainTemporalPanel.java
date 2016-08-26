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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.SwingConstants;

import app.algorithms.basic.BasicCalcs;
import app.exceptions.ConfigurationParameterseException;
import app.file.io.Reader;
import app.file.io.Writer;
import app.gui.frames.MainFrame;
import app.gui.frames.ProgressBar;
import app.gui.style.Style;
import app.gui.threads.MGCRFTrainMyModelForGUI;
import app.gui.threads.RLSRTrainMyModelForGUI;
import app.gui.threads.UpGCRFTrainMyModelForGUI;
import app.predictors.helper.Helper;
import app.predictors.linearregression.LinearRegression;
import app.predictors.linearregression.MultivariateLinearRegression;
import app.predictors.linearregression.MyLR;
import app.predictors.linearregression.TemporalData;
import app.predictors.neuralnetwork.MyNN;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.Map;

import javax.swing.JCheckBox;

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
	private int iterTemp;
	private int hidden;
	private int iterNN;
	private String matlabPath;
	private boolean useMatlab;
	private String lambda;
	private int rlsrHidden;
	private int rlsrIterNN;
	private int sseIter;
	private int lsIter;
	private long proxy;

	private JLabel lblData;
	private JLabel lblModel;
	private JLabel lblPredictor;
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
	private JTextField txtLFSize;
	private JTextField txtLambda;
	private JButton btnQuestionLambda;
	private JTextField txtNoTest;
	private JLabel lblLfSize;
	private JLabel lblSseIter;
	private JTextField txtSseIter;
	private JLabel lblLsIter;
	private JTextField txtLsIter;

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
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		fc.setFileFilter(filter);
		this.mainFrame = mainFrame;
		// add(getLblTime());
		add(getLabel());
		add(getTxtNoOfNodes());
		add(getLblData());
		add(getLblModel());
		add(getLblPredictor());
		add(getLblNoOfHidden());
		add(getTxtHidden());

		add(getLblNoOfIterations());
		add(getTxtIterNN());
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
		add(getTxtLFSize());
		add(getTxtLambda());
		add(getBtnQuestionLambda());
		add(getTxtNoTest());
		add(getLblLfSize());
		add(getLblSseIter());
		add(getTxtSseIter());
		add(getLblLsIter());
		add(getTxtLsIter());

		setTxtValues();
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
			txtMatrixFile.setBounds(247, 285, 315, 30);
			txtMatrixFile.setColumns(10);
		}
		return txtMatrixFile;
	}

	private JLabel getLblSFile() {
		if (lblSFile == null) {
			lblSFile = new JLabel("File with edges:");
			lblSFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblSFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblSFile.setBounds(114, 285, 120, 30);
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
			btnBrowseS.setBounds(579, 285, 100, 30);

		}
		return btnBrowseS;
	}

	private JLabel getLblModelName() {
		if (lblModelName == null) {
			lblModelName = new JLabel("Model name:");
			lblModelName.setHorizontalAlignment(SwingConstants.RIGHT);
			lblModelName.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblModelName.setBounds(134, 326, 100, 30);
		}
		return lblModelName;
	}

	private JTextField getTxtModelName() {
		if (txtModelName == null) {
			txtModelName = new JTextField();
			txtModelName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtModelName.setColumns(10);
			txtModelName.setBounds(247, 326, 315, 30);
		}
		return txtModelName;
	}

	private JLabel getLblRArrayFile() {
		if (lblRArrayFile == null) {
			lblRArrayFile = new JLabel("File with attributes:");
			lblRArrayFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblRArrayFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblRArrayFile.setBounds(94, 205, 140, 30);
		}
		return lblRArrayFile;
	}

	private JTextField getTxtXFile() {
		if (txtXFile == null) {
			txtXFile = new JTextField();
			txtXFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtXFile.setColumns(10);
			txtXFile.setBounds(247, 207, 315, 30);
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
			btnBrowseX.setBounds(579, 207, 100, 30);
		}
		return btnBrowseX;
	}

	private JLabel getLblAlpha() {
		if (lblAlpha == null) {
			lblAlpha = new JLabel("Regularization alpha:");
			lblAlpha.setVisible(false);
			lblAlpha.setHorizontalAlignment(SwingConstants.RIGHT);
			lblAlpha.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblAlpha.setBounds(37, 449, 197, 30);
		}
		return lblAlpha;
	}

	private JLabel getLblBeta() {
		if (lblBeta == null) {
			lblBeta = new JLabel("Regularization beta:");
			lblBeta.setVisible(false);
			lblBeta.setHorizontalAlignment(SwingConstants.RIGHT);
			lblBeta.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblBeta.setBounds(0, 489, 234, 30);
		}
		return lblBeta;
	}

	private JTextField getTxtAlpha() {
		if (txtAlpha == null) {
			txtAlpha = new JTextField();
			txtAlpha.setVisible(false);
			txtAlpha.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAlpha.setColumns(10);
			txtAlpha.setBounds(247, 449, 91, 30);
		}
		return txtAlpha;
	}

	private JTextField getTxtBeta() {
		if (txtBeta == null) {
			txtBeta = new JTextField();
			txtBeta.setVisible(false);
			txtBeta.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtBeta.setColumns(10);
			txtBeta.setBounds(247, 490, 91, 30);
		}
		return txtBeta;
	}

	private JButton getBtnQuestionX() {
		if (btnQuestionX == null) {
			btnQuestionX = new JButton("");
			btnQuestionX.setBounds(698, 207, 30, 30);
			Style.questionButtonStyle(btnQuestionX);
			btnQuestionX.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// "Text file (.txt) that contains output predicted by
					// unstructured predictor for each node."
					// + "\nEach output should be in a separate line. "
					// +
					// "\nOrder of outputs should be consistent with ordinal
					// numbers of nodes in the file with edges (S)."
					JOptionPane.showMessageDialog(mainFrame,
							"Text file (.txt) that contains value of each atribute for each node for each time point."
									+ "\nEach line should contain attributes for one node for all time points (comma separated). "
									+ "\nProvide all attributes for first time point, then for second time point, and so on. "
									+ "\nFor example if there are 3 attributes and 2 time points, one line should look like:"
									+ " a1.1, a2.1, a3.1, a2.1, a2.2, a2.3" + "\nAll atributes should be numbers. ",
							"Help", JOptionPane.QUESTION_MESSAGE, Style.questionIcon());
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
			lblYArrayFile.setBounds(104, 247, 130, 30);
		}
		return lblYArrayFile;
	}

	private JTextField getTxtYFile() {
		if (txtYFile == null) {
			txtYFile = new JTextField();
			txtYFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtYFile.setColumns(10);
			txtYFile.setBounds(247, 247, 315, 30);
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
			btnBrowseY.setBounds(579, 247, 100, 30);
		}
		return btnBrowseY;
	}

	private JButton getBtnQuestionY() {
		if (btnQuestionY == null) {
			btnQuestionY = new JButton("");
			btnQuestionY.setBounds(698, 247, 30, 30);
			Style.questionButtonStyle(btnQuestionY);
			btnQuestionY.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JOptionPane.showMessageDialog(mainFrame,
							"Text file (.txt) that contains actual output for each node for each time point."
									+ "\nEach line should contain outputs for one node for all time points (comma separated). "
									+ "\nOrder of arrays of outputs should be consistent with ordinal numbers of nodes in the file with attributes."
									+ "\nFor missing values put -9999.",
							"Help", JOptionPane.QUESTION_MESSAGE, Style.questionIcon());
				}
			});
		}
		return btnQuestionY;
	}

	private JButton getBtnTrain() {
		if (btnTrain == null) {
			btnTrain = new JButton("TRAIN & TEST");
			btnTrain.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					String message = validateData();
					if (message != null) {
						JOptionPane.showMessageDialog(mainFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
					} else {

						int noOfNodes = Integer.parseInt(txtNoOfNodes.getText());
						int noOfTime = Integer.parseInt(txtNoTime.getText());
						int noOfTimeTrain = Integer.parseInt(txtNoTimeTrain.getText());
						int noOfX = Integer.parseInt(txtNoX.getText());

						String[] x = Reader.read(txtXFile.getText());
						String[] y = Reader.read(txtYFile.getText());

						if (isMGCRF()) {
							double[][] s = Reader.readGraph(txtMatrixFile.getText(), noOfNodes);

							String message1 = checkAllFiles(noOfNodes, noOfTime, noOfX, x, y, s);

							if (message1 != null) {
								JOptionPane.showMessageDialog(mainFrame, message1, "Error", JOptionPane.ERROR_MESSAGE);
								return;
							}

							String path = createFolderAndSaveData();
							runMGCRF(noOfNodes, noOfTime, noOfTimeTrain, noOfX, x, y, s, path);
						} else {
							int maxIter = Integer.parseInt(txtIter.getText());

							String yMSg = checkY(noOfNodes, y, noOfX, noOfTime);

							if (yMSg != null) {
								JOptionPane.showMessageDialog(mainFrame, yMSg, "Error", JOptionPane.ERROR_MESSAGE);
								return;
							}

							double[][] yMatrix = Reader.readMatrix(txtYFile.getText(), noOfNodes, noOfTime);

							if (isUpGCRF()) {

								double[][] x1 = null;
								if (chckUseX.isSelected()) {
									String xMSg = checkX(noOfNodes, x, noOfX, noOfTime);

									if (xMSg != null) {
										JOptionPane.showMessageDialog(mainFrame, xMSg, "Error",
												JOptionPane.ERROR_MESSAGE);
										return;
									}

									x1 = Reader.readMatrix(txtXFile.getText(), noOfNodes, noOfTime * noOfX);
								}
								double[][] s = null;
								if (!chkLearn.isSelected()) {
									String sMSg = checkS(noOfNodes, s);

									if (sMSg != null) {
										JOptionPane.showMessageDialog(mainFrame, sMSg, "Error",
												JOptionPane.ERROR_MESSAGE);
										return;
									}
									s = Reader.readGraph(txtMatrixFile.getText(), noOfNodes);
								}

								String path = createFolderAndSaveData();
								if (s == null) {
									s = new double[noOfNodes][noOfNodes];
								}
								int lag = Integer.parseInt(txtLag.getText());
								int test = Integer.parseInt(txtNoTest.getText());
								trainUpGCRF(matlabPath, path, x1, yMatrix, s, noOfTime, noOfTimeTrain, maxIter,
										noOfNodes, lag, noOfX, test);
							}
							if (isRLSR()) {
								String xMSg = checkX(noOfNodes, x, noOfX, noOfTime);

								if (xMSg != null) {
									JOptionPane.showMessageDialog(mainFrame, xMSg, "Error", JOptionPane.ERROR_MESSAGE);
									return;
								}

								double[][] x1 = Reader.readMatrix(txtXFile.getText(), noOfNodes, noOfTime * noOfX);

								int validation = Integer.parseInt(txtLag.getText());
								int lfSize = Integer.parseInt(txtLFSize.getText());
								int test = Integer.parseInt(txtNoTest.getText());
								int iterNN = Integer.parseInt(txtIterNN.getText());
								int hidden = Integer.parseInt(txtHidden.getText());
								int iterSSE = Integer.parseInt(txtSseIter.getText());
								int iterLs = Integer.parseInt(txtLsIter.getText());
								String lambda = txtLambda.getText();
								String path = createFolderAndSaveData();
								trainRLSR(matlabPath, path, x1, yMatrix, noOfTime, noOfTimeTrain, maxIter, noOfNodes,
										validation, noOfX, lfSize, lambda, test, iterNN, hidden, iterSSE, iterLs);
							}

						}

					}
				}

			});
			Style.buttonStyle(btnTrain);
			btnTrain.setBounds(348, 660, 188, 45);
		}
		return btnTrain;
	}

	public void trainMGCRF(String matlabPath, String modelFolder, double[][] r, double[][] y, double[][] s, int noTime,
			int training, int maxIter, int regAlpha, int regBeta) {
		ProgressBar frame = new ProgressBar("Training");
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		MGCRFTrainMyModelForGUI t = new MGCRFTrainMyModelForGUI(matlabPath, modelFolder, frame, frame, s, r, y, noTime,
				training, maxIter, regAlpha, regBeta, proxy);

		t.start();
	}

	public void trainUpGCRF(String matlabPath, String modelFolder, double[][] r, double[][] y, double[][] s, int noTime,
			int training, int maxIter, int noOfNodes, int lag, int noX, int test) {
		ProgressBar frame = new ProgressBar("Training");
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		boolean useX = true;
		if (!chckUseX.isSelected()) {
			useX = false;
		}
		UpGCRFTrainMyModelForGUI t = new UpGCRFTrainMyModelForGUI(matlabPath, modelFolder, frame, frame, s, r, y,
				noTime, training, maxIter, noOfNodes, lag, noX, useX, test, proxy);

		t.start();
	}

	public void trainRLSR(String matlabPath, String modelFolder, double[][] r, double[][] y, int noTime, int training,
			int maxIter, int noOfNodes, int validation, int noX, int lfSize, String lambda, int test, int iterNN,
			int hidden, int iterSSE, int iterLs) {
		ProgressBar frame = new ProgressBar("Training");
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		RLSRTrainMyModelForGUI t = new RLSRTrainMyModelForGUI(matlabPath, modelFolder, frame, frame, r, y, noTime,
				training, maxIter, noOfNodes, validation, noX, lfSize, lambda, test, iterNN, hidden, iterSSE, iterLs,
				proxy);

		t.start();
	}

	private String checkS(int noOfNodes, double[][] s) {
		if (s == null) {
			return "Ordinal number of node can be between 1 and " + noOfNodes + ".";
		}
		return null;
	}

	private String checkX(int noOfNodes, String[] x, int noOfX, int noOfTime) {
		int totalX = noOfTime * noOfX;
		if (x == null) {
			return "Error while reading file with attributes.";
		}

		if (x.length != noOfNodes) {
			return "Number of lines in the file with attributes should be " + noOfNodes + ".";
		}
		for (int i = 0; i < x.length; i++) {
			if (x[i].split(",").length != totalX) {
				return "Number of values in each line in the file with attributes should be equal to no. of attributes * no. of time points: "
						+ totalX;
			}
		}
		return null;
	}

	private String checkY(int noOfNodes, String[] y, int noOfX, int noOfTime) {
		if (y == null) {
			return "Error while reading file with attributes.";
		}
		if (y.length != noOfNodes) {
			return "Number of lines in the file with outputs should be " + noOfNodes + ".";
		}
		for (int i = 0; i < y.length; i++) {
			if (y[i].split(",").length != noOfTime) {
				return "Number of values in each line in the file with outputs should be equal to no. of time points: "
						+ noOfTime;
			}
		}
		return null;
	}

	private String checkAllFiles(int noOfNodes, int noOfTime, int noOfX, String[] x, String[] y, double[][] s) {

		String xMsg = checkX(noOfNodes, x, noOfX, noOfTime);
		if (xMsg != null) {
			return xMsg;
		}

		String yMsg = checkY(noOfNodes, y, noOfX, noOfTime);
		if (yMsg != null) {
			return yMsg;
		}

		String sMsg = checkS(noOfNodes, s);
		if (sMsg != null) {
			return sMsg;
		}
		return null;
	}

	private void runMGCRF(int noOfNodes, int noOfTime, int noOfTimeTrain, int noOfX, String[] x, String[] y,
			double[][] s, String path) {
		double result = callPredictor(path, x, y, noOfX, noOfTime, noOfTimeTrain, noOfNodes);

		if (result == -7000) {
			JOptionPane.showMessageDialog(mainFrame, "Unknown predictor.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (result == -3000) {
			JOptionPane.showMessageDialog(mainFrame,
					cmbPredictor.getSelectedItem().toString()
							+ " cannot be applied to your data. Choose different predictor.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (result == -5000) {
			JOptionPane.showMessageDialog(mainFrame, "Files are not in correct format.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		if (result == -10000) {
			JOptionPane.showMessageDialog(mainFrame,
					"Values should be normalized (range from 0 to 1) for neural network.", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			double[][] r = Reader.readMatrixTwoFiles(path + "/data/r.txt", path + "/data/rTest.txt", noOfNodes,
					noOfTime, noOfTimeTrain);
			double[][] yMatrix = Reader.readMatrix(path + "/data/y.txt", noOfNodes, noOfTime);
			if (BasicCalcs.isSymmetric(s)) {
				int maxIter = Integer.parseInt(txtIter.getText());
				int regAlpha = Integer.parseInt(txtAlpha.getText());
				int regBeta = Integer.parseInt(txtBeta.getText());
				trainMGCRF(matlabPath, path, r, yMatrix, s, noOfTime, noOfTimeTrain, maxIter, regAlpha, regBeta);
			} else {
				JOptionPane.showMessageDialog(mainFrame, "For m-GCRF method matrix should be symmetric.", "Error",
						JOptionPane.ERROR_MESSAGE);
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

		URL location = MainFrame.class.getProtectionDomain().getCodeSource().getLocation();
		String path1 = location.getFile();
		path1 = path1.substring(1, path1.lastIndexOf("/"));
		String mainPath = path1.substring(0, path1.lastIndexOf("/"));
		Writer.createFolder(mainPath + "/MyModels" + method);
		String path = mainPath + "/MyModels" + method + "/" + txtModelName.getText();
		Writer.createFolder(path);
		String dataPath =  mainPath + "/MyModels" + method + "/" + txtModelName.getText() + "/data";
		Writer.createFolder(dataPath);
		Writer.copyFile(xFile, dataPath + "/x.txt");
		Writer.copyFile(yFile, dataPath + "/y.txt");
		if (matrixFile != null) {
			Writer.copyFile(matrixFile, dataPath + "/matrix.txt");
		}
		return path;
	}

	private double callPredictor(String path, String[] x, String[] y, int noX, int noT, int noTrain, int nodes) {
		if (cmbPredictor.getSelectedItem().toString().contains("neural")) {
			int noOfHidden = Integer.parseInt(txtHidden.getText());
			int noOfIter = Integer.parseInt(txtIterNN.getText());
			DataSet trainingSet = Helper.prepareTemporalDataForNN(x, y, noX, noT, true);
			if (trainingSet == null) {
				return -10000;
			}
			return MyNN.learnAndTest(noOfHidden, trainingSet, 0.003, noOfIter, path, noT, noTrain);
		}
		if (cmbPredictor.getSelectedItem().toString().contains("linear")) {
			TemporalData t = Helper.prepareTemporalDataForLR(x, y, noX, noT, nodes, noTrain);
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
				LinearRegression lr = (LinearRegression) Helper.deserilazie(path + "/lr/lr.txt");
				return LinearRegression.test(t.getyTest(), xOne, path, lr, true);
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
			lblMaxIterations.setBounds(102, 530, 132, 30);
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

		if (isRLSR()) {
			String validate = validateDataForRLSR();

			if (validate != null) {
				return validate;
			}
		}
		URL location = MainFrame.class.getProtectionDomain().getCodeSource().getLocation();
		String path1 = location.getFile();
		path1 = path1.substring(1, path1.lastIndexOf("/"));
		String mainPath = path1.substring(0, path1.lastIndexOf("/"));
		String method = cmbMethod.getSelectedItem().toString();
		if (Writer.checkFolder(mainPath + "/MyModels" + method + "/" + txtModelName.getText())) {
			return "Model with name " + txtModelName.getText() + " already exists.";
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

		int t = Integer.parseInt(txtNoTime.getText());
		int train = Integer.parseInt(txtNoTimeTrain.getText());
		int lag = 0;
		try {
			lag = Integer.parseInt(txtLag.getText());
			if (lag <= 0) {
				return "Lag should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "Lag should be integer.";
		}
		try {
			int a = Integer.parseInt(txtNoTest.getText());
			if (a <= 0) {
				return "No. of time points for test should be greater than 0.";
			}
			if ((train + a + lag) > t) {
				return "Sum of no. of time points for training, for test and Lag should be lower than or equal to total no. of time points.";
			}
		} catch (NumberFormatException e) {
			return "No. of time points for test should be integer.";
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

	private String validateDataForRLSR() {
		int t = Integer.parseInt(txtNoTime.getText());
		int train = Integer.parseInt(txtNoTimeTrain.getText());
		int valid = 0;
		try {
			valid = Integer.parseInt(txtLag.getText());
			if (valid <= 0) {
				return "No. of time points for validation should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "No. of time points for validation should be integer.";
		}

		try {
			int a = Integer.parseInt(txtNoTest.getText());
			if (a <= 0) {
				return "No. of time points for test should be greater than 0.";
			}
			if ((train + a + valid) > t) {
				return "Sum of no. of time points for training, validation and test should be lower than or equal to total no. of time points.";
			}
		} catch (NumberFormatException e) {
			return "No. of time points for test should be integer.";
		}

		int iter = 0;
		try {
			iter = Integer.parseInt(txtIter.getText());
			if (iter <= 0) {
				return "No. of iteration for RLSR training should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "No. of iteration for RLSR training should be integer.";
		}
		try {
			int b = Integer.parseInt(txtLFSize.getText());
			if (b <= 0 || b >= iter) {
				return "LFSize should be greater than 0 and lower than max. iterations.";
			}
		} catch (NumberFormatException e) {
			return "LFSize should be integer.";
		}

		String lambda = txtLambda.getText();
		if (lambda.contains(",")) {
			String[] allLambda = lambda.split(",");
			for (int i = 0; i < allLambda.length; i++) {
				try {
					Double.parseDouble(allLambda[i]);
				} catch (NumberFormatException e) {
					return "Lambda should be comma separated array of double values or single double value.";
				}
			}

		} else {
			try {
				Double.parseDouble(txtLambda.getText());
			} catch (NumberFormatException e) {
				return "Lambda should be comma separated array of double values or single double value.";
			}
		}
		return null;
	}

	private boolean isMGCRF() {
		return cmbMethod.getSelectedItem().toString().contains("m-GCRF");
	}

	private boolean isUpGCRF() {
		return cmbMethod.getSelectedItem().toString().contains("up-GCRF");
	}

	private boolean isRLSR() {
		return cmbMethod.getSelectedItem().toString().contains("RLSR");
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
			txtIter.setBounds(247, 529, 91, 30);
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
			label.setBounds(134, 57, 100, 30);
		}
		return label;
	}

	private JTextField getTxtNoOfNodes() {
		if (txtNoOfNodes == null) {
			txtNoOfNodes = new JTextField();
			txtNoOfNodes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNoOfNodes.setColumns(10);
			txtNoOfNodes.setBounds(247, 56, 91, 30);
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
				hidden = Integer.parseInt(params.get("NN hidden").toString());
				iterNN = Integer.parseInt(params.get("Iterations NN").toString());
				iterTemp = Integer.parseInt(params.get("Iterations temporal").toString());
				lambda = params.get("Lambda").toString();
				rlsrHidden = Integer.parseInt(params.get("RLSR hidden NN").toString());
				rlsrIterNN = Integer.parseInt(params.get("RLSR iterations NN").toString());
				sseIter = Integer.parseInt(params.get("RLSR SSE iterations").toString());
				lsIter = Integer.parseInt(params.get("RLSR SSE LS iterations").toString());
				proxy = Integer.parseInt(params.get("Proxy").toString());
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
		txtIter.setText(iterTemp + "");
		txtLambda.setText(lambda);
		txtSseIter.setText(sseIter + "");
		txtLsIter.setText(lsIter + "");
	}

	private JLabel getLblData() {
		if (lblData == null) {
			lblData = new JLabel("DATA");
			lblData.setForeground(Color.WHITE);
			lblData.setBackground(Color.GRAY);
			lblData.setHorizontalAlignment(SwingConstants.CENTER);
			lblData.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblData.setOpaque(true);
			lblData.setBounds(0, 0, 901, 23);
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

	private JLabel getLblPredictor() {
		if (lblPredictor == null) {
			lblPredictor = new JLabel("Unstructured predictor:");
			lblPredictor.setVisible(false);
			lblPredictor.setHorizontalAlignment(SwingConstants.RIGHT);
			lblPredictor.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblPredictor.setBounds(430, 448, 188, 30);
		}
		return lblPredictor;
	}

	private JComboBox<String> getCmbPredictor() {
		if (cmbPredictor == null) {
			cmbPredictor = new JComboBox<String>();
			cmbPredictor.setVisible(false);
			cmbPredictor.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if (cmbPredictor.getSelectedItem().toString().contains("neural")) {
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
			cmbPredictor.setBounds(628, 451, 149, 30);
			cmbPredictor.addItem("choose predictor");
			cmbPredictor.addItem("neural network");
			cmbPredictor.addItem("linear regression");
		}
		return cmbPredictor;
	}

	private JLabel getLblNoOfHidden() {
		if (lblNoOfHidden == null) {
			lblNoOfHidden = new JLabel("No. of hidden neurons for NN:");
			lblNoOfHidden.setVisible(false);
			lblNoOfHidden.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNoOfHidden.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNoOfHidden.setBounds(405, 488, 214, 30);
		}
		return lblNoOfHidden;
	}

	private JTextField getTxtHidden() {
		if (txtHidden == null) {
			txtHidden = new JTextField();
			txtHidden.setVisible(false);
			txtHidden.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtHidden.setColumns(10);
			txtHidden.setBounds(629, 490, 91, 30);
		}
		return txtHidden;
	}

	private JLabel getLblNoOfIterations() {
		if (lblNoOfIterations == null) {
			lblNoOfIterations = new JLabel("No. of iterations for NN:");
			lblNoOfIterations.setVisible(false);
			lblNoOfIterations.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNoOfIterations.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNoOfIterations.setBounds(446, 530, 172, 30);
		}
		return lblNoOfIterations;
	}

	private JTextField getTxtIterNN() {
		if (txtIterNN == null) {
			txtIterNN = new JTextField();
			txtIterNN.setVisible(false);
			txtIterNN.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtIterNN.setColumns(10);
			txtIterNN.setBounds(629, 531, 91, 30);
		}
		return txtIterNN;
	}

	private JComboBox<String> getCmbMethod() {
		if (cmbMethod == null) {
			cmbMethod = new JComboBox<String>();
			cmbMethod.setBounds(247, 408, 315, 30);

			if (useMatlab) {
				cmbMethod.addItem("choose method");
				cmbMethod.addItem("RLSR");
				cmbMethod.addItem("up-GCRF");
				cmbMethod.addItem("m-GCRF");
			} else {
				cmbMethod.addItem("no available methods without MATLAB");
			}
			cmbMethod.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					String method = cmbMethod.getSelectedItem().toString();
					switch (method) {
					case "m-GCRF":
						hideParamsUpGCRF();
						hideParamsRLSR();
						showParamsMGCRF();
						break;
					case "up-GCRF":
						hideParamsRLSR();
						hideParamsMGCRF();
						showParamsUpGCRF();
						break;
					case "RLSR":
						hideParamsMGCRF();
						hideParamsUpGCRF();
						showParamsRLSR();
						break;
					default:
						hideParamsMGCRF();
						hideParamsUpGCRF();
						hideParamsRLSR();
						break;
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
		lblBeta.setText("Regularization beta:");
		lblBeta.setVisible(true);
		txtBeta.setVisible(true);
		lblMaxIterations.setVisible(true);
		txtIter.setVisible(true);
		lblPredictor.setText("Unstructured predictor:");
		lblPredictor.setVisible(true);
		cmbPredictor.setVisible(true);
		btnQuestionRegAlpha.setVisible(true);
		txtIterNN.setText(iterNN + "");
		txtHidden.setText(hidden + "");
		cmbPredictor.setSelectedIndex(0);
	}

	public void showParamsUpGCRF() {
		lblLag.setText("Lag:");
		lblLag.setVisible(true);
		txtLag.setText("");
		txtLag.setVisible(true);
		btnQuestionLag.setVisible(true);
		chckUseX.setVisible(true);
		lblMaxIterations.setVisible(true);
		txtIter.setVisible(true);
		lblBeta.setText("No. of time points for test:");
		lblBeta.setVisible(true);
		txtNoTest.setVisible(true);
	}

	public void showParamsRLSR() {
		lblLag.setText("No. of time points for validation:");
		lblLag.setVisible(true);
		txtLag.setText("");
		txtLag.setVisible(true);
		lblMaxIterations.setVisible(true);
		txtIter.setVisible(true);
		lblBeta.setText("No. of time points for test:");
		lblBeta.setVisible(true);
		txtLFSize.setVisible(true);
		lblPredictor.setText("Lambda set:");
		lblPredictor.setVisible(true);
		btnQuestionLambda.setVisible(true);
		txtLambda.setVisible(true);
		lblLfSize.setVisible(true);
		txtNoTest.setVisible(true);
		lblNoOfHidden.setVisible(true);
		txtHidden.setVisible(true);
		lblNoOfIterations.setVisible(true);
		txtIterNN.setVisible(true);
		lblLsIter.setVisible(true);
		txtLsIter.setVisible(true);
		lblSseIter.setVisible(true);
		txtSseIter.setVisible(true);
		txtHidden.setText(rlsrHidden + "");
		txtIterNN.setText(rlsrIterNN + "");
	}

	public void hideParamsMGCRF() {
		lblAlpha.setVisible(false);
		txtAlpha.setVisible(false);
		lblBeta.setVisible(false);
		txtBeta.setVisible(false);
		lblMaxIterations.setVisible(false);
		txtIter.setVisible(false);
		lblPredictor.setVisible(false);
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
		txtNoTest.setVisible(false);
	}

	public void hideParamsRLSR() {
		lblLag.setVisible(false);
		txtLag.setVisible(false);
		lblMaxIterations.setVisible(false);
		txtIter.setVisible(false);
		lblBeta.setVisible(false);
		txtLFSize.setVisible(false);
		btnQuestionLambda.setVisible(false);
		txtLambda.setVisible(false);
		lblLfSize.setVisible(false);
		txtNoTest.setVisible(false);
		lblNoOfHidden.setVisible(false);
		txtHidden.setVisible(false);
		lblNoOfIterations.setVisible(false);
		txtIterNN.setVisible(false);
		lblLsIter.setVisible(false);
		txtLsIter.setVisible(false);
		lblSseIter.setVisible(false);
		txtSseIter.setVisible(false);
	}

	private JLabel getLblMethod() {
		if (lblMethod == null) {
			lblMethod = new JLabel("Method:");
			lblMethod.setHorizontalAlignment(SwingConstants.RIGHT);
			lblMethod.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblMethod.setBounds(134, 408, 100, 30);
		}
		return lblMethod;
	}

	private JLabel getLblNoOfTime() {
		if (lblNoOfTime == null) {
			lblNoOfTime = new JLabel("No. of time points:");
			lblNoOfTime.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNoOfTime.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNoOfTime.setBounds(94, 90, 143, 30);
		}
		return lblNoOfTime;
	}

	private JTextField getTxtNoTime() {
		if (txtNoTime == null) {
			txtNoTime = new JTextField();
			txtNoTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNoTime.setColumns(10);
			txtNoTime.setBounds(247, 92, 91, 30);
		}
		return txtNoTime;
	}

	private JTextField getTxtNoTimeTrain() {
		if (txtNoTimeTrain == null) {
			txtNoTimeTrain = new JTextField();
			txtNoTimeTrain.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNoTimeTrain.setColumns(10);
			txtNoTimeTrain.setBounds(247, 130, 91, 30);
		}
		return txtNoTimeTrain;
	}

	private JLabel getLblNoOfTime_1() {
		if (lblNoOfTime_1 == null) {
			lblNoOfTime_1 = new JLabel("No. of time points for train:");
			lblNoOfTime_1.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNoOfTime_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNoOfTime_1.setBounds(10, 128, 228, 30);
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
			chkLearn.setBounds(741, 290, 140, 23);
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

					JOptionPane.showMessageDialog(mainFrame,
							"Text file (.txt) that contains data about connections between nodes."
									+ "\nThis file contains data about all edges in following format: "
									+ "from node, to node, weight\n"
									+ "For example an edge from node 1 to node 2 with weight 10 will be presented as: "
									+ "1,2,10" + "\nEach edge should be in a separate line."
									+ "\nNodes are represented by ordinal numbers.",
							"Help", JOptionPane.QUESTION_MESSAGE, Style.questionIcon());
				}
			});
			btnQuestionS.setBounds(698, 285, 30, 30);
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
					JOptionPane.showMessageDialog(mainFrame,
							"For higher percentage of missing values put higher value regularization parameter.",
							"Help", JOptionPane.QUESTION_MESSAGE, Style.questionIcon());
				}
			});
			btnQuestionRegAlpha.setBounds(348, 448, 30, 30);
		}
		return btnQuestionRegAlpha;
	}

	private JLabel getLblNoOfAttributres() {
		if (lblNoOfAttributres == null) {
			lblNoOfAttributres = new JLabel("No. of attributes per node:");
			lblNoOfAttributres.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNoOfAttributres.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNoOfAttributres.setBounds(10, 164, 228, 30);
		}
		return lblNoOfAttributres;
	}

	private JTextField getTxtNoX() {
		if (txtNoX == null) {
			txtNoX = new JTextField();
			txtNoX.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNoX.setColumns(10);
			txtNoX.setBounds(247, 166, 91, 30);
		}
		return txtNoX;
	}

	private JLabel getLblProvideTrainAn() {
		if (lblProvideTrainAn == null) {
			lblProvideTrainAn = new JLabel("Provide train and test data together");
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
			lblLag.setBounds(4, 449, 230, 30);
		}
		return lblLag;
	}

	private JTextField getTxtLag() {
		if (txtLag == null) {
			txtLag = new JTextField();
			txtLag.setVisible(false);
			txtLag.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtLag.setColumns(10);
			txtLag.setBounds(247, 448, 91, 30);
		}
		return txtLag;
	}

	private JButton getBtnQuestionLag() {
		if (btnQuestionLag == null) {
			btnQuestionLag = new JButton("");
			btnQuestionLag.setVisible(false);
			btnQuestionLag.setBounds(348, 448, 30, 30);
			Style.questionButtonStyle(btnQuestionLag);
			btnQuestionLag.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(mainFrame,
							"Number of previous time step values that will be used as inputs", "Help",
							JOptionPane.QUESTION_MESSAGE, Style.questionIcon());
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
			chckUseX.setBounds(247, 566, 140, 23);
		}
		return chckUseX;
	}

	private JTextField getTxtLFSize() {
		if (txtLFSize == null) {
			txtLFSize = new JTextField();
			txtLFSize.setVisible(false);
			txtLFSize.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtLFSize.setColumns(10);
			txtLFSize.setBounds(247, 570, 91, 30);
		}
		return txtLFSize;
	}

	private JTextField getTxtLambda() {
		if (txtLambda == null) {
			txtLambda = new JTextField();
			txtLambda.setVisible(false);
			txtLambda.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtLambda.setColumns(10);
			txtLambda.setBounds(628, 449, 149, 30);
		}
		return txtLambda;
	}

	private JButton getBtnQuestionLambda() {
		if (btnQuestionLambda == null) {
			btnQuestionLambda = new JButton("");
			btnQuestionLambda.setVisible(false);
			btnQuestionLambda.setBounds(787, 449, 30, 30);
			Style.questionButtonStyle(btnQuestionLambda);
			btnQuestionLambda.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JOptionPane.showMessageDialog(mainFrame,
							"Set of hyperparameters for regularizor. It can be array of double values or single value. "
									+ "\nValues should be comma separated." + "\nExample: 0.0001,0.001,0.01,0.01,0.1,1",
							"Help", JOptionPane.QUESTION_MESSAGE, Style.questionIcon());
				}
			});
		}
		return btnQuestionLambda;
	}

	private JTextField getTxtNoTest() {
		if (txtNoTest == null) {
			txtNoTest = new JTextField();
			txtNoTest.setVisible(false);
			txtNoTest.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNoTest.setColumns(10);
			txtNoTest.setBounds(247, 489, 91, 30);
		}
		return txtNoTest;
	}

	private JLabel getLblLfSize() {
		if (lblLfSize == null) {
			lblLfSize = new JLabel("LF size:");
			lblLfSize.setVisible(false);
			lblLfSize.setHorizontalAlignment(SwingConstants.RIGHT);
			lblLfSize.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblLfSize.setBounds(134, 568, 100, 30);
		}
		return lblLfSize;
	}

	private JLabel getLblSseIter() {
		if (lblSseIter == null) {
			lblSseIter = new JLabel("SSE max. iterations:");
			lblSseIter.setVisible(false);
			lblSseIter.setHorizontalAlignment(SwingConstants.RIGHT);
			lblSseIter.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblSseIter.setBounds(469, 571, 149, 30);
		}
		return lblSseIter;
	}

	private JTextField getTxtSseIter() {
		if (txtSseIter == null) {
			txtSseIter = new JTextField();
			txtSseIter.setVisible(false);
			txtSseIter.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtSseIter.setColumns(10);
			txtSseIter.setBounds(629, 572, 91, 30);
		}
		return txtSseIter;
	}

	private JLabel getLblLsIter() {
		if (lblLsIter == null) {
			lblLsIter = new JLabel("SSE LS max. iterations:");
			lblLsIter.setVisible(false);
			lblLsIter.setHorizontalAlignment(SwingConstants.RIGHT);
			lblLsIter.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblLsIter.setBounds(457, 612, 161, 30);
		}
		return lblLsIter;
	}

	private JTextField getTxtLsIter() {
		if (txtLsIter == null) {
			txtLsIter = new JTextField();
			txtLsIter.setVisible(false);
			txtLsIter.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtLsIter.setColumns(10);
			txtLsIter.setBounds(627, 614, 91, 30);
		}
		return txtLsIter;
	}
}
