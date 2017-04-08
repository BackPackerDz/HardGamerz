package com.squalala.hardgamerdz.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.squalala.hardgamerdz.App;
import com.squalala.hardgamerdz.data.api.PostService;
import com.squalala.hardgamerdz.data.prefs.Preferences;
import com.squalala.hardgamerdz.greendao.ArticleDao;
import com.squalala.hardgamerdz.greendao.CategoryArticleDao;
import com.squalala.hardgamerdz.greendao.CategoryDao;
import com.squalala.hardgamerdz.utils.EmailUtils;
import com.squalala.hardgamerdz.utils.RxUtils;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Back Packer
 * Date : 23/08/15
 */
public abstract class BaseFragment extends Fragment {

    protected CompositeSubscription _subscriptions = new CompositeSubscription();

    @Inject
    protected PostService postService;

    @Inject
    protected Preferences preferences;

    @Inject
    protected ArticleDao articleDao;

    @Inject
    protected CategoryDao categoryDao;

    @Inject
    protected CategoryArticleDao categoryArticleDao;

    @DebugLog
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(getActivity()).component().inject(this);


    /*    if (!preferences.isLoggedIn()) {
            String email = EmailUtils.getEmail(getContext());

            preferences.setEmail(email);

            EmailUtils.subscribeWithEmail(email);
        }  */

    }

    @Override
    public void onPause() {
        super.onPause();
        RxUtils.unsubscribeIfNotNull(_subscriptions);
    }

    @Override
    public void onResume() {
        super.onResume();
        _subscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(_subscriptions);
    }


}
