package app.gui.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JMenuBar;

import app.file.io.Reader;
import app.gui.panels.AddDatasetPanel;
import app.gui.panels.ConfigurePanel;
import app.gui.panels.HelpPanel;
import app.gui.panels.ManageDatasetPanel;
import app.gui.panels.PredictPanel;
import app.gui.panels.TestPanel;
import app.gui.panels.TestRandomPanel;
import app.gui.panels.TrainPanel;
import app.gui.panels.TrainRandomPanel;
import app.gui.panels.TrainTemporalPanel;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JButton;

public class a extends JFrame {

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JPanel panel;
	private JMenu mnTrain;
	private JMenuItem menuTrain;
	private JMenuItem menuTrainRandom;
	private JMenu mnTest;
	private JMenuItem menuTest;
	private JMenuItem menuTestRandom;
	private JMenu mnSettings;
	private JMenuItem menuFile;
	private JMenuItem menuParameters;
	private JFrame frame;
	private JPanel mainPanel;
	private JMenu mnPredict;
	private JMenuItem mnPredictYour;
	private JMenuItem mntmNewMenuItem;
	private JMenu mnDatasets;
	private JMenuItem menuAddDataset;
	private JMenuItem menuManageDatasets;
	private JMenu mnHelp;
	private JMenuItem menuAbout;
	private JMenuItem menuDatasets;
	private JMenuItem menuMethods;
	private JPanel panel_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					a frame = new a();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public a() {
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(getPanel(), gbc_panel);
	}

	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new GridLayout(1, 0, 0, 0));
			panel.add(getPanel_1());
		}
		return panel;
	}
	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.setBackground(Color.WHITE);
			menuBar.setBounds(0, 0, 900, 59);
			menuBar.add(getMnTrain());
			menuBar.add(getMnTest());
//			menuBar.add(getMnPredict());
			menuBar.add(getMnDatasets());
			menuBar.add(getMnSettings());
			menuBar.add(getMnHelp());
		}
		return menuBar;
	}

	private JMenu getMnTrain() {
		if (mnTrain == null) {
			mnTrain = new JMenu("Train");
			mnTrain.setIcon(new ImageIcon(Reader.jarFile() + "/images/train.png"));
			mnTrain.setFont(new Font("Segoe UI", Font.BOLD, 17));
			mnTrain.add(getMenuTrain());
			mnTrain.add(new JSeparator());
			mnTrain.add(getMntmNewMenuItem());
			mnTrain.add(new JSeparator());
			mnTrain.add(getMenuTrainRandom());
		}
		return mnTrain;
	}

	private JMenuItem getMenuTrain() {
		if (menuTrain == null) {
			menuTrain = new JMenuItem("Train on networks");
			menuTrain.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			menuTrain.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					TrainPanel t = new TrainPanel(frame);
					t.setBounds(0, 61, 900, 750);
					if (mainPanel != null) {
						contentPane.remove(mainPanel);
						contentPane.repaint();
						contentPane.revalidate();
					}
					mainPanel = t;
					contentPane.add(mainPanel);
					contentPane.repaint();
					contentPane.revalidate();

				}
			});
		}
		return menuTrain;
	}

	private JMenuItem getMenuTrainRandom() {
		if (menuTrainRandom == null) {
			menuTrainRandom = new JMenuItem("Train on random networks");
			menuTrainRandom.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			menuTrainRandom
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(
								java.awt.event.ActionEvent evt) {
							TrainRandomPanel t = new TrainRandomPanel(frame);
							t.setBounds(0, 61, 900, 750);
							if (mainPanel != null) {
								contentPane.remove(mainPanel);
								contentPane.repaint();
								contentPane.revalidate();
							}
							mainPanel = t;
							contentPane.add(mainPanel);
							contentPane.repaint();
							contentPane.revalidate();
						}
					});
		}
		return menuTrainRandom;
	}

	private JMenu getMnTest() {
		if (mnTest == null) {
			mnTest = new JMenu("Test");
			mnTest.setFont(new Font("Segoe UI", Font.BOLD, 17));
			mnTest.setIcon(new ImageIcon(Reader.jarFile() +"/images/test.png"));
			mnTest.add(getMenuTest());
			mnTest.add(new JSeparator());
			mnTest.add(getMenuTestRandom());
		}
		return mnTest;
	}

	private JMenuItem getMenuTest() {
		if (menuTest == null) {
			menuTest = new JMenuItem("Test on networks");
			menuTest.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			menuTest.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					TestPanel t = new TestPanel(frame);
					t.setBounds(0, 61, 900, 500);
					if (mainPanel != null) {
						contentPane.remove(mainPanel);
						contentPane.repaint();
						contentPane.revalidate();
					}
					mainPanel = t;
					contentPane.add(mainPanel);
					contentPane.repaint();
					contentPane.revalidate();
				}
			});
		}
		return menuTest;
	}

	private JMenuItem getMenuTestRandom() {
		if (menuTestRandom == null) {
			menuTestRandom = new JMenuItem("Test on random networks");
			menuTestRandom.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			menuTestRandom
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(
								java.awt.event.ActionEvent evt) {
							TestRandomPanel t = new TestRandomPanel(frame);
							t.setBounds(0, 61, 900, 500);
							if (mainPanel != null) {
								contentPane.remove(mainPanel);
								contentPane.repaint();
								contentPane.revalidate();
							}
							mainPanel = t;
							contentPane.add(mainPanel);
							contentPane.repaint();
							contentPane.revalidate();
						}
					});
		}
		return menuTestRandom;
	}

	private JMenu getMnSettings() {
		if (mnSettings == null) {
			mnSettings = new JMenu("Settings");
			mnSettings.setFont(new Font("Segoe UI", Font.BOLD, 17));
			mnSettings.setIcon(new ImageIcon(Reader.jarFile() +"/images/settings.png"));
			mnSettings.add(getMenuParameters());
			// mnSettings.add(new JSeparator());
			// mnSettings.add(getMenuFile());
			// mnSettings.add(new JSeparator());

		}
		return mnSettings;
	}

	private JMenuItem getMenuFile() {
		if (menuFile == null) {
			menuFile = new JMenuItem("File system");
			menuFile.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		}
		return menuFile;
	}

	private JMenuItem getMenuParameters() {
		if (menuParameters == null) {
			menuParameters = new JMenuItem("Configuration");
			menuParameters.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			menuParameters
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(
								java.awt.event.ActionEvent evt) {
							ConfigurePanel c = new ConfigurePanel(frame);
							c.setBounds(0, 61, 900, 750);
							if (mainPanel != null) {
								contentPane.remove(mainPanel);
								contentPane.repaint();
								contentPane.revalidate();
							}
							mainPanel = c;
							contentPane.add(mainPanel);
							contentPane.repaint();
							contentPane.revalidate();
						}
					});
		}
		return menuParameters;
	}

	private JMenu getMnPredict() {
		if (mnPredict == null) {
			mnPredict = new JMenu("Predict");
			mnPredict.setFont(new Font("Segoe UI", Font.BOLD, 17));
			mnPredict.setIcon(new ImageIcon(Reader.jarFile() + "/images/predict.png"));
			mnPredict.add(getMnPredictYour());
		}
		return mnPredict;
	}

	private JMenuItem getMnPredictYour() {
		if (mnPredictYour == null) {
			mnPredictYour = new JMenuItem("Predict using existing model");
			mnPredictYour.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			mnPredictYour.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					PredictPanel t = new PredictPanel(frame);
					t.setBounds(0, 61, 900, 500);
					if (mainPanel != null) {
						contentPane.remove(mainPanel);
						contentPane.repaint();
						contentPane.revalidate();
					}
					mainPanel = t;
					contentPane.add(mainPanel);
					contentPane.repaint();
					contentPane.revalidate();
				}
			});
		}
		return mnPredictYour;
	}

	private JMenuItem getMntmNewMenuItem() {
		if (mntmNewMenuItem == null) {
			mntmNewMenuItem = new JMenuItem("Train on temporal networks");
			mntmNewMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					TrainTemporalPanel t = new TrainTemporalPanel(frame);
					t.setBounds(0, 61, 900, 750);
					if (mainPanel != null) {
						contentPane.remove(mainPanel);
						contentPane.repaint();
						contentPane.revalidate();
					}
					mainPanel = t;
					contentPane.add(mainPanel);
					contentPane.repaint();
					contentPane.revalidate();
				}
			});
			mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		}
		return mntmNewMenuItem;
	}
	private JMenu getMnDatasets() {
		if (mnDatasets == null) {
			mnDatasets = new JMenu("Datasets");
			mnDatasets.setFont(new Font("Segoe UI", Font.BOLD, 17));
			mnDatasets.setIcon(new ImageIcon(Reader.jarFile() + "/images/data.png"));
			mnDatasets.add(getMenuAddDataset());
			mnDatasets.add(new JSeparator());
			mnDatasets.add(getMenuManageDatasets());
		}
		return mnDatasets;
	}
	private JMenuItem getMenuAddDataset() {
		if (menuAddDataset == null) {
			menuAddDataset = new JMenuItem("Add dataset");
			menuAddDataset.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			menuAddDataset.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					AddDatasetPanel t = new AddDatasetPanel(frame);
					t.setBounds(0, 61, 900, 750);
					if (mainPanel != null) {
						contentPane.remove(mainPanel);
						contentPane.repaint();
						contentPane.revalidate();
					}
					mainPanel = t;
					contentPane.add(mainPanel);
					contentPane.repaint();
					contentPane.revalidate();

				}
			});
		}
		return menuAddDataset;
	}
	private JMenuItem getMenuManageDatasets() {
		if (menuManageDatasets == null) {
			menuManageDatasets = new JMenuItem("Manage datasets");
			menuManageDatasets.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			menuManageDatasets.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					ManageDatasetPanel t = new ManageDatasetPanel(frame);
					t.setBounds(0, 61, 900, 750);
					if (mainPanel != null) {
						contentPane.remove(mainPanel);
						contentPane.repaint();
						contentPane.revalidate();
					}
					mainPanel = t;
					contentPane.add(mainPanel);
					contentPane.repaint();
					contentPane.revalidate();

				}
			});
		}
		return menuManageDatasets;
	}
	private JMenu getMnHelp() {
		if (mnHelp == null) {
			mnHelp = new JMenu("Help");
			mnHelp.setFont(new Font("Segoe UI", Font.BOLD, 17));
			mnHelp.setIcon(new ImageIcon(Reader.jarFile() +"/images/question-menu.png"));
			mnHelp.add(getMenuAbout());
			mnHelp.add(new JSeparator());
			mnHelp.add(getMenuDatasets());
			mnHelp.add(new JSeparator());
			mnHelp.add(getMenuMethods());
		}
		return mnHelp;
	}
	private JMenuItem getMenuAbout() {
		if (menuAbout == null) {
			menuAbout = new JMenuItem("About");
			menuAbout.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			menuAbout.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					HelpPanel t = new HelpPanel(frame,Reader.jarFile()  + "/html/about.html");
					t.setBounds(0, 61, 900, 750);
					if (mainPanel != null) {
						contentPane.remove(mainPanel);
						contentPane.repaint();
						contentPane.revalidate();
					}
					mainPanel = t;
					contentPane.add(mainPanel);
					contentPane.repaint();
					contentPane.revalidate();

				}
			});
		}
		return menuAbout;
	}
	private JMenuItem getMenuDatasets() {
		if (menuDatasets == null) {
			menuDatasets = new JMenuItem("Datasets");
			menuDatasets.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			menuDatasets.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {

					HelpPanel t = new HelpPanel(frame,Reader.jarFile() + "/html/dataset.html");
					t.setBounds(0, 61, 900, 750);
					if (mainPanel != null) {
						contentPane.remove(mainPanel);
						contentPane.repaint();
						contentPane.revalidate();
					}
					mainPanel = t;
					contentPane.add(mainPanel);
					contentPane.repaint();
					contentPane.revalidate();

				}
			});
		}
		return menuDatasets;
	}
	private JMenuItem getMenuMethods() {
		if (menuMethods == null) {
			menuMethods = new JMenuItem("Methods");
			menuMethods.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			menuMethods.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					HelpPanel t = new HelpPanel(frame,null);
					t.setBounds(0, 61, 900, 750);
					if (mainPanel != null) {
						contentPane.remove(mainPanel);
						contentPane.repaint();
						contentPane.revalidate();
					}
					mainPanel = t;
					contentPane.add(mainPanel);
					contentPane.repaint();
					contentPane.revalidate();

				}
			});
		}
		return menuMethods;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			gbl_panel_1.columnWidths = new int[]{0};
			gbl_panel_1.rowHeights = new int[]{0};
			gbl_panel_1.columnWeights = new double[]{Double.MIN_VALUE};
			gbl_panel_1.rowWeights = new double[]{Double.MIN_VALUE};
			panel_1.setLayout(gbl_panel_1);
			panel_1 = new b();
		}
		return panel_1;
	}
}
