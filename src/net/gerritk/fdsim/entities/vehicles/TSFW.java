package net.gerritk.fdsim.entities.vehicles;

import net.gerritk.fdsim.Playground;
import net.gerritk.fdsim.entities.SquadVehicle;
import net.gerritk.fdsim.lights.*;
import net.gerritk.fdsim.resource.SimImage;

public class TSFW extends SquadVehicle {
	
	public TSFW(String name, int x, int y, Playground playground) {
		super(name, x, y, SimImage.VEH_TSFW, playground);
		
		Light l;
		
		l = new Light(6, -8, SimImage.LIGHT_1, -1, this);
		l.setEnabled(true);
		addLight(l);
		
		l = new Light(35, -8, SimImage.LIGHT_1, -1, this);
		l.setEnabled(true);
		addLight(l);
		
		// Bluelight
		Bluelight bluelight[] = new Bluelight[4];
		
		bluelight[0] = new Bluelight(8, 21, SimImage.LIGHT_BLUE_1, 10, 90, 5, this);
		bluelight[0].setEnabled(true);
		
		bluelight[1] = new Bluelight(8, 21, SimImage.LIGHT_BLUE_1, 10, 90, 18, this);
		bluelight[1].setEnabled(true);
		
		bluelight[2] = new Bluelight(33, 21, SimImage.LIGHT_BLUE_1, 8, 85, 2, this);
		bluelight[2].setEnabled(true);
		
		bluelight[3] = new Bluelight(33, 21, SimImage.LIGHT_BLUE_1, 8, 85, 13, this);
		bluelight[3].setEnabled(true);
		
		setBluelights(bluelight);
	}
}
