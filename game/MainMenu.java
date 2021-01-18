package game;

import settings.HighScore;
import settings.SoundClip;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class MainMenu extends JPanel {
    private File file;
    private BufferedImage image;

    private GameImagePanel mainbg;
    private Game game;
    private HighScore highScore;

    private JButton playButton;
    private JButton howToPlayButton;
    private JButton creditsButton;
    private JButton exitButton;
    private JButton musicButton;
    private JButton sfxButton;

    private ImageIcon[] musicIcons;

    private SoundClip soundmain = new SoundClip(System.getProperty("user.dir") + "/resources/Main BG Music.wav", 0);

    public MainMenu(int width, int height, Game g){
        setSize(width, height);
        setLayout(null);
        setOpaque(false);

        game =  g;
        
        showHighScore();
        setButtons();
        setBackground(System.getProperty("user.dir") + "/graphics/Main.png");

        setActionAndMouseListeners();
    }

    public void showHighScore(){
        highScore = new HighScore();
        int currentHighScore = highScore.showHighScore();

        JPanel highscorePanel = new JPanel();
        highscorePanel.setBounds(670, 335, 350, 50);
        highscorePanel.setOpaque(false);
        add(highscorePanel);

        JLabel highScoreLabel = new JLabel("High Score: " + Integer.toString(currentHighScore));
        highScoreLabel.setLocation(0, 0);
        highScoreLabel.setSize(220, 50);
        highScoreLabel.setFont(game.useFont(System.getProperty("user.dir") + "/graphics/Emulogic.ttf", 10));
        highScoreLabel.setForeground(Color.ORANGE);
        highscorePanel.add(highScoreLabel);
    }

    public JButton button(JButton theButton, ImageIcon icon) {
        theButton = new JButton();
		theButton.setIcon(icon);
		theButton.setContentAreaFilled(false);
        theButton.setFocusPainted(false);
    	theButton.setBorder(BorderFactory.createEmptyBorder());
		return theButton;
	}

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

    public void setButtons() {
        playButton = button(playButton, new ImageIcon(System.getProperty("user.dir") + "/graphics/play1.png"));
        howToPlayButton = button(howToPlayButton, new ImageIcon(System.getProperty("user.dir") + "/graphics/how1.png"));
        creditsButton = button(creditsButton, new ImageIcon(System.getProperty("user.dir") + "/graphics/credits1.png"));
        exitButton = button(exitButton, new ImageIcon(System.getProperty("user.dir") + "/graphics/exit1.png"));
        
        audioSettings(game.music, game.sfx);

        playButton.setBounds(670, 380, 350, 50);
        howToPlayButton.setBounds(670, 430, 350, 50);
        creditsButton.setBounds(670, 480, 350, 50);
        exitButton.setBounds(670, 530, 350, 50);
        musicButton.setBounds(1000,600,80,80);
        sfxButton.setBounds(1090,600,80,80);

        add(playButton);
        add(howToPlayButton);
        add(creditsButton);
        add(exitButton);
        add(musicButton);
        add(sfxButton);
    }

    // whether a music should play or not
    public void updateMusic(boolean State){
        if (State) { // MUSIC IS ON
            soundmain.start();
            musicButton.setIcon(musicIcons[0]);
        }

        else { // MUSIC IS OFF
            soundmain.stop();
            musicButton.setIcon(musicIcons[2]);
        }
    }

    // whether sound effects are on or not
    public void updateSFX(boolean State){
        if(State) // SFX IS ON
            sfxButton.setIcon(musicIcons[4]);
        else sfxButton.setIcon(musicIcons[6]); // SFX IS OFF
    }

    // initial music and sound settings
    public void audioSettings(boolean music, boolean sfx){
        setMusicIcons();
        if(music == true) {
            musicButton = button(musicButton, musicIcons[0]);
            soundmain.start();
        }
        else {
            musicButton = button(musicButton, musicIcons[2]);
        }

        if(sfx == true)
            sfxButton = button(sfxButton, musicIcons[4]);
        else sfxButton = button(sfxButton, musicIcons[6]);
    }

    // we need this so we can change the icon to be displayed on the music and sfx buttons
    public void setMusicIcons(){
        musicIcons = new ImageIcon[8];
        for(int i = 0; i < musicIcons.length; i++){
            if(i < 4)
                musicIcons[i] = new ImageIcon(System.getProperty("user.dir") + "/graphics/music" + (i + 1) + ".png");
            else musicIcons[i] = new ImageIcon(System.getProperty("user.dir") + "/graphics/sfx" + (i - 3) + ".png");
        }
    }

    public void setActionAndMouseListeners(){
        playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                if(game.music)
                    soundmain.stop();
                game.showCard("play");
			}
        });
        
        playButton.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e){
                playButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/play2.png"));
            }
            public void mouseExited(MouseEvent e) {
                playButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/play1.png"));
            }
            public void mouseReleased(MouseEvent e) {
                playButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/play1.png"));
            }
            public void mouseClicked(MouseEvent e){}
	        public void mousePressed(MouseEvent e){}
        });
		
		howToPlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.showCard("how");
			}
        });

        howToPlayButton.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e){
                howToPlayButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/how2.png"));
            }
            public void mouseExited(MouseEvent e) {
                howToPlayButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/how1.png"));
            }
            public void mouseReleased(MouseEvent e) {
                howToPlayButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/how1.png"));
            }
            public void mouseClicked(MouseEvent e){}
	        public void mousePressed(MouseEvent e){}
        });
        
		creditsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.showCard("credits");
			}
        });

        creditsButton.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e){
                creditsButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/credits2.png"));
            }
            public void mouseExited(MouseEvent e) {
                creditsButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/credits1.png"));
            }
            public void mouseReleased(MouseEvent e) {
                creditsButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/credits1.png"));
            }
            public void mouseClicked(MouseEvent e){}
	        public void mousePressed(MouseEvent e){}
        });
        
        exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.showCard("exit");
			}
        });
        
        exitButton.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e){
                exitButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/exit2.png"));
            }
            public void mouseExited(MouseEvent e) {
                exitButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/exit1.png"));
            }
            public void mouseReleased(MouseEvent e) {
                exitButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/exit1.png"));
            }
            public void mouseClicked(MouseEvent e){}
	        public void mousePressed(MouseEvent e){}
        });

        musicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                if(musicButton.getIcon() == musicIcons[3]) {
                    game.music = true;
                    updateMusic(game.music);
                    
                }
                    
                if(musicButton.getIcon() == musicIcons[1]) {
                    game.music = false;
                    updateMusic(game.music);
                }
			}
        });
        
        musicButton.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e){
                if(musicButton.getIcon() == musicIcons[0]) {
                    musicButton.setIcon(musicIcons[1]);
                }

                if(musicButton.getIcon() == musicIcons[2]){
                    musicButton.setIcon(musicIcons[3]);
                }
            }
            public void mouseExited(MouseEvent e) {
                if(musicButton.getIcon() == musicIcons[1]) {
                    musicButton.setIcon(musicIcons[0]);
                }

                if(musicButton.getIcon() == musicIcons[3]){
                    musicButton.setIcon(musicIcons[2]);
                }
            }
            public void mouseReleased(MouseEvent e){}
            public void mouseClicked(MouseEvent e){}
	        public void mousePressed(MouseEvent e){}
        });

        sfxButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                if(game.sfx) {
                    game.sfx = false;
                    updateSFX(game.sfx);
                    
                }
                else {
                    game.sfx = true;
                    updateSFX(game.sfx);
                }
			}
        });
        
        sfxButton.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e){
                if(sfxButton.getIcon() == musicIcons[4]) {
                    sfxButton.setIcon(musicIcons[5]);
                }

                if(sfxButton.getIcon() == musicIcons[6]){
                    sfxButton.setIcon(musicIcons[7]);
                }
            }
            public void mouseExited(MouseEvent e) {
                if(sfxButton.getIcon() == musicIcons[5]) {
                    sfxButton.setIcon(musicIcons[4]);
                }

                if(sfxButton.getIcon() == musicIcons[7]){
                    sfxButton.setIcon(musicIcons[6]);
                }
            }

            public void mouseReleased(MouseEvent e){}
            public void mouseClicked(MouseEvent e){}
	        public void mousePressed(MouseEvent e){}
        });
    }
}