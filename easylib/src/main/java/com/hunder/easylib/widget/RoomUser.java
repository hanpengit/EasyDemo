package com.hunder.easylib.widget;

import android.text.TextUtils;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.hunder.easylib.widget.AppRTCUtils.*;

public class RoomUser {
    public static int PUBLISH_STATE_AUDIOONLY;
    public static int PUBLISH_STATE_BOTH;
    public static int PUBLISH_STATE_MUTE_ALL;
    public static int PUBLISH_STATE_NONE = 0;
    public static int PUBLISH_STATE_VIDEOONLY;
    public int audioPublished;
    public boolean canDraw = false;
    public boolean disableaudio = false;
    public boolean disablevideo = false;
    public boolean enableDualStream = false;
    public boolean hasAudio = true;
    public boolean hasVideo = true;
    public String nickName = "";
    public String peerId = "";
    private HashMap<String, HashMap<String, Boolean>> playingStream = new HashMap();
    public ConcurrentHashMap<String, Object> properties = null;
    public int publishState = PUBLISH_STATE_NONE;
    public int role = -1;
    public int videoPublished;
    int watchStatus = 0;

    static {
        PUBLISH_STATE_AUDIOONLY = 1;
        PUBLISH_STATE_VIDEOONLY = 2;
        PUBLISH_STATE_BOTH = 3;
        PUBLISH_STATE_MUTE_ALL = 4;
    }

    RoomUser() {
        this.properties = new ConcurrentHashMap();
    }

    RoomUser(Map<String, String> paramMap) {
        this.peerId = ((String) paramMap.get("id"));
        ///((String) paramMap.get("streams"));
        String str = (String) paramMap.get("properties");
        if ((str == null) || (!(str instanceof String)) || (str.isEmpty())) ;
       /* while (true) {
            return;
            try {
                this.properties = AppRTCUtils.jsonToMap1(new JSONObject(str));
                if (this.properties.containsKey("role"))
                    this.role = Integer.parseInt(this.properties.get("role").toString());
                if (this.properties.containsKey("nickname"))
                    this.nickName = this.properties.get("nickname").toString();
                this.hasAudio = getBoolean("hasaudio");
                this.hasVideo = getBoolean("hasvideo");
                this.canDraw = getBoolean("candraw");
                if (!this.properties.containsKey("publishstate"))
                    continue;
                this.publishState = Integer.parseInt(this.properties.get("publishstate").toString());
                return;
            } catch (JSONException localJSONException) {
                localJSONException.printStackTrace();
            }
        }*/
    }

    RoomUser(JSONObject paramJSONObject) {
        this.peerId = paramJSONObject.optString("id");
        paramJSONObject.optString("streams");
        String str = paramJSONObject.optString("properties");
        if ((str == null) || (!(str instanceof String)) || (str.isEmpty())) ;
        /*while (true) {
            return;
            try {
                this.properties = jsonToMap1(new JSONObject(str));
                if (this.properties.containsKey("role"))
                    this.role = Integer.parseInt(this.properties.get("role").toString());
                if (this.properties.containsKey("nickname"))
                    this.nickName = this.properties.get("nickname").toString();
                this.hasAudio = getBoolean("hasaudio");
                this.hasVideo = getBoolean("hasvideo");
                this.disablevideo = getBoolean("disablevideo");
                this.disableaudio = getBoolean("disableaudio");
                this.canDraw = getBoolean("candraw");
                if (!this.properties.containsKey("publishstate"))
                    continue;
                this.publishState = Integer.parseInt(this.properties.get("publishstate").toString());
                return;
            } catch (JSONException localJSONException) {
                localJSONException.printStackTrace();
            }
        }*/
    }

    private boolean getBoolean(String paramString) {
        if (!this.properties.containsKey(paramString)) ;
        /*do {
            return false;
            Object localObject = this.properties.get(paramString);
            if ((localObject instanceof Boolean))
                return ((Boolean) localObject).booleanValue();
        }*/
        while (Integer.valueOf(this.properties.get(paramString).toString()).intValue() == 0);
        return true;
    }

    public int audioStatus() {
        int i = 0;
        Iterator localIterator = this.properties.keySet().iterator();
        while (localIterator.hasNext()) {
            String str = (String) localIterator.next();
            if (!str.startsWith("publishstate"))
                continue;
            int j = ((Integer) this.properties.get(str)).intValue();
            if ((j != PUBLISH_STATE_AUDIOONLY) && (j != PUBLISH_STATE_BOTH))
                continue;
            i = 1;
        }
        return i;
    }

    public int getPublishState() {
        if ((this.properties != null) && (this.properties.containsKey("publishstate"))) {
            int i = ((Integer) this.properties.get("publishstate")).intValue();
            this.publishState = i;
            return i;
        }
        return 0;
    }

    public int getpublishstate(String paramString) {
        String str = "publishstate";
        if (!TextUtils.isEmpty(paramString))
            str = str + ":" + paramString;
        if (this.properties.get(str) == null)
            return 0;
        return ((Integer) this.properties.get(str)).intValue();
    }

    public boolean hasState(String paramString) {
        return this.playingStream.containsKey(paramString);
    }

    public boolean isMuteAudio(String paramString) {
        if ((this.playingStream.containsKey(paramString)) && (((HashMap) this.playingStream.get(paramString)).containsKey("ismuteaudio")))
            return ((Boolean) ((HashMap) this.playingStream.get(paramString)).get("ismuteaudio")).booleanValue();
        return false;
    }

    public boolean isMuteVideo(String paramString) {
        if ((this.playingStream.containsKey(paramString)) && (((HashMap) this.playingStream.get(paramString)).containsKey("ismutevideo")))
            return ((Boolean) ((HashMap) this.playingStream.get(paramString)).get("ismutevideo")).booleanValue();
        return false;
    }

    public boolean isSmall(String paramString) {
        if ((this.playingStream.containsKey(paramString)) && (((HashMap) this.playingStream.get(paramString)).containsKey("small")))
            return ((Boolean) ((HashMap) this.playingStream.get(paramString)).get("small")).booleanValue();
        return false;
    }

    public int publishState(String paramString) {
        String str = "publishstate";
        if (!TextUtils.isEmpty(paramString))
            str = str + ":" + paramString;
        if (this.properties.get(str) == null) ;
        for (Object localObject = Integer.valueOf(0); ; localObject = this.properties.get(str))
            return ((Integer) localObject).intValue();
    }

    public void putPublishState(String paramString, int paramInt) {
        if (paramString.indexOf(":") == -1)
            this.publishState = paramInt;
        this.properties.put(paramString, Integer.valueOf(paramInt));
    }

    public void putState(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
        if (this.playingStream.containsKey(paramString)) ;
        for (HashMap localHashMap = (HashMap) this.playingStream.get(paramString); ; localHashMap = new HashMap()) {
            localHashMap.put("small", Boolean.valueOf(paramBoolean1));
            localHashMap.put("ismutevideo", Boolean.valueOf(paramBoolean2));
            localHashMap.put("ismuteaudio", Boolean.valueOf(paramBoolean3));
            this.playingStream.put(paramString, localHashMap);
            return;
        }
    }

    public String removeState(String paramString) {
        if (this.playingStream.containsKey(this.peerId))
            this.playingStream.remove(this.peerId);
        return this.peerId;
    }

    public void setPublishState(String paramString, int paramInt) {
        String str = "publishstate";
        if (!TextUtils.isEmpty(paramString))
            str = str + ":" + paramString;
        this.properties.put(str, Integer.valueOf(paramInt));
    }

    public JSONObject toJson() {
        JSONObject localJSONObject = new JSONObject();
        try {
            localJSONObject.put("nickname", this.nickName);
            localJSONObject.put("id", this.peerId);
            localJSONObject.put("role", this.role);
            localJSONObject.put("hasaudio", this.hasAudio);
            localJSONObject.put("hasvideo", this.hasVideo);
            localJSONObject.put("candraw", this.canDraw);
            localJSONObject.put("publishstate", this.publishState);
            Iterator localIterator = this.properties.keySet().iterator();
            while (localIterator.hasNext()) {
                String str = (String) localIterator.next();
                localJSONObject.put(str, this.properties.get(str));
            }
        } catch (JSONException localJSONException) {
            localJSONException.printStackTrace();
        }
        return localJSONObject;
    }

    public int videoStatus() {
        int i = this.publishState;
        int j;
        if (i != PUBLISH_STATE_VIDEOONLY) {
            int k = PUBLISH_STATE_BOTH;
            j = 0;
            if (i != k) ;
        } else {
            j = 1;
        }
        return j;
    }

    public int videoStatus(String paramString) {
        String str = "publishstate";
        if (!TextUtils.isEmpty(paramString))
            str = str + ":" + paramString;
        if (this.properties.get(str) == null) ;
        for (int i = 0; ; i = ((Integer) this.properties.get(str)).intValue()) {
            int j;
            if (i != PUBLISH_STATE_VIDEOONLY) {
                int k = PUBLISH_STATE_BOTH;
                j = 0;
                if (i != k) ;
            } else {
                j = 1;
            }
            return j;
        }
    }
}
