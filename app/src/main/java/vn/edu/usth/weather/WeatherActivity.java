package vn.edu.usth.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

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