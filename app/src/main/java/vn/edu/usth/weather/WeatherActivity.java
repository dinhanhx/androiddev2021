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
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

        final Handler handler_ = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                String content = msg.getData().getString("server_response");
                Toast.makeText(getBaseContext(), content, Toast.LENGTH_SHORT).show();
            }
        };

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Bundle bun = new Bundle();
                bun.putString("server_response", "some thing beyond the sea");

                Message msg = new Message();
                msg.setData(bun);
                handler_.sendMessage(msg);
            }
        });

        switch (item.getItemId()) {
            case R.id.refresh:
            {
                th.start();
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