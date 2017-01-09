package app.gui.panels;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
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
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JTabbedPane;

public class ManageDatasetPanel extends JPanel {

	private static final long serialVersionUID = 7542338256284881226L;
	private JPanel panel;
	private JFrame mainFrame;
	private JScrollPane scrollPaneTable2;
	private JScrollPane scrollPaneTable1;
	private JButton btnDelete1;
	private JTable table1;
	private DefaultTableModel model1;
	private JButton btnDelete2;
	private JTable table2;
	private DefaultTableModel model2;
	private JButton btnRename1;
	private JButton btnRename2;

	public ManageDatasetPanel(JFrame mainFrame) {
//		setBounds(new Rectangle(0, 0, 900, 650));
//		setMinimumSize(new Dimension(500, 500));

		setBackground(UIManager.getColor("Button.background"));
		setLayout(null);

		this.mainFrame = mainFrame;

		JTabbedPane tabbedPane = new JTabbedPane();

		JComponent panel1 = makePanelNetworks();
		tabbedPane
				.addTab("NETWORKS", null, panel1, "MANAGE NETWORKS  DATASETS");

		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JComponent panel2 = makePanelTemporalNetworks();
		tabbedPane.addTab("TEMPORAL NETWORKS", null, panel2,
				"MANAGE TEMPORAL NETWORKS  DATASETS");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		// Add the tabbed pane to this panel.
		tabbedPane.setBounds(0, 0, 900, 650);
		add(tabbedPane);

		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

	}

	protected JComponent makePanelNetworks() {
		JPanel panel = new JPanel(false);
		model1 = createTable1();
		panel.setLayout(null);
		panel.add(getBtnDelete1());
		table1 = new JTable(model1);
		table1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		scrollPaneTable1 = new JScrollPane(table1);
		table1.setFillsViewportHeight(true);
		scrollPaneTable1.setBounds(0, 5, 895, 427);
		panel.add(scrollPaneTable1);
		panel.add(getBtnRename1());

		return panel;
	}

	protected JComponent makePanelTemporalNetworks() {
		JPanel panel = new JPanel(false);
		model2 = createTable2();
		panel.setLayout(null);
		table2 = new JTable(model2);
		scrollPaneTable2 = new JScrollPane(table2);
		table2.setFillsViewportHeight(true);
		scrollPaneTable2.setBounds(0, 5, 895, 427);
		panel.add(scrollPaneTable2);
		panel.add(getBtnDelete2());
		panel.add(getBtnRename2());
		return panel;
	}

	private DefaultTableModel createTable1() {
		String[] columnNames = { "Name", "No of nodes train",
				"No of nodes test" };
		String mainPath = Reader.jarFile() + "/Datasets/Networks";
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

	private DefaultTableModel createTable2() {
		String[] columnNames = { "Name", "No of nodes", "No of time points",
				"No of attributes" };
		String mainPath = Reader.jarFile() + "/Datasets/TemporalNetworks";
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

	private JButton getBtnDelete1() {
		if (btnDelete1 == null) {
			btnDelete1 = new JButton("Delete selected");
			btnDelete1.setBounds(229, 456, 172, 42);
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
							String mainPath = Reader.jarFile()
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
			btnDelete2.setBounds(229, 456, 172, 42);
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
							String mainPath = Reader.jarFile()
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
		
			btnRename1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					if (table1.getSelectedRow() != -1) {
						String name = model1.getValueAt(
								table1.getSelectedRow(), 0).toString();
						String newName = (String) JOptionPane.showInputDialog(
								mainFrame, "Insert new name for dataset", name);
						String mainPath = Reader.jarFile()
								+ "/Datasets/Networks/" + name;
						if ((newName != null) && (newName.length() > 0)) {
							model1.setValueAt(newName, table1.getSelectedRow(),
									0);
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
			Style.buttonStyle(btnRename1);
			btnRename1.setBounds(411, 456, 180, 42);
		}
		return btnRename1;
	}

	private JButton getBtnRename2() {
		if (btnRename2 == null) {
			btnRename2 = new JButton("Rename selected");
			Style.buttonStyle(btnRename2);
			btnRename2.setBounds(411, 456, 180, 42);
			btnRename2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					if (table2.getSelectedRow() != -1) {
						String name = model2.getValueAt(
								table2.getSelectedRow(), 0).toString();
						String newName = (String) JOptionPane.showInputDialog(
								mainFrame, "Insert new name for dataset", name);
						String mainPath = Reader.jarFile()
								+ "/Datasets/TemporalNetworks/" + name;
						if ((newName != null) && (newName.length() > 0)) {
							model2.setValueAt(newName, table2.getSelectedRow(),
									0);
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
