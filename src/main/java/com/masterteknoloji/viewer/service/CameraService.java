package com.masterteknoloji.viewer.service;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.masterteknoloji.viewer.domain.Camera;
import com.masterteknoloji.viewer.domain.Line;
import com.masterteknoloji.viewer.util.Util;

@Service
public class CameraService {

	final String url = "rtsp://192.168.173.217:8085";
	final String url2 ="http://wmccpinetop.axiscam.net/mjpg/video.mjpg";
	final String murl2 = "C:\\Users\\ramazan\\Downloads\\bandicam_output.mp4";
	
	final String kbb1 = "C:\\Users\\ramazan\\Downloads\\Location1-EVENING-Part1-KALE_PASAJLAR_20210415170000_20210415171350_84587.mp4";
	final String kbb2 = "D:\\KBB\\location2\\Location2-EVENING-Part3-KARTAL+KAVŞAK_PASAJLAR_20210415174939_20210415180000_212255.mp4";
	final String kbb3 = "D:\\KBB\\location3\\Location3-EVENING-Part1-VALİLİK_PASAJLAR_20210415170000_20210415172522_84587.mp4";
	final String kbb4 = "D:\\KBB\\location3\\Location3-MORNING-valilik.mp4";
//	final String kbb5 = "D:\\KBB\\input_2_lines.webm";
	
	final String kbb5 = "https://canliyayin.kayseri.bel.tr/canli/2ad3b57f-8391-4a25-999b-d90b83bcba43.stream/chunklist_w248468715_tkYmx0b2tlbnN0YXJ0dGltZT0xNjIzODMyNzYwJmJsdG9rZW5lbmR0aW1lPTE2MjM4MzMwNjAmYmx0b2tlbkN1c3RvbVBhcmFtZXRlcj1zZWN1cmVkJmJsdG9rZW5oYXNoPUxRWHNyaUZla2lSbXBCTzdha2lUUVZMdFhuOFc2b18yR3dKSk0wbHRSYlU9.m3u8";
	
	List<Camera> cameraList = null;
	
	
	
	
	public List<Camera> getCameraList(){
		if(cameraList == null)
			cameraList = prepareCameraListForTwoRoadDay();
	
		return cameraList;
	}
	
public Line findLine(Long lineId) {
		
		for (Camera camera :  getCameraList()) {
			for (Line line : camera.getLineList()) {
//				System.out.println(line.getId()+ " "+lineId);
				if(line.getId()==lineId.longValue())
					return line;
			}
		}
		
		return null;
	}

	
	
public List<Camera> prepareCameraListForTwoRoadDay(){
		
		cameraList = new ArrayList<Camera>();
		Camera camera1 ;
		Camera camera2 ;
		Camera camera3 ;
		Camera camera4 ;


		camera1 = new Camera();
		camera2 = new Camera();
		camera3 = new Camera();
		camera4 = new Camera();

		Line line1 = new Line();
		line1.setStart(new Point(1094 , 445));
		line1.setEnd(new Point(651 , 422));
		line1.setId(3l);
		line1.setCamera(camera1);
		line1.setData(Util.getLineData(3l));
		line1.setReportRectangle(new Rectangle(1450, 50, 250, 150));
		camera1.getLineList().add(line1);
		camera1.setShow(true);
		camera1.setConnectionUrl(kbb5);
		line1.setColor(Color.CYAN);
		line1.setOrijinalColor(Color.CYAN);
		Polygon polygon1 = new Polygon();
		polygon1.addPoint(565 , 561);
		polygon1.addPoint(1166 , 591);
		polygon1.addPoint(1094 , 445);
		polygon1.addPoint(651 , 422);
		camera1.getPolygons().add(polygon1);
		
		Polygon polygon2 = new Polygon();
		polygon2.addPoint(653 , 416);
		polygon2.addPoint(1091 , 444);
		polygon2.addPoint(1037 , 368);
		polygon2.addPoint(703 , 345);
		camera1.getPolygons().add(polygon2);
		
		Line line2 = new Line();
		line2.setStart(new Point(331 ,337));
		line2.setEnd(new Point(654 ,381));
		line2.setId(4l);
		line2.setColor(Color.yellow);
		line2.setOrijinalColor(Color.yellow);
		line2.setCamera(camera2);
		line2.setData(Util.getLineData(4l));
		line2.setReportRectangle(new Rectangle(50, 50, 250, 150));
		camera1.getLineList().add(line2);
		
		Polygon polygon3 = new Polygon();
		polygon3.addPoint(604 ,431);
		polygon3.addPoint(301 ,377);
		polygon3.addPoint(489 ,300);
		polygon3.addPoint(692 ,327);
		camera1.getPolygons().add(polygon3);
		
		Polygon polygon4 = new Polygon();
		polygon4.addPoint(598 ,431);
		polygon4.addPoint(281 ,387);
		polygon4.addPoint(154 ,452);
		polygon4.addPoint(502 ,541);
		camera1.getPolygons().add(polygon4);
		
		
		
		Line line3 = new Line();
		line3.setStart(new Point(400, 480));
		line3.setEnd(new Point(500, 575));
		line3.setId(3l);
		line3.setCamera(camera3);
//		line3.setData(getDataForCamera());
		camera3.getLineList().add(line3);
		camera3.setConnectionUrl(kbb3);
		camera3.setShow(false);
		
		Line line4 = new Line();
		line4.setStart(new Point(700, 500));
		line4.setEnd(new Point(1000, 600));
		line4.setId(4l);
		line4.setCamera(camera4);
//		line4.setData(getDataForCamera());
		camera4.getLineList().add(line4);
		camera4.setConnectionUrl(kbb4);
		camera4.setShow(false);
		
		cameraList.add(camera1);
//		cameraList.add(camera2);
//		cameraList.add(camera3);
//		cameraList.add(camera4);

		return cameraList;		
	}

public List<Camera> prepareCameraListForNigth(){
	
	cameraList = new ArrayList<Camera>();
	Camera camera1 ;
	Camera camera2 ;
	Camera camera3 ;
	Camera camera4 ;


	camera1 = new Camera();
	camera2 = new Camera();
	camera3 = new Camera();
	camera4 = new Camera();

	Line line1 = new Line();
	line1.setStart(new Point(629, 455));
	line1.setEnd(new Point(876, 534));
	line1.setId(1l);
	line1.setCamera(camera1);
	line1.setData(Util.getCameraData(1l));
	camera1.getLineList().add(line1);
	camera1.setShow(true);
	camera1.setConnectionUrl(kbb1);
	Polygon polygon1 = new Polygon();
	polygon1.addPoint(366, 489);
	polygon1.addPoint(374, 537);
	polygon1.addPoint(651, 668);
	polygon1.addPoint(967, 675);
	polygon1.addPoint(1018, 570);
	polygon1.addPoint(579, 435);
	camera1.getPolygons().add(polygon1);
	
	Polygon polygon2 = new Polygon();
	polygon2.addPoint(598, 434);
	polygon2.addPoint(1026, 564);
	polygon2.addPoint(1047, 516);
	polygon2.addPoint(716, 402);
	camera1.getPolygons().add(polygon2);
	
	Line line2 = new Line();
	line2.setStart(new Point(245, 462));
	line2.setEnd(new Point(576,375));
	line2.setId(2l);
	line2.setCamera(camera2);
	line2.setData(Util.getCameraData(6l));
	camera2.getLineList().add(line2);
	camera2.setConnectionUrl(kbb5);
	camera2.setShow(false);
	Polygon polygon3 = new Polygon();
	polygon3.addPoint(209,368);
	polygon3.addPoint(459,321);
	polygon3.addPoint(846,443);
	polygon3.addPoint(293,586);
	camera2.getPolygons().add(polygon3);
	
	Polygon polygon4 = new Polygon();
	polygon4.addPoint(298,601);
	polygon4.addPoint(856,448);
	polygon4.addPoint(1230,579);
	polygon4.addPoint(1239,665);
	polygon4.addPoint(347,670);
	camera2.getPolygons().add(polygon4);
	
	
	
	Line line3 = new Line();
	line3.setStart(new Point(400, 480));
	line3.setEnd(new Point(500, 575));
	line3.setId(3l);
	line3.setCamera(camera3);
//	line3.setData(getDataForCamera());
	camera3.getLineList().add(line3);
	camera3.setConnectionUrl(kbb3);
	camera3.setShow(false);
	
	Line line4 = new Line();
	line4.setStart(new Point(700, 500));
	line4.setEnd(new Point(1000, 600));
	line4.setId(4l);
	line4.setCamera(camera4);
//	line4.setData(getDataForCamera());
	camera4.getLineList().add(line4);
	camera4.setConnectionUrl(kbb4);
	camera4.setShow(false);
	
	cameraList.add(camera1);
//	cameraList.add(camera2);
//	cameraList.add(camera3);
//	cameraList.add(camera4);

	return cameraList;		
}
	
	
	
}
