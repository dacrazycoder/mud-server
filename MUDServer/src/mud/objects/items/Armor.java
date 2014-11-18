package mud.objects.items;

import java.util.ArrayList;
import java.util.EnumSet;

import mud.Coins;
import mud.ObjectFlag;
import mud.interfaces.Wearable;
import mud.objects.Item;
import mud.objects.ItemType;
import mud.utils.Utils;

/**
 * 
 * @author Jeremy
 *
 * @param aMod Armor bonus modifier (integer)
 * @param aGroup err, no idea.
 */
public class Armor extends Item implements Wearable<Armor>
{
	/**
	 * Flag: I
	 * ItemType: Armor
	 */

	// weight - light, medium, heavy
	//private String group = "";
	private ArmorType armor_type;
	
	protected int mod = 0;                    // modifier - +0, +2, +3, +4, ... and so on

	public Armor() {
	}
	
	public Armor(int aMod, ArmorType armor)
	{
		super(-1, armor.getName().toLowerCase(), EnumSet.noneOf(ObjectFlag.class), "armor", 8);
		this.equippable = true;
		this.equip_type = ItemType.ARMOR;
		this.item_type = ItemType.ARMOR;
		
		//this.mod = aMod;
		this.mod = 0;
		
		this.armor_type = armor;
		this.weight = armor.getWeight();
	}
	
	public Armor(int dbref, int aMod, ArmorType armor)
	{
		super(dbref, armor.getName().toLowerCase(), EnumSet.noneOf(ObjectFlag.class), "armor", 8);
		this.equippable = true;
		this.equip_type = ItemType.ARMOR;
		this.item_type = ItemType.ARMOR;
		
		//this.mod = aMod;
		this.mod = 0;
		
		this.armor_type = armor;
		this.weight = armor.getWeight();
	}
	
	/**
	 * Armor - Copy Constructor
	 * 
	 * @see "Use this to make an identical copy of another armor. Just
	 * remember to change the database reference, etc accordingly"
	 * 
	 * @param template the armor to copy
	 */
	protected Armor( Armor template ) {
		super( template );
		
		this.armor_type = template.armor_type;
	}
	
	/**
	 * Object Loading Constructor
	 * 
	 * Use this only for testing purposes and loading objects into the
	 * server database, for anything else, use one of the other two constructors
	 * that have parameters.
	 * @param tempName
	 * @param tempDesc
	 * @param tempLoc
	 * @param tempDBREF
	 */
	public Armor(String wName, String wDesc, int wLoc, int wDBREF, int aMod, ArmorType armor)
	{
		super(wDBREF, wName, EnumSet.noneOf(ObjectFlag.class), wDesc, wLoc);
		this.equippable = true;
		this.equip_type = ItemType.ARMOR;
		
		//this.mod = aMod;
		this.mod = 0;
		
		this.item_type = ItemType.ARMOR;
		this.armor_type = armor;
		
		this.weight = armor.getWeight();
	}
	
	@Override
	public String getName() {
		return this.armor_type.getName() + " Armor";
	}
	
	public int getArmorBonus() {
		return this.armor_type.getArmorBonus();
	}
	
	public int getDexBonus() {
		return this.armor_type.getDexBonus();
	}
	
	public double getSpellFailure() {
		return this.armor_type.getSpellFailure();
	}
	
	@Override
	public Coins getValue() {
		return new Coins(armor_type.getCost());
	}
	
	public int getMod() {
		return this.mod;
	}
	
	public void setMod(int newMod) {
		this.mod = newMod;
	}
	
	@Override
	public String toDB() {
		String[] output = new String[8];
		output[0] = this.getDBRef() + "";          // database reference number
		output[1] = this.getName();                // name
		output[2] = this.getFlagsAsString();       // flags
		output[3] = this.getDesc();                // description
		output[4] = this.getLocation() + "";       // location
		output[5] = this.item_type.ordinal() + ""; // item type
		output[6] = this.armor_type.ordinal() + "";     // armor type
		output[7] = this.mod + "";                 // modifier
		return Utils.join(output, "#");
	}
	
	@Override
	public String toString() {
		if (this.mod > 0) {
			return "+" + this.mod + " " + this.getName();
		}
		else if (this.mod < 0) {
			return this.mod + " " + this.getName();
		}
		else {
			return this.getName();
		}
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Armor clone() {
		return new Armor(this);
	}
}