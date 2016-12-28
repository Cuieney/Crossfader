package crossfader.cuieney.www.crossfader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import crossfader.cuieney.www.crossfader.recyclerView.PowerLayoutManager;
import crossfader.cuieney.www.crossfader.recyclerView.PowerRecyclerView;

public class MainTestActivity extends AppCompatActivity {

    public PowerRecyclerView mRecyclerView;
    public MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        mRecyclerView = (PowerRecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new PowerLayoutManager());
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        mAdapter = new MyAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);
        View view = new View(this);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


}
