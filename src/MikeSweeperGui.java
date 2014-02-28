import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;


public class MikeSweeperGui implements ActionListener
{
    Icon icon = new ImageIcon("resources/10x10.png");
    Icon iconEx = new ImageIcon("resources/10x10ex.png");

    private JFrame frmMikesweeper;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    MikeSweeperGui window = new MikeSweeperGui();
                    window.frmMikesweeper.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MikeSweeperGui()
    {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        frmMikesweeper = new JFrame();
        frmMikesweeper.setTitle("MikeSweeper");
        frmMikesweeper.setBounds(100, 100, 400, 450);
        frmMikesweeper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMikesweeper.getContentPane().setLayout(new GridLayout(10, 10, 0, 0));
        


        
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
            	JButton ij = new JButton(icon);
            	ij.addActionListener(this);
                frmMikesweeper.getContentPane().add(new JPanel().add(ij));
            }
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        	((AbstractButton) e.getSource()).setIcon(iconEx);
		
	}
	
}
