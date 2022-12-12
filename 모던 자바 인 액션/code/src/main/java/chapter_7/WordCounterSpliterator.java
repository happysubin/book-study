package chapter_7;

import java.util.Spliterator;
import java.util.function.Consumer;

public class WordCounterSpliterator implements Spliterator<Character> {

    private final String string;
    private int currentChar = 0;

    public WordCounterSpliterator(String string) {
        this.string = string;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(string.charAt(currentChar++)); //현재 문자를 소비
        return currentChar < string.length(); //소비할 문자가 있으면 true를 반환
    }

    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar;
        if(currentSize < 10){
            return null; //파싱할 문자열을 순차 처리할 수 있을만큼 작아졌ㄷ면 null을 반환
        }

        for(int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++){
            if(Character.isWhitespace(string.charAt(splitPos))){ //다음 공백이 나올 때 까지 분할 위치를 뒤로 이동
                WordCounterSpliterator spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
                currentChar = splitPos;
                return spliterator; //공백을 찾았고 문자열을 분할했으므로 루프를 종료
            }
        }

        return null;
    }

    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
