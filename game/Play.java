package game;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.FontFormatException;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Play extends JPanel implements KeyListener{
    private File file;
    private BufferedImage image;
    private GameImagePanel mainbg;
    private Game game;

    private JPanel playerPanel;
    private JPanel highScorePanel;
    private JPanel scorePanel;
    private JPanel dodgedBalloonsPanel;
    private JPanel gameOverPanel;
    private JPanel choicePanel;

    private JLabel highScoreLabel; 
    private JLabel scoreLabel;
    private JLabel dodgedBalloonsLabel; 
    private JLabel gameOverLabel; 
    private JLabel exitLabel; 
    private JLabel againLabel;

    private JLabel[] player = new JLabel[4];

    private int score;
    private int DELTA; // Necessary when changing a particular column

    private boolean hold; // Necessary to avoid holding of key
    private boolean gameOver;

    public Play(int width, int height, Game g){
        setSize(width, height);
        setLayout(null);
        setOpaque(false);

        setPanels();
        setPlayers();
        setLabels();

        setBackground(System.getProperty("user.dir") + "/graphics/Play.png");
        addKeyListener(this);

        game =  g;
    }

    // Game starts
    public void initial(){
        // score
        clearScore();
        updateScore();

        // if game is not over yet
        isGameOver(false);

        // player in certain position
        hold = false;
        DELTA = 400;
        visiblePlayer(0);
    }

    public void isGameOver(boolean state){
        gameOver = state;
        gameOverPanel.setVisible(state);
        choicePanel.setVisible(state);
    }

    // Which character shows up
    public void visiblePlayer(int i){
        for(int x = 0; x < player.length; x++) {
            if(x == i){
                player[x].setVisible(true);
            }
            else player[x].setVisible(false);
        }

        player[i].setBounds(DELTA, 435, 254, 254);
    }

    public void clearScore(){
        score = 0;
    }

    public void updateScore(){
        scoreLabel.setText(Integer.toString(score));
    }
    
    // Play Wallpaper
    public void setBackground(String filename) {
        try {
            file = new File(filename);
            image = ImageIO.read(file);
            mainbg = new GameImagePanel(image);
            add(mainbg);
        } catch(IOException ioException) {
            System.err.println("IOException occured!");
            ioException.printStackTrace();
        }
    }

    // Everything with JPanel
    public void setPanels(){
        playerPanel = panel(playerPanel, 0, 0, 1200, 725, null);
        highScorePanel = panel(highScorePanel, 885, 70, 265, 75, new GridLayout(1,1));
        scorePanel = panel(scorePanel, 885, 200, 265, 75, new GridLayout(1,1));
        dodgedBalloonsPanel = panel(dodgedBalloonsPanel, 885, 350, 265, 75, new GridLayout(1,1));
        gameOverPanel = panel(gameOverPanel, 85, 265, 800, 75, new GridLayout(1,1));
        choicePanel = panel(choicePanel, 85, 375, 800, 50, new GridLayout(2,1));

        add(playerPanel);
        add(highScorePanel);
        add(scorePanel);
        add(dodgedBalloonsPanel);
        add(gameOverPanel);
        add(choicePanel);
    }
    
    // setting up a panel
    public JPanel panel(JPanel thePanel, int x, int y, int width, int height, GridLayout layout) {
        thePanel = new JPanel();
        thePanel.setBounds(x, y, width, height);
        thePanel.setLayout(layout);
        thePanel.setOpaque(false);
        return thePanel;
    }

    // Everything with JLabel
    public void setLabels(){
        highScoreLabel = label(highScoreLabel, useFont(System.getProperty("user.dir") + "/graphics/Emulogic.ttf", 40), Color.WHITE, "BLABLA");
        scoreLabel = label(scoreLabel, useFont(System.getProperty("user.dir") + "/graphics/Emulogic.ttf", 40), Color.WHITE, Integer.toString(score));
        dodgedBalloonsLabel = label(dodgedBalloonsLabel, useFont(System.getProperty("user.dir") + "/graphics/Emulogic.ttf", 40), Color.WHITE, "0");
        gameOverLabel = label(gameOverLabel, useFont(System.getProperty("user.dir") + "/graphics/Emulogic.ttf", 60), Color.ORANGE, "GAME OVER!");
        exitLabel = label(exitLabel, useFont(System.getProperty("user.dir") + "/graphics/Emulogic.ttf", 20), Color.WHITE, "(Press ESC to exit)");
        againLabel = label(againLabel, useFont(System.getProperty("user.dir") + "/graphics/Emulogic.ttf", 20), Color.WHITE, "(Press ENTER to play again)");

        scorePanel.add(scoreLabel);
        highScorePanel.add(highScoreLabel);
        dodgedBalloonsPanel.add(dodgedBalloonsLabel);
        gameOverPanel.add(gameOverLabel);
        choicePanel.add(againLabel);
        choicePanel.add(exitLabel);
    }

    // Setting up a label
    public JLabel label(JLabel theLabel, Font font, Color color, String message) {
        theLabel = new JLabel(message);
        theLabel.setFont(font);
        theLabel.setForeground(color);
        theLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return theLabel;
    }

    // Everything with Player Settings
    public void setPlayers(){
        player[0] = playerLabel(player[0], System.getProperty("user.dir") + "/graphics/CLOSE.png");
        player[1] = playerLabel(player[1], System.getProperty("user.dir") + "/graphics/OPEN.png");
        player[2] = playerLabel(player[2], System.getProperty("user.dir") + "/graphics/WET.png");
        player[3] = playerLabel(player[3], System.getProperty("user.dir") + "/graphics/CRY.png");

        for(int i = 0; i < player.length; i++)
            playerPanel.add(player[i]);
    }

    // Setting up a player
    public JLabel playerLabel(JLabel theLabel, String filename) {
        theLabel = new JLabel(new ImageIcon(filename));
        return theLabel;
    }

    // What font to use
    public Font useFont(String path, int size){
        try {
			return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
		} catch (FontFormatException | IOException e) {e.printStackTrace();}
		return null;
    }


    // WHERE THE ACTION BEGINS!!
    @Override
	public void keyPressed(KeyEvent e) {
        int move = 175;

        // If player wishes to leave to Main Menu when the game is over
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && gameOver == true) {
            game.showCard("main");
        }

        // If player wishes to play again when the game is over
        if (e.getKeyCode() == KeyEvent.VK_ENTER && gameOver == true) {
            initial();
        }

        // If player wishes to move left while playing
        if (e.getKeyCode() == KeyEvent.VK_LEFT && gameOver == false) {
			while(hold == false) {
                DELTA -= move;

                // STAYS AT LEFTMOST
				if(DELTA < 0)
                    DELTA += move;

                visiblePlayer(0);
				hold = true;
			}
        }
        
        // If player wishes to move right while playing
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && gameOver == false) {
			while(hold == false) {
                DELTA += move;

                // STAYS AT RIGHTMOST
				if(DELTA > 700)
                    DELTA -= move;

                visiblePlayer(0);
				hold = true;
			}
        }
        
        // If player wishes to open umbrella
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameOver == false) {
            while(hold == false) {
                visiblePlayer(1);
				hold = true;
            }
        }

        // If player wants to end the game
        if (e.getKeyCode() ==  KeyEvent.VK_END) {
            isGameOver(true);
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT)
			hold = false;
    }

	@Override
	public void keyTyped(KeyEvent e) {}
}