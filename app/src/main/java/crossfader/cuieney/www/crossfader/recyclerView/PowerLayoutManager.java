package crossfader.cuieney.www.crossfader.recyclerView;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import crossfader.cuieney.www.crossfader.App;

/**
 * Created by cuieney on 16/12/26.
 */
public class PowerLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        WindowManager wm = (WindowManager) App.getCtx().getSystemService(Context.WINDOW_SERVICE);
        int pxW = wm.getDefaultDisplay().getWidth();//屏幕宽度
        int pxH = wm.getDefaultDisplay().getHeight();

        for (int i = 0; i < getItemCount(); i++) {
            View child = recycler.getViewForPosition(i);
            child.setTop(child.getHeight()*i);
            measureChildWithMargins(child, 0, 0);
            addView(child);

            int width = getDecoratedMeasuredWidth(child);
            int height = getDecoratedMeasuredHeight(child);
            layoutDecorated(child, 0, 0, width, height);
            //缩放
            if (i == getItemCount()-1) {
                child.setTranslationX(0);
                child.setBackgroundColor(Color.parseColor("#ff0000"));

            } else if (i == getItemCount()-2) {
                child.setTranslationX(pxW/2);
                child.setBackgroundColor(Color.parseColor("#00ff00"));
            } else {
                child.setScaleX(0.8f);
                child.setScaleY(0.8f);
                child.setTranslationX(pxW/2-child.getWidth()/2);
//                child.setTranslationY(child.getHeight()*i);
            }
        }
    }

}
