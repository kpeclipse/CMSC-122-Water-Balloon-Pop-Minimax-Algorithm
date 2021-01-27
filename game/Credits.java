package game;

import settings.GameImagePanel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Credits extends JPanel {
    private static final long serialVersionUID = -2657183267005854109L;
    
    private File file;
    private BufferedImage image;
    private GameImagePanel mainbg;
    private Game game;

    private JButton backButton;

    public Credits(int width, int height, Game g){
        setSize(width, height);
        setLayout(null);
        setOpaque(false);

        setButtons();
        setBackground(System.getProperty("user.dir") + "/graphics/Credits.png");

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
        backButton = button(backButton, new ImageIcon(System.getProperty("user.dir") + "/graphics/back1.png"));
        backButton.setBounds(10, 10, 200, 100);
        add(backButton);
    }

    public void setActionAndMouseListeners(){
        backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.showCard("main");
			}
        });
        
        backButton.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e){
                backButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/back2.png"));
            }
            public void mouseExited(MouseEvent e) {
                backButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/back1.png"));
            }
            public void mouseReleased(MouseEvent e) {
                backButton.setIcon(new ImageIcon(System.getProperty("user.dir") + "/graphics/back1.png"));
            }
            public void mouseClicked(MouseEvent e){}
	        public void mousePressed(MouseEvent e){}
        });
    }
}