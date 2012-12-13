package net.gerritk.fdsim.entities.vehicles;

import net.gerritk.fdsim.Playground;
import net.gerritk.fdsim.entities.Vehicle;
import net.gerritk.fdsim.lights.*;
import net.gerritk.fdsim.resource.Images;

public class TSFW extends Vehicle {
	public Bluelight bluelight[];
	
	public TSFW(String name, int x, int y, Playground playground) {
		super("TSFW_" + name, x, y, Images.VEH_TSFW, playground);
		
		bluelight = new Bluelight[4];
		
		bluelight[0] = new Bluelight(8, 21, Images.LIGHT_BLUE_1, 10, 70, 5, this);
		bluelight[0].setEnabled(true);
		
		bluelight[1] = new Bluelight(8, 21, Images.LIGHT_BLUE_1, 10, 70, 18, this);
		bluelight[1].setEnabled(true);
		
		bluelight[2] = new Bluelight(33, 21, Images.LIGHT_BLUE_1, 8, 71, 2, this);
		bluelight[2].setEnabled(true);
		
		bluelight[3] = new Bluelight(33, 21, Images.LIGHT_BLUE_1, 8, 71, 13, this);
		bluelight[3].setEnabled(true);
		
		addLights(bluelight);
	}
}