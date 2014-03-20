import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.Timer;

public class MikeSweeperGui implements ActionListener {
	
    Icon icon = new ImageIcon("resources/10x10.png");
    Icon blankIcon = new ImageIcon("resources/blank.png");
	Icon iconEx = new ImageIcon("resources/10x10ex.png");
	Icon quest = new ImageIcon("resources/quest.png");
	Icon smile = new ImageIcon("resources/smile.png");
	Icon[] numberIcons = new ImageIcon[9];
	
	private MikeSweeper model;
	private JDialog dialog;
	private JFrame frmMikesweeper;
	private JMenuItem mntmNewGame;
	private JButton[][] buttons;
	private JLabel clock;
	private int timeElapsed;
	private boolean counting;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MikeSweeperGui window = new MikeSweeperGui();
					window.frmMikesweeper.setVisible(true);
					window.dialog.setVisible(false);
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
	
	public void updateView()
	{
	    int[][] board = model.getBoard();
	    for(int i = 0; i < model.getSize(); i++) {
	        for(int j = 0; j < model.getSize(); j++) {
	            if(!model.getCovered(i, j)) {
	                int val = board[i][j];
    	            if(val == 10) {
    	                buttons[i][j].setIcon(iconEx);
    	            } else if(val == 0){
                        buttons[i][j].setIcon(numberIcons[0]);
                    } else {
    	                buttons[i][j].setIcon(numberIcons[val]);
    	            }
	            }
	            else {
	            	buttons[i][j].setIcon(icon);
	            }
	        }
	    }
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Makes board, and sets buttons to things and stuff.
		model = new MikeSweeper(Difficulty.MEDIUM);
		counting = false;
		frmMikesweeper = new JFrame();
		createButtonsArray();
		createIconsArray();

		frmMikesweeper.setTitle("MikeSweeper");
		ImageIcon img = new ImageIcon("resources/10x10.png");
		frmMikesweeper.setIconImage(img.getImage());
		frmMikesweeper.setBounds(100, 100, model.getSize() * 39 + 8, model.getSize() * 39 + 8);
		frmMikesweeper.setResizable(false);
		frmMikesweeper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMikesweeper.getContentPane().setLayout(new GridLayout(model.getSize(), model.getSize(), 0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		frmMikesweeper.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewGame = new JMenuItem("New Game");
		mntmNewGame.setName("newGame");
		mntmNewGame.addActionListener(this);
		mnFile.add(mntmNewGame);
		
		
		JMenuItem mntmReset = new JMenuItem("Reset");
		mntmReset.setName("reset");
		mntmReset.addActionListener(this);
		mnFile.add(mntmReset);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mnFile.add(mntmQuit);
		
		clock = new JLabel(String.format("%120d",  0));
		menuBar.add(clock);
				
		System.out.println(model);
	}

	private void createButtonsArray()
    {
	    buttons = new JButton[model.getSize()][model.getSize()];
	    for (int i = 0; i < model.getSize(); i++) {
            for (int j = 0; j < model.getSize(); j++) {
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
        Object o = e.getSource();
    	//findButton(e.getSource());
    	if (o instanceof JMenuItem)
    	{
    		if (((JMenuItem) o).getName() == "reset")
    		{
    		    reset();
    		}
    	}
    	else if (o instanceof JButton)
    	{
    		if(!counting) 
    		{
    			counting = true;
    			count();
    		}
    		int[] clicked = getButtonClicked((JButton)o);
    		model.clicked(clicked[0], clicked[1]);
    	}
    	else if (o instanceof Timer) 
    	{
    		if (model.getGameOver())
    		{
    			clock.setText(String.format("%120d %s", timeElapsed, "Game Over"));
    		}
    		else{
    			timeElapsed++;
    			clock.setText(String.format("%120d", timeElapsed));
    		}    		
    	}
        updateView();
	}
	
	

	private void reset()
	{
		Difficulty difficulty = model.getDifficulty();
		model = new MikeSweeper(difficulty);
		System.out.println(model);
		timeElapsed = 0;
		updateView();
	}
	
	private void newGame()
	{
		dialog.setVisible(true);
	}

	// For later
	public void findButton(Object source) {
		if (((AbstractButton) source).getIcon().equals(icon)) {
			((AbstractButton) source).setIcon(iconEx);
		} else if (((AbstractButton) source).getIcon().equals(quest)) {
			((JButton) source).setIcon(smile);
		}

	}
	
	public int[] getButtonClicked(JButton source)
	{
		for (int i = 0; i < buttons.length; i++)
		{
			for (int j = 0; j < buttons[i].length; j++)
			{
				if (source == buttons[i][j])
				{
					return new int[] {i,j};
				}
			}
		}
		return null;
	}
	
	private void count()
	{
		Timer timer = new Timer(1000, this);
		timer.start();
	}

}
