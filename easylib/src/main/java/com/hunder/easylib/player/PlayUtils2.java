package com.hunder.easylib.player;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hunder.easylib.utils.BrightnessUtils;
import com.hunder.easylib.utils.LogUtils;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by zhangxuan on 2016/8/3.
 */
public class PlayUtils2 {

    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;

    private static PlayUtils2 instance;

    private PlayUtils2() {
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }

    public static PlayUtils2 getInstance() {
        if (instance == null) {
            synchronized (PlayUtils2.class) {
                if (instance == null) {
                    instance = new PlayUtils2();
                }
            }
        }
        return instance;
    }

    boolean isPlaying = true;

    public void pause() {

    }


    public void smallScreen2FullScreen(Activity activity, View videoView, ImageView mCoursePlaybackDetailImg) {
        WindowManager wm = (WindowManager) (activity.getSystemService(Context.WINDOW_SERVICE));
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, wm.getDefaultDisplay().getHeight());
        mCoursePlaybackDetailImg.setLayoutParams(layoutParams);
        videoView.setLayoutParams(layoutParams);
    }

    public void smallScreen2FullScreen(Activity activity, View videoView) {
        WindowManager wm = (WindowManager) (activity.getSystemService(Context.WINDOW_SERVICE));
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) videoView.getLayoutParams();
        layoutParams.width = Math.max(wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight());
        layoutParams.height = Math.min(wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight());
        videoView.setLayoutParams(layoutParams);
    }

    public void fullScreen2SmallScreen(Activity activity, View videoView, int height, ImageView mCoursePlaybackDetailImg) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        mCoursePlaybackDetailImg.setLayoutParams(layoutParams);
        videoView.setLayoutParams(layoutParams);
    }

    public void fullScreen2SmallScreen(Activity activity, View videoView, int height) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        videoView.setLayoutParams(layoutParams);
    }


    /*public int setProgress(View videoView, ProgressBar mProgress, TextView mCurrentTime, TextView mEndTime) {
        if (videoView == null) {
            return 0;
        }
        int position = videoView.getCurrentPosition();
        int duration = videoView.getDuration();
        if (mProgress != null) {
            if (duration > 0) {
                // use long to avoid overflow
                long pos = 1000L * position / duration;
                mProgress.setProgress((int) pos);
            }
            int percent = videoView.getBufferPercentage();
            mProgress.setSecondaryProgress(percent * 10);
        }

        if (mEndTime != null) {
            mEndTime.setText("");
            mEndTime.setText(stringForTime(duration));
        }
        if (mCurrentTime != null) {
            mCurrentTime.setText("");
            mCurrentTime.setText(stringForTime(position));
        }

        return position;
    }*/

    public String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /**
     * 左右滑动时，上下滑动无效
     */
    private boolean onSlideUpDown;
    private boolean onSlideLeftRight;
    float sumMoveY = 0;
    private long mDownTime;
    private int mEffectiveSlidePosition = POSITON_UNEFFECTIVE;
    private float mDownX;
    private float mDownY;
    private float mUpX;
    private float mMoveX;
    private float mMoveY;
    public static final int SLIDE_LEFT = 0;
    public static final int SLIDE_RIGHT = 1;
    public static final int SLIDE_LEFT_UP_DOWN = 2;
    public static final int SLIDE_RIGHT_UP_DOWN = 3;
    /**
     * 无效的位置
     */
    private static final int POSITON_UNEFFECTIVE = -1;
    /**
     * 有效的移动距离
     */
    private static final int EFFECTIVE_MOVING_DISTANCE = 80;
    /**
     * 滑动整个屏幕 进度最多为5分钟
     */
    private static final int TIME_VIDEO_WIDTH = 5 * 60 * 1000;

    public boolean onTouchListener(Context context, MotionEvent event, OnSlideListener mOnSlideListener, View view, MediaPlayerListener mMediaPlayer) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownTime = System.currentTimeMillis();
                mDownX = event.getX();
                mDownY = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = event.getX() - mDownX;
                float y = event.getY();
                mMoveY = y - mDownY;
                mDownY = y;
                sumMoveY += mMoveY;

                if (mMediaPlayer != null && !onSlideLeftRight && (Math.tan(Math.toRadians(75)) < (Math.abs(mMoveY) / Math.abs(mMoveX)) || onSlideUpDown)) {
                    onSlideUpDown = true;
                    if (Math.abs(mDownX) >= view.getMeasuredWidth() / 2) {
                        //声音调节
                        Log.d("IjkVideoView", "voice");
                        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        int currentVoice = am.getStreamVolume(AudioManager.STREAM_MUSIC);

                        if (Math.round(Math.abs(sumMoveY) / (view.getHeight() / 255)) > 0) {
                            currentVoice = sumMoveY > 0 ? currentVoice - Math.round(Math.round(Math.abs(sumMoveY) / (view.getHeight() / 255)) / 10) : currentVoice + Math.round(Math.round(Math.abs(sumMoveY) / (view.getHeight() / 255)) / 10);
                            sumMoveY = Math.round(Math.round(Math.abs(sumMoveY) / (view.getHeight() / 255)) / 10) > 0 ? sumMoveY % (view.getHeight() / 255) : sumMoveY;
                            Log.d("IjkVideoView", "Math.round(Math.round(Math.abs(sumMoveY) / (getHeight() / 255) )/5):" + Math.round(Math.round(Math.abs(sumMoveY) / (view.getHeight()) / 255)) / 10);
                            Log.d("IjkVideoView", "sumMoveY:" + sumMoveY);
                        }
                        currentVoice = currentVoice < 0 ? 0 : (currentVoice > 15 ? 15 : currentVoice);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVoice, 0);
                        if (mOnSlideListener != null) {
                            mOnSlideListener.showSlideView(currentVoice, SLIDE_RIGHT_UP_DOWN);
                        }
                    } else {
                        //亮度调节
                        Log.d("IjkVideoView", mMoveY + "");
                        if (mEffectiveSlidePosition == POSITON_UNEFFECTIVE) {
                            mEffectiveSlidePosition = BrightnessUtils.getScreenBrightness(context);
                        }
                        ///BrightnessUtils.stopAutoBrightness((Activity) context);
                        if (Math.round(Math.abs(sumMoveY) / (view.getHeight() / 255)) > 0) {
                            mEffectiveSlidePosition = sumMoveY > 0 ? mEffectiveSlidePosition - Math.round(Math.abs(sumMoveY) / (view.getHeight() / 255)) : mEffectiveSlidePosition + Math.round(Math.abs(sumMoveY) / (view.getHeight() / 255));
                            sumMoveY = sumMoveY % (view.getHeight() / 255);
                        }
                        mEffectiveSlidePosition = mEffectiveSlidePosition < 0 ? 0 : (mEffectiveSlidePosition > 255 ? 255 : mEffectiveSlidePosition);

                        if (mOnSlideListener != null) {
                            mOnSlideListener.showSlideView(mEffectiveSlidePosition, SLIDE_LEFT_UP_DOWN);
                        }

                        BrightnessUtils.setBrightness((Activity) context, (int) mEffectiveSlidePosition);
                        ///BrightnessUtils.saveBrightness((Activity) context, (int) mEffectiveSlidePosition);
                    }

                } else {
                    if (mMediaPlayer != null && mEffectiveSlidePosition == POSITON_UNEFFECTIVE && Math.abs(mMoveX) > EFFECTIVE_MOVING_DISTANCE && !onSlideUpDown) {
                        onSlideLeftRight = true;
                        mEffectiveSlidePosition = mMediaPlayer.getCurrentPosition();
                    }
                    if (mEffectiveSlidePosition != POSITON_UNEFFECTIVE && !onSlideUpDown) {
                        if (mOnSlideListener != null) {
                            int position = computeMoveTime(mMoveX, view.getWidth(), mMediaPlayer.getDuration());
                            LogUtils.d("ACTION_MOVE position:"+position);

                            mOnSlideListener.showSlideView(computeMoveTime(mMoveX, view.getWidth(), mMediaPlayer.getDuration()), mMoveX > 0 ? SLIDE_RIGHT : SLIDE_LEFT);
                        }
                    }

                    ///ToastUtils.showMessage("--快进快退--");
                }

                break;
            case MotionEvent.ACTION_UP:
                mUpX = event.getX();
                sumMoveY = 0;
                float x = mUpX - mDownX;
                long upTime = System.currentTimeMillis();
                if (upTime - mDownTime < 500 && Math.abs(x) < EFFECTIVE_MOVING_DISTANCE) {
                    if (mOnSlideListener != null) {
                        mOnSlideListener.toggleControlView();
                    }
                }

                if (onSlideLeftRight && !onSlideUpDown) {
                    if (mEffectiveSlidePosition != POSITON_UNEFFECTIVE && Math.abs(mMoveX) > Math.abs(mMoveY)) {
                        int position = computeMoveTime(mMoveX, view.getWidth(), mMediaPlayer.getDuration());
                        LogUtils.d("ACTION_UP position:"+position);

                        mMediaPlayer.seekTo(computeMoveTime(mMoveX, view.getWidth(), mMediaPlayer.getDuration()));
                    }

                    ///ToastUtils.showMessage("--快进快退--");
                }

                if (mOnSlideListener != null) {
                    mOnSlideListener.hideSlideView();
                }

                mEffectiveSlidePosition = POSITON_UNEFFECTIVE;

                onSlideLeftRight = false;
                onSlideUpDown = false;
                break;
        }
        return true;
    }

    private int computeMoveTime(float distance, int width, int duration) {
        ///int time = mEffectiveSlidePosition + (int) (TIME_VIDEO_WIDTH * distance / width + 0.5);
        int time = mEffectiveSlidePosition + (int) (duration / 2 / width * distance + 0.5);
        time = time < 0 ? 0 : time;
        time = time > duration ? duration : time;
        return time;
    }

    public interface OnSlideListener {
        void showSlideView(int position, int distance);

        void hideSlideView();

        void toggleControlView();
    }
}
