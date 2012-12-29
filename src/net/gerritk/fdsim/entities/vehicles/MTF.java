package net.gerritk.fdsim.entities.vehicles;

import net.gerritk.fdsim.Playground;
import net.gerritk.fdsim.entities.SquadVehicle;
import net.gerritk.fdsim.lights.Bluelight;
import net.gerritk.fdsim.resource.SimImage;

public class MTF extends SquadVehicle {	
	public MTF(String name, int x, int y,Playground playground) {
		super(name, x, y, SimImage.VEH_MTF, playground);
		
		Bluelight bluelights[] = new Bluelight[3];
		
		bluelights[0] = new Bluelight(6, 21, SimImage.LIGHT_BLUE_1, 10, 50, 3, this);
		bluelights[0].setEnabled(true);
		
		bluelights[1] = new Bluelight(31, 21, SimImage.LIGHT_BLUE_1, 8, 51, 1, this);
		bluelights[1].setEnabled(true);
		
		bluelights[2] = new Bluelight(8, 56, SimImage.LIGHT_BLUE_1, 9, 34, 2, this);
		bluelights[2].setEnabled(true);
		
		setBluelights(bluelights);
	}
}
