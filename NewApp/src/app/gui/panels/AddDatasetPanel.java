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
	private JLabel lblAttributes;
	private JTextField txtAttributes;
	private JLabel lblTimePoints;
	private JTextField txtTimePoints;
	private JCheckBox chkLearn;

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
		add(getLblAttributes());
		add(getTxtAttributes());
		add(getLblTimePoints());
		add(getTxtTimePoints());
		add(getChkLearn());

	}

	private JTextField getTxtSFile() {
		if (txtSFile == null) {
			txtSFile = new JTextField();
			txtSFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtSFile.setBounds(214, 94, 315, 30);
			txtSFile.setColumns(10);
		}
		return txtSFile;
	}

	private JLabel getLblFile() {
		if (lblFile == null) {
			lblFile = new JLabel("File with edges:");
			lblFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblFile.setBounds(78, 92, 120, 30);
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
			btnBrowseS.setBounds(546, 92, 100, 30);

		}
		return btnBrowseS;
	}

	private JButton getBtnQuestionS() {
		if (btnQuestionS == null) {
			btnQuestionS = new JButton("");
			btnQuestionS.setBounds(665, 92, 30, 30);
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
			lblModelName.setBounds(44, 51, 154, 30);
		}
		return lblModelName;
	}

	private JTextField getTxtName() {
		if (txtName == null) {
			txtName = new JTextField();
			txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtName.setColumns(10);
			txtName.setBounds(214, 53, 315, 30);
		}
		return txtName;
	}

	private JLabel getLblRArrayFile() {
		if (lblRArrayFile == null) {
			lblRArrayFile = new JLabel("File with attributes:");
			lblRArrayFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblRArrayFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblRArrayFile.setBounds(58, 132, 140, 30);
		}
		return lblRArrayFile;
	}

	private JTextField getTxtXFile() {
		if (txtXFile == null) {
			txtXFile = new JTextField();
			txtXFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtXFile.setColumns(10);
			txtXFile.setBounds(214, 132, 315, 30);
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
			btnBrowseX.setBounds(546, 132, 100, 30);
		}
		return btnBrowseX;
	}

	private JButton getBtnQuestionX() {
		if (btnQuestionX == null) {
			btnQuestionX = new JButton("");
			btnQuestionX.setBounds(665, 132, 30, 30);
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
			lblYArrayFile.setBounds(68, 172, 130, 30);
		}
		return lblYArrayFile;
	}

	private JTextField getTxtYFile() {
		if (txtYFile == null) {
			txtYFile = new JTextField();
			txtYFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtYFile.setColumns(10);
			txtYFile.setBounds(214, 172, 315, 30);
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
			btnBrowseY.setBounds(546, 172, 100, 30);
		}
		return btnBrowseY;
	}

	private JButton getBtnQuestionY() {
		if (btnQuestionY == null) {
			btnQuestionY = new JButton("");
			btnQuestionY.setBounds(665, 172, 30, 30);
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
						URL location = MainFrame.class.getProtectionDomain()
								.getCodeSource().getLocation();
						String path1 = location.getFile();
						path1 = path1.substring(1, path1.lastIndexOf("/"));
						String mainPath = path1.substring(0,
								path1.lastIndexOf("/"))
								+ "/Datasets";
						String name = txtName.getText();
						String dataPath = "";
						if (!chkTogether.isSelected()) {
							dataPath = mainPath + "/Networks/" + name;
						} else {
							dataPath = mainPath + "/TemporalNetworks/" + name;
						}
						if (Writer.checkFolder(dataPath)) {

							int selectedOption = JOptionPane
									.showConfirmDialog(
											mainFrame,

											"Dataset with name "
													+ name
													+ " already exists. Do you want to replace it?",
											"Question",
											JOptionPane.YES_NO_OPTION);
							if (selectedOption == JOptionPane.YES_OPTION) {
								Reader.deleteFiles(dataPath);
							}
							if (selectedOption == JOptionPane.NO_OPTION) {
								return;
							}
						}
						Writer.createFolder(dataPath);

						String xTrain = txtXFile.getText();
						String yTrain = txtYFile.getText();
						String sTrain = txtSFile.getText();

						int noOfNodes = Integer.parseInt(txtNodes.getText());

						if (!chkTogether.isSelected()) {
							String[] x = Reader.read(xTrain);
							double[] y = Reader.readArray(yTrain, noOfNodes);
							double[][] s = Reader.readGraph(sTrain, noOfNodes);

							String message1 = checkFilesTrain(noOfNodes, x, y,
									s,"train");

							if (message1 != null) {
								JOptionPane.showMessageDialog(mainFrame,
										message1, "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}

							String xTest = txtXTestFile.getText();
							String yTest = txtYTestFile.getText();
							String sTest = txtSTestFile.getText();

							int noOfNodesTest = Integer.parseInt(txtNodesTest.getText());
							
							String[] x1 = Reader.read(xTest);
							double[] y1 = Reader.readArray(yTest, noOfNodesTest);
							double[][] s1 = Reader.readGraph(sTest, noOfNodesTest);

							String message2 = checkFilesTrain(noOfNodesTest, x1,
									y1, s1, "test");

							if (message2 != null) {
								JOptionPane.showMessageDialog(mainFrame,
										message2, "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}

							Writer.copyFile(new File(xTrain), dataPath
									+ "/xTrain.txt");
							Writer.copyFile(new File(yTrain), dataPath
									+ "/yTrain.txt");
							Writer.copyFile(new File(sTrain), dataPath
									+ "/sTrain.txt");

							Writer.copyFile(new File(xTest), dataPath
									+ "/xTest.txt");
							Writer.copyFile(new File(yTest), dataPath
									+ "/yTest.txt");
							Writer.copyFile(new File(sTest), dataPath
									+ "/sTest.txt");

							String[] text = new String[2];
							text[0] = "Train nodes: " + noOfNodes;
							text[1] = "Test nodes: " + noOfNodesTest;

							Writer.write(text, dataPath + "/readme.txt");
						} else {

							int noOfTime = Integer.parseInt(txtTimePoints
									.getText());
							int noOfX = Integer.parseInt(txtAttributes
									.getText());

							String[] x = Reader.read(xTrain);
							String[] y = Reader.read(yTrain);

							double[][] s = null;
							if (!chkLearn.isSelected()) {
								s = Reader.readGraph(sTrain, noOfNodes);
							}
							String message1 = checkAllFiles(noOfNodes,
									noOfTime, noOfX, x, y, s);

							if (message1 != null) {
								JOptionPane.showMessageDialog(mainFrame,
										message1, "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
							Writer.copyFile(new File(xTrain), dataPath
									+ "/x.txt");
							Writer.copyFile(new File(yTrain), dataPath
									+ "/y.txt");
							if (!chkLearn.isSelected()) {
								Writer.copyFile(new File(sTrain), dataPath
										+ "/s.txt");
							}

							String[] text = new String[3];
							text[0] = "Nodes: " + noOfNodes;
							text[1] = "Time points: " + noOfTime;
							text[2] = "Attributes per node: " + noOfX;
							Writer.write(text, dataPath + "/readme.txt");
						}

						JOptionPane.showMessageDialog(mainFrame,
								"Dataset is created successfully.", "Error",
								JOptionPane.INFORMATION_MESSAGE);
						resetValues();
					}
				}

			});
			Style.buttonStyle(btnTrain);
			btnTrain.setBounds(317, 603, 112, 45);
		}
		return btnTrain;
	}

	private void resetValues() {
		txtName.setText("");
		txtXTestFile.setText("");
		txtYTestFile.setText("");
		txtSTestFile.setText("");
		txtXFile.setText("");
		txtYFile.setText("");
		txtSFile.setText("");
		txtNodes.setText("");
		txtNodesTest.setText("");
		chkTogether.setSelected(false);
		txtTimePoints.setText("");
		txtAttributes.setText("");
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
		if (txtSFile.getText().equals("") && !chkLearn.isSelected()) {
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
			label.setBounds(98, 213, 100, 30);
		}
		return label;
	}

	private JTextField getTxtNodes() {
		if (txtNodes == null) {
			txtNodes = new JTextField();
			txtNodes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNodes.setColumns(10);
			txtNodes.setBounds(214, 212, 91, 30);
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
			txtSTestFile.setBounds(214, 318, 315, 30);
		}
		return txtSTestFile;
	}

	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("File with edges:");
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);
			label_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_1.setBounds(78, 318, 120, 30);
		}
		return label_1;
	}

	private JButton getBtnBrowseSTest() {
		if (btnBrowseSTest == null) {
			btnBrowseSTest = new JButton("Browse");
			btnBrowseSTest.setBounds(546, 318, 100, 30);
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
			btnQuestionSTest.setBounds(665, 318, 30, 30);
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
			label_3.setBounds(58, 358, 140, 30);
		}
		return label_3;
	}

	private JTextField getTxtXTestFile() {
		if (txtXTestFile == null) {
			txtXTestFile = new JTextField();
			txtXTestFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtXTestFile.setColumns(10);
			txtXTestFile.setBounds(214, 358, 315, 30);
		}
		return txtXTestFile;
	}

	private JButton getBtnBrowseXTest() {
		if (btnBrowseXTest == null) {
			btnBrowseXTest = new JButton("Browse");
			btnBrowseXTest.setBounds(546, 358, 100, 30);
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
			btnQuestionXTest.setBounds(665, 358, 30, 30);
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
			label_4.setBounds(68, 398, 130, 30);
		}
		return label_4;
	}

	private JTextField getTxtYTestFile() {
		if (txtYTestFile == null) {
			txtYTestFile = new JTextField();
			txtYTestFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtYTestFile.setColumns(10);
			txtYTestFile.setBounds(214, 398, 315, 30);
		}
		return txtYTestFile;
	}

	private JButton getBtnBrowseYTest() {
		if (btnBrowseYTest == null) {
			btnBrowseYTest = new JButton("Browse");
			btnBrowseYTest.setBounds(546, 398, 100, 30);
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
			btnQuestionYTest.setBounds(665, 398, 30, 30);
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
			label_5.setBounds(98, 440, 100, 30);
		}
		return label_5;
	}

	private JTextField getTxtNodesTest() {
		if (txtNodesTest == null) {
			txtNodesTest = new JTextField();
			txtNodesTest.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNodesTest.setColumns(10);
			txtNodesTest.setBounds(214, 439, 91, 30);
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
						lblAttributes.setVisible(true);
						lblTimePoints.setVisible(true);
						txtAttributes.setVisible(true);
						txtTimePoints.setVisible(true);
					} else {
						txtSTestFile.setEnabled(true);
						txtXTestFile.setEnabled(true);
						txtYTestFile.setEnabled(true);
						txtNodesTest.setEnabled(true);
						btnBrowseSTest.setEnabled(true);
						btnBrowseXTest.setEnabled(true);
						btnBrowseYTest.setEnabled(true);
						lblAttributes.setVisible(false);
						lblTimePoints.setVisible(false);
						txtAttributes.setVisible(false);
						txtTimePoints.setVisible(false);
					}
				}
			});
			chkTogether.setBounds(214, 486, 260, 23);
		}
		return chkTogether;
	}

	private JLabel getLblAttributes() {
		if (lblAttributes == null) {
			lblAttributes = new JLabel("No. of attributes per node:");
			lblAttributes.setVisible(false);
			lblAttributes.setHorizontalAlignment(SwingConstants.RIGHT);
			lblAttributes.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblAttributes.setBounds(0, 554, 198, 30);
		}
		return lblAttributes;
	}

	private JTextField getTxtAttributes() {
		if (txtAttributes == null) {
			txtAttributes = new JTextField();
			txtAttributes.setVisible(false);
			txtAttributes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAttributes.setColumns(10);
			txtAttributes.setBounds(214, 554, 91, 30);
		}
		return txtAttributes;
	}

	private JLabel getLblTimePoints() {
		if (lblTimePoints == null) {
			lblTimePoints = new JLabel("No. of time points:");
			lblTimePoints.setVisible(false);
			lblTimePoints.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTimePoints.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblTimePoints.setBounds(55, 516, 143, 30);
		}
		return lblTimePoints;
	}

	private JTextField getTxtTimePoints() {
		if (txtTimePoints == null) {
			txtTimePoints = new JTextField();
			txtTimePoints.setVisible(false);
			txtTimePoints.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtTimePoints.setColumns(10);
			txtTimePoints.setBounds(214, 516, 91, 30);
		}
		return txtTimePoints;
	}

	private JCheckBox getChkLearn() {
		if (chkLearn == null) {
			chkLearn = new JCheckBox("Learn similarity");
			chkLearn.setBounds(711, 99, 140, 23);
		}
		return chkLearn;
	}

	private String checkAllFiles(int noOfNodes, int noOfTime, int noOfX,
			String[] x, String[] y, double[][] s) {

		String xMsg = checkX(noOfNodes, x, noOfX, noOfTime);
		if (xMsg != null) {
			return xMsg;
		}

		String yMsg = checkY(noOfNodes, y, noOfX, noOfTime);
		if (yMsg != null) {
			return yMsg;
		}
		if (s != null) {
			String sMsg = checkS(noOfNodes, s);
			if (sMsg != null) {
				return sMsg;
			}
		}
		return null;
	}

	private String checkFilesTrain(int noOfNodes, String[] x, double[] y,
			double[][] s, String trainTest) {
		if (x == null) {
			return "Error while reading file with attributes for " + trainTest
					+ ".";
		}

		if (x.length != noOfNodes) {
			return "Error while reading file with attributes for " + trainTest
					+ ": Number of lines should be " + noOfNodes + ".";
		}

		if (y == null) {
			return "Error while reading file with outputs for " + trainTest
					+ ": Number of lines should be " + noOfNodes + ".";
		}

		if (s == null) {
			return "Error while reading file with edges for " + trainTest
					+ ": Ordinal number of node can be between 1 and "
					+ noOfNodes + ".";
		}
		return null;
	}

	private String checkS(int noOfNodes, double[][] s) {
		if (s == null) {
			return "Ordinal number of node can be between 1 and " + noOfNodes
					+ ".";
		}
		return null;
	}

	private String checkX(int noOfNodes, String[] x, int noOfX, int noOfTime) {
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
		return null;
	}

	private String checkY(int noOfNodes, String[] y, int noOfX, int noOfTime) {
		if (y == null) {
			return "Error while reading file with attributes.";
		}
		if (y.length != noOfNodes) {
			return "Number of lines in the file with outputs should be "
					+ noOfNodes + ".";
		}
		for (int i = 0; i < y.length; i++) {
			if (y[i].split(",").length != noOfTime) {
				return "Number of values in each line in the file with outputs should be equal to no. of time points: "
						+ noOfTime;
			}
		}
		return null;
	}
}
