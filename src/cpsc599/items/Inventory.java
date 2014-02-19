package cpsc599.items;

import java.util.ArrayList;

import cpsc599.util.Logger;

public class Inventory {
	ArrayList<Item> equip;
	ArrayList<Item> carry;
	
	public static final int HEAD_SLOT = 0;
	public static final int CHEST_SLOT = 1;
	public static final int RHAND_SLOT = 2;
	public static final int LHAND_SLOT = 3;
	public static final int LEGS_SLOT = 4;

	public Inventory()
	{
		Logger.debug("Inventory::contruc - Creating inventory.");
		equip = new ArrayList<Item>();
		carry = new ArrayList<Item>();
	}
}
