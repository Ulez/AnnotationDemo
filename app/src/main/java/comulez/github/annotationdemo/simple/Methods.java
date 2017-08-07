package comulez.github.annotationdemo.simple;

/**
 * Created by Ulez on 2017/8/7.
 * Email：lcy1532110757@gmail.com
 */


public class Methods {

    @TestAnnotation
    public int plus(int x, int y) {
        return x + y;
    }

    @TestAnnotation
    public int minus(int x, int y) {
        return x - y;
    }

    @TestAnnotation
    public int multiplied(int x, int y) {
        return x * y;
    }

    @TestAnnotation
    public int divided(int x, int y) {
        return x / y;
    }
}
