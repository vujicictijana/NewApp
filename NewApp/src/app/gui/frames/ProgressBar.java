package app.gui.frames;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class ProgressBar extends JFrame {

	private JProgressBar current;

	public ProgressBar(int max) {
		super("Progress");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel pane = new JPanel();
		pane.setLayout(new FlowLayout());
		current = new JProgressBar(0, max);
		current.setValue(0);
		current.setStringPainted(true);
		Dimension prefSize = current.getPreferredSize();
		prefSize.width = 350;
		current.setPreferredSize(prefSize);
		pane.add(current);
		pane.setPreferredSize(new Dimension(400,50));
		setContentPane(pane);
		
	}

	public JProgressBar getCurrent() {
		return current;
	}

	public void setCurrent(JProgressBar current) {
		this.current = current;
	}

}