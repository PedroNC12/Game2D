package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];
	
	//Pegar a url dos arquivos de som e colocar em um array
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
		soundURL[1] = getClass().getResource("/sound/coin.wav");
		soundURL[2] = getClass().getResource("/sound/powerup.wav");
		soundURL[3] = getClass().getResource("/sound/unlock.wav");
		soundURL[4] = getClass().getResource("/sound/fanfare.wav");
	}
	
	//Carregar os arquivos de som
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Iniciar o áudio
	public void play() {
		clip.start();
	}
	
	//Colocar o áudio em loop contínuo
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	//Parar o áudio
	public void stop() {
		clip.stop();
	}
}
