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

import app.algorithms.asymmetric.CalculationsAsymmetric;
import app.data.generators.ArrayGenerator;
import app.file.io.Reader;
import app.file.io.Writer;
import app.gui.frames.ProgressBar;
import app.gui.style.Style;
import app.gui.threads.TrainForGUI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JCheckBox;

public class TrainPanel extends JPanel {
	private JTextField txtMatrixFile;
	private JLabel lblFile;
	private JButton btnBrowseS;
	private JButton btnQuestionS;
	private JLabel lblModelName;
	private JTextField txtModelName;
	private JLabel lblRArrayFile;
	private JTextField txtRFile;
	private JButton btnBrowseR;
	private JLabel lblAlpha;
	private JLabel lblFirstBeta;
	private JTextField txtAlpha;
	private JTextField txtBeta;
	private JLabel lblLearningRate;
	private JTextField txtLr;
	private JButton btnQuestionR;
	private JLabel lblYArrayFile;
	private JTextField txtYFile;
	private JButton btnBrowseY;
	private JButton btnQuestionY;
	private JButton btnTrain;
	private JLabel lblMaxIterations;
	private JTextField txtMaxIter;
	private JFileChooser fc;
	private JPanel panel;
	private JFrame mainFrame;
	private JLabel label;
	private JTextField txtNoOfNodes;
	private JCheckBox chckbxSymmetric;
	private JLabel label_1;

	/**
	 * Create the panel.
	 */
	public TrainPanel(JFrame mainFrame) {
		setBackground(UIManager.getColor("Button.background"));
		setLayout(null);
		add(getTxtMatrixFile());
		add(getLblFile());
		add(getBtnBrowseS());
		add(getBtnQuestionS());
		add(getLblModelName());
		add(getTxtModelName());
		add(getLblRArrayFile());
		add(getTxtRFile());
		add(getBtnBrowseR());
		add(getLblAlpha());
		add(getLblFirstBeta());
		add(getTxtAlpha());
		add(getTxtBeta());
		add(getLblLearningRate());
		add(getTxtLr());
		add(getBtnQuestionR());
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
		add(getLabel());
		add(getTxtNoOfNodes());
		add(getChckbxSymmetric());
		add(getLabel_1());
		setUpDefaultValues();
	}

	private JTextField getTxtMatrixFile() {
		if (txtMatrixFile == null) {
			txtMatrixFile = new JTextField();
			txtMatrixFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtMatrixFile.setBounds(183, 36, 315, 30);
			txtMatrixFile.setColumns(10);
		}
		return txtMatrixFile;
	}

	private JLabel getLblFile() {
		if (lblFile == null) {
			lblFile = new JLabel("S matrix file:");
			lblFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblFile.setBounds(67, 36, 100, 30);
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
			btnBrowseS.setBounds(515, 36, 100, 30);

		}
		return btnBrowseS;
	}

	private JButton getBtnQuestionS() {
		if (btnQuestionS == null) {
			btnQuestionS = new JButton("");
			btnQuestionS.setBounds(634, 36, 30, 30);
			Style.questionButtonStyle(btnQuestionS);
		}
		return btnQuestionS;
	}

	private JLabel getLblModelName() {
		if (lblModelName == null) {
			lblModelName = new JLabel("Model name:");
			lblModelName.setHorizontalAlignment(SwingConstants.RIGHT);
			lblModelName.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblModelName.setBounds(67, 157, 100, 30);
		}
		return lblModelName;
	}

	private JTextField getTxtModelName() {
		if (txtModelName == null) {
			txtModelName = new JTextField();
			txtModelName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtModelName.setColumns(10);
			txtModelName.setBounds(183, 157, 315, 30);
		}
		return txtModelName;
	}

	private JLabel getLblRArrayFile() {
		if (lblRArrayFile == null) {
			lblRArrayFile = new JLabel("R array file:");
			lblRArrayFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblRArrayFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblRArrayFile.setBounds(67, 76, 100, 30);
		}
		return lblRArrayFile;
	}

	private JTextField getTxtRFile() {
		if (txtRFile == null) {
			txtRFile = new JTextField();
			txtRFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtRFile.setColumns(10);
			txtRFile.setBounds(183, 76, 315, 30);
		}
		return txtRFile;
	}

	private JButton getBtnBrowseR() {
		if (btnBrowseR == null) {
			btnBrowseR = new JButton("Browse");
			btnBrowseR.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					chooseFile(txtRFile);
				}
			});
			Style.buttonStyle(btnBrowseR);
			btnBrowseR.setBounds(515, 76, 100, 30);
		}
		return btnBrowseR;
	}

	private JLabel getLblAlpha() {
		if (lblAlpha == null) {
			lblAlpha = new JLabel("First alpha:");
			lblAlpha.setHorizontalAlignment(SwingConstants.RIGHT);
			lblAlpha.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblAlpha.setBounds(67, 235, 100, 30);
		}
		return lblAlpha;
	}

	private JLabel getLblFirstBeta() {
		if (lblFirstBeta == null) {
			lblFirstBeta = new JLabel("First beta:");
			lblFirstBeta.setHorizontalAlignment(SwingConstants.RIGHT);
			lblFirstBeta.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblFirstBeta.setBounds(67, 275, 100, 30);
		}
		return lblFirstBeta;
	}

	private JTextField getTxtAlpha() {
		if (txtAlpha == null) {
			txtAlpha = new JTextField();
			txtAlpha.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAlpha.setColumns(10);
			txtAlpha.setBounds(183, 236, 91, 30);
		}
		return txtAlpha;
	}

	private JTextField getTxtBeta() {
		if (txtBeta == null) {
			txtBeta = new JTextField();
			txtBeta.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtBeta.setColumns(10);
			txtBeta.setBounds(183, 277, 91, 30);
		}
		return txtBeta;
	}

	private JLabel getLblLearningRate() {
		if (lblLearningRate == null) {
			lblLearningRate = new JLabel("Learning rate:");
			lblLearningRate.setHorizontalAlignment(SwingConstants.RIGHT);
			lblLearningRate.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblLearningRate.setBounds(67, 315, 100, 30);
		}
		return lblLearningRate;
	}

	private JTextField getTxtLr() {
		if (txtLr == null) {
			txtLr = new JTextField();
			txtLr.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtLr.setColumns(10);
			txtLr.setBounds(183, 315, 91, 30);
		}
		return txtLr;
	}

	private JButton getBtnQuestionR() {
		if (btnQuestionR == null) {
			btnQuestionR = new JButton("");
			btnQuestionR.setBounds(634, 76, 30, 30);
			Style.questionButtonStyle(btnQuestionR);
		}
		return btnQuestionR;
	}

	private JLabel getLblYArrayFile() {
		if (lblYArrayFile == null) {
			lblYArrayFile = new JLabel("Y array file:");
			lblYArrayFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblYArrayFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblYArrayFile.setBounds(67, 116, 100, 30);
		}
		return lblYArrayFile;
	}

	private JTextField getTxtYFile() {
		if (txtYFile == null) {
			txtYFile = new JTextField();
			txtYFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtYFile.setColumns(10);
			txtYFile.setBounds(183, 116, 315, 30);
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
			btnBrowseY.setBounds(515, 116, 100, 30);
		}
		return btnBrowseY;
	}

	private JButton getBtnQuestionY() {
		if (btnQuestionY == null) {
			btnQuestionY = new JButton("");
			btnQuestionY.setBounds(634, 116, 30, 30);
			Style.questionButtonStyle(btnQuestionY);
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
						Writer.createFolder("MyModels");
						String path = "MyModels/" + txtModelName.getText();
						Writer.createFolder(path);
						String dataPath = "MyModels/" + txtModelName.getText() + "/data";
						Writer.createFolder(dataPath);
						File matrixFile = new File(txtMatrixFile.getText());
						Writer.copyFile(matrixFile, dataPath + "/matrix.txt");
						File rFile = new File(txtRFile.getText());
						Writer.copyFile(rFile, dataPath + "/r.txt");
						File yFile = new File(txtYFile.getText());
						Writer.copyFile(yFile, dataPath + "/y.txt");
						int noOfNodes = Integer.parseInt(txtNoOfNodes.getText());
						Writer.createFolder(path + "/results");
						train(noOfNodes, path + "/results");
					}
				}
			});
			Style.buttonStyle(btnTrain);
			btnTrain.setBounds(340, 440, 112, 45);
		}
		return btnTrain;
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

		double[][] s = Reader.readGraph(txtMatrixFile.getText(), noOfNodes);
		double[] r = Reader.readArray(txtRFile.getText(), noOfNodes);
		double[] y = Reader.readArray(txtYFile.getText(), noOfNodes);
		boolean both = false;
		if (chckbxSymmetric.isSelected()) {
			both = true;
		}
		TrainForGUI t = new TrainForGUI(modelFolder, frame, mainFrame, s, r, y,
				alpha, beta, lr, maxIter, panel, both, 50, 500);
		t.start();

	}

	private JLabel getLblMaxIterations() {
		if (lblMaxIterations == null) {
			lblMaxIterations = new JLabel("Max. iterations:");
			lblMaxIterations.setHorizontalAlignment(SwingConstants.RIGHT);
			lblMaxIterations.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblMaxIterations.setBounds(35, 355, 132, 30);
		}
		return lblMaxIterations;
	}

	public String validateData() {
		if (txtMatrixFile.getText().equals("")) {
			return "Choose matrix file.";
		}
		if (txtRFile.getText().equals("")) {
			return "Choose R file.";
		}
		if (txtYFile.getText().equals("")) {
			return "Choose Y file.";
		}
		try {
			Integer.parseInt(txtNoOfNodes.getText());
		} catch (NumberFormatException e) {
			return "No. of nodes should be integer.";
		}
		if (txtModelName.getText().equals("")) {
			return "Insert model name.";
		}
		if (Writer.checkFolder("MyModels/" + txtModelName.getText())) {
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
			Double.parseDouble(txtLr.getText());
		} catch (NumberFormatException e) {
			return "Learning rate should be number.";
		}

		try {
			Integer.parseInt(txtMaxIter.getText());
		} catch (NumberFormatException e) {
			return "Max. iterations should be integer.";
		}
		return null;
	}

	private JTextField getTxtMaxIter() {
		if (txtMaxIter == null) {
			txtMaxIter = new JTextField();
			txtMaxIter.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtMaxIter.setColumns(10);
			txtMaxIter.setBounds(183, 355, 91, 30);
		}
		return txtMaxIter;
	}

	public void chooseFile(JTextField txt) {
		int returnVal = fc.showOpenDialog(panel);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			txt.setText(fc.getSelectedFile().getPath());
		}
	}

	public void setUpDefaultValues() {
		txtAlpha.setText("1");
		txtBeta.setText("1");
		txtLr.setText("0.0001");
		txtMaxIter.setText("10000");
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("No. of nodes:");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label.setBounds(67, 199, 100, 30);
		}
		return label;
	}

	private JTextField getTxtNoOfNodes() {
		if (txtNoOfNodes == null) {
			txtNoOfNodes = new JTextField();
			txtNoOfNodes.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtNoOfNodes.setColumns(10);
			txtNoOfNodes.setBounds(183, 198, 91, 30);
		}
		return txtNoOfNodes;
	}

	private JCheckBox getChckbxSymmetric() {
		if (chckbxSymmetric == null) {
			chckbxSymmetric = new JCheckBox("");
			chckbxSymmetric.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			chckbxSymmetric.setBounds(181, 403, 91, 23);
		}
		return chckbxSymmetric;
	}

	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("Train symmetric:");
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);
			label_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_1.setBounds(35, 396, 132, 30);
		}
		return label_1;
	}
}
