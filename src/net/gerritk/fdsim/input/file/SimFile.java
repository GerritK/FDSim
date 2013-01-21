package net.gerritk.fdsim.input.file;

import java.io.*;

import net.gerritk.fdsim.lists.SectionList;

public class SimFile extends File {
	private static final long serialVersionUID = 3307313336495390758L;
	
	public static final int MODE_CONTAINS = 0, MODE_START = 1, MODE_EQUALS = 2, MODE_EQUALS_I = 3;
	
	private LineNumberReader reader;
	private String buffer[];
	
	private SectionList sections;
	private Section currentSection;

	public SimFile(String pathname) {
		super(pathname);
		
		try {
			setReader(new LineNumberReader(new InputStreamReader(new FileInputStream(this))));
			getReader().mark(0);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		buffer();
	}
	
	public boolean buffer(int start, int end) {
		for(int i = start; i < end; i++) {
			try {
				buffer[i] = getLine(i, false);
			} catch(IOException e) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean buffer() {
		buffer = new String[getLineCount()];
		
		return buffer(0, buffer.length);
	}
	
	public boolean loadSections() {
		int line = 0;
		Section s = null;
		while((line = findLine("[", line, MODE_START)) != -1) {
			try {
				if(s != null) s.setEnd(line - 1);
				
				s = new Section(getLine(line, true), line, buffer.length);
			} catch(IOException e) {
				return false;
			}
		}
		
		return true;
	}
	
	/*
	 * Getter & Setter
	 */
	public int getLineCount() {
		int lines = 0;
		
		try {
			while(getReader().readLine() != null) {
				lines++;
			}
			
			getReader().reset();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}
	
	public String getLine(int i, boolean fromBuffer) throws IOException {
		if((fromBuffer && i > buffer.length) || (!fromBuffer && i > getLineCount())) return null;
		
		if(fromBuffer) {
			return buffer[i];
		} else {
			LineNumberReader r = getReader();
			
			r.setLineNumber(i);
			
			String l = r.readLine();
			r.reset();
			return l;
		}
	}
	
	public int findLine(String str, int start, int end, int mode) {
		if(buffer == null) return -1;
		
		if(end > buffer.length) end = buffer.length;
		
		for(int i = start; i < end; i++) {
			switch(mode) {
				case MODE_CONTAINS:
					if(buffer[i].contains(str)) {
						return i;
					}
					break;
				
				case MODE_START:
					if(buffer[i].startsWith(str)) {
						return i;
					}
					break;
					
				case MODE_EQUALS:
					if(buffer[i].equals(str)) {
						return i;
					}
					break;
					
				case MODE_EQUALS_I:
					if(buffer[i].equalsIgnoreCase(str)) {
						return i;
					}
					break;
			}
		}
		
		return -1;
	}
	
	public int findLine(String str, int start, int mode) {
		return findLine(str, start, buffer.length, mode);
	}
	
	public int findLine(String str, int mode) {
		return findLine(str, 0, mode);
	}
	
	public LineNumberReader getReader() {
		return reader;
	}

	public void setReader(LineNumberReader reader) {
		this.reader = reader;
	}
	
	public String[] getBuffer() {
		return buffer;
	}

	public void setBuffer(String[] buffer) {
		this.buffer = buffer;
	}

	public Section getCurrentSection() {
		return currentSection;
	}

	public void setCurrentSection(Section currentSection) {
		this.currentSection = currentSection;
	}

	public SectionList getSections() {
		return sections;
	}

	public void setSections(SectionList sections) {
		this.sections = sections;
	}
}
