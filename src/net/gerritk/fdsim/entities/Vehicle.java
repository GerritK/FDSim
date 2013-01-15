package net.gerritk.fdsim.entities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.gerritk.fdsim.Playground;
import net.gerritk.fdsim.lights.Light;
import net.gerritk.util.ExGraphics;

public abstract class Vehicle extends Entity {
	private ArrayList<Light> lights = new ArrayList<Light>();
	private boolean lightEnabled;
	
	public Vehicle(String name, int x, int y, BufferedImage img, Playground playground) {
		super(name, x, y, img, playground);
	}
	
	@Override
	public void update(long delta) {		
		if(isLightEnabled()) {
			for(Light l : lights) {
				l.update(delta);
			}
		}
	}
	
	@Override
	public void draw(ExGraphics g) {
		super.draw(g);
		
		if(isLightEnabled()) {
			for(Light l : lights) {
				l.draw(g);
			}
		}
	}
	
	/*
	 * Getter & Setter
	 */
	protected void addLight(Light light) {
		lights.add(light);
	}
	
	protected void addLights(Light lights[]) {
		for(Light l : lights) {
			this.lights.add(l);
		}
	}

	public boolean isLightEnabled() {
		return lightEnabled;
	}

	public void setLightEnabled(boolean lightEnabled) {
		this.lightEnabled = lightEnabled;
	}
}
