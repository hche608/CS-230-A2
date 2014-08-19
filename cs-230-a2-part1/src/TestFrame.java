import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TestFrame extends JFrame {

	public TestFrame() {
		setTitle("Test Application");
		setPreferredSize(new Dimension(200, 400));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container cp = getContentPane();

		final StringIntegerTableModel tableModel = new StringIntegerTableModel(
				"String", "Interger");
		JTable table = new JTable(tableModel);
		cp.add(table.getTableHeader(), BorderLayout.NORTH);
		cp.add(table, BorderLayout.CENTER);

		final JTextField nameField = new JTextField("A string");
		final JTextField scoreField = new JTextField("0");
		JButton button = new JButton("Add");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModel.addRow(nameField.getText(),
						Integer.valueOf(scoreField.getText()));
			}
		});

		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.add(nameField);
		panel.add(scoreField);
		panel.add(button);
		cp.add(panel, BorderLayout.SOUTH);

		pack();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		(new TestFrame()).setVisible(true);

	}

}
