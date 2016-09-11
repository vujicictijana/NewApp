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
import app.gui.threads.DirGCRFTrainMyModelForGUI;
import app.gui.threads.GCRFTrainMyModelForGUI;
import app.gui.threads.UmGCRFTrainMyModelForGUI;
import app.predictors.helper.Helper;
import app.predictors.linearregression.MyLR;
import app.predictors.neuralnetwork.MyNN;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
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

public class AddDatasetPanel extends JPanel {

	private static final long serialVersionUID = 7542338256284881226L;
	private JTextField txtSFile;
	private JLabel lblFile;
	private JButton btnBrowseS;
	private JButton btnQuestionS;
	private JLabel lblModelName;
	private JTextField txtName;
	private JLabel lblRArrayFile;
	private JTextField txtXFile;
	private JButton btnBrowseX;
	private JButton btnQuestionX;
	private JLabel lblYArrayFile;
	private JTextField txtYFile;
	private JButton btnBrowseY;
	private JButton btnQuestionY;
	private JButton btnTrain;
	private JFileChooser fc;
	private JPanel panel;
	private JFrame mainFrame;
	private JLabel label;
	private JTextField txtNodes;

	private JLabel lblData;
	private JLabel lblTestData;
	private JTextField txtSTestFile;
	private JLabel label_1;
	private JButton btnBrowseSTest;
	private JButton btnQuestionSTest;
	private JLabel label_3;
	private JTextField txtXTestFile;
	private JButton btnBrowseXTest;
	private JButton btnQuestionXTest;
	private JLabel label_4;
	private JTextField txtYTestFile;
	private JButton btnBrowseYTest;
	private JButton btnQuestionYTest;
	private JLabel label_5;
	private JTextField txtNodesTest;
	private JCheckBox chkTogether;

	public AddDatasetPanel(JFrame mainFrame) {
		setBounds(new Rectangle(0, 0, 900, 650));
		setMinimumSize(new Dimension(500, 500));

		setBackground(UIManager.getColor("Button.background"));
		setLayout(null);
		add(getTxtSFile());
		add(getLblFile());
		add(getBtnBrowseS());
		add(getBtnQuestionS());
		add(getLblModelName());
		add(getTxtName());
		add(getLblRArrayFile());
		add(getTxtXFile());
		add(getBtnBrowseX());
		add(getBtnQuestionX());
		add(getLblYArrayFile());
		add(getTxtYFile());
		add(getBtnBrowseY());
		add(getBtnQuestionY());
		add(getBtnTrain());
		fc = new JFileChooser();
		panel = this;
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"TEXT FILES", "txt", "text");
		fc.setFileFilter(filter);
		URL location = MainFrame.class.getProtectionDomain().getCodeSource()
				.getLocation();
		String path = location.getFile();
		path = path.substring(1, path.lastIndexOf("/"));
		path = path.substring(0, path.lastIndexOf("/"));
		fc.setCurrentDirectory(new File(path));
		this.mainFrame = mainFrame;
		// add(getLblTime());
		add(getLabel());
		add(getTxtNodes());
		add(getLblData());
		add(getLblTestData());
		add(getTxtSTestFile());
		add(getLabel_1());
		add(getBtnBrowseSTest());
		add(getBtnQuestionSTest());
		add(getLabel_3());
		add(getTxtXTestFile());
		add(getBtnBrowseXTest());
		add(getBtnQuestionXTest());
		add(getLabel_4());
		add(getTxtYTestFile());
		add(getBtnBrowseYTest());
		add(getBtnQuestionYTest());
		add(getLabel_5());
		add(getTxtNodesTest());
		add(getChkTogether());

	}

	private JTextField getTxtSFile() {
		if (txtSFile == null) {
			txtSFile = new JTextField();
			txtSFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtSFile.setBounds(180, 90, 315, 30);
			txtSFile.setColumns(10);
		}
		return txtSFile;
	}

	private JLabel getLblFile() {
		if (lblFile == null) {
			lblFile = new JLabel("File with edges:");
			lblFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblFile.setBounds(44, 88, 120, 30);
		}
		return lblFile;
	}

	private JButton getBtnBrowseS() {
		if (btnBrowseS == null) {
			btnBrowseS = new JButton("Browse");
			btnBrowseS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					chooseFile(txtSFile);
				}
			});
			Style.buttonStyle(btnBrowseS);
			btnBrowseS.setBounds(512, 88, 100, 30);

		}
		return btnBrowseS;
	}

	private JButton getBtnQuestionS() {
		if (btnQuestionS == null) {
			btnQuestionS = new JButton("");
			btnQuestionS.setBounds(631, 88, 30, 30);
			Style.questionButtonStyle(btnQuestionS);
			btnQuestionS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JOptionPane
							.showMessageDialog(
									mainFrame,
									"Text file (.txt) that contains data about connections between nodes for train data."
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
			lblModelName = new JLabel("Dataset name:");
			lblModelName.setHorizontalAlignment(SwingConstants.RIGHT);
			lblModelName.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblModelName.setBounds(10, 47, 154, 30);
		}
		return lblModelName;
	}

	private JTextField getTxtName() {
		if (txtName == null) {
			txtName = new JTextField();
			txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtName.setColumns(10);
			txtName.setBounds(180, 49, 315, 30);
		}
		return txtName;
	}

	private JLabel getLblRArrayFile() {
		if (lblRArrayFile == null) {
			lblRArrayFile = new JLabel("File with attributes:");
			lblRArrayFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblRArrayFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblRArrayFile.setBounds(24, 128, 140, 30);
		}
		return lblRArrayFile;
	}

	private JTextField getTxtXFile() {
		if (txtXFile == null) {
			txtXFile = new JTextField();
			txtXFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtXFile.setColumns(10);
			txtXFile.setBounds(180, 128, 315, 30);
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
			btnBrowseX.setBounds(512, 128, 100, 30);
		}
		return btnBrowseX;
	}

	private JButton getBtnQuestionX() {
		if (btnQuestionX == null) {
			btnQuestionX = new JButton("");
			btnQuestionX.setBounds(631, 128, 30, 30);
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
									"Text file (.txt) that contains value of each atribute for each node for train data."
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
			lblYArrayFile.setBounds(34, 168, 130, 30);
		}
		return lblYArrayFile;
	}

	private JTextField getTxtYFile() {
		if (txtYFile == null) {
			txtYFile = new JTextField();
			txtYFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtYFile.setColumns(10);
			txtYFile.setBounds(180, 168, 315, 30);
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
			btnBrowseY.setBounds(512, 168, 100, 30);
		}
		return btnBrowseY;
	}

	private JButton getBtnQuestionY() {
		if (btnQuestionY == null) {
			btnQuestionY = new JButton("");
			btnQuestionY.setBounds(631, 168, 30, 30);
			Style.questionButtonStyle(btnQuestionY);
			btnQuestionY.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JOptionPane
							.showMessageDialog(
									mainFrame,
									"Text file (.txt) that contains actual output for each node for train data."
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
			btnTrain = new JButton("SAVE");
			btnTrain.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String message = validateData();
					if (message != null) {
						JOptionPane.showMessageDialog(mainFrame, message,
								"Error", JOptionPane.ERROR_MESSAGE);
					} else {

					}
				}

			});
			Style.buttonStyle(btnTrain);
			btnTrain.setBounds(322, 537, 112, 45);
		}
		return btnTrain;
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
		File matrixFile = new File(txtSFile.getText());
		File xFile = new File(txtXFile.getText());
		File yFile = new File(txtYFile.getText());

		URL location = MainFrame.class.getProtectionDomain().getCodeSource()
				.getLocation();
		String path1 = location.getFile();
		path1 = path1.substring(1, path1.lastIndexOf("/"));
		String mainPath = path1.substring(0, path1.lastIndexOf("/"));
		Writer.createFolder(mainPath + "/MyModels" + method);
		String path = mainPath + "/MyModels" + method + "/" + txtName.getText();
		Writer.createFolder(path);

		String dataPath = mainPath + "/MyModels" + method + "/"
				+ txtName.getText() + "/data";
		Writer.createFolder(dataPath);
		Writer.copyFile(matrixFile, dataPath + "/matrix.txt");
		Writer.copyFile(xFile, dataPath + "/x.txt");

		Writer.copyFile(yFile, dataPath + "/y.txt");
		Writer.createFolder(path);
		return path;
	}

	public String validateData() {
		if (txtName.getText().equals("")) {
			return "Insert dataset name.";
		}
		if (txtSFile.getText().equals("")) {
			return "Choose matrix file for training.";
		}
		if (txtXFile.getText().equals("")) {
			return "Choose file with attributes values for training.";
		}
		if (txtYFile.getText().equals("")) {
			return "Choose file with output values for training.";
		}
		try {
			Integer.parseInt(txtNodes.getText());
		} catch (NumberFormatException e) {
			return "No. of nodes for training should be integer.";
		}
		if (!chkTogether.isSelected()) {
			if (txtSTestFile.getText().equals("")) {
				return "Choose matrix file for test.";
			}
			if (txtXTestFile.getText().equals("")) {
				return "Choose file with attributes values for test.";
			}
			if (txtYTestFile.getText().equals("")) {
				return "Choose file with output values for test.";
			}
			try {
				Integer.parseInt(txtNodesTest.getText());
			} catch (NumberFormatException e) {
				return "No. of nodes for test should be integer.";
			}
		}

		return null;
	}

	public String validateDataForTestPredictor() {

		if (txtSFile.getText().equals("")) {
			return "Choose file with edges.";
		}
		if (txtXFile.getText().equals("")) {
			return "Choose file with attributes values.";
		}
		if (txtYFile.getText().equals("")) {
			return "Choose file with output values.";
		}
		try {
			Integer.parseInt(txtNodes.getText());
		} catch (NumberFormatException e) {
			return "No. of nodes should be integer.";
		}

		return null;
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

	private JTextField getTxtNodes() {
		if (txtNodes == null) {
			txtNodes = new JTextField();
			txtNodes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNodes.setColumns(10);
			txtNodes.setBounds(180, 208, 91, 30);
		}
		return txtNodes;
	}

	private JLabel getLblData() {
		if (lblData == null) {
			lblData = new JLabel("TRAIN DATA:");
			lblData.setForeground(Color.WHITE);
			lblData.setBackground(Color.GRAY);
			lblData.setHorizontalAlignment(SwingConstants.CENTER);
			lblData.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblData.setBounds(0, 0, 901, 30);
			lblData.setOpaque(true);
		}
		return lblData;
	}

	private JLabel getLblTestData() {
		if (lblTestData == null) {
			lblTestData = new JLabel("TEST DATA:");
			lblTestData.setOpaque(true);
			lblTestData.setHorizontalAlignment(SwingConstants.CENTER);
			lblTestData.setForeground(Color.WHITE);
			lblTestData.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblTestData.setBackground(Color.GRAY);
			lblTestData.setBounds(0, 276, 901, 30);
		}
		return lblTestData;
	}

	private JTextField getTxtSTestFile() {
		if (txtSTestFile == null) {
			txtSTestFile = new JTextField();
			txtSTestFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtSTestFile.setColumns(10);
			txtSTestFile.setBounds(180, 317, 315, 30);
		}
		return txtSTestFile;
	}

	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("File with edges:");
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);
			label_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_1.setBounds(44, 317, 120, 30);
		}
		return label_1;
	}

	private JButton getBtnBrowseSTest() {
		if (btnBrowseSTest == null) {
			btnBrowseSTest = new JButton("Browse");
			btnBrowseSTest.setBounds(512, 317, 100, 30);
			btnBrowseSTest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					chooseFile(txtSTestFile);
				}
			});
		}
		return btnBrowseSTest;
	}

	private JButton getBtnQuestionSTest() {
		if (btnQuestionSTest == null) {
			btnQuestionSTest = new JButton("");
			btnQuestionSTest.setBounds(631, 317, 30, 30);
			Style.questionButtonStyle(btnQuestionSTest);
			btnQuestionSTest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JOptionPane
							.showMessageDialog(
									mainFrame,
									"Text file (.txt) that contains data about connections between nodes for test data."
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
		return btnQuestionSTest;
	}

	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel("File with attributes:");
			label_3.setHorizontalAlignment(SwingConstants.RIGHT);
			label_3.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_3.setBounds(24, 357, 140, 30);
		}
		return label_3;
	}

	private JTextField getTxtXTestFile() {
		if (txtXTestFile == null) {
			txtXTestFile = new JTextField();
			txtXTestFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtXTestFile.setColumns(10);
			txtXTestFile.setBounds(180, 357, 315, 30);
		}
		return txtXTestFile;
	}

	private JButton getBtnBrowseXTest() {
		if (btnBrowseXTest == null) {
			btnBrowseXTest = new JButton("Browse");
			btnBrowseXTest.setBounds(512, 357, 100, 30);
			btnBrowseXTest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					chooseFile(txtXTestFile);
				}
			});
		}
		return btnBrowseXTest;
	}

	private JButton getBtnQuestionXTest() {
		if (btnQuestionXTest == null) {
			btnQuestionXTest = new JButton("");
			btnQuestionXTest.setBounds(631, 357, 30, 30);
			Style.questionButtonStyle(btnQuestionXTest);
			btnQuestionXTest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// "Text file (.txt) that contains output predicted by unstructured predictor for each node."
					// + "\nEach output should be in a separate line. "
					// +
					// "\nOrder of outputs should be consistent with ordinal numbers of nodes in the file with edges (S)."
					JOptionPane
							.showMessageDialog(
									mainFrame,
									"Text file (.txt) that contains value of each atribute for each node for test data."
											+ "\nAtributes for each node should be in a separate line. "
											+ "\nAtributes should be comma separated. "
											+ "\nAll atributes should be numbers. "
											+ "\nOrder should be consistent with ordinal numbers of nodes in the file with edges.",
									"Help", JOptionPane.QUESTION_MESSAGE,
									Style.questionIcon());
				}
			});
		}
		return btnQuestionXTest;
	}

	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel("File with outputs:");
			label_4.setHorizontalAlignment(SwingConstants.RIGHT);
			label_4.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_4.setBounds(34, 397, 130, 30);
		}
		return label_4;
	}

	private JTextField getTxtYTestFile() {
		if (txtYTestFile == null) {
			txtYTestFile = new JTextField();
			txtYTestFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtYTestFile.setColumns(10);
			txtYTestFile.setBounds(180, 397, 315, 30);
		}
		return txtYTestFile;
	}

	private JButton getBtnBrowseYTest() {
		if (btnBrowseYTest == null) {
			btnBrowseYTest = new JButton("Browse");
			btnBrowseYTest.setBounds(512, 397, 100, 30);
			btnBrowseYTest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					chooseFile(txtYTestFile);
				}
			});

		}
		return btnBrowseYTest;
	}

	private JButton getBtnQuestionYTest() {
		if (btnQuestionYTest == null) {
			btnQuestionYTest = new JButton("");
			btnQuestionYTest.setBounds(631, 397, 30, 30);
			Style.questionButtonStyle(btnQuestionYTest);
			btnQuestionYTest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JOptionPane
							.showMessageDialog(
									mainFrame,
									"Text file (.txt) that contains actual output for each node for test data."
											+ "\nEach output should be in a separate line. "
											+ "\nOrder of outputs should be consistent with ordinal numbers of nodes in the file with edges (S).",
									"Help", JOptionPane.QUESTION_MESSAGE,
									Style.questionIcon());
				}
			});
		}
		return btnQuestionYTest;
	}

	private JLabel getLabel_5() {
		if (label_5 == null) {
			label_5 = new JLabel("No. of nodes:");
			label_5.setHorizontalAlignment(SwingConstants.RIGHT);
			label_5.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_5.setBounds(64, 439, 100, 30);
		}
		return label_5;
	}

	private JTextField getTxtNodesTest() {
		if (txtNodesTest == null) {
			txtNodesTest = new JTextField();
			txtNodesTest.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNodesTest.setColumns(10);
			txtNodesTest.setBounds(180, 438, 91, 30);
		}
		return txtNodesTest;
	}

	private JCheckBox getChkTogether() {
		if (chkTogether == null) {
			chkTogether = new JCheckBox(
					"train and test data are provided together");
			chkTogether.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if (chkTogether.isSelected()) {
						txtSTestFile.setEnabled(false);
						txtXTestFile.setEnabled(false);
						txtYTestFile.setEnabled(false);
						txtNodesTest.setEnabled(false);
						btnBrowseSTest.setEnabled(false);
						btnBrowseXTest.setEnabled(false);
						btnBrowseYTest.setEnabled(false);
					} else {
						txtSTestFile.setEnabled(true);
						txtXTestFile.setEnabled(true);
						txtYTestFile.setEnabled(true);
						txtNodesTest.setEnabled(true);
						btnBrowseSTest.setEnabled(true);
						btnBrowseXTest.setEnabled(true);
						btnBrowseYTest.setEnabled(true);
					}
				}
			});
			chkTogether.setBounds(180, 485, 260, 23);
		}
		return chkTogether;
	}
}
