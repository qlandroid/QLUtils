package com.ql.view.rv;

import android.view.View;

/**
 * Created by android on 2017/11/14.
 */

public interface IEmpty {

    void displayLoadingView();

    void displayErrorView();

    void displayNoDataView();


    View getLoadingView();

    View getErrorView();

    View getNoDateView();


}
