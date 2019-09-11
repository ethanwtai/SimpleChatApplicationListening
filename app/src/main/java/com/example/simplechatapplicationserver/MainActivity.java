/*
This is the serverside application that I built for the simple chat application.
Later I decided to integrate both the server and client into one application, which is
the SimpleChatApplication. 
 */

package com.example.simplechatapplicationserver;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.*;
import java.net.*;

public class MainActivity extends AppCompatActivity {
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.textView);
    }

    public void connectServer(View view){
        new connect().execute("");
    }

    public void changeText(String text){
        display.setText(text);
    }


    private class connect extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            changeText("Waiting for connection");
            try {
                changeText("Try");
                ServerSocket server = new ServerSocket(8008);
                Socket socket = server.accept();
                changeText("Connected.");
                BufferedReader in = null;
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg = "";
                while (!msg.equals("done")) {
                    try {
                        msg = in.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (msg == null) {
                        break; // disconnected
                    } else {
                        changeText(msg);
                        //break;
                    }
                }
                changeText("Closing Socket.");
                socket.close();
                in.close();

            }catch(IOException e){
                changeText(e.getMessage());

            }catch(Exception e){
                changeText(e.getMessage());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}

