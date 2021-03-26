package com.cst2335.niki0007;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherHome extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_home);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        TextView currentTemp = (TextView) findViewById(R.id.currentTemp);
        TextView minTemp = (TextView) findViewById(R.id.minTemp);
        TextView maxTemp = (TextView) findViewById(R.id.maxTemp);
        ImageView weatherImage = (ImageView) findViewById(R.id.weatherImage);

        ForecastQuery forecast = new ForecastQuery();
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
        forecast.execute(urlString);

    }

    protected static Bitmap getImage(URL url){
        HttpURLConnection iconConn = null;
        try{
            iconConn = (HttpURLConnection) url.openConnection();
            iconConn.connect();
            int response = iconConn.getResponseCode();
            if(response == 200){
                return BitmapFactory.decodeStream(iconConn.getInputStream());
            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            if(iconConn != null){
                iconConn.disconnect();
            }
        }
    }

    public boolean fileExistance(String fileName){
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }


    public class ForecastQuery extends AsyncTask<String, Integer, String> {

        String min;
        String max;
        String current;
        String iconName;
        Bitmap icon;


        @Override
        protected String doInBackground(String... params) {
            try{
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream stream = conn.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(stream, null);

                while(parser.next() != XmlPullParser.END_DOCUMENT){
                    if(parser.getEventType() != XmlPullParser.START_TAG){
                        continue;
                    }if(parser.getName().equals("tempuarture")){
                        current = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        min = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        max = parser.getAttributeValue(null, "max");
                        publishProgress(75);
                    }if(parser.getName().equals("weather")){
                        iconName = parser.getAttributeValue(null, "icon");
                        String iconFile = iconName+".png";
                        if(fileExistance(iconFile)){
                            FileInputStream inputStream = null;
                            try{
                                inputStream = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            icon = BitmapFactory.decodeStream(inputStream);
                        }else{
                            URL iconURL = new URL("http://openweathermap.org/img/w/"+ iconName+ ".png");
                            icon = getImage(iconURL);
                            FileOutputStream outputStream = openFileOutput(iconName + ".png", MODE_PRIVATE);
                            icon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        }
                        publishProgress(100);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... value){
            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);

        }

        @Override
        protected void onPostExecute(String result){
            String degree = Character.toString((char)0x0080);
            currentTemp.setText(currentTemp.getText()+current+degree+"C");
            minTemp.setText(minTemp.getText()+min+degree+"C");
            maxTemp.setText(maxTemp.getText()+max+degree+"C");
            weatherImage.setImageBitmap(icon);
            progressBar.setVisibility(View.INVISIBLE);

        }

    }

    protected static final String ACTIVITY_NAME = "WeatherForecast";
    private ProgressBar progressBar = findViewById(R.id.progressBar);
    private TextView currentTemp = findViewById(R.id.currentTemp);
    private TextView minTemp = findViewById(R.id.minTemp);
    private TextView maxTemp = findViewById(R.id.maxTemp);
    private ImageView weatherImage = findViewById(R.id.weatherImage);



}