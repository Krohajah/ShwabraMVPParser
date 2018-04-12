package com.krohajah.shwabramvpparser.ui.activity.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.krohajah.shwabramvpparser.R;
import com.krohajah.shwabramvpparser.model.FeedItem;
import com.krohajah.shwabramvpparser.ui.adapter.rcadapter.FeedAdapter;

import java.util.List;

/**
 * @author Maxim Berezin
 */
public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainPresenter presenter;
    private FeedAdapter feedAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    boolean swipeEnabled;
    boolean swipeRefreshing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        feedAdapter = new FeedAdapter();
        recyclerView.setAdapter(feedAdapter);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.onSwipedToRefresh());

        presenter = new MainPresenter();
        presenter.attachView(this);
        presenter.initialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        if (isFinishing()) {
            presenter.destroy();
        }
    }

    @Override
    public void setLoading(boolean loading) {
        this.swipeRefreshing = loading;
        if (swipeEnabled != swipeRefreshLayout.isEnabled()) {
            swipeRefreshLayout.setEnabled(swipeEnabled);
        }
        swipeRefreshLayout.setRefreshing(loading);
    }

    @Override
    public void setSwipeToRefreshEnabled(boolean enabled) {
        this.swipeEnabled = enabled;
        if (!swipeRefreshing) {
            swipeRefreshLayout.setEnabled(enabled);
        }
    }

    @Override
    public void setFeedItems(List<FeedItem> items) {
        feedAdapter.setItems(items);
    }
}
