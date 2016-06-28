package input.service;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import com.example.yosimizrachi.calckeyboard.R;

import views.CalcKeyboard;
import views.CalcKeyboardView;

/**
 * Created by yosimizrachi on 27/06/16.
 */
public class CalcInputService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private InputMethodManager mInputMethodManager;
    private CalcKeyboardView mKeyboardView;
    private CalcKeyboard mKeyboard;
    private StringBuilder mTextComposition = new StringBuilder();

    @Override
    public void onCreate() {
        super.onCreate();
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    @Override
    public View onCreateInputView() {
        mKeyboardView = (CalcKeyboardView) getLayoutInflater().inflate(R.layout.input_layout, null);
        mKeyboard = new CalcKeyboard(this, R.xml.digits);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setOnKeyboardActionListener(this);
        return mKeyboardView;
    }

    @Override
    public View onCreateCandidatesView() {
        // the layout for the history transactions
        return super.onCreateCandidatesView();
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        mTextComposition.setLength(0);
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;
            case CalcKeyboard.SETTINGS_KEY:
                showInputChooser();
                break;
            case CalcKeyboard.CALCULATE:
                break;
            default:
                char code = (char) primaryCode;
                ic.commitText(String.valueOf(code), 1);
        }

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    private void showInputChooser() {
        if (mInputMethodManager != null) {
            mInputMethodManager.showInputMethodPicker();
        }
    }
}
