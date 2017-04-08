package com.squalala.hardgamerdz.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.squalala.hardgamerdz.R;
import com.squalala.hardgamerdz.data.prefs.Preferences;
import com.squalala.hardgamerdz.eventbus.PostActionEvent;
import com.squalala.hardgamerdz.eventbus.PostEvent;
import com.squalala.hardgamerdz.eventbus.QueryEvent;
import com.squalala.hardgamerdz.greendao.Article;
import com.squalala.hardgamerdz.ui.fragment.AboutFragment;
import com.squalala.hardgamerdz.ui.fragment.CategoryFragment;
import com.squalala.hardgamerdz.ui.fragment.FavorisFragment;
import com.squalala.hardgamerdz.utils.NoteAppReminder;
import com.squalala.hardgamerdz.utils.EmailUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

public class MainActivity extends AppCompatActivity
    implements ShowPostActivity.PostListener {

    private Drawer drawer;

    private final static String TAG = MainActivity.class.getSimpleName();

    private String [] categoryTags, categoryNames;

    private NoteAppReminder noteAppReminder;

    @Bind(R.id.tool_bar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        categoryNames = getResources().getStringArray(R.array.categories_names);
        categoryTags = getResources().getStringArray(R.array.categories_tags);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackgroundScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .withHeaderBackground(R.drawable.logo)
                .build();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(

                        /*     <item>Dernières 24h</item>
        <item>Actualités</item>
        JEUx
        <item>High-Tech</item>
        <item>Événement</item>
        <item>Séries/Cinéma</item>
        <item>Mangas/Animes</item>
        <item>Favoris</item>
        <item>Forum</item>
        <item>Paramètres</item>
        <item>Contact</item>
        <item>Notez l\'application</item>
        <item>A propos</item> */

                        new PrimaryDrawerItem().withName(categoryNames[0]).withIcon(FontAwesome.Icon.faw_clock_o).withIdentifier(1),
                        new PrimaryDrawerItem().withName(categoryNames[1]).withIcon(FontAwesome.Icon.faw_newspaper_o).withIdentifier(2),
                        new PrimaryDrawerItem().withName(categoryNames[2]).withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3),
                        new PrimaryDrawerItem().withName(categoryNames[3]).withIcon(FontAwesome.Icon.faw_desktop).withIdentifier(4),
                        new PrimaryDrawerItem().withName(categoryNames[4]).withIcon(FontAwesome.Icon.faw_calendar).withIdentifier(5),
                        new PrimaryDrawerItem().withName(categoryNames[5]).withIcon(FontAwesome.Icon.faw_tv).withIdentifier(6),
                        new PrimaryDrawerItem().withName(categoryNames[6]).withIcon(FontAwesome.Icon.faw_paw).withIdentifier(7),
                        new PrimaryDrawerItem().withName(categoryNames[7]).withIcon(FontAwesome.Icon.faw_star).withIdentifier(8),
                        new PrimaryDrawerItem().withName(categoryNames[8]).withIcon(FontAwesome.Icon.faw_comments_o).withIdentifier(9),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(categoryNames[9]).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(10),
                        new PrimaryDrawerItem().withName(categoryNames[10]).withIcon(FontAwesome.Icon.faw_envelope).withIdentifier(11),
                        new PrimaryDrawerItem().withName(categoryNames[11]).withIcon(FontAwesome.Icon.faw_play).withIdentifier(12),
                        new PrimaryDrawerItem().withName(categoryNames[12]).withIcon(FontAwesome.Icon.faw_info_circle).withIdentifier(13)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D

                        if (drawerItem != null && drawerItem instanceof Nameable) {

                            Answers.getInstance().logContentView(new ContentViewEvent()
                                    .putContentName(categoryNames[ (int) drawerItem.getIdentifier() - 1]));

                            if (drawerItem.getIdentifier() < 10) {

                                if (drawerItem.getIdentifier() == 8)
                                {
                                    getSupportActionBar().setTitle(((Nameable) drawerItem).getName().toString());
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                            new FavorisFragment()).commit();
                                }
                                else if (drawerItem.getIdentifier() == 1)
                                {
                                    getSupportActionBar().setTitle(((Nameable) drawerItem).getName().toString());
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                            CategoryFragment.newInstance(null)).commit();
                                }
                                else if (drawerItem.getIdentifier() == 9)
                                {


                                    Intent i = new Intent(Intent.ACTION_VIEW,
                                            Uri.parse("http://www.hardgamerz.com/forums/"));
                                    startActivity(i);
                                }
                                else
                                {
                                    getSupportActionBar().setTitle(((Nameable) drawerItem).getName().toString());
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                            CategoryFragment.newInstance(categoryTags[(int) drawerItem.getIdentifier() - 1])).commit();
                                }

                            }
                            else {

                                switch ((int) drawerItem.getIdentifier()) {

                                    case 10:


                                        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                                        startActivity(intent);

                                        break;

                                    case 11:

                                        Intent email = new Intent(Intent.ACTION_SEND); //
                                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"hardgamerz.com@gmail.com"});
                                        email.putExtra(Intent.EXTRA_SUBJECT, "Hardgamerdz");
                                        email.putExtra(Intent.EXTRA_TEXT, "");
                                        email.setType("message/rfc822");
                                        startActivity(Intent.createChooser(email, "Choisissez :"));

                                        break;

                                    case 12:

                                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                        } catch (android.content.ActivityNotFoundException anfe) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                        }

                                        break;

                                    case 13:

                                        getSupportActionBar().setTitle(((Nameable) drawerItem).getName().toString());
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                                new AboutFragment()).commit();


                                        break;
                                }


                            }


                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        drawer.setSelection(1);


        noteAppReminder = new NoteAppReminder(this, new Preferences(this));
    }

    @Override
    public void onSelectPost(Article post) {

        Intent intent = new Intent(this, ShowPostActivity.class);
        intent.putExtra("id", post.getId());


        // 8 c'est la position des favoris
        if (drawer.getCurrentSelectedPosition() - 1 == 8)
        {
            intent.putExtra("favoris", true);
        }
        else
        {
            intent.putExtra("category", categoryTags[drawer.getCurrentSelectedPosition() - 1]);

            Log.e(TAG, "cat " + categoryNames[drawer.getCurrentSelectedPosition() - 1]);

            if (categoryNames[drawer.getCurrentSelectedPosition() - 1].equals("Dernières 24h"))
                intent.putExtra("all_category", true);
        }

        startActivity(intent);

        // On le met en lus
        post.setStatus(true);

        // Et on notifie notre CategoryFragment
        EventBus.getDefault().post(new PostEvent(post));

    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAppReminder.checkMomentNoteApp();
    }

    @DebugLog
    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent();
        intent.setAction("com.squalala.hardgamerdz_ACTION");
        sendBroadcast(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = drawer.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                EventBus.getDefault().post(new QueryEvent(query));

                invalidateOptionsMenu();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.action_cacher_lus:

                EventBus.getDefault().post(new PostActionEvent(PostActionEvent.ActionType.HIDE_READ_ARTICLES));

                break;

            case R.id.action_tout_marque_comme_lu:

                EventBus.getDefault().post(new PostActionEvent(PostActionEvent.ActionType.SET_ALL_POST_READ));

                break;

            case R.id.action_tout_supprimer:

                EventBus.getDefault().post(new PostActionEvent(PostActionEvent.ActionType.DELETE_ALL));

                break;

            case R.id.action_supprimer_tous_les_lu:

                EventBus.getDefault().post(new PostActionEvent(PostActionEvent.ActionType.DELETE_ALL_READ_POST));

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
