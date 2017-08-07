package comulez.github.annotationdemo.a;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Ulez on 2017/8/4.
 * Email：lcy1532110757@gmail.com
 */

@TestAnnotation(id = 3, msg = "hello Annotation")//赋值；
public class Test {
    @Check(value = "hi")
    int a;


    @Perform//无属性，括号省略
    public void testMethod() {
    }

    public static void main(String[] args) {
        if (Test.class.isAnnotationPresent(TestAnnotation.class)) {
            TestAnnotation annotation = Test.class.getAnnotation(TestAnnotation.class);
            System.out.println("id=" + annotation.id());
            System.out.println("msg=" + annotation.msg());
        }
        try {
            Field a = Test.class.getDeclaredField("a");
            a.setAccessible(true);
            //获取一个成员变量上的注解
            Check check = a.getAnnotation(Check.class);

            if (check != null) {
                System.out.println("check value:" + check.value());
            }
            Method testMethod = Test.class.getDeclaredMethod("testMethod");
            if (testMethod != null) {
                // 获取方法中的注解
                Annotation[] ans = testMethod.getAnnotations();
                for (int i = 0; i < ans.length; i++) {
                    System.out.println("method testMethod annotation:" + ans[i].annotationType().getSimpleName());
                }
            }
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
