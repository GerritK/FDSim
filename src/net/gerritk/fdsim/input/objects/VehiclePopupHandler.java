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
		String cmd = e.getActionCommand();
		Vehicle v = getInstance().getVehicle();
		
		if(cmd.equals("toggleBluelight")) {			
			if(v instanceof SquadVehicle) {
				SquadVehicle sv = (SquadVehicle) v;
				sv.setBluelightEnabled(!sv.isBluelightEnabled());
			}
		} else if(cmd.equals("toggleLight")) {
			v.setLightEnabled(!v.isLightEnabled());
		}
	}
}
