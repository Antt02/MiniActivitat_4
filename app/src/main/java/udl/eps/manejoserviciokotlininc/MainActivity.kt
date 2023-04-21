package udl.eps.manejoserviciokotlininc

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import udl.eps.manejoserviciokotlininc.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() , View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    var mService: ElServicio? = null
    var mIsBound: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInSound.setOnClickListener(this)
        binding.btnInSong.setOnClickListener(this)
        binding.btnFin.setOnClickListener(this)

        this.registerReceiver(MyBroadcast(), IntentFilter(Intent.ACTION_HEADSET_PLUG))
    }

    override fun onClick(src: View) {
        when(src.id) {
            R.id.btnInSound -> startBroadcast("Sound")//startService("Sound")
            R.id.btnInSong -> startBroadcast("Song")//startService("Song")
            R.id.btnFin -> stopBroadcast()//stopService()
        }
    }
    private fun startBroadcast(mode: String){
        val serviceIntent = Intent(this, MyBroadcast::class.java)
        serviceIntent.putExtra("mode", mode)
        sendBroadcast(serviceIntent)
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun stopBroadcast(){
        val serviceIntent = Intent(this, MyBroadcast::class.java)
        serviceIntent.putExtra("mode", "Stop")
        sendBroadcast(serviceIntent)
        unbindService(serviceConnection)
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            // We've bound to MyService, cast the IBinder and get MyBinder instance
            val binder: ElServicio.MyBinder = iBinder as ElServicio.MyBinder
            mService = binder.getService()
            mIsBound = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            mIsBound = false
        }
    }
}