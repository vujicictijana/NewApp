package app.gui.panels;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import app.exceptions.ConfigurationParameterseException;
import app.file.io.Reader;
import app.file.io.Writer;
import app.gui.frames.MainFrame;
import app.gui.style.Style;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JSeparator;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

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
	private int alphaReg;
	private int betaReg;
	private int iterTemp;
	private String lambda;
	private int rlsrHidden;
	private int rlsrIterNN;
	private int sseIter;
	private int lsIter;
	private long proxy;

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
	private JButton btnBrowse;
	private JTextField txtPath;
	private JFileChooser fc;
	private JLabel lblHelp;
	private JLabel lblParametersForTraining;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JTextField txtBetaReg;
	private JTextField txtAlphaReg;
	private JTextField txtIterMGCRF;
	private JLabel label_5;
	private JLabel lblParametersForTraining_1;
	private JLabel lblLambdaSet;
	private JLabel label_7;
	private JTextField txtRlsrHidden;
	private JLabel label_8;
	private JTextField txtRlsrIterNN;
	private JTextField txtLambda;
	private JButton button_1;
	private JLabel label_9;
	private JTextField txtSseIter;
	private JLabel label_10;
	private JTextField txtLsIter;
	private JLabel lblProxytimeout;
	private JTextField txtProxy;
	private JSeparator separator;
	private JSeparator separator_1;

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
		add(getBtnBrowse());
		add(getTxtPath());
		add(getLblHelp());
		add(getLblParametersForTraining());
		add(getLabel_2());
		add(getLabel_3());
		add(getLabel_4());
		add(getTxtBetaReg());
		add(getTextField_1_4());
		add(getTextField_2_1());
		add(getLabel_5());
		add(getLblParametersForTraining_1());
		add(getLblLambdaSet());
		add(getLabel_7());
		add(getTxtRlsrHidden());
		add(getLabel_8());
		add(getTextField_1_5());
		add(getTextField_2_2());
		add(getButton_1());
		add(getLabel_9());
		add(getTxtSseIter());
		add(getLabel_10());
		add(getTxtLsIter());
		add(getLblProxytimeout());
		add(getTxtProxy());
		add(getSeparator());
		add(getSeparator_1());
		fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"EXE FILES", "exe", "exe");
		fc.setFileFilter(filter);
		if (Reader.checkFile(Reader.jarFile() + "/cfg.txt")) {
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
		alphaReg = 10000;
		betaReg = 10;
		iterTemp = 50;
		lambda = "0.01";
		rlsrHidden = 20;
		rlsrIterNN = 200;
		sseIter = 1000;
		lsIter = 1000;
		proxy = 300;
	}

	private JTextField getTxtAlphaGen() {
		if (txtAlphaGen == null) {
			txtAlphaGen = new JTextField();
			txtAlphaGen.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAlphaGen.setColumns(10);
			txtAlphaGen.setBounds(189, 52, 169, 30);
		}
		return txtAlphaGen;
	}

	private JLabel getLblub() {
		if (lblub == null) {
			lblub = new JLabel("Alpha:");
			lblub.setHorizontalAlignment(SwingConstants.RIGHT);
			lblub.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblub.setBounds(73, 52, 100, 30);
		}
		return lblub;
	}

	private JLabel getLblParametersForRandom() {
		if (lblParametersForRandom == null) {
			lblParametersForRandom = new JLabel(
					"Parameters for random data generation:");
			lblParametersForRandom.setBounds(0, 11, 406, 30);
			lblParametersForRandom.setForeground(Color.WHITE);
			lblParametersForRandom.setBackground(Color.GRAY);
			lblParametersForRandom.setHorizontalAlignment(SwingConstants.CENTER);
			lblParametersForRandom.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblParametersForRandom.setOpaque(true);
		}
		return lblParametersForRandom;
	}

	private JTextField getTxtBetaGen() {
		if (txtBetaGen == null) {
			txtBetaGen = new JTextField();
			txtBetaGen.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtBetaGen.setColumns(10);
			txtBetaGen.setBounds(189, 93, 169, 30);
		}
		return txtBetaGen;
	}

	private JLabel getLblBeta() {
		if (lblBeta == null) {
			lblBeta = new JLabel("Beta:");
			lblBeta.setHorizontalAlignment(SwingConstants.RIGHT);
			lblBeta.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblBeta.setBounds(73, 93, 100, 30);
		}
		return lblBeta;
	}

	private JLabel getLblParametersForModel() {
		if (lblParametersForModel == null) {
			lblParametersForModel = new JLabel(
					"Parameters for training GCRF and DirGCRF:");
			lblParametersForModel.setForeground(Color.WHITE);
			lblParametersForModel.setBackground(Color.GRAY);
			lblParametersForModel.setHorizontalAlignment(SwingConstants.CENTER);
			lblParametersForModel.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblParametersForModel.setOpaque(true);
			lblParametersForModel.setBounds(0, 139, 406, 30);
		}
		return lblParametersForModel;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("Alpha:");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label.setBounds(73, 180, 100, 30);
		}
		return label;
	}

	private JTextField getTextField_1() {
		if (txtAlpha == null) {
			txtAlpha = new JTextField();
			txtAlpha.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAlpha.setColumns(10);
			txtAlpha.setBounds(189, 180, 169, 30);
		}
		return txtAlpha;
	}

	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("Beta:");
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);
			label_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_1.setBounds(73, 221, 100, 30);
		}
		return label_1;
	}

	private JTextField getTextField_1_1() {
		if (txtBeta == null) {
			txtBeta = new JTextField();
			txtBeta.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtBeta.setColumns(10);
			txtBeta.setBounds(189, 221, 169, 30);
		}
		return txtBeta;
	}

	private JLabel getLblLearningRate() {
		if (lblLearningRate == null) {
			lblLearningRate = new JLabel("Learning rate:");
			lblLearningRate.setHorizontalAlignment(SwingConstants.RIGHT);
			lblLearningRate.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblLearningRate.setBounds(73, 262, 100, 30);
		}
		return lblLearningRate;
	}

	private JTextField getTextField_2() {
		if (txtLR == null) {
			txtLR = new JTextField();
			txtLR.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtLR.setColumns(10);
			txtLR.setBounds(189, 262, 169, 30);
		}
		return txtLR;
	}

	private JTextField getTextField_1_2() {
		if (txtIter == null) {
			txtIter = new JTextField();
			txtIter.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtIter.setColumns(10);
			txtIter.setBounds(189, 303, 169, 30);
		}
		return txtIter;
	}

	private JLabel getLblMaxIterations() {
		if (lblMaxIterations == null) {
			lblMaxIterations = new JLabel("Max. iterations:");
			lblMaxIterations.setHorizontalAlignment(SwingConstants.RIGHT);
			lblMaxIterations.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblMaxIterations.setBounds(54, 303, 119, 30);
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
						Writer.write(text, Reader.jarFile() + "/cfg.txt");
						MainFrame m = (MainFrame) mainFrame;
						m.enableMenu();
						panel.removeAll();
						panel.repaint();
						panel.revalidate();
					}
				}
			});
			btnSave.setBounds(223, 649, 169, 45);
		}
		return btnSave;
	}

	public String[] prepareForFile() {
		String[] text = new String[19];
		alphaGen = Integer.parseInt(txtAlphaGen.getText());
		betaGen = Integer.parseInt(txtBetaGen.getText());
		alpha = Integer.parseInt(txtAlpha.getText());
		beta = Integer.parseInt(txtBeta.getText());
		lr = Double.parseDouble(txtLR.getText());
		iterations = Integer.parseInt(txtIter.getText());
		hidden = Integer.parseInt(txtHidden.getText());
		iterNN = Integer.parseInt(txtIterNN.getText());
		alphaReg = Integer.parseInt(txtAlphaReg.getText());
		betaReg = Integer.parseInt(txtBetaReg.getText());
		iterTemp = Integer.parseInt(txtIterMGCRF.getText());
		lambda = txtLambda.getText();
		rlsrHidden = Integer.parseInt(txtRlsrHidden.getText());
		rlsrIterNN = Integer.parseInt(txtRlsrIterNN.getText());
		sseIter = Integer.parseInt(txtSseIter.getText());
		lsIter = Integer.parseInt(txtLsIter.getText());
		useMatlab = chckMatlab.isSelected();
		if (useMatlab) {
			proxy = Integer.parseInt(txtProxy.getText())*1000L;
		}
		matlabPath = txtPath.getText();
		text[0] = "AlphaGen=" + alphaGen;
		text[1] = "BetaGen=" + betaGen;
		text[2] = "Alpha=" + alpha;
		text[3] = "Beta=" + beta;
		text[4] = "LR=" + lr;
		text[5] = "Iterations=" + iterations;
		text[6] = "NN hidden=" + hidden;
		text[7] = "Iterations NN=" + iterNN;
		text[8] = "Iterations temporal=" + iterTemp;
		text[9] = "AlphaReg=" + alphaReg;
		text[10] = "BetaReg=" + betaReg;
		text[11] = "Lambda=" + lambda;
		text[12] = "RLSR hidden NN=" + rlsrHidden;
		text[13] = "RLSR iterations NN=" + rlsrIterNN;
		text[14] = "RLSR SSE iterations=" + sseIter;
		text[15] = "RLSR SSE LS iterations=" + lsIter;
		text[16] = "Use MATLAB=" + useMatlab;
		text[17] = "Path=" + matlabPath;
		text[18] = "Proxy=" + proxy;
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

		try {
			int a = Integer.parseInt(txtAlphaReg.getText());
			if (a <= 0) {
				return "Alpha for regularization should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "Alpha for regularization should be integer.";
		}
		try {
			int b = Integer.parseInt(txtBetaReg.getText());
			if (b <= 0) {
				return "Beta for regularization should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "Beta for regularization should be integer.";
		}

		try {
			int b = Integer.parseInt(txtIterMGCRF.getText());
			if (b <= 0) {
				return "No. of iteration for temporal methods should be greater than 0.";
			}
		} catch (NumberFormatException e) {
			return "No. of iteration for temporal methods should be integer.";
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
				alphaReg = Integer.parseInt(params.get("AlphaReg").toString());
				betaReg = Integer.parseInt(params.get("BetaReg").toString());
				iterTemp = Integer.parseInt(params.get("Iterations temporal")
						.toString());
				lambda = params.get("Lambda").toString();
				rlsrHidden = Integer.parseInt(params.get("RLSR hidden NN")
						.toString());
				rlsrIterNN = Integer.parseInt(params.get("RLSR iterations NN")
						.toString());
				sseIter = Integer.parseInt(params.get("RLSR SSE iterations")
						.toString());
				lsIter = Integer.parseInt(params.get("RLSR SSE LS iterations")
						.toString());
				proxy = Integer.parseInt(params.get("Proxy")
						.toString())/1000;
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
		txtAlphaReg.setText(alphaReg + "");
		txtBetaReg.setText(betaReg + "");
		txtIterMGCRF.setText(iterTemp + "");
		txtLambda.setText(lambda);
		txtRlsrHidden.setText(rlsrHidden + "");
		txtRlsrIterNN.setText(rlsrIterNN + "");
		txtSseIter.setText(sseIter + "");
		txtLsIter.setText(lsIter + "");
		txtProxy.setText(proxy + "");
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
			btnResetToDefaults.setBounds(417, 649, 169, 45);
		}
		return btnResetToDefaults;
	}

	private JLabel getLblParametersForNeural() {
		if (lblParametersForNeural == null) {
			lblParametersForNeural = new JLabel(
					"Parameters for training neural network:");
			lblParametersForNeural.setBounds(0, 399, 406, 30);
			lblParametersForNeural.setForeground(Color.WHITE);
			lblParametersForNeural.setBackground(Color.GRAY);
			lblParametersForNeural.setHorizontalAlignment(SwingConstants.CENTER);
			lblParametersForNeural.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblParametersForNeural.setOpaque(true);
		}
		return lblParametersForNeural;
	}

	private JLabel getLblHidden() {
		if (lblHidden == null) {
			lblHidden = new JLabel("No. of hidden neurons:");
			lblHidden.setHorizontalAlignment(SwingConstants.RIGHT);
			lblHidden.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblHidden.setBounds(9, 451, 164, 30);
		}
		return lblHidden;
	}

	private JTextField getTxtHidden() {
		if (txtHidden == null) {
			txtHidden = new JTextField();
			txtHidden.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtHidden.setColumns(10);
			txtHidden.setBounds(189, 448, 169, 30);
		}
		return txtHidden;
	}

	private JTextField getTextField_1_3() {
		if (txtIterNN == null) {
			txtIterNN = new JTextField();
			txtIterNN.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtIterNN.setColumns(10);
			txtIterNN.setBounds(189, 489, 169, 30);
		}
		return txtIterNN;
	}

	private JLabel getLblIterNN() {
		if (lblIterNN == null) {
			lblIterNN = new JLabel("No. of iterations:");
			lblIterNN.setHorizontalAlignment(SwingConstants.RIGHT);
			lblIterNN.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblIterNN.setBounds(43, 489, 130, 30);
		}
		return lblIterNN;
	}

	private JLabel getLblMatlab() {
		if (lblMatlab == null) {
			lblMatlab = new JLabel("MATLAB:");
			lblMatlab.setBounds(404, 399, 499, 30);
			lblMatlab.setForeground(Color.WHITE);
			lblMatlab.setBackground(Color.GRAY);
			lblMatlab.setHorizontalAlignment(SwingConstants.CENTER);
			lblMatlab.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblMatlab.setOpaque(true);
		}
		return lblMatlab;
	}

	private JLabel getLabel_2_1() {
		if (lblPathToMatlabexe == null) {
			lblPathToMatlabexe = new JLabel("Path to MATLAB.exe:");
			lblPathToMatlabexe.setEnabled(false);
			lblPathToMatlabexe.setHorizontalAlignment(SwingConstants.LEFT);
			lblPathToMatlabexe.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblPathToMatlabexe.setBounds(455, 479, 164, 30);
		}
		return lblPathToMatlabexe;
	}

	private JCheckBox getChckMatlab() {
		if (chckMatlab == null) {
			chckMatlab = new JCheckBox("Use methods implemented in MATLAB");
			chckMatlab.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if(chckMatlab.isSelected()){
						txtPath.setEnabled(true);
						btnBrowse.setEnabled(true);
						txtProxy.setEnabled(true);
						lblPathToMatlabexe.setEnabled(true);
						lblHelp.setEnabled(true);
						lblProxytimeout.setEnabled(true);
					}else{
						txtPath.setEnabled(false);
						btnBrowse.setEnabled(false);
						txtProxy.setEnabled(false);
						lblPathToMatlabexe.setEnabled(false);
						lblHelp.setEnabled(false);
						lblProxytimeout.setEnabled(false);
					}
				}
			});
			chckMatlab.setBounds(455, 445, 269, 23);
			
		}
		return chckMatlab;
	}

	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton("Browse");
			btnBrowse.setEnabled(false);
			btnBrowse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					chooseFile(txtPath);
				}
			});
			btnBrowse.setBounds(787, 520, 100, 30);
		}
		return btnBrowse;
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
			txtPath.setEnabled(false);
			txtPath.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtPath.setColumns(10);
			txtPath.setBounds(455, 520, 315, 30);
		}
		return txtPath;
	}

	private JLabel getLblHelp() {
		if (lblHelp == null) {
			lblHelp = new JLabel(
					"Example for Windows: C:/Program Files/MATLAB/Version/bin/matlab.exe");
			lblHelp.setEnabled(false);
			lblHelp.setBounds(455, 561, 447, 23);
		}
		return lblHelp;
	}

	private JLabel getLblParametersForTraining() {
		if (lblParametersForTraining == null) {
			lblParametersForTraining = new JLabel(
					"Parameters for methods for temporal networks:");
			lblParametersForTraining.setBounds(0, 544, 406, 30);
			lblParametersForTraining.setForeground(Color.WHITE);
			lblParametersForTraining.setBackground(Color.GRAY);
			lblParametersForTraining.setHorizontalAlignment(SwingConstants.CENTER);
			lblParametersForTraining.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblParametersForTraining.setOpaque(true);
		}
		return lblParametersForTraining;
	}

	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("Regularization alpha:");
			label_2.setHorizontalAlignment(SwingConstants.RIGHT);
			label_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_2.setBounds(427, 57, 197, 30);
		}
		return label_2;
	}

	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel("Regularization beta:");
			label_3.setHorizontalAlignment(SwingConstants.RIGHT);
			label_3.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_3.setBounds(417, 98, 207, 30);
		}
		return label_3;
	}

	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel("Max. iterations:");
			label_4.setHorizontalAlignment(SwingConstants.RIGHT);
			label_4.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_4.setBounds(54, 585, 119, 30);
		}
		return label_4;
	}

	private JTextField getTxtBetaReg() {
		if (txtBetaReg == null) {
			txtBetaReg = new JTextField();
			txtBetaReg.setText("0");
			txtBetaReg.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtBetaReg.setColumns(10);
			txtBetaReg.setBounds(640, 98, 169, 30);
		}
		return txtBetaReg;
	}

	private JTextField getTextField_1_4() {
		if (txtAlphaReg == null) {
			txtAlphaReg = new JTextField();
			txtAlphaReg.setText("0");
			txtAlphaReg.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAlphaReg.setColumns(10);
			txtAlphaReg.setBounds(640, 57, 169, 30);
		}
		return txtAlphaReg;
	}

	private JTextField getTextField_2_1() {
		if (txtIterMGCRF == null) {
			txtIterMGCRF = new JTextField();
			txtIterMGCRF.setText("0");
			txtIterMGCRF.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtIterMGCRF.setColumns(10);
			txtIterMGCRF.setBounds(189, 587, 169, 30);
		}
		return txtIterMGCRF;
	}

	private JLabel getLabel_5() {
		if (label_5 == null) {
			label_5 = new JLabel("Parameters for training m-GCRF:");
			label_5.setBounds(404, 11, 499, 30);
			label_5.setForeground(Color.WHITE);
			label_5.setBackground(Color.GRAY);
			label_5.setHorizontalAlignment(SwingConstants.CENTER);
			label_5.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_5.setOpaque(true);
		}
		return label_5;
	}

	private JLabel getLblParametersForTraining_1() {
		if (lblParametersForTraining_1 == null) {
			lblParametersForTraining_1 = new JLabel(
					"Parameters for training RLSR:");
			lblParametersForTraining_1.setBounds(404, 139, 499, 30);
			lblParametersForTraining_1.setForeground(Color.WHITE);
			lblParametersForTraining_1.setBackground(Color.GRAY);
			lblParametersForTraining_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblParametersForTraining_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblParametersForTraining_1.setOpaque(true);
		}
		return lblParametersForTraining_1;
	}

	private JLabel getLblLambdaSet() {
		if (lblLambdaSet == null) {
			lblLambdaSet = new JLabel("Lambda set:");
			lblLambdaSet.setHorizontalAlignment(SwingConstants.RIGHT);
			lblLambdaSet.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblLambdaSet.setBounds(439, 180, 188, 30);
		}
		return lblLambdaSet;
	}

	private JLabel getLabel_7() {
		if (label_7 == null) {
			label_7 = new JLabel("No. of hidden neurons for NN:");
			label_7.setHorizontalAlignment(SwingConstants.RIGHT);
			label_7.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_7.setBounds(414, 220, 214, 30);
		}
		return label_7;
	}

	private JTextField getTxtRlsrHidden() {
		if (txtRlsrHidden == null) {
			txtRlsrHidden = new JTextField();
			txtRlsrHidden.setText("0");
			txtRlsrHidden.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtRlsrHidden.setColumns(10);
			txtRlsrHidden.setBounds(638, 222, 169, 30);
		}
		return txtRlsrHidden;
	}

	private JLabel getLabel_8() {
		if (label_8 == null) {
			label_8 = new JLabel("No. of iterations for NN:");
			label_8.setHorizontalAlignment(SwingConstants.RIGHT);
			label_8.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_8.setBounds(455, 262, 172, 30);
		}
		return label_8;
	}

	private JTextField getTextField_1_5() {
		if (txtRlsrIterNN == null) {
			txtRlsrIterNN = new JTextField();
			txtRlsrIterNN.setText("0");
			txtRlsrIterNN.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtRlsrIterNN.setColumns(10);
			txtRlsrIterNN.setBounds(638, 263, 169, 30);
		}
		return txtRlsrIterNN;
	}

	private JTextField getTextField_2_2() {
		if (txtLambda == null) {
			txtLambda = new JTextField();
			txtLambda.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtLambda.setColumns(10);
			txtLambda.setBounds(637, 181, 169, 30);
		}
		return txtLambda;
	}

	private JButton getButton_1() {
		if (button_1 == null) {
			button_1 = new JButton("");
			Style.questionButtonStyle(button_1);
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JOptionPane
							.showMessageDialog(
									mainFrame,
									"Set of hyperparameters for regularizor. It can be array of double values or single value. "
											+ "\nValues should be comma separated."
											+ "\nExample: 0.0001,0.001,0.01,0.01,0.1,1",
									"Help", JOptionPane.QUESTION_MESSAGE,
									Style.questionIcon());
				}
			});
			button_1.setBounds(819, 180, 30, 30);
		}
		return button_1;
	}

	private JLabel getLabel_9() {
		if (label_9 == null) {
			label_9 = new JLabel("SSE max. iterations:");
			label_9.setHorizontalAlignment(SwingConstants.RIGHT);
			label_9.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_9.setBounds(478, 303, 149, 30);
		}
		return label_9;
	}

	private JTextField getTxtSseIter() {
		if (txtSseIter == null) {
			txtSseIter = new JTextField();
			txtSseIter.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtSseIter.setColumns(10);
			txtSseIter.setBounds(638, 304, 169, 30);
		}
		return txtSseIter;
	}

	private JLabel getLabel_10() {
		if (label_10 == null) {
			label_10 = new JLabel("SSE LS max. iterations:");
			label_10.setHorizontalAlignment(SwingConstants.RIGHT);
			label_10.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_10.setBounds(466, 344, 161, 30);
		}
		return label_10;
	}

	private JTextField getTxtLsIter() {
		if (txtLsIter == null) {
			txtLsIter = new JTextField();
			txtLsIter.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtLsIter.setColumns(10);
			txtLsIter.setBounds(636, 346, 169, 30);
		}
		return txtLsIter;
	}

	private JLabel getLblProxytimeout() {
		if (lblProxytimeout == null) {
			lblProxytimeout = new JLabel("Proxy timeout (seconds):");
			lblProxytimeout.setEnabled(false);
			lblProxytimeout.setHorizontalAlignment(SwingConstants.LEFT);
			lblProxytimeout.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblProxytimeout.setBounds(455, 595, 223, 30);
		}
		return lblProxytimeout;
	}

	private JTextField getTxtProxy() {
		if (txtProxy == null) {
			txtProxy = new JTextField();
			txtProxy.setEnabled(false);
			txtProxy.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtProxy.setColumns(10);
			txtProxy.setBounds(640, 595, 169, 30);
		}
		return txtProxy;
	}
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
			separator.setOrientation(SwingConstants.VERTICAL);
			separator.setForeground(Color.GRAY);
			separator.setBackground(Color.GRAY);
			separator.setBounds(404, 11, 2, 627);
		}
		return separator;
	}
	private JSeparator getSeparator_1() {
		if (separator_1 == null) {
			separator_1 = new JSeparator();
			separator_1.setForeground(Color.GRAY);
			separator_1.setBackground(Color.GRAY);
			separator_1.setBounds(0, 636, 950, 16);
		}
		return separator_1;
	}
}
