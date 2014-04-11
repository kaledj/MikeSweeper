import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class MikeSweeperGui implements ActionListener {
	
    Icon icon = new ImageIcon("resources/10x10.png");
    Icon flag = new ImageIcon("resources/flag.png");
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
	private JMenuBar menuBar;
	private JMenuItem mntmQuit;
	private JMenuItem mntmReset;
	private JMenu mnFile;
	private Timer timer;
	
	private int timeElapsed;
	private int flags;
	private boolean counting;
	private Difficulty diff = Difficulty.EASY;
	private JLabel lblFlags;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MikeSweeperGui window = new MikeSweeperGui();					
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
		frmMikesweeper.setVisible(true);
		dialog.setVisible(false);
	}
	
	public MikeSweeper getModel()
	{
		return model;
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
	            else if(model.flagged[i][j]) 
	            {
	            	if (flags < model.getMines())
	            	{
	            		buttons[i][j].setIcon(flag);
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
		model = new MikeSweeper(diff,1,3);
		counting = false;
		flags = 0;
		
        //New game dialog box
		dialog = new JDialog(frmMikesweeper, "Play again?");
		dialog.setSize(new Dimension(75, 75));
		dialog.getContentPane().setLayout(new FlowLayout());
		JLabel playLabel = new JLabel("Choose difficulty:");
		dialog.getContentPane().add(playLabel);
		JButton easy = new JButton("Easy");
		easy.setName("easy");
		JButton med = new JButton("Medium");
		med.setName("medium");
		JButton hard = new JButton("Hard");
		hard.setName("hard");
		dialog.getContentPane().add(easy);
		easy.setBounds(0, 0, 20, 10);
		easy.addActionListener(this);
		dialog.getContentPane().add(med);
		med.addActionListener(this);
		dialog.getContentPane().add(hard);
		hard.addActionListener(this);
		dialog.validate();
		dialog.pack();
		
		frmMikesweeper = new JFrame();
		createButtonsArray();
		createIconsArray();

		frmMikesweeper.setTitle("MikeSweeper");
		ImageIcon img = new ImageIcon("resources/10x10.png");
		frmMikesweeper.setIconImage(img.getImage());
		if (diff == diff.EASY)
		{
			frmMikesweeper.setBounds(100, 100, model.getSize() * 40, model.getSize() * 50);
		}
		else if (diff == diff.MEDIUM)
		{
			frmMikesweeper.setBounds(100, 100, model.getSize() * 35, model.getSize() * 40);
		}
		else if (diff == diff.HARD)
		{
			frmMikesweeper.setBounds(100, 100, model.getSize() * 25, model.getSize() * 30);
		}
		frmMikesweeper.setResizable(false);
		frmMikesweeper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMikesweeper.getContentPane().setLayout(new GridLayout(model.getSize(), model.getSize(), 0, 0));
		
		menuBar = new JMenuBar();
		frmMikesweeper.setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmNewGame = new JMenuItem("New Game");
		mntmNewGame.setName("newGame");
		mntmNewGame.addActionListener(this);
		mnFile.add(mntmNewGame);
		
		
		mntmReset = new JMenuItem("Reset");
		mntmReset.setName("reset");
		mntmReset.addActionListener(this);
		mnFile.add(mntmReset);
		
		mntmQuit = new JMenuItem("Quit");
		mntmQuit.setName("quit");
		mntmQuit.addActionListener(this);
		mnFile.add(mntmQuit);
		
		clock = new JLabel(String.format("%10d",  0));
		menuBar.add(clock);
		
		lblFlags = new JLabel("Flags:");
		menuBar.add(lblFlags);

		frmMikesweeper.setVisible(true);
		dialog.setLocationRelativeTo(frmMikesweeper);	
		
		System.out.println(model);
		
		Solver solver = new Solver(this);
		solver.solve();
	}

	private void createButtonsArray()
    {
	    buttons = new JButton[model.getSize()][model.getSize()];
	    for (int i = 0; i < model.getSize(); i++) {
            for (int j = 0; j < model.getSize(); j++) {
                JButton ij = new JButton(icon);
                ij.setOpaque(false);
                ij.setContentAreaFilled(false);
                ij.setBorderPainted(true);
                ij.addActionListener(this);
                ij.addMouseListener(new RightClickListener(ij));
                frmMikesweeper.getContentPane().add(new JPanel().add(ij));
                buttons[i][j] = ij;
            }
        }
    }

    private void createIconsArray()
    {
        for(int i = 0; i < numberIcons.length; i++) {
            numberIcons[i] = new ImageIcon("resources/" + i + ".png");
            if (diff == diff.HARD)
            {
            	Image scaleImage = ((ImageIcon) numberIcons[i]).getImage().getScaledInstance(26, 26,Image.SCALE_SMOOTH);
            	numberIcons[i] = new ImageIcon(scaleImage);
            	
            	Image scaleImageTwo = ((ImageIcon) icon).getImage().getScaledInstance(26, 26,Image.SCALE_SMOOTH);
            	icon = new ImageIcon(scaleImageTwo);
            }
            
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
    		else if (((JMenuItem) o).getName() == "newGame")
    		{
    			newGame();
    		}
    		else if (((JMenuItem) o).getName() == "quit")
    		{
    			System.exit(0);
    		}
    	}
    	else if (o instanceof JButton)
    	{
    		if(!counting) 
    		{
    			counting = true;
    			count();
    		}
    		if (((JButton) o).getName() == "easy")
    		{
    			dialog.setVisible(false);
    			diff = Difficulty.EASY;
    			frmMikesweeper.setVisible(false);
    			model.setGameOver(false);
    			timeElapsed = 0;
    			counting = false;
    			timer.stop();
    			initialize();
    			updateView();
    		}
    		else if (((JButton) o).getName() == "medium")
    		{
    			dialog.setVisible(false);
    			diff = Difficulty.MEDIUM;
    			frmMikesweeper.setVisible(false);
    			model.setGameOver(false);
    			timeElapsed = 0;
    			counting = false;
    			timer.stop();
    			initialize();
    			updateView();
    		}
    		else if (((JButton) o).getName() == "hard")
    		{
    			dialog.setVisible(false);
    			diff = Difficulty.HARD;
    			frmMikesweeper.setVisible(false);
    			timeElapsed = 0;
    			counting = false;
    			timer.stop();
    			initialize();
    			updateView();
    		}
    		else
    		{
    			int[] clicked = getButtonClicked((JButton)o);
    			if(!model.flagged[clicked[0]][clicked[1]]) {
        			model.clicked(clicked[0], clicked[1]);
    			}
    		}
    	}
    	else if (o instanceof Timer) 
    	{
    		if (model.getGameOver())
    		{
    			clock.setText(String.format("%10d %s", timeElapsed, "Game Over!"));
    			dialog.setVisible(true);
    		}
    		else if (model.getGameWon())
    		{
    			clock.setText(String.format("%10d %s", timeElapsed, "You win!"));
    			dialog.setVisible(true);
    		}
    		else
    		{
    			timeElapsed++;
    			clock.setText(String.format("%10d", timeElapsed));
    		}    		
    	}
        updateView();
	}

	private void reset()
	{
		model.coverAll();
		model.unflagAll();
		model.setGameOver(false);
		model.setGameWon(false);
		timeElapsed = 0;
		flags = 0;
		counting = false;
		timer.stop();
		clock.setText(String.format("%10d", timeElapsed));
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
		if (counting)
		{
			timer = new Timer(1000, this);
			timer.start();
		}
	}
	
	private class RightClickListener extends MouseAdapter 
	{
		private JButton button;
		private boolean pressed;
		
		public RightClickListener(JButton button)
		{
			this.button = button;
		}
		
		
		@Override
        public void mousePressed(MouseEvent e) {
            if(!counting || model.getGameOver()) return;
			button.getModel().setArmed(true);
            button.getModel().setPressed(true);
            pressed = true;
        }
		
		@Override
        public void mouseReleased(MouseEvent e) {
            if(!counting || model.getGameOver()) return;
            button.getModel().setArmed(false);
            button.getModel().setPressed(false);
            if (pressed) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int[] clicked = getButtonClicked(button);
                    if(model.getCovered(clicked[0], clicked[1]) && !model.getGameWon()) 
                    {
                    	if(model.flagged[clicked[0]][clicked[1]]) 
                    	{
                    		button.setIcon(icon);
                    		flags--;
                    		model.flagged[clicked[0]][clicked[1]] = !model.flagged[clicked[0]][clicked[1]];
                    	}
                    	else if (flags < model.getMines())
                    	{
                    		button.setIcon(flag);
                    		flags++;
                    		model.flagged[clicked[0]][clicked[1]] = !model.flagged[clicked[0]][clicked[1]];
                    	}                    	
                    }                    
                }
                else {
                    button.setIcon(button.getIcon());
                }
            }
            pressed = false;
        }
	}
}

