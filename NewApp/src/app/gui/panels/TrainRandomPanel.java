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
import app.file.io.Reader;
import app.file.io.Writer;
import app.gui.frames.ProgressBar;
import app.gui.style.Style;
import app.gui.threads.TrainWithRandomForGUI;

import javax.swing.JComboBox;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;

public class TrainRandomPanel extends JPanel {

	private static final long serialVersionUID = 2574347379339766542L;
	private JLabel lblType;
	private JButton btnQuestionS;
	private JTextField txtProb;
	private JLabel lblRArrayFile;
	private JLabel lblAlpha;
	private JLabel lblFirstBeta;
	private JTextField txtAlpha;
	private JTextField txtBeta;
	private JLabel lblLearningRate;
	private JTextField txtLr;
	private JLabel lblYArrayFile;
	private JTextField txtNoOfNodes;
	private JButton btnTrain;
	private JTextField txtMaxIter;
	private JLabel label;
	private JComboBox<String> cmbGraphType;
	private JFrame mainFrame;
	private JCheckBox chckbxSymmetric;
	private JLabel lblTrainSymmetric;
	private JPanel panelForTable;

	/**
	 * Create the panel.
	 */
	public TrainRandomPanel(JFrame mainFrame) {
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
		setUpDefaultValues();
		createMainFolders();
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
		if (btnQuestionS == null) {
			btnQuestionS = new JButton("");
			btnQuestionS.addActionListener(new ActionListener() {
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
											+ "if there is direct connection from A to B, there is no direct connection from B to A",
									"Help", JOptionPane.QUESTION_MESSAGE,
									Style.questionIcon());
				}
			});

			btnQuestionS.setBounds(515, 36, 30, 30);
			Style.questionButtonStyle(btnQuestionS);
		}
		return btnQuestionS;
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
		if (txtLr == null) {
			txtLr = new JTextField();
			txtLr.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtLr.setColumns(10);
			txtLr.setBounds(181, 238, 91, 30);
		}
		return txtLr;
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
						String modelFolder = "RandomModels/" + model + "/"
								+ noOfNodes + "nodes";
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
		if (txtMaxIter == null) {
			txtMaxIter = new JTextField();
			txtMaxIter.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtMaxIter.setColumns(10);
			txtMaxIter.setBounds(181, 279, 91, 30);
		}
		return txtMaxIter;
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
		double lr = Double.parseDouble(txtLr.getText());
		int maxIter = Integer.parseInt(txtMaxIter.getText());

		ProgressBar frame = new ProgressBar(maxIter);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		double[][] s = generateGraph(noOfNodes);
		double[] r = ArrayGenerator.generateArray(noOfNodes, 5);
		CalculationsAsymmetric c = new CalculationsAsymmetric(s, r);
		double[] y = c.y(5, 1, 0.05);
		boolean both = false;
		if (chckbxSymmetric.isSelected()) {
			both = true;
		}
		TrainWithRandomForGUI t = new TrainWithRandomForGUI(modelFolder, frame,
				mainFrame, s, r, y, alpha, beta, lr, maxIter, panelForTable,
				both, 10, 10);
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
			Double.parseDouble(txtLr.getText());
		} catch (NumberFormatException e) {
			return "Learning rate should be number.";
		}

		try {
			Integer.parseInt(txtMaxIter.getText());
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

	public void setUpDefaultValues() {
		txtAlpha.setText("1");
		txtBeta.setText("1");
		txtLr.setText("0.01");
		txtMaxIter.setText("1000");
	}

	public void createMainFolders() {
		Writer.createFolder("RandomModels");
		for (int i = 1; i < cmbGraphType.getItemCount(); i++) {
			String folder = Writer.folderName(cmbGraphType.getItemAt(i)
					.toString());
			Writer.createFolder("RandomModels/" + folder);
		}
	}

	public boolean checkModel(String path) {
		return Writer.checkFolder(path);
	}

	private JPanel getPanelForTable() {
		if (panelForTable == null) {
			panelForTable = new JPanel();
			panelForTable.setLayout(null);
			panelForTable.setBounds(21, 402, 850, 130);
		}
		return panelForTable;
	}
}
