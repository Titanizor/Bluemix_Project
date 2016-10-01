import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public class Teste {
	private File fileToRead;
	private Gson GsonFile;
	private JsonReader reader;
	private LinkedHashMap<String, Object> cacheMap;
	private String mapCacheName;
	private LinkedHashMap<String, Object> globalMap = new LinkedHashMap<String, Object>();
	private int numberObjectsReceived;
	private int counterPrint;

	public Teste(String[] args) {
		validateParameters(args);
		readFile(args);
	}
	
	
private void validateParameters(String[] args) {

	setGsonFile(new Gson());
	setFileToRead(new File(args[0]));
	/*
	if (args[0] == null || (getFileToRead().exists() && !getFileToRead().isDirectory())) {
		System.out.println("Failing in getting the file");
		System.exit(0);
	}
	*/
		
	URL location = Teste.class.getProtectionDomain().getCodeSource().getLocation();
    System.out.println("Java Location - " + location.getFile());
		
	}


private void readFile(String[] args) {
setNumberObjectsReceived(Integer.parseInt(args[1]));
int numberObjectsPrinted = 0;
    try {
        setReader(new JsonReader(new FileReader(args[0])));
        
        while(numberObjectsPrinted != getNumberObjectsReceived()) {
        	printObject(getReader());
        	printCacheMap();
        	saveGlobalMap();
  //      	printGlobalCacheMap();
        //	clearCacheMap();
        	numberObjectsPrinted++;
        }
        
        getReader().close();
    } catch (FileNotFoundException e) {
        System.err.print(e.getMessage());
    } catch (IOException e) {
        System.err.print(e.getMessage());
    }
    printGlobalCacheMap();
    printGlobalCacheMapWithSeparator();
}
		
private void printGlobalCacheMap() {
	Iterator<String> keySetIterator = getGlobalMap().keySet().iterator();
	System.out.println("Priting Global Map:");
	while(keySetIterator.hasNext()){
		String key = keySetIterator.next();
		System.out.println("Found 1 Map Inside: " + key);
		LinkedHashMap<String, Object> mapFound = (LinkedHashMap<String, Object>) getGlobalMap().get(key);
		System.out.println(mapFound.toString());
		Iterator<String> keySetIterator2 =  mapFound.keySet().iterator();
	//	System.out.println(keySetIterator2.toString());
		while(keySetIterator2.hasNext()) {
			String key2 = keySetIterator2.next();
			System.out.println("Found something..." + key2);
			mapFound.get(key2);
			System.out.println("key: " + key2 + " value: " + mapFound.get(key2));
		}
	}
}

private void printGlobalCacheMapWithSeparator() {
	
	String keysStored = "";
	setCounterPrint(0);
	
	// headers..
	Iterator<String> keySetIterator = getGlobalMap().keySet().iterator();
	while(keySetIterator.hasNext()){
		String key = keySetIterator.next();
		LinkedHashMap<String, Object> mapFound = (LinkedHashMap<String, Object>) getGlobalMap().get(key);
		Iterator<String> keySetIterator2 =  mapFound.keySet().iterator();
	//	System.out.println(keySetIterator2.toString());
		setCounterPrint(getCounterPrint() + 1);
		while(keySetIterator2.hasNext()) {
			String key2 = keySetIterator2.next();
			if(keySetIterator2.hasNext())
			keysStored = keysStored + key2 + " | ";
			//System.out.println(key2 + " |");
			else {
			if(getCounterPrint() == getNumberObjectsReceived() )
				keysStored = keysStored + key2;
			else
			keysStored = keysStored + key2 + " | ";
			//System.out.println(key2);	
			}
		}
	}
	
	setCounterPrint(0);
	String valuesStored = "";
	// values..
	Iterator<String> keySetIterator3 = getGlobalMap().keySet().iterator();
	while(keySetIterator3.hasNext()){
		String key3 = keySetIterator3.next();
		LinkedHashMap<String, Object> mapFound2 = (LinkedHashMap<String, Object>) getGlobalMap().get(key3);
		Iterator<String> keySetIterator4 =  mapFound2.keySet().iterator();
	//	System.out.println(keySetIterator2.toString());
		setCounterPrint(getCounterPrint() + 1);
		while(keySetIterator4.hasNext()) {
			String key4 = keySetIterator4.next();
			if(keySetIterator4.hasNext())
				valuesStored = valuesStored + mapFound2.get(key4) + " | ";
				//System.out.println(mapFound2.get(key4) + " | ");
			else {
			if(getCounterPrint() == getNumberObjectsReceived() )
			    valuesStored = valuesStored + mapFound2.get(key4);
			else
				valuesStored = valuesStored + mapFound2.get(key4) + " | ";
				//System.out.println(mapFound2.get(key4));
			}
		}
	}
	System.out.println(keysStored);
	System.out.println(valuesStored);
	
	createNewFile(keysStored, valuesStored);
}


private void createNewFile(String keysStored, String valuesStored) {
	PrintWriter writer;
	try {
		writer = new PrintWriter("the-file-name.txt", "UTF-8");
		writer.println(keysStored);
		writer.println(valuesStored);
		writer.close();
	} catch (FileNotFoundException | UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
}


private void saveGlobalMap() {
	getGlobalMap().put(getMapCacheName(), getCacheMap());
	System.out.println("Map Size:" + getGlobalMap().size());
}


private void printCacheMap() {
	Iterator<String> keySetIterator = getCacheMap().keySet().iterator();
	System.out.println("Priting Map: - Size:" + getCacheMap().size());
	while(keySetIterator.hasNext()){
	  String key = keySetIterator.next();
	  System.out.println("key: " + key + " value: " + getCacheMap().get(key));
	}
}


private void clearCacheMap() {
	getCacheMap().clear();
}

private void printObject(JsonReader reader) throws IOException {
	int counter = 0;
	Object variableValue = null;
	String variableKey = null;
	setCacheMap(new LinkedHashMap<String, Object>());
	setMapCacheName(null);
	 while (getReader().hasNext()){
	     //   System.out.println("Counter - " + counter);
	     //   System.out.println("Next object --> " + getReader().peek().toString());
	        switch (getReader().peek()) {
			case BEGIN_OBJECT:
			//	System.out.println("Begin object found..");
				getReader().beginObject();
				if(getMapCacheName() == null) {
					setMapCacheName(getReader().nextName());
					variableKey = getMapCacheName();
			//		System.out.println(getMapCacheName());
				}
				else {
				variableKey = getReader().nextName();
			//	System.out.println(variableKey);
				}
				break;
			case STRING:
			//	System.out.println("String object found..");
				variableValue = getReader().nextString();
			//	System.out.println(variableValue);
				break;
			case END_OBJECT:
			//	System.out.println("End object found..");
				getReader().endObject();
			//	System.out.println(getReader().nextName());
				break;
			case NAME:
			//	System.out.println("Name object found..");
				variableKey = getReader().nextName();
			//	System.out.println(variableKey);
				break;
			case NULL:
			//	System.out.println("NULL object found..");
				getReader().nextNull();
				variableValue = null;
			//	System.out.println(variableValue);
				break;
			case NUMBER:
			//	System.out.println("Number object found..");
				variableValue = getReader().nextString();
			//	System.out.println(variableValue);
				break;
			case BOOLEAN:
			//	System.out.println("Boolean object found..");
				variableValue = getReader().nextString();
			//	System.out.println(variableValue);
				break;
			default:
				break;
			}
		        counter++;
		      saveObjectMap(getCacheMap(), variableKey, variableValue);
		        
	      }
	        getReader().endObject();
	        System.out.println("-- Finished object! --");
}

private void saveObjectMap(HashMap<String, Object> cacheMap, String variableKey, Object variableValue) {
	cacheMap.put(variableKey, variableValue);
}


public static void main(String[] args) {
	new Teste(args);
}


public File getFileToRead() {
	return fileToRead;
}


public void setFileToRead(File fileToRead) {
	this.fileToRead = fileToRead;
}


public Gson getGsonFile() {
	return GsonFile;
}


public void setGsonFile(Gson gsonFile) {
	GsonFile = gsonFile;
}


public JsonReader getReader() {
	return reader;
}


public void setReader(JsonReader reader) {
	this.reader = reader;
}


public HashMap<String, Object> getCacheMap() {
	return cacheMap;
}


public void setCacheMap(LinkedHashMap<String, Object> cacheMap) {
	this.cacheMap = cacheMap;
}


public String getMapCacheName() {
	return mapCacheName;
}


public void setMapCacheName(String mapCacheName) {
	this.mapCacheName = mapCacheName;
}


public LinkedHashMap<String, Object> getGlobalMap() {
	return globalMap;
}


public void setGlobalMap(LinkedHashMap<String, Object> globalMap) {
	this.globalMap = globalMap;
}


public int getNumberObjectsReceived() {
	return numberObjectsReceived;
}


public void setNumberObjectsReceived(int numberObjectsReceived) {
	this.numberObjectsReceived = numberObjectsReceived;
}


public int getCounterPrint() {
	return counterPrint;
}


public void setCounterPrint(int counterPrint) {
	this.counterPrint = counterPrint;
}


}

