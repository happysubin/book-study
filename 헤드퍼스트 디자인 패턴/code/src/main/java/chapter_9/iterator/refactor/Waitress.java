package chapter_9.iterator.refactor;

import chapter_9.iterator.pure.MenuItem;
import java.util.Iterator;

public class Waitress {

	Menu pancakeHouseMenu;
	Menu dinerMenu;
	Menu cafeMenu;

	public Waitress(Menu pancakeHouseMenu, Menu dinerMenu, Menu cafeMenu) {
		this.pancakeHouseMenu = pancakeHouseMenu;
		this.dinerMenu = dinerMenu;
		this.cafeMenu = cafeMenu;
	}

	public void printMenu() {
		Iterator<MenuItem> pancakeHouseMenuIterator = pancakeHouseMenu.createIterator();
		Iterator<MenuItem> dinerMenuIterator = dinerMenu.createIterator();
		Iterator<MenuItem> cafeMenuIterator = cafeMenu.createIterator();

		System.out.println("아침 메뉴");
		printMenu(pancakeHouseMenuIterator);
		System.out.println("점심 메뉴");
		printMenu(dinerMenuIterator);
		System.out.println("저녁 메뉴");
		printMenu(cafeMenuIterator);
	}
   
	void printMenu(Iterator<MenuItem> iterator) {
		while (iterator.hasNext()) {
			MenuItem menuItem = iterator.next();
			System.out.print(menuItem.getName() + ", ");
			System.out.print(menuItem.getPrice() + " -- ");
			System.out.println(menuItem.getDescription());
		}
	}
}  
