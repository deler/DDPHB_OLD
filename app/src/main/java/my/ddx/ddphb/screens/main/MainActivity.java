package my.ddx.ddphb.screens.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.tapadoo.alerter.Alerter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import my.ddx.ddphb.R;
import my.ddx.ddphb.screens.base.BaseActivity;
import my.ddx.ddphb.screens.main.characters.CharactersListFragmentView;
import my.ddx.ddphb.screens.main.spells.SpellsListFragmentView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.content)
    FrameLayout mContent;

    private int selectedMenuId = R.id.nav_characters;

    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.main_drawer_open, R.string.main_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState != null) {
            selectedMenuId = savedInstanceState.getInt("selectedMenuId", R.id.nav_characters);
        }

        mNavView.setCheckedItem(selectedMenuId);
        mNavView.setNavigationItemSelectedListener(this);

        mFragment = getSupportFragmentManager().findFragmentById(mContent.getId());

        if (mFragment == null){
            mFragment = CharactersListFragmentView.newInstance();
            getSupportFragmentManager().beginTransaction().replace(mContent.getId(),mFragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("selectedMenuId", selectedMenuId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_characters:
                selectedMenuId = item.getItemId();
                mFragment = CharactersListFragmentView.newInstance();
                getSupportFragmentManager().beginTransaction().replace(mContent.getId(),mFragment).commitAllowingStateLoss();
                break;
            case R.id.nav_spells:
                selectedMenuId = item.getItemId();
                mFragment = SpellsListFragmentView.newInstance();
                getSupportFragmentManager().beginTransaction().replace(mContent.getId(),mFragment).commitAllowingStateLoss();
                break;
            case R.id.nav_skills:
                Alerter.create(this)
                        .setTitle(R.string.main_title_skills)
                        .setText("Not yet ready")
                        .setDuration(2000)
                        .setBackgroundColorRes(R.color.orange)
                        .show();
                return false;
            case R.id.nav_items:
                Alerter.create(this)
                        .setTitle(R.string.main_title_items)
                        .setText("Not yet ready")
                        .setDuration(2000)
                        .setBackgroundColorRes(R.color.orange)
                        .show();
                return false;
            case R.id.nav_monsters:
                Alerter.create(this)
                        .setTitle(R.string.main_title_monsters)
                        .setText("Not yet ready")
                        .setDuration(2000)
                        .setBackgroundColorRes(R.color.orange)
                        .show();
                return false;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
