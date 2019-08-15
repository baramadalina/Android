package com.androidtutorialshub.loginregister.activities.util;

import android.os.AsyncTask;

/**
 * Created by Madalina Bara on 6/14/2019
 */
public interface TaskProvider<T extends AsyncTask<Void,?,?>> {
    T getTask(int num);
}
