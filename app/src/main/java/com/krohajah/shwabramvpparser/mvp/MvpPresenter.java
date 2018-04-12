package com.krohajah.shwabramvpparser.mvp;

/**
 * @author Maxim Berezin
 */
public interface MvpPresenter<V extends MvpView> {

    void attachView(V mvpView);

    void initialize();

    void detachView();

    void destroy();
}
