/*
 *    Copyright (c) The League of Amazing Programmers 2013-2018
 *    Level 1
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javazoom.jl.player.advanced.AdvancedPlayer;

/* 1. Download the JavaZoom jar from here: http://bit.ly/javazoom
 * 2. Right click your project and add it as an External JAR (Under Java Build Path > Libraries).*/

public class Jukebox implements Runnable, ActionListener {

	static JFrame frame = new JFrame();
	static JPanel panel = new JPanel();
	static JLabel label_PickSong = new JLabel();

	static Song CurrentSong;

	static JButton button_Russian = new JButton();
	static Song Russian = new Song("RussianAccent.mp3");

	static JButton button_Elevator = new JButton();
	static Song Elevator = new Song("elevator_music.mp3");
	
	static JButton button_Drums = new JButton();
	static Song Drums = new Song("drums.mp3");

	static JButton button_stop = new JButton();
	
	static JButton button_play = new JButton();
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Jukebox());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void prepFrame() {
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(500, 100);

		panel.add(button_Russian);
		button_Russian.setText("4chan reference in Russian Accent");
		button_Russian.addActionListener(this);

		panel.add(button_Elevator);
		button_Elevator.setText("Elevator Music");
		button_Elevator.addActionListener(this);
		
		panel.add(button_Drums);
		button_Drums.setText("Drums");
		button_Drums.addActionListener(this);
		
		panel.add(button_stop);
		button_stop.setText("||");
		button_stop.addActionListener(this);
		
		panel.add(button_play);
		button_play.setText("|>");
		button_play.addActionListener(this);
		
		panel.add(label_PickSong);
	}

	public void run() {
		prepFrame();

		// 3. Find an mp3 on your computer or on the Internet.
		// 4. Create a Song

		// 5. Play the Song

		/*
		 * 6. Create a user interface for your Jukebox so that the user can to choose
		 * which song to play. You can use can use a different button for each song, or
		 * a picture of the album cover. When the button or album cover is clicked, stop
		 * the currently playing song, and play the one that was selected.
		 */
	}

	/* Use this method to add album covers to your Panel. */
	private JLabel loadImage(String fileName) {
		URL imageURL = getClass().getResource(fileName);
		Icon icon = new ImageIcon(imageURL);
		return new JLabel(icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		JButton clicked = (JButton) e.getSource();
		if ((clicked == button_stop || clicked == button_play) && CurrentSong == null) {
			label_PickSong.setText("Pick A Song");
		} else if (clicked == button_stop) {
			CurrentSong.stop();
		} else if (clicked == button_play) {
			CurrentSong.play();
		} else if (clicked == button_Russian) {
			if (CurrentSong != null) {
				CurrentSong.stop();
			}
			CurrentSong = Russian;
		} else if (clicked == button_Elevator) {
			if (CurrentSong != null) {
				CurrentSong.stop();
			}
			CurrentSong = Elevator;
		} else if (clicked == button_Drums) {
			if (CurrentSong != null) {
				CurrentSong.stop();
			}
			CurrentSong = Drums;
		}
		
		if (CurrentSong != null) {
			label_PickSong.setText("");
		}
	}
}

class Song {

	private int duration;
	private String songAddress;
	private AdvancedPlayer mp3Player;
	private InputStream songStream;

	/**
	 * Songs can be constructed from files on your computer or Internet addresses.
	 * 
	 * Examples: <code> 
	 * 		new Song("everywhere.mp3"); 	//from default package 
	 * 		new Song("/Users/joonspoon/music/Vampire Weekend - Modern Vampires of the City/03 Step.mp3"); 
	 * 		new	Song("http://freedownloads.last.fm/download/569264057/Get%2BGot.mp3"); 
	 * </code>
	 */
	public Song(String songAddress) {
		this.songAddress = songAddress;
	}

	public void play() {
		loadFile();
		if (songStream != null) {
			loadPlayer();
			startSong();
		} else
			System.err.println("Unable to load file: " + songAddress);
	}

	public void setDuration(int seconds) {
		this.duration = seconds * 100;
	}

	public void stop() {
		if (mp3Player != null)
			mp3Player.close();
	}

	private void startSong() {
		Thread t = new Thread() {
			public void run() {
				try {
					if (duration > 0)
						mp3Player.play(duration);
					else
						mp3Player.play();
				} catch (Exception e) {
				}
			}
		};
		t.start();
	}

	private void loadPlayer() {
		try {
			this.mp3Player = new AdvancedPlayer(songStream);
		} catch (Exception e) {
		}
	}

	private void loadFile() {
		if (songAddress.contains("http"))
			this.songStream = loadStreamFromInternet();
		else
			this.songStream = loadStreamFromComputer();
	}

	private InputStream loadStreamFromInternet() {
		try {
			return new URL(songAddress).openStream();
		} catch (Exception e) {
			return null;
		}
	}

	private InputStream loadStreamFromComputer() {
		try {
			return new FileInputStream(songAddress);
		} catch (FileNotFoundException e) {
			return this.getClass().getResourceAsStream(songAddress);
		}
	}
}
