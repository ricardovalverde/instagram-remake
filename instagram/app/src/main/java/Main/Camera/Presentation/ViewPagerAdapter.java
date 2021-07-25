package Main.Camera.Presentation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<Fragment> fragments = new ArrayList<>(2);


    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }
}
