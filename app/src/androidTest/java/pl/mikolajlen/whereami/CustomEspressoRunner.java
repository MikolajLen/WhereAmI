package pl.mikolajlen.whereami;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import pl.mikolajlen.whereami.application.EspressoTestApplication;

/**
 * Created by mikolaj on 05.08.2017.
 */

public class CustomEspressoRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, EspressoTestApplication.class.getName(), context);
    }
}
