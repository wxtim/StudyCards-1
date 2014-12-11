package studyCards;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Utilities {

	public static void saveCollection(CardCollection collection) {
		PrintWriter writer;
		try {
			writer = new PrintWriter("savedCollections//"+collection.getName(), "UTF-8");
			for (int i = 0; i < collection.size(); i++){
				writer.println(collection.getCard(i).getQuestion());
				writer.println(collection.getCard(i).getAnswer());
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException | CardNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public static CardCollection loadCollection(String collectionName){
		CardCollection tempCollection = new CardCollection(collectionName);
	    boolean needNewCard = true;
	    BufferedReader br;
	    
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("savedCollections//"+collectionName),"UTF-8"));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
		            sb.append(line);
		            
		            if (needNewCard){
		            	tempCollection.addCard(new Card());
		            	tempCollection.getCard(0).setQuestion(line);
		            	needNewCard = false;
		            } else {
		            	tempCollection.getCard(0).setAnswer(line);
		            	needNewCard = true;
		            }
		            line = br.readLine();
		            
		        }
		    } catch (IOException | CardNotFoundException e) {
				e.printStackTrace();
			} finally {
		        try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    return tempCollection;
	}

	public static ArrayList<CardCollection>  loadCollectionList(){
		ArrayList<String> tempList = new ArrayList<String>();
	    BufferedReader br;
	    
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("savedCollections//"+"All collections"),"UTF-8"));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
		            sb.append(line);
		            tempList.add(line);

		            line = br.readLine();
		            
		        }
		    } catch (IOException e) {
				e.printStackTrace();
			} finally {
		        try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ArrayList<CardCollection> tempColList = new ArrayList<CardCollection>();
				
		for (int i = 0; i < tempList.size();i++){
			tempColList.add(loadCollection(tempList.get(i)));
		}
		return tempColList;
		
	}
	
	public static void saveCollectionList(ArrayList<CardCollection> collections){
		PrintWriter writer;
		for (int i = 0; i < collections.size(); i++){
			saveCollection(collections.get(i));
		}
		try {
			writer = new PrintWriter("savedCollections//"+"All collections", "UTF-8");
			for (int i = 0; i < collections.size(); i++){
				writer.println(collections.get(i).getName());
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
