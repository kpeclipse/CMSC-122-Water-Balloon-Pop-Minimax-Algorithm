package settings;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public class SoundClip{
	private URL url;
	private Clip clip;
	private final String filename;
	private final int identifier;

	public SoundClip(final String filename, final int identifier) {
		this.filename = filename;
		this.identifier = identifier;
	}

	public void stop(){
		clip.stop();
	}

	private int lastFrame;

	public void pause(){
		if (clip != null && clip.isRunning()) {
			lastFrame = clip.getFramePosition();
			clip.stop();
		}
	}

	public void resume(){
		if (clip != null && !clip.isRunning()) {
			if (lastFrame < clip.getFrameLength()) {
				clip.setFramePosition(lastFrame);
			}else {
				clip.setFramePosition(0);
			}
			clip.start();
		}
	}


	public void start(){
		try{
			url = this.getClass().getClassLoader().getResource(filename);
			final AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioIn);

			if(identifier == 0)
			clip.loop(Clip.LOOP_CONTINUOUSLY);

			if(identifier == 1)
			clip.start();
		}catch (final UnsupportedAudioFileException e) {
			e.printStackTrace();
		}catch (final IOException e) {
			e.printStackTrace();
		}catch (final LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
