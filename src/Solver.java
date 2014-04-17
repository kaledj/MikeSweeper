import java.awt.EventQueue;
import java.util.ArrayList;

import Jama.Matrix;


public class Solver {

	MikeSweeper game;
	
	public Solver(MikeSweeper gameModel)
	{
		game = gameModel;
	}
	
	public void solve()
	{			
		for(int[] i : findKnownMines()) {
			game.setFlagged(i[0], i[1]);
		}	
	}
	
	public int[] bestMove()
	{
		return new int[] {0, 0};
	}
	
	
	public int[] linAlgSolve()
	{
		int size = game.getSize();
		int unCovered = 0;
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (!game.getCovered(i,  j))
                {
                    unCovered++;
                }
            }
        }
        double[][] bb = new double[unCovered + 1][1];
        int k = 0;
        double[][] AA = new double[unCovered + 1][size * size];
        double[] temp;

        int ii = 0;
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (!game.getCovered(i,  j))
                {
                    bb[k][0] = game.getBoard()[i][j];
                    k++;
                    temp = createRowInA(size, i, j);
                    //AA[ii] = createRowInA(size, i, j);
                    for (int jj = 0; jj < size * size; jj++)
                    {
                        AA[ii][jj] = temp[jj];
                    }
                    ii++;
                }
            }
        }
        
        // Append rows to A and b because of total probability
        bb[unCovered][0] = game.getNumMoves();
        for (int i = 0; i < size * size; i++)
        {
            AA[unCovered][i] = 1;
        }
        
        Matrix b = new Matrix(bb);
        
        System.out.println("b: ");
        for(Double d : b.getColumnPackedCopy()) System.out.println(d);
        
        Matrix A = new Matrix(AA);
        
        System.out.println("A: ");
        for(double[] dd : A.getArray()) {
        	for(Double d : dd) {
        		System.out.print(d + " ");
        	}
        	System.out.println();
        }
        
        Matrix x = A.solve(b);

        double lowest = Double.POSITIVE_INFINITY;
        int indexLow = -1;
        for (int i = 0; i < size * size; i++)
        {
            if (x.get(i, 0) < lowest)
            {
                lowest = x.get(i,  0);
                indexLow = i;
            }
        }
        
        return new int[] {indexLow / size, indexLow % size}; 
	}
	
	/**
     * createRowNA
     */
    public double[] createRowInA(int size, int i, int j)
    {
        double[] temp = new double[size * size];
        for (int ii = i - 1; ii <= i + 1; ii++)
        {
            for (int jj = j - 1; jj <= j + 1; jj++)
            {
                if (ii >= 0 && ii < size)
                {
                    if (jj >= 0 && jj < size)
                    {
                        if (game.getCovered(ii, jj))
                        {
                            temp[jj + (ii * size)] = 1;
                        }
                    }
                }
            }
        }
        return temp;
    }
    
    public ArrayList<int[]> findKnownMines()
    {
    	ArrayList<int[]> result = new ArrayList<int[]>();
    	
    	// Look for corners
    	for(int i = 0; i < game.getSize(); i++) {
    		for(int j = 0; j < game.getSize(); j++) {
    			if (game.getCovered(i, j)) {
    				if(inCorner(i, j)) {
    					result.add(new int[] {i, j});
    				}
    			}
    		}
    	}    	
    	return result;
    }
    
    public boolean inCorner(int i, int j)
    {
    	// Assumes {i, j} is unknown
    	// whack code because im lazy/stupid
    	
    	// Top row
    	if(i - 1 < 0) {
    		if(j - 1 < 0) {
    			if(!game.getCovered(i + 1, j + 1) 
    					&& game.getBoard()[i + 1][j + 1] == game.adjacentUnknowns(i + 1, j + 1)
    					&& !game.getCovered(i + 1, j)
    					&& !game.getCovered(i, j + 1)) return true;
    		} else if(j + 1 >= game.getSize()) {
    			if(!game.getCovered(i + 1, j - 1)
    					&& game.getBoard()[i + 1][j - 1] == game.adjacentUnknowns(i + 1, j - 1)
    					&& !game.getCovered(i + 1, j)
    					&& !game.getCovered(i, j - 1)) return true;
    		} else {
    			if(!game.getCovered(i + 1, j + 1) 
    					&& game.getBoard()[i + 1][j + 1] == game.adjacentUnknowns(i + 1, j + 1)
    					&& !game.getCovered(i + 1, j)
    					&& !game.getCovered(i, j + 1)) return true;
    			if(!game.getCovered(i + 1, j - 1)
    					&& game.getBoard()[i + 1][j - 1] == game.adjacentUnknowns(i + 1, j -1)
    					&& !game.getCovered(i + 1, j)
    					&& !game.getCovered(i, j - 1)) return true;
    		}
    	// Bottom Row	
    	} else if(i + 1 >= game.getSize()) {
    		if(j - 1 < 0) {
    			if(!game.getCovered(i - 1, j + 1)
    					&& game.getBoard()[i - 1][j + 1] == game.adjacentUnknowns(i - 1, j + 1)
    					&& !game.getCovered(i - 1, j)
    					&& !game.getCovered(i, j + 1)) return true;
    		} else if(j + 1 >= game.getSize()) {
    			if(!game.getCovered(i - 1, j - 1)
    					&& game.getBoard()[i - 1][j - 1] == game.adjacentUnknowns(i - 1, j -1)
    					&& !game.getCovered(i - 1, j)
    					&& !game.getCovered(i, j - 1)) return true;
    		} else {
    			if(!game.getCovered(i - 1, j + 1)
    					&& game.getBoard()[i - 1][j + 1] == game.adjacentUnknowns(i - 1, j + 1)
    					&& !game.getCovered(i - 1, j)
    					&& !game.getCovered(i, j + 1)) return true;
    			if(!game.getCovered(i - 1, j - 1)
    					&& game.getBoard()[i - 1][j - 1] == game.adjacentUnknowns(i - 1, j - 1)
    					&& !game.getCovered(i - 1, j)
    					&& !game.getCovered(i, j - 1)) return true;
    		}  
    	// Left Side
    	} else if(j - 1 < 0) {
    		if(i - 1 < 0) {
    			if(!game.getCovered(i + 1, j + 1)
    					&& game.getBoard()[i + 1][j + 1] == game.adjacentUnknowns(i + 1, j + 1)
    					&& !game.getCovered(i + 1, j)
    					&& !game.getCovered(i, j + 1)) return true;
    		} else if(i + 1 >= game.getSize()) {
    			if(!game.getCovered(i - 1, j + 1)
    					&& game.getBoard()[i - 1][j + 1] == game.adjacentUnknowns(i - 1, j + 1)
    					&& !game.getCovered(i - 1, j)
    					&& !game.getCovered(i, j + 1)) return true;
    		} else {
    			if(!game.getCovered(i + 1, j + 1)
    					&& game.getBoard()[i + 1][j + 1] == game.adjacentUnknowns(i + 1, j + 1)
    					&& !game.getCovered(i + 1, j)
    					&& !game.getCovered(i, j + 1)) return true;
    			if(!game.getCovered(i - 1, j + 1)
    					&& game.getBoard()[i - 1][j + 1] == game.adjacentUnknowns(i - 1, j + 1)
    					&& !game.getCovered(i - 1, j)
    					&& !game.getCovered(i, j + 1)) return true;
    		}
    	// Right Side
    	} else if(j + 1 >= game.getSize()) {
    		if(i - 1 < 0) {
    			if(!game.getCovered(i + 1, j - 1)
    					&& game.getBoard()[i + 1][j - 1] == game.adjacentUnknowns(i + 1, j - 1)
    					&& !game.getCovered(i, j - 1)
    					&& !game.getCovered(i + 1, j)) return true;
    		} else if(i + 1 >= game.getSize()) {
    			if(!game.getCovered(i - 1, j - 1)
    					&& game.getBoard()[i - 1][j - 1] == game.adjacentUnknowns(i - 1, j - 1)
    					&& !game.getCovered(i - 1, j)
    					&& !game.getCovered(i, j - 1)) return true;
    		} else {
    			if(!game.getCovered(i + 1, j - 1)
    					&& game.getBoard()[i + 1][j - 1] == game.adjacentUnknowns(i + 1, j - 1)
    					&& !game.getCovered(i, j - 1)
    					&& !game.getCovered(i + 1, j)) return true;
    			if(!game.getCovered(i - 1, j - 1)
    					&& game.getBoard()[i - 1][j - 1] == game.adjacentUnknowns(i - 1, j - 1)
    					&& !game.getCovered(i - 1, j)
    					&& !game.getCovered(i, j - 1)) return true;
    		}
    	// Middle
    	} else {
    		if(!game.getCovered(i - 1, j - 1)
    				&& game.getBoard()[i - 1][j - 1] == game.adjacentUnknowns(i - 1, j - 1)
					&& !game.getCovered(i - 1, j)
					&& !game.getCovered(i, j - 1)) return true;
    		if(!game.getCovered(i - 1, j + 1)
    				&& game.getBoard()[i - 1][j + 1] == game.adjacentUnknowns(i - 1, j + 1)
					&& !game.getCovered(i - 1, j)
					&& !game.getCovered(i, j + 1)) return true;
    		if(!game.getCovered(i + 1, j - 1)
    				&& game.getBoard()[i + 1][j - 1] == game.adjacentUnknowns(i + 1, j - 1)
					&& !game.getCovered(i , j - 1)
					&& !game.getCovered(i + 1, j)) return true;
    		if(!game.getCovered(i + 1, j + 1)
    				&& game.getBoard()[i + 1][j + 1] == game.adjacentUnknowns(i + 1, j + 1)
					&& !game.getCovered(i + 1, j)
					&& !game.getCovered(i, j + 1)) return true;
    	}
    	return false;
    }

}
