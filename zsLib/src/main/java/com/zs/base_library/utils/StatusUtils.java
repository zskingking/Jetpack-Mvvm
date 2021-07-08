package com.zs.base_library.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;

public class StatusUtils {

    /**
     * Use default color {@link #defaultColor_21} between 5.0 and 6.0.
     */
    public static final int USE_DEFAULT_COLOR = -1;

    /**
     * Use color {@link #setUseStatusBarColor} between 5.0 and 6.0.
     */
    public static final int USE_CUR_COLOR = -2;

    /**
     * Default status bar color between 21(5.0) and 23(6.0).
     * If status color is white, you can set the color outermost.
     */
    public static int defaultColor_21 = ColorUtils.parseColor("#33000000");

    /**
     * Setting the status bar color.
     * It must be more than 21(5.0) to be valid.
     *
     * @param color Status color.
     */
    public static void setUseStatusBarColor(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            color = Color.GRAY;
        }
        setUseStatusBarColor(activity, color, USE_CUR_COLOR);
    }

    /**
     * It must be more than 21(5.0) to be valid.
     * Setting the status bar color.Supper between 21 and 23.
     *
     * @param color        Status color.
     * @param surfaceColor Between 21 and 23,if surfaceColor == {@link #USE_DEFAULT_COLOR},the status color is defaultColor_21,
     *                     else if surfaceColor == {@link #USE_CUR_COLOR}, the status color is color,
     *                     else the status color is surfaceColor.
     */
    public static void setUseStatusBarColor(Activity activity, @ColorInt int color, int surfaceColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || (surfaceColor == USE_CUR_COLOR)) ? color : surfaceColor == USE_DEFAULT_COLOR ? defaultColor_21 : surfaceColor);
        }
    }

    /**
     * Setting the status bar transparently.
     * See {@link #setUseStatusBarColor}.
     */
    @Deprecated
    public static void setTransparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // after 21(5.0)
            setUseStatusBarColor(activity, Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // between 19(4.4) and 21(5.0)
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * Setting up whether or not to invade the status bar & status bar font color
     *
     * @param isTransparent 是否沉浸 Whether or not to invade the status bar?
     *                      If true, will invade the status bar,
     *                      otherwise, fits system windows.
     * @param isBlack       状态栏字体是否为黑色。
     *                      Whether the status bar font is set to black?
     *                      If true, the status bar font will be black,
     *                      otherwise, it is white.
     */
    public static void setSystemStatus(Activity activity, boolean isTransparent, boolean isBlack) {
        int flag = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && isTransparent) {
            // after 16(4.1)
            flag |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isBlack) {
            // after 23(6.0)
            flag |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(flag);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // between 19(4.4) and 21(5.0)
            if (isTransparent) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            ViewGroup contentView = (ViewGroup) activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
            View childAt = contentView.getChildAt(0);
            if (childAt != null) {
                childAt.setFitsSystemWindows(!isTransparent);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && OSUtils.isEMUI3_x()) {
            if (isTransparent) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            ViewGroup contentView = (ViewGroup) activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
            View childAt = contentView.getChildAt(0);
            if (childAt != null) {
                childAt.setFitsSystemWindows(!isTransparent);
            }
        }
    }

    /**
     * Get the height of the state bar by reflection.
     *
     * @return Status bar height if it is not equal to -1,
     */
    public static int getStatusBarHeight(Context context) {
        return getSizeByReflection(context, "status_bar_height");
    }


    /**
     * Get the height of the state bar by reflection.
     *
     * @return Status bar height if it is not equal to -1,
     */
    public static int getNavigationBarHeight(Context context) {
        return getSizeByReflection(context, "navigation_bar_height");
    }

    public static int getSizeByReflection(Context context, String field) {
        int size = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField(field).get(object).toString());
            size = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * Set bottom navigation bar color
     */
    public static void setNavigationBar(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(color);
        }
    }
}