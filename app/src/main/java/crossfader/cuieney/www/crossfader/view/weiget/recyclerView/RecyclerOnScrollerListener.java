package crossfader.cuieney.www.crossfader.view.weiget.recyclerView;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;

/**
 * Created by Administrator on 2016/12/29 0029.
 */
public class RecyclerOnScrollerListener extends OnScrollListener{
    RecyclerView recyclerView;
    public RecyclerOnScrollerListener(RecyclerView recyclerView){
        this.recyclerView=recyclerView;
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        RecyclerView.LayoutManager layout = recyclerView.getLayoutManager();
        if (layout != null) {
            if (layout instanceof LinearLayoutManager) {
                int lastVisibleItem = ((LinearLayoutManager) layout).findLastVisibleItemPosition();
                int firstVisibleItem = ((LinearLayoutManager) layout).findFirstVisibleItemPosition();
                Log.e("scroll",lastVisibleItem+"   "+firstVisibleItem);
                if (lastVisibleItem-firstVisibleItem==1){
                    recyclerView.scrollToPosition(lastVisibleItem-1);
                }else {
                    recyclerView.scrollToPosition(firstVisibleItem);
                }
            }
        }
    }
}
