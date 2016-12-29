package crossfader.cuieney.www.crossfader.view.weiget.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import crossfader.cuieney.www.crossfader.view.weiget.recyclerView.itfc.ItemTouchAdapterHelper;
import crossfader.cuieney.www.crossfader.view.weiget.recyclerView.itfc.ItemTouchUiHelper;

/**
 * Created by cuieney on 16/12/26.
 */
public class PowerRecyclerView extends RecyclerView {

    public FrameLayout mDecorView;
    private int[] mDecorViewLocation = new int[2];
    private WrapAdapter mWrapAdapter;
    private ItemTouchUiHelper mUiHelper;
    private ItemTouchHelper mItemTouchHelper;
    private ItemTouchAdapterHelper.OnStartDragListener onStartDragListener;

    public PowerRecyclerView(Context context) {
        super(context);
        initView();
    }

    public PowerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PowerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        mDecorView = (FrameLayout) ((Activity) getContext()).getWindow().getDecorView();
        mDecorView.getLocationOnScreen(mDecorViewLocation);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mWrapAdapter = new WrapAdapter(adapter, onStartDragListener);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mWrapAdapter, mUiHelper);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(this);
        super.setAdapter(mWrapAdapter);
    }


    public void setItemTouchHelper(ItemTouchUiHelper mUiHelper) {
        this.mUiHelper = mUiHelper;
    }

    public void setOnStartDragListener(ItemTouchAdapterHelper.OnStartDragListener onStartDragListener) {
        this.onStartDragListener = onStartDragListener;
    }

    //避免用户自己调用getAdapter() 引起的ClassCastException
    @Override
    public Adapter getAdapter() {
        if (mWrapAdapter != null)
            return mWrapAdapter.getOriginalAdapter();
        else
            return null;
    }

    private class WrapAdapter extends Adapter<ViewHolder> implements ItemTouchAdapterHelper {

        private RecyclerView.Adapter adapter;
        private OnStartDragListener mDragStartListener;

        public WrapAdapter(RecyclerView.Adapter adapter, OnStartDragListener mDragStartListener) {
            this.adapter = adapter;
            this.mDragStartListener = mDragStartListener;
        }

        public RecyclerView.Adapter getOriginalAdapter() {
            return this.adapter;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {

                        mDragStartListener.onStartDrag(holder);
                    }
                    return false;
                }
            });
            holder.itemView.setRotationY(0);
            holder.itemView.setTranslationX(0);
        }

        @Override
        public int getItemCount() {
            return adapter.getItemCount();
        }

        @Override
        public void onItemDismiss(int position) {
            notifyItemRemoved(position);
        }
    }


    public class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private Drawable mDivider;
        private int mOrientation;

        /**
         * Sole constructor. Takes in a {@link Drawable} to be used as the interior
         * divider.
         *
         * @param divider A divider {@code Drawable} to be drawn on the RecyclerView
         */
        public DividerItemDecoration(Drawable divider) {
            mDivider = divider;
        }

        /**
         * Draws horizontal or vertical dividers onto the parent RecyclerView.
         *
         * @param canvas The {@link Canvas} onto which dividers will be drawn
         * @param parent The RecyclerView onto which dividers are being added
         * @param state The current RecyclerView.State of the RecyclerView
         */
        @Override
        public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
            if (mOrientation == LinearLayoutManager.HORIZONTAL) {
                drawHorizontalDividers(canvas, parent);
            } else if (mOrientation == LinearLayoutManager.VERTICAL) {
                drawVerticalDividers(canvas, parent);
            }
        }

        /**
         * Determines the size and location of offsets between items in the parent
         * RecyclerView.
         *
         * @param outRect The {@link Rect} of offsets to be added around the child
         *                view
         * @param view The child view to be decorated with an offset
         * @param parent The RecyclerView onto which dividers are being added
         * @param state The current RecyclerView.State of the RecyclerView
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            if (parent.getChildAdapterPosition(view) <= 0) {
                return;
            }
            mOrientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            if (mOrientation == LinearLayoutManager.HORIZONTAL) {
                outRect.left = mDivider.getIntrinsicWidth();
            } else if (mOrientation == LinearLayoutManager.VERTICAL) {
                outRect.top = mDivider.getIntrinsicHeight();
            }
        }

        /**
         * Adds dividers to a RecyclerView with a LinearLayoutManager or its
         * subclass oriented horizontally.
         *
         * @param canvas The {@link Canvas} onto which horizontal dividers will be
         *               drawn
         * @param parent The RecyclerView onto which horizontal dividers are being
         *               added
         */
        private void drawHorizontalDividers(Canvas canvas, RecyclerView parent) {
            int parentTop = parent.getPaddingTop();
            int parentBottom = parent.getHeight() - parent.getPaddingBottom();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int parentLeft = child.getRight() + params.rightMargin;
                int parentRight = parentLeft + mDivider.getIntrinsicWidth();

                mDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
                mDivider.draw(canvas);
            }
        }

        /**
         * Adds dividers to a RecyclerView with a LinearLayoutManager or its
         * subclass oriented vertically.
         *
         * @param canvas The {@link Canvas} onto which vertical dividers will be
         *               drawn
         * @param parent The RecyclerView onto which vertical dividers are being
         *               added
         */
        private void drawVerticalDividers(Canvas canvas, RecyclerView parent) {
            int parentLeft = parent.getPaddingLeft();
            int parentRight = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int parentTop = child.getBottom() + params.bottomMargin;
                int parentBottom = parentTop + mDivider.getIntrinsicHeight();

                mDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
                mDivider.draw(canvas);
            }
        }
    }

}
