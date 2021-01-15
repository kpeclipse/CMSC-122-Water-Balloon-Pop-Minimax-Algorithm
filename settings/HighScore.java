package settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HighScore {
    private int number;
    private String line;

	public HighScore(){
        try {
			File file = new File(System.getProperty("user.dir") + "/resources/High Score.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            while((line = bufferedReader.readLine()) != null) {
				number = Integer.valueOf(line);
            }
            
		} catch(IOException ioException) { ioException.printStackTrace(); }
    }
    
    public int showHighScore(){
        return number;
    }

    public void updateHighScore(int newHighScore){
        try {
			File file = new File(System.getProperty("user.dir") + "/resources/High Score.txt");
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			bufferedWriter.write(Integer.toString(newHighScore));
			
			bufferedWriter.close();
			fileWriter.close();
		}catch(IOException ioException) { ioException.printStackTrace();}
    }
}