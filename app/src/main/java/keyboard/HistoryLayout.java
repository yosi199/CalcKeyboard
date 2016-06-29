package keyboard;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;


/**
 * Created by yosimizrachi on 29/06/2016.
 */
public class HistoryLayout extends LinearLayout {

    private ObjectAnimator mAnimator;

    public HistoryLayout(Context context) {
        super(context);
    }

    public HistoryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HistoryLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setTranslationY(500);

        mAnimator = ObjectAnimator.ofFloat(this, "translationY", 500, 0);
        mAnimator.setDuration(500);

    }

    public void animateToPosition(boolean shown) {
        if (shown) {
            mAnimator.start();
        } else {
            mAnimator.reverse();
        }
    }


}
