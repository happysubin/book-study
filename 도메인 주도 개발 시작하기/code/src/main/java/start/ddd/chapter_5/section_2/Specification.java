package start.ddd.chapter_5.section_2;

public interface Specification <T> {
    boolean isSatisfiedBy(T agg);
}
