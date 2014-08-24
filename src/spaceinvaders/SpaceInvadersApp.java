package spaceinvaders;

import spaceinvaders.highscores.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;

/**
 * Main application class for the Space Invaders game.
 * 
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class SpaceInvadersApp extends JFrame {

	private final GamePanel game;

	final private JMenuItem menuItemGamePause;
	
	final private HighScoreTableModel scoresTable = new HighScoreTableModel("Name","Score");

	/**
	 * Create new Space Invaders application.
	 * 
	 * @throws HeadlessException
	 */
	public SpaceInvadersApp() throws HeadlessException {

		// Set title of application window.
		setTitle("Space Invaders");

		// Get content pane:
		Container cp = getContentPane();

		// Set up menu hierarchy:

		JMenuBar menuBar = new JMenuBar();

		JMenu menuGame = new JMenu("Game");
		menuGame.setMnemonic(KeyEvent.VK_G);

		final JMenuItem menuItemGameNew = new JMenuItem("New", KeyEvent.VK_N);
		menuItemGameNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		menuGame.add(menuItemGameNew);

		menuItemGamePause = new JMenuItem("Pause/Unpause", KeyEvent.VK_P);
		menuItemGamePause.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_ESCAPE, 0));
		menuItemGamePause.setEnabled(false);
		menuGame.add(menuItemGamePause);

		//add top scores button
		final JMenuItem topScores = new JMenuItem("Top Scores", KeyEvent.VK_T);
		menuGame.add(topScores);

		menuBar.add(menuGame);
		//end add top scores button
		
		menuGame.addSeparator();

		final JMenuItem menuItemGameExit = new JMenuItem("Exit", KeyEvent.VK_X);
		menuItemGameExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.CTRL_MASK));
		menuGame.add(menuItemGameExit);

		menuBar.add(menuGame);

		JMenu menuHelp = new JMenu("Help");
		menuHelp.setMnemonic(KeyEvent.VK_H);

		final JMenuItem menuItemHelpAbout = new JMenuItem("About",
				KeyEvent.VK_A);
		menuHelp.add(menuItemHelpAbout);

		menuBar.add(menuHelp);

		setJMenuBar(menuBar);

		// Set up listeners for menu item selection events:

		menuItemGameNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemGamePause.setEnabled(true);
				game.requestFocus();
				game.startGame();
			}
		});

		menuItemGamePause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.pauseGame();
			}
		});

		menuItemGameExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// access top scores

		topScores.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				(new HighScoreDialog(scoresTable)).setVisible(true);
				/*				
				JPanel scoresPanel = new JPanel(new BorderLayout());				
				JTable table = new JTable(scoresTable);
				scoresPanel.add(table.getTableHeader(), BorderLayout.NORTH);
				scoresPanel.add(table, BorderLayout.CENTER);

				JButton button = new JButton("OK");
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cp.remove(scoresPanel);
						pack();
					}
				});

				scoresPanel.add(button, BorderLayout.SOUTH);
				cp.add(scoresPanel, BorderLayout.EAST);
				
				pack();	*/	
			}
		});

		// end access top scores

		menuItemHelpAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								rootPane,
								"Space Invaders game originally due to Kevin Glass.\n"
										+ "(See http://www.cokeandcode.com/info/tut2d.html.)\n\n"
										+ "Mercilessly adapted for CompSci230 assignment 2\n"
										+ "by Tim Vaughan.",
								"About Space Invaders",
								JOptionPane.INFORMATION_MESSAGE,
								new ImageIcon(getClass().getClassLoader()
										.getResource("sprites/logo.png")));
			}
		});

		// Set up game canvas:
		game = new GamePanel(this);
		cp.add(game);

		// Cause outer components to adjust to the the size of the canvas:
		pack();

		// Our game canvas has a fixed size - don't let user's change
		// the window size:
		setResizable(false);

		// Centre window initially.
		setLocationRelativeTo(null);

		// Define what happens when a user closes the game window:
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Method called by game when a game has been won/lost.
	 */
	public void gameEnded() {
		String message;
		if (game.isGameWon()) {
			message = "You defeated the alien menace!  Congratulations!\n\n"
					+ "Your score was " + game.getScore();
			
			//add high score
			
			if(game.getScore() > 0){
				String playerName;
				playerName = JOptionPane.showInputDialog(
						"Your score was " + game.getScore(), "Please enter your name");
				while(playerName == null){
					int reply = JOptionPane.showConfirmDialog(null, "You did not enter a Player name\nAre you sure? ", "Warning", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.NO_OPTION){
						playerName = JOptionPane.showInputDialog(
								"Your score was " + game.getScore(), "Please enter your name");
						scoresTable.addRow(playerName,
								Integer.valueOf(game.getScore()));
					} else {
						break;
					}
				}
			}
			// end add high score
			
		} else {
			message = "Oh no! The aliens have defeated you.";
		}

		JOptionPane.showMessageDialog(this, message, "Game Over",
				JOptionPane.INFORMATION_MESSAGE);

		menuItemGamePause.setEnabled(false);
		
	}

	/**
	 * Main entry point for app.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		(new SpaceInvadersApp()).setVisible(true);

	}

}
