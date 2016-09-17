package app.gui.panels;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import app.file.io.Reader;
import app.file.io.Writer;
import app.gui.frames.MainFrame;
import app.gui.style.Style;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

public class ManageDatasetPanel extends JPanel {

	private static final long serialVersionUID = 7542338256284881226L;
	private JPanel panel;
	private JFrame mainFrame;
	private JLabel lblDatasetsNetworks;
	private JLabel lblDatasetsTemporalNetworks;
	private JScrollPane scrollPaneTable2;
	private JScrollPane scrollPaneTable1;
	private JButton btnDelete1;
	private JTable table1;
	private DefaultTableModel model1;
	private String path;
	private JButton btnDelete2;
	private JTable table2;
	private DefaultTableModel model2;
	private JButton btnRename1;
	private JButton btnRename2;

	public ManageDatasetPanel(JFrame mainFrame) {
		setBounds(new Rectangle(0, 0, 900, 650));
		setMinimumSize(new Dimension(500, 500));

		setBackground(UIManager.getColor("Button.background"));
		setLayout(null);

		this.mainFrame = mainFrame;

		URL location = MainFrame.class.getProtectionDomain().getCodeSource()
				.getLocation();
		path = location.getFile();
		path = path.substring(1, path.lastIndexOf("/"));

		model1 = createTable1(path);

		table1 = new JTable(model1);
		table1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		scrollPaneTable1 = new JScrollPane(table1);
		table1.setFillsViewportHeight(true);
		scrollPaneTable1.setBounds(28, 41, 850, 203);

		model2 = createTable2(path);
		table2 = new JTable(model2);
		scrollPaneTable2 = new JScrollPane(table2);
		table2.setFillsViewportHeight(true);
		scrollPaneTable2.setBounds(28, 368, 850, 203);

		panel = this;
		panel.add(scrollPaneTable1);
		panel.add(scrollPaneTable2);
		add(getLblDatasetsNetworks());
		add(getLblDatasetsTemporalNetworks());
		add(getBtnDelete1());
		add(getBtnDelete2());
		add(getBtnRename1());
		add(getBtnRename2());

	}

	private DefaultTableModel createTable1(String path) {
		String[] columnNames = { "Name", "No of nodes train",
				"No of nodes test" };
		String mainPath = path.substring(0, path.lastIndexOf("/"))
				+ "/Datasets/Networks";
		String[] files = Reader.getAllFolders(mainPath);

		Object[][] data = new Object[files.length][3];
		String readme = "";
		String[] lines = null;
		for (int i = 0; i < files.length; i++) {
			readme = mainPath + "/" + files[i] + "/readme.txt";
			lines = Reader.read(readme);
			data[i][0] = files[i];
			data[i][1] = Integer.parseInt(lines[0].substring(lines[0]
					.indexOf(":") + 2));
			data[i][2] = Integer.parseInt(lines[1].substring(lines[1]
					.indexOf(":") + 2));
		}

		return new DefaultTableModel(data, columnNames) {

			private static final long serialVersionUID = -7347188371755670835L;

			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}

		};
	}

	private DefaultTableModel createTable2(String path) {
		String[] columnNames = { "Name", "No of nodes", "No of time points",
				"No of attributes" };
		String mainPath = path.substring(0, path.lastIndexOf("/"))
				+ "/Datasets/TemporalNetworks";
		String[] files = Reader.getAllFolders(mainPath);

		Object[][] data = new Object[files.length][4];
		String readme = "";
		String[] lines = null;
		for (int i = 0; i < files.length; i++) {
			readme = mainPath + "/" + files[i] + "/readme.txt";
			lines = Reader.read(readme);
			data[i][0] = files[i];
			data[i][1] = Integer.parseInt(lines[0].substring(lines[0]
					.indexOf(":") + 2));
			data[i][2] = Integer.parseInt(lines[1].substring(lines[1]
					.indexOf(":") + 2));
			data[i][3] = Integer.parseInt(lines[2].substring(lines[2]
					.indexOf(":") + 2));
		}

		return new DefaultTableModel(data, columnNames) {

			private static final long serialVersionUID = 7690123115074512018L;

			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}

		};
	}

	private JLabel getLblDatasetsNetworks() {
		if (lblDatasetsNetworks == null) {
			lblDatasetsNetworks = new JLabel("DATASETS NETWORKS:");
			lblDatasetsNetworks.setOpaque(true);
			lblDatasetsNetworks.setHorizontalAlignment(SwingConstants.CENTER);
			lblDatasetsNetworks.setForeground(Color.WHITE);
			lblDatasetsNetworks.setFont(new Font("Segoe UI", Font.BOLD, 15));
			lblDatasetsNetworks.setBackground(Color.GRAY);
			lblDatasetsNetworks.setBounds(0, 0, 901, 30);
		}
		return lblDatasetsNetworks;
	}

	private JLabel getLblDatasetsTemporalNetworks() {
		if (lblDatasetsTemporalNetworks == null) {
			lblDatasetsTemporalNetworks = new JLabel(
					"DATASETS TEMPORAL NETWORKS:");
			lblDatasetsTemporalNetworks.setOpaque(true);
			lblDatasetsTemporalNetworks
					.setHorizontalAlignment(SwingConstants.CENTER);
			lblDatasetsTemporalNetworks.setForeground(Color.WHITE);
			lblDatasetsTemporalNetworks.setFont(new Font("Segoe UI", Font.BOLD,
					15));
			lblDatasetsTemporalNetworks.setBackground(Color.GRAY);
			lblDatasetsTemporalNetworks.setBounds(0, 327, 901, 30);
		}
		return lblDatasetsTemporalNetworks;
	}

	private JButton getBtnDelete1() {
		if (btnDelete1 == null) {
			btnDelete1 = new JButton("Delete selected");
			btnDelete1.setBounds(28, 266, 180, 23);
			Style.buttonStyle(btnDelete1);
			btnDelete1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (table1.getSelectedRow() != -1) {
						String name = model1.getValueAt(
								table1.getSelectedRow(), 0).toString();
						int selectedOption = JOptionPane.showConfirmDialog(
								mainFrame,

								"Are you sure that you want to permanently delete \""
										+ name + "\" dataset?", "Question",
								JOptionPane.YES_NO_OPTION);
						if (selectedOption == JOptionPane.YES_OPTION) {
							String mainPath = path.substring(0,
									path.lastIndexOf("/"))
									+ "/Datasets/Networks/" + name;
							Reader.deleteDir(new File(mainPath));
							model1.removeRow(table1.getSelectedRow());
						}
						if (selectedOption == JOptionPane.NO_OPTION) {
							return;
						}
					} else {
						JOptionPane.showMessageDialog(mainFrame,
								"Please select dataset.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return btnDelete1;
	}

	private JButton getBtnDelete2() {
		if (btnDelete2 == null) {
			btnDelete2 = new JButton("Delete selected");
			btnDelete2.setBounds(28, 596, 180, 23);
			Style.buttonStyle(btnDelete2);
			btnDelete2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (table2.getSelectedRow() != -1) {
						String name = model2.getValueAt(
								table2.getSelectedRow(), 0).toString();
						int selectedOption = JOptionPane.showConfirmDialog(
								mainFrame,

								"Are you sure that you want to permanently delete \""
										+ name + "\" dataset?", "Question",
								JOptionPane.YES_NO_OPTION);
						if (selectedOption == JOptionPane.YES_OPTION) {
							String mainPath = path.substring(0,
									path.lastIndexOf("/"))
									+ "/Datasets/TemporalNetworks/" + name;
							Reader.deleteDir(new File(mainPath));
							model2.removeRow(table2.getSelectedRow());
						}
						if (selectedOption == JOptionPane.NO_OPTION) {
							return;
						}
					} else {
						JOptionPane.showMessageDialog(mainFrame,
								"Please select dataset.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return btnDelete2;
	}

	private JButton getBtnRename1() {
		if (btnRename1 == null) {
			btnRename1 = new JButton("Rename selected");
			Style.buttonStyle(btnRename1);
			btnRename1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					if (table1.getSelectedRow() != -1) {
						String name = model1.getValueAt(
								table1.getSelectedRow(), 0).toString();
						String newName = (String) JOptionPane.showInputDialog(
								mainFrame, "Insert new name for dataset", name);
						String mainPath = path.substring(0,
								path.lastIndexOf("/"))
								+ "/Datasets/Networks/" + name;
						if ((newName != null) && (newName.length() > 0)) {
							model1.setValueAt(newName, table1.getSelectedRow(), 0);
							Writer.renameDir(mainPath, newName);
							return;
						}
					} else {
						JOptionPane.showMessageDialog(mainFrame,
								"Please select dataset.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			});
			btnRename1.setBounds(236, 266, 180, 23);
		}
		return btnRename1;
	}
	private JButton getBtnRename2() {
		if (btnRename2 == null) {
			btnRename2 = new JButton("Rename selected");
			btnRename2.setBounds(236, 596, 180, 23);
			Style.buttonStyle(btnRename2);
			btnRename2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					if (table2.getSelectedRow() != -1) {
						String name = model2.getValueAt(
								table2.getSelectedRow(), 0).toString();
						String newName = (String) JOptionPane.showInputDialog(
								mainFrame, "Insert new name for dataset", name);
						String mainPath = path.substring(0,
								path.lastIndexOf("/"))
								+ "/Datasets/TemporalNetworks/" + name;
						if ((newName != null) && (newName.length() > 0)) {
							model2.setValueAt(newName, table2.getSelectedRow(), 0);
							Writer.renameDir(mainPath, newName);
							return;
						}
					} else {
						JOptionPane.showMessageDialog(mainFrame,
								"Please select dataset.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			});
		}
		return btnRename2;
	}
}
