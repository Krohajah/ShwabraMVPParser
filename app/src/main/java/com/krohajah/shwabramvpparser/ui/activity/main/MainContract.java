package com.krohajah.shwabramvpparser.ui.activity.main;


import com.krohajah.shwabramvpparser.model.FeedItem;
import com.krohajah.shwabramvpparser.mvp.MvpPresenter;
import com.krohajah.shwabramvpparser.mvp.MvpView;

import java.util.List;

/**
 * @author Maxim Berezin
 */
public interface MainContract {

    interface View extends MvpView {

        void setLoading(boolean loading);

        void setSwipeToRefreshEnabled(boolean enabled);

        void setFeedItems(List<FeedItem> items);
    }


    interface Presenter extends MvpPresenter<View> {

        void initialize();

        void onSwipedToRefresh();
    }


}
