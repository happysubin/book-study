package chapter_09.iterator.refactor;

import chapter_09.iterator.pure.MenuItem;

import java.util.Iterator;

public interface Menu {
    Iterator<MenuItem> createIterator();
}
