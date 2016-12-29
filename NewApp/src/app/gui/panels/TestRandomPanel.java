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

import app.data.generators.GraphGenerator;
import app.exceptions.ConfigurationParameterseException;
import app.file.io.Reader;
import app.file.io.Writer;
import app.gui.frames.MainFrame;
import app.gui.frames.ProgressBar;
import app.gui.style.Style;
import app.gui.threads.TestWithRandomForGUI;

import javax.swing.JComboBox;

import java.awt.event.ItemListener;
import java.net.URL;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;

public class TestRandomPanel extends JPanel {

	private static final long serialVersionUID = 4390862740626681899L;
	private JLabel lblType;
	private JLabel lblRArrayFile;
	private JTextField txtNoOfNodes;
	private JButton btnTrain;
	private JComboBox<String> cmbModel;
	private JFrame mainFrame;
	private JLabel label;
	private JTextField txtProb;
	private JLabel lblTimes;
	private JTextField txtTimes;
	private JPanel panelForTable;
	private int alphaGen;
	private int betaGen;

	/**
	 * Create the panel.
	 */
	public TestRandomPanel(JFrame mainFrame) {
		if (Reader.checkFile("cfg.txt")) {
			String result = readParametersFromCfg();
			if (result != null) {
				JOptionPane.showMessageDialog(mainFrame,
						result + " Please configure parameters values in Settings->Configuration.", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				setBackground(UIManager.getColor("Button.background"));
				setLayout(null);
				add(getLblType());
				add(getLblRArrayFile());
				add(getTxtNoOfNodes());
				add(getBtnTrain());
				add(getCmbModel());
				this.mainFrame = mainFrame;
				add(getLabel());
				add(getTxtProb());
				add(getLblTimes());
				add(getTxtTimes());
				add(getPanelForTable());
			}
		}
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
						JOptionPane.showMessageDialog(mainFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						URL location = MainFrame.class.getProtectionDomain().getCodeSource().getLocation();

						String model = Reader.jarFile()  + "/RandomModels/"
								+ cmbModel.getSelectedItem().toString().replaceAll(" - ", "/");
						int noOfNodes = Integer.parseInt(txtNoOfNodes.getText());
						int times = Integer.parseInt(txtTimes.getText());
						double probability = 0;
						if (cmbModel.getSelectedItem().toString().contains("probability")) {
							probability = Double.parseDouble(txtProb.getText());
						}
						ProgressBar frame = new ProgressBar(times);
						frame.pack();
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
						TestWithRandomForGUI test = new TestWithRandomForGUI(frame, mainFrame, panelForTable, model,
								noOfNodes, times, probability, alphaGen, betaGen);
						test.start();
					}
				}
			});

			Style.buttonStyle(btnTrain);
			btnTrain.setBounds(305, 209, 112, 45);
		}
		return btnTrain;
	}

	private JComboBox<String> getCmbModel() {
		if (cmbModel == null) {
			cmbModel = new JComboBox<String>();
			cmbModel.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if (cmbModel.getSelectedItem().toString().contains("probability")) {

						String model = Reader.jarFile()  + "/RandomModels/"
								+ cmbModel.getSelectedItem().toString().replaceAll(" - ", "/");
						String probModel = model.split("/")[model.split("/").length - 1];
						probModel = probModel.substring(probModel.indexOf("s") + 1, probModel.indexOf("p"));
						txtProb.setEnabled(true);
						txtProb.setText(probModel);
					}
				}
			});
			cmbModel.setBounds(181, 39, 417, 30);
			cmbModel.addItem("choose model");

			String[] files = Reader.getAllFiles(Reader.jarFile()  + "/RandomModels");
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
			return "No. of graphs should be integer.";
		}

		return null;
	}

	public double[][] generateGraph(int noOfNodes) {
		if (cmbModel.getSelectedIndex() == 1) {
			return GraphGenerator.generateDirectedGraph(noOfNodes);
		} else if (cmbModel.getSelectedIndex() == 2) {
			double prob = Double.parseDouble(txtProb.getText());
			return GraphGenerator.generateDirectedGraphWithEdgeProbability(noOfNodes, prob);
		} else if (cmbModel.getSelectedIndex() == 3) {
			return GraphGenerator.generateDirectedAcyclicGraph(noOfNodes);
		}
		return null;
	}

	public void createMainFolders() {
		Writer.createFolder("Models");
		for (int i = 1; i < cmbModel.getItemCount(); i++) {
			String folder = Writer.folderName(cmbModel.getItemAt(i).toString());
			Writer.createFolder(Reader.jarFile() + "/Models/" + folder);
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
			lblTimes = new JLabel("No. of graphs:");
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

	public String readParametersFromCfg() {
		try {
			Map<String, String> params = Reader.readCfg();
			try {
				alphaGen = Integer.parseInt(params.get("AlphaGen").toString());
				betaGen = Integer.parseInt(params.get("BetaGen").toString());
			} catch (NumberFormatException e) {
				return "Configuration file reading failed. File has wrong format.";
			}
		} catch (ConfigurationParameterseException e) {
			return e.getMessage();
		}
		return null;
	}
}
