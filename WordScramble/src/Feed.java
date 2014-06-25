import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;



public class Feed{
		
		private URL url = null;
		private InputStream is = null;
		private JSONTokener tokener = null;
		private JSONArray mainArr = null;
		private JSONObject postObj = null;
		
		private List<List<String>> definitionList= new ArrayList<>();
		private List<String> targetWords = new ArrayList<>();
		
		private String targetword;
		
		private String query1,query2;
		
	
		public Feed(int n){
			getWords(n);
			getDefinitions();
		}
		
	
	
	private void getWords(int n){
		//-----------------WORDS----------------------------------------------------
		
			try {
				query1 = "http://api.wordnik.com/v4/words.json/randomWords?hasDictionaryDef=false&minCorpusCount=0&"
						+ "maxCorpusCount=-1&minDictionaryCount=1&maxDictionaryCount=-1&"
						+ "minLength="+n+"&maxLength="+n+"&sortBy=alpha&limit="+(n+7)+"&api_key=a2a73e7b926c924fad7001ca3111acd55af2ffabf50eb4ae5";
				url = new URL(query1);
				is = url.openStream();
				tokener = new JSONTokener(is);
				mainArr = new JSONArray(tokener);
				
				for(int i=0; i < mainArr.length();i++){
					postObj = mainArr.getJSONObject(i);
					targetword = postObj.getString("word");
					if(targetword.matches("^[a-zA-Z]+$") && targetWords.size() < n){
						targetWords.add(targetword);
					}
				}
				
			} catch (IOException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	
	
	private void getDefinitions(){
		//----------------Definitions------------------------
		try {
			for(int i= 0; i < targetWords.size(); i++){
				query2 = "http://api.wordnik.com:80/v4/word.json/"+targetWords.get(i)+"/definitions?"
						+ "limit=200&includeRelated=true&sourceDictionaries=all&useCanonical=false&includeTags=false&"
						+ "api_key=a2a73e7b926c924fad7001ca3111acd55af2ffabf50eb4ae5";
				
				url = new URL(query2);
				is = url.openStream();
				tokener = new JSONTokener(is);
				mainArr = new JSONArray(tokener);
				List<String> finaldef = new ArrayList<>();
				int size=0;
				if(mainArr.length() > 1){ //ensures if the API return less than 2 definitions
					size=2;
				}else{
					size=1;
				}
				for(int j = 0; j < size; j++){
					postObj = mainArr.getJSONObject(j);
					targetword = postObj.getString("text");
					finaldef.add(targetword);
				}
				definitionList.add(finaldef);
				}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
			
			
	public List<String> getWordList(){
		return targetWords;
	}
	
	public List<List<String>> getDefinitionList(){
		return definitionList;
	}
	
}
