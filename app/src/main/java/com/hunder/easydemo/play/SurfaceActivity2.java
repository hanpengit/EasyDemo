package com.hunder.easydemo.play;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.View;

import com.hunder.easydemo.R;
import com.hunder.easydemo.base.BaseActivity;
import com.hunder.easylib.player.CustomVideoView;
import com.hunder.easylib.player.MediaPlayerListener;
import com.hunder.easylib.player.PlayerState;
import com.hunder.easylib.utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hp on 2020/3/23.
 */

public class SurfaceActivity2 extends BaseActivity implements MediaPlayerListener {

    @BindView(R.id.custom_video_view)
    CustomVideoView mVideoView;

    private MediaPlayer mMediaPlayer;
    private String mVideoPath = "https://www.bilibili.com/video/av42490922";
    private int mPlayerState = 0;
    private boolean mPlaying = false;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SurfaceActivity2.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_surface2;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mVideoView.setMediaPlayerListener(this);

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

                mPlayerState = PlayerState.PlayerState_Started;
                mPlaying = true;

                mVideoView.updateProgress();
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
        mVideoView.getSurfaceView().getHolder().addCallback(new SurfaceHolder.Callback() {

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
        if (mPlayerState == PlayerState.PlayerState_Paused) {
            ///pause();
        } else if (mPlayerState == PlayerState.PlayerState_Started) {
            mVideoView.postDelayed(new Runnable() {

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
                    mPlayerState = PlayerState.PlayerState_Paused;
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


    //------------------------- MediaPlayerListener -----------------------------

    @Override
    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    @Override
    public void start() {
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
            mMediaPlayer.setDisplay(mVideoView.getSurfaceView().getHolder()); //把视频画面输出到SurfaceView中
            prepare();
        } else if (!isPlaying()) {
            mMediaPlayer.start();
            mVideoView.updateProgress();
            mPlayerState = PlayerState.PlayerState_Started;
            mPlaying = true;
        }

        mVideoView.setPlayImage(R.mipmap.icon_pause);
    }

    @Override
    public void pause() {
        mMediaPlayer.pause();
        mVideoView.stopUpdateProgress();
        mVideoView.setPlayImage(R.mipmap.icon_play);

        mPlaying = false;
    }

    @Override
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mVideoView.stopUpdateProgress();
            mVideoView.stopUpdateTime();

            mVideoView.setPlayImage(R.mipmap.icon_play);
        }
    }

    @Override
    public void reset() {
        mMediaPlayer.reset();
    }

    @Override
    public void seekTo(int position) {
        mMediaPlayer.seekTo(position);
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer != null && mPlaying;
    }

    @Override
    public int getPlayerState() {
        return mPlayerState;
    }

}
