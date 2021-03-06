/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2009-2018 Caprica Software Limited.
 */

package com.masterteknoloji.viewer.player;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import javax.swing.JPanel;

import org.springframework.util.StringUtils;

import com.masterteknoloji.viewer.domain.Camera;
import com.masterteknoloji.viewer.domain.Line;
import com.masterteknoloji.viewer.domain.dto.VideoRecordQueryVM;
import com.masterteknoloji.viewer.util.Util;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.direct.BufferFormat;
import uk.co.caprica.vlcj.player.direct.BufferFormatCallback;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.RenderCallbackAdapter;
import uk.co.caprica.vlcj.player.direct.format.RV32BufferFormat;

/**
 * This simple test player shows how to get direct access to the video frame data.
 * <p>
 * This implementation uses the new (1.1.1) libvlc video call-backs function.
 * <p>
 * Since the video frame data is made available, the Java call-back may modify the contents of the
 * frame if required.
 * <p>
 * The frame data may also be rendered into components such as an OpenGL texture.
 */
public class ViewerOverlay2 {
	
//	int carIndex=0;

	Camera camera ;
	
    // The size does NOT need to match the mediaPlayer size - it's the size that
    // the media will be scaled to
    // Matching the native size will be faster of course
    private final int width;

    private final int height;

    // private final int width = 1280;
    // private final int height = 720;

    /**
     * Image to render the video frame data.
     */
    private final BufferedImage image;

    private MediaPlayerFactory factory;

    private final DirectMediaPlayer mediaPlayer;

    public JPanel getImagePane() {
		return imagePane;
	}

	public DirectMediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	private final ImagePane imagePane;

    public ViewerOverlay2(Camera camera ,int width, int height, String[] args) throws InterruptedException, InvocationTargetException {
        image = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width, height);
        image.setAccelerationPriority(1.0f);
        
        this.width = width;
        this.height = height;
        this.camera = camera;

        imagePane = new ImagePane(image);
        imagePane.setSize(width, height);
        imagePane.setMinimumSize(new Dimension(width, height));
        imagePane.setPreferredSize(new Dimension(width, height));
        
        factory = new MediaPlayerFactory(args);
        mediaPlayer = factory.newDirectMediaPlayer(new TestBufferFormatCallback(), new TestRenderCallback());
//        mediaPlayer.
//        processdata();
    }

    @SuppressWarnings("serial")
    private final class ImagePane extends JPanel {

        private final BufferedImage image;

        private final Font font = new Font("Sansserif", Font.BOLD, 36);

        public ImagePane(BufferedImage image) {
            this.image = image;
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.drawImage(image, null, 0, 0);
            g2.setColor(Color.red);
           g2.setComposite(AlphaComposite.SrcOver.derive(0.5f));

          if(camera==null)
        	  return;
          
//          Polygon polygon1 = new Polygon();
//          addPointToPolygon(polygon1, 366, 489, width, height);
//          addPointToPolygon(polygon1, 374, 537, width, height);
//          addPointToPolygon(polygon1, 651, 668, width, height);
//          addPointToPolygon(polygon1, 967, 675, width, height);
//          addPointToPolygon(polygon1, 1018, 570, width, height);
//          addPointToPolygon(polygon1, 579, 435, width, height);
//          //g2.drawPolygon(polygon1);
  
//      	Polygon polygon2 = new Polygon();
//      	addPointToPolygon(polygon2, 598, 434, width, height);
//      	addPointToPolygon(polygon2, 1026, 564, width, height);
//      	addPointToPolygon(polygon2, 1047, 516, width, height);
//      	addPointToPolygon(polygon2, 716, 402, width, height);
        
//        g2.drawPolygon(polygon2);
          
        for (Polygon polygon: camera.getPolygons()) {
        	Polygon temp = projectPolygon(polygon, width, height);
        	//g2.drawPolygon(temp);
		}  
          
          
  		for (Iterator iterator = camera.getLineList().iterator(); iterator.hasNext();) {
			Line line = (Line) iterator.next();
			projectLine(line);
			g.setColor(line.getColor());

			Polygon polygon3 = new Polygon();
			polygon3.addPoint((int) line.getProjectedStart().getX(), (int) line.getProjectedStart().getY());
			polygon3.addPoint((int) line.getProjectedStart().getX(), (int) line.getProjectedStart().getY()-20);

			polygon3.addPoint((int) line.getProjectedEnd().getX() , (int) line.getProjectedEnd().getY()-20);
			polygon3.addPoint((int) line.getProjectedEnd().getX(), (int) line.getProjectedEnd().getY());
			
			g2.fillPolygon(polygon3);

		}

		Font myFont = new Font ("Courier New", 1, 13);
		g2.setFont (myFont);
		g2.setColor(Color.BLACK);
		g2.setComposite(AlphaComposite.SrcOver.derive(1f));
		for (Iterator iterator = camera.getLineList().iterator(); iterator.hasNext();) {
			Line line = (Line) iterator.next();
			int countX = ((int)line.getProjectedStart().getX()  +(int)line.getProjectedEnd().getX())/2;
			int countY = ((int)line.getProjectedStart().getY() -10 +(int)line.getProjectedEnd().getY())/2;
			g2.drawString(line.getCount().toString(), countX, countY);
			
		}
         
		
		Font myFont2 = new Font ("Courier New", 1, 24);
		g2.setFont (myFont2);
		
		g2.setComposite(AlphaComposite.SrcOver.derive(0.5f));
		for (Iterator iterator = camera.getLineList().iterator(); iterator.hasNext();) {
			Line line = (Line) iterator.next();
			g2.setColor(line.getColor());
			int countX = ((int)line.getProjectedStart().getX()-20);
			int countY = ((int)line.getProjectedStart().getY()-20);
			
			g2.fillOval(countX -10, countY -20, 30, 30);
			g2.setColor(Color.BLACK);
			g2.drawString(line.getId().toString(), countX, countY);
			
		}
		for (Iterator iterator = camera.getLineList().iterator(); iterator.hasNext();) {
			Line line = (Line) iterator.next();
			drawReport(g2,line);
		}
        }
    }

    
    public void drawReport( Graphics2D g2,Line line) {
    	g2.setColor(line.getColor());
    	g2.setComposite(AlphaComposite.SrcOver.derive(0.8f));
    	g2.fill(line.getReportRectangle());
    	Font myFont2 = new Font ("Courier New", 1, 20);
		g2.setFont (myFont2);
		g2.setColor(Color.black);
		int index=0;
		int x=(int)line.getReportRectangle().getX();
		int y = (int)line.getReportRectangle().getY()+20;
    	for (Iterator iterator = line.getLastDatas().iterator(); iterator.hasNext();) {
    		VideoRecordQueryVM type = (VideoRecordQueryVM) iterator.next();
    		g2.drawString(prepareReportMessage(type) , x, y+(index*20));
    		index++;
		}
    }
    
    public String prepareReportMessage(VideoRecordQueryVM type) {
    	String result ="";
    	if(!StringUtils.isEmpty(type.getDirection())) {
    		result = type.getDirection()+","+type.getVehicleType();
    	}else {
    		result = "type:"+type.getVehicleType()+ " - speed:"+type.getSpeed() ;
    	}
    	
    	return result;
    }
    
    private final class TestRenderCallback extends RenderCallbackAdapter {

        public TestRenderCallback() {
            super(((DataBufferInt) image.getRaster().getDataBuffer()).getData());
        }

        @Override
        public void onDisplay(DirectMediaPlayer mediaPlayer, int[] data) {
            imagePane.repaint();
        }
    }

    private final class TestBufferFormatCallback implements BufferFormatCallback {
        @Override
        public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
            return new RV32BufferFormat(width, height);
        }

    }
   
   public void projectLine(Line line) {
    	int start_X = (int)line.getStart().getX();
		int start_Y = (int)line.getStart().getY();
		int end_X = (int)line.getEnd().getX();
		int end_Y = (int)line.getEnd().getY();
		
		int w = width;
		int h = height;

//		System.out.println(videoSurface.getHeight()+ ":"+videoSurface.getWidth());

		Dimension videoDimension = new Dimension(1280, 720);
		
		int interpolated_start_x = (int) (1.0f * start_X * w / videoDimension.width);
		int interpolated_start_y = (int) (1.0f * start_Y * h / videoDimension.height);
		int interpolated_end_x = (int) (1.0f * end_X * w / videoDimension.width);
		int interpolated_end_y = (int) (1.0f * end_Y * h / videoDimension.height);
		
		float aspectRatio = 1.0f * videoDimension.width / videoDimension.height;
		float surfaceRatio = 1.0f * w / h;
		
		//Determine black borders
		if(surfaceRatio > aspectRatio) {
			//border left/right -> change x / width
			
			int actualWidth = (int) (aspectRatio * h);				
			int borderSize = w - actualWidth; //left and right
			
			//recalculate values with actual width and add half of border size
			interpolated_start_x = (int) (1.0f * start_X * actualWidth / videoDimension.width) + borderSize/2;
			interpolated_end_x = (int) (1.0f * end_X * actualWidth / videoDimension.width);
			
		} else {
			//border up/down -> change y / height
			
			int actualHeight = (int) (w / aspectRatio);		
			int borderSize = h - actualHeight; //top and down
			
			//recalculate values with actual height and add half of border size
			interpolated_start_y = (int) (1.0f * start_Y * actualHeight / videoDimension.height) + borderSize/2;
			interpolated_end_y = (int) (1.0f * end_Y * actualHeight / videoDimension.height);
		}

//		g2.drawRect(interpolated_x, interpolated_y, interpolated_w, interpolated_h);
//      g2.fillRect(interpolated_x, interpolated_y, interpolated_w, interpolated_h);

		line.getProjectedStart().setLocation(interpolated_start_x, interpolated_start_y);
		line.getProjectedEnd().setLocation(interpolated_end_x, interpolated_end_y);
    }

  public void addPointToPolygon(Polygon polygon,int x,int y,int width,int height) {
	  Point point1 = Util.project(x,y, width, height);
	  polygon.addPoint(point1.x,point1.y);	  
  }
   
  public Polygon projectPolygon(Polygon polygon,int width,int height) {
	 
	  Polygon result = new Polygon();
	  for (int i = 0; i < polygon.npoints; i++) {
		  Point point1 = Util.project(polygon.xpoints[i],polygon.ypoints[i], width, height);
		  result.addPoint(point1.x,point1.y);	  
	  
	  }
	  
	  return result;
	  }
   
 public void play() {
	 if(camera!=null)
		 mediaPlayer.playMedia(camera.getConnectionUrl());
 }

 public void prepare() {
	 if(camera!=null)
		 mediaPlayer.prepareMedia(camera.getConnectionUrl()); 
 }
 
public Camera getCamera() {
	return camera;
}

public void setCamera(Camera camera) {
	this.camera = camera;
}

public MediaPlayerFactory getFactory() {
	return factory;
}
}