package mud.objects;

import java.util.ArrayList;

import mud.MUDObject;
import mud.ObjectFlag;
import mud.TypeFlag;

import mud.interfaces.Lockable;
import mud.utils.Utils;

import java.util.EnumSet;

/*
 * Copyright (c) 2012 Jeremy N. Harton
 * 
 * Released under the MIT License:
 * LICENSE.txt, http://opensource.org/licenses/MIT
 * 
 * NOTE: license provided with code controls, if any
 * changes are made to the one referred to.
 */

/**
 * Exit Class
 * 
 * @author Jeremy N. Harton
 * 
 */
public class Exit extends MUDObject implements Lockable
{
	/**
	 * 
	 */

	private ExitType eType = ExitType.STD;

	private boolean isLocked = false;

	private int origin = 0;      // strictly for doors?
	private int destination = 0;
	
	public String succMsg;       // message about successfully using exit
	public String osuccMsg;      // message others see about you using exit
	public String failMsg;       // message about failing to use the exit (locked, etc)
	public String ofailMsg;      // message others see about you failing to use the exit
	
	public ArrayList<String> aliases = new ArrayList<String>();
	
	// empty default constructor for subclasses
	public Exit() {
		this.type  = TypeFlag.EXIT;
		
		// set messages to clear
		this.succMsg = "";  // Success Message
		this.osuccMsg = ""; // OSuccess Message
		this.failMsg = "";  // Failure Message
		this.ofailMsg = ""; // OFailure Message
	}
	
	public Exit(ExitType eType) {
		this.type = TypeFlag.EXIT;
		this.eType = eType;
		
		// set messages to clear
		this.succMsg = "";  // Success Message
		this.osuccMsg = ""; // OSuccess Message
		this.failMsg = "";  // Failure Message
		this.ofailMsg = ""; // OFailure Message
	}
	
	public Exit(String name, int location, int destination) {
		this.type = TypeFlag.EXIT;
		// Set the name
		this.name = name;
		// Set the description to the default
		this.desc = "You see nothing.";
		// Set flags
		//this.flags = null;
		// Set the locks
		this.locks = "";
		// Set the location
		this.location = location;
		// Set the destination
		this.destination = destination;
		// set messages to clear
		this.succMsg = "";  // Success Message
		this.osuccMsg = ""; // OSuccess Message
		this.failMsg = "";  // Failure Message
		this.ofailMsg = ""; // OFailure Message
	}
	
	// Object Loading Constructor
	// Name, Flags, Description, DBRef #, Location of exit (DBRef #), destination of exit (DBRef #)
	public Exit(int tempDBRef, String tempName, final EnumSet<ObjectFlag> flagsNotUsed, String tempDesc, int tempLoc, int tempDestination)
	{
		// Set the dbref (database reference)
		super(tempDBRef);
		this.type = TypeFlag.EXIT;
		
		// Set the name
		this.name = tempName;
		// Set the description to the default
		this.desc = "You see nothing.";
		// Set flags
		this.flags = flagsNotUsed;
		// Set the locks
		this.locks = "";
		// Set the location
		this.location = tempLoc;
		// Set the destination
		this.destination = tempDestination;
		// set messages to clear
		this.succMsg = "";  // Success Message
		this.osuccMsg = ""; // OSuccess Message
		this.failMsg = "";  // Failure Message
		this.ofailMsg = ""; // OFailure Message
	}
	
	public void setDestination(int newDestination) {
		this.destination = newDestination;
	}

	public int getDestination()
	{
		return destination;
	}
	
	public ArrayList<String> getAliases() {
		return this.aliases;
	}

	public void setMessage(String name, String newMsg) {
		if (name.toLowerCase().equals("succmsg")) { this.succMsg = newMsg; }
		else if (name.toLowerCase().equals("osuccmsg")) { this.osuccMsg = newMsg; }
		else if (name.toLowerCase().equals("failmsg")) { this.failMsg = newMsg; }
		else if (name.toLowerCase().equals("ofailmsg")) { this.ofailMsg = newMsg; }
	}

	@Override
	public void lock() {
		if (this.eType == ExitType.DOOR) {
			this.isLocked = true;
		}
	}

	@Override
	public void unlock() {
		if (this.eType == ExitType.DOOR) {
			this.isLocked = false;
		}
	}

	@Override
	public boolean isLocked() {
		return this.isLocked;
	}
	
	public void setExitType(ExitType newType) {
		this.eType = newType;
	}
	
	public ExitType getExitType() {
		return this.eType;
	}

	public String toDB() {
		String[] output = new String[7];
		output[0] = this.getDBRef() + "";       // database reference number
		output[1] = this.getName();             // name
		output[2] = this.getFlagsAsString();    // flags
		output[3] = this.getDesc();             // description
		output[4] = this.getLocation() + "";    // location (a.k.a source)
		output[5] = this.getDestination() + ""; // destination
		output[6] = this.eType.ordinal() + "";  // exit type
		return Utils.join(output, "#");
	}
	
	@Override
	public String toString() {
		return "";
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exit fromJSON() {
		// TODO Auto-generated method stub
		return null;
	}
}