package crossfader.cuieney.www.crossfader;

import android.app.Application;

import com.feetsdk.android.FeetSdk;


/**
 * Created by cuieney on 16/12/8.
 */
public class App extends Application {

    private static App app;
    public synchronized static App getCtx(){
        if (app == null) {
            app = new App();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        FeetSdk.getInstance(this).init("5b133060-46f9-4223-ae75-70d59dda2a25","feet");
    }
}
