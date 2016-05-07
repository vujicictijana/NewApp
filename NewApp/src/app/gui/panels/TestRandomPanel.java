package app.gui.panels;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JToolBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.border.LineBorder;

import java.awt.SystemColor;

import javax.swing.UIManager;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;

import app.algorithms.asymmetric.CalculationsAsymmetric;
import app.algorithms.asymmetric.GradientDescentAsymmetric;
import app.data.generators.ArrayGenerator;
import app.data.generators.GraphGenerator;
import app.file.io.Reader;
import app.file.io.Writer;
import app.gui.frames.ProgressBar;
import app.gui.style.Style;

import javax.swing.JComboBox;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JTable;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JCheckBox;

public class TestRandomPanel extends JPanel {
	private JLabel lblType;
	private JButton btnQuestionS;
	private JLabel lblRArrayFile;
	private JTextField txtNoOfNodes;
	private JButton btnTrain;
	private JComboBox cmbModel;
	private JPanel panel;
	private JFrame mainFrame;
	private JLabel label;
	private JTextField txtProb;

	/**
	 * Create the panel.
	 */
	public TestRandomPanel(JFrame mainFrame) {
		setBackground(UIManager.getColor("Button.background"));
		setLayout(null);
		add(getLblType());
		add(getBtnQuestionS());
		add(getLblRArrayFile());
		add(getTxtNoOfNodes());
		add(getBtnTrain());
		add(getCmbModel());
		panel = this;
		this.mainFrame = mainFrame;
		add(getLabel());
		add(getTxtProb());
		setUpDefaultValues();
		createMainFolders();
	}

	private JLabel getLblType() {
		if (lblType == null) {
			lblType = new JLabel("Model:");
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

	private JLabel getLblRArrayFile() {
		if (lblRArrayFile == null) {
			lblRArrayFile = new JLabel("No. of nodes:");
			lblRArrayFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblRArrayFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblRArrayFile.setBounds(65, 78, 100, 30);
		}
		return lblRArrayFile;
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
			btnTrain = new JButton("TEST");
			btnTrain.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

				}
			});

			Style.buttonStyle(btnTrain);
			btnTrain.setBounds(305, 161, 112, 45);
		}
		return btnTrain;
	}

	private JComboBox getCmbModel() {
		if (cmbModel == null) {
			cmbModel = new JComboBox();
			cmbModel.setBounds(181, 39, 315, 30);
			cmbModel.addItem("choose model");
			String[] files = Reader.getAllFiles("RandomModels");
			for (int i = 0; i < files.length; i++) {
				cmbModel.addItem(files[i]);
			}
			cmbModel.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if (cmbModel.getSelectedItem().toString()
							.contains("probability")) {
						txtProb.setEnabled(true);
						txtProb.setEditable(true);
					} else {
						txtProb.setEnabled(false);
						txtProb.setEditable(false);
					}
				}
			});
		}
		return cmbModel;
	}

	public String validateData() {
		if (cmbModel.getSelectedIndex() == 0) {
			return "Choose graph model.";
		}
		try {
			Integer.parseInt(txtNoOfNodes.getText());
		} catch (NumberFormatException e) {
			return "No. of nodes should be integer.";
		}

		return null;
	}

	public double[][] generateGraph(int noOfNodes) {
		if (cmbModel.getSelectedIndex() == 1) {
			return GraphGenerator.generateDirectedGraph(noOfNodes);
		} else if (cmbModel.getSelectedIndex() == 2) {
			double prob = Double.parseDouble(txtProb.getText());
			return GraphGenerator.generateDirectedGraphWithEdgeProbability(
					noOfNodes, prob);
		} else if (cmbModel.getSelectedIndex() == 3) {
			return GraphGenerator.generateDirectedAcyclicGraph(noOfNodes);
		}
		return null;
	}

	public void setUpDefaultValues() {
	}

	public void createMainFolders() {
		Writer.createFolder("Models");
		for (int i = 1; i < cmbModel.getItemCount(); i++) {
			String folder = Writer.folderName(cmbModel.getItemAt(i).toString());
			Writer.createFolder("Models/" + folder);
		}
	}

	public boolean checkModel(String path) {
		return Writer.checkFolder(path);
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("Probability:");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label.setBounds(65, 119, 100, 30);
		}
		return label;
	}

	private JTextField getTxtProb() {
		if (txtProb == null) {
			txtProb = new JTextField();
			txtProb.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtProb.setEnabled(false);
			txtProb.setEditable(false);
			txtProb.setColumns(10);
			txtProb.setBounds(181, 119, 91, 30);
		}
		return txtProb;
	}
}
