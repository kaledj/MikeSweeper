import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MikeSweeperGui
{

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
        frmMikesweeper.setBounds(100, 100, 450, 300);
        frmMikesweeper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMikesweeper.getContentPane().setLayout(new GridLayout(10, 10, 0, 0));
        
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                frmMikesweeper.getContentPane().add(new JPanel().add(new JLabel("(" + i + ", " + j + ")")));
            }
        }
    }

}
