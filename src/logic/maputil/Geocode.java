package logic.maputil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.lynden.gmapsfx.javascript.object.LatLong;
public class Geocode {
	private static Geocode instance = null;
	

	protected Geocode() {

	}

	public static synchronized Geocode getSingletonInstance() {
		if (Geocode.instance == null)
			Geocode.instance = new Geocode();		
		return instance;
	}
	private LatLong coordinates;

	public LatLong getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(LatLong coordinates) {
		System.out.println("COORDINATES :" + coordinates);
		this.coordinates = coordinates;
		System.out.println("COORDINATES EXIT");
		return;
	}


	public StringBuilder getConnection(String addr) {
		BufferedReader reader;
		String line;
		HttpURLConnection connection = null;
		final String protocol = "https://maps.googleapis.com/maps/api/geocode/json?address=";
		final String key = "&key=AIzaSyD_VEWJiOmLMpbqhldxdyEiDNj-FTyiv44";
		StringBuilder responseContent = new StringBuilder();
		String newUrl = makeRequest(addr, key, protocol);

		try {
			URL url = new URL(newUrl);
			connection = (HttpURLConnection) url.openConnection();

			//Request setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			int status = connection.getResponseCode();
			System.out.println(status);

			if(status > 200) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine())!= null) {
					responseContent.append(line);
				}
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine())!= null) {
					responseContent.append(line);
				}
				reader.close();
			}
			System.out.println("RESPONSECONTENT "+ responseContent.toString());

		}  catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return responseContent;

	}

	public static String makeRequest(String address,String key ,String protocol ) {
		StringBuilder urlFinal = new StringBuilder();
		try {
			urlFinal.append(protocol);
			urlFinal.append(URLEncoder.encode(address, "UTF-8"));
			urlFinal.append(key);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		urlFinal.append(key);
		return urlFinal.toString();
	}

	public void getLocation(String address) {

		StringBuilder str = this.getConnection(address);

		JSONObject location = null;
		LatLong coord = null;
		try {
			JSONParser parser = new JSONParser();
			String requestResult = str.toString();
			Object obj = parser.parse(requestResult);
			JSONObject jb = (JSONObject) obj;
			JSONArray array = (JSONArray) jb.get("results");
			JSONObject result = (JSONObject) array.get(0);
			JSONObject geometry = (JSONObject) result.get("geometry");
			location = (JSONObject) geometry.get("location");
			System.out.println("Result searcing:" + array.toString());
			System.out.println("geometry searcing:" + geometry.toString());
			System.out.println("array searcing:" + array.toString());
			System.out.println("Location:"+location.toString());
			double lng = (double)location.get("lng");
			double lat = (double)location.get("lat");
			System.out.println("lng " + lng+"lat"+lat);
			coord = new LatLong(lat, lng);
			System.out.println("SONO IN GEOCODE");
			setCoordinates(coord);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
}
