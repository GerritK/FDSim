package net.gerritk.fdsim.entities;

import java.awt.image.BufferedImage;

import net.gerritk.fdsim.Playground;
import net.gerritk.fdsim.lights.Light;
import net.gerritk.fdsim.lists.LightList;
import net.gerritk.util.ExGraphics;

public abstract class Vehicle extends Entity {
	private LightList<Light> lights = new LightList<Light>();
	
	public Vehicle(String name, int x, int y, BufferedImage img, Playground playground) {
		super(name, x, y, img, playground);
	}
	
	@Override
	public void update(long delta) {		
		for(Light l : lights) {
			l.update(delta);
		}
	}
	
	@Override
	public void draw(ExGraphics g) {
		super.draw(g);
		
		for(Light l : lights) {
			l.draw(g);
		}
	}
	
	/*
	 * Getter & Setter
	 */
	public boolean isLightEnabled() {
		Light ll[] = lights.getLightsByType(Light.HEADLIGHT);
		
		if(ll.length <= 0) return false;
		
		return ll[0].isEnabled();
	}

	public void setLightEnabled(boolean lightEnabled) {
		Light ll[] = lights.getLightsByType(Light.HEADLIGHT);
		
		for(Light l : ll) {
			l.setEnabled(lightEnabled);
		}
	}	
	
	protected void addLight(Light light) {
		lights.add(light);
	}
	
	protected void addLights(Light lights[]) {
		for(Light l : lights) {
			this.lights.add(l);
		}
	}
}
