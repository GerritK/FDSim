package net.gerritk.fdsim.entities;

import java.awt.image.BufferedImage;

import net.gerritk.fdsim.Playground;
import net.gerritk.fdsim.lights.Bluelight;
import net.gerritk.util.ExGraphics;

public class SquadVehicle extends Vehicle {
	private Bluelight bluelights[];
	private boolean bluelightEnabled;
	
	public SquadVehicle(String name, int x, int y, BufferedImage img, Playground playground) {
		super(name, x, y, img, playground);
	}
	
	@Override
	public void update(long delta) {
		super.update(delta);
		
		if(isBluelightEnabled()) {
			for(Bluelight bl : bluelights) {
				bl.update(delta);
			}
		}
	}
	
	@Override
	public void draw(ExGraphics g) {
		super.draw(g);
		
		if(isBluelightEnabled()) {
			for(Bluelight bl : bluelights) {
				bl.draw(g);
			}
		}
	}
	
	/*
	 * Getter & Setter
	 */
	public void setBluelightEnabled(boolean enabled) {
		this.bluelightEnabled = enabled;
	}
	
	public boolean isBluelightEnabled() {
		return bluelightEnabled;
	}
	
	public Bluelight[] getBluelights() {
		return bluelights;
	}

	public void setBluelights(Bluelight bluelights[]) {
		this.bluelights = bluelights;
	}
}
