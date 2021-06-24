package com.masterteknoloji.viewer.service;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.masterteknoloji.viewer.domain.Camera;
import com.masterteknoloji.viewer.domain.Line;
import com.masterteknoloji.viewer.domain.dto.VideoRecordQueryVM;
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
    final String kbb5 = "D:\\KBB\\input_2_lines.webm";
	
	//final String kbb5 = "https://canliyayin.kayseri.bel.tr/canli/2ad3b57f-8391-4a25-999b-d90b83bcba43.stream/chunklist_w248468715_tkYmx0b2tlbnN0YXJ0dGltZT0xNjIzODMyNzYwJmJsdG9rZW5lbmR0aW1lPTE2MjM4MzMwNjAmYmx0b2tlbkN1c3RvbVBhcmFtZXRlcj1zZWN1cmVkJmJsdG9rZW5oYXNoPUxRWHNyaUZla2lSbXBCTzdha2lUUVZMdFhuOFc2b18yR3dKSk0wbHRSYlU9.m3u8";
	
    
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

public List<Camera> prepareCameraListIntersectionDay(Long videoId,String file){
	
	cameraList = new ArrayList<Camera>();
	Camera camera1 ;
	Camera camera2 ;
	Camera camera3 ;
	Camera camera4 ;


	camera1 = new Camera();
	camera2 = new Camera();
	camera3 = new Camera();
	camera4 = new Camera();

	camera1.setShow(true);
	camera1.setConnectionUrl(file);
	
	Line line1 = new Line();
	line1.setStart(new Point(443 , 686));
	line1.setEnd(new Point(1066,  422));
	line1.setId(0l);
	line1.setCamera(camera1);
	line1.getData().addAll(Util.getDirectionDataForIntersections(videoId,0l));
	line1.getData().addAll(Util.getDirectionDataForIntersections(videoId,7l));
	line1.getData().addAll(Util.getDirectionDataForIntersections(videoId,5l));
	line1.setReportRectangle(new Rectangle(1650, 550, 250, 150));
	line1.setColor(Color.CYAN);
	line1.setOrijinalColor(Color.CYAN);
	prepareLineIds(line1,0l);
	camera1.getLineList().add(line1);
	
	Line line2 = new Line();
	line2.setStart(new Point(847 , 228));
	line2.setEnd(new Point(1060 , 259));
	line2.setId(1l);
	line2.setColor(Color.yellow);
	line2.setOrijinalColor(Color.yellow);
	line2.setCamera(camera1);
	line2.getData().addAll(Util.getDirectionDataForIntersections(videoId,1l));
	line2.getData().addAll(Util.getDirectionDataForIntersections(videoId,9l));
	line2.getData().addAll(Util.getDirectionDataForIntersections(videoId,8l));
	line2.setReportRectangle(new Rectangle(1600, 200, 250, 150));
	prepareLineIds(line2,1l);
	camera1.getLineList().add(line2);
	
	Line line3 = new Line();
	line3.setStart(new Point(469, 240));
	line3.setEnd(new Point(551 ,205));
	line3.setId(3l);
	line3.setColor(Color.green);
	line3.setOrijinalColor(Color.green);
	line3.setCamera(camera1);
	line3.getData().addAll(Util.getDirectionDataForIntersections(videoId,3l));
	line3.getData().addAll(Util.getDirectionDataForIntersections(videoId,10l));
	line3.getData().addAll(Util.getDirectionDataForIntersections(videoId,2l));
	line3.setReportRectangle(new Rectangle(800, 125, 250, 150));
	prepareLineIds(line3,3l);
	camera1.getLineList().add(line3);
	
	Line line4 = new Line();
	line4.setStart(new Point(164, 338));
	line4.setEnd(new Point(224, 461));
	line4.setId(4l);
	line4.setColor(Color.orange);
	line4.setOrijinalColor(Color.orange);
	line4.setCamera(camera1);
	line4.getData().addAll(Util.getDirectionDataForIntersections(videoId,4l));
	line4.getData().addAll(Util.getDirectionDataForIntersections(videoId,6l));
	line4.getData().addAll(Util.getDirectionDataForIntersections(videoId,11l));
	line4.setReportRectangle(new Rectangle(50, 650, 250, 150));
	prepareLineIds(line4,4l);
	camera1.getLineList().add(line4);
	
	//region_1_IN
	Polygon polygon1in = new Polygon();
	polygon1in.addPoint(117, 301);
	polygon1in.addPoint(324, 244);
	polygon1in.addPoint(414, 245);
	polygon1in.addPoint(483, 239);
	polygon1in.addPoint(497, 238);
	polygon1in.addPoint(206, 342);
	polygon1in.addPoint(177, 348);
	
	
	//region_1_OUT
	Polygon polygon1out = new Polygon();
	polygon1out.addPoint(443 , 686);
	polygon1out.addPoint(468 , 717);
	polygon1out.addPoint(493 , 716);
	polygon1out.addPoint(512 , 715);
	polygon1out.addPoint(1066,  447);
	polygon1out.addPoint(1027 , 422);
	
	
	//region_2_IN
	Polygon polygon2in = new Polygon();
	polygon2in.addPoint(117, 301);
	polygon2in.addPoint(324 ,244);
	polygon2in.addPoint(414 ,245);
	polygon2in.addPoint(483 ,239);
	polygon2in.addPoint(497 ,238);
	polygon2in.addPoint(206 ,342);
	polygon2in.addPoint(177 ,348);
	
	
	//region_2_OUT
	Polygon polygon2out = new Polygon();
	polygon2out.addPoint(889 , 223);
	polygon2out.addPoint(865 , 226);
	polygon2out.addPoint(853 , 227);
	polygon2out.addPoint(847 , 228);
	polygon2out.addPoint(1060 , 259);
	polygon2out.addPoint(1039 , 240);
	polygon2out.addPoint(1030,  234);
	
	//region_3_IN
		Polygon polygon3in = new Polygon();
		polygon3in.addPoint(117, 301);
		polygon3in.addPoint(324, 244);
		polygon3in.addPoint(414, 245);
		polygon3in.addPoint(483, 239);
		polygon3in.addPoint(497, 238);
		polygon3in.addPoint(206, 342);
		polygon3in.addPoint(177, 348);
		
		//region_3_OUT
		Polygon polygon3out = new Polygon();
		polygon3out.addPoint(470, 228);
		polygon3out.addPoint(526, 205);
		polygon3out.addPoint(551, 207);
		polygon3out.addPoint(469, 240);
		polygon3out.addPoint(461, 237);
		

		//region_4_IN
		Polygon polygon4in = new Polygon();
		polygon4in.addPoint(1233 , 325);
		polygon4in.addPoint(1210 , 476);
		polygon4in.addPoint(969  ,392);
		polygon4in.addPoint(1018 , 261);
		polygon4in.addPoint(1138 , 281);
		
		//region_4_OUT
		Polygon polygon4out = new Polygon();
		polygon4out.addPoint(470, 228);
		polygon4out.addPoint(526, 205);
		polygon4out.addPoint(551, 207);
		polygon4out.addPoint(469, 240);
		polygon4out.addPoint(461, 237);
	
		//region_5_IN
				Polygon polygon5in = new Polygon();
				polygon5in.addPoint(1233,  325);
				polygon5in.addPoint(1210,  476);
				polygon5in.addPoint( 969,  392);
				polygon5in.addPoint(1018,  261);
				polygon5in.addPoint(1138,  281);
				
				//region_5_OUT
				Polygon polygon5out = new Polygon();
				polygon5out.addPoint(106, 371);
				polygon5out.addPoint(141, 496);
				polygon5out.addPoint(224, 461);
				polygon5out.addPoint(164, 338);
				polygon5out.addPoint( 97, 357);
		
				//region_6_IN
				Polygon polygon6in = new Polygon();
				polygon6in.addPoint(1233 , 325);
				polygon6in.addPoint(1210 , 476);
				polygon6in.addPoint( 969 , 392);
				polygon6in.addPoint(1018 , 261);
				polygon6in.addPoint(1138 , 281);
				
				//region_6_OUT
				Polygon polygon6out = new Polygon();
				polygon6out.addPoint(443,  686);
				polygon6out.addPoint(468,  717);
				polygon6out.addPoint(493,  716);
				polygon6out.addPoint(512,  715);
				polygon6out.addPoint( 1066,  447);
				polygon6out.addPoint( 1027,  422);
				
				//region_7_IN
				Polygon polygon7in = new Polygon();
				polygon7in.addPoint(714, 193);
				polygon7in.addPoint(854, 208);
				polygon7in.addPoint(841, 221);
				polygon7in.addPoint(841, 233);
				polygon7in.addPoint(842, 236);
				polygon7in.addPoint(795, 232);
				polygon7in.addPoint(747, 227);
				polygon7in.addPoint(685, 222);
				polygon7in.addPoint(621, 216);
				
				//region_7_OUT
				Polygon polygon7out = new Polygon();
				polygon7out.addPoint(106, 371);
				polygon7out.addPoint(141, 496);
				polygon7out.addPoint(224, 461);
				polygon7out.addPoint(164, 338);
				polygon7out.addPoint(97, 357);
	
				//region_8_IN
				Polygon polygon8in = new Polygon();
				polygon8in.addPoint(714, 193);
				polygon8in.addPoint(854, 208);
				polygon8in.addPoint(841, 221);
				polygon8in.addPoint(841, 233);
				polygon8in.addPoint(842, 236);
				polygon8in.addPoint(795, 232);
				polygon8in.addPoint(747, 227);
				polygon8in.addPoint(685, 222);
				polygon8in.addPoint(621, 216);
				
				//region_8_OUT
				Polygon polygon8out = new Polygon();
				polygon8out.addPoint(443,  686);
				polygon8out.addPoint(468,  717);
				polygon8out.addPoint(493,  716);
				polygon8out.addPoint(512,  715);
				polygon8out.addPoint(1066,  447);
				polygon8out.addPoint(1027,  422);
	
				//region_9_IN
				Polygon polygon9in = new Polygon();
				polygon9in.addPoint(714, 193);
				polygon9in.addPoint(854, 208);
				polygon9in.addPoint(841, 221);
				polygon9in.addPoint(841, 233);
				polygon9in.addPoint(842, 236);
				polygon9in.addPoint(795, 232);
				polygon9in.addPoint(747, 227);
				polygon9in.addPoint(685, 222);
				polygon9in.addPoint(621, 216);
				
				//region_9_OUT
				Polygon polygon9out = new Polygon();
				polygon9out.addPoint(889,  223);
				polygon9out.addPoint(865,  226);
				polygon9out.addPoint(853,  227);
				polygon9out.addPoint(847,  228);
				polygon9out.addPoint(1060,  259);
				polygon9out.addPoint(1039,  240);
				polygon9out.addPoint(1030 , 234);
	
				//region_10_IN
				Polygon polygon10in = new Polygon();
				polygon10in.addPoint(99 ,568);
				polygon10in.addPoint(131, 716);
				polygon10in.addPoint(451, 719);
				polygon10in.addPoint(289, 498);
				
				//region_10_OUT
				Polygon polygon10out = new Polygon();
				polygon10out.addPoint(889 , 223);
				polygon10out.addPoint(865 , 226);
				polygon10out.addPoint(853 , 227);
				polygon10out.addPoint(847 , 228);
				polygon10out.addPoint(1060 , 259);
				polygon10out.addPoint(1039,  240);
				polygon10out.addPoint(1030,  234);
				

				//region_11_IN
				Polygon polygon11in = new Polygon();
				polygon11in.addPoint(99, 568);
				polygon11in.addPoint(131, 716);
				polygon11in.addPoint(451, 719);
				polygon11in.addPoint(289, 498);
				
				//region_11_OUT
				Polygon polygon11out = new Polygon();
				polygon11out.addPoint(470, 228);
				polygon11out.addPoint(526, 205);
				polygon11out.addPoint(551, 207);
				polygon11out.addPoint(469, 240);
				polygon11out.addPoint(461, 237);
	
				//region_12_IN
				Polygon polygon12in = new Polygon();
				polygon12in.addPoint(99, 568);
				polygon12in.addPoint(131, 716);
				polygon12in.addPoint(451, 719);
				polygon12in.addPoint(289, 498);
				
				//region_12_OUT
				Polygon polygon12out = new Polygon();
				polygon12out.addPoint(106 ,371);
				polygon12out.addPoint(141 ,496);
				polygon12out.addPoint(224 ,461);
				polygon12out.addPoint(164 ,338);
				polygon12out.addPoint(97 ,357);
	
//				camera1.getPolygons().add(polygon1in);
//				camera1.getPolygons().add(polygon1out);
//				camera1.getPolygons().add(polygon2in);
//				camera1.getPolygons().add(polygon2out);
//				camera1.getPolygons().add(polygon3in);
//				camera1.getPolygons().add(polygon3out);
//				camera1.getPolygons().add(polygon4in);
//				camera1.getPolygons().add(polygon4out);
				camera1.getPolygons().add(polygon5in);
				camera1.getPolygons().add(polygon5out);
//				camera1.getPolygons().add(polygon6in);
//				camera1.getPolygons().add(polygon6out);
//				camera1.getPolygons().add(polygon7in);
//				camera1.getPolygons().add(polygon7out);
//				camera1.getPolygons().add(polygon8in);
//				camera1.getPolygons().add(polygon8out);
//				camera1.getPolygons().add(polygon9in);
//				camera1.getPolygons().add(polygon9out);
//				camera1.getPolygons().add(polygon10in);
//				camera1.getPolygons().add(polygon10out);
//				camera1.getPolygons().add(polygon11in);
//				camera1.getPolygons().add(polygon11out);
//				camera1.getPolygons().add(polygon12in);
//				camera1.getPolygons().add(polygon12out);
				
	cameraList.add(camera1);

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
	
	public void prepareLineIds(Line line,Long defaultID) {
		for (Iterator iterator = line.getData().iterator(); iterator.hasNext();) {
			VideoRecordQueryVM temp = (VideoRecordQueryVM) iterator.next();
			temp.setLineId(defaultID);
			
		}
	}
	
}
