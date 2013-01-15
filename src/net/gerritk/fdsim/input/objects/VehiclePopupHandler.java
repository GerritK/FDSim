package net.gerritk.fdsim.input.objects;

import java.awt.event.ActionEvent;

import net.gerritk.fdsim.entities.*;
import net.gerritk.fdsim.gui.objects.VehiclePopup;
import net.gerritk.fdsim.input.ButtonHandler;

public class VehiclePopupHandler extends ButtonHandler<VehiclePopup> {

	public VehiclePopupHandler(VehiclePopup instance) {
		super(instance);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Vehicle v = getInstance().getVehicle();
		
		if(e.getActionCommand().equals("toggleBluelight")) {			
			if(v instanceof SquadVehicle) {
				SquadVehicle sv = (SquadVehicle) v;
				sv.setBluelightEnabled(!sv.isBluelightEnabled());
			}
		} else if(e.getActionCommand().equals("toggleLight")) {
			v.setLightEnabled(!v.isLightEnabled());
		}
	}
}
