import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MikeSweeperGui implements ActionListener {
	Icon icon = new ImageIcon("resources/10x10.png");
	Icon iconEx = new ImageIcon("resources/10x10ex.png");
	Icon quest = new ImageIcon("resources/quest.png");
	Icon smile = new ImageIcon("resources/smile.png");
	private MikeSweeper model;

	private JFrame frmMikesweeper;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MikeSweeperGui window = new MikeSweeperGui();
					window.frmMikesweeper.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MikeSweeperGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMikesweeper = new JFrame();
		frmMikesweeper.setTitle("MikeSweeper");
		frmMikesweeper.setBounds(100, 100, 400, 450);
		frmMikesweeper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMikesweeper.getContentPane().setLayout(new GridLayout(10, 10, 0, 0));

		// Makes board, and sets buttons to things and stuff.
		model = new MikeSweeper(10);
		int[][] tempArray = model.getBoard();
		for (int i = 0; i < tempArray.length; i++) {
			for (int j = 0; j < tempArray[i].length; j++) {
				// This is a dumb way to name the buttons
				if (tempArray[i][j] == 10) {
					JButton ij = new JButton(icon);
					ij.addActionListener(this);
					frmMikesweeper.getContentPane().add(new JPanel().add(ij));
				} else {
					JButton ij = new JButton(quest);
					ij.addActionListener(this);
					frmMikesweeper.getContentPane().add(new JPanel().add(ij));
				}

			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		findButton(e.getSource());

	}

	// For later
	public void findButton(Object source) {
		if (((AbstractButton) source).getIcon().equals(icon)) {
			((AbstractButton) source).setIcon(iconEx);
		} else if (((AbstractButton) source).getIcon().equals(quest)) {
			((JButton) source).setIcon(smile);
		}

	}

}
