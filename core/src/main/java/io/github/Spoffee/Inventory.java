package io.github.Spoffee;

import java.util.ArrayList;

public class Inventory {
    private int itemsUsed = 0;
    private ArrayList<Item> inventoryContents = new ArrayList<>();

    public boolean pickUpItem(Item item) {
        if (item == null) {
            return false;
        }
        inventoryContents.add(item);
        return true;
    }

    /**
     * Returns the item at the given inventory position.
     * This is zero-indexed.
     *
     * @param inventoryPosition zero-based index into the inventory
     * @return the item, or null if the position is out of range
     */
    public Item getItem(int inventoryPosition) {

        if (inventoryPosition < 0 || inventoryPosition >= inventoryContents.size()) {
            return null;
        }
        return inventoryContents.get(inventoryPosition);
    }

    public int getItemsUsed() {
        return itemsUsed;
    }

    /**
     * Removes the item at the given inventory position. Returns true on success.
     * Also increments the used items counter.
     *
     * @param inventoryPosition The inventory position to remove the item from
     * @return true if the item was removed, false if the position was out of range
     */
    public boolean useItem(int inventoryPosition) {
        if (removeItem(inventoryPosition)) {
            itemsUsed++;
            return true;
        }

        return false;
    }

    private boolean removeItem(int inventoryPosition) {
        if (inventoryPosition < 0 || inventoryPosition >= inventoryContents.size()) {
            return false;
        }
        inventoryContents.remove(inventoryPosition);
        return true;
    }
}
