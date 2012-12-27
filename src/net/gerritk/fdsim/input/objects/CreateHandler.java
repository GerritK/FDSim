package net.gerritk.fdsim.input.objects;

import java.awt.event.ActionEvent;

import net.gerritk.fdsim.entities.vehicles.*;
import net.gerritk.fdsim.gui.objects.*;
import net.gerritk.fdsim.input.ButtonHandler;

public class CreateHandler extends ButtonHandler<CreateBar> {
	public CreateHandler(CreateBar instance) {
		super(instance);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		getInstance().setToCreateButton((CreateButton) e.getSource());
		
		if(cmd.equals("mtf")) {
			getInstance().setToCreate(new MTF("", -1, -1, null));
		} else if(cmd.equals("tsfw")) {
			getInstance().setToCreate(new TSFW("", -1, -1, null));
		}
	}
}
