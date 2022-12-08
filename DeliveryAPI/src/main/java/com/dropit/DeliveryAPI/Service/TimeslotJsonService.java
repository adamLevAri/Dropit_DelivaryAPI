package com.dropit.DeliveryAPI.Service;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Async
public class TimeslotJsonService {
	
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public static CompletableFuture<Map<String, Object>> getTimesoltByJson(){
		
		try {
			InputStream inputStream = new FileInputStream("src/main/resources/static/courierAPI.json");
			TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {};
			
			return CompletableFuture.completedFuture(objectMapper.readValue(inputStream, typeReference));
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}

}
