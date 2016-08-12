package app.gui.panels;

import javax.sound.midi.Patch;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import app.exceptions.ConfigurationParameterseException;
import app.file.io.Reader;
import app.file.io.Writer;
import app.gui.frames.MainFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ConfigurePanel extends JPanel {

	private static final long serialVersionUID = 356011421979477981L;
	private JPanel panel;
	private JFrame mainFrame;

	// params

	private int alphaGen;
	private int betaGen;
	private int alpha;
	private int beta;
	private double lr;
	private int iterations;
	private int hidden;
	private int iterNN;
	private String matlabPath;
	private boolean useMatlab;

	private JTextField txtAlphaGen;
	private JLabel lblub;
	private JLabel lblParametersForRandom;
	private JTextField txtBetaGen;
	private JLabel lblBeta;
	private JLabel lblParametersForModel;
	private JLabel label;
	private JTextField txtAlpha;
	private JLabel label_1;
	private JTextField txtBeta;
	private JLabel lblLearningRate;
	private JTextField txtLR;
	private JTextField txtIter;
	private JLabel lblMaxIterations;
	private JButton btnSave;
	private JButton btnResetToDefaults;
	private JLabel lblParametersForNeural;
	private JLabel lblHidden;
	private JTextField txtHidden;
	private JTextField txtIterNN;
	private JLabel lblIterNN;
	private JLabel lblMatlab;
	private JLabel lblPathToMatlabexe;
	private JCheckBox chckMatlab;
	private JButton button;
	private JTextField txtPath;
	private JFileChooser fc;
	private JLabel lblHelp;

	public ConfigurePanel(JFrame mainFrame) {
		setBackground(UIManager.getColor("Button.background"));
		setLayout(null);
		panel = this;
		this.mainFrame = mainFrame;
		add(getTxtAlphaGen());
		add(getLblub());
		add(getLblParametersForRandom());
		add(getTxtBetaGen());
		add(getLblBeta());
		add(getLblParametersForModel());
		add(getLabel());
		add(getTextField_1());
		add(getLabel_1());
		add(getTextField_1_1());
		add(getLblLearningRate());
		add(getTextField_2());
		add(getTextField_1_2());
		add(getLblMaxIterations());
		add(getBtnSave());
		add(getBtnResetToDefaults());
		add(getLblParametersForNeural());
		add(getLblHidden());
		add(getTxtHidden());
		add(getTextField_1_3());
		add(getLblIterNN());
		add(getLblMatlab());
		add(getLabel_2_1());
		add(getChckMatlab());
		add(getButton());
		add(getTxtPath());
		add(getLblHelp());
		fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"EXE FILES", "exe", "exe");
		fc.setFileFilter(filter);
		if (Reader.checkFile("cfg.txt")) {
			String result = readParametersFromCfg();
			if (result != null) {
				JOptionPane.showMessageDialog(mainFrame, result
						+ " Parameters will be reset to the default values.",
						"Error", JOptionPane.ERROR_MESSAGE);
				setUpDefaultValues();
			}
		} else {
			setUpDefaultValues();
		}
		setTxtValues();

	}

	public void setUpDefaultValues() {
		alpha = 1;
		beta = 1;
		lr = 0.01;
		iterations = 1000;
		alphaGen = 5;
		betaGen = 1;
		hidden = 1;
		iterNN = 1000;
		useMatlab = true;
		matlabPath = "";
	}

	private JTextField getTxtAlphaGen() {
		if (txtAlphaGen == null) {
			txtAlphaGen = new JTextField();
			txtAlphaGen.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAlphaGen.setColumns(10);
			txtAlphaGen.setBounds(169, 74, 169, 30);
		}
		return txtAlphaGen;
	}

	private JLabel getLblub() {
		if (lblub == null) {
			lblub = new JLabel("Alpha:");
			lblub.setHorizontalAlignment(SwingConstants.RIGHT);
			lblub.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblub.setBounds(53, 74, 100, 30);
		}
		return lblub;
	}

	private JLabel getLblParametersForRandom() {
		if (lblParametersForRandom == null) {
			lblParametersForRandom = new JLabel(
					"Parameters for random data generation:");
			lblParametersForRandom.setHorizontalAlignment(SwingConstants.LEFT);
			lblParametersForRandom.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblParametersForRandom.setBounds(34, 28, 374, 30);
		}
		return lblParametersForRandom;
	}

	private JTextField getTxtBetaGen() {
		if (txtBetaGen == null) {
			txtBetaGen = new JTextField();
			txtBetaGen.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtBetaGen.setColumns(10);
			txtBetaGen.setBounds(169, 115, 169, 30);
		}
		return txtBetaGen;
	}

	private JLabel getLblBeta() {
		if (lblBeta == null) {
			lblBeta = new JLabel("Beta:");
			lblBeta.setHorizontalAlignment(SwingConstants.RIGHT);
			lblBeta.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblBeta.setBounds(53, 115, 100, 30);
		}
		return lblBeta;
	}

	private JLabel getLblParametersForModel() {
		if (lblParametersForModel == null) {
			lblParametersForModel = new JLabel("Parameters for training:");
			lblParametersForModel.setHorizontalAlignment(SwingConstants.LEFT);
			lblParametersForModel.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblParametersForModel.setBounds(34, 167, 434, 30);
		}
		return lblParametersForModel;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("Alpha:");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label.setBounds(53, 209, 100, 30);
		}
		return label;
	}

	private JTextField getTextField_1() {
		if (txtAlpha == null) {
			txtAlpha = new JTextField();
			txtAlpha.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAlpha.setColumns(10);
			txtAlpha.setBounds(169, 209, 169, 30);
		}
		return txtAlpha;
	}

	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("Beta:");
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);
			label_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_1.setBounds(53, 250, 100, 30);
		}
		return label_1;
	}

	private JTextField getTextField_1_1() {
		if (txtBeta == null) {
			txtBeta = new JTextField();
			txtBeta.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtBeta.setColumns(10);
			txtBeta.setBounds(169, 250, 169, 30);
		}
		return txtBeta;
	}

	private JLabel getLblLearningRate() {
		if (lblLearningRate == null) {
			lblLearningRate = new JLabel("Learning rate:");
			lblLearningRate.setHorizontalAlignment(SwingConstants.RIGHT);
			lblLearningRate.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblLearningRate.setBounds(53, 291, 100, 30);
		}
		return lblLearningRate;
	}

	private JTextField getTextField_2() {
		if (txtLR == null) {
			txtLR = new JTextField();
			txtLR.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtLR.setColumns(10);
			txtLR.setBounds(169, 291, 169, 30);
		}
		return txtLR;
	}

	private JTextField getTextField_1_2() {
		if (txtIter == null) {
			txtIter = new JTextField();
			txtIter.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtIter.setColumns(10);
			txtIter.setBounds(169, 332, 169, 30);
		}
		return txtIter;
	}

	private JLabel getLblMaxIterations() {
		if (lblMaxIterations == null) {
			lblMaxIterations = new JLabel("Max. iterations:");
			lblMaxIterations.setHorizontalAlignment(SwingConstants.RIGHT);
			lblMaxIterations.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblMaxIterations.setBounds(34, 332, 119, 30);
		}
		return lblMaxIterations;
	}

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String message = validateData();
					if (message != null) {
						JOptionPane.showMessageDialog(mainFrame, message,
								"Error", JOptionPane.ERROR_MESSAGE);
					} else {
						String[] text = prepareForFile();
						Writer.write(text, "cfg.txt");
						MainFrame m = (MainFrame) mainFrame;
						m.enableMenu();
						panel.removeAll();
						panel.repaint();
						panel.revalidate();
					}
				}
			});
			btnSave.setBounds(239, 432, 169, 45);
		}
		return btnSave;
	}

	public String[] prepareForFile() {
		String[] text = new String[10];
		alphaGen = Integer.parseInt(txtAlphaGen.getText());
		betaGen = Integer.parseInt(txtBetaGen.getText());
		alpha = Integer.parseInt(txtAlpha.getText());
		beta = Integer.parseInt(txtBeta.getText());
		lr = Double.parseDouble(txtLR.getText());
		iterations = Integer.parseInt(txtIter.getText());
		hidden = Integer.parseInt(txtHidden.getText());
		iterNN = Integer.parseInt(txtIterNN.getText());
		useMatlab = chckMatlab.isSelected();
		matlabPath = txtPath.getText();
		text[0] = "AlphaGen=" + alphaGen;
		text[1] = "BetaGen=" + betaGen;
		text[2] = "Alpha=" + alpha;
		text[3] = "Beta=" + beta;
		text[4] = "LR=" + lr;
		text[5] = "Iterations=" + iterations;
		text[6] = "NN hidden=" + hidden;
		text[7] = "Iterations NN=" + iterNN;
		text[8] = "Use MATLAB=" + useMatlab;
		text[9] = "Path=" + matlabPath;
		return text;
	}

	public String validateData() {
		try {
			int a = Integer.parseInt(txtAlphaGen.getText());
			if (a <= 0) {
				return "Alpha for data generation should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "Alpha for data generation should be integer.";
		}
		try {
			int b = Integer.parseInt(txtBetaGen.getText());
			if (b <= 0) {
				return "Beta for data generation should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "Beta for data generation should be integer.";
		}

		try {
			int a = Integer.parseInt(txtAlpha.getText());
			if (a <= 0) {
				return "Alpha should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "Alpha should be integer.";
		}
		try {
			int b = Integer.parseInt(txtBeta.getText());
			if (b <= 0) {
				return "Beta should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "Beta should be integer.";
		}
		try {
			double l = Double.parseDouble(txtLR.getText());
			if (l <= 0) {
				return "Learning rate should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "Learning rate should be number.";
		}

		try {
			int i = Integer.parseInt(txtIter.getText());
			if (i <= 0) {
				return "Max. iterations should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "Max. iterations should be integer.";
		}
		try {
			int b = Integer.parseInt(txtHidden.getText());
			if (b <= 0) {
				return "No. of hidden neurons for neural network should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "No. of hidden neurons for neural network should be integer.";
		}
		try {
			int b = Integer.parseInt(txtIterNN.getText());
			if (b <= 0) {
				return "No. of iteration for neural network training should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "No. of iteration for neural network training should be integer.";
		}
		if (chckMatlab.isSelected() && txtPath.getText().equals("")) {
			return "Choose path to MATLAB.exe or deselect 'Use methods implemented in MATLAB' check box.";
		}
		return null;
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
		txtAlphaGen.setText(alphaGen + "");
		txtBetaGen.setText(betaGen + "");
		txtAlpha.setText(alpha + "");
		txtBeta.setText(beta + "");
		txtLR.setText(lr + "");
		txtIter.setText(iterations + "");
		txtIterNN.setText(iterNN + "");
		txtHidden.setText(hidden + "");
		chckMatlab.setSelected(useMatlab);
		txtPath.setText(matlabPath);
	}

	private JButton getBtnResetToDefaults() {
		if (btnResetToDefaults == null) {
			btnResetToDefaults = new JButton("Reset to defaults");
			btnResetToDefaults.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setUpDefaultValues();
					setTxtValues();
				}
			});
			btnResetToDefaults.setBounds(435, 432, 169, 45);
		}
		return btnResetToDefaults;
	}

	private JLabel getLblParametersForNeural() {
		if (lblParametersForNeural == null) {
			lblParametersForNeural = new JLabel(
					"Parameters for neural network:");
			lblParametersForNeural.setHorizontalAlignment(SwingConstants.LEFT);
			lblParametersForNeural.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblParametersForNeural.setBounds(418, 28, 434, 30);
		}
		return lblParametersForNeural;
	}

	private JLabel getLblHidden() {
		if (lblHidden == null) {
			lblHidden = new JLabel("No. of hidden neurons:");
			lblHidden.setHorizontalAlignment(SwingConstants.RIGHT);
			lblHidden.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblHidden.setBounds(457, 74, 164, 30);
		}
		return lblHidden;
	}

	private JTextField getTxtHidden() {
		if (txtHidden == null) {
			txtHidden = new JTextField();
			txtHidden.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtHidden.setColumns(10);
			txtHidden.setBounds(646, 74, 91, 30);
		}
		return txtHidden;
	}

	private JTextField getTextField_1_3() {
		if (txtIterNN == null) {
			txtIterNN = new JTextField();
			txtIterNN.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtIterNN.setColumns(10);
			txtIterNN.setBounds(646, 115, 91, 30);
		}
		return txtIterNN;
	}

	private JLabel getLblIterNN() {
		if (lblIterNN == null) {
			lblIterNN = new JLabel("No. of iterations:");
			lblIterNN.setHorizontalAlignment(SwingConstants.RIGHT);
			lblIterNN.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblIterNN.setBounds(490, 118, 130, 30);
		}
		return lblIterNN;
	}

	private JLabel getLblMatlab() {
		if (lblMatlab == null) {
			lblMatlab = new JLabel("MATLAB:");
			lblMatlab.setHorizontalAlignment(SwingConstants.LEFT);
			lblMatlab.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblMatlab.setBounds(418, 167, 434, 30);
		}
		return lblMatlab;
	}

	private JLabel getLabel_2_1() {
		if (lblPathToMatlabexe == null) {
			lblPathToMatlabexe = new JLabel("Path to MATLAB.exe:");
			lblPathToMatlabexe.setHorizontalAlignment(SwingConstants.LEFT);
			lblPathToMatlabexe.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblPathToMatlabexe.setBounds(447, 242, 164, 30);
		}
		return lblPathToMatlabexe;
	}

	private JCheckBox getChckMatlab() {
		if (chckMatlab == null) {
			chckMatlab = new JCheckBox("Use methods implemented in MATLAB");
			chckMatlab.setBounds(447, 208, 269, 23);
		}
		return chckMatlab;
	}

	private JButton getButton() {
		if (button == null) {
			button = new JButton("Browse");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					chooseFile(txtPath);
				}
			});
			button.setBounds(779, 283, 100, 30);
		}
		return button;
	}

	public void chooseFile(JTextField txt) {
		int returnVal = fc.showOpenDialog(panel);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			txt.setText(fc.getSelectedFile().getPath());
		}
	}

	private JTextField getTxtPath() {
		if (txtPath == null) {
			txtPath = new JTextField();
			txtPath.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtPath.setColumns(10);
			txtPath.setBounds(447, 283, 315, 30);
		}
		return txtPath;
	}

	private JLabel getLblHelp() {
		if (lblHelp == null) {
			lblHelp = new JLabel(
					"Example for Windows: C:/Program Files/MATLAB/Version/bin/matlab.exe");
			lblHelp.setBounds(447, 324, 447, 23);
		}
		return lblHelp;
	}
}
