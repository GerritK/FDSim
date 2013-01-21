package net.gerritk.fdsim.entities.vehicles;

import net.gerritk.fdsim.Playground;
import net.gerritk.fdsim.entities.SquadVehicle;
import net.gerritk.fdsim.lights.*;
import net.gerritk.fdsim.resource.SimImage;

public class TSFW extends SquadVehicle {
	
	public TSFW(String name, int x, int y, Playground playground) {
		super(name, x, y, SimImage.VEH_TSFW, playground);
		
		Light l;
		
		l = new Light(6, -8, SimImage.LIGHT_1, Light.HEADLIGHT, this);
		addLight(l);
		
		l = new Light(35, -8, SimImage.LIGHT_1, Light.HEADLIGHT, this);
		addLight(l);
		
		// Bluelight
		Bluelight bluelight;
		
		bluelight = new Bluelight(8, 21, SimImage.LIGHT_BLUE_1, 10, 90, 5, this);
		addLight(bluelight);
		
		bluelight = new Bluelight(8, 21, SimImage.LIGHT_BLUE_1, 10, 90, 18, this);
		addLight(bluelight);
		
		bluelight = new Bluelight(33, 21, SimImage.LIGHT_BLUE_1, 8, 85, 2, this);
		addLight(bluelight);
		
		bluelight = new Bluelight(33, 21, SimImage.LIGHT_BLUE_1, 8, 85, 13, this);
		addLight(bluelight);
	}
}
