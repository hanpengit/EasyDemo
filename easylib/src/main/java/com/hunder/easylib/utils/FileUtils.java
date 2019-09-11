package com.hunder.easylib.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by hp on 2016/7/25.
 */
public class FileUtils {
    private static String APP_PATH_ROOT; //APP文件存储根目录
    private static String VOICE_PATH; //录音文件存储目录
    private static String AUDIO_PATH; //音频文件存储目录
    private static String IMAGE_COMPRESS_CACHE_PATH; //压缩图片缓存存储目录
    private static String CALL_RECORD_PATH; //通话录音存储目录

    public static String getAppPath() {
        if (TextUtils.isEmpty(APP_PATH_ROOT)) {
            APP_PATH_ROOT = getRootPath().getAbsolutePath() + File.separator + "EasyDemo";
            initDirectory(APP_PATH_ROOT);
        }
        return APP_PATH_ROOT;
    }

    public static String getVoicePath() {
        if (TextUtils.isEmpty(VOICE_PATH)) {
            VOICE_PATH = getAppPath() + File.separator + "voice";
            initDirectory(VOICE_PATH);
        }
        return VOICE_PATH;
    }

    public static String getAudioPath() {
        if (TextUtils.isEmpty(AUDIO_PATH)) {
            AUDIO_PATH = getAppPath() + File.separator + "audio";
            initDirectory(AUDIO_PATH);
        }
        return AUDIO_PATH;
    }

    public static String getImageCompressCachePath() {
        if (TextUtils.isEmpty(IMAGE_COMPRESS_CACHE_PATH)) {
            IMAGE_COMPRESS_CACHE_PATH = getAppPath() + File.separator + "image_compress_cache";
            initDirectory(IMAGE_COMPRESS_CACHE_PATH);
        }
        return IMAGE_COMPRESS_CACHE_PATH;
    }

    public static String getCallRecordPath() {
        if (TextUtils.isEmpty(CALL_RECORD_PATH)) {
            CALL_RECORD_PATH = getAppPath() + File.separator + "call_record";
            initDirectory(CALL_RECORD_PATH);
        }
        return CALL_RECORD_PATH;
    }


    /**
     * 得到SD卡根目录.
     */
    public static File getRootPath() {
        File path = null;
        if (sdCardIsAvailable()) {
            path = Environment.getExternalStorageDirectory(); // 取得sdcard文件路径
        } else {
            path = Environment.getDataDirectory();
        }
        return path;
    }

    /**
     * SD卡是否可用.
     */
    public static boolean sdCardIsAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sd = new File(Environment.getExternalStorageDirectory().getPath());
            return sd.canWrite();
        } else
            return false;
    }

    /**
     * 创建一个文件夹.
     */
    public static boolean initDirectory(String path) {
        boolean result = false;
        File file = new File(path);
        if (!file.exists()) {
            result = file.mkdir();
        } else if (!file.isDirectory()) {
            file.delete();
            result = file.mkdir();
        } else if (file.exists()) {
            result = true;
        }
        return result;
    }

    /**
     * 获取磁盘可用空间和总大小，单位kb.
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static long[] getSDCardSize() {
        File path = getRootPath();
        StatFs stat = new StatFs(path.getPath());
        long blockSize, availableBlocks, totalBlocks;
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
            totalBlocks = stat.getBlockCountLong();
        } else {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
            totalBlocks = stat.getBlockCount();
        }
        long[] sizes = {availableBlocks * blockSize, totalBlocks * blockSize};
        return sizes;
    }

    /**
     * 转换文件大小
     */
    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static long getDownloadFolderSize() {
        long size = 0;
        File dir = new File(getAppPath());
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file != null && file.exists() && file.isFile()) {
                    size += file.length();
                }
            }
        }
        return size;
    }

    public static String getNowSpaceSize() {
        long availableSize = getSDCardSize()[0];
        long usedSize = getDownloadFolderSize();
        return "已占用空间" + (".00B".equals(formatFileSize(usedSize)) ? 0 : formatFileSize(usedSize)) + "， 可用空间" + formatFileSize(availableSize);
    }

    public static String getDownloadedFilePath(String fileName, String fileType) {
        return getAppPath() + "/" + fileName + "." + fileType;
    }

    /**
     * 文件夹是否可用
     */
    public static boolean dirIsAvailable(String dirPath) {
        File dir = new File(dirPath);
        if (dir.isDirectory() && dir.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }


}
