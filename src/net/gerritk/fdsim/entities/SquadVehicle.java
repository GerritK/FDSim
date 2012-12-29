package net.gerritk.fdsim.entities;

import java.awt.image.BufferedImage;

import net.gerritk.fdsim.Playground;
import net.gerritk.fdsim.lights.Bluelight;

public class SquadVehicle extends Vehicle {
	private Bluelight bluelights[];
	
	public SquadVehicle(String name, int x, int y, BufferedImage img, Playground playground) {
		super(name, x, y, img, playground);
	}

	public Bluelight[] getBluelights() {
		return bluelights;
	}

	public void setBluelights(Bluelight bluelights[]) {
		addLights(bluelights);
		this.bluelights = bluelights;
	}
}
