package com.masterteknoloji.viewer;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.masterteknoloji.viewer.domain.Camera;
import com.masterteknoloji.viewer.overlay.ViewerOverlay;
import com.masterteknoloji.viewer.service.CameraService;
import com.masterteknoloji.viewer.service.DataProcessManager;

/**
 * Created by Administrator on 2017/12/7.
 */
@SpringBootApplication
@ComponentScan("com")
public class ViewerRolling extends JFrame{


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
		
		Timer timer = new Timer(5000, new ActionListener() {
			  @Override
			  public void actionPerformed(ActionEvent arg0) {
				  Collections.shuffle(cameraList);
				  try {
					 play(cameraList); 		
				  } catch (Exception e) {
					e.printStackTrace();
				}
			  }
			});
	timer.setRepeats(true); // Only execute once
	timer.start(); // Go go go!
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

		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(ViewerRolling.class)
              .headless(false).run(args);

      EventQueue.invokeLater(() -> {

      	ViewerRolling ex = (ViewerRolling)ctx.getBean(ViewerRolling.class);
      	ex.createAndShowGUI();
      	ex.setVisible(true);
      	
      });
  }


}
