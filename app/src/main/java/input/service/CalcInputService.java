package input.service;

import android.content.res.Configuration;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.example.yosimizrachi.calckeyboard.R;

import java.util.ArrayList;

import input.calculator.CalcManager;
import input.calculator.CalculationItem;
import keyboard.CalcKeyboard;
import keyboard.CalcKeyboardView;
import keyboard.HistoryView;

/**
 * Created by yosimizrachi on 27/06/16.
 */
public class CalcInputService extends InputMethodService implements KeyboardView.OnKeyboardActionListener, AdapterView.OnItemClickListener {

    private static final String TAG = "CalcInputService";
    private final CalcManager mCalcManager = CalcManager.getInstance();
    private InputMethodManager mInputMethodManager;
    private CalcKeyboardView mKeyboardView;
    private CalcKeyboard mKeyboard;
    private HistoryView mHistoryListView;
    private StringBuilder mTextComposition = new StringBuilder();
    private int mLastDisplayWidth;
    private boolean historyShown = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    @Override
    public void onInitializeInterface() {
        if (mKeyboard != null) {
            // Configuration changes can happen after the keyboard gets recreated,
            // so we need to be able to re-build the keyboards if the available
            // space has changed.
            int displayWidth = getMaxWidth();
            if (displayWidth == mLastDisplayWidth) return;
            mLastDisplayWidth = displayWidth;
        }
        mKeyboard = new CalcKeyboard(this, R.xml.digits);
    }


    @Override
    public View onCreateInputView() {
        mKeyboardView = (CalcKeyboardView) getLayoutInflater().inflate(R.layout.input_layout, null);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setOnKeyboardActionListener(this);
        return mKeyboardView;
    }

    @Override
    public View onCreateCandidatesView() {
        // the layout for the history transactions
        mHistoryListView = (HistoryView) getLayoutInflater().inflate(R.layout.history_layout, null);
        mHistoryListView.setOnItemClickListener(this);
        return mHistoryListView;
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
                    String input = mTextComposition.toString();

                    // nothing entered - show message to user
                    if (input.length() == 0) {
                        printEmptyValue();
                        return;
                    }

                    // if not enough input to build a calculation
                    if (input.length() > 0 && input.length() < 3) {
                        return;
                    }

                    Integer result = mCalcManager.calculate(input);
                    // if null something is wrong with calculation
                    if (result == null) {
                        printError();
                        return;
                    }

                    reset();
                    mTextComposition.append(result);
                    ic.commitText(String.valueOf(result), 1);
                    if (historyShown) { // if history already shown to user - update the list
                        mHistoryListView.update();
                    }
                } catch (NumberFormatException exception) {
                    // "complicated expressions not yet implemented. For now only simple expression will be evaluated.
                    printError();
                }
                break;
            case CalcKeyboard.HISTORY:
                if (!historyShown) {
                    showHistory();
                } else {
                    hideHistory();
                }
                break;
            default:
                char code = (char) primaryCode;
                ic.commitText(String.valueOf(code), 1);
                mTextComposition.append(code);
        }
    }

    private void printError() {
        reset();
        getCurrentInputConnection().commitText(getString(R.string.error), 1);
    }

    private void printEmptyValue() {
        reset();
        getCurrentInputConnection().commitText(getString(R.string.empty), 1);
    }

    @Override
    public void onComputeInsets(final InputMethodService.Insets outInsets) {
        super.onComputeInsets(outInsets);
//        if (!isFullscreenMode()) {
        outInsets.touchableInsets = outInsets.contentTopInsets = outInsets.visibleTopInsets;
//        }

    }

    /**
     * Made a workaround for the bug of API>=23. please see {@link keyboard.HistoryAdapter#onClick(View)}
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<CalculationItem> items = CalcManager.getInstance().getHistoryItems();
        if (items != null && items.size() > 0) {
            CalculationItem item = items.get(position);
            InputConnection ic = getCurrentInputConnection();
            reset();
            mTextComposition.append(item.getCalculationString());
            ic.commitText(item.getCalculationString(), 1);

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (historyShown) {
            showHistory();
        } else {
            hideHistory();
        }
    }

    @Override
    public void setCandidatesViewShown(boolean shown) {
        super.setCandidatesViewShown(shown);
    }

    private void showHistory() {
        historyShown = true;
        mHistoryListView.loadHistory();
        setCandidatesViewShown(true);
    }

    private void hideHistory() {
        historyShown = false;
        setCandidatesViewShown(false);
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
