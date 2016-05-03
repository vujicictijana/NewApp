package app.gui.panels;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;

import javax.swing.UIManager;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.SwingConstants;
import app.gui.style.Style;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

	/**
	 * Create the panel.
	 */
	public TrainPanel() {
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
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		fc.setFileFilter(filter);
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
			lblAlpha.setBounds(67, 197, 100, 30);
		}
		return lblAlpha;
	}

	private JLabel getLblFirstBeta() {
		if (lblFirstBeta == null) {
			lblFirstBeta = new JLabel("First beta:");
			lblFirstBeta.setHorizontalAlignment(SwingConstants.RIGHT);
			lblFirstBeta.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblFirstBeta.setBounds(67, 237, 100, 30);
		}
		return lblFirstBeta;
	}

	private JTextField getTxtAlpha() {
		if (txtAlpha == null) {
			txtAlpha = new JTextField();
			txtAlpha.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAlpha.setColumns(10);
			txtAlpha.setBounds(183, 198, 91, 30);
		}
		return txtAlpha;
	}

	private JTextField getTxtBeta() {
		if (txtBeta == null) {
			txtBeta = new JTextField();
			txtBeta.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtBeta.setColumns(10);
			txtBeta.setBounds(183, 239, 91, 30);
		}
		return txtBeta;
	}

	private JLabel getLblLearningRate() {
		if (lblLearningRate == null) {
			lblLearningRate = new JLabel("Learning rate:");
			lblLearningRate.setHorizontalAlignment(SwingConstants.RIGHT);
			lblLearningRate.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblLearningRate.setBounds(67, 277, 100, 30);
		}
		return lblLearningRate;
	}

	private JTextField getTxtLr() {
		if (txtLr == null) {
			txtLr = new JTextField();
			txtLr.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtLr.setColumns(10);
			txtLr.setBounds(183, 277, 91, 30);
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

				}
			});
			Style.buttonStyle(btnTrain);
			btnTrain.setBounds(338, 365, 112, 45);
		}
		return btnTrain;
	}

	private JLabel getLblMaxIterations() {
		if (lblMaxIterations == null) {
			lblMaxIterations = new JLabel("Max. iterations:");
			lblMaxIterations.setHorizontalAlignment(SwingConstants.RIGHT);
			lblMaxIterations.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblMaxIterations.setBounds(35, 317, 132, 30);
		}
		return lblMaxIterations;
	}

	private JTextField getTxtMaxIter() {
		if (txtMaxIter == null) {
			txtMaxIter = new JTextField();
			txtMaxIter.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtMaxIter.setColumns(10);
			txtMaxIter.setBounds(183, 317, 91, 30);
		}
		return txtMaxIter;
	}
	
	public void chooseFile(JTextField txt){
		int returnVal = fc.showOpenDialog(panel);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			txt.setText(fc.getSelectedFile().getPath());
		}
	}
}
