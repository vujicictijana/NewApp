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
import javax.swing.SwingConstants;

import app.file.io.Reader;
import app.file.io.Writer;
import app.gui.frames.MainFrame;
import app.gui.style.Style;
import app.gui.threads.TestMyModelForGUI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.filechooser.FileNameExtensionFilter;

public class ConfigurePanel extends JPanel {

	private static final long serialVersionUID = 356011421979477981L;
	private JPanel panel;
	private JFrame mainFrame;

	// params

	private double alphaGen;
	private double betaGen;
	private double alpha;
	private double beta;
	private double lr;
	private int iterations;
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
		if (Reader.checkFile("cfg.txt")) {

		} else {
			setUpDefaultValues();
		}
		txtAlphaGen.setText(alphaGen + "");
		txtBetaGen.setText(betaGen + "");
		txtAlpha.setText(alpha + "");
		txtBeta.setText(beta + "");
		txtLR.setText(lr + "");
		txtIter.setText(iterations + "");
		add(getBtnSave());
	}

	public void setUpDefaultValues() {
		alpha = 1;
		beta = 1;
		lr = 0.01;
		iterations = 1000;
		alphaGen = 5;
		betaGen = 1;
	}

	private JTextField getTxtAlphaGen() {
		if (txtAlphaGen == null) {
			txtAlphaGen = new JTextField();
			txtAlphaGen.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAlphaGen.setColumns(10);
			txtAlphaGen.setBounds(210, 73, 169, 30);
		}
		return txtAlphaGen;
	}

	private JLabel getLblub() {
		if (lblub == null) {
			lblub = new JLabel("Alpha:");
			lblub.setHorizontalAlignment(SwingConstants.RIGHT);
			lblub.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblub.setBounds(94, 73, 100, 30);
		}
		return lblub;
	}

	private JLabel getLblParametersForRandom() {
		if (lblParametersForRandom == null) {
			lblParametersForRandom = new JLabel(
					"Parameters for random data generation:");
			lblParametersForRandom.setHorizontalAlignment(SwingConstants.LEFT);
			lblParametersForRandom.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblParametersForRandom.setBounds(75, 27, 434, 30);
		}
		return lblParametersForRandom;
	}

	private JTextField getTxtBetaGen() {
		if (txtBetaGen == null) {
			txtBetaGen = new JTextField();
			txtBetaGen.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtBetaGen.setColumns(10);
			txtBetaGen.setBounds(210, 114, 169, 30);
		}
		return txtBetaGen;
	}

	private JLabel getLblBeta() {
		if (lblBeta == null) {
			lblBeta = new JLabel("Beta:");
			lblBeta.setHorizontalAlignment(SwingConstants.RIGHT);
			lblBeta.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblBeta.setBounds(94, 114, 100, 30);
		}
		return lblBeta;
	}

	private JLabel getLblParametersForModel() {
		if (lblParametersForModel == null) {
			lblParametersForModel = new JLabel(
					"Parameters for training the model:");
			lblParametersForModel.setHorizontalAlignment(SwingConstants.LEFT);
			lblParametersForModel.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblParametersForModel.setBounds(75, 166, 434, 30);
		}
		return lblParametersForModel;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("Alpha:");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label.setBounds(94, 208, 100, 30);
		}
		return label;
	}

	private JTextField getTextField_1() {
		if (txtAlpha == null) {
			txtAlpha = new JTextField();
			txtAlpha.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAlpha.setColumns(10);
			txtAlpha.setBounds(210, 208, 169, 30);
		}
		return txtAlpha;
	}

	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("Beta:");
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);
			label_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
			label_1.setBounds(94, 249, 100, 30);
		}
		return label_1;
	}

	private JTextField getTextField_1_1() {
		if (txtBeta == null) {
			txtBeta = new JTextField();
			txtBeta.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtBeta.setColumns(10);
			txtBeta.setBounds(210, 249, 169, 30);
		}
		return txtBeta;
	}

	private JLabel getLblLearningRate() {
		if (lblLearningRate == null) {
			lblLearningRate = new JLabel("Learning rate:");
			lblLearningRate.setHorizontalAlignment(SwingConstants.RIGHT);
			lblLearningRate.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblLearningRate.setBounds(94, 290, 100, 30);
		}
		return lblLearningRate;
	}

	private JTextField getTextField_2() {
		if (txtLR == null) {
			txtLR = new JTextField();
			txtLR.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtLR.setColumns(10);
			txtLR.setBounds(210, 290, 169, 30);
		}
		return txtLR;
	}

	private JTextField getTextField_1_2() {
		if (txtIter == null) {
			txtIter = new JTextField();
			txtIter.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtIter.setColumns(10);
			txtIter.setBounds(210, 331, 169, 30);
		}
		return txtIter;
	}

	private JLabel getLblMaxIterations() {
		if (lblMaxIterations == null) {
			lblMaxIterations = new JLabel("Max. iterations:");
			lblMaxIterations.setHorizontalAlignment(SwingConstants.RIGHT);
			lblMaxIterations.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblMaxIterations.setBounds(75, 331, 119, 30);
		}
		return lblMaxIterations;
	}

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					MainFrame m = (MainFrame) mainFrame;
					m.enableMenu();
					panel.removeAll();
					panel.repaint();
					panel.revalidate();
				}
			});
			btnSave.setBounds(237, 388, 112, 45);
		}
		return btnSave;
	}
}
