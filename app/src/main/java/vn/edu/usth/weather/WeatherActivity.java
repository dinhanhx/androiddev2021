package vn.edu.usth.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

//        ForecastFragment ff = new ForecastFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.frag_forecaset, ff, null).commit();
//        WeatherFragment wf = new WeatherFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.frag_weather, wf, null).commit();

//        WeatherAndForecastFragment waf = new WeatherAndForecastFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, waf).commit();

        vp = (ViewPager) findViewById(R.id.viewPager);
        addTabs(vp);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vp);

        MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.intro);
        mp.start();

        getWeatherJson();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refresh:
            {
//                AsyncTask<String, Integer, Bitmap> tsk = new AsyncTask<String, Integer, Bitmap>() {
//                    Bitmap bitmap1;
//
//                    @SuppressLint("StaticFieldLeak")
//                    @Override
//                    protected Bitmap doInBackground(String... strings) {
//                        try {
//                            Thread.sleep(1000);
//                            URL url = new URL(strings[0]);
//
//                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                            connection.setRequestMethod("GET");
//                            connection.setDoInput(true);
//                            connection.connect();
//
//                            InputStream inputstream = connection.getInputStream();
//                            bitmap1 = BitmapFactory.decodeStream(inputstream);
//
//                            connection.disconnect();
//                        }
//                        catch (MalformedURLException e)
//                        {
//                            e.printStackTrace();
//                        }
//                        catch (IOException e)
//                        {
//                            e.printStackTrace();
//                        }
//                        catch (InterruptedException e)
//                        {
//                            e.printStackTrace();
//                        }
//                        return bitmap1;
//                    }
//
//                    @Override
//                    protected void onPreExecute() {
//
//                    }
//
//                    @Override
//                    protected void onProgressUpdate(Integer... values) {
//                        super.onProgressUpdate(values);
//                    }
//
//                    @Override
//                    protected void onPostExecute(Bitmap bitmap) {
////                        Toast.makeText(getApplicationContext(), "something beyond the sky", Toast.LENGTH_LONG).show();
//                        ImageView logo = (ImageView) findViewById(R.id.logo);
//                        logo.setImageBitmap(bitmap);
//                    }
//                };
//                tsk.execute("https://usth.edu.vn/uploads/logo_moi-eng.png");
                RequestQueue rq = Volley.newRequestQueue(this);
                Response.Listener<Bitmap> rl = new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ImageView logo = (ImageView) findViewById(R.id.logo);
                        logo.setImageBitmap(response);
                    }
                };
                ImageRequest imageRequest = new ImageRequest(
                        "https://usth.edu.vn/uploads/logo_moi-eng.png", rl,
                        0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, null
                );
                rq.add(imageRequest);
                return true;
            }

            case R.id.setting:
            {
                Intent intent = new Intent(this, PrefActivity.class);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addTabs(ViewPager viewPager)
    {
        ViewPagerAdapter vap = new ViewPagerAdapter(getSupportFragmentManager());
        vap.addFrag(new WeatherAndForecastFragment(), "Hanoi");
        vap.addFrag(new WeatherAndForecastFragment(), "Also Hanoi");
        vap.addFrag(new WeatherAndForecastFragment(), "Still Hanoi");
        viewPager.setAdapter(vap);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List mFragmentList = new ArrayList<>();
        private final List mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager man)
        {
            super(man);
        }

        @Override
        public Fragment getItem(int position)
        {
            return (Fragment) mFragmentList.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragmentList.size();
        }

        public void addFrag(Fragment frag, String title)
        {
            mFragmentList.add(frag);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return (CharSequence) mFragmentTitleList.get(position);
        }
    }

    private void getWeatherJson()
    {
        // e3fc7cd1499bf87b25c7829f2ff41639
        // hanoi
        String url = "https://api.openweathermap.org/data/2.5/weather?q=hanoi&appid=e3fc7cd1499bf87b25c7829f2ff41639";
        JsonObjectRequest js = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        TextView tv = (TextView) findViewById(R.id.weather_info);
                        String output = "";
                        try {
                            String name = response.getString("name");
                            double temp = response.getJSONObject("main").getDouble("temp");

                            output = name + "\n" +  String.valueOf(temp) + "*F";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        tv.setText(output);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(js);
    }
}