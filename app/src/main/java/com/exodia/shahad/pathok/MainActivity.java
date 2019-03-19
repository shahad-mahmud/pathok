package com.exodia.shahad.pathok;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.exodia.shahad.pathok.Fragments.FragmentBooks;
import com.exodia.shahad.pathok.Fragments.FragmentCreatePost;
import com.exodia.shahad.pathok.Fragments.FragmentHome;
import com.exodia.shahad.pathok.Fragments.FragmentNotifications;
import com.exodia.shahad.pathok.Fragments.FragmentProfile;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager mainViewPager;
    private BottomNavigationViewEx bottomNav;
    private MenuItem prevMenuItem;
    private Toolbar topNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findElements(); //find all the elements
        setUpBottomNavigation(); //setup the bottom navigation bar
        setUpViewPager(); //setup the view pager initially

        mainViewPager.setOffscreenPageLimit(4); //4 pages in offScreen won't be destroyed

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        mainViewPager.setCurrentItem(0, true);
                        break;
                    case R.id.menu_notification:
                        mainViewPager.setCurrentItem(1, true);
                        break;
                    case R.id.menu_create_post:
                        mainViewPager.setCurrentItem(2, true);
                        break;
                    case R.id.menu_profile:
                        mainViewPager.setCurrentItem(3, true);
                        break;
                    case R.id.menu_books:
                        mainViewPager.setCurrentItem(4, true);
                        break;
                }
                return false;
            }
        });

        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(prevMenuItem != null){
                    prevMenuItem.setChecked(false);
                }else {
                    bottomNav.getMenu().getItem(0).setChecked(false); //the home was selected
                }

                prevMenuItem = bottomNav.getMenu().getItem(i); //this will be the previous menu item
                prevMenuItem.setChecked(true); //this frag was selected
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void findElements(){
        mainViewPager = (ViewPager) findViewById(R.id.home_fragment_container);
        bottomNav = (BottomNavigationViewEx) findViewById(R.id.home_bottom_nav);
        topNav = (Toolbar) findViewById(R.id.home_top_nav);
    }

    private void setUpBottomNavigation(){
        BottomNavHandler.setUpBottomNav(bottomNav);
    }

    private void setUpViewPager(){
        BottomNavigtionPagerAdapter adapter = new BottomNavigtionPagerAdapter(getSupportFragmentManager());
        FragmentHome home = new FragmentHome();
        FragmentNotifications notifications = new FragmentNotifications();
        FragmentCreatePost createPost = new FragmentCreatePost();
        FragmentProfile profile = new FragmentProfile();
        FragmentBooks books = new FragmentBooks();

        adapter.addFragment(home); //at position 0
        adapter.addFragment(notifications); //at position 1
        adapter.addFragment(createPost); //at position 2
        adapter.addFragment(profile); //at position 3
        adapter.addFragment(books); //at position 4

        mainViewPager.setAdapter(adapter);
    }
}
