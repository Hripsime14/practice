package com.cypress.myapplication.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.AudioManager.*
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.cypress.myapplication.activities.PracticeActivity
import com.cypress.myapplication.R
import com.cypress.myapplication.constants.*
import com.cypress.myapplication.fragments.media.MediaFragment
import com.cypress.myapplication.fragments.media.Mode
import com.cypress.myapplication.modeldatas.model.MediaItem


class MediaService: Service(), OnAudioFocusChangeListener{
    private var notificationLayout: RemoteViews? = null
    private var notificationLayoutExpanded: RemoteViews? = null
    private var audioManager: AudioManager? = null
    companion object {
        var curMedia: MediaItem? = null
    }

    private var mediaPlayer: MediaPlayer? = null
    
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        createNotificationChannel()
        mediaPlayer = MediaPlayer()
        notificationLayout = RemoteViews(packageName, R.layout.notification_small)
        notificationLayoutExpanded= RemoteViews(packageName, R.layout.notification_large)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MediaChannel"
            val descriptionText = "This is the channel for media data notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = descriptionText
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager?.requestAudioFocus(this, STREAM_MUSIC, AUDIOFOCUS_GAIN)
            when (intent?.action) {
                PLAY_ACTION -> {
                    resume()
                    makeStopVisible()
                }
                PAUSE_ACTION -> {
                    pause()
                    makePlayVisible()
                }
                START_ACTION -> {
                    makeStopVisible()
                    curMedia = intent.getParcelableExtra(KEY)
                    start()
                }
                NOTIFICATION_SNOOZE_ACTION -> {
                    pause()
                    sendBroadcast(NOTIFICATION_SNOOZE_ACTION)
                    stopSelf()
                }
                NOTIFICATION_PLAY_ACTION -> {
                    resume()
                    makeStopVisible()
                    sendBroadcast(NOTIFICATION_PLAY_ACTION)

                }
                NOTIFICATION_PAUSE_ACTION -> {
                    pause()
                    makePlayVisible()
                    sendBroadcast(NOTIFICATION_PAUSE_ACTION)
                }
            }

        snoozeFromNotification()
        pauseFromNotification()
        playFromNotification()
        showNotification()
        return START_REDELIVER_INTENT
    }

    private fun sendBroadcast(action: String) {
        val intent = Intent()
        intent.action = action
        sendBroadcast(intent)
    }

    private fun makePlayVisible() {
        notificationLayout?.setViewVisibility(R.id.sNotificationPlayImg, View.VISIBLE)
        notificationLayoutExpanded?.setViewVisibility(R.id.lNotificationPlayImg, View.VISIBLE)
        notificationLayout?.setViewVisibility(R.id.sNotificationStopImg, View.GONE)
        notificationLayoutExpanded?.setViewVisibility(R.id.lNotificationStopImg, View.GONE)
    }

    private fun makeStopVisible() {
        notificationLayout?.setViewVisibility(R.id.sNotificationPlayImg, View.GONE)
        notificationLayoutExpanded?.setViewVisibility(R.id.lNotificationPlayImg, View.GONE)
        notificationLayout?.setViewVisibility(R.id.sNotificationStopImg, View.VISIBLE)
        notificationLayoutExpanded?.setViewVisibility(R.id.lNotificationStopImg, View.VISIBLE)

    }

    private fun snoozeFromNotification() {
        val snoozeIntent = Intent(this, MediaService::class.java)
        snoozeIntent.action = NOTIFICATION_SNOOZE_ACTION
        val pendingIntent = PendingIntent.getService(this, 0, snoozeIntent, 0)
        notificationLayoutExpanded?.setOnClickPendingIntent(
            R.id.lNotificationSnoozeImg,
            pendingIntent
        )
    }

    private fun playFromNotification() {
        val snoozeIntent = Intent(this, MediaService::class.java)
        snoozeIntent.action = NOTIFICATION_PLAY_ACTION
        val pendingIntent = PendingIntent.getService(this, 0, snoozeIntent, 0)
        notificationLayout?.setOnClickPendingIntent(R.id.sNotificationPlayImg, pendingIntent)
        notificationLayoutExpanded?.setOnClickPendingIntent(
            R.id.lNotificationPlayImg,
            pendingIntent
        )
    }

    private fun pauseFromNotification() {
        val snoozeIntent = Intent(this, MediaService::class.java)
        snoozeIntent.action = NOTIFICATION_PAUSE_ACTION
        val pendingIntent = PendingIntent.getService(this, 0, snoozeIntent, 0)
        notificationLayout?.setOnClickPendingIntent(R.id.sNotificationStopImg, pendingIntent)
        notificationLayoutExpanded?.setOnClickPendingIntent(
            R.id.lNotificationStopImg,
            pendingIntent
        )
    }

    private fun start() {
        mediaPlayer?.reset()
        mediaPlayer?.setDataSource(curMedia?.uri)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
        curMedia?.mode = Mode.PLAY
    }

    private fun pause() {
        if (mediaPlayer?.isPlaying == true) {
            curMedia?.mode = Mode.PAUSE
            mediaPlayer?.pause()
        }
    }

    private fun resume() {
        if (mediaPlayer?.isPlaying != true) {
            curMedia?.mode = Mode.PLAY
            mediaPlayer?.start()
        }
    }


    private fun showNotification()
    {
        notificationLayout?.setTextViewText(R.id.sNotificationTitle, curMedia?.title)
        notificationLayout?.setTextViewTextSize(
            R.id.sNotificationTitle,
            TypedValue.DENSITY_DEFAULT,
            30F
        )

        notificationLayoutExpanded?.setTextViewText(R.id.lNotificationTitle, curMedia?.title)
        notificationLayoutExpanded?.setTextViewTextSize(
            R.id.lNotificationTitle,
            TypedValue.DENSITY_DEFAULT,
            30F
        )

        val intent = Intent(this, PracticeActivity::class.java).apply {
            this.action = OPEN_MEDIA_ACTION
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val snoozeIntent = Intent(this, MediaFragment::class.java)
        snoozeIntent.action = NOTIFICATION_SNOOZE_ACTION
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0)


        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.note)
            .setContentText(curMedia?.title)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayoutExpanded)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        startForeground(1, notification.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onAudioFocusChange(focusChange: Int) {
        Log.d("fffff0", "onAudioFocusChange: dfgdfg")
//        mediaPlayer = MediaPlayer()
        when (focusChange) {
            AUDIOFOCUS_GAIN ->     //ThefocusChange service gained audio focus, so it needs to start playing.
                if (mediaPlayer?.isPlaying != true) {
                    Log.d("ffffffffffffff", "onAudioFocusChange: ffff")
                    resume()
                    showNotification()
                    sendBroadcast(NOTIFICATION_PAUSE_ACTION)
                }
            AUDIOFOCUS_LOSS ->     // The service lost audio focus, the user probably moved to playing media on another app.
                if (mediaPlayer?.isPlaying == true) {
                    Log.d("ffffffffffffff1", "onAudioFocusChange: ffff")
                    pause()
                    makePlayVisible()
                    showNotification()
                    sendBroadcast(NOTIFICATION_PAUSE_ACTION)
                }
            AUDIOFOCUS_LOSS_TRANSIENT ->     //Fucos lost for a short time, pause the MediaPlayer.
                if (mediaPlayer?.isPlaying == true) {
                    Log.d("ffffffffffffff2", "onAudioFocusChange: ffff")
                    pause()
                    sendBroadcast(PAUSE_ACTION)
                }
            AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ->     // Lost focus for a short time, probably a notification arrived on the device, lower the playback volume.
                if (mediaPlayer?.isPlaying == true) {
                    Log.d("ffffffffffffff3", "onAudioFocusChange: ffff")
                    mediaPlayer?.setVolume(0.1f, 0.1f)
                }
        }
    }
}