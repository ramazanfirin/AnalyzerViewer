package com.masterteknoloji.viewer;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import com.masterteknoloji.viewer.player.PlayerManager;
import com.masterteknoloji.viewer.player.ViewerOverlay2;
import com.masterteknoloji.viewer.service.CameraService;
import com.masterteknoloji.viewer.service.DataProcessManager;

/**
 * Created by Administrator on 2017/12/7.
 */
@SpringBootApplication
@ComponentScan("com")
public class ViewerIntersections extends JFrame {

	final String intersectionDay = "D:\\KBB\\intersection\\input_multiple_regions.m4v";
	//final String intersectionNigth = "D:\\KBB\\intersection\\intersection_nigth.webm";
	
	final String intersectionNigth = "C:\\Users\\ramazan\\Documents\\Bandicam\\bandicam 2021-06-24 10-09-41-126.mp4";
    

	Dimension DimMax = Toolkit.getDefaultToolkit().getScreenSize();
	int viewWitdh = (int)DimMax.getWidth();
	int viewHeight = (int)DimMax.getHeight();
	int viewCount = 1;
	JPanel jPanel = new JPanel();
	
	List<ViewerOverlay2> overlayList = new ArrayList<ViewerOverlay2>();
	
	@Autowired
	CameraService cameraService;
	
	@Autowired
	DataProcessManager dataProcessManager;
	
	
	List<Camera> cameraList = new ArrayList<Camera>();
	
	PlayerManager playerManager;
	
	public void startProcess() {
		dataProcessManager.processDataByMQ(cameraList);
	}
    
    private  void createAndShowGUI(){
    
    	setSize(1920, 1080);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		jPanel.setLayout(new GridLayout(2, 2));
		setContentPane(jPanel);
		
		//playerManager = new PlayerManager(this);
		
		//cameraList = cameraService.prepareCameraListIntersectionDay(9l,intersectionDay);
		cameraList = cameraService.prepareCameraListIntersectionDay(10l,intersectionNigth);
		System.out.println(cameraList.size());
		
		try {
			prepareOverlays(viewCount);
			play(cameraList);
//			dataProcessManager.processdata(cameraList);
//			dataProcessManager.processDataByMQ(cameraList);
			
			addListeners(this);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
//		Timer timer = new Timer(1000, new ActionListener() {
//			  @Override
//			  public void actionPerformed(ActionEvent arg0) {
//				  Collections.shuffle(cameraList);
//				  try {
//					  for (ViewerOverlay2 embeddedMediaPlayer2 : overlayList) {
//                      	embeddedMediaPlayer2.getMediaPlayer().play();
//                      	startProcess();
//						}				  } catch (Exception e) {
//					e.printStackTrace();
//				}
//			  }
//			});
//	timer.setRepeats(false); // Only execute once
//	timer.start(); // Go go go!
    }

    
    public void prepareOverlays(int viewCount) throws InvocationTargetException, InterruptedException {
    	for (int i = 0; i < viewCount; i++) {
    		ViewerOverlay2 directTestPlayer = new ViewerOverlay2(null,viewWitdh, viewHeight, null);
    		//directTestPlayer.getMediaPlayer().addMediaPlayerEventListener(playerManager);
    		JPanel imagePane = directTestPlayer.getImagePane();
			jPanel.add(imagePane);
			overlayList.add(directTestPlayer);
    	}
    	    	
    }
    
    public void play(List<Camera> cameraList) throws InvocationTargetException, InterruptedException {
    	for (int i = 0; i < cameraList.size(); i++) {
			Camera camera = cameraList.get(i);
			if(i<viewCount) {
				ViewerOverlay2 directTestPlayer = overlayList.get(i);
				directTestPlayer.setCamera(camera);
				
//				Thread.currentThread().sleep(5000);
				directTestPlayer.prepare();
			}
		}
    }
    
    
    public void addListeners(JFrame f) {
    	f.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        for (ViewerOverlay2 embeddedMediaPlayer2 : overlayList) {
                        	embeddedMediaPlayer2.getMediaPlayer().play();
                        	startProcess();
						}
                    	break;
                    	
                                   }
            }
        });

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (ViewerOverlay2 embeddedMediaPlayer2 : overlayList) {
                	embeddedMediaPlayer2.getMediaPlayer().release();
                	embeddedMediaPlayer2.getFactory().release();
                }
                System.exit(0);
            }
        });
        
        f.addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent me) { }
            public void mouseReleased(MouseEvent me) { }
            public void mouseEntered(MouseEvent me) { }
            public void mouseExited(MouseEvent me) { }
            public void mouseClicked(MouseEvent me) { 
              int x = me.getX();
              int y = me.getY();
//              text.setText("X:" + x + " Y:" + y); 
              System.out.println("X:" + x + " Y:" + y);
            }
        });
    }

    
	
	    
	
	
    
	public static void main(String[] args){

		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(ViewerIntersections.class)
              .headless(false).run(args);

      EventQueue.invokeLater(() -> {

      	ViewerIntersections ex = (ViewerIntersections)ctx.getBean(ViewerIntersections.class);
      	ex.createAndShowGUI();
      	ex.setVisible(true);
//      	ex.startProcess();
      });
  }


}
