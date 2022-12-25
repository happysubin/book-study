package chapter_9.iterator.refactor;

import chapter_9.iterator.pure.MenuItem;

import java.util.Iterator;

public interface Menu {
    Iterator<MenuItem> createIterator();
}
