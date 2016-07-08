package keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.yosimizrachi.calckeyboard.R;

/**
 * Created by yosimizrachi on 08/07/16.
 */
public class HistoryLayout extends FrameLayout {

    private int maxHeight;

    public HistoryLayout(Context context) {
        super(context);
        init();
    }

    public HistoryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public HistoryLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        maxHeight = (int) (getResources().getDimensionPixelSize(R.dimen.history_item_height) * 1.2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, maxHeight);
    }
}
