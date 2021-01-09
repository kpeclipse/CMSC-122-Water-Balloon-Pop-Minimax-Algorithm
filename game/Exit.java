package game;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Exit extends JPanel {
    private File file;
    private BufferedImage image;
    private GameImagePanel mainbg;
    private Game game;

    private JButton yesButton;
    private JButton noButton;

    public Exit(int width, int height, Game g){
        setSize(width, height);
        setLayout(null);
        setOpaque(false);

        setButtons();
        setBackground(System.getProperty("user.dir") + "/graphics/Exit.png");

        game =  g;
        setActionAndMouseListeners();
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
        yesButton = button(yesButton, new ImageIcon(System.getProperty("user.dir") + "/graphics/yes1.png"));
        noButton = button(noButton, new ImageIcon(System.getProperty("user.dir") + "/graphics/no1.png"));
        
        yesButton.setBounds(250, 200, 200,150);
        noButton.setBounds(700, 200, 200,150);

        add(yesButton);
        add(noButton);
    }

    public void setActionAndMouseListeners(){
        yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
        });
        
        yesButton.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e){
                yesButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/yes2.png"));
            }
            public void mouseExited(MouseEvent e) {
                yesButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/yes1.png"));
            }
            public void mouseReleased(MouseEvent e) {
                yesButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/yes1.png"));
            }
            public void mouseClicked(MouseEvent e){}
	        public void mousePressed(MouseEvent e){}
        });

        noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.showCard("main");
			}
        });
        
        noButton.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e){
                noButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/no2.png"));
            }
            public void mouseExited(MouseEvent e) {
                noButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/no1.png"));
            }
            public void mouseReleased(MouseEvent e) {
                noButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/no1.png"));
            }
            public void mouseClicked(MouseEvent e){}
	        public void mousePressed(MouseEvent e){}
        });
    }
}