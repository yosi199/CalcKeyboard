package keyboard;

import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ListView;

import com.example.yosimizrachi.calckeyboard.R;


/**
 * Created by yosimizrachi on 08/07/16.
 */
public class HistoryListView extends ListView implements ValueAnimator.AnimatorUpdateListener {

    private ValueAnimator mAnimator;
    private int maxHeight;


    public HistoryListView(Context context) {
        super(context);
        init();
    }

    public HistoryListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public HistoryListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        maxHeight = (int) (getResources().getDimensionPixelSize(R.dimen.history_item_height) * 1.2);
        getTranslationY();
        setLayoutTransition(new LayoutTransition());
        setTranslationY(500);

        mAnimator = ValueAnimator.ofInt(500, 0);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addUpdateListener(this);
        mAnimator.setDuration(300);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, maxHeight);
    }


    public void animateHeight(boolean show) {
        if (show) {
            mAnimator.start();
        } else {
            mAnimator.reverse();
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        setTranslationY(value);
    }
}