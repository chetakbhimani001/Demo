package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity  implements BlankFragment.OnFragmentInteractionListener{
    private ViewPager2 viewPager2;
    private View indicator;
    private TabLayout tab;
    private Handler sliderHandler = new Handler();
    public static FragmentManager fragmentManager;
    private int indicatorWidth;
    private ViewPager viewPagerTabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        indicator = findViewById(R.id.indicator);
        viewPagerTabs = findViewById(R.id.viewPagerTabs);
        fragmentManager=getSupportFragmentManager();
        tab = findViewById(R.id.tab);
        bindViewPagerAdapter(viewPagerTabs);
        bindViewPagerTabs(tab, viewPagerTabs);
        tabSettings();

        viewPager2 = findViewById(R.id.viewPagerImageSlider);

        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.image_1));
        sliderItems.add(new SliderItems(R.drawable.image_2));
        sliderItems.add(new SliderItems(R.drawable.image_3));
        sliderItems.add(new SliderItems(R.drawable.image_4));
        sliderItems.add(new SliderItems(R.drawable.image_5));
        sliderItems.add(new SliderItems(R.drawable.image_6));

        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 2000); // slide duration 2 seconds
            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 2000);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void bindViewPagerAdapter(final ViewPager view) {
        final SamplePagerAdapter adapter = new SamplePagerAdapter(view.getContext(), fragmentManager);
        view.setAdapter(adapter);
    }

    public void bindViewPagerTabs(final TabLayout view, final ViewPager pagerView) {
        view.setupWithViewPager(pagerView, true);

    }
    private void tabSettings() {
        tab.post(() -> {
            indicatorWidth = tab.getWidth() / tab.getTabCount();
            FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) indicator.getLayoutParams();
            indicatorParams.width = indicatorWidth;
            indicator.setLayoutParams(indicatorParams);
        });

        viewPagerTabs.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetP
            ) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) indicator.getLayoutParams();
                float translationOffset = (positionOffset + position) * indicatorWidth;
                params.leftMargin = (int) translationOffset;
                indicator.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    public class SamplePagerAdapter extends FragmentStatePagerAdapter {

        private static final int FIRST_TAB = 0;
        private static final int SECOND_TAB = 1;

        private int[] TABS;

        private Context mContext;

        public SamplePagerAdapter(final Context context, final FragmentManager fm) {
            super(fm);
            mContext = context.getApplicationContext();
            TABS = new int[]{FIRST_TAB, SECOND_TAB};
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            switch (TABS[position]) {
                case FIRST_TAB:
                    bundle.putString("param1","FIRST TAB");
                    BlankFragment blankFragment=new BlankFragment();
                    blankFragment.setArguments(bundle);
                    return blankFragment;

                case SECOND_TAB:
                    bundle.putString("param1","SECOND TAB");
                    BlankFragment blankFragment1=new BlankFragment();
                    blankFragment1.setArguments(bundle);
                    return blankFragment1;

            }
            return null;

        }

        @Override
        public int getCount() {
            return TABS.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (TABS[position]) {
                case FIRST_TAB:
                    return "FIRST TAB";
                case SECOND_TAB:
                    return "SECOND TAB";
            }
            return null;
        }
    }

}