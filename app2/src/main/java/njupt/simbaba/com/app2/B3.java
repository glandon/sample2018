package njupt.simbaba.com.app2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import njupt.simbaba.com.app2.fragment.FB1;
import njupt.simbaba.com.app2.fragment.FB2;
import njupt.simbaba.com.app2.fragment.FB3;


public class B3 extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b3);

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));

        BottomNavigationView bar = findViewById(R.id.bottom_bar);
        bar.setOnNavigationItemSelectedListener(this::onBottomBarClicked);
    }

    boolean onBottomBarClicked(MenuItem item) {
        Intent intent = new Intent();

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        int id = item.getItemId();

        if (id == R.id.id_b1) {
            mViewPager.setCurrentItem(0);
        } else if (id == R.id.id_b2) {
            mViewPager.setCurrentItem(1);
        } else if (id == R.id.id_b3) {
            mViewPager.setCurrentItem(2);
        }

        return true;
    }


    class MyPageAdapter extends FragmentPagerAdapter {

        MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new FB1();
            } else if (position == 1) {
                return new FB2();
            } else {
                return new FB3();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
