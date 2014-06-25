
import java.util.ArrayList;
import java.util.List;




import javax.swing.JFrame;
import javax.swing.SwingWorker;



public class HintWorker extends SwingWorker<Void, Object> {
	private Feed x;	
	private List<String> Words;
	private List<List<String>> Definitions;
	
	private List<List<String>> WordsSet; //ends with size of 5, each element with a list of words 
	private List<List<List<String>>> DefinitionsSet; //size of 5, in each one a size of (words), then in each a max size
													//of 2 for (definitions)
	private JFrame frame;
	
	public HintWorker(JFrame frame) {
		this.frame = frame;
		WordsSet = new ArrayList<>();
		DefinitionsSet = new ArrayList<>();
	}
	
	@Override//fetches all words starting with i =3 for word length
	protected Void doInBackground() throws Exception {
			int counter = 0;
		 for(int i = 3; i < 8 ; i++){
			 counter+=20;
             x = new Feed(i);
			 Words = x.getWordList(); //words of each level
			 Definitions = x.getDefinitionList(); //definitions for each of the words in the level
			
			 WordsSet.add(Words); 
			 DefinitionsSet.add(Definitions);
			 setProgress(counter); //progress bar
		}
		return null;
	}
	
	protected void done() {
		frame.setContentPane(new GamePanel(WordsSet, DefinitionsSet)); //when done, it sets the content pane to the GamePanel
		frame.pack();
	}
	
	public List<List<String>> getWords(){
		return WordsSet;
	}
	
	public List<List<List<String>>> getDefs(){
		return DefinitionsSet;
	}
	
	
}
