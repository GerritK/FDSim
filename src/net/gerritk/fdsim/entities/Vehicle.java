package net.gerritk.fdsim.entities;

import java.awt.image.BufferedImage;

import net.gerritk.fdsim.Playground;

public abstract class Vehicle extends Entity {
	public Vehicle(String name, int x, int y, BufferedImage img, Playground playground) {
		super(name, x, y, img, playground);
	}
}
