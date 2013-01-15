package net.gerritk.fdsim.gui.lists;

import java.awt.Point;
import java.util.ArrayList;

import net.gerritk.fdsim.gui.InterfaceObject;

public class GUIList<T extends InterfaceObject> extends ArrayList<T> {
	private static final long serialVersionUID = 1062180830869895797L;
	
	public InterfaceObject contains(Point p) {
		for(InterfaceObject ui : this) {
			if(ui.contains(p)) {
				return ui;
			}
		}
		
		return null;
	}
}
