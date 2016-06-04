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
import app.gui.threads.TestWithRandomForGUI;

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
	private JLabel lblRArrayFile;
	private JTextField txtNoOfNodes;
	private JButton btnTrain;
	private JComboBox cmbModel;
	private JPanel panel;
	private JFrame mainFrame;
	private JLabel label;
	private JTextField txtProb;
	private JLabel lblTimes;
	private JTextField txtTimes;
	private JPanel panelForTable;

	/**
	 * Create the panel.
	 */
	public TestRandomPanel(JFrame mainFrame) {
		setBackground(UIManager.getColor("Button.background"));
		setLayout(null);
		add(getLblType());
		add(getLblRArrayFile());
		add(getTxtNoOfNodes());
		add(getBtnTrain());
		add(getCmbModel());
		panel = this;
		this.mainFrame = mainFrame;
		add(getLabel());
		add(getTxtProb());
		add(getLblTimes());
		add(getTxtTimes());
		add(getPanelForTable());
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
					String message = validateData();
					if (message != null) {
						JOptionPane.showMessageDialog(mainFrame, message,
								"Error", JOptionPane.ERROR_MESSAGE);
					} else {
						String model = "RandomModels/"
								+ cmbModel.getSelectedItem().toString()
										.replaceAll(" - ", "/");
						int noOfNodes = Integer.parseInt(txtNoOfNodes.getText());
						int times = Integer.parseInt(txtTimes.getText());
						double probability = 0;
						if (cmbModel.getSelectedItem().toString()
								.contains("probability")) {
							probability = Double.parseDouble(txtProb.getText());
						}
						ProgressBar frame = new ProgressBar(times);
						frame.pack();
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
						TestWithRandomForGUI test = new TestWithRandomForGUI(frame, mainFrame,
								panelForTable, model, noOfNodes, times,
								probability);
						test.start();
					}
				}
			});

			Style.buttonStyle(btnTrain);
			btnTrain.setBounds(305, 209, 112, 45);
		}
		return btnTrain;
	}

	private JComboBox getCmbModel() {
		if (cmbModel == null) {
			cmbModel = new JComboBox();
			cmbModel.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if (cmbModel.getSelectedItem().toString()
							.contains("probability")) {
						String model = "RandomModels/"
								+ cmbModel.getSelectedItem().toString()
										.replaceAll(" - ", "/");
						String probModel = model.split("/")[model.split("/").length - 1];
						probModel = probModel.substring(
								probModel.indexOf("s") + 1,
								probModel.indexOf("p"));
						txtProb.setEnabled(true);
						txtProb.setText(probModel);
					}
				}
			});
			cmbModel.setBounds(181, 39, 417, 30);
			cmbModel.addItem("choose model");
			String[] files = Reader.getAllFiles("RandomModels");
			for (int i = 0; i < files.length; i++) {
				cmbModel.addItem(files[i]);
			}
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

		try {
			Integer.parseInt(txtTimes.getText());
		} catch (NumberFormatException e) {
			return "Repeat n times should be integer.";
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

	private JLabel getLblTimes() {
		if (lblTimes == null) {
			lblTimes = new JLabel("Repeat n times:");
			lblTimes.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTimes.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblTimes.setBounds(10, 158, 155, 30);
		}
		return lblTimes;
	}

	private JTextField getTxtTimes() {
		if (txtTimes == null) {
			txtTimes = new JTextField();
			txtTimes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtTimes.setColumns(10);
			txtTimes.setBounds(181, 160, 91, 30);
		}
		return txtTimes;
	}

	private JPanel getPanelForTable() {
		if (panelForTable == null) {
			panelForTable = new JPanel();
			panelForTable.setBounds(45, 268, 850, 285);
			panelForTable.setLayout(null);
		}
		return panelForTable;
	}
}
