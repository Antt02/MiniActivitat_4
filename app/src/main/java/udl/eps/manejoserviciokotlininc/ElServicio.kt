package udl.eps.manejoserviciokotlininc

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.widget.Toast

class ElServicio: Service() {

    private var soundPlayer: MediaPlayer? = null
    private var songPlayer: MediaPlayer? = null
    private val iBinder:IBinder = MyBinder()

    class MyBinder : Binder() {
        fun getService(): ElServicio{
            return ElServicio()
        }
    }

    override fun onBind(p0: Intent?): IBinder {
        return iBinder
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, R.string.creaserv, Toast.LENGTH_LONG).show()
        soundPlayer = MediaPlayer.create(this, R.raw.train)
        soundPlayer!!.isLooping = true
        songPlayer = MediaPlayer.create(this, R.raw.song)
        songPlayer!!.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val mode: String? = intent?.extras?.getString("mode")
        if (mode == "Sound"){
            Toast.makeText(this, R.string.iniservSound, Toast.LENGTH_LONG).show()
            soundPlayer?.start()
        }
        else if (mode == "Song"){
            Toast.makeText(this, R.string.iniservSong, Toast.LENGTH_LONG).show()
            songPlayer?.start()
        }
        return startId
    }

    override fun onDestroy() {
        super.onDestroy()
        if (soundPlayer?.isPlaying == true)soundPlayer!!.stop()
        if (songPlayer?.isPlaying == true)songPlayer!!.stop()
        Toast.makeText(this, R.string.finaserv, Toast.LENGTH_LONG).show()
    }
}