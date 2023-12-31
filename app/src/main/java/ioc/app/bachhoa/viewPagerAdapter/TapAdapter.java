package ioc.app.bachhoa.viewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import ioc.app.bachhoa.tapAdapter.viewProduct_pm;
import ioc.app.bachhoa.tapAdapter.viewShelf_fm;

public class TapAdapter extends FragmentStatePagerAdapter {
    public TapAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new viewProduct_pm();
            case 1:
                return new viewShelf_fm();
            default:
                return new viewProduct_pm();

        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Xem sản phẩm";
            case 1:
                return "Xem kệ";
            default:
                return "Xem sản phẩm";
        }
    }
}
