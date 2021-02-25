import java.util.*;

public class Duplicate
{
	public static void main(String[] args) {
		String[] arrText = {
			"Буря", "мглою", "небо", "кроет,", 
			"Вихри", "снежные", "крутя;",
			"То,", "как", "зверь,", "она", "завоет,",
			"То", "заплачет,", "как", "дитя,",
			"То", "по", "кровле", "обветшалой",
			"Вдруг", "соломой", "зашумит,",
			"То,", "как", "путник", "запоздалый,",
			"К", "нам", "в", "окошко", "застучит."
		};

		Set<String> wordList = new LinkedHashSet<>();
		for(String word : arrText){
		    wordList.add(word);
		}
		
		for (String word : wordList){
		    int count = 0;
    		for(String wArr : arrText){
    		    if(word.equals(wArr)){
    		        count++;
    		    }
    		}
    	System.out.println(word + ": " + count + "шт.");
		}
	}
}
