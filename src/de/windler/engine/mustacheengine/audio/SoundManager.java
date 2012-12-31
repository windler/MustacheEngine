package de.windler.engine.mustacheengine.audio;

import java.util.Dictionary;
import java.util.Hashtable;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**
 * the soundmanager
 * 
 * @author Nico Windler
 * 
 */
public class SoundManager {

	private SoundPool sp;
	private AudioManager am;
	private MediaPlayer mediaPlayer;
	private Dictionary<String, Integer> sounds;
	private Dictionary<String, Integer> music;
	private boolean isPlaying = false;

	/**
	 * creates a new soundmanager
	 * 
	 * @param act
	 */
	public SoundManager(Activity act) {
		sounds = new Hashtable<String, Integer>();
		music = new Hashtable<String, Integer>();

		sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		am = (AudioManager) act.getSystemService(Context.AUDIO_SERVICE);
		act.setVolumeControlStream(AudioManager.STREAM_MUSIC);

		mediaPlayer = new MediaPlayer();
	}

	/**
	 * registers a sound in the soundpool
	 * 
	 * @param id
	 *            id of resource
	 * @param act
	 */
	public void registerSound(String name, int id, Activity act) {
		sounds.put(name, sp.load(act, id, 1));
	}

	/**
	 * registers music in the soundpool
	 * 
	 * @param id
	 *            id of resource
	 * @param act
	 */
	public void registerMusic(String name, int id, Activity act) {
		music.put(name, id);
	}

	/**
	 * loads a new music-file to the mediaplayer
	 * 
	 * @param name
	 * @param act
	 */
	public void changeMusic(String name, Activity act) {
		mediaPlayer.release();
		mediaPlayer = MediaPlayer.create(act, music.get(name));
	}

	/**
	 * pauses music
	 */
	public void pauseMusic() {
		mediaPlayer.pause();
	}

	/**
	 * starts music
	 */
	public void playMusic() {
		mediaPlayer.start();
		isPlaying = !isPlaying;
	}

	/**
	 * plays soundeffect
	 * 
	 * @param id
	 */
	public void playSound(String name) {
		int volume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		sp.play(sounds.get(name), volume, volume, 1, 0, 1);
	}

	/**
	 * disposes the SoundManager
	 */
	public void dispose() {
		sp.release();
		mediaPlayer.release();
	}

	/**
	 * resumes the musicplayer
	 */
	public void resumeMusic() {
		if (isPlaying) {
			mediaPlayer.start();
		}
	}

}
