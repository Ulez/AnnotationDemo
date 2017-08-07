package comulez.github.annotationdemo.simple;

import java.lang.reflect.Method;

/**
 * Created by Ulez on 2017/8/7.
 * Email：lcy1532110757@gmail.com
 */


public class TestMain {
    public static void main(String[] arg) {
        Methods methods = new Methods();
        Class clz = methods.getClass();
        Method[] mes = clz.getDeclaredMethods();

        for (Method method : mes) {
            if (method.isAnnotationPresent(TestAnnotation.class)) {//标记过注解的执行才测试方法。
                try {
                    method.setAccessible(true);
                    System.out.println(method.getName() + ",result=" + method.invoke(methods, 18, 2));
                } catch (Exception e) {
                    System.out.println("error method name=" + method.getName());
                    e.printStackTrace();
                }
            }
        }
    }
}
