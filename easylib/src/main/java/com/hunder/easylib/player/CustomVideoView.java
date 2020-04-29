package com.hunder.easylib.player;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hunder.easylib.R;
import com.hunder.easylib.R2;
import com.hunder.easylib.utils.FormatUtils;
import com.hunder.easylib.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hp on 2020/4/27.
 */

public class CustomVideoView extends FrameLayout {

    @BindView(R2.id.surface_view)
    SurfaceView mSurfaceView;
    @BindView(R2.id.tv_time)
    TextView mTvTime;
    @BindView(R2.id.seekbar)
    SeekBar mSeekBar;

    @BindView(R2.id.media_controller_slide_up_down_icon)
    ImageView mMediaControllerSlideUpDownIcon;
    @BindView(R2.id.media_controller_slide_up_down_text)
    TextView mMediaControllerSlideUpDownText;
    @BindView(R2.id.media_controller_slide_up_down)
    LinearLayout mMediaControllerSlideUpDown;
    @BindView(R2.id.media_controller_slide_icon)
    ImageView mMediaControllerSlideIcon;
    @BindView(R2.id.media_controller_slide_time_target)
    TextView mMediaControllerSlideTimeTarget;
    @BindView(R2.id.media_controller_slide_time)
    TextView mMediaControllerSlideTime;
    @BindView(R2.id.media_controller_slide_left_right)
    LinearLayout mMediaControllerSlideLeftRight;

    @BindView(R2.id.iv_play)
    ImageView mIvPlay;
    @BindView(R2.id.tv_totaltime)
    TextView mTvTotaltime;
    @BindView(R2.id.tv_play_speed)
    TextView mTvPlaySpeed;
    @BindView(R2.id.tv_play_quality)
    TextView mTvPlayQuality;
    @BindView(R2.id.ll_play_control)
    LinearLayout mLlPlayControl;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;

    private static final int INVISIBLE_CONTROLLER = 1;

    private int mDuration;
    private boolean mIsProgressUpdating;

    private MediaPlayerListener mPlayerListener;

    private Handler mProgressUpdateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updateProgress();
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INVISIBLE_CONTROLLER:
                    mLlPlayControl.setVisibility(View.INVISIBLE);
                    mTvTitle.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };

    public CustomVideoView(Context context) {
        super(context);
        initView(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        // 绑定布局
        View view = View.inflate(context, R.layout.custom_video_view, this);
        ButterKnife.bind(view);

        setSeekBarListener();
        setSurfaceViewListener();
    }

    private void setSurfaceViewListener() {
        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return PlayUtils2.getInstance().onTouchListener(getContext(), event, new PlayUtils2.OnSlideListener() {

                    private int mLastProgress;

                    @Override
                    public void showSlideView(int position, int distance) {
                        switch (distance) {
                            case PlayUtils.SLIDE_LEFT:
                            case PlayUtils.SLIDE_RIGHT:
                                mMediaControllerSlideLeftRight.setVisibility(View.VISIBLE);
                                mMediaControllerSlideIcon.setImageResource(position < mLastProgress ? R.mipmap.play_down_fast_forward : R.mipmap.play_fast_forward);
                                mMediaControllerSlideTimeTarget.setText(FormatUtils.formatTime(position));
                                mMediaControllerSlideTime.setText(FormatUtils.formatTime(mDuration));

                                //mMediaPlayer.seekTo((int) position);

                                mSeekBar.setProgress(position);
                                updateTime(position, mDuration);
                                if (mIsProgressUpdating) {
                                    stopUpdateProgress();
                                }

                                mLastProgress = position;
                                break;

                            case PlayUtils.SLIDE_LEFT_UP_DOWN:
                                mMediaControllerSlideUpDown.setVisibility(View.VISIBLE);
                                mMediaControllerSlideUpDownIcon.setImageResource(R.mipmap.blightness);
                                mMediaControllerSlideUpDownText.setText((100 * position / 255) + "%");
                                break;

                            case PlayUtils.SLIDE_RIGHT_UP_DOWN:
                                mMediaControllerSlideUpDown.setVisibility(View.VISIBLE);
                                mMediaControllerSlideUpDownIcon.setImageResource(position <= 0 ? R.mipmap.voice_none : R.mipmap.voice);
                                mMediaControllerSlideUpDownText.setText((position * 100 / 15) + "%");
                                break;
                        }
                    }

                    @Override
                    public void hideSlideView() {
                        mMediaControllerSlideLeftRight.setVisibility(View.GONE);
                        mMediaControllerSlideUpDown.setVisibility(View.GONE);

                        if (!mIsProgressUpdating) {
                            mProgressUpdateHandler.removeMessages(0);
                            updateProgress();
                        }

                        mLastProgress = 0;
                    }

                    @Override
                    public void toggleControlView() {
                        // 处理标题栏和播放控制栏显示和隐藏
                        mHandler.removeMessages(INVISIBLE_CONTROLLER);

                        if (mLlPlayControl.getVisibility() == View.VISIBLE) {
                            mLlPlayControl.setVisibility(View.INVISIBLE);
                            mTvTitle.setVisibility(View.INVISIBLE);
                        } else {
                            mLlPlayControl.setVisibility(View.VISIBLE);
                            mTvTitle.setVisibility(View.VISIBLE);
                            mHandler.sendEmptyMessageDelayed(INVISIBLE_CONTROLLER, 3000);
                        }
                    }
                }, mSurfaceView, mPlayerListener);
            }
        });
    }

    private void setSeekBarListener() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            private int mLastProgress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //mMediaPlayer.seekTo(progress);
                    LogUtils.d("seekbar onProgressChanged position:" + progress);

                    mMediaControllerSlideLeftRight.setVisibility(View.VISIBLE);
                    mMediaControllerSlideIcon.setImageResource(progress < mLastProgress ? R.mipmap.play_down_fast_forward : R.mipmap.play_fast_forward);
                    mMediaControllerSlideTimeTarget.setText(FormatUtils.formatTime(progress));
                    mMediaControllerSlideTime.setText(FormatUtils.formatTime(mDuration));

                    updateTime(progress, mDuration);

                    mLastProgress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopUpdateProgress();
                mHandler.removeMessages(INVISIBLE_CONTROLLER);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LogUtils.d("seekbar onStopTrackingTouch position:" + seekBar.getProgress());

                if (mPlayerListener != null) {
                    mPlayerListener.seekTo(seekBar.getProgress());
                }
                mMediaControllerSlideLeftRight.setVisibility(View.GONE);
                mLastProgress = 0;

                updateProgress();
                mHandler.sendEmptyMessageDelayed(INVISIBLE_CONTROLLER, 3000);
            }
        });
    }

    public void setMediaPlayerListener(MediaPlayerListener playerListener) {
        mPlayerListener = playerListener;
    }

    public void updateProgress() {
        getProgress();
        mProgressUpdateHandler.sendEmptyMessageDelayed(0, 1000);

        if (!mIsProgressUpdating) {
            mIsProgressUpdating = true;
        }
    }

    public void stopUpdateProgress() {
        mProgressUpdateHandler.removeMessages(0);

        if (mIsProgressUpdating) {
            mIsProgressUpdating = false;
        }
    }

    private void getProgress() {
        int position = mPlayerListener.getCurrentPosition();
        mDuration = mPlayerListener.getDuration();

        mSeekBar.setMax(mDuration);
        mSeekBar.setProgress(position);

        updateTime(position, mDuration);

        LogUtils.d("update progress position:" + position);
    }

    public void updateTime(int position, int duration) {
        mTvTime.setText(FormatUtils.formatTime(position) + " / " + FormatUtils.formatTime(duration));
    }

    public void stopUpdateTime() {
        updateTime(0, mDuration);
    }

    public SurfaceView getSurfaceView() {
        return mSurfaceView;
    }

    public void setPlayImage(int resId) {
        mIvPlay.setImageResource(resId);
    }

}
