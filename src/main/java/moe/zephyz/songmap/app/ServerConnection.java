package moe.zephyz.songmap.app;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
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

    private final static char SEPARATOR = '\t';
    private final int port;
    private final String address;
    private final int DEFAULT_RADUIS = 100;
    private Socket socket = null;
    private BufferedReader bufferedReader = null;
    private PrintWriter printWriter = null;

    public ServerConnection(String address, int port) throws SQLException, URISyntaxException {
        this.address = address;
        this.port = port;

        Thread clientThread = new Thread(new ClientRunnable());
        clientThread.start();
        try {
            clientThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void addTitle(LatLng position, String title, String album, String artist){
        printWriter.println("ADD" + SEPARATOR + position.longitude + SEPARATOR + position.latitude + SEPARATOR + title + SEPARATOR + album + SEPARATOR + artist);
    }

    public String getAllSongs(){
        printWriter.println("GET_ALL");
        return sendAndRecieve();
    }


    public ArrayList<String> getSongsFromLocation(LatLng position){
        ArrayList<String> list = new ArrayList<>();
        printWriter.println("GET" + SEPARATOR + position.longitude + SEPARATOR + position.latitude + SEPARATOR + DEFAULT_RADUIS);
        list.add(sendAndRecieve());
        return list;
    }

    private String sendAndRecieve(){
        final String[] resultText = new String[1];
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    resultText[0] = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            t.join();
            if (resultText[0].equals("no_song")) {
                return "";
            } else {
                String output = "";
                String[] songs = resultText[0].split("\t\t");
                for (String string : songs) {
                    String[] s = string.split("\t");
                    if (s.length >= 3) {
                        output += "Title : " + s[0] + "\n";
                        output += "Album : " + s[1] + "\n";
                        output += "Artist : " + s[2] + "\n\n";
                    }
                }
                return output;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }


    class ClientRunnable implements Runnable {
        @Override
        public void run() {
            try {
                InetAddress inetAddress = InetAddress.getByName(address);
                socket = new Socket(inetAddress, port);
                printWriter = new PrintWriter(socket.getOutputStream(), true);
                bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(socket.getInputStream())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

