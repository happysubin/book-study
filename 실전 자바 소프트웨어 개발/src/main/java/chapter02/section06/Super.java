package chapter02.section06;

public class Super {

    static class Super1 {
        int a;

        public Super1() {

        }

        public Super1(int a) {
            this.a = a;
        }
    }

    static class Super2 extends Super1 {

        public Super2() {
        }
    }


}
