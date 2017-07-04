package com.meetferrytan.popularmovies.presentation.moviedetail;

import com.meetferrytan.popularmovies.data.component.NetComponent;
import com.meetferrytan.popularmovies.presentation.base.BaseContract;
import com.meetferrytan.popularmovies.util.ActivityScope;

import dagger.Component;

/**
 * Created by ferrytan on 7/4/17.
 */

@ActivityScope
@Component(dependencies = NetComponent.class)
public interface MovieDetailComponent extends BaseContract.Component<MovieDetailPresenter>{
    MovieDetailPresenter presenter();
}
