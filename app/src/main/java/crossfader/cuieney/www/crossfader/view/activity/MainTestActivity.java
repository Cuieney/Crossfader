package crossfader.cuieney.www.crossfader.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import crossfader.cuieney.www.crossfader.view.adapter.MyAdapter;
import crossfader.cuieney.www.crossfader.R;
import crossfader.cuieney.www.crossfader.view.weiget.recyclerView.PowerRecyclerView;
import crossfader.cuieney.www.crossfader.view.weiget.recyclerView.itfc.ItemTouchAdapterHelper;
import crossfader.cuieney.www.crossfader.view.weiget.recyclerView.itfc.ItemTouchUiHelper;

public class MainTestActivity extends AppCompatActivity {

    public PowerRecyclerView mRecyclerView;
    public MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        mRecyclerView = (PowerRecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(mRecyclerView.new DividerItemDecoration(getDrawable(R.drawable.divider_sample)));
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        mRecyclerView.setItemTouchHelper(new ItemTouchUiHelper() {
            @Override
            public void onItemDelete(int position, String type) {
            }

            @Override
            public void changeRotation(String type, int degrees) {
            }
        });

        mRecyclerView.setOnStartDragListener(new ItemTouchAdapterHelper.OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
            }
        });
        mAdapter = new MyAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);


    }



}
