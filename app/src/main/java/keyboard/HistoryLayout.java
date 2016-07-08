package keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.FrameLayout;

import com.example.yosimizrachi.calckeyboard.R;

/**
 * Created by yosimizrachi on 08/07/16.
 */
public class HistoryLayout extends FrameLayout {

    /**
     * The handler used to execute delayed operation (like animations)
     */
    private Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * A view used to notify user of info and events
     */
    private View mEmptyView;

    /**
     * the max height this layout can have
     */
    private int maxHeight;

    /**
     * a flag indicating whether the "version" notification should be shown or not.
     */
    private boolean seenNotification;
    private Runnable notificationRunner = new Runnable() {
        @Override
        public void run() {
            mEmptyView.animate().setInterpolator(new AnticipateInterpolator()).setDuration(300).scaleY(0.7f).scaleX(0.7f).translationY(200).start();
            seenNotification = true;
        }
    };

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
        seenNotification = getNotificationStatus();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mEmptyView = findViewById(R.id.empty_view);
        //if first time - show the notification
        if (!seenNotification) {
            mEmptyView.setVisibility(VISIBLE);
            mHandler.postDelayed(notificationRunner, 3000);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, maxHeight);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        saveNotificationStatus(seenNotification);

        if (mHandler != null) {
            mHandler.removeCallbacks(notificationRunner);
            mHandler = null;
        }

        mEmptyView = null;
    }

    private void saveNotificationStatus(boolean wasSeen) {
        SharedPreferences.Editor editor = getContext().getSharedPreferences(getContext().getString(R.string.conf), Context.MODE_PRIVATE).edit();
        editor.putBoolean(getContext().getString(R.string.seen_notification), wasSeen);
        editor.apply();
    }

    private boolean getNotificationStatus() {
        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.conf), Context.MODE_PRIVATE);
        return prefs.getBoolean(getContext().getString(R.string.seen_notification), false);
    }
}
