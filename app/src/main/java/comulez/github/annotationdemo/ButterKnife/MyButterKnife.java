package comulez.github.annotationdemo.ButterKnife;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Ulez on 2017/8/17.
 * Email：lcy1532110757@gmail.com
 */


public class MyButterKnife {

    public static void bind(final Activity activity) {
        try {
            Field[] fields = activity.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Bind.class)) {
                    Bind bind = field.getAnnotation(Bind.class);
                    int viewId = bind.id();
                    if (viewId <= 0) {
                        throw new Exception("viewId cannot be 0");
                    }
                    field.setAccessible(true);
                    View view = activity.findViewById(viewId);
                    field.set(activity, view);
                }
            }
            Method[] methods = activity.getClass().getDeclaredMethods();
            for (final Method method : methods) {
                if (method.isAnnotationPresent(OnClick.class)) {
                    OnClick bind = method.getAnnotation(OnClick.class);
                    int[] viewIds = bind.value();
                    for (int id : viewIds) {
                        if (id > 0) {
                            method.setAccessible(true);
                            View view = activity.findViewById(id);
                            if (view != null)
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            method.invoke(activity, v);
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        } catch (InvocationTargetException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
