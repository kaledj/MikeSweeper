import java.util.Random;

public class MikeSweeper
{
    private int size;
    private int[][] board;
    private final int MAX_MINES = 20; //amount may change
    private int numTouching;
    
    public MikeSweeper(int size)
    {
        makeBoard(size);
    }

    /**
     * creates the board.
     * 
     */
    public void makeBoard(int size)
    {
        board = new int[size][size];
        fillMines(size);
        fillTouchMines(size);
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
     * randomly fills in the grid with mines.
     * 
     */
    public void fillMines(int size)
    {
        Random random = new Random();
        int ranX;
        int ranY;
        int numMines = 0;
        while (numMines <= MAX_MINES)
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
        fillCorners(size);
        fillSides(size);
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
                numTouching++;
            if (board[1][0] == 10)
                numTouching++;
            if (board[1][1] == 10)
                numTouching++;
            board[0][0] = numTouching;
            setNumTouching(0);
        }
        if (board[0][size - 1] != 10) //top-right corner
        {
            if (board[0][size - 2] == 10)
                numTouching++;
            if (board[1][size - 2] == 10)
                numTouching++;
            if (board[1][size - 1] == 10)
                numTouching++;
            board[0][size - 1] = numTouching;
            setNumTouching(0);
        }
        if (board[size - 1][0] != 10) //bottom-left corner
        {
            if (board[size - 2][0] == 10)
                numTouching++;
            if (board[size - 2][1] == 10)
                numTouching++;
            if (board[size - 1][1] == 10)
                numTouching++;
            board[0][size - 1] = numTouching;
            setNumTouching(0);
        }
        if (board[size - 1][size - 1] != 10) //bottom-right corner
        {
            if (board[size - 2][size - 1] == 10)
                numTouching++;
            if (board[size - 2][size - 2] == 10)
                numTouching++;
            if (board[size - 1][size - 2] == 10)
                numTouching++;
            board[0][size - 1] = numTouching;
            setNumTouching(0);
        }
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
                    numTouching++;
                if (board[0][j + 1] == 10)
                    numTouching++;
                for (int k = j - 1; k <= j + 1; k++)
                {
                    if (board[1][k] == 10)
                    {
                        numTouching++;
                    }
                }
            }
        }
        setNumTouching(0);
        for (int j = 1; j < size - 1; j++) //bottom row
        {
            if (board[size - 1][j] != 10)
            {
                if (board[size - 1][j - 1] == 10)
                    numTouching++;
                if (board[size - 1][j + 1] == 10)
                    numTouching++;
                for (int k = j - 1; k <= j + 1; k++)
                {
                    if (board[size - 2][k] == 10)
                    {
                        numTouching++;
                    }
                }
            }
        }
        setNumTouching(0);
        for (int i = 1; i < size - 1; i++) //left side
        {
            if (board[i][0] != 10)
            {
                if (board[i - 1][0] == 10)
                    numTouching++;
                if (board[i + 1][0] == 10)
                    numTouching++;
                for (int k = i - 1; k <= i + 1; k++)
                {
                    if (board[k][1] == 10)
                    {
                        numTouching++;
                    }
                }
            }
        }
        setNumTouching(0);
        for (int i = 1; i < size - 1; i++) //right side
        {
            if (board[i][size - 1] != 10)
            {
                if (board[i - 1][size - 1] == 10)
                    numTouching++;
                if (board[i + 1][size - 1] == 10)
                    numTouching++;
                for (int k = i - 1; k <= i + 1; k++)
                {
                    if (board[k][size - 2] == 10)
                    {
                        numTouching++;
                    }
                }
            }
        }
    }
    /**
     * fills the rest of the board.
     * 
     */
    public void fillRest(int size)
    {
        for (int i = 1; i <= size - 2; i++)
        {
            for (int j = 1; j <= size - 2; j++)
            {
                if (board[i][j] != 10)
                {
                    setNumTouching(0);
                    checkAbove(i, j);
                    checkBelow(i, j);
                    checkSides(i, j);
                }
            }
        }
        
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
     * prints the board un-hidden.
     */
    public String toString()
    {
        String str = "";
        System.out.print("works");
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; i < size; j++)
            {
                str += board[i][j] + " ";
            }
            str += "\n";
        }
        return str;
    }
    
    public static void main(String[] args)
    {
        MikeSweeper test = new MikeSweeper(10);
        System.out.println(test);
    }
}
