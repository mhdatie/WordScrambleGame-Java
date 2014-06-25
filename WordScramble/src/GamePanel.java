import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;


public class GamePanel extends JPanel {
	//-------------VARIABLES and DECLARATIONS---------------------
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private int total_time, current_time, not_position, current_score, word_count, success, fail, current_word, twoS, twoF, dn;
	private String label1="";
	private JPanel northPanel, letterPanel, secondary, thirdPanel;
	private JLabel header, timeLabel, scoreLabel, statusLabel;
	private JButton hintButton, checkButton;
	
	private JFrame frame;
	private JTextArea hintArea;
	private JScrollPane scrollPane;
	
	private TimeWorker tw;
	private ShowHint sh;
	
	private int level;
	private List<String> targetWords;
	private ArrayList<Character> letters = new ArrayList<>();
	private JLabel[] labels = new JLabel[2];
	
	
	private List<List<String>> WordsSet;
	private List<List<List<String>>> DefinitionsSet;
	
	
	//----------------CONSTRUCTOR-------------------//
	public GamePanel(List<List<String>> WordsSet, List<List<List<String>>> DefinitionsSet){
		
	this.WordsSet = WordsSet;
	this.DefinitionsSet = DefinitionsSet;
	
	setPreferredSize(new Dimension(600,300));
	setBackground(Color.LIGHT_GRAY);
	setLayout(new BorderLayout());
	
	northPanel = createnorthPanel();
	
	letterPanel = new JPanel();
	letterPanel.setBackground(Color.GRAY);
	
	secondary = createSecondary();
	
	add(northPanel, BorderLayout.NORTH);
	add(letterPanel, BorderLayout.CENTER);
	add(secondary,BorderLayout.SOUTH);
	
	twoS = 0;
	twoF = 0;
	level = 0;
	current_word = 0;
	//starts with level one
	levelOne();
	
	frame = new JFrame();
	prepareHint(); //gets the hint of the current word and sets visibility to false until hint button is clicked
	sh = new ShowHint(frame); //executes the hint in background 
	sh.execute();
	
	//creates a SwingWorker to run the time in the background without having to halt the end user's interaction with the GUI
	tw = new TimeWorker(current_time, timeLabel, secondary, statusLabel, hintButton, checkButton);
	tw.execute();
	}
	
	private JPanel createnorthPanel(){
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(1, 2));
		
		header = new JLabel("");
		header.setPreferredSize(new Dimension(400,90));
		header.setFont(new Font(Font.SERIF, Font.BOLD+Font.ITALIC, 20));
		header.setForeground(Color.RED);
		
		statusLabel = new JLabel();
		statusLabel.setSize(200, 90);
		statusLabel.setHorizontalAlignment(JLabel.CENTER);
		statusLabel.setFont(new Font(Font.SERIF, Font.BOLD, 30));
		statusLabel.setForeground(Color.BLACK);
		statusLabel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Status"));
		
		panel.add(header);
		panel.add(statusLabel);
		
		
		return panel;
	}
	
	private JPanel createSecondary(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3, 0, 1));
		panel.setPreferredSize(new Dimension(100, 40));
		
		current_time = 0;
		timeLabel = new JLabel(current_time+"");
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		timeLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		timeLabel.setForeground(Color.BLUE);
		timeLabel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Time"));
				
		current_score = 0;
		scoreLabel = new JLabel(current_score+"");
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreLabel.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		scoreLabel.setForeground(Color.BLUE);
		scoreLabel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Score"));
		
		hintButton = new JButton("Hint");
		hintButton.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		hintButton.addActionListener(new HintListener());
		
		checkButton = new JButton("Check");
		checkButton.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		checkButton.addActionListener(new CheckListener());
		checkButton.setForeground(Color.RED);
		
		thirdPanel = new JPanel();
		thirdPanel.setLayout(new GridLayout(1,2, 0, 1));
		thirdPanel.add(timeLabel);
		thirdPanel.add(scoreLabel);
		

		panel.add(thirdPanel);
		panel.add(hintButton);
		panel.add(checkButton);
		
		return panel;
	}
	
	private void levelOne(){
		header.setText("Word Scramble");
		
		word_count = 3;
		statusLabel.setText("Level " + (word_count-2));
		success = 3;
		fail = 3;
		current_word = 0;
		dn = 0;
		
		letterPanel.setLayout(new GridLayout(1, word_count));
		
		
		total_time = 80;
		current_time = total_time;
		timeLabel.setText(current_time+"");
		
		targetWords = WordsSet.get(level); //gets the list of words for this level
		newWord(current_word);
	}
	
	private void levelTwo(){
		statusLabel.setText("Level " + (word_count-2));
		success = 4;
		fail = 4;
		current_word = 0;
		dn = 0;
		
		letterPanel.setLayout(new GridLayout(1, word_count));
		
		targetWords = WordsSet.get(level); //gets the list of words for this level
		newWord(current_word);
	}
	
	private void levelThree(){
		statusLabel.setText("Level " + (word_count-2));
		success = 5;
		fail = 5;
		current_word = 0; 
		dn = 0;
		
		letterPanel.setLayout(new GridLayout(1, word_count));
		
		targetWords = WordsSet.get(level); //gets the list of words for this level
		newWord(current_word);
	}
	
	private void levelFour(){
		statusLabel.setText("Level " + (word_count-2));
		success = 6;
		fail = 6;
		current_word = 0;
		dn = 0;
		
		letterPanel.setLayout(new GridLayout(1, word_count));
		
		targetWords = WordsSet.get(level);//gets the list of words for this level
		newWord(current_word);
	}
	
	private void levelFive(){
		statusLabel.setText("Level " + (word_count-2));
		success = 7;
		fail = 7;
		current_word = 0;
		dn = 0;
		
		letterPanel.setLayout(new GridLayout(1, word_count));
		
		targetWords = WordsSet.get(level); //gets the list of words for this level
		newWord(current_word);
	}
	
	//getting a new word from a accumulated lists of words
	private void newWord(int current_word){
		String temp = targetWords.get(current_word).toUpperCase();
		String shuffled_word = shuffleWord(temp);
		not_position = NotInPlace(temp,shuffled_word);
		for(char c : shuffled_word.toCharArray()){
			JLabel characterLabel = new JLabel(c+"");
			charStyle(characterLabel);
			characterLabel.addMouseListener(new ClickListener());
			letterPanel.add(characterLabel);	
			}
	}
	
	//The letter style to be displayed to the end user
	private void charStyle(JLabel label){
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font(Font.SERIF, Font.BOLD, 70));
		label.setBackground(Color.ORANGE);
		label.setForeground(Color.CYAN);
		label.setBorder(BorderFactory.createTitledBorder(new EtchedBorder()));
	}
	
	//returns how many letter not in place originally to help aid the scoring system
	private int NotInPlace(String word, String shuffledWord){
		int n= 0;
		char[] c = word.toCharArray();
		char[] sc = shuffledWord.toCharArray();
		for(int i = 0; i<c.length; i++){
			if(c[i] != sc[i]){
				n++;
			}
		}
		return n;
	}
	
	//shuffles the passed String using a built in method from the Collection class, called shuffle(List<>)
	private String shuffleWord(String word){
		
		for(char c : word.toCharArray()){
			letters.add(c);
		}
		Collections.shuffle(letters);
		
		char[] shuffled_letters = new char[letters.size()];
		for(int i = 0; i < letters.size(); i++){
			shuffled_letters[i] = letters.get(i);
		}
		
		String shuffled_word = new String(shuffled_letters);
		letters.clear();
		
		return shuffled_word;
	}
	
	private class CheckListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Component[] components = letterPanel.getComponents(); //combines the GUI texts to compare with original
			String word="";
			JLabel label = null;
			for(int i = 0; i < components.length;i++){
				label = (JLabel) components[i];
				word += label.getText();
			}//if correct, check for successive wins to reward the end user
			if(word.equalsIgnoreCase(targetWords.get(current_word).toUpperCase())){
				twoS++;
				twoF=0;
				
				success--;
				current_score += not_position * current_time; //updates the score by using the given formula
				scoreLabel.setText(current_score+"");
				
				if(success > 0){ 
					header.setText("Correct - " + success + " words left " + word);
					prepareNewWord(); //calls support method (explained below)
				}
				
				if(success == 0){ //if user guessed all words in a level, check if it's last level, if not, then prepareNextLevel();
					if(word.length() == 7){
						header.setText("You Win!");
						statusLabel.setText("");
						secondary.remove(hintButton);
						secondary.remove(checkButton);
						thirdPanel.remove(timeLabel);
					}else{
						prepareNextLevel(); 
					}
				}
				
			}else{  //wrong guess: incr. the twoF and checks for two successive fails, if yes then..
				twoF++;
				twoS=0;
				if(twoF == 2){
					tw.punish(current_time); //..it decreases time by 10 seconds
				}
				fail--;
				if(fail == 0){ //if player reaches fail limit, game is over, else, show how many attempts are left
					current_time = 0;
					tw.updateTime(current_time);
					header.setText("Game Over");
					remove(letterPanel);
					secondary.remove(hintButton);
					secondary.remove(checkButton);
					thirdPanel.remove(timeLabel);
				}else{
					header.setText("Incorrect - " + fail + " attempts left " + targetWords.get(current_word).toUpperCase());
				}
			}
		}
		
	}
	
	//prepares the next word if the user solved the preceding word
	private void prepareNewWord(){
		current_time = total_time;
		tw.updateTime(current_time);
		
		if(twoS == 2){
			tw.reward(current_time);
		}
		
		letterPanel.removeAll();
		labels[0] = null;
		labels[1] = null;
		dn=0;
		current_word++;
		newWord(current_word);
	}
	//checks which level is next based on the word_count variable (3,4,5,6,7), resets other variables to meet
	// the requirements of every respective level
	private void prepareNextLevel(){
		word_count++;
		letterPanel.removeAll();
		switch (word_count){
		case 4:
			header.setText("Word Scramble");
			total_time = total_time-10; //decrease time by 10 every time the player passes a level
			current_time = total_time;
			twoS = 0;
			twoF = 0;
			level=1;
			levelTwo(); //calls level two
			tw.updateTime(current_time);
			break;
		case 5:
			header.setText("Word Scramble");
			total_time = total_time-10;
			current_time = total_time;
			twoS = 0;
			twoF = 0;
			level=2;
			levelThree(); //level three, and so on...
			tw.updateTime(current_time);
			break;
		case 6:
			header.setText("Word Scramble");						
			total_time = total_time-10;
			current_time = total_time;
			twoS = 0;
			twoF = 0;
			level=3;
			levelFour();
			tw.updateTime(current_time);
			break;
		case 7:
			header.setText("Word Scramble");
			total_time = total_time-10;
			current_time = total_time;
			twoS = 0;
			twoF = 0;
			level=4;
			levelFive();
			tw.updateTime(current_time);
			break;
		}
	}
	//prepares the GUI of the hintArea on Hint Button click
	private void prepareHint(){
		hintArea = new JTextArea("loading");
		hintArea.setEditable(false);
		scrollPane = new JScrollPane(hintArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		frame.getContentPane().add(scrollPane);
		frame.setUndecorated(true);
		frame.setVisible(false);
		frame.setResizable(false);
		frame.setSize(300,100);
	}
	
	//All this listener does is that it sets the frame to visible and sets the new definition to the HINTAREA label
	private class HintListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			frame.toFront(); 
			frame.setLocationRelativeTo(GamePanel.this);
			
			if(!frame.isVisible()){
				frame.setVisible(true);
			}
			
			//sets the next definition
			hintArea.setText("Definition:\n" + DefinitionsSet.get(level).get(current_word).get(dn));
			hintArea.setCaretPosition(0);
			
			//some words have 1 definition only, and since I'm obtaining max 2, so it's either 0 or 1 for the index
			if(dn==0 && (DefinitionsSet.get(level).get(current_word).size() > 1)){
				dn++;
			}else{
				if(dn==1){
					dn--;
				}
			}
			
			//if the timer for the definition frame is done, call it again, if not, reset the timer.
			if(sh.isDone()){
				sh = new ShowHint(frame);
				sh.execute();
			}else{
				sh.callAgain();
			}
			
		}
		
	}


	// clicking one of the letter labels will store the text in Label[] element, it uses a support method (checkLabelArray) to 
	//to determine, what to select and when to swap the letters on the second letter choice
	private class ClickListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent e) {
			JLabel label = (JLabel) e.getComponent();
			if(checkLabelArray(labels) == 0){
				labels[0] = label;
				label.setForeground(Color.RED);
			}else if(checkLabelArray(labels) == 1){
				labels[1] = label;
				if(checkLabelArray(labels) == 2){
					label1 = labels[0].getText();
					labels[0].setText(labels[1].getText());
					labels[1].setText(label1);
					
					labels[0].setForeground(Color.CYAN);
				
					labels[0] = null;
					labels[1] = null;
				}
			}
		}

	}
	
	
	//check if both, one or none of the Letters in the label are selected
	private int checkLabelArray(JLabel[] labels){
		int count = 0;
		if(labels[0] == null && labels[1] == null){
			return count;
		}
		if(labels[0] != null && labels[1] == null){
			count = 1;
			return count;
		}
		if(labels[0] != null && labels[1] != null){
			count = 2;
			return count;
		}
		return count;
		
	}
	
	
	
	
	

}
