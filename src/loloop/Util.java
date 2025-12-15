package loloop;

public class Util {
    public static int divideCeil(int a, int b) {
        double r = (double) (a) / (double) (b);

        r = Math.ceil(r);

        return (int) r;
    }

    public static int divideFloor(int a, int b) {
        double r = (double) (a) / (double) (b);

        r = Math.floor(r);

        return (int) r;
    }
}
