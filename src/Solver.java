import java.awt.EventQueue;


public class Solver {

	private MikeSweeperGui game;
	
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

}
