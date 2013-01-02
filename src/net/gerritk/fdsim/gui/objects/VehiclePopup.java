package net.gerritk.fdsim.gui.objects;

import net.gerritk.fdsim.Simulation;
import net.gerritk.fdsim.entities.Vehicle;
import net.gerritk.fdsim.gui.IconButton;
import net.gerritk.fdsim.gui.InterfaceObject;
import net.gerritk.fdsim.gui.PopupMenu;
import net.gerritk.fdsim.resource.SimImage;
import net.gerritk.util.ExGraphics;

public class VehiclePopup extends PopupMenu {
	private static final long serialVersionUID = 8275348944554274489L;
	
	private Vehicle v;
	private IconButton btnBluelight, btnLight, btnHazardLights;
	
	public VehiclePopup(int x, int y, int width, int height, Vehicle v, InterfaceObject ref) {
		super(x, y, width, height, ref);
		
		setVehicle(v);
		
		btnBluelight = new IconButton(SimImage.LIGHT_BLUE_1, 2, 2, 14, 14, this);
		btnBluelight.setToolTip("Blaulicht an-/ausschalten");
		Simulation.buttons.add(btnBluelight);
		
		btnLight = new IconButton(SimImage.LIGHT_BLUE_1, 18, 2, 14, 14, this);
		btnLight.setToolTip("Abblendlicht an-/ausschalten");
		Simulation.buttons.add(btnLight);
		
		btnHazardLights = new IconButton(SimImage.LIGHT_BLUE_1, 34, 2, 14, 14, this);
		btnHazardLights.setToolTip("Warnblinker an-/ausschalten");
		Simulation.buttons.add(btnHazardLights);
	}
	
	public void drawGUI(ExGraphics g) {
		if(!isVisible()) return;
		
		super.drawGUI(g);
		
		btnBluelight.drawGUI(g);
		btnLight.drawGUI(g);
		btnHazardLights.drawGUI(g);
	}
	
	/*
	 * Getter & Setter
	 */
	public Vehicle getVehicle() {
		return v;
	}

	public void setVehicle(Vehicle v) {
		this.v = v;
	}
}
