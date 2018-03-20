package com.tcs.remindmeapplication.services;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 979129 on 10/8/2015.
 */
public class RM_HttpManager extends AsyncTask<String,Void,String> {
    private  ServiceResponds serviceResponds;
    private String jsonback;

    public RM_HttpManager(ServiceResponds serviceResponds) {
        this.serviceResponds=serviceResponds;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            URL url=new URL(params[0]);

            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode()==200)
            {
                InputStream inputStream=httpURLConnection.getInputStream();
                jsonback=convertInputStream(inputStream);


            }
            else
            {

            }


            httpURLConnection.disconnect();

            return jsonback;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String convertInputStream(InputStream inputStream) {
        String result="";
        String line=null;

        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        try {
            while((line=bufferedReader.readLine())!=null)
            {
                result+=line;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String jsonsString) {
        if(jsonsString!=null)
        {
            serviceResponds.onServiseResponce(jsonsString);

        }
    }

    public void setProgressBar(ProgressBar progressBar) {
        ProgressBar bar;
    }

    public interface  ServiceResponds{

        void onServiseResponce(String responce);
    }
}