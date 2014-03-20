package cpsc599.items;

public class Item {

	public String name;
	public boolean equipable;
	public int equipSlot;
    public int range;
	
	public Item(String nm, boolean equip, int slot, int range)
	{
		name = nm;
		equipable = equip;
		equipSlot = slot;
        this.range = range;
	}
}
