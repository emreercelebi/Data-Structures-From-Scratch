import java.util.ArrayList;

public class Trie {
	
	private TrieNode[] beginning;
	
	public Trie(){
		beginning = new TrieNode[26];
	}
	
	//Insert word into trie. return true if successful, false otherwise
	public boolean insert(String word){
		if (word == null || word.length() == 0) return false;
		//convert input to lower case; eliminate leading and trailing whitespace
		word = word.toLowerCase().trim();
		
		//return false if any non alphabetic character is found. (ASCII char codes)
		for (int i = 0; i < word.length(); i++){
			int c = word.charAt(i);
			if (c < 97 || c > 122) return false;
		}
		//at this point the word is valid
		
		TrieNode[] current = beginning;
		for (int i = 0; i < word.length(); i++){
			char c = word.charAt(i);
			int charCode = word.charAt(i);
			if (current[charCode - 97] == null){
				current[charCode - 97] = new TrieNode(c);
			}
			if (i < word.length() - 1){
				current = current[charCode - 97].getChildren();
			}
		}
		//set isEnd of last TrieNode of word to true
		current[word.charAt(word.length() - 1) - 97].setEnd();
		
		return true;		
	}
	
	//Returns true of word is in the trie, false otherwise
	public boolean contains(String word){
		//return false for null and empty string requests
		if (word == null || word.length() == 0){
			return false;
		}
		
		//convert word to lowercase and remove leading and trailing whitespace
		word = word.toLowerCase().trim();
		
		//return false if word contains any non alphabetic characters
		for (int i = 0; i < word.length(); i++){
			int c = word.charAt(i);
			if (c < 97 || c > 122) return false;
		}
		//at this point, the word is valid
		
		TrieNode[] current = beginning;
		
		for (int i = 0; i < word.length(); i++){
			int charCode = word.charAt(i);
			if (current[charCode - 97] == null) return false;
			if (i < word.length() - 1){
				current = current[charCode - 97].getChildren();
			}
		}
		
		//return true if last character represents end of an existing word, false otherwise
		return current[word.charAt(word.length() - 1) - 97].isEnd();
		
	}
	
	//return true if Trie contains at least one word beginning with prefix
	public boolean startsWith(String prefix){
		//return false for null and empty string requests
		if (prefix == null || prefix.length() == 0){
			return false;
		}
			
		//convert prefix to lowercase and remove leading and trailing whitespace
		prefix = prefix.toLowerCase().trim();
			
		//return false if prefix contains any non alphabetic characters
		for (int i = 0; i < prefix.length(); i++){
			int c = prefix.charAt(i);
			if (c < 97 || c > 122) return false;
		}
		//at this point, the prefix is valid	
		
		TrieNode[] current = beginning;
		
		for (int i = 0; i < prefix.length(); i++){
			int charCode = prefix.charAt(i);
			if (current[charCode - 97] == null) return false;
			if (i < prefix.length() - 1){
				current = current[charCode - 97].getChildren();
			}
		}
		
		//prefix found; doesn't matter if last character is the end of an existing word
		return true;		
	}
	
	//return ArrayList of all words in Trie
	public ArrayList<String> getWords(){
		ArrayList<String> words = new ArrayList<String>();
		TrieNode[] current = beginning;
		StringBuilder sb = new StringBuilder();
		getWordsHelper(words, current, sb);
		return words;
	}
	
	private void getWordsHelper(ArrayList<String> words, TrieNode[] current, StringBuilder sb){
		for (int i = 0; i < current.length; i++){
			if (current[i] != null){
				sb.append(current[i].getValue());
				if (current[i].isEnd){
					words.add(sb.toString());
				}
				getWordsHelper(words, current[i].getChildren(), sb);
				sb.deleteCharAt(sb.length() - 1);
			}
		}
	}	
	
	class TrieNode {
		private char value;
		private TrieNode[] children;
		private boolean isEnd;
		
		TrieNode(char c) {
			this.value = c;
			children = new TrieNode[26];
			isEnd = false;
		}
		
		char getValue(){
			return this.value;
		}
		
		TrieNode[] getChildren(){
			return children;
		}
		
		boolean isEnd(){
			return this.isEnd;
		}
		
		void setEnd(){
			this.isEnd = true;
		}
	}

}
