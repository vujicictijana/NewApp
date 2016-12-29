package app.gui.panels;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import app.file.io.Reader;
import app.gui.frames.MainFrame;
import app.gui.style.SwingLink;

import javax.swing.SwingConstants;

public class HelpPanel extends JPanel {

	private static final long serialVersionUID = 7542338256284881226L;
	private JPanel panel;
	private JFrame mainFrame;
	private JLabel lblText;

	public HelpPanel(JFrame mainFrame, String fileName) {
		setBounds(new Rectangle(0, 0, 900, 650));
		setMinimumSize(new Dimension(500, 500));

		setBackground(UIManager.getColor("Button.background"));
		setLayout(null);

		this.mainFrame = mainFrame;

		panel = this;
		if (fileName != null) {
			
			String initialText = getHtml(fileName);
			lblText = new JLabel(initialText);
			lblText.setVerticalAlignment(SwingConstants.TOP);
			lblText.setBounds(10, 10, 850, 750);

			JScrollPane scrollPane = new JScrollPane(lblText);
			scrollPane.setBounds(10, 10, 850, 700);
			panel = this;
			panel.add(scrollPane);
		} else {
			setLinks();
		}
	}

	private String getHtml(String fileName) {
		String initialText="";
		String[] read = Reader.read(fileName);
		for (int i = 0; i < read.length; i++) {
			initialText += read[i];
		}
		return initialText;
	}

	public String[] generateLinks() {
		String[] methods = new String[6];
		methods[0] = "GCRF - http://www.ist.temple.edu/~zoran/papers/ECAI125.pdf";
		methods[1] = "DirGCRF – unpublished";
		methods[2] = "UmGCRF – http://www.ist.temple.edu/~zoran/papers/jesseAAAI2016.pdf";
		methods[3] = "RLSR - http://www.ist.temple.edu/~zoran/papers/chaoSDM2016.pdf";
		methods[4] = "up-GCRF - http://www.ist.temple.edu/~zoran/papers/djoleAAAI2016.pdf";
		methods[5] = "m-GCRF - http://www.dabi.temple.edu/~zoran/papers/StojanovicSDM15.pdf";
		return methods;
	}

	public void setLinks() {
		String initialText = getHtml(Reader.jarFile() + "/html/methods.html");
		lblText = new JLabel(initialText);
		lblText.setVerticalAlignment(SwingConstants.TOP);
		lblText.setBounds(10, 10, 850, 500);
		JScrollPane scrollPane = new JScrollPane(lblText);
		scrollPane.setBounds(10,10, 850, 500);
		panel.add(scrollPane);
		
		JLabel label = new JLabel("Links:");
		int y = 520;
		label.setBounds(20, y, 900, 20);
		panel.add(label);

		String[] links = generateLinks();

		for (int j = 0; j < links.length; j++) {
			if (!links[j].contains("unpublished")) {
				String name = links[j].substring(0,
						links[j].lastIndexOf(" ") - 2);
				String link = links[j].substring(links[j].lastIndexOf(" ") + 1);
				SwingLink linkLabel = new SwingLink(name, link);
				linkLabel.setBounds(20, y + (20 * (j + 1)), 900, 20);
				panel.add(linkLabel);
			} else {
				JLabel label1 = new JLabel(links[j]);
				label1.setBounds(20, y + (20 * (j + 1)), 900, 20);
				panel.add(label1);
			}
		}

	}

}
