package cpsc599.items;

public class Item {

	public String name;
	public boolean equipable;
	public int equipSlot;
	
	public Item(String nm, boolean equip, int slot)
	{
		name = nm;
		equipable = equip;
		equipSlot = slot;
	}
}
