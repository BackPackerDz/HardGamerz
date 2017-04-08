package com.squalala.hardgamerdz;

import android.app.Application;
import android.content.Context;

import com.squalala.hardgamerdz.common.BaseFragment;
import com.squalala.hardgamerdz.data.api.ApiModule;
import com.squalala.hardgamerdz.data.prefs.Preferences;
import com.squalala.hardgamerdz.greendao.ArticleDao;
import com.squalala.hardgamerdz.service.NotificationService;
import com.squalala.hardgamerdz.ui.activity.ShowPostActivity;
import com.squalala.hardgamerdz.ui.widget.ListProvider;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by Back Packer
 * Date : 30/04/15
 */
@Singleton
@Component(
        modules = {
                AppModule.class,
                ApiModule.class
        }
)
public interface AppComponent {

        void inject(App app);
        void inject(ShowPostActivity activity);
        void inject(BaseFragment fragment);
        void inject(NotificationService service);
        void inject(ListProvider listProvider);

        Application getApplication();

        Context getContext();

        Preferences getPreferences();

        ArticleDao getArticleDao();
}
