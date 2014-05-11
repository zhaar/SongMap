package moe.zephyz.songmap.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zephyz on 11/05/14.
 */
public class SongGraber {

    private String artist;
    private String track;
    private String album;
    private BroadcastReceiver receiver;
    private IntentFilter iF;

    public SongGraber(final Activity activity){
        iF = new IntentFilter();
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.android.music.playbackcomplete");
        iF.addAction("com.android.music.queuechanged");

        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent)
            {
                String action = intent.getAction();
                String cmd = intent.getStringExtra("command");
                Log.d("mIntentReceiver.onReceive ", action + " / " + cmd);
                artist = intent.getStringExtra("artist");
                album = intent.getStringExtra("album");
                track = intent.getStringExtra("track");
                Log.d("Music",artist+":"+album+":"+track);
                Toast.makeText(activity, track, Toast.LENGTH_SHORT).show();
            }
        };
        activity.registerReceiver(receiver, iF);

    }

    public String getCurrentSongTitle(){
        return track;
    }

    public String getCurrentSongAlbum(){
        return album;
    }

    public String getCurrentSongArtist(){
        return artist;
    }
}
