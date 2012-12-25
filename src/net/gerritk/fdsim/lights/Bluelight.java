package net.gerritk.fdsim.lights;

import java.awt.image.BufferedImage;

import net.gerritk.fdsim.entities.Entity;
import net.gerritk.util.ExGraphics;

public class Bluelight extends Light {
	private long timerFrequence;
	private int frequence;
	private int pause;
	private int delay;
	private boolean flash;
	
	public Bluelight(int x, int y, BufferedImage img, int frequence, int pause, int delay, Entity reference) {
		super(x, y, img, Light.TYPE_BLUELIGHT, reference);
		setFrequence(frequence);
		setPause(pause);
		setDelay(delay);
		
		timerFrequence = 10 * delay;
	}
	
	@Override
	public void update(long delta) {
		timerFrequence += delta;
		
		super.update(delta);
		
		if((flash && timerFrequence >= 10 * getFrequence()) || (!flash && timerFrequence >= 10 * getPause())) {
			flash = !flash;

			timerFrequence = 0;
		}
	}
	
	@Override
	public void draw(ExGraphics g) {
		if(flash) {
			super.draw(g);
		}
	}
	
	/*
	 * Getter & Setter
	 */
	public int getFrequence() {
		return frequence;
	}

	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}

	public int getPause() {
		return pause;
	}

	public void setPause(int pause) {
		this.pause = pause;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

}
