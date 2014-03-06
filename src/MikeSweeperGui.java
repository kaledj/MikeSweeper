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
	
    public static int DIM = 10;
    
    Icon icon = new ImageIcon("resources/10x10.png");
    Icon blankIcon = new ImageIcon("resources/blank.png");
	Icon iconEx = new ImageIcon("resources/10x10ex.png");
	Icon quest = new ImageIcon("resources/quest.png");
	Icon smile = new ImageIcon("resources/smile.png");
	Icon[] numberIcons = new ImageIcon[9];
	
	private MikeSweeper model;
	private JFrame frmMikesweeper;
	private JButton[][] buttons;

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
		System.out.println(model);
	}
	
	public void updateView()
	{
	    int[][] board = model.getBoard();
	    for(int i = 0; i < DIM; i++) {
	        for(int j = 0; j < DIM; j++) {
	            if(!model.getCovered(i, j)) {
	                int val = board[i][j];
    	            if(val == 10) {
    	                buttons[i][j].setIcon(iconEx);
    	            } else if(val == 0){
                        buttons[i][j].setIcon(icon);
                    } else {
    	                buttons[i][j].setIcon(numberIcons[val]);
    	            }
	            }
	        }
	    }
	}

	private void gameOver()
    {
	    int[][] board = model.getBoard();
        for(int i = 0; i < DIM; i++) {
            for(int j = 0; j < DIM; j++) {
                int val = board[i][j];
                if(val == 10) {
                    buttons[i][j].setIcon(iconEx);
                } 
            }
        }
    }

    /**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMikesweeper = new JFrame();
		createButtonsArray();
		createIconsArray();

		frmMikesweeper.setTitle("MikeSweeper");
		frmMikesweeper.setBounds(100, 100, DIM * 39 + 8, DIM * 39 + 8);
		frmMikesweeper.setResizable(false);
		frmMikesweeper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMikesweeper.getContentPane().setLayout(new GridLayout(DIM, DIM, 0, 0));

		// Makes board, and sets buttons to things and stuff.
		model = new MikeSweeper(DIM);
	}

	private void createButtonsArray()
    {
	    buttons = new JButton[DIM][DIM];
	    for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                JButton ij = new JButton(icon);
                ij.addActionListener(this);
                frmMikesweeper.getContentPane().add(new JPanel().add(ij));
                buttons[i][j] = ij;
            }
        }
    }

    private void createIconsArray()
    {
        for(int i = 0; i < numberIcons.length; i++) {
            numberIcons[i] = new ImageIcon("resources/" + i + ".png");
        }
        
    }

    @Override
	public void actionPerformed(ActionEvent e) {
		//findButton(e.getSource());
		int[] clicked = getButtonClicked(e);
		updateModel(clicked[0], clicked[1]);
        updateView();
	}
	
	

	// For later
	public void findButton(Object source) {
		if (((AbstractButton) source).getIcon().equals(icon)) {
			((AbstractButton) source).setIcon(iconEx);
		} else if (((AbstractButton) source).getIcon().equals(quest)) {
			((JButton) source).setIcon(smile);
		}

	}
	
	public int[] getButtonClicked(Object source)
	{
		for (int i = 0; i < buttons.length; i++)
		{
			for (int j = 0; j < buttons[i].length; j++)
			{
				if (source.equals(buttons[i][j]))
				{
					return new int[] {i,j};
				}
			}
		}
		return null;
	}

}
