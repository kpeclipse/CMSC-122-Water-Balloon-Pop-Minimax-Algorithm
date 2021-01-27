package game;

import settings.HighScore;
import settings.SoundClip;
import settings.GameImagePanel;

import java.awt.image.BufferedImage;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Play extends JPanel implements KeyListener{
    private static final long serialVersionUID = -2657183267005854109L;
    private final int[] playerColumns = {50, 225, 400, 575};
    private final int[] balloonColumns = {160, 335, 510, 685}; 
    
    // So many global variables :<
    private File file;
    private GameImagePanel mainbg;

    private Game game;
    private Fall fall;
    private MiniMax miniMax;
    private VillainTravel move;
    private UmbrellaClose umbrella;
    private HighScore highScore;

    private JPanel playerPanel;
    private JPanel highScorePanel;
    private JPanel scorePanel;
    private JPanel dodgedBalloonsPanel;
    private JPanel gameOverPanel;
    private JPanel choicePanel;
    private JPanel tubePanel;

    private JLabel highScoreLabel; 
    private JLabel scoreLabel;
    private JLabel dodgedBalloonsLabel; 
    private JLabel gameOverLabel; 
    private JLabel exitLabel; 
    private JLabel againLabel;

    private JLabel villain;
    private JLabel rock;
    private JLabel[] player = new JLabel[4];
    private JLabel[] tube = new JLabel[4];
    private JLabel[] hole = new JLabel[4];
    private JLabel[] balloon = new JLabel[6];

    private int score;
    private int dodgedBalloons;
    private int DELTA; // Necessary when changing a particular column

    private boolean hold; // Necessary to avoid holding of key
    private boolean hitByBalloon;
    private boolean gameOver;

    private SoundClip sound = new SoundClip(System.getProperty("user.dir") + "/resources/Play BG Music.wav", 0);
    private SoundClip soundpop = new SoundClip(System.getProperty("user.dir") + "/resources/Balloon Pop.wav", 1);
    private SoundClip soundcry = new SoundClip(System.getProperty("user.dir") + "/resources/Game Over Cry.wav", 1);

    public Play(int width, int height, Game g){
        setSize(width, height);
        setLayout(null);
        setOpaque(false);

        game =  g;

        setVillain();
        setPanels();
        setTubes();
        setBalloons();
        setPlayers();
        setLabels();
        setBackground(System.getProperty("user.dir") + "/graphics/Play.png");
        
        addKeyListener(this);
    }

    // Once this panel is visible
    public void initial(){
        // Display score to 0
        updateScore(4); // any number can be used except for 0, 1, and 2 since these numbers determine the kind of balloon

        isGameOver(false); // declare the game is ongoing
        hitByBalloon = false;

        // player in certain position
        hold = false;
        DELTA = 400;
        visiblePlayer(0);

        if(game.music) // if music is on
            sound.start();

        Start();
    }

    // Starts the falling of Balloons
    public void Start() {
        Timer timer = new Timer("Timer");
        TimerTask task = new TimerTask() {
            int drop = 1;
            public void run() {
                if(!gameOver){
                    if(drop <= 10) {
                        // Villain is about to show up
                        switch(drop){
                            case 8:
                                villain.setBounds(925, 470, 250, 250);
                                break;
                            case 9:
                                villain.setBounds(720, 200, 250, 250);
                                break;
                            case 10:
                                villain.setBounds(playerColumns[2], 0, 250, 250);
                                break;
                        }
                        
                        BeforeBalloonFall(0);
                    }
        
                    else {                    
                        BeforeRockFall();
                            
                        Random r = new Random();
                        int randomColor = r.nextInt(3); // change in color of balloon
    
                        BeforeBalloonFall(randomColor);
                    }
    
                    drop += 1;
                }
            }
        };
    
            long delay = 100L;
            long period = 1000L;
            
            timer.scheduleAtFixedRate(task, delay, period);
	}

    // this sets whether the game is over or not
    public void isGameOver(boolean state){
        gameOver = state;

        if(hitByBalloon == true)
            visiblePlayer(2);
        else visiblePlayer(3);

        gameOverPanel.setVisible(state);
        choicePanel.setVisible(state);

        if(score > Integer.parseInt(highScoreLabel.getText()))
            highScore.updateHighScore(score);

        if(state == true){
            if(game.sfx) // If sfx is on
                soundcry.start();
            if(game.music) // if music is on
                sound.stop();
        }
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

    // Which balloon shows up
    public void visibleBalloon(int i){
        // No balloon shall be visible
        if(i >= balloon.length || i < 0){
            for(int x = 0; x < balloon.length; x++)
                balloon[x].setVisible(false);
        }

        else {
            for(int x = 0; x < balloon.length; x++) {
                if(x == i){
                    balloon[x].setVisible(true);
                }
                else balloon[x].setVisible(false);
            }
        }
    }

    // Updates score when balloon is popped
    public void updateScore(int color){
        switch(color){
            case 0: // RED
                score += 10;
                break;
            case 1: // SHINY
                score += 30;
                break;
            case 2: // BLACK
                score += 20;
                break;
        }

        scoreLabel.setText(Integer.toString(score));
    }

    // Deduct score when black balloon is dodged
    public void deductScore(){
        score -= 5;
        scoreLabel.setText(Integer.toString(score));
    }

    // Display current high score
    public void showHighScore(){
        highScore = new HighScore();
        highScoreLabel.setText(Integer.toString(highScore.showHighScore()));
    }
    
    // Updates how many balloons were dodged
    public void updateDodgedBalloons(){
        dodgedBalloonsLabel.setText(Integer.toString(dodgedBalloons));
        switch(dodgedBalloons){
            case 4:
                dodgedBalloonsLabel.setForeground(Color.ORANGE);
                break;
            case 5:
                dodgedBalloonsLabel.setForeground(Color.RED);
                break;
            default:
                dodgedBalloonsLabel.setForeground(Color.WHITE);
                break;
        }
    }
    
    // Wallpaper
    public void setBackground(String filename) {
        try {
            file = new File(filename);
            BufferedImage image = ImageIO.read(file);
            mainbg = new GameImagePanel(image);
            add(mainbg);
        } catch(IOException ioException) {
            System.err.println("IOException occured!");
            ioException.printStackTrace();
        }
    }

    // Everything with JPanels
    public void setPanels(){
        playerPanel = panel(playerPanel, 0, 0, 1200, 725, null);
        highScorePanel = panel(highScorePanel, 885, 70, 265, 75, new GridLayout(1,1));
        scorePanel = panel(scorePanel, 885, 200, 265, 75, new GridLayout(1,1));
        dodgedBalloonsPanel = panel(dodgedBalloonsPanel, 885, 350, 265, 75, new GridLayout(1,1));
        gameOverPanel = panel(gameOverPanel, 85, 265, 800, 75, new GridLayout(1,1));
        choicePanel = panel(choicePanel, 85, 375, 800, 50, new GridLayout(2,1));
        tubePanel = panel(tubePanel, 115, 0, 655, 75, null);

        add(gameOverPanel);
        add(choicePanel);
        add(tubePanel);
        add(playerPanel);
        add(highScorePanel);
        add(scorePanel);
        add(dodgedBalloonsPanel);

        playerPanel.add(rock); // Rock is only placed
    }
    
    // setting up a panel
    public JPanel panel(JPanel thePanel, int x, int y, int width, int height, GridLayout layout) {
        thePanel = new JPanel();
        thePanel.setBounds(x, y, width, height);
        thePanel.setLayout(layout);
        thePanel.setOpaque(false);
        return thePanel;
    }

    // Everything with JLabels
    public void setLabels(){
        highScoreLabel = label(highScoreLabel, game.useFont(System.getProperty("user.dir") + "/graphics/Emulogic.ttf", 40), Color.WHITE, null);
        scoreLabel = label(scoreLabel, game.useFont(System.getProperty("user.dir") + "/graphics/Emulogic.ttf", 40), Color.WHITE, Integer.toString(score));
        dodgedBalloonsLabel = label(dodgedBalloonsLabel, game.useFont(System.getProperty("user.dir") + "/graphics/Emulogic.ttf", 40), Color.WHITE, "0");
        gameOverLabel = label(gameOverLabel, game.useFont(System.getProperty("user.dir") + "/graphics/Emulogic.ttf", 60), Color.ORANGE, "GAME OVER!");
        exitLabel = label(exitLabel, game.useFont(System.getProperty("user.dir") + "/graphics/Emulogic.ttf", 20), Color.WHITE, "(Press ESC to exit)");
        againLabel = label(againLabel, game.useFont(System.getProperty("user.dir") + "/graphics/Emulogic.ttf", 20), Color.WHITE, "(Press ENTER to play again)");

        showHighScore();

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

    // Everything involved with the opponent
    public void setVillain(){
        villain = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/graphics/AI.png"));
        rock = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/graphics/rock.png"));

        add(villain);
    }

    // Everything with Player Settings
    public void setPlayers(){
        player[0] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/graphics/CLOSE.png"));
        player[1] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/graphics/OPEN.png"));
        player[2] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/graphics/WET.png"));
        player[3] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/graphics/CRY.png"));

        for(int i = 0; i < player.length; i++)
            playerPanel.add(player[i]);
    }

    // Everything with Tubes Settings
    public void setTubes(){
        for(int i = 0, x = 0; i < tube.length; i++){
            tube[i] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/graphics/Tube.png"));
            tube[i].setBounds(x, 0, 129, 75);

            hole[i] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/graphics/Hole.png"));
            hole[i].setBounds(x + 115, 45, 129, 75);
            
            tubePanel.add(tube[i]);
            add(hole[i]);
            x = x + 175;
        }
    }

    // Everything with Balloon Settings
    public void setBalloons(){
        balloon[0] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/graphics/RED BALLOON.png"));
        balloon[1] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/graphics/SHINY BALLOON.png"));
        balloon[2] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/graphics/BLACK BALLOON.png"));
        balloon[3] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/graphics/Red Pop.png"));
        balloon[4] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/graphics/Shiny Pop.png"));
        balloon[5] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "/graphics/Black Pop.png"));

        for(int i = 0; i < balloon.length; i++)
            playerPanel.add(balloon[i]);
    }

    // If opponent is about to fall a rock
    public void BeforeRockFall(){
        if(!gameOver)
            visibleBalloon(-1);
        
        try {
            miniMax = new MiniMax("opponent");

            move = new VillainTravel(miniMax.location);
            move.start();

            Thread.sleep(200);
        } catch (Exception e) {};
        
        try {
            miniMax.start();
            Thread.sleep(800);
        } catch (Exception e) {};
    }

    // Settings before a balloon fall happens
    public void BeforeBalloonFall(int i) {
		try {
            Random r = new Random();
            int index = r.nextInt(4);   // to determine which column the balloon will fall
            
            switch(i){
                case 0: // Red Balloon will fall 
                    fall = new Fall(0, index);
                    break;
                case 1: // Shiny Balloon will fall
                    fall = new Fall(1, index);
                    break;
                case 2: // Black Balloon will fall
                    fall = new Fall(2, index);
                    break;
            }
            
            fall.start();

            Thread.sleep(1300);
		} catch(Exception e){e.printStackTrace();}
	}

    // WHERE THE ACTION BEGINS!!
    @Override
	public void keyPressed(KeyEvent e) {
        int move = 175;

        // If player wishes to leave to Main Menu when the game is over
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && gameOver == true) {
            game.resetMain();
            game.resetPlay();
            game.showCard("main");
        }

        // If player wishes to play again when the game is over
        if (e.getKeyCode() == KeyEvent.VK_ENTER && gameOver == true) {
            game.resetPlay();
            game.showCard("play");
        }

        // If player wishes to move left while playing
        if (e.getKeyCode() == KeyEvent.VK_LEFT && gameOver == false && DELTA != 50) {
			while(hold == false) {
                DELTA -= move;
                visiblePlayer(0);
				hold = true;
			}
        }
        
        // If player wishes to move right while playing
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && gameOver == false && DELTA != 575) {
			while(hold == false) {
                DELTA += move;
                visiblePlayer(0);
				hold = true;
			}
        }
        
        // If player wishes to open umbrella
        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameOver == false) {
            while(hold == false) {
                visiblePlayer(1);
                umbrella = new UmbrellaClose();
                umbrella.start();
                hold = true;
            }
        }

        // If player wants to end the game
        if (e.getKeyCode() ==  KeyEvent.VK_END && gameOverPanel.isVisible() == false)
            isGameOver(true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
        // restarts hold
        if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT)
            hold = false;
    }

	@Override
    public void keyTyped(KeyEvent e) {}
    
    /**
     * INNER CLASSES
     */

    // Everything that happens in a falling balloon
    class Fall extends Thread {
        int columnIndex;
        int balloonIndex = 1;
        int time = 5;
        int freeFall = 0;

        boolean popped = false;
        boolean flag = true;

        public Fall (int balloonIndex, int columnIndex) {
            this.balloonIndex = balloonIndex;
            this.columnIndex = columnIndex;
        }

        @Override
        public void run() {
            while(gameOver == false && flag == true) {
                try {
                    visibleBalloon(balloonIndex);
                    balloon[balloonIndex].setBounds(balloonColumns[columnIndex], freeFall, 61, 90);
                    balloon[balloonIndex + 3].setBounds(balloonColumns[columnIndex], freeFall, 61, 90);
                    
                    // THE PHYSICS IN THE FALLING BALLOON
                    Thread.sleep(20);
                    freeFall += 1 * time;
                    if(time < 30){
                        time += 1;
                    }

                    // COLLISION
                    // If balloon was popped by the player or was dodged
                    if ((balloon[balloonIndex].getBounds().intersects(player[1].getBounds()) && player[1].isVisible()) || freeFall > 605) {
                        visibleBalloon(balloonIndex + 3);
                        
                        if(game.sfx) // If sfx is on
                            soundpop.start();
                        // If the balloon was popped
                        if(freeFall < 605){
                            updateScore(balloonIndex);
                        }

                        // If the balloon was dodged
                        else{
                            // If dodge limit is not yet reached
                            if(dodgedBalloons < 5) {
                                dodgedBalloons += 1;
                                updateDodgedBalloons();

                                if(balloonIndex == 2) // If it was a black balloon
                                    deductScore();
                            }

                            // If dodge limit has been reached
                            else
                                isGameOver(true);
                        }

                        flag = false;
                    }

                    // If the player was hit by a balloon
                    else if((balloon[balloonIndex].getBounds().intersects(player[0].getBounds()) && balloon[balloonIndex].getY() > 425 && player[0].isVisible())){
                        visibleBalloon(balloonIndex + 3);
                        hitByBalloon = true;
                        isGameOver(true);
                    }
                    
                } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    // Everything related to the falling rock
    class MiniMax extends Thread{
        int time = 5;
        int freeFall = 170;
        int location = getMin();

        String turn;
        boolean flag = true;

        public MiniMax(String turn) {
            this.turn = turn;
        }

        @Override
        public void run() {
            if(turn == "opponent") {
                while (gameOver == false && flag == true){
                    try {
                        villain.setBounds(playerColumns[location], 0, 250, 250);
                        rock.setBounds(balloonColumns[location], freeFall, 45, 45);
                        Thread.sleep(35);
                        // THE PHYSICS IN THE FALLING ROCK
                        freeFall += 2 * time;
                        if(time < 30){
                            time += 1;
                        }

                        // if rock hits player regardless the open umbrella or not
                        if((rock.getBounds().intersects(player[0].getBounds()) && rock.getY() > 465))
                            isGameOver(true);
    
                        if(freeFall > 750)
                            flag = false;
                    } catch (Exception e) { e.printStackTrace(); }
                }
                location = getMin();
            }
        }

        public int getMin(){
            int current = player[0].getX();
            for(int i = 0; i < 4; i++){
                if(playerColumns[i] == current)
                    return i; // This is the index of playerColumns, and this tube has the min value = 0;
            }
            return 0;
        }

        public int getMax(){
            int current = villain.getX();
            int exclude = 0;
            for(int i = 0; i < 4; i++){
                if(playerColumns[i] == current){
                    exclude = i; // This tube has the min value = 0
                    break;
                }
            }

            switch(exclude){
                case 0: // Tube 4 has max value of 3
                case 1: // Tube 4 has max value of 2
                    return playerColumns[3];
                case 2: // Tube 1 has max value of 2
                case 3: // Tube 1 has max value of 3
                    return playerColumns[0];
            }
            
            return 0;
        }
    }
    
    // This happens if the opponent is at a particular place and needs to move once it's its turn
    class VillainTravel extends Thread{
        int location;
        int current = villain.getX();
        
        public VillainTravel(int location){
            this.location = location;
        }

        @Override
        public void run(){
            while(gameOver == false && playerColumns[location] != current){
                try {
                    // must go to the right
                    if (playerColumns[location] > current)
                        current += 175;
                
                    // must go to the left        
                    else if (playerColumns[location] < current)
                        current -= 175;
                    
                    villain.setBounds(current, 0, 250, 250);
                    Thread.sleep(50);
                } catch (Exception e) { e.printStackTrace(); };
            }
        }
    }
    
    // This is to stop the umbrella being open while holding the SPACE KEY
    class UmbrellaClose extends Thread {
		private boolean running = false;

		@Override
		public void run() {
		    do {
			    try {
                    Thread.sleep(100);
                    visiblePlayer(0);

                    if(gameOver == true)
                        visiblePlayer(3);
                    running = true;
                } catch (Exception ex) { ex.printStackTrace(); }
            }while(running == false);
		}
    }
}