package mud.objects;

/* Exit Types include Door, Portal, and Standard and work as follows:
 * 
 * Door - any kind of door, such as a shaped piece of wood, metal, or some other material that
 * is used to block an entrance, usually into an enclosed space like a building or cave. May also
 * apply to extradimensional doors if they are not portals
 * 
 * Portal - magical "portals" of the one or two way varieties that lead to other places on the same
 * plane or other planes, dimensions, etc
 * 
 * Standard - the representation of a "virtual" exit such as may exist between to game areas, and
 * may have no physical representation of any kind (i.e. kind of like the border between two countries,
 * which exists, but rarely has a physical presence.
 */

public enum ExitType {
	DOOR("STD"),
	PORTAL("PORTAL"),
	STD("STD");
	
	private String name;
	
	ExitType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}