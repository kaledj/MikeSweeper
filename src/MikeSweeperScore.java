import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MikeSweeperScore
{
    public MikeSweeperGui gui;

    public static void main(String[] arg)
    {
        MikeSweeperScore mss = new MikeSweeperScore();
    }

    public void highScore(int time, String name, Difficulty diff)
            throws IOException
    {
        String files = "";
        if (diff == diff.EASY)
        {
            files = "easyScores";
            if (!isFull(files))
            {
                FileWriter writer = new FileWriter("resources/easyScores.txt",
                        true);
                writer.write(name + "\r\n");
                writer.write(time + "\r\n");
                writer.close();
            }
            else
            {
                isGreater(time, name, files);
            }
        }
        else if (diff == diff.MEDIUM)
        {
            files = "medScores";
            if (!isFull(files))
            {
                FileWriter writer = new FileWriter("resources/medScores.txt",
                        true);
                writer.write(name + "\r\n");
                writer.write(time + "\r\n");
                writer.close();
            }
            else
            {
                isGreater(time, name, files);
            }
        }
        else if (diff == diff.HARD)
        {
            files = "hardScores";
            if (!isFull(files))
            {
                FileWriter writer = new FileWriter("resources/hardScores.txt",
                        true);
                writer.write(name + "\r\n");
                writer.write(time + "\r\n");
                writer.close();
            }
            else
            {
                isGreater(time, name, files);
            }
        }
    }

    boolean isHighScore(int newScore, Difficulty diff)
    {
        String oldName = "";
        int oldScore = 0;
        String newData = "";
        String files = "";
        if (diff == diff.EASY)
        {
            files = "easyScores";
        }
        if (diff == diff.MEDIUM)
        {
            files = "medScores";
        }
        if (diff == diff.HARD)
        {
            files = "hardScores";
        }
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("resources/"
                    + files + ".txt"));
            while ((oldName = br.readLine()) != null)
            {
                oldScore = Integer.parseInt(br.readLine());
                if (newScore < oldScore)
                {
                    return true;
                }
            }
        }
        catch (IOException e)
        {
            System.err.println("Error: " + e);
        }
        return false;
    }

    private void isGreater(int newScore, String newName, String files)
    {
        String oldName = "";
        int oldScore = 0;
        String newData = "";
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("resources/"
                    + files + ".txt"));
            while ((oldName = br.readLine()) != null)
            {
                oldScore = Integer.parseInt(br.readLine());
                if (newScore < oldScore)
                {
                    newData += newName + "\r\n" + newScore + "\r\n";
                    newName = oldName;
                    newScore = oldScore;
                }
                else
                {
                    newData += oldName + "\r\n" + oldScore + "\r\n";
                }
            }
            FileWriter writer = new FileWriter("resources/" + files + ".txt",
                    false);
            writer.write(newData);
            writer.close();
            br.close();
        }
        catch (IOException e)
        {
            System.err.println("Error: " + e);
        }

    }

    public boolean isFull(String files)
    {
        int count = 0;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("resources/"
                    + files + ".txt"));
            while ((br.readLine()) != null)
            {
                count++;
            }
            if (count >= 6)
            {
                br.close();
                return true;
            }
            br.close();
        }
        catch (IOException e)
        {
            System.err.println("Error: " + e);
        }
        return false;
    }

    public String getHighScores(Difficulty diff)
    {
        String oldName = "";
        int oldScore = 0;
        String newData = "";
        String files = "";
        if (diff == diff.EASY)
        {
            files = "easyScores";
        }
        if (diff == diff.MEDIUM)
        {
            files = "medScores";
        }
        if (diff == diff.HARD)
        {
            files = "hardScores";
        }
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("resources/"
                    + files + ".txt"));
            while ((oldName = br.readLine()) != null)
            {
                oldScore = Integer.parseInt(br.readLine());
                newData += oldName + "\t" + oldScore + "\r\n";
            }
            return newData;
        }
        catch (IOException e)
        {
            System.err.println("Error: " + e);
        }
        return newData;
    }

}
