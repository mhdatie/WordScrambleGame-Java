import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingWorker;
import javax.swing.Timer;


public class ShowHint extends SwingWorker<JFrame, Object> {
	private JFrame frame;
	private Timer timer;
	private int counter;
	
	
	public ShowHint(JFrame frame){
		this.frame= frame;
		counter = 5;
	}
	
	@Override//timer for the hint...5 seconds
	protected JFrame doInBackground() throws Exception {
		
		timer = new Timer(1000, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(counter > 0){
						counter--;
					}	
				}
			});
		 	
			timer.start();
			while(timer.isRunning()){
				if(counter == 0){
					timer.stop();
					}
				}
		
		
		return null;
	}
	
	protected void done() {
		frame.setVisible(false);//once the timer is done, hide the definition
	}
	
	
	public void callAgain(){
		counter=5; //if the timer is still running, reset timer instead of creating a new object
	}
	
	
}
