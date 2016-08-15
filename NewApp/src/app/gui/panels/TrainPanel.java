package app.gui.panels;

import javax.imageio.ImageIO;
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
import app.exceptions.ConfigurationParameterseException;
import app.file.io.Reader;
import app.file.io.Writer;
import app.gui.frames.ProgressBar;
import app.gui.style.Style;
import app.gui.threads.DirGCRFTrainMyModelForGUI;
import app.gui.threads.GCRFTrainMyModelForGUI;
import app.gui.threads.UmGCRFTrainMyModelForGUI;
import app.predictors.helper.Helper;
import app.predictors.linearregression.MyLR;
import app.predictors.neuralnetwork.MyNN;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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

public class TrainPanel extends JPanel {

	private static final long serialVersionUID = 7542338256284881226L;
	private JTextField txtMatrixFile;
	private JLabel lblFile;
	private JButton btnBrowseS;
	private JButton btnQuestionS;
	private JLabel lblModelName;
	private JTextField txtModelName;
	private JLabel lblRArrayFile;
	private JTextField txtXFile;
	private JButton btnBrowseX;
	private JLabel lblAlpha;
	private JLabel lblBeta;
	private JTextField txtAlpha;
	private JTextField txtBeta;
	private JLabel lblLearningRate;
	private JTextField txtLR;
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
	private JCheckBox chckbxStandard;
	private JLabel lblStandard;
	// private JPanel panelForTable;
	// private JLabel lblTime;

	// params

	private int alpha;
	private int beta;
	private double lr;
	private int iterations;
	private int hidden;
	private int iterNN;
	private String matlabPath;
	private boolean useMatlab;

	private JSeparator separator;
	private JLabel lblData;
	private JSeparator separator_1;
	private JLabel lblPredictor;
	private JLabel lblModel;
	private JLabel lblPredictor_1;
	private JComboBox<String> cmbPredictor;
	private JButton btnTestPredictr;
	private JLabel lblNoOfHidden;
	private JTextField txtHidden;
	private JLabel lblNoOfIterations;
	private JTextField txtIterNN;
	private JComboBox<String> cmbMethod;
	private JLabel lblMethod;

	public TrainPanel(JFrame mainFrame) {
		setBounds(new Rectangle(0, 0, 900, 650));
		setMinimumSize(new Dimension(500, 500));
		if (Reader.checkFile("cfg.txt")) {
			String result = readParametersFromCfg();
			if (result != null) {
				JOptionPane
						.showMessageDialog(
								mainFrame,
								result
										+ " Please configure parameters values in Settings->Configuration.",
								"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				setBackground(UIManager.getColor("Button.background"));
				setLayout(null);
				add(getTxtMatrixFile());
				add(getLblFile());
				add(getBtnBrowseS());
				add(getBtnQuestionS());
				add(getLblModelName());
				add(getTxtModelName());
				add(getLblRArrayFile());
				add(getTxtXFile());
				add(getBtnBrowseX());
				add(getLblAlpha());
				add(getLblBeta());
				add(getTxtAlpha());
				add(getTxtBeta());
				add(getLblLearningRate());
				add(getTxtLr());
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
				add(getChckbxStandard());
				add(getLblStandard());
				// add(getPanelForTable());
				add(getSeparator());
				add(getLblData());
				add(getSeparator_1());
				add(getLblPredictor());
				add(getLblModel());
				add(getLblPredictor_1());
				add(getBtnTestPredictr());
				add(getLblNoOfHidden());
				add(getTxtHidden());

				add(getLblNoOfIterations());
				add(getTxtIterNN());
				setTxtValues();
				add(getCmbPredictor());
				add(getCmbMethod());
				add(getLblMethod());
			}
		} else {
			JOptionPane
					.showMessageDialog(
							mainFrame,
							"Please configure parameters values in Settings->Configuration.",
							"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private JTextField getTxtMatrixFile() {
		if (txtMatrixFile == null) {
			txtMatrixFile = new JTextField();
			txtMatrixFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtMatrixFile.setBounds(180, 46, 315, 30);
			txtMatrixFile.setColumns(10);
		}
		return txtMatrixFile;
	}

	private JLabel getLblFile() {
		if (lblFile == null) {
			lblFile = new JLabel("File with edges:");
			lblFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblFile.setBounds(44, 46, 120, 30);
		}
		return lblFile;
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
			btnBrowseS.setBounds(512, 46, 100, 30);

		}
		return btnBrowseS;
	}

	private JButton getBtnQuestionS() {
		if (btnQuestionS == null) {
			btnQuestionS = new JButton("");
			btnQuestionS.setBounds(631, 46, 30, 30);
			Style.questionButtonStyle(btnQuestionS);
			btnQuestionS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

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
		}
		return btnQuestionS;
	}

	private JLabel getLblModelName() {
		if (lblModelName == null) {
			lblModelName = new JLabel("Model name:");
			lblModelName.setHorizontalAlignment(SwingConstants.RIGHT);
			lblModelName.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblModelName.setBounds(64, 167, 100, 30);
		}
		return lblModelName;
	}

	private JTextField getTxtModelName() {
		if (txtModelName == null) {
			txtModelName = new JTextField();
			txtModelName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtModelName.setColumns(10);
			txtModelName.setBounds(180, 167, 315, 30);
		}
		return txtModelName;
	}

	private JLabel getLblRArrayFile() {
		if (lblRArrayFile == null) {
			lblRArrayFile = new JLabel("File with attributes:");
			lblRArrayFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblRArrayFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblRArrayFile.setBounds(24, 86, 140, 30);
		}
		return lblRArrayFile;
	}

	private JTextField getTxtXFile() {
		if (txtXFile == null) {
			txtXFile = new JTextField();
			txtXFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtXFile.setColumns(10);
			txtXFile.setBounds(180, 86, 315, 30);
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
			btnBrowseX.setBounds(512, 86, 100, 30);
		}
		return btnBrowseX;
	}

	private JLabel getLblAlpha() {
		if (lblAlpha == null) {
			lblAlpha = new JLabel("First alpha:");
			lblAlpha.setVisible(false);
			lblAlpha.setHorizontalAlignment(SwingConstants.RIGHT);
			lblAlpha.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblAlpha.setBounds(515, 349, 100, 30);
		}
		return lblAlpha;
	}

	private JLabel getLblBeta() {
		if (lblBeta == null) {
			lblBeta = new JLabel("First beta:");
			lblBeta.setVisible(false);
			lblBeta.setHorizontalAlignment(SwingConstants.RIGHT);
			lblBeta.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblBeta.setBounds(515, 389, 100, 30);
		}
		return lblBeta;
	}

	private JTextField getTxtAlpha() {
		if (txtAlpha == null) {
			txtAlpha = new JTextField();
			txtAlpha.setVisible(false);
			txtAlpha.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAlpha.setColumns(10);
			txtAlpha.setBounds(631, 350, 91, 30);
		}
		return txtAlpha;
	}

	private JTextField getTxtBeta() {
		if (txtBeta == null) {
			txtBeta = new JTextField();
			txtBeta.setVisible(false);
			txtBeta.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtBeta.setColumns(10);
			txtBeta.setBounds(631, 391, 91, 30);
		}
		return txtBeta;
	}

	private JLabel getLblLearningRate() {
		if (lblLearningRate == null) {
			lblLearningRate = new JLabel("Learning rate:");
			lblLearningRate.setVisible(false);
			lblLearningRate.setHorizontalAlignment(SwingConstants.RIGHT);
			lblLearningRate.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblLearningRate.setBounds(515, 429, 100, 30);
		}
		return lblLearningRate;
	}

	private JTextField getTxtLr() {
		if (txtLR == null) {
			txtLR = new JTextField();
			txtLR.setVisible(false);
			txtLR.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtLR.setColumns(10);
			txtLR.setBounds(631, 429, 91, 30);
		}
		return txtLR;
	}

	private JButton getBtnQuestionX() {
		if (btnQuestionX == null) {
			btnQuestionX = new JButton("");
			btnQuestionX.setBounds(631, 86, 30, 30);
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
									"Text file (.txt) that contains value of each atribute for each node."
											+ "\nAtributes for each node should be in a separate line. "
											+ "\nAtributes should be comma separated. "
											+ "\nAll atributes should be numbers. "
											+ "\nOrder should be consistent with ordinal numbers of nodes in the file with edges.",
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
			lblYArrayFile.setBounds(34, 126, 130, 30);
		}
		return lblYArrayFile;
	}

	private JTextField getTxtYFile() {
		if (txtYFile == null) {
			txtYFile = new JTextField();
			txtYFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtYFile.setColumns(10);
			txtYFile.setBounds(180, 126, 315, 30);
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
			btnBrowseY.setBounds(512, 126, 100, 30);
		}
		return btnBrowseY;
	}

	private JButton getBtnQuestionY() {
		if (btnQuestionY == null) {
			btnQuestionY = new JButton("");
			btnQuestionY.setBounds(631, 126, 30, 30);
			Style.questionButtonStyle(btnQuestionY);
			btnQuestionY.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JOptionPane
							.showMessageDialog(
									mainFrame,
									"Text file (.txt) that contains actual output for each node."
											+ "\nEach output should be in a separate line. "
											+ "\nOrder of outputs should be consistent with ordinal numbers of nodes in the file with edges (S).",
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

						String method = cmbMethod.getSelectedItem().toString();

						double alpha = Double.parseDouble(txtAlpha.getText());
						double beta = Double.parseDouble(txtBeta.getText());
						double lr = Double.parseDouble(txtLR.getText());
						int maxIter = Integer.parseInt(txtIter.getText());

						String[] x = Reader.read(txtXFile.getText());
						double[] y = Reader.readArray(txtYFile.getText(),
								noOfNodes);
						double[][] s = Reader.readGraph(
								txtMatrixFile.getText(), noOfNodes);

						String message1 = checkFiles(noOfNodes, x, y, s);

						if (message1 != null) {
							JOptionPane.showMessageDialog(mainFrame, message1,
									"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}

						String path = createFolderAndSaveData(method);
						double result = callPredictor(path, x, y);

						if (result == -7000) {
							JOptionPane.showMessageDialog(mainFrame,
									"Unknown predictor.", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (result == -3000) {
							JOptionPane
									.showMessageDialog(
											mainFrame,
											cmbPredictor.getSelectedItem()
													.toString()
													+ " cannot be applied to your data. Choose different predictor.",
											"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (result == -5000) {
							JOptionPane
									.showMessageDialog(
											mainFrame,
											"File with attributes is not in correct format.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} else {

							double[] r = Reader.readArray(path + "/data/r.txt",
									noOfNodes);

							callMethod(method, path, noOfNodes, alpha, beta,
									lr, maxIter, y, r, s);
						}

					}
				}

			});
			Style.buttonStyle(btnTrain);
			btnTrain.setBounds(628, 547, 112, 45);
		}
		return btnTrain;
	}

	private double callPredictor(String path, String[] x, double[] y) {
		if (cmbPredictor.getSelectedItem().toString().contains("neural")) {
			int noOfHidden = Integer.parseInt(txtHidden.getText());
			int noOfIter = Integer.parseInt(txtIterNN.getText());
			DataSet trainingSet = Helper.prepareDataForNN(x, y);
			return MyNN.learn(noOfHidden, trainingSet, 0.003, noOfIter, path);
		}
		if (cmbPredictor.getSelectedItem().toString().contains("linear")) {
			double[][] xNumbers = Helper.prepareDataForLR(x);
			return MyLR.learn(xNumbers, y, path);
		}
		return -7000;

	}

	private String checkFiles(int noOfNodes, String[] x, double[] y,
			double[][] s) {
		if (x == null) {
			return "Error while reading file with attributes.";
		}

		if (x.length != noOfNodes) {
			return "Number of lines in the file with attributes should be "
					+ noOfNodes + ".";
		}

		if (y == null) {
			return "Number of lines in in the file with outputs should be "
					+ noOfNodes + ".";
		}

		if (s == null) {
			return "Ordinal number of node can be between 1 and " + noOfNodes
					+ ".";
		}
		return null;
	}

	private String createFolderAndSaveData(String method) {
		File matrixFile = new File(txtMatrixFile.getText());
		File xFile = new File(txtXFile.getText());
		File yFile = new File(txtYFile.getText());
		Writer.createFolder("MyModels" + method);
		String path = "MyModels" + method + "/" + txtModelName.getText();
		Writer.createFolder(path);
		String dataPath = "MyModels" + method + "/" + txtModelName.getText()
				+ "/data";
		Writer.createFolder(dataPath);
		Writer.copyFile(matrixFile, dataPath + "/matrix.txt");
		Writer.copyFile(xFile, dataPath + "/x.txt");

		Writer.copyFile(yFile, dataPath + "/y.txt");
		Writer.createFolder(path);
		return path;
	}

	private void callMethod(String method, String path, int noOfNodes,
			double alpha, double beta, double lr, int maxIter, double[] y,
			double[] r, double[][] s) {
		switch (method) {
		case "DirGCRF":
			trainDirGCRF(noOfNodes, path, maxIter, alpha, beta, lr, r, y, s);
			break;
		case "GCRF":
			if (BasicCalcs.isSymmetric(s)) {
				trainGCRF(noOfNodes, path, maxIter, alpha, beta, lr, r, y, s);
			} else {
				JOptionPane.showMessageDialog(mainFrame,
						"For GCRF method matrix should be symmetric.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "UmGCRF":
			if (BasicCalcs.isSymmetric(s)) {
				trainUmGCRF(path, r, y, s);
			} else {
				JOptionPane.showMessageDialog(mainFrame,
						"For UmGCRF method matrix should be symmetric.",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			break;
		default:
			JOptionPane.showMessageDialog(mainFrame, "Unknown method.",
					"Error", JOptionPane.ERROR_MESSAGE);
			break;
		}

	}

	public void trainDirGCRF(int noOfNodes, String modelFolder, int maxIter,
			double alpha, double beta, double lr, double[] r, double[] y,
			double[][] s) {

		ProgressBar frame = new ProgressBar(maxIter);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		boolean both = false;
		if (chckbxStandard.isSelected()) {
			both = true;
		}
		DirGCRFTrainMyModelForGUI t = new DirGCRFTrainMyModelForGUI(
				modelFolder, frame, mainFrame, s, r, y, alpha, beta, lr,
				maxIter, both);
		// 10, 10
		t.start();

	}

	public void trainGCRF(int noOfNodes, String modelFolder, int maxIter,
			double alpha, double beta, double lr, double[] r, double[] y,
			double[][] s) {

		ProgressBar frame = new ProgressBar(maxIter);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		GCRFTrainMyModelForGUI t = new GCRFTrainMyModelForGUI(modelFolder,
				frame, mainFrame, s, r, y, alpha, beta, lr, maxIter);

		t.start();
	}

	public void trainUmGCRF(String modelFolder, double[] r, double[] y,
			double[][] s) {
		ProgressBar frame = new ProgressBar("Training");
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		UmGCRFTrainMyModelForGUI t = new UmGCRFTrainMyModelForGUI(matlabPath,
				modelFolder, frame, mainFrame, s, r, y);

		t.start();
	}

	private JLabel getLblMaxIterations() {
		if (lblMaxIterations == null) {
			lblMaxIterations = new JLabel("Max. iterations:");
			lblMaxIterations.setVisible(false);
			lblMaxIterations.setHorizontalAlignment(SwingConstants.RIGHT);
			lblMaxIterations.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblMaxIterations.setBounds(483, 469, 132, 30);
		}
		return lblMaxIterations;
	}

	public String validateData() {
		if (txtMatrixFile.getText().equals("")) {
			return "Choose matrix file.";
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
		if (txtModelName.getText().equals("")) {
			return "Insert model name.";
		}

		if (cmbPredictor.getSelectedIndex() == 0) {
			return "Choose predictor.";
		}
		if (cmbMethod.getSelectedIndex() == 0) {
			return "Choose method.";
		}
		String method = cmbMethod.getSelectedItem().toString();
		if (Writer.checkFolder("MyModels" + method + "/"
				+ txtModelName.getText())) {
			return "Model with name " + txtModelName.getText()
					+ " already exists.";
		}
		try {
			Double.parseDouble(txtAlpha.getText());
		} catch (NumberFormatException e) {
			return "First alpha should be number.";
		}
		try {
			Double.parseDouble(txtBeta.getText());
		} catch (NumberFormatException e) {
			return "First beta should be number.";
		}
		try {
			Double.parseDouble(txtLR.getText());
		} catch (NumberFormatException e) {
			return "Learning rate should be number.";
		}

		try {
			Integer.parseInt(txtIter.getText());
		} catch (NumberFormatException e) {
			return "Max. iterations should be integer.";
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
			txtIter.setBounds(631, 469, 91, 30);
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
			label.setBounds(64, 209, 100, 30);
		}
		return label;
	}

	private JTextField getTxtNoOfNodes() {
		if (txtNoOfNodes == null) {
			txtNoOfNodes = new JTextField();
			txtNoOfNodes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNoOfNodes.setColumns(10);
			txtNoOfNodes.setBounds(180, 208, 91, 30);
		}
		return txtNoOfNodes;
	}

	private JCheckBox getChckbxStandard() {
		if (chckbxStandard == null) {
			chckbxStandard = new JCheckBox("");
			chckbxStandard.setVisible(false);
			chckbxStandard.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			chckbxStandard.setBounds(629, 517, 91, 23);
		}
		return chckbxStandard;
	}

	private JLabel getLblStandard() {
		if (lblStandard == null) {
			lblStandard = new JLabel("Apply standard GCRF:");
			lblStandard.setVisible(false);
			lblStandard.setHorizontalAlignment(SwingConstants.RIGHT);
			lblStandard.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblStandard.setBounds(450, 510, 165, 30);
		}
		return lblStandard;
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
				alpha = Integer.parseInt(params.get("Alpha").toString());
				beta = Integer.parseInt(params.get("Beta").toString());
				lr = Double.parseDouble(params.get("LR").toString());
				iterations = Integer.parseInt(params.get("Iterations")
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
		txtAlpha.setText(alpha + "");
		txtBeta.setText(beta + "");
		txtLR.setText(lr + "");
		txtIter.setText(iterations + "");
		txtIterNN.setText(iterNN + "");
		txtHidden.setText(hidden + "");
	}

	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
			separator.setBackground(Color.GRAY);
			separator.setForeground(Color.GRAY);
			separator.setBounds(0, 250, 1027, 2);
		}
		return separator;
	}

	private JLabel getLblData() {
		if (lblData == null) {
			lblData = new JLabel("DATA:");
			lblData.setForeground(Color.WHITE);
			lblData.setBackground(Color.GRAY);
			lblData.setHorizontalAlignment(SwingConstants.CENTER);
			lblData.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblData.setBounds(0, 0, 901, 30);
			lblData.setOpaque(true);
		}
		return lblData;
	}

	private JSeparator getSeparator_1() {
		if (separator_1 == null) {
			separator_1 = new JSeparator();
			separator_1.setOrientation(SwingConstants.VERTICAL);
			separator_1.setForeground(Color.GRAY);
			separator_1.setBackground(Color.GRAY);
			separator_1.setBounds(450, 251, 2, 429);
		}
		return separator_1;
	}

	private JLabel getLblPredictor() {
		if (lblPredictor == null) {
			lblPredictor = new JLabel("UNSTRUCTURED PREDICTOR:");
			lblPredictor.setOpaque(true);
			lblPredictor.setHorizontalAlignment(SwingConstants.CENTER);
			lblPredictor.setForeground(Color.WHITE);
			lblPredictor.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblPredictor.setBackground(Color.GRAY);
			lblPredictor.setBounds(0, 250, 452, 30);
		}
		return lblPredictor;
	}

	private JLabel getLblModel() {
		if (lblModel == null) {
			lblModel = new JLabel("METHOD:");
			lblModel.setOpaque(true);
			lblModel.setHorizontalAlignment(SwingConstants.CENTER);
			lblModel.setForeground(Color.WHITE);
			lblModel.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblModel.setBackground(Color.GRAY);
			lblModel.setBounds(450, 250, 451, 30);
		}
		return lblModel;
	}

	private JLabel getLblPredictor_1() {
		if (lblPredictor_1 == null) {
			lblPredictor_1 = new JLabel("Unstructured predictor:");
			lblPredictor_1.setHorizontalAlignment(SwingConstants.RIGHT);
			lblPredictor_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblPredictor_1.setBounds(0, 309, 188, 30);
		}
		return lblPredictor_1;
	}

	private JComboBox<String> getCmbPredictor() {
		if (cmbPredictor == null) {
			cmbPredictor = new JComboBox<String>();
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
			cmbPredictor.setBounds(204, 309, 227, 30);
			cmbPredictor.addItem("choose predictor");
			cmbPredictor.addItem("neural network");
			cmbPredictor.addItem("linear regression");
		}
		return cmbPredictor;
	}

	private JButton getBtnTestPredictr() {
		if (btnTestPredictr == null) {
			btnTestPredictr = new JButton("Test predictor");
			Style.buttonStyle(btnTestPredictr);
			btnTestPredictr.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String message = validateDataForTestPredictor();
					if (message != null) {
						JOptionPane.showMessageDialog(mainFrame, message,
								"Error", JOptionPane.ERROR_MESSAGE);
					} else {
						int noOfNodes = Integer.parseInt(txtNoOfNodes.getText());

						String[] x = Reader.read(txtXFile.getText());
						if (x == null) {
							JOptionPane.showMessageDialog(mainFrame,
									"Number of lines in the file with attributes should be "
											+ noOfNodes + ".", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}

						double[] y = Reader.readArray(txtYFile.getText(),
								noOfNodes);
						if (y == null) {
							JOptionPane.showMessageDialog(mainFrame,
									"Number of lines in in the file with outputs should be "
											+ noOfNodes + ".", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}

						double result = callPredictor(null, x, y);
						if (result == -7000) {
							JOptionPane.showMessageDialog(mainFrame,
									"Unknown predictor.", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (result == -3000) {
							JOptionPane
									.showMessageDialog(
											mainFrame,
											cmbPredictor.getSelectedItem()
													.toString()
													+ " cannot be applied to your data. Choose different predictor.",
											"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (result == -5000) {
							JOptionPane
									.showMessageDialog(
											mainFrame,
											"File with attributes is not in correct format.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} else {
							DecimalFormat df = new DecimalFormat("#.####");
							JOptionPane.showMessageDialog(
									mainFrame,
									"Testing with same data\nR^2 value for "
											+ cmbPredictor.getSelectedItem()
													.toString() + " is: "
											+ df.format(result), "Results",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			});
			btnTestPredictr.setBounds(204, 547, 140, 45);
		}
		return btnTestPredictr;
	}

	private JLabel getLblNoOfHidden() {
		if (lblNoOfHidden == null) {
			lblNoOfHidden = new JLabel("No. of hidden neurons:");
			lblNoOfHidden.setVisible(false);
			lblNoOfHidden.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNoOfHidden.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNoOfHidden.setBounds(24, 349, 164, 30);
		}
		return lblNoOfHidden;
	}

	private JTextField getTxtHidden() {
		if (txtHidden == null) {
			txtHidden = new JTextField();
			txtHidden.setVisible(false);
			txtHidden.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtHidden.setColumns(10);
			txtHidden.setBounds(204, 349, 91, 30);
		}
		return txtHidden;
	}

	private JLabel getLblNoOfIterations() {
		if (lblNoOfIterations == null) {
			lblNoOfIterations = new JLabel("No. of iterations:");
			lblNoOfIterations.setVisible(false);
			lblNoOfIterations.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNoOfIterations.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblNoOfIterations.setBounds(58, 389, 130, 30);
		}
		return lblNoOfIterations;
	}

	private JTextField getTxtIterNN() {
		if (txtIterNN == null) {
			txtIterNN = new JTextField();
			txtIterNN.setVisible(false);
			txtIterNN.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtIterNN.setColumns(10);
			txtIterNN.setBounds(204, 397, 91, 30);
		}
		return txtIterNN;
	}

	private JComboBox<String> getCmbMethod() {
		if (cmbMethod == null) {
			cmbMethod = new JComboBox<String>();
			cmbMethod.setBounds(631, 309, 227, 30);
			cmbMethod.addItem("choose method");
			cmbMethod.addItem("GCRF");
			cmbMethod.addItem("DirGCRF");
			if (useMatlab) {
				cmbMethod.addItem("UmGCRF");
			}
			cmbMethod.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					String method = cmbMethod.getSelectedItem().toString();
					if (method.contains("Dir")) {
						lblStandard.setVisible(true);
						chckbxStandard.setVisible(true);
					} else {
						lblStandard.setVisible(false);
						chckbxStandard.setVisible(false);
					}
					if (method.equalsIgnoreCase("DirGCRF")
							|| method.equalsIgnoreCase("GCRF")) {
						showGradientDescentParams();
					} else {
						hideGradientDescentParams();
					}
				}
			});
		}
		return cmbMethod;
	}

	public void showGradientDescentParams() {
		lblAlpha.setVisible(true);
		txtAlpha.setVisible(true);
		lblBeta.setVisible(true);
		txtBeta.setVisible(true);
		lblLearningRate.setVisible(true);
		txtLR.setVisible(true);
		lblMaxIterations.setVisible(true);
		txtIter.setVisible(true);
	}

	public void hideGradientDescentParams() {
		lblAlpha.setVisible(false);
		txtAlpha.setVisible(false);
		lblBeta.setVisible(false);
		txtBeta.setVisible(false);
		lblLearningRate.setVisible(false);
		txtLR.setVisible(false);
		lblMaxIterations.setVisible(false);
		txtIter.setVisible(false);
	}

	private JLabel getLblMethod() {
		if (lblMethod == null) {
			lblMethod = new JLabel("Method:");
			lblMethod.setHorizontalAlignment(SwingConstants.RIGHT);
			lblMethod.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblMethod.setBounds(515, 309, 100, 30);
		}
		return lblMethod;
	}
}
