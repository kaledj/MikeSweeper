import java.awt.EventQueue;


public class Solver {

	private MikeSweeperGui game;
		
	public Solver(MikeSweeperGui game)
	{
		this.game = game;
	}
	
	public void solve()
	{
		MikeSweeper ms = game.getModel();
		
		ms.clicked(1, 3);
		ms.clicked(0, 0);
		game.updateView();
		
		int[] bestMove = ms.testMatrix();
		ms.clicked(bestMove[0], bestMove[1]);
		game.updateView();
	}

}
