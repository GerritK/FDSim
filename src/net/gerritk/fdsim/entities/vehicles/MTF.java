package net.gerritk.fdsim.entities.vehicles;

import net.gerritk.fdsim.Playground;
import net.gerritk.fdsim.entities.Vehicle;
import net.gerritk.fdsim.resource.Images;

public class MTF extends Vehicle {

	public MTF(String name, int x, int y,Playground playground) {
		super("MTF_" + name, x, y, Images.VEH_MTF, playground);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
