package vn.edu.usth.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
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
                Toast.makeText(getApplicationContext(), "Not refreshing", Toast.LENGTH_LONG).show();
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
}