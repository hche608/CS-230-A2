package spaceinvaders.highscores;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class HighScoreDialog extends JDialog {

	public HighScoreDialog(HighScoreTableModel tableModel) {
		setTitle("Top Scores");
		setPreferredSize(new Dimension(200, 400));
		//setLocationRelativeTo(null);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container cp = getContentPane();
		
		JTable table = new JTable(tableModel);
		cp.add(table.getTableHeader(), BorderLayout.NORTH);
		cp.add(table, BorderLayout.CENTER);

		JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.add(button);
		cp.add(panel, BorderLayout.SOUTH);

		pack();
	}
}
