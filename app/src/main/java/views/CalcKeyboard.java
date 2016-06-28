package views;

import android.content.Context;
import android.inputmethodservice.Keyboard;

/**
 * Created by yosimizrachi on 27/06/16.
 */
public class CalcKeyboard extends Keyboard {

    public static final int SETTINGS_KEY = -200;
    public static final int HISTORY = -199;
    public static final int CALCULATE = 20;

    public CalcKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public CalcKeyboard(Context context, int xmlLayoutResId, int modeId, int width, int height) {
        super(context, xmlLayoutResId, modeId, width, height);
    }

    public CalcKeyboard(Context context, int xmlLayoutResId, int modeId) {
        super(context, xmlLayoutResId, modeId);
    }

    public CalcKeyboard(Context context, int layoutTemplateResId, CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }
}
