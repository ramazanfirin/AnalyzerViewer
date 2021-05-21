package com.masterteknoloji.viewer;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.masterteknoloji.viewer.domain.Camera;
import com.masterteknoloji.viewer.player.ViewerOverlay;
import com.masterteknoloji.viewer.service.CameraService;
import com.masterteknoloji.viewer.service.DataProcessManager;

/**
 * Created by Administrator on 2017/12/7.
 */
@SpringBootApplication
@ComponentScan("com")
public class ViewerMultiple extends JFrame{


	Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
	int viewWitdh = (int)DimMax.getWidth()/2;
	int viewHeight = (int)DimMax.getHeight()/2;
	int viewCount = 4;
	JPanel jPanel = new JPanel();
	
	List<ViewerOverlay> overlayList = new ArrayList<ViewerOverlay>();
	
	@Autowired
	CameraService cameraService;
	
	@Autowired
	DataProcessManager dataProcessManager;
	
	
	List<Camera> cameraList = new ArrayList<Camera>();
	
	
    
    private  void createAndShowGUI(){
    
    	setSize(1920, 1080);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jPanel.setLayout(new GridLayout(2, 2));
		setContentPane(jPanel);
		
		cameraList  = cameraService.getCameraList();
		System.out.println(cameraList.size());
		
		try {
			prepareOverlays(viewCount);
			play(cameraList);
//			dataProcessManager.processdata(cameraList);
			dataProcessManager.processDataByMQ(cameraList);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }

    
    public void prepareOverlays(int viewCount) throws InvocationTargetException, InterruptedException {
    	for (int i = 0; i < viewCount; i++) {
    		ViewerOverlay directTestPlayer = new ViewerOverlay(null,viewWitdh, viewHeight, null);
			JPanel imagePane = directTestPlayer.getImagePane();
			jPanel.add(imagePane);
			overlayList.add(directTestPlayer);
    	}
    	    	
    }
    
    public void play(List<Camera> cameraList) throws InvocationTargetException, InterruptedException {
    	for (int i = 0; i < cameraList.size(); i++) {
			Camera camera = cameraList.get(i);
			if(i<viewCount) {
				ViewerOverlay directTestPlayer = overlayList.get(i);
				directTestPlayer.setCamera(camera);
				directTestPlayer.play();
			}
		}
    }
    
    
    
    
	
	    
	
	
    
	public static void main(String[] args){

		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(ViewerMultiple.class)
              .headless(false).run(args);

      EventQueue.invokeLater(() -> {

      	ViewerMultiple ex = (ViewerMultiple)ctx.getBean(ViewerMultiple.class);
      	ex.createAndShowGUI();
      	ex.setVisible(true);
      	
      });
  }


}
