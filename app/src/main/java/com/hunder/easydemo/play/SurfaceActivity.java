package com.hunder.easydemo.play;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hunder.easydemo.R;
import com.hunder.easydemo.base.BaseActivity;
import com.hunder.easylib.player.PlayUtils;
import com.hunder.easylib.utils.FormatUtils;
import com.hunder.easylib.utils.LogUtils;
import com.hunder.easylib.utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hp on 2020/3/23.
 */

public class SurfaceActivity extends BaseActivity {

    @BindView(R.id.surface_view)
    SurfaceView mSurfaceView;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.seekbar)
    SeekBar mSeekBar;

    @BindView(R.id.media_controller_slide_up_down_icon)
    ImageView mMediaControllerSlideUpDownIcon;
    @BindView(R.id.media_controller_slide_up_down_text)
    TextView mMediaControllerSlideUpDownText;
    @BindView(R.id.media_controller_slide_up_down)
    LinearLayout mMediaControllerSlideUpDown;
    @BindView(R.id.media_controller_slide_icon)
    ImageView mMediaControllerSlideIcon;
    @BindView(R.id.media_controller_slide_time_target)
    TextView mMediaControllerSlideTimeTarget;
    @BindView(R.id.media_controller_slide_time)
    TextView mMediaControllerSlideTime;
    @BindView(R.id.media_controller_slide_left_right)
    LinearLayout mMediaControllerSlideLeftRight;

    @BindView(R.id.iv_play)
    ImageView mIvPlay;
    @BindView(R.id.tv_totaltime)
    TextView mTvTotaltime;
    @BindView(R.id.tv_play_speed)
    TextView mTvPlaySpeed;
    @BindView(R.id.tv_play_quality)
    TextView mTvPlayQuality;
    @BindView(R.id.ll_play_control)
    LinearLayout mLlPlayControl;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    private static final int INVISIBLE_CONTROLLER = 1;
    private static final int PlayerState_Started = 1;
    private static final int PlayerState_Paused = 2;

    private MediaPlayer mMediaPlayer;
    private SurfaceHolder mHolder;
    private String mVideoPath = "https://www.bilibili.com/video/av42490922";
    private int mDuration;
    private boolean mIsProgressUpdating;
    private int mPlayerState = 0;
    private boolean mPlaying = false;

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

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SurfaceActivity.class);
        context.startActivity(intent);
    }

    private void updateProgress() {
        getProgress();
        mProgressUpdateHandler.sendEmptyMessageDelayed(0, 1000);

        if (!mIsProgressUpdating) {
            mIsProgressUpdating = true;
        }
    }

    private void stopUpdateProgress() {
        mProgressUpdateHandler.removeMessages(0);

        if (mIsProgressUpdating) {
            mIsProgressUpdating = false;
        }
    }

    private void getProgress() {
        int position = mMediaPlayer.getCurrentPosition();
        mDuration = mMediaPlayer.getDuration();

        mSeekBar.setMax(mDuration);
        mSeekBar.setProgress(position);

        setTime(position, mDuration);

        LogUtils.d("update progress position:" + position);
    }

    private void setTime(int position, int duration) {
        mTvTime.setText(FormatUtils.formatTime(position) + " / " + FormatUtils.formatTime(duration));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_surface;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            private int mLastProgress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mMediaPlayer != null && fromUser) {
                    //mMediaPlayer.seekTo(progress);
                    LogUtils.d("seekbar onProgressChanged position:" + progress);

                    mMediaControllerSlideLeftRight.setVisibility(View.VISIBLE);
                    mMediaControllerSlideIcon.setImageResource(progress < mLastProgress ? R.mipmap.play_down_fast_forward : R.mipmap.play_fast_forward);
                    mMediaControllerSlideTimeTarget.setText(FormatUtils.formatTime(progress));
                    mMediaControllerSlideTime.setText(FormatUtils.formatTime(mDuration));

                    setTime(progress, mDuration);

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

                if (mMediaPlayer != null) {
                    mMediaPlayer.seekTo(seekBar.getProgress());
                }
                mMediaControllerSlideLeftRight.setVisibility(View.GONE);
                mLastProgress = 0;

                updateProgress();
                mHandler.sendEmptyMessageDelayed(INVISIBLE_CONTROLLER, 3000);
            }
        });

        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return PlayUtils.getInstance().onTouchListener(SurfaceActivity.this, event, new PlayUtils.OnSlideListener() {

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
                                setTime(position, mDuration);
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
                }, mSurfaceView, mMediaPlayer);
            }
        });

        initPlayer();
        initSurfaceView();
        prepare();
    }

    private void initPlayer() {
        //mVideoPath = "https://haokan.baidu.com/v?vid=8103185499800508930&tab=yinyue"; //此链接无法播放
        //mVideoPath = "https://vdept.bdstatic.com/4b75616d7432796a61427146367a3759/7955717966504233/67a0665d040704e74b92413d979462ce76765fe5af135f2712f37a968a6f3e78ca8da8bd06569712034e164c66a86e871643dcbe7236a3d6dd8b251bb38b7f32.mp4?auth_key=1585134362-0-0-d2109ba3c01a2b8271846851286baf42"; //此链接是上边链接中的视频的视频源链接，上边的是一个网页链接
        mVideoPath = "http://vfx.mtime.cn/Video/2018/07/06/mp4/180706094003288023.mp4";

        mMediaPlayer = new MediaPlayer();


        ///initSurfaceView();

        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                ToastUtils.showMessage("onPrepared");
                mMediaPlayer.start();

                mPlayerState = PlayerState_Started;
                mPlaying = true;

                updateProgress();
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                ToastUtils.showMessage("onCompletion");
                ///stop();
            }
        });

    }

    private void prepare() {
        try {
            mMediaPlayer.setDataSource(mVideoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaPlayer.prepareAsync();
    }

    private void initSurfaceView() {
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                mMediaPlayer.setDisplay(surfaceHolder); //设置播放的容器，将MediaPlayer和SurfaceView关联起来
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                ///stop();
            }
        });
    }

    private void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            stopUpdateProgress();
            setTime(0, mDuration);

            mIvPlay.setImageResource(R.mipmap.icon_play);
        }
    }

    private void start() {
        /*try {
            mMediaPlayer.reset();
            mMediaPlayer.setDisplay(mSurfaceView.getHolder());//把视频画面输出到SurfaceView中
            mMediaPlayer.setDataSource(mVideoPath);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        if (mMediaPlayer == null) {
            initPlayer();
            mMediaPlayer.setDisplay(mSurfaceView.getHolder()); //把视频画面输出到SurfaceView中
            prepare();
        } else if (!isPlaying()) {
            mMediaPlayer.start();
            updateProgress();
            mPlayerState = PlayerState_Started;
            mPlaying = true;
        }

        mIvPlay.setImageResource(R.mipmap.icon_pause);
    }

    private void pause() {
        mMediaPlayer.pause();
        stopUpdateProgress();
        mIvPlay.setImageResource(R.mipmap.icon_play);

        mPlaying = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePlayerState();
    }

    private void savePlayerState() {
        if (isPlaying()) {
            pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumePlayerState();
    }

    private void resumePlayerState() {
        if (mPlayerState == PlayerState_Paused) {
            ///pause();
        } else if (mPlayerState == PlayerState_Started) {
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    start();
                }
            }, 500);
        }
    }

    @OnClick({R.id.iv_play, R.id.tv_play_speed, R.id.tv_play_quality, R.id.iv_fullScreen, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play:
                if (isPlaying()) {
                    pause();
                    mPlayerState = PlayerState_Paused;
                } else {
                    start();
                }
                break;
            case R.id.tv_play_speed:
                break;
            case R.id.tv_play_quality:
                break;
            case R.id.iv_fullScreen:
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private boolean isPlaying() {
        return mMediaPlayer != null && mPlaying;
    }


}
