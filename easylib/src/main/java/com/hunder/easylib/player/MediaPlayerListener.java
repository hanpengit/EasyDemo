package com.hunder.easylib.player;

/**
 * Created by hp on 2020/4/28.
 */

public interface MediaPlayerListener {

    int getCurrentPosition();

    int getDuration();

    void start();

    void pause();

    void stop();

    void reset();

    // @param position the offset in milliseconds from the start to seek to
    void seekTo(int position);

    boolean isPlaying();

    // PlayerState.PlayerState_Started
    int getPlayerState();

}
