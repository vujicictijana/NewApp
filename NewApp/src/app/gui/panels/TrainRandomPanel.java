package app.gui.panels;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import app.algorithms.asymmetric.CalculationsAsymmetric;
import app.data.generators.ArrayGenerator;
import app.data.generators.GraphGenerator;
import app.exceptions.ConfigurationParameterseException;
import app.file.io.Reader;
import app.file.io.Writer;
import app.gui.frames.MainFrame;
import app.gui.frames.ProgressBar;
import app.gui.style.Style;
import app.gui.threads.TrainWithRandomForGUI;

import javax.swing.JComboBox;

import java.awt.event.ItemListener;
import java.net.URL;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.JCheckBox;

public class TrainRandomPanel extends JPanel {

	private static final long serialVersionUID = 2574347379339766542L;
	private JLabel lblType;
	private JButton btnQuestionType;
	private JTextField txtProb;
	private JLabel lblRArrayFile;
	private JLabel lblAlpha;
	private JLabel lblFirstBeta;
	private JTextField txtAlpha;
	private JTextField txtBeta;
	private JLabel lblLearningRate;
	private JTextField txtLR;
	private JLabel lblYArrayFile;
	private JTextField txtNoOfNodes;
	private JButton btnTrain;
	private JTextField txtIter;
	private JLabel label;
	private JComboBox<String> cmbGraphType;
	private JFrame mainFrame;
	private JCheckBox chckbxSymmetric;
	private JLabel lblTrainSymmetric;
	private JPanel panelForTable;
	private JLabel lblTime;

	// params

	private int alphaGen;
	private int betaGen;
	private int alpha;
	private int beta;
	private double lr;
	private int iterations;

	/**
	 * Create the panel.
	 */
	public TrainRandomPanel(JFrame mainFrame) {
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
				add(getLblType());
				add(getBtnQuestionS());
				add(getTxtProb());
				add(getLblRArrayFile());
				add(getLblAlpha());
				add(getLblFirstBeta());
				add(getTxtAlpha());
				add(getTxtBeta());
				add(getLblLearningRate());
				add(getTxtLr());
				add(getLblYArrayFile());
				add(getTxtNoOfNodes());
				add(getBtnTrain());
				add(getTxtMaxIter());
				add(getLabel());
				add(getCmbGraphType());
				add(getChckbxSymmetric());
				add(getLblTrainSymmetric());
				this.mainFrame = mainFrame;
				add(getPanelForTable());
				add(getLblTime());
				createMainFolders();
				setTxtValues();
			}
		} else {
			JOptionPane
					.showMessageDialog(
							mainFrame,
							"Please configure parameters values in Settings->Configuration.",
							"Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	private JLabel getLblType() {
		if (lblType == null) {
			lblType = new JLabel("Graph type:");
			lblType.setHorizontalAlignment(SwingConstants.RIGHT);
			lblType.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblType.setBounds(65, 36, 100, 30);
		}
		return lblType;
	}

	private JButton getBtnQuestionS() {
		if (btnQuestionType == null) {
			btnQuestionType = new JButton("");
			btnQuestionType.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JOptionPane
							.showMessageDialog(
									mainFrame,
									"Graph types:\n"
											+ "* Directed graph - fully connected directed graph\n"
											+ "* Directed graph with edge probability - edge probability represents the "
											+ "probability that edge between two random nodes exists\n"
											+ "* Directed acyclic graph - directed graph without cycles\n"
											+ "* Directed directed graph without direct feedback- "
											+ "if there is direct connection from A to B, there is no direct connection from B to A\n"
											+ "* Chain -  all nodes are connected in a single sequence, from one node to another\n"
											+ "* Binary tree - graph with a tree structure in which each node could have at most two children\n",
									"Help", JOptionPane.QUESTION_MESSAGE,
									Style.questionIcon());
				}
			});

			btnQuestionType.setBounds(515, 36, 30, 30);
			Style.questionButtonStyle(btnQuestionType);
		}
		return btnQuestionType;
	}

	private JTextField getTxtProb() {
		if (txtProb == null) {
			txtProb = new JTextField();
			txtProb.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtProb.setColumns(10);
			txtProb.setBounds(181, 118, 91, 30);
			txtProb.setEnabled(false);
			txtProb.setEditable(false);
		}
		return txtProb;
	}

	private JLabel getLblRArrayFile() {
		if (lblRArrayFile == null) {
			lblRArrayFile = new JLabel("No. of nodes:");
			lblRArrayFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblRArrayFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblRArrayFile.setBounds(65, 78, 100, 30);
		}
		return lblRArrayFile;
	}

	private JLabel getLblAlpha() {
		if (lblAlpha == null) {
			lblAlpha = new JLabel("First alpha:");
			lblAlpha.setHorizontalAlignment(SwingConstants.RIGHT);
			lblAlpha.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblAlpha.setBounds(65, 158, 100, 30);
		}
		return lblAlpha;
	}

	private JLabel getLblFirstBeta() {
		if (lblFirstBeta == null) {
			lblFirstBeta = new JLabel("First beta:");
			lblFirstBeta.setHorizontalAlignment(SwingConstants.RIGHT);
			lblFirstBeta.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblFirstBeta.setBounds(65, 198, 100, 30);
		}
		return lblFirstBeta;
	}

	private JTextField getTxtAlpha() {
		if (txtAlpha == null) {
			txtAlpha = new JTextField();
			txtAlpha.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAlpha.setColumns(10);
			txtAlpha.setBounds(181, 159, 91, 30);
		}
		return txtAlpha;
	}

	private JTextField getTxtBeta() {
		if (txtBeta == null) {
			txtBeta = new JTextField();
			txtBeta.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtBeta.setColumns(10);
			txtBeta.setBounds(181, 200, 91, 30);
		}
		return txtBeta;
	}

	private JLabel getLblLearningRate() {
		if (lblLearningRate == null) {
			lblLearningRate = new JLabel("Learning rate:");
			lblLearningRate.setHorizontalAlignment(SwingConstants.RIGHT);
			lblLearningRate.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblLearningRate.setBounds(65, 238, 100, 30);
		}
		return lblLearningRate;
	}

	private JTextField getTxtLr() {
		if (txtLR == null) {
			txtLR = new JTextField();
			txtLR.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtLR.setColumns(10);
			txtLR.setBounds(181, 238, 91, 30);
		}
		return txtLR;
	}

	private JLabel getLblYArrayFile() {
		if (lblYArrayFile == null) {
			lblYArrayFile = new JLabel("Probability:");
			lblYArrayFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblYArrayFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblYArrayFile.setBounds(65, 118, 100, 30);
		}
		return lblYArrayFile;
	}

	private JTextField getTxtNoOfNodes() {
		if (txtNoOfNodes == null) {
			txtNoOfNodes = new JTextField();
			txtNoOfNodes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNoOfNodes.setColumns(10);
			txtNoOfNodes.setBounds(181, 77, 91, 30);
		}
		return txtNoOfNodes;
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
						String model = Writer.folderName(cmbGraphType
								.getSelectedItem().toString());
						URL location = MainFrame.class.getProtectionDomain()
								.getCodeSource().getLocation();

						String path1 = location.getFile();
						path1 = path1.substring(1, path1.lastIndexOf("/"));
						String mainPath = path1.substring(0,
								path1.lastIndexOf("/"));
						String modelFolder = mainPath + "/RandomModels/"
								+ model + "/" + noOfNodes + "nodes";
						if (model.contains("Probability")) {
							double probability = Double.parseDouble(txtProb
									.getText());
							modelFolder += probability + "probability";
						}

						if (checkModel(modelFolder)) {

							int selectedOption = JOptionPane
									.showConfirmDialog(
											mainFrame,

											"Model for "
													+ cmbGraphType
															.getSelectedItem()
															.toString()
													+ " with "
													+ noOfNodes
													+ " nodes already exists. Do you want to replace it?",
											"Question",
											JOptionPane.YES_NO_OPTION);
							if (selectedOption == JOptionPane.YES_OPTION) {
								Reader.deleteFiles(modelFolder);
								train(noOfNodes, modelFolder);
							}
						} else {
							train(noOfNodes, modelFolder);
						}
					}
				}
			});

			Style.buttonStyle(btnTrain);
			btnTrain.setBounds(330, 348, 112, 45);
		}
		return btnTrain;
	}

	private JTextField getTxtMaxIter() {
		if (txtIter == null) {
			txtIter = new JTextField();
			txtIter.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtIter.setColumns(10);
			txtIter.setBounds(181, 279, 91, 30);
		}
		return txtIter;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("Max. iterations:");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label.setBounds(35, 279, 132, 30);
		}
		return label;
	}

	private JComboBox<String> getCmbGraphType() {
		if (cmbGraphType == null) {
			cmbGraphType = new JComboBox<String>();
			cmbGraphType.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if (cmbGraphType.getSelectedItem().toString()
							.contains("probability")) {
						txtProb.setEnabled(true);
						txtProb.setEditable(true);
					} else {
						txtProb.setEnabled(false);
						txtProb.setEditable(false);
					}
				}
			});
			cmbGraphType.setBounds(181, 39, 315, 30);
			cmbGraphType.addItem("choose graph type");
			cmbGraphType.addItem("directed graph");
			cmbGraphType.addItem("directed graph with edge probability");
			cmbGraphType.addItem("directed acyclic graph");
			cmbGraphType.addItem("directed graph without direct feedback");
			cmbGraphType.addItem("chain");
			cmbGraphType.addItem("binary tree");
		}
		return cmbGraphType;
	}

	public void train(int noOfNodes, String modelFolder) {

		double alpha = Double.parseDouble(txtAlpha.getText());
		double beta = Double.parseDouble(txtBeta.getText());
		double lr = Double.parseDouble(txtLR.getText());
		int maxIter = Integer.parseInt(txtIter.getText());

		ProgressBar frame = new ProgressBar(maxIter);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		double[][] s = generateGraph(noOfNodes);
		double[] r = ArrayGenerator.generateArray(noOfNodes, 5);
		CalculationsAsymmetric c = new CalculationsAsymmetric(s, r);
		double[] y = c.y(alphaGen, betaGen, 0.05);
		boolean both = false;
		if (chckbxSymmetric.isSelected()) {
			both = true;
		}
		TrainWithRandomForGUI t = new TrainWithRandomForGUI(modelFolder, frame,
				mainFrame, s, r, y, alpha, beta, lr, maxIter, panelForTable,
				both, 10, 10, lblTime,alphaGen,betaGen);
		t.start();

	}

	public String validateData() {
		if (cmbGraphType.getSelectedIndex() == 0) {
			return "Choose graph type.";
		}
		try {
			Integer.parseInt(txtNoOfNodes.getText());
		} catch (NumberFormatException e) {
			return "No. of nodes should be integer.";
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

		if (cmbGraphType.getSelectedItem().toString().contains("probability")) {
			try {
				Double.parseDouble(txtProb.getText());
			} catch (NumberFormatException e) {
				return "Probability should be number.";
			}
		}
		return null;
	}

	public double[][] generateGraph(int noOfNodes) {
		String type = Writer.folderName(cmbGraphType.getSelectedItem()
				.toString());
		double probability = 0;
		if (cmbGraphType.getSelectedItem().toString().contains("probability")) {
			probability = Double.parseDouble(txtProb.getText());
		}
		return GraphGenerator.generateGraphByType(noOfNodes, type, probability);
	}

	private JCheckBox getChckbxSymmetric() {
		if (chckbxSymmetric == null) {
			chckbxSymmetric = new JCheckBox("");
			chckbxSymmetric.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			chckbxSymmetric.setBounds(181, 327, 91, 23);
		}
		return chckbxSymmetric;
	}

	private JLabel getLblTrainSymmetric() {
		if (lblTrainSymmetric == null) {
			lblTrainSymmetric = new JLabel("Train symmetric:");
			lblTrainSymmetric.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTrainSymmetric.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblTrainSymmetric.setBounds(35, 320, 132, 30);
		}
		return lblTrainSymmetric;
	}

	public void createMainFolders() {
		URL location = MainFrame.class.getProtectionDomain().getCodeSource()
				.getLocation();
		String path1 = location.getFile();
		path1 = path1.substring(1, path1.lastIndexOf("/"));
		String mainPath = path1.substring(0, path1.lastIndexOf("/"));
		String path = mainPath + "/RandomModels";
		Writer.createFolder(path);
		for (int i = 1; i < cmbGraphType.getItemCount(); i++) {
			String folder = Writer.folderName(cmbGraphType.getItemAt(i)
					.toString());
			Writer.createFolder(mainPath + "/RandomModels/" + folder);
		}
	}

	public boolean checkModel(String path) {
		return Writer.checkFolder(path);
	}

	private JPanel getPanelForTable() {
		if (panelForTable == null) {
			panelForTable = new JPanel();
			panelForTable.setLayout(null);
			panelForTable.setBounds(22, 404, 850, 130);
		}
		return panelForTable;
	}

	private JLabel getLblTime() {
		if (lblTime == null) {
			lblTime = new JLabel("Time:");
			lblTime.setVisible(false);
			lblTime.setHorizontalAlignment(SwingConstants.LEFT);
			lblTime.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblTime.setBounds(32, 545, 421, 30);
		}
		return lblTime;
	}

	public String readParametersFromCfg() {
		try {
			Map<String, String> params = Reader.readCfg();
			try {
				alphaGen = Integer.parseInt(params.get("AlphaGen").toString());
				betaGen = Integer.parseInt(params.get("BetaGen").toString());
				alpha = Integer.parseInt(params.get("Alpha").toString());
				beta = Integer.parseInt(params.get("Beta").toString());
				lr = Double.parseDouble(params.get("LR").toString());
				iterations = Integer.parseInt(params.get("Iterations")
						.toString());
			} catch (NumberFormatException e) {
				return "Configuration file reading failed. File has wrong format.";
			}
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
	}
}
