package net.gerritk.fdsim.entities;

import java.awt.image.BufferedImage;

import net.gerritk.fdsim.Playground;
import net.gerritk.fdsim.lights.Light;
import net.gerritk.fdsim.lists.LightList;
import net.gerritk.util.ExGraphics;

public class SquadVehicle extends Vehicle {	
	public SquadVehicle(String name, int x, int y, BufferedImage img, Playground playground) {
		super(name, x, y, img, playground);
	}
	
	@Override
	public void update(long delta) {
		super.update(delta);
	}
	
	@Override
	public void draw(ExGraphics g) {
		super.draw(g);
	}
	
	/*
	 * Getter & Setter
	 */
	public void setBluelightEnabled(boolean enabled) {
		LightList<Light> bl = getLights().getByType(Light.BLUELIGHT);
		
		for(Light b : bl) {
			b.setEnabled(enabled);
		}
	}
	
	public boolean isBluelightEnabled() {
		return isLightEnabled(Light.BLUELIGHT);
	}
	
	public LightList<Light> getBluelights() {
		return getLights().getByType(Light.BLUELIGHT);
	}
}
