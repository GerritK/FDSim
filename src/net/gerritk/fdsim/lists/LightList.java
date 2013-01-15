package net.gerritk.fdsim.lists;

import java.util.ArrayList;

import net.gerritk.fdsim.lights.Light;

public class LightList<T extends Light> extends ArrayList<T> {
	private static final long serialVersionUID = -5384597399237697749L;
	
	@SuppressWarnings("unchecked")
	public T[] getLightsByType(int type) {
		LightList<T> lights = new LightList<T>();
		
		for(T l : this) {
			if(l.getType() == type) {
				lights.add(l);
			}
		}
		
		Light tmp[] = new Light[lights.size()];
		lights.toArray(tmp);
		
		return (T[]) tmp;
	}
}
