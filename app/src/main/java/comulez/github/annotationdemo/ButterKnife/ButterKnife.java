package comulez.github.annotationdemo.ButterKnife;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * Created by Ulez on 2017/8/21.
 * Email：lcy1532110757@gmail.com
 */


public class ButterKnife {

    public static final String TAG = "ButterKnife";

    public static Unbinder bind(Activity target) {
        View sourceView = target.getWindow().getDecorView();
        return createBinding(target, sourceView);
    }


    private static Unbinder createBinding(Object target, View source) {
        Class<?> targetClass = target.getClass();
        Constructor<? extends Unbinder> constructor = findViewBinder(targetClass);
        try {
            return constructor.newInstance(target, source);//这里调用了2个参数的构造方法。生成实例并且绑定了对象；
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Constructor<? extends Unbinder> findViewBinder(Class<?> cls) {
        String clsName = cls.getName();
        try {
            Class<?> bindingClass = cls.getClassLoader().loadClass(clsName + "_ViewBinding");
            return (Constructor<? extends Unbinder>) bindingClass.getConstructor(cls, View.class);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "ClassNotFoundException Not found.");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find binding constructor for " + clsName, e);
        }
        return null;
    }
}
