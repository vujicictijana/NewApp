package app.gui.panels;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.border.LineBorder;

import java.awt.SystemColor;

import javax.swing.UIManager;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;

public class TrainPanel extends JPanel {
	private JTextField txtFile;
	private JLabel lblFile;
	private JButton btnBrowse;
	private JButton btnQuestion;
	private JLabel lblModelName;
	private JTextField txtModelName;

	/**
	 * Create the panel.
	 */
	public TrainPanel() {
		setBackground(UIManager.getColor("Button.background"));
		setLayout(null);
		add(getTxtFile());
		add(getLblFile());
		add(getBtnBrowse());
		add(getBtnQuestion());
		add(getLblModelName());
		add(getTxtModelName());
	}
	private JTextField getTxtFile() {
		if (txtFile == null) {
			txtFile = new JTextField();
			txtFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtFile.setBounds(126, 34, 315, 30);
			txtFile.setColumns(10);
		}
		return txtFile;
	}
	private JLabel getLblFile() {
		if (lblFile == null) {
			lblFile = new JLabel("File:");
			lblFile.setHorizontalAlignment(SwingConstants.RIGHT);
			lblFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblFile.setBounds(10, 34, 100, 30);
		}
		return lblFile;
	}
	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton("Browse");
			btnBrowse.setBackground(UIManager.getColor("Button.background"));
			btnBrowse.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			btnBrowse.setBounds(458, 33, 100, 30);
			btnBrowse.setFocusPainted(false);
		}
		return btnBrowse;
	}
	private JButton getBtnQuestion() {
		if (btnQuestion == null) {
			btnQuestion = new JButton("");
			btnQuestion.setBorder(null);
			btnQuestion.setBackground(UIManager.getColor("Button.background"));
			btnQuestion.setBounds(577, 34, 30, 30);
			btnQuestion.setIcon(new ImageIcon("images/question.png"));
			btnQuestion.setOpaque(false);
			btnQuestion.setContentAreaFilled(false);
			btnQuestion.setBorderPainted(false);
			btnQuestion.setFocusPainted(false);

		}
		return btnQuestion;
	}
	private JLabel getLblModelName() {
		if (lblModelName == null) {
			lblModelName = new JLabel("Model name:");
			lblModelName.setHorizontalAlignment(SwingConstants.RIGHT);
			lblModelName.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblModelName.setBounds(10, 75, 100, 30);
		}
		return lblModelName;
	}
	private JTextField getTxtModelName() {
		if (txtModelName == null) {
			txtModelName = new JTextField();
			txtModelName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtModelName.setColumns(10);
			txtModelName.setBounds(126, 75, 315, 30);
		}
		return txtModelName;
	}
}
