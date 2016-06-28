package input.service;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import com.example.yosimizrachi.calckeyboard.R;

import input.calculator.CalcManager;
import keyboard.CalcKeyboard;
import keyboard.CalcKeyboardView;
import keyboard.HistoryAdapter;
import keyboard.HistoryView;

/**
 * Created by yosimizrachi on 27/06/16.
 */
public class CalcInputService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private static final String TAG = "CalcInputService";
    private final CalcManager mCalcManager = CalcManager.getInstance();
    private InputMethodManager mInputMethodManager;
    private CalcKeyboardView mKeyboardView;
    private CalcKeyboard mKeyboard;
    private HistoryView mHistoryView;
    private StringBuilder mTextComposition = new StringBuilder();
    private boolean historyShown = false;

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
        mHistoryView = (HistoryView) getLayoutInflater().inflate(R.layout.history_layout, null);
        return mHistoryView;
//        return super.onCreateCandidatesView();
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                reset();
                break;
            case CalcKeyboard.SETTINGS_KEY:
                showInputChooser();
                break;
            case CalcKeyboard.CALCULATE:
                try {
                    int result = mCalcManager.calculate(mTextComposition.toString());
                    reset();
                    mTextComposition.append(result);
                    ic.commitText(String.valueOf(result), 1);
                    if (historyShown) { // if history already shown to user - update the list
                        ((HistoryAdapter) mHistoryView.getAdapter()).notifyDataSetChanged();
                    }
                } catch (NumberFormatException exception) {
                    // "complicated expressions not yet implemented. For now only simple expression will be evaluated.
                    reset();
                    ic.commitText(getString(R.string.error), 1);
                }
                break;
            case CalcKeyboard.HISTORY:
                if (!historyShown) {
                    historyShown = true;
                    mHistoryView.loadHistory();
                    setCandidatesViewShown(true);
                } else {
                    historyShown = false;
                    setCandidatesViewShown(false);
                }
                break;
            default:
                char code = (char) primaryCode;
                ic.commitText(String.valueOf(code), 1);
                mTextComposition.append(code);
        }
    }


    private void showInputChooser() {
        if (mInputMethodManager != null) {
            mInputMethodManager.showInputMethodPicker();
        }
    }

    private void reset() {
        mTextComposition.delete(0, mTextComposition.length());
        getCurrentInputConnection().deleteSurroundingText(1000, 1000);
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

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

}
