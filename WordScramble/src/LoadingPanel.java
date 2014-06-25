import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class LoadingPanel extends JPanel {
	private JLabel header;
	private HintWorker hw;
	private JFrame frame;
	private JProgressBar progressBar;
	
	public LoadingPanel(JFrame frame){
		this.frame = frame;
		setPreferredSize(new Dimension(300,100));
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		
		header = new JLabel("Loading...");
		header.setFont(new Font(Font.SERIF, Font.BOLD+Font.ITALIC, 20));
		header.setHorizontalAlignment(JLabel.CENTER);
		header.setForeground(Color.ORANGE);
		
		
		//pass the frame, and start fetching for words and definitions online
		hw = new HintWorker(frame);
		progressBar = new JProgressBar(0,100); //setting the progress bar
		progressBar.setStringPainted(true);
		hw.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				 if ("progress".equals(e.getPropertyName())) {
		                progressBar.setValue((Integer) e.getNewValue()); //setProgress(counter)	
				 }
			}
		});
		
		
		hw.execute(); //execute worker
		
		
		add(header, BorderLayout.CENTER);
		add(progressBar, BorderLayout.SOUTH);
	}
}
