package net.gerritk.fdsim.lists;

import java.util.ArrayList;

import net.gerritk.fdsim.input.file.Section;

public class SectionList extends ArrayList<Section> {
	private static final long serialVersionUID = -3863285635525164920L;
	
	public Section[] getByName(String name) {
		SectionList sl = new SectionList();
		
		for(Section s : this) {
			sl.add(s);
		}
		
		Section sections[] = new Section[sl.size()];
		sections = (Section[]) sl.toArray();
		
		return sections;
	}
}
