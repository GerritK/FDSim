package net.gerritk.fdsim.input;

import java.awt.event.ActionListener;

public abstract class ButtonHandler<T> implements ActionListener {
	private T instance;
	
	public ButtonHandler(T instance) {
		setInstance(instance);
	}

	public T getInstance() {
		return instance;
	}

	public void setInstance(T instance) {
		this.instance = instance;
	}
}
