package comulez.github.annotationdemo.ButterKnife;

import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by Ulez on 2017/8/21.
 * Email：lcy1532110757@gmail.com
 */


public class Utils {


    public static View findRequiredView(View source, @IdRes int id) {
        View view = source.findViewById(id);
        if (view != null) {
            return view;
        }
        throw new IllegalStateException("view '"
                + "' with ID "
                + id
                + " was not found.");
    }

    public static <T> T findRequiredViewAsType(View source, @IdRes int id, String who, Class<T> cls) {
        View view = findRequiredView(source, id);
        return castView(view, id, who, cls);
    }

    public static <T> T castView(View view, @IdRes int id, String who, Class<T> cls) {
        try {
            return cls.cast(view);
        } catch (ClassCastException e) {
            throw new IllegalStateException("View '"
                    + "' with ID "
                    + id
                    + " for "
                    + who
                    + " was of the wrong type. See cause for more info.", e);
        }
    }

}
