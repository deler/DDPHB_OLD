package my.ddx.ddphb.screens.spelldetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import my.ddx.ddphb.R;
import my.ddx.ddphb.screens.spelldetail.spelldetail.SpellDetailFragmentView;

public class SpellDetailActivity extends AppCompatActivity {

    private static final String KEY_SPELL_IDS = "KEY_SPELL_IDS";
    private static final String KEY_CURRENT_SPELL_ID = "KEY_CURRENT_SPELL_ID";

    public static void start(Context context, String currentSpellId, List<String> spellIds) {
        Intent starter = new Intent(context, SpellDetailActivity.class);
        starter.putExtra(KEY_CURRENT_SPELL_ID, currentSpellId);
        starter.putStringArrayListExtra(KEY_SPELL_IDS, new ArrayList<>(spellIds));
        context.startActivity(starter);
    }

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pager)
    ViewPager mPager;

    private List<String> mSpellIds;
    private String mCurrentSpellId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        mSpellIds = extras.getStringArrayList(KEY_SPELL_IDS);
        mCurrentSpellId = extras.getString(KEY_CURRENT_SPELL_ID);

        if (savedInstanceState != null) {
            mCurrentSpellId = savedInstanceState.getString(KEY_CURRENT_SPELL_ID, mCurrentSpellId);
        }

        setContentView(R.layout.activity_spell_detail);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), mSpellIds));
        mPager.addOnPageChangeListener(new PageChangeListener());
        int pageIndex = mSpellIds.indexOf(mCurrentSpellId);
        mPager.setCurrentItem(pageIndex);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_CURRENT_SPELL_ID, mCurrentSpellId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentSpellId = mSpellIds.get(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private static class PagerAdapter extends FragmentPagerAdapter {
        private List<String> mSpellIds;

        public PagerAdapter(FragmentManager fm, List<String> spellIds) {
            super(fm);
            mSpellIds = spellIds;
        }

        @Override
        public Fragment getItem(int position) {
            return SpellDetailFragmentView.newInstance(mSpellIds.get(position));
        }

        @Override
        public int getCount() {
            return mSpellIds.size();
        }
    }
}
