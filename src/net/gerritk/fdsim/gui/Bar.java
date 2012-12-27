package net.gerritk.fdsim.gui;

import net.gerritk.fdsim.input.ButtonHandler;

public abstract class Bar extends InterfaceObject {
	private static final long serialVersionUID = 4012522882488339339L;
	
	private ButtonHandler<?> buttonHandler;
	
	public Bar(int x, int y, int width, int height, InterfaceObject ref) {
		super(x, y, width, height, ref);
	}

	public ButtonHandler<?> getButtonHandler() {
		return buttonHandler;
	}

	public void setButtonHandler(ButtonHandler<?> buttonHandler) {
		this.buttonHandler = buttonHandler;
	}
}
