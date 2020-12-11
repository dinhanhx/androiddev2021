package vn.edu.usth.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Log.i(null, "onCreate");

        ForecastFragment ff = new ForecastFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, ff).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(null, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(null, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(null, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(null, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(null, "onDestroy");
    }
}