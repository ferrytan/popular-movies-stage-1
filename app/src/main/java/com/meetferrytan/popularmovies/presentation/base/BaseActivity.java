package com.meetferrytan.popularmovies.presentation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends BaseContract.Presenter, C extends BaseContract.Component<P>>
        extends AppCompatActivity implements BaseContract.View {
    protected P mPresenter;
    protected C mComponent;

    protected abstract void createComponent();

    protected P createPresenter() {
        return mComponent.presenter();
    }

    public abstract int createLayout();

    public abstract void startingUpActivity(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set content via create layout implement
        setContentView(createLayout());
        ButterKnife.bind(this);

        createComponent();
        mPresenter = createPresenter();
        mPresenter.attachView(this);
        // init action
        startingUpActivity(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return true;
    }
}