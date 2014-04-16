import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MikeSweeperScore {
	public MikeSweeperGui gui;
	
	public static void main(String[] arg)  {
		
	}
	
	public void highScore(int time, String name) throws IOException
	{
		FileWriter writer = new FileWriter("resources/scores.txt", true);
		writer.write("Name: " + name + " Score: " + time);
		writer.close();
	}
}
