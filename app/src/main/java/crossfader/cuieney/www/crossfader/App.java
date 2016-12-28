package crossfader.cuieney.www.crossfader;

import android.app.Application;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;

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
        FeedbackAPI.init(this,"23576107");
    }
}
