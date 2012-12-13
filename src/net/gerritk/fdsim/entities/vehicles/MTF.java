package net.gerritk.fdsim.entities.vehicles;

import net.gerritk.fdsim.Playground;
import net.gerritk.fdsim.entities.Vehicle;
import net.gerritk.fdsim.lights.Bluelight;
import net.gerritk.fdsim.resource.Images;

public class MTF extends Vehicle {
	public Bluelight bluelight[];
	
	public MTF(String name, int x, int y,Playground playground) {
		super("MTF_" + name, x, y, Images.VEH_MTF, playground);
		
		bluelight = new Bluelight[3];
		
		bluelight[0] = new Bluelight(6, 21, Images.LIGHT_BLUE_1, 10, 50, 3, this);
		bluelight[0].setEnabled(true);
		
		bluelight[1] = new Bluelight(31, 21, Images.LIGHT_BLUE_1, 8, 51, 1, this);
		bluelight[1].setEnabled(true);
		
		bluelight[2] = new Bluelight(8, 56, Images.LIGHT_BLUE_1, 9, 34, 2, this);
		bluelight[2].setEnabled(true);
		
		addLights(bluelight);
	}
}
