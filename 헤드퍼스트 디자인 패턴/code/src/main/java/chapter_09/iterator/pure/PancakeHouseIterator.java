package chapter_09.iterator.pure;


import java.util.List;

public class PancakeHouseIterator implements Iterator {

	List<MenuItem> items;
	int position = 0;

	public PancakeHouseIterator(List<MenuItem> menuItems) {
		this.items = menuItems;
	}

	@Override
	public boolean hasNext() {
		return items.size() > position;
	}

	@Override
	public MenuItem next() {
		return items.get(position++);
	}
}
