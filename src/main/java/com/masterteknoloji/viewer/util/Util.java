package com.masterteknoloji.viewer.util;

import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masterteknoloji.viewer.domain.dto.VideoRecordQueryVM;

public class Util {

	public static List<VideoRecordQueryVM> getCameraData() {
		List<VideoRecordQueryVM> result = new ArrayList<VideoRecordQueryVM>();
		
		
		try {
			URL url = new URL("http://localhost:8080/api/video-records/getAllData/1");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int status = con.getResponseCode();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
					
			System.out.println(content.toString());
			ObjectMapper mapper = new ObjectMapper();
	        mapper.findAndRegisterModules();
	        result = mapper.readValue(content.toString(), new TypeReference<List<VideoRecordQueryVM>>() { });

//	  	    for (VideoRecordQueryVM videoRecordQueryVM : asList) {
//	  	    	result.add(videoRecordQueryVM.getDuration());
//			}
	  	    
			System.out.println("bitti");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return result;
	}
	
	public static List<Long>  getRandomDataForCamera() {
		Random rand = new Random();
		List<Long> result = new ArrayList<Long>();
		for (int i = 0; i < 10; i++) {
			int rand_int1 = rand.nextInt(10000);
			result.add(new Long(rand_int1+1000));
		}
		
		Collections.sort(result);
		return result;
	}
	
	public static Point project(int x, int y,int w,int h) {
		
		Dimension videoDimension = new Dimension(1280, 720);
		
		int interpolated_x = (int) (1.0f * x * w / videoDimension.width);
		int interpolated_y = (int) (1.0f * y * h / videoDimension.height);
		
		float aspectRatio = 1.0f * videoDimension.width / videoDimension.height;
		float surfaceRatio = 1.0f * w / h;
		
		
		if(surfaceRatio > aspectRatio) {
			//border left/right -> change x / width
			
			int actualWidth = (int) (aspectRatio * h);				
			int borderSize = w - actualWidth; //left and right
			
			//recalculate values with actual width and add half of border size
			interpolated_x = (int) (1.0f * x * actualWidth / videoDimension.width) + borderSize/2;
			
		} else {
			//border up/down -> change y / height
			
			int actualHeight = (int) (w / aspectRatio);		
			int borderSize = h - actualHeight; //top and down
			
			//recalculate values with actual height and add half of border size
			interpolated_y = (int) (1.0f * y * actualHeight / videoDimension.height) + borderSize/2;
		}
		
		return new Point(interpolated_x, interpolated_y);
	}
	
	
}
