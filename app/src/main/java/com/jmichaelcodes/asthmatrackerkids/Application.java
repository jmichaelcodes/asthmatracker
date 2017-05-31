package com.jmichaelcodes.asthmatrackerkids;

import com.davidstemmer.screenplay.MutableStage;
import com.davidstemmer.screenplay.flow.Screenplay;

import flow.Backstack;
import flow.Flow;

/**
 * Created by jmichaelcodes on 8/27/15.
 */
public class Application extends android.app.Application {
    public final MutableStage stage = new MutableStage();
    public final Screenplay screenplay = new Screenplay(stage);
    private static Application application;

    public void onCreate() {
        application = this;
    }

    public static Application getInstance()         { return application; }
    public static MutableStage getStage()               { return getInstance().stage; }
    public static Screenplay getScreenplay()            { return getInstance().screenplay; }
}
