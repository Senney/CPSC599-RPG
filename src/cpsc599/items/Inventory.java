package cpsc599.items;

import java.util.ArrayList;

import cpsc599.assets.Player;
import cpsc599.util.Logger;

public class Inventory {
	//private ArrayList<Item> equip;
	//private ArrayList<Item> carry;
	
	private static final int CARRY_SIZE = 3;
	private static final int EQUIP_SIZE = 5;
	
	private Item[] equip;
	private Item[] carry;

    public static final int NONE = -1;
	public static final int HEAD_SLOT = 0;
	public static final int CHEST_SLOT = 1;
	public static final int RHAND_SLOT = 2;
	public static final int LHAND_SLOT = 3;
	public static final int LEGS_SLOT = 4;
	
	private int carryIndex = 0;

    private Player owner;

	public Inventory(Player p)
	{
		Logger.debug("Creating inventory.");
		equip = new Item[EQUIP_SIZE];
		carry = new Item[CARRY_SIZE];
        this.owner = p;
	}
	
	public boolean pickUp(Item item)
	{
		/*if(carryIndex == CARRY_SIZE)
		{
			Logger.debug("Inventory::pickUp - pickup failed, Inventory full");
			return false;
		}
		carry[carryIndex] = item;
		carryIndex++;
		Logger.debug("Inventory::pickUp - you just picked up a " + item.name);
		return true;*/
		Logger.debug("you just picked up a " + item.name);
		return addItem(item);
	}
	
	public boolean equip(Item item)
	{
		if(equip[item.equipSlot] != null)
		{
			Item temp = equip[item.equipSlot];
			equip[item.equipSlot] = item;
			
			return addItem(temp);
		}
		equip[item.equipSlot] = item;
		removeItem(item);
		Logger.debug("you just equipped a " + item.name);
		return true;
	}
	
	public boolean use(Item item)
	{
		removeItem(item);
		Logger.debug("you just used a " + item.name);
		return true;
	}
	
	private boolean addItem(Item item)
	{
		if(carryIndex == CARRY_SIZE)
		{
			Logger.debug("pickup failed, Inventory full");
			return false;
		}
		carry[carryIndex] = item;
		carryIndex++;
		return true;
	}
	
	private boolean removeItem(Item item)
	{
		int index = -1;
		for(int count = 0; count < CARRY_SIZE; count++)
		{
			if(carry[count] == item)
				index = count;
		}
		if(carryIndex == 0)
		{
			Logger.debug("remove failed, Inventory empty");
			return false;
		}
		carry[index] = null;
		for(int count = index; count < CARRY_SIZE-1; count++)
		{
			carry[count] = carry[count+1];
		}
		carryIndex--;
		return true;
	}
	
	public Item getEquip(int slot)
	{
		return equip[slot];
	}
	
	public Item[] getCarry()
	{
		return carry;
	}
	
	public static void main(String[] args)
	{
		Item sword = new Item("sword", true, RHAND_SLOT);
		Item potion = new Item("potion", false, -1);
		
		Inventory inventory = new Inventory(new Player(null));
		
		inventory.pickUp(sword);
		inventory.pickUp(potion);
		
		inventory.equip(sword);
	}
}
