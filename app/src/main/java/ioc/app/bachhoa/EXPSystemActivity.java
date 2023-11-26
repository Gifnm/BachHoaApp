package ioc.app.bachhoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import ioc.app.bachhoa.viewPagerAdapter.ExpPagerAdapter;

public class EXPSystemActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ExpPagerAdapter expPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expsystem);
        uiInit();
    }

    private void uiInit() {
        tabLayout = findViewById(R.id.aexp_tab_layout);
        viewPager = findViewById(R.id.aexp_view_pager);
        expPagerAdapter = new ExpPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(expPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}