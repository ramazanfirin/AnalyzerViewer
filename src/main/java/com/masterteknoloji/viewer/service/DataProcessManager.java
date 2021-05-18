package com.masterteknoloji.viewer.service;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masterteknoloji.viewer.domain.Camera;
import com.masterteknoloji.viewer.domain.Line;
import com.masterteknoloji.viewer.service.rabbitmq.RabbitMQSender;

@Service
public class DataProcessManager {

	
	@Autowired
	RabbitMQSender rabbitMQSender;

	@Autowired
	CameraService cameraService;
	
	public void processdata(List<Camera> cameraList) {
    	for (Camera camera : cameraList) {
    		if(camera.getProcessData()) {
				for (Line line : camera.getLineList()) {
					for (Long  duration : line.getData()) {
					Timer timer = new Timer(duration.intValue(), new ActionListener() {
						  @Override
						  public void actionPerformed(ActionEvent arg0) {
							lineCrossed(line);
						  }
						});
			    	timer.setRepeats(false); // Only execute once
			    	timer.start(); // Go go go!
					}
				}
			}
		}
    }
	
	public void lineCrossed(Line line) {
    	if(line!=null) {
	    	line.setCount(line.getCount()+1);
	    	line.setColor(Color.red);
	    	System.out.println(line.getId()+ ":"+ line.getColor() +" yapıldı");
	    	Timer timer = new Timer(100, new ActionListener() {
				  @Override
				  public void actionPerformed(ActionEvent arg0) {
					line.setColor(Color.yellow);
				  }
				});
	    	timer.setRepeats(false); // Only execute once
	    	timer.start(); // Go go go!
    	}
    }
	
	public void lineCrossedByMQ(Long lineId) {
		Line line  = cameraService.findLine(lineId);
		lineCrossed(line);
	}
	
	
	public void processDataByMQ(List<Camera> cameraList) {
    	for (Camera camera : cameraList) {
    		if(camera.getProcessData()) {
				for (Line line : camera.getLineList()) {
					for (Long  duration : line.getData()) {
					Timer timer = new Timer(duration.intValue(), new ActionListener() {
						  @Override
						  public void actionPerformed(ActionEvent arg0) {
							//lineCrossed(line);
							rabbitMQSender.send(line.getId());
						  }
						});
			    	timer.setRepeats(false); // Only execute once
			    	timer.start(); // Go go go!
//			    	break;
					}
				}
			}
		}
    }

	
}
