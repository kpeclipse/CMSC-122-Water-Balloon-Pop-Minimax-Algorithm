package game;

import settings.HighScore;
import settings.SoundClip;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
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

    private SoundClip soundmain = new SoundClip(System.getProperty("user.dir") + "/resources/Main BG Music.wav", 0);

    public MainMenu(int width, int height, Game g){
        setSize(width, height);
        setLayout(null);
        setOpaque(false);
        
        showHighScore();
        setButtons();
        setBackground(System.getProperty("user.dir") + "/graphics/Main.png");

        soundmain.start();
        game =  g;
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
        highScoreLabel.setFont(useFont(System.getProperty("user.dir") + "/graphics/Emulogic.ttf", 10));
        highScoreLabel.setForeground(Color.ORANGE);
//        highscoreLabel.setHorizontalAlignment(JTextField.CENTER);
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

    public void setButtons(){
        playButton = button(playButton, new ImageIcon(System.getProperty("user.dir") + "/graphics/play1.png"));
        howToPlayButton = button(howToPlayButton, new ImageIcon(System.getProperty("user.dir") + "/graphics/how1.png"));
        creditsButton = button(creditsButton, new ImageIcon(System.getProperty("user.dir") + "/graphics/credits1.png"));
        exitButton = button(exitButton, new ImageIcon(System.getProperty("user.dir") + "/graphics/exit1.png"));
        musicButton = button(musicButton, new ImageIcon(System.getProperty("user.dir") + "/graphics/music1.png"));
        sfxButton = button(sfxButton, new ImageIcon(System.getProperty("user.dir") + "/graphics/sfx1.png"));

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

    // What font to use
    public Font useFont(String path, int size){
        try {
			return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
		} catch (FontFormatException | IOException e) {e.printStackTrace();}
		return null;
    }

    public void setActionAndMouseListeners(){
        playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
                if(musicButton.getIcon() == new ImageIcon(System.getProperty("user.dir") + "/graphics/music3.png"))
                {
                        musicButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/music2.png"));
                        //enabledMusic = false;
                }
                    
                if(musicButton.getIcon() == new ImageIcon(System.getProperty("user.dir") + "/graphics/music1.png"))
                {
                        musicButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/music4.png"));
                        //enabledMusic = true;
                }
			}
        });
        
        musicButton.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e){
                if(musicButton.getIcon() == new ImageIcon(System.getProperty("user.dir") + "/graphics/music1.png")){
                    musicButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/music2.png"));
                }
                if(musicButton.getIcon() == new ImageIcon(System.getProperty("user.dir") + "/graphics/music3.png")){
                    musicButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/music4.png"));
                }
            }
            public void mouseExited(MouseEvent e) {
                if(musicButton.getIcon() == new ImageIcon(System.getProperty("user.dir") + "/graphics/music2.png")){
                    musicButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/music1.png"));
                }
                if(musicButton.getIcon() == new ImageIcon(System.getProperty("user.dir") + "/graphics/music4.png")){
                    musicButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/music3.png"));
                }
            }
            public void mouseReleased(MouseEvent e){}
            public void mouseClicked(MouseEvent e){}
	        public void mousePressed(MouseEvent e){}
        });
    }
}