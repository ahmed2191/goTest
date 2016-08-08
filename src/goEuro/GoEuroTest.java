package goEuro;
import java.net.*;
import java.io.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class GoEuroTest {
	public static boolean writeToFile(String path,String data){
		PrintWriter writer;
		try {
			writer = new PrintWriter( path, "UTF-16");
			writer.println(data);
	        writer.close();
		} catch (FileNotFoundException e) {
			if(path.compareToIgnoreCase("log")!=0){
				System.out.println("Sorry there were an error while trying to write to the file "+path+ " please make sure this file is not used by any other application and try again. For more information please check the log file");
				writeToFile("log.txt",e.toString());
			}else{
				System.out.println("Sorry cannot write to the log file, please make sure the file is not used by any other application");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			if(path.compareToIgnoreCase("log")!=0){
				//should never happen
				System.out.println("Sorry there were an error while writing to the file "+path+ " please try again. For more information please check the log file");
				writeToFile("log.txt",e.toString());
			}else{
				//should never happen.
				System.out.println("Sorry there were a problem when writing to the log file, please try again");
			}
		}
        
		return true;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String query;
		if(args.length != 0){
			query=args[0];
		}else{
			System.out.println("Please enter a city name to query");
			return;
		}
		
		if(query.compareToIgnoreCase("")==0){
			System.out.println("Please enter a city name to query");
			return;
		}
		URL goEuro;
		try {
			goEuro = new URL("http://api.goeuro.com/api/v2/position/suggest/en/"+query);
		
        URLConnection go = goEuro.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                go.getInputStream(), "UTF-8"));
        if(in!=null){
	        String answer=in.readLine();
	        if(answer != null){
		        JSONArray jsonMainArr = new JSONArray(answer);
		        String entry="";
		        for(int i=0;i<jsonMainArr.length();i++){
		        	int id =jsonMainArr.getJSONObject(i).getInt("_id");
		        	entry += id +"";
		        	String name =jsonMainArr.getJSONObject(i).getString("name");
		        	entry+= ","+name;
		        	String type =jsonMainArr.getJSONObject(i).getString("type");
		        	entry+= ","+type;
		        	JSONObject loc =(JSONObject) jsonMainArr.getJSONObject(i).get("geo_position");
		        	if(loc != null){
		        		Double latitude  = loc.getDouble("latitude");
		        		entry+= ","+latitude;
		        		Double longitude = loc.getDouble("longitude");
		        		entry+= ","+longitude;
		        				
		        	}
		        	entry+= "\n";
		        }
		        //note here it wasnt mentioned if the file should append new queries or create new files
		        //so i found create new files was more relevant. Also it wasnt mentioned if same query done twice
		        //should it create new files or overwrite so i picked overwrite. 
		        writeToFile(query+".csv", entry);
	        }
        }
        in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("Sorry there were a mistake in the URL please try again, for more infromation check the log file");
			writeToFile("log.txt",e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Sorry there were a mistake try again, for more infromation check the log file");
			writeToFile("log.txt",e.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Sorry there were a mistake in the recived data (JSON object) please try again, for more infromation check the log file");
			writeToFile("log.txt",e.toString());
		}
    }

}
