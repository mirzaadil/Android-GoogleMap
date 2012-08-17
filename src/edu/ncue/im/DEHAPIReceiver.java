package edu.ncue.im;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import android.app.ListActivity;
//import android.os.Bundle;
import android.util.Log;
//import android.widget.SimpleAdapter;
/*
 * �զs��DEP API POI Request
 * �ǥXRequest �è��oJSON�^��
 * �̫�B�z���@��ArrayList
 */

public class DEHAPIReceiver{
	final static String POI_ID = "id", POI_TiTle = "title", POI_DISTANCE = "distance", POI_LATITUDE ="latitude", POI_LONGTITUE = "longitude", POI_DESCRIPTION = "description";
	String formatted_result;
	String request_URL;
	private ArrayList<Map<String, String>> soilist;
	
	protected JSONObject jsonObjcet;
	protected JSONArray jsonList;
	public DEHAPIReceiver(Double lat,Double lng,float dist){
		request_URL ="http://deh.csie.ncku.edu.tw/dehencode/json/nearbyPOIs?lat="+lat+"&lng="+lng+"&dist="+dist;
		soilist = new ArrayList<Map<String,String>>();
		try{
			formatted_result = this.sentHttpRequest(request_URL);
			soilist = parseJson(formatted_result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
			
	private ArrayList<Map<String, String>> parseJson(String str)throws JSONException{
				ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
				if(formatted_result!=null){
					//formatted_result = purge(formatted_result);
					jsonObjcet = new JSONObject(str);
					JSONArray soilist = jsonObjcet.getJSONArray("results");
					
					
					for(int i=0;i<soilist.length();i++){
						JSONObject temp = soilist.getJSONObject(i);
						
						//add hashmap to ArrayList
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("POI_id", temp.getString("POI_id"));
						map.put("POI_title", temp.getString("POI_title"));
						map.put("distance", temp.getString("distance"));
						map.put("latitude", temp.getString("latitude"));
						map.put("longitude", temp.getString("longitude"));
						map.put("POI_description",temp.getString("POI_description"));
						map.put("POI_year", temp.getString("year"));
						JSONObject picJson = temp.getJSONObject("PICs");
						map.put("pic_Count",picJson.getString("count"));
						if(picJson.getInt("count") != 0){
						
							JSONArray pics = picJson.getJSONArray("pic");
							String picsSumURL = new String();
							for (int j=0;j<picJson.getInt("count");j++){
								JSONObject jsonPic = pics.getJSONObject(j);
								picsSumURL += jsonPic.getString("url")+";";
								
								}
							map.put("PICsURL", picsSumURL);
							Log.d("jsonURL",map.get("PICsURL"));
						}					
						list.add(map);
						Log.d("json","Title:"+temp.getString("POI_title") );
				        Log.d("json","id:"+temp.getString("POI_id") );
				        Log.d("json","longitude:"+temp.getString("longitude"));
				        Log.d("json","latitude:"+temp.getString("latitude"));
				        Log.d("json","distance:"+temp.getString("distance") );
				        Log.d("json",map.get("pic_Count"));
				        Log.d("json","year:"+temp.getString("year"));
				        
					}
						
					}
				else
					Log.d("mine","Null List");
					return list;
				}
	/*private String purge(String str){
	    str.replace("\t", "");
	    str.replace("\n", "");
	    return str;
	    }*/
			
	private String sentHttpRequest(String url) throws Exception{
		BufferedReader in = null;
		try{
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			Log.d("json", url);
			HttpResponse response = client.execute(request);
			
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			
			while((line = in.readLine())!= null){
				sb.append(line+NL);
			}
			in.close();
			
			String result = sb.toString();
			return result;
		}catch(Exception e){
			
			e.getMessage();
			e.printStackTrace();
			
			return null;
		}
	}
	
	public ArrayList<Map<String, String>> getsoilist(){
		return soilist;
	}
}
