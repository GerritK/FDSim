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
	public boolean isHeadlightEnabled() {
		LightList<Light> ll = lights.getByType(Light.HEADLIGHT);
		
		if(ll.size() <= 0) return false;
		
		return ll.get(0).isEnabled();
	}

	public void setHeadlightEnabled(boolean lightEnabled) {
		LightList<Light> ll = lights.getByType(Light.HEADLIGHT);
		
		for(Light l : ll) {
			l.setEnabled(lightEnabled);
		}
	}
	
	public boolean isLightEnabled(int type) {
		LightList<Light> ll = lights.getByType(type);
		
		if(ll.size() <= 0) return false;
		
		return ll.get(0).isEnabled();
	}

	public void setLightEnabled(boolean enabled, int type) {
		LightList<Light> ll = lights.getByType(type);
		
		for(Light l : ll) {
			l.setEnabled(enabled);
		}
	}
	
	protected void addLight(Light light) {
		lights.add(light);
		
		light.setEnabled(isLightEnabled(light.getType()));
	}
	
	protected void addLights(Light lights[]) {
		for(Light l : lights) {
			this.lights.add(l);
		}
	}
	
	protected LightList<Light> getLights() {
		return lights;
	}
}
