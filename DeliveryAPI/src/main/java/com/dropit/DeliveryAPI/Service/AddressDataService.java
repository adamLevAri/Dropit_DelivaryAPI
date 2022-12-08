package com.dropit.DeliveryAPI.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AddressDataService {
	
	private static HttpURLConnection connection;
	
	public Map<String, Object> getAddressByTerm(String searchTerm) {
		
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		Map<String, String> paramsMap = new HashMap<>();
		Map<String, Object> resMap = new HashMap<>();
		
		paramsMap.put("text", searchTerm);
		paramsMap.put("apiKey", "c43dce0e5e3c4505b61ee7adcb1d760e");
		
		try {
			
			URL url = new URL("https://api.geoapify.com/v1/geocode/search?" + getParamsString(paramsMap));
			connection = (HttpURLConnection) url.openConnection();
			
			connection.setRequestMethod("GET");
			
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			
			int status = connection.getResponseCode();
			
			if(status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			resMap = convertJsonToMap(responseContent.toString());
			//System.out.println(responseContent.toString());
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resMap;
		
	}
	
	private Map<String, Object> convertJsonToMap(String json) {

        Map<String, Object> map = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try {

            // convert JSON string to Map
            map = (Map<String, Object>) mapper.readValue(json,new TypeReference<HashMap<String, Object>>() {}).get("query");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
	
	 private static String getParamsString(Map<String, String> params) 
		      throws UnsupportedEncodingException{
		        StringBuilder result = new StringBuilder();

		        for (Map.Entry<String, String> entry : params.entrySet()) {
		          result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
		          result.append("=");
		          result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		          result.append("&");
		        }

		        String resultString = result.toString();
		        return resultString.length() > 0
		          ? resultString.substring(0, resultString.length() - 1)
		          : resultString;
		    }

}
