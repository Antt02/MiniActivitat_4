package udl.eps.manejoserviciokotlininc

import android.content.*
import android.widget.Toast

class MyBroadcast: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action !=null){
            if (intent.action.equals("android.intent.action.HEADSET_PLUG")){
                when(intent.getIntExtra("state", 2)){
                    0 ->{
                        Toast.makeText(context, "HeadsetPlugRecieved - 0", Toast.LENGTH_LONG).show()
                        stopService(context)
                    }
                    1 -> {
                        Toast.makeText(context, "HeadsetPlugRecieved - 1", Toast.LENGTH_LONG).show()
                        startService("Song", context)
                    }
                }
            }
        }
        when(intent?.getStringExtra("mode")){
            "Sound" -> startService("Sound", context)
            "Song" -> startService("Song", context)
            "Stop" -> stopService(context)
        }
    }

    private fun startService(mode: String?, context: Context?){
        if(mode == "Sound") Toast.makeText(context, R.string.iniservSoundBr, Toast.LENGTH_LONG).show()
        if(mode == "Song") Toast.makeText(context, R.string.iniservSongBr, Toast.LENGTH_LONG).show()
        val serviceIntent = Intent(context, ElServicio::class.java)
        serviceIntent.putExtra("mode", mode)
        context?.startService(serviceIntent)
    }

    private fun stopService(context: Context?){
        Toast.makeText(context, R.string.finaservBr, Toast.LENGTH_LONG).show()
        val serviceIntent = Intent(context, ElServicio::class.java)
        context?.stopService(serviceIntent)
    }
}