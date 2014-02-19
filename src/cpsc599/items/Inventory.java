package cpsc599.items;

import java.util.ArrayList;

import cpsc599.util.Logger;

public class Inventory {
	ArrayList<Item> equip;
	ArrayList<Item> carry;
	
	public static final int CARRY_SIZE = 3;
	public static final int EQUIP_SIZE = 5;
	
	public static final int HEAD_SLOT = 0;
	public static final int CHEST_SLOT = 1;
	public static final int RHAND_SLOT = 2;
	public static final int LHAND_SLOT = 3;
	public static final int LEGS_SLOT = 4;

	public Inventory()
	{
		Logger.debug("Inventory::contruc - Creating inventory.");
		equip = new ArrayList<Item>(EQUIP_SIZE);
		carry = new ArrayList<Item>(CARRY_SIZE);
	}
	
	public boolean pickUp(Item item)
	{
		if(carry.get(CARRY_SIZE) != null)
		{
			Logger.debug("Inventory::pickUp - pickup failed, Inventory full");
			return false;
		}
		carry.add(item);
		Logger.debug("Inventory::pickUp - you just picked up a " + item.name);
		return true;
	}
	
	public boolean equip(Item item)
	{
		if(equip.get(item.equipSlot) == null)
		{
			Logger.debug("Inventory::equip - equip failed, you have something there already");
			return false;
		}
		equip.add(item.equipSlot, item);
		carry.remove(item);
		Logger.debug("Inventory::pickUp - you just equipped a " + item.name);
		return true;
	}
	
	public boolean use(Item item)
	{
		carry.remove(item);
		Logger.debug("Inventory::pickUp - you just used a " + item.name);
		return true;
	}
}
