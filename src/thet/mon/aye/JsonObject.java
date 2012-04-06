package thet.mon.aye;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class JsonObject {
	//this is the local server ip address for server running on same host
	// localhost or 127.0.0.1 per android is its own emulator
//	String URL = "http://10.0.2.2:8080/jersey/rest/hello";
//	String URL = "http://www.csd.abdn.ac.uk/~bscharla/helloworld.xml";

	// for tesco this url gets you logged in and returns sessionkey to use 
	// in followup requests
	static String loginURL = "https://secure.techfortesco.com/groceryapi_b1/restservice.aspx?command=LOGIN&email=&password=&developerkey=unJODMpBjLJ45JhFEwyJ&applicationkey=7DB6C7F4EFB014B11CD5";
	static  String startURL = "http://www.techfortesco.com/groceryapi/RESTService.aspx?COMMAND=PRODUCTSEARCH&SESSIONKEY=";
    
	 static String result = "";
	 static String deviceId="xxxx";
	//static String deviceId = "HT08DPY02479";
	final static String tag = "your logcat tag: ";
	private static final String String = null;
   private static JSONObject jObject;

public static String getTescoDetails(String barcode) throws JSONException
{
	// JsonObject j = new JsonObject();
	String name= JsonObject.getTescoProductName(barcode);
	 double value= JsonObject.getTescoProductValue(barcode);
	 

	return name+value;
}
/*public static double getTescoTotal(String barcode) throws JSONException
{    
	double parsedvalue= JsonObject.getTescoProductValue(barcode);
	
	//int i= Integer.parseInt(parsedvalue);
	int total = 0;
	while(parsedvalue!=0)
	{
		total+=parsedvalue;
	}
  return total;	
}*/
	public static String getTescoProductName(String barcode) throws JSONException{
		    String key="empty";
		    key = login();
			String URL = startURL+key+"&SEARCHTEXT=" +barcode;
	    
	    	//InputStream is=null;
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpGet request = new HttpGet(URL);
	    	request.addHeader("deviceId", deviceId);
		ResponseHandler<String> handler = new BasicResponseHandler();
		
			try {
				result = httpclient.execute(request, handler);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 jObject= new JSONObject(result);
			 
			JSONArray productinfo= jObject.getJSONArray("Products");
		
		String name=null;
		
			for(int i =0;i<productinfo.length();i++)
			{  name=productinfo.getJSONObject(i).getString("Name").toString();
		 
		     }
			
			return name;
  	
			}
	
	public static double getTescoProductValue(String barcode) throws JSONException{
	    String key="empty";
	    key = login();
		String URL = startURL+key+"&SEARCHTEXT=" +barcode;
    
    	//InputStream is=null;
	 HttpClient httpclient = new DefaultHttpClient();
	 HttpGet request = new HttpGet(URL);
    	request.addHeader("deviceId", deviceId);
	ResponseHandler<String> handler = new BasicResponseHandler();
	
		try {
			result = httpclient.execute(request, handler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 jObject= new JSONObject(result);
		 
		JSONArray productinfo= jObject.getJSONArray("Products");
	double price=0;

		for(int i =0;i<productinfo.length();i++)
		{  
	       price=productinfo.getJSONObject(i).getDouble("Price");
	       
	     }
		
		return price;
	
		}
	
	private static String login() throws JSONException{
		 HttpClient httpclient = new DefaultHttpClient();
		    // 	HttpGet request = new HttpGet(URL+q);
		    	HttpGet request = new HttpGet(loginURL);
		    	request.addHeader("deviceId", deviceId);
		    	ResponseHandler<String> handler = new BasicResponseHandler();
		    	try {
		    		result = httpclient.execute(request, handler);
		    	// shall i create new obj or not??????????????????????
		    		JSONObject objectForGettingSessionKey = (JSONObject) new JSONTokener(result).nextValue();
			   	    result = objectForGettingSessionKey.getString("SessionKey");
                  
		    	}catch (ClientProtocolException e) {
		    		e.printStackTrace();
		    	}catch(IOException e) {
		    		e.printStackTrace();
		    	}
		    	httpclient.getConnectionManager().shutdown();
		    	Log.i(tag, result);

		    	return result;
	}
}