import javax.swing.JFrame;


public class MainFrame {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Word Scramble");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(new LoadingPanel(frame));
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

}
