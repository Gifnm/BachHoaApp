package ioc.app.bachhoa.viewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import ioc.app.bachhoa.fm.EXPSystem_fm;
import ioc.app.bachhoa.fm.EndDate_fm;
import ioc.app.bachhoa.fm.NearDate_fm;

public class ExpPagerAdapter extends FragmentStatePagerAdapter {
    public ExpPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EXPSystem_fm();

            case 1:
                return new NearDate_fm();
            case 2:
                return new EndDate_fm();
            default:
                return new EXPSystem_fm();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Date cần xử lý"
                        ;
            case 1:
                return "Cận date"
                        ;
            case 3:
                return "Hết date"
                        ;
            default:
                return "Date cần xử lý";
        }

    }
}
