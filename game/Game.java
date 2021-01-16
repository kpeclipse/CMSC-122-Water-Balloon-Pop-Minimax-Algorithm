package game;

import java.awt.CardLayout;
import javax.swing.JFrame;

public class Game extends JFrame{
    protected static int width = 1200, height = 725;
    
    private MainMenu mainMenu;
    private HowToPlay howToPlay;
    private Credits credits;
    private Exit exit;
    private Play play;

    private CardLayout layout = new CardLayout();

    public static void main(String[] args) {
        new Game();
    }   
    
    public Game() {
        super("WATER BALLOON POP! (Version 2)");
        setFrame();
        initial();

        setVisible(true);
    }

    // First thing to do when game starts running
    public void initial(){
        mainMenu = new MainMenu(width, height, this);   // The Main Menu panel
        howToPlay = new HowToPlay(width, height, this); // The How to Play panel
        credits = new Credits(width, height, this); // The Credits panel
        exit = new Exit(width, height, this);   // The Exit panel
        play = new Play(width, height, this);   //  The Play panel (where the game happens)

        add("main", mainMenu);
        add("how", howToPlay);
        add("credits", credits);
        add("exit", exit);
        add("play", play);
    }

    // Card Layout
    public void showCard(String card) {
        layout.show(this.getContentPane(), card);
        
        // For the Key Listener in the Play panel to work
        if(card == "play") {
            play.requestFocusInWindow();
            play.initial();
        }
	}

    // JFrame Settings
    public void setFrame() {
        setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
        setLayout(layout);
    }
}