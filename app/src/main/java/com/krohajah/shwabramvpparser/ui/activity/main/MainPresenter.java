package com.krohajah.shwabramvpparser.ui.activity.main;

import android.os.Handler;
import android.util.Log;

import com.krohajah.shwabramvpparser.model.FeedItem;
import com.krohajah.shwabramvpparser.mvp.PresenterBase;
import com.krohajah.shwabramvpparser.network.Executor;
import com.krohajah.shwabramvpparser.ui.activity.main.loader.Loader;
import com.krohajah.shwabramvpparser.utils.UiThread;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author Maxim Berezin
 */
public class MainPresenter extends PresenterBase<MainContract.View> implements MainContract.Presenter {

    private static final String TAG = "ShwabraShwabra";
    private boolean initialized = false;
    private UiThread uiThread;
    private ExecutorService executorService;
    private boolean loading = false;

    @Override
    public void initialize() {
        if (!initialized) {
            initialized = true;
            onInitialize();
        }
    }

    @Override
    public void onSwipedToRefresh() {
        loadPage();
    }

    private void onInitialize() {
        uiThread = new UiThread(new Handler());
        executorService = Executor.newNetworkSingleThreadExecutor();
        loadPage();
    }

    private void loadPage() {
        Log.d(TAG, "loadPage");
        Loader loader = new Loader();
        Runnable runnable = () -> {
            List<FeedItem> feedItems = loader.load();
            loading = true;
            uiThread.post(() -> {
                loading = false;
                setFedItems(feedItems);
            });
        };
        executorService.execute(runnable);
        updateSwipeToRefreshState();
    }


    private void updateSwipeToRefreshState() {
        if (loading) {
            getView().setLoading(true);
            getView().setSwipeToRefreshEnabled(false);
        } else {
            getView().setLoading(false);
            getView().setSwipeToRefreshEnabled(true);
        }
    }

    private void setFedItems(List<FeedItem> items) {
        getView().setSwipeToRefreshEnabled(true);
        getView().setLoading(false);
        if (items != null) {
            getView().setFeedItems(items);
        }
    }
}
