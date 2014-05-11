package moe.zephyz.songmap.app;

import com.google.android.gms.maps.model.LatLng;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zephyz on 11/05/14.
 */
public class ServerConnection {

    private Connection connection;

    public ServerConnection(String address) throws SQLException, URISyntaxException {
        URI dbUri = new URI(System.getenv(address));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
        this.connection = DriverManager.getConnection(dbUrl, username, password);
    }

    public void addTitle(LatLng position, String title, Date date){
    }


    public ArrayList<String> getSongsFromLocation(LatLng position){
        ArrayList<String> list = new ArrayList<>();
        return list;
    }
}
