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

    protected static final String URL_STRING = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
    protected static final String URL_IMAGE = "http://openweathermap.org/img/w/";
    protected static final String ACTIVITY_NAME = "WeatherForecast";
    private ImageView weatherImage;
    private TextView currentText, minText, maxText;
    private ProgressBar progressBar;
    private TextView uvRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_home);

            uvRating = (TextView) findViewById(R.id.uvRating);
            weatherImage = (ImageView) findViewById(R.id.weatherImage);
            currentText = (TextView) findViewById(R.id.currentTemp);
            minText = (TextView) findViewById(R.id.minTemp);
            maxText = (TextView) findViewById(R.id.maxTemp);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(100);

            new ForecastQuery().execute(null, null, null);
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {

        private String currentTemp = currentText.toString();
        private String minTemp = minText.toString();
        private String maxTemp = maxText.toString();
        private String iconFile = URL_IMAGE;
        private Bitmap weatherImage;
        private String currentLocation;


        @Override
        protected String doInBackground(String... params) {
            InputStream inputStream = null;
            try {
                URL url = new URL(URL_STRING);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                inputStream = connection.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(inputStream, null);

                int eventType = parser.getEventType();
                boolean set = false;
                while(eventType != XmlPullParser.END_DOCUMENT){
                    if(eventType == XmlPullParser.START_TAG){
                        if(parser.getName().equalsIgnoreCase("current")){
                            set = true;
                        }else if(parser.getName().equalsIgnoreCase("tempurature") && set){
                            currentTemp = parser.getAttributeValue(null, "value");
                            publishProgress(25);
                            minTemp = parser.getAttributeValue(null, "min");
                            publishProgress(50);
                            maxTemp = parser.getAttributeValue(null, "max");
                            publishProgress(75);
                        }else if(parser.getName().equalsIgnoreCase("weather") && set){
                            iconFile = parser.getAttributeValue(null, "icon") + ".png";
                            File file = getBaseContext().getFileStreamPath(iconFile);
                            if(!file.exists()){
                                saveImage(iconFile);
                            }else{
                                Log.i(ACTIVITY_NAME, "Icon saved, " +iconFile+ " is displayed.");
                                try{
                                    FileInputStream input = new FileInputStream(file);
                                    weatherImage = BitmapFactory.decodeStream(input);
                                }catch(FileNotFoundException e){
                                    Log.i(ACTIVITY_NAME, "Icon Saved, " +iconFile+ " is not found.");
                                }
                            }
                            publishProgress(100);
                        }
                    }
                    eventType = parser.next();
                }
            }catch(IOException e){
                Log.i(ACTIVITY_NAME, "IOException: " +e.getMessage());
            }catch(XmlPullParserException e){
                Log.i(ACTIVITY_NAME, "XmlPulParserException: " +e.getMessage());
            }finally{
                if(inputStream != null)
                    try{
                        inputStream.close();
                    }catch(IOException e){
                        Log.i(ACTIVITY_NAME, "IOException: " +e.getMessage());
                    }
                return null;
            }

        }

        @Override
        protected void onProgressUpdate(Integer... value){
            super.onProgressUpdate(value);
            progressBar.setProgress(value[0]);
            if(value[0] == 100){

            }
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            uvRating.setText("UV Rating for: " + uvRating);
            currentText.setText("Current " + currentTemp);
            minText.setText("Min " + minTemp);
            maxText.setText("Max " + maxTemp);
//            weatherImage.setImageBitMap(weatherImage);
            progressBar.setVisibility(View.INVISIBLE);
        }

        private void saveImage(String fname){
            HttpURLConnection connection = null;
            try{
                URL url = new URL(URL_IMAGE + fname);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if(responseCode == 200){
                    weatherImage = BitmapFactory.decodeStream(connection.getInputStream());
                    FileOutputStream outputStream = openFileOutput(fname, Context.MODE_PRIVATE);
                    weatherImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Log.i(ACTIVITY_NAME, "Weather icon, " +fname+ " is downloaded and is being displayed.");
                }else
                    Log.i(ACTIVITY_NAME, "Cannot download icon");
            }catch(Exception e){
                    Log.i(ACTIVITY_NAME, "Weather icon error: " + e.getMessage());
            }finally{
                if(connection !=null){
                    connection.disconnect();
                }
            }
        }

    }



}