package ioc.app.bachhoa.viewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class fragmentPagerAdapter extends FragmentPagerAdapter {
    public fragmentPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                return new home_fm();
            case 1:
                return  new displayProduct_pm();
            case 2: return  new acount_fm();
            default: return new home_fm();
        }


    }

    @Override
    public int getCount() {
        return 3;
    }
}
