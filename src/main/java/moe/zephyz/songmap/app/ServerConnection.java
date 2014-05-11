package moe.zephyz.songmap.app;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zephyz on 11/05/14.
 */
public class ServerConnection {

    private final String address;
    private final int port;

    public ServerConnection(String address, int port){
        this.address = address;
        this.port = port;
    }

    public void addTitle(LatLng position, String title, Date date){

    }

    public List<String＞ getSongsFromPosition(LatLng position){
        ArrayList<String> list = new ArrayList<>();

        return list;
    }

}
