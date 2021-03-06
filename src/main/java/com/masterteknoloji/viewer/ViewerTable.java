package com.masterteknoloji.viewer;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
public class ViewerTable extends JFrame {


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
		addMenu();
		
		cameraList = cameraService.getCameraList();
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
//    		directTestPlayer.getMediaPlayer().addMediaPlayerEventListener(playerManager);
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
    }

    
	
	    
	public void addMenu() {
		 JMenu menu, submenu;  
		 menu=new JMenu("Menu");  
		 JMenuItem i1=new JMenuItem("Item 1");  
		 menu.add(i1);
		 
		 JMenuBar mb=new JMenuBar();  
		 mb.add(menu);
		 this.setJMenuBar(mb);
	}
	
    
	public static void main(String[] args){

		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(ViewerTable.class)
              .headless(false).run(args);

      EventQueue.invokeLater(() -> {

      	ViewerTable ex = (ViewerTable)ctx.getBean(ViewerTable.class);
      	ex.createAndShowGUI();
      	ex.setVisible(true);
//      	ex.startProcess();
      });
  }


}
