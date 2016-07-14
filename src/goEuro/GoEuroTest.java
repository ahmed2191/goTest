package goEuro;
import java.net.*;
import java.io.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class GoEuroTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String query="Berlin";
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
		        PrintWriter writer = new PrintWriter(query+".csv", "UTF-16");
		        writer.println(entry);
		        writer.close();
	        }
        }
        in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("Sorry there were a mistake in the URL please try again for more infromation check the following trace:");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Sorry there were a mistake in the URL please try again for more infromation check the following trace:");
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Sorry there were a mistake in the recived data (JSOB object) please try again for more infromation check the following trace:");
			e.printStackTrace();
		}
    }

}
