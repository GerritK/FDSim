package net.gerritk.fdsim.lists;

import java.util.ArrayList;

import net.gerritk.fdsim.lights.Light;

public class LightList<T extends Light> extends ArrayList<T> {
	private static final long serialVersionUID = -5384597399237697749L;
	
	public LightList<T> getByType(int type) {
		LightList<T> ll = new LightList<T>();
		
		for(T l : this) {
			if(l.getType() == type) {
				ll.add(l);
			}
		}
		
		return ll;
	}
}
