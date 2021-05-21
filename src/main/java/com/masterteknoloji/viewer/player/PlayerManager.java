package com.masterteknoloji.viewer.player;

import java.util.Date;

import javax.swing.JFrame;

import com.masterteknoloji.viewer.Viewer;

import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class PlayerManager  extends MediaPlayerEventAdapter{

	
	Viewer main;
	
	
	
	public PlayerManager(Viewer main) {
		super();
		this.main = main;
	}



	@Override
	    public void playing(MediaPlayer mediaPlayer) {
//	        System.out.println(new Date().getTime()+ "playing");
//	         processdata();
//	        main.startProcess();
	        
	    }
	
	
	@Override
    public void opening(MediaPlayer mediaPlayer) 
	{
		
//		System.out.println(new Date().getTime()+ "opening");
    }
}
