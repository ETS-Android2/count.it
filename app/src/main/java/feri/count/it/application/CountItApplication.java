package feri.count.it.application;

import android.app.Application;

import feri.count.datalib.User;

public class CountItApplication extends Application {
    public static final String TAG = CountItApplication.class.getSimpleName();

    private User loggedUser;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = new User(loggedUser);
    }
}
