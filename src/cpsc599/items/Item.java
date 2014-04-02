package cpsc599.items;

public class Item {

	public String name;
	public boolean equipable;
	public int equipSlot;
    public int range;
    public int damage;
    public int weight;

    public Item(String nm, boolean equip, int slot) {
        this(nm, equip, slot, -1, -1, 0);
    }

    public Item(String nm, boolean equip, int slot, int damage) {
        this(nm, equip, slot, -1, damage, 0);
    }
	
	public Item(String nm, boolean equip, int slot, int range, int damage, int weight)
	{
		name = nm;
		equipable = equip;
		equipSlot = slot;
        this.range = range;
        this.damage = damage;
        this.weight = weight;
	}
}
