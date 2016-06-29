package input.service;

import android.animation.ValueAnimator;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.example.yosimizrachi.calckeyboard.R;

import input.calculator.CalcManager;
import keyboard.CalcKeyboard;
import keyboard.CalcKeyboardView;
import keyboard.HistoryLayout;
import keyboard.HistoryView;

/**
 * Created by yosimizrachi on 27/06/16.
 */
public class CalcInputService extends InputMethodService implements KeyboardView.OnKeyboardActionListener, ViewTreeObserver.OnPreDrawListener {

    private static final String TAG = "CalcInputService";
    private final CalcManager mCalcManager = CalcManager.getInstance();
    int candidateHeight = 0;
    int treeHeight = 0;
    private InputMethodManager mInputMethodManager;
    private CalcKeyboardView mKeyboardView;
    private CalcKeyboard mKeyboard;
    private HistoryLayout mHistoryLayout;
    private HistoryView mHistoryListView;
    private StringBuilder mTextComposition = new StringBuilder();
    private boolean historyShown = false;
    private Insets mInstes;
    private int mHistoryHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mHistoryHeight = getBaseContext().getResources().getDimensionPixelSize(R.dimen.history_height);
    }

    @Override
    public View onCreateInputView() {
        mKeyboardView = (CalcKeyboardView) getLayoutInflater().inflate(R.layout.input_layout, null);
        mKeyboard = new CalcKeyboard(this, R.xml.digits);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setOnKeyboardActionListener(this);
        mKeyboardView.getViewTreeObserver().addOnPreDrawListener(this);
        super.setCandidatesViewShown(true);
        return mKeyboardView;
    }

    @Override
    public View onCreateCandidatesView() {
        // the layout for the history transactions
        mHistoryLayout = (HistoryLayout) getLayoutInflater().inflate(R.layout.history_layout, null);
        mHistoryListView = (HistoryView) mHistoryLayout.findViewById(R.id.history);
//        mHistoryLayout.setTranslationY(500);

        mHistoryLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (candidateHeight != mHistoryLayout.getHeight()) {
                    candidateHeight = mHistoryLayout.getHeight();
                } else {
                    mHistoryLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mHistoryLayout.getLayoutParams();
                    params.height = 0;
                    mHistoryLayout.setLayoutParams(params);
                }
                return false;
            }
        });

        return mHistoryLayout;
    }

    @Override
    public void onFinishInput() {
        super.onFinishInput();
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
                        mHistoryListView.update();
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
                    mHistoryListView.loadHistory();
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

    @Override
    public void onComputeInsets(final InputMethodService.Insets outInsets) {
        mInstes = outInsets;
//        super.onComputeInsets(outInsets);
        if (!isFullscreenMode()) {
            outInsets.contentTopInsets = outInsets.visibleTopInsets;
        }

    }

    @Override
    public void setCandidatesViewShown(boolean shown) {
//        super.setCandidatesViewShown(shown);
        ValueAnimator anim = ValueAnimator.ofInt(0, candidateHeight);
        anim.setDuration(100);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mHistoryLayout.getLayoutParams();
                params.height = value;
                mHistoryLayout.setLayoutParams(params);
//                mInstes.visibleTopInsets = value;
//                mInstes.contentTopInsets = value;
            }
        });
        if (shown) {
            anim.start();
        } else {
            anim.reverse();
        }
//        mHistoryLayout.animateToPosition(shown);
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

    @Override
    public boolean onPreDraw() {
        if (treeHeight != mKeyboardView.getBottom()) {
            treeHeight = mKeyboardView.getBottom();
        } else {
            mKeyboardView.getViewTreeObserver().removeOnPreDrawListener(this);

        }


        return true;
    }
}
