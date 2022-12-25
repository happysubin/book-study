package chapter_9.iterator.refactor;


import chapter_9.iterator.pure.MenuItem;

import java.util.Iterator;

public class DinerMenuIterator implements Iterator<MenuItem> {

    private MenuItem[] items;
    int position = 0;

    public DinerMenuIterator(MenuItem[] menuItems) {
        this.items = menuItems;
    }

    @Override
    public boolean hasNext() {
        if(position >= items.length || items[position] == null){
            return false;
        }
        return true;
    }

    @Override
    public MenuItem next() {
        MenuItem menuItem = items[position];
        position = position + 1;
        return menuItem;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("메뉴 항목을 지우면 안됩니다");
    }
}
