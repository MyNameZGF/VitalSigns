package com.xincheng.vitalsigns.utlis;

import android.app.Application;
import android.app.Service;
import android.content.ContentResolver;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public final class SettingUtils {
    /**
     * 获取睡眠时间
     *
     * @return the duration of sleep.
     */
    public static int getSleepDuration(Application app) {
        try {
            return Settings.System.getInt(app.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }

    /**
     * 设置休眠时长
     * @param duration
     */
    public static boolean setSleepDuration(Application app,final long duration) {
        return Settings.System.putLong(app.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, duration);
    }

    /**
     * 是否开启自动调节
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAutoBrightnessEnabled(Application app) {
        try {
            int mode = Settings.System.getInt(app.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
            return mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 设置自动调节
     * //TODO 需要开启 <uses-permission android:name="android.permission.WRITE_SETTINGS" /> 权限
     *
     * @param enabled True to enabled, false otherwise.
     */
    public static boolean setAutoBrightnessEnabled(final boolean enabled,Application app) {
        return Settings.System.putInt(
                app.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, enabled ? Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC : Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }


    /**
     * 获取屏幕亮度
     *
     * @return 屏幕亮度 0-255
     */
    public static int getBrightness(Application app) {
        try {
            return Settings.System.getInt(app.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 设置屏幕亮度
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     * 并得到授权
     *
     * @param brightness 亮度值
     */
    public static boolean setBrightness(@IntRange(from = 0, to = 255) final int brightness,Application app) {
        ContentResolver resolver = app.getContentResolver();
        boolean b = Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        resolver.notifyChange(Settings.System.getUriFor("screen_brightness"), null);
        return b;
    }

    /**
     * 设置窗口亮度
     *
     * @param window     窗口
     * @param brightness 亮度值
     */
    public static void setWindowBrightness(@NonNull final Window window, @IntRange(from = 0, to = 255) final int brightness) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255f;
        window.setAttributes(lp);
    }

    /**
     * 获取窗口亮度
     *
     * @param window 窗口
     * @return 屏幕亮度 0-255
     */
    public static int getWindowBrightness(final Window window,Application app) {
        WindowManager.LayoutParams lp = window.getAttributes();
        float brightness = lp.screenBrightness;
        if (brightness < 0) return getBrightness(app);
        return (int) (brightness * 255);
    }

    /**
     * 获取系统警报音量
     * AudioManager.STREAM_ALARM 可以是以下几种.
     *                   <ul>
     *                   <li>{@link AudioManager#STREAM_VOICE_CALL}</li>
     *                   <li>{@link AudioManager#STREAM_SYSTEM}</li>
     *                   <li>{@link AudioManager#STREAM_RING}</li>
     *                   <li>{@link AudioManager#STREAM_MUSIC}</li>
     *                   <li>{@link AudioManager#STREAM_ALARM}</li>
     *                   <li>{@link AudioManager#STREAM_NOTIFICATION}</li>
     *                   <li>{@link AudioManager#STREAM_DTMF}</li>
     *                   <li>{@link AudioManager#STREAM_ACCESSIBILITY}</li>
     *                   </ul>
     * @return the volume
     */
    public static int getVolume(Application app) {
        AudioManager am = (AudioManager) app.getSystemService(app.AUDIO_SERVICE);
        //noinspection ConstantConditions
        int voice = am.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        Log.e("voice","getVolume："+voice);
        return voice;
    }

    /**
     * 设置音量
     * AudioManager.STREAM_ALARM 可以是以下几种.
      *                   <ul>
      *                   <li>{@link AudioManager#STREAM_VOICE_CALL}</li>
      *                   <li>{@link AudioManager#STREAM_SYSTEM}</li>
      *                   <li>{@link AudioManager#STREAM_RING}</li>
      *                   <li>{@link AudioManager#STREAM_MUSIC}</li>
      *                   <li>{@link AudioManager#STREAM_ALARM}</li>
      *                   <li>{@link AudioManager#STREAM_NOTIFICATION}</li>
      *                   <li>{@link AudioManager#STREAM_DTMF}</li>
      *                   <li>{@link AudioManager#STREAM_ACCESSIBILITY}</li>
      *                   </ul>
      * @param volume     The volume.
     * AudioManager.FLAG_ALLOW_RINGER_MODES 可以是以下几种.
     *                   <ul>
     *                   <li>{@link AudioManager#FLAG_SHOW_UI}</li>
     *                   <li>{@link AudioManager#FLAG_ALLOW_RINGER_MODES}</li>
     *                   <li>{@link AudioManager#FLAG_PLAY_SOUND}</li>
     *                   <li>{@link AudioManager#FLAG_REMOVE_SOUND_AND_VIBRATE}</li>
     *                   <li>{@link AudioManager#FLAG_VIBRATE}</li>
     *                   </ul>
     */
    public static void setVolume(int volume,Application app) {
        AudioManager am = (AudioManager) app.getSystemService(app.AUDIO_SERVICE);
        try {
            //noinspection ConstantConditions
            am.setStreamVolume(AudioManager.STREAM_NOTIFICATION, volume, AudioManager.FLAG_SHOW_UI);
        } catch (SecurityException ignore) {
        }
    }


    /**
     * 获取最大音量
     *
     * AudioManager.STREAM_ALARM 可以是以下几种.
     *                   <ul>
     *                   <li>{@link AudioManager#STREAM_VOICE_CALL}</li>
     *                   <li>{@link AudioManager#STREAM_SYSTEM}</li>
     *                   <li>{@link AudioManager#STREAM_RING}</li>
     *                   <li>{@link AudioManager#STREAM_MUSIC}</li>
     *                   <li>{@link AudioManager#STREAM_ALARM}</li>
     *                   <li>{@link AudioManager#STREAM_NOTIFICATION}</li>
     *                   <li>{@link AudioManager#STREAM_DTMF}</li>
     *                   <li>{@link AudioManager#STREAM_ACCESSIBILITY}</li>
     *                   </ul>
     * @return the maximum volume
     */
    public static int getMaxVolume(Application app) {
        AudioManager am = (AudioManager) app.getSystemService(app.AUDIO_SERVICE);
        //noinspection ConstantConditions
        return am.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION );
    }

    /**
     * 获取最小音量
     *
     * AudioManager.STREAM_ALARM 可以是以下几种.
     *                   <ul>
     *                   <li>{@link AudioManager#STREAM_VOICE_CALL}</li>
     *                   <li>{@link AudioManager#STREAM_SYSTEM}</li>
     *                   <li>{@link AudioManager#STREAM_RING}</li>
     *                   <li>{@link AudioManager#STREAM_MUSIC}</li>
     *                   <li>{@link AudioManager#STREAM_ALARM}</li>
     *                   <li>{@link AudioManager#STREAM_NOTIFICATION}</li>
     *                   <li>{@link AudioManager#STREAM_DTMF}</li>
     *                   <li>{@link AudioManager#STREAM_ACCESSIBILITY}</li>
     *                   </ul>
     * @return the minimum volume
     */
    public static int getMinVolume(Application app) {
        AudioManager am = (AudioManager) app.getSystemService(app.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            //noinspection ConstantConditions
            return am.getStreamMinVolume(AudioManager.STREAM_NOTIFICATION );
        }
        return 0;
    }

    /**
     * 设置静音
     * 这个方法不能用，有问题
     */
    public static int setMute(Application app){
        AudioManager myAudioManager = (AudioManager)app.getSystemService(app.AUDIO_SERVICE);
        int ringerMode  = myAudioManager.getRingerMode();  //记录用户先前的声音模式

        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);//设置静音
        return ringerMode;
    }

    /**
     * 恢复之前的声音模式
     * @param voiceType 之前的声音模式
     * @param app
     * 这个方法不能用，有问题
     */
    public static void openVoice(int voiceType,Application app){
        AudioManager myAudioManager = (AudioManager)app.getSystemService(app.AUDIO_SERVICE);
        myAudioManager.setRingerMode(voiceType); //恢复之前的声音模式
    }
}
