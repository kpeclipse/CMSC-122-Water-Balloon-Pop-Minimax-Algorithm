package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GameImagePanel extends JPanel
{
	private static final long serialVersionUID = -2657183267005854109L;
	private BufferedImage image;

	public GameImagePanel(BufferedImage image) {
		this.image = image;
		setSize(image.getWidth(), image.getHeight());
		repaint();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
}