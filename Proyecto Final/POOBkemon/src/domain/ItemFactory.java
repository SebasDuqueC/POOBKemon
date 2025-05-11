package domain;

import java.util.ArrayList;
import java.util.List;

public class ItemFactory {

    public static List<Item> crearItemsBasicos() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("HyperPotion", TipoItem.HYPERPOTION, 2));
        items.add(new Item("Revive", TipoItem.REVIVE, 1));
        return items;
    }
}
