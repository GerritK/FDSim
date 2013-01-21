package net.gerritk.fdsim.input.file;

public class Section {
	private String name;
	private int start, end;
	
	public Section(String name, int start, int end) {
		setName(name);
		setStart(start);
		setEnd(end);
	}
	
	/*
	 * Getter & Setter
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
