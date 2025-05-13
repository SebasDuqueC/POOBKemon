package domain;

import java.util.ArrayList;
import java.util.List;

public class ItemFactory {

    public static List<Item> crearItemsBasicos() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("HyperPotion", TipoItem.HYPERPOCION, 2));
        items.add(new Item("Revive", TipoItem.REVIVIR, 1));
        return items;
    }
}
