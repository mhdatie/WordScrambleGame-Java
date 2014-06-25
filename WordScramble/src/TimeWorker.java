import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.Timer;


public class TimeWorker extends SwingWorker<String,Object > {

	private int current_time;
	private JLabel timeLabel, header;
	private JPanel secondary;
	private JButton hintButton, checkButton;
	
	private Timer timer;
	public TimeWorker(int n, JLabel label, JPanel panel, JLabel head, JButton hint, JButton check) {
		current_time = n;
		timeLabel = label;
		secondary = panel;
		header = head;
		hintButton = hint;
		checkButton = check;
	}
	
	@Override
	protected String doInBackground() throws Exception {
		 timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(current_time > 0){
					timeLabel.setText(current_time+""); //update GUI
					current_time--;
					timeLabel.setText(current_time+"");
				}	
			}
		});
		
		timer.start();
		while(timer.isRunning()){
			if(current_time == 0){ //end of game, TIME IS UP and update GUI
				timer.stop();
				secondary.remove(hintButton);
				secondary.remove(checkButton);
				}
			}
		
		return "Game Over";
		
	}
	
	protected void done() {
		try {
			header.setText(get().toString()); // prints GAME OVER 
		} catch (InterruptedException e) {
			return;
		} catch (ExecutionException e) {
			return;
		}
	}
	
	public void updateTime(int counter){
		current_time = counter;
	}
	
	public void reward(int counter){
		current_time+=10;
	}
	
	public void punish(int counter){
		current_time-=10;
	}
	
}
