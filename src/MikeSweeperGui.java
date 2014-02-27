import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;


public class MikeSweeperGui {

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
        frmMikesweeper.setBounds(100, 100, 497, 389);
        frmMikesweeper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMikesweeper.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
    }

}
