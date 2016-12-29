package crossfader.cuieney.www.crossfader.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import crossfader.cuieney.www.crossfader.R;

/**
 * Created by xingzhu on 2016/11/15.
 */

public class MyAdapter extends SwipeCardAdapter<MyAdapter.MyHolder> {
    private Context mContext;

    public MyAdapter(Context context, List<String> list) {
        super(list);
        mContext = context;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.setData((String) mList.get(position));
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public MyHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text);
        }

        public void setData(String text) {
            mTextView.setText(text);
        }
    }
}
