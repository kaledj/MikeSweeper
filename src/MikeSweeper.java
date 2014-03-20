import java.util.Random;

public class MikeSweeper
{
    private int size;
    private int[][] board;
    private int maxMines; 
    private int numTouching;
    private boolean[][] coveredBoard;
    private boolean gameOver;
    private Difficulty difficulty;
    
    public MikeSweeper(Difficulty diff)
    {
        difficultySelect(diff);
        difficulty = diff;
        setNumTouching(0);
        makeBoard(getSize());
        gameOver = false;
    }
    /**
     * sets the difficulty.
     * @param diff
     */
    public void setDifficulty(Difficulty diff)
    {
        this.difficulty = diff;
    }
    /**
     * returns the difficulty.
     * @return
     */
    public Difficulty getDifficulty()
    {
        return difficulty;
    }
    /**
     * selects the difficulty and sets the size and mines.
     * @param diff
     */
    public void difficultySelect(Difficulty diff)
    {
        switch (diff) {
            case EASY:
                setSize(9);
                setMines(10);
                break;
                    
            case MEDIUM:
                setSize(16);
                setMines(40);
                break;
                         
            case HARD:
                setSize(20);
                setMines(99);
                break;
                        
            case CUSTOM:
                try
                {
                    makeCustom(size, maxMines);
                }
                catch (IllegalArgumentException e)
                {
                    System.out.println("Number of mines must be between 10 to " + ((size * size) - 1));
                    difficultySelect(diff);
                }
                break;
                
            default:
                setSize(16);
                setMines(40);
                break;
        }
    }
    /**
     * makes a custom board.
     */
    public void makeCustom(int size, int mines) throws IllegalArgumentException
    {
        setSize(size);
        if (mines > 10)
        {
            if (mines >= size * size)
            {
                throw new IllegalArgumentException("Too many mines.");
            }
            setMines(mines);
        }
        else
        {
            throw new IllegalArgumentException("Too few mines.");
        }
    }
    /**
     * creates the board.
     * 
     */
    public void makeBoard(int size)
    {
        board = new int[size][size];
        setNumTouching(0);
        fillMines(size);
        fillTouchMines(size);
        coveredBoard = new boolean[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                coveredBoard[i][j] = true;
            }
        }
    }
    public int[][] getBoard()
    {
    	return board;
    }
    /**
     * sets size.
     */
    public void setSize(int size)
    {
        this.size = size;
    }
    /**
     * returns size.
     */
    public int getSize()
    {
        return size;
    }
    /**
     * sets max mines.
     */
    public void setMines(int mines)
    {
        this.maxMines = mines;
    }
    /**
     * returns max mines.
     */
    public int getMines()
    {
        return this.maxMines;
    }
    /**
     * sets numTouching.
     */
    public void setNumTouching(int num)
    {
        numTouching = num;
    }
    /**
     * returns the numTouching.
     */
    public int getNumTouching()
    {
        return numTouching;
    }
    /**
     * returns the boolean in covered.
     */
    public boolean getCovered(int i, int j)
    {
        return coveredBoard[i][j];
    }
    /**
     * randomly fills in the grid with mines.
     * 
     */
    public void fillMines(int size)
    {
        Random random = new Random();
        int ranX;
        int ranY;
        int numMines = 0;
        while (numMines < maxMines)
        {
            ranX = random.nextInt(size);
            ranY = random.nextInt(size);
            if (board[ranX][ranY] != 10)
            {
                board[ranX][ranY] = 10;
                numMines++;
            }
        }
    }
    /**
     * fills in the cells that are next to a mine(s)
     * with the number of mine(s) it touches.
     */
    public void fillTouchMines(int size)
    {
        setNumTouching(0);
        fillCorners(size);
        setNumTouching(0);
        fillSides(size);
        setNumTouching(0);
        fillRest(size);
    }
    /**
     * fills in the corners.
     */
    public void fillCorners(int size)
    {
        setNumTouching(0);
        if (board[0][0] != 10) //top-left corner
        {
            if (board[0][1] == 10)
                setNumTouching(getNumTouching() + 1);
            if (board[1][0] == 10)
                setNumTouching(getNumTouching() + 1);
            if (board[1][1] == 10)
                setNumTouching(getNumTouching() + 1);
            board[0][0] = getNumTouching();
        }
        setNumTouching(0);
        if (board[0][size - 1] != 10) //top-right corner
        {
            if (board[0][size - 2] == 10)
                setNumTouching(getNumTouching() + 1);
            if (board[1][size - 2] == 10)
                setNumTouching(getNumTouching() + 1);
            if (board[1][size - 1] == 10)
                setNumTouching(getNumTouching() + 1);
            board[0][size - 1] = getNumTouching();
        }
        setNumTouching(0);
        if (board[size - 1][0] != 10) //bottom-left corner
        {
            if (board[size - 2][0] == 10)
                setNumTouching(getNumTouching() + 1);
            if (board[size - 2][1] == 10)
                setNumTouching(getNumTouching() + 1);
            if (board[size - 1][1] == 10)
                setNumTouching(getNumTouching() + 1);
            board[size - 1][0] = getNumTouching();
        }
        setNumTouching(0);
        if (board[size - 1][size - 1] != 10) //bottom-right corner
        {
            if (board[size - 2][size - 1] == 10)
                setNumTouching(getNumTouching() + 1);
            if (board[size - 2][size - 2] == 10)
                setNumTouching(getNumTouching() + 1);
            if (board[size - 1][size - 2] == 10)
                setNumTouching(getNumTouching() + 1);
            board[size - 1][size - 1] = getNumTouching();
        }
        setNumTouching(0);
    }
    /**
     * fills in each side.
     * 
     */
    public void fillSides(int size)
    {
        setNumTouching(0);
        for (int j = 1; j < size - 1; j++) //top row
        {
            if (board[0][j] != 10)
            {
                if (board[0][j - 1] == 10)
                    setNumTouching(getNumTouching() + 1);
                if (board[0][j + 1] == 10)
                    setNumTouching(getNumTouching() + 1);
                for (int k = j - 1; k <= j + 1; k++)
                {
                    if (board[1][k] == 10)
                    {
                        setNumTouching(getNumTouching() + 1);
                    }
                }
                board[0][j] = getNumTouching();
            }
            setNumTouching(0);
        }
        setNumTouching(0);
        for (int j = 1; j < size - 1; j++) //bottom row
        {
            if (board[size - 1][j] != 10)
            {
                if (board[size - 1][j - 1] == 10)
                    setNumTouching(getNumTouching() + 1);
                if (board[size - 1][j + 1] == 10)
                    setNumTouching(getNumTouching() + 1);
                for (int k = j - 1; k <= j + 1; k++)
                {
                    if (board[size - 2][k] == 10)
                    {
                        setNumTouching(getNumTouching() + 1);
                    }
                }
                board[size - 1][j] = getNumTouching();
            }
            setNumTouching(0);
        }
        setNumTouching(0);
        for (int i = 1; i < size - 1; i++) //left side
        {
            if (board[i][0] != 10)
            {
                if (board[i - 1][0] == 10)
                    setNumTouching(getNumTouching() + 1);
                if (board[i + 1][0] == 10)
                    setNumTouching(getNumTouching() + 1);
                for (int k = i - 1; k <= i + 1; k++)
                {
                    if (board[k][1] == 10)
                    {
                        setNumTouching(getNumTouching() + 1);
                    }
                }
                board[i][0] = getNumTouching();
            }
            setNumTouching(0);
        }
        setNumTouching(0);
        for (int i = 1; i < size - 1; i++) //right side
        {
            if (board[i][size - 1] != 10)
            {
                if (board[i - 1][size - 1] == 10)
                    setNumTouching(getNumTouching() + 1);
                if (board[i + 1][size - 1] == 10)
                    setNumTouching(getNumTouching() + 1);
                for (int k = i - 1; k <= i + 1; k++)
                {
                    if (board[k][size - 2] == 10)
                    {
                        setNumTouching(getNumTouching() + 1);
                    }
                }
                board[i][size - 1] = getNumTouching();
            }
            setNumTouching(0);
        }
        setNumTouching(0);
    }
    /**
     * fills the rest of the board.
     * 
     */
    public void fillRest(int size)
    {
        setNumTouching(0);
        for (int i = 1; i <= size - 2; i++)
        {
            for (int j = 1; j <= size - 2; j++)
            {
                setNumTouching(0);
                if (board[i][j] != 10)
                {
                    
                    checkAbove(i, j);
                    checkBelow(i, j);
                    checkSides(i, j);
                    board[i][j] = getNumTouching();
                }
                setNumTouching(0);
            }
            setNumTouching(0);
        }
        setNumTouching(0);
    }
    /**
     * checks the three spots above.
     * 
     */
    public void checkAbove(int i, int j)
    {
        for (int k = j - 1; k <= j + 1; k++)
        {
            if (board[i - 1][k] == 10)
                setNumTouching(getNumTouching() + 1);
        }
    }
    /**
     * checks the three spots below.
     * 
     */
    public void checkBelow(int i, int j)
    {
        for (int k = j - 1; k <= j + 1; k++)
        {
            if (board[i + 1][k] == 10)
                setNumTouching(getNumTouching() + 1);
        }
    }
    /**
     * checks left and right
     * 
     */
    public void checkSides(int i, int j)
    {
        if (board[i][j - 1] == 10)
            setNumTouching(getNumTouching() + 1);
        if (board[i][j + 1] == 10)
            setNumTouching(getNumTouching() + 1);
    }
    /**
     * uncovers the button clicked.
     * false means uncovered. true means covered
     */
    public void uncover(int i, int j)
    {
        coveredBoard[i][j] = false;
    }
    /**
     * returns all adjacent 0s of the one clicked.
     */
    public void revealZeros(int i, int j)
    {
        uncover(i, j);
        // Reveal all hidden, adjacent zeros and their adjacent squares
        for(int ii = i-1; ii <= i+1; ii++) {
            for(int jj = j-1; jj <= j+1; jj++) {
                if(ii >= 0 && ii < size) {
                    if(jj >= 0 && jj < size) {
                        if(coveredBoard[ii][jj]) {
                            if(board[ii][jj] == 0) {
                                revealZeros(ii, jj);
                            } 
                            else if(board[ii][jj] != 10) {
                                uncover(ii, jj);
                            }
                        }
                    }
                }
            }
        }    
    }
    
    /**
     * helper method for revealZero, for corner buttons.
     */
    public void cornerRevealZeros(int i, int j)
    {
        if (i == 0 && j == 0) //top-left corner
        {
            uncover(i, j + 1);
            uncover(i + 1, j);
            uncover(i + 1, j + 1);
            if (board[i][j + 1] == 0)
            {
                revealZeros(i, j + 1);
            }
            else if (board[i + 1][j] == 0)
            {
                revealZeros(i + 1, j);
            }
            else if (board[i + 1][j + 1] == 0)
            {
                revealZeros(i + 1, j + 1);
            }
            return;
        }
        if (i == 0 && j == size - 1) //top-right corner
        {
            uncover(i, j - 1);
            uncover(i + 1, j - 1);
            uncover(i + 1, j);
            if (board[i][j - 1] == 0)
            {
                revealZeros(i, j -1);
            }
            else if (board[i + 1][j - 1] == 0)
            {
                revealZeros(i + 1, j - 1);
            }
            else if (board[i + 1][j] == 0)
            {
                revealZeros(i + 1, j);
            }
            return;
        }
        if (i == size - 1 && j == 0) //bottom-left corner
        {
            uncover(i - 1, j);
            uncover(i - 1, j + 1);
            uncover(i, j + 1);
            if (board[i - 1][j] == 0)
            {
                revealZeros(i - 1, j);
            }
            else if (board[i - 1][j + 1] == 0)
            {
                revealZeros(i - 1, j + 1);
            }
            else if (board[i][j + 1] == 0)
            {
                revealZeros(i, j + 1);
            }
            return;
        }
        if (i == size - 1 && j == size - 1) //bottom-right corner
        {
            uncover(i - 1, j - 1);
            uncover(i - 1, j);
            uncover(i, j - 1);
            if (board[i - 1][j - 1] == 0)
            {
                revealZeros(i - 1, j -1);
            }
            else if (board[i - 1][j] == 0)
            {
                revealZeros(i - 1, j);
            }
            else if (board[i][j - 1] == 0)
            {
                revealZeros(i, j -1);
            }
            return;
        }
        return;
    }
    /**
     * helper method for revealZero, for side buttons.
     */
    public void sideRevealZeros(int i, int j)
    {
        if (i == 0 && j >= 1 && j <= size - 2) //top row
        {
            uncover(i, j - 1);
            uncover(i, j + 1);
            uncover(i + 1, j - 1);
            uncover(i + 1, j);
            uncover(i + 1, j + 1);
            if (board[i][j - 1] == 0)
            {
                revealZeros(i, j -1);
            }
            else if (board[i][j + 1] == 0)
            {
                revealZeros(i, j + 1);
            }
            else if (board[i + 1][j - 1] == 0)
            {
                revealZeros(i + 1, j - 1);
            }
            else if (board[i + 1][j] == 0)
            {
                revealZeros(i + 1, j);
            }
            else if (board[i + 1][j + 1] == 0)
            {
                revealZeros(i + 1, j + 1);
            }
            return;
        }
        if (i == size - 1 && j >= 1 && j <= size - 2) //bottom row
        {
            uncover(i - 1, j - 1);
            uncover(i - 1, j);
            uncover(i - 1, j + 1);
            uncover(i, j - 1);
            uncover(i, j + 1);
            if (board[i - 1][j - 1] == 0)
            {
                revealZeros(i - 1, j -1);
            }
            else if (board[i - 1][j] == 0)
            {
                revealZeros(i - 1, j);
            }
            else if (board[i - 1][j + 1] == 0)
            {
                revealZeros(i - 1, j + 1);
            }
            else if (board[i][j - 1] == 0)
            {
                revealZeros(i, j -1);
            }
            else if (board[i][j + 1] == 0)
            {
                revealZeros(i, j + 1);
            }
            return;
        }
        if (i >= 1 && i <= size - 2 && j == 0) //left side
        {
            uncover(i - 1, j);
            uncover(i - 1, j + 1);
            uncover(i, j + 1);
            uncover(i + 1, j);
            uncover(i + 1, j + 1);
            if (board[i - 1][j] == 0)
            {
                revealZeros(i - 1, j);
            }
            else if (board[i - 1][j + 1] == 0)
            {
                revealZeros(i - 1, j + 1);
            }
            else if (board[i][j + 1] == 0)
            {
                revealZeros(i, j + 1);
            }
            else if (board[i + 1][j] == 0)
            {
                revealZeros(i + 1, j);
            }
            else if (board[i + 1][j + 1] == 0)
            {
                revealZeros(i + 1, j + 1);
            }
            return;
        }
        if (i >= 1 && i <= size - 2 && j == size - 1) //right side
        {
            uncover(i - 1, j - 1);
            uncover(i - 1, j);
            uncover(i, j - 1);
            uncover(i + 1, j - 1);
            uncover(i + 1, j);
            if (board[i - 1][j - 1] == 0)
            {
                revealZeros(i - 1, j -1);
            }
            else if (board[i - 1][j] == 0)
            {
                revealZeros(i - 1, j);
            }
            else if (board[i][j - 1] == 0)
            {
                revealZeros(i, j -1);
            }
            else if (board[i + 1][j - 1] == 0)
            {
                revealZeros(i + 1, j - 1);
            }
            else if (board[i + 1][j] == 0)
            {
                revealZeros(i + 1, j);
            }
            return;
        }
        return;
    }
    
    /**
     * helper method for revealZero, for center buttons.
     */
    public void restRevealZero(int i, int j)
    {
        if (board[i][j] == 0)
        {
            uncover(i - 1, j - 1);
            uncover(i, j - 1);
            uncover(i + 1, j - 1);
            uncover(i - 1, j);
            uncover(i + 1, j);
            uncover(i - 1, j + 1);
            uncover(i, j + 1);
            uncover(i + 1, j + 1);
            if (board[i - 1][j - 1] == 0)
            {
                revealZeros(i - 1, j -1);
            }
            else if (board[i][j - 1] == 0)
            {
                revealZeros(i, j -1);
            }
            else if (board[i + 1][j - 1] == 0)
            {
                revealZeros(i + 1, j - 1);
            }
            else if (board[i - 1][j] == 0)
            {
                revealZeros(i - 1, j);
            }
            else if (board[i + 1][j] == 0)
            {
                revealZeros(i + 1, j);
            }
            else if (board[i - 1][j + 1] == 0)
            {
                revealZeros(i - 1, j + 1);
            }
            else if (board[i][j + 1] == 0)
            {
                revealZeros(i, j + 1);
            }
            else if (board[i + 1][j + 1] == 0)
            {
                revealZeros(i + 1, j + 1);
            }
            return;
        }
        return;
    }
    /**
     * prints the board un-hidden.
     */
    public String toString()
    {
        String str = "";
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                String temp = board[i][j] + "";
                
                str += String.format("%3s", temp);
            }
            str += "\n";
        }
        return str;
    }
    
    public void clicked(int i, int j)
    {
        if(gameOver) return;
        int val = board[i][j];
        if(val == 0) {
            revealZeros(i, j);
        } else if(val == 10) {
            revealMines();
            gameOver = true;
        } else {
            uncover(i, j);
        }
    }
    
    private void revealMines()
    {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(board[i][j] == 10) {
                    uncover(i, j);
                }
            }
        }
            
    }
    
    public boolean getGameOver()
    {
    	return gameOver;
    }

    public static void main(String[] args)
    {
        MikeSweeper test = new MikeSweeper(Difficulty.EASY);
        System.out.println(test);
    }
}
