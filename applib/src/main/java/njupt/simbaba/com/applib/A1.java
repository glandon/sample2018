package njupt.simbaba.com.applib;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class A1 extends Base {
    public static String ACTION_SHARE_PICTURE = "cn.njupt.action.share_picture";
    public static String PERMISSION_OF_SHARE = "cn.njupt.simbaba.share.permission";
    private RecyclerView mRecyclerView;
    private GestureDetector mGesture;
    private ItemTouchHelper mItemTouchHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1);

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    return makeMovementFlags(ItemTouchHelper.LEFT |
                            ItemTouchHelper.RIGHT|
                            ItemTouchHelper.UP|
                            ItemTouchHelper.DOWN, 0);
                } else {
                    return makeMovementFlags(ItemTouchHelper.UP|
                            ItemTouchHelper.DOWN, 0);
                }
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

        mGesture = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){

            @Override
            public void onLongPress(MotionEvent e) {
                View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (view == null) {
                    return;
                }
                RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(view);
                mItemTouchHelper.startDrag(viewHolder);
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child==null) {
                    return false;
                }

                mRecyclerView.setTag(child);
                Rect rect = new Rect();
                child.getHitRect(rect);
                mRecyclerView.invalidate(rect);

                child.startAnimation(AnimationUtils.loadAnimation(A1.this, android.R.anim.fade_in));
                return true;
            }
        });

        mRecyclerView = findViewById(R.id.list_view);
        mRecyclerView.setAdapter(new MyAdapter());
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return mGesture.onTouchEvent(e);
            }

            /**
             * TODO: why not show toast, why onTouch not executed?
             * @param rv, the RecyclerView
             * @param e, the TouchPoint
             */
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                Toast.makeText(A1.this, "onTouch!", Toast.LENGTH_SHORT).show();
                super.onTouchEvent(rv, e);
            }
        });

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = getResources().getDimensionPixelSize(R.dimen.list_item_gap);
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                View child = (View) mRecyclerView.getTag();
                if (child == null || !child.isShown()) {
                    return;
                }

                Paint paint = new Paint();
                paint.setStrokeWidth(5);

                RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

                if (layoutManager instanceof GridLayoutManager) {
                    paint.setColor(Color.GREEN);
                    drawFocus(c, child, paint, layoutManager);
                } else {
                    paint.setColor(Color.RED);
                    drawFocus(c, child, paint, layoutManager);
                }
            }

            private void drawFocus(Canvas c, View child, Paint paint, RecyclerView.LayoutManager layoutManager) {
                int width = child.getWidth();
                int left = child.getLeft();
                int b = layoutManager.getDecoratedBottom(child);
                c.drawLine(left, b, left + width, b, paint);
            }
        });

        Switch btnSwitch = findViewById(R.id.btn_switch);
        btnSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> layout(isChecked));

        // default LinearLayoutManager
        layout(false);
    }

    private void layout(boolean grid) {
        RecyclerView.LayoutManager layoutManager;

        if (!grid) {
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        } else {
            layoutManager = new GridLayoutManager(this, 2);
        }

        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void onShare(MenuItem item) {
        Intent intent = new Intent(ACTION_SHARE_PICTURE);
        sendBroadcast(intent, PERMISSION_OF_SHARE);

//        if (Build.VERSION.SDK_INT > 25 ) {
//            sendImplicitBroadcast(this, intent);
//        } else {
//            sendBroadcast(intent);
//        }
    }

    /**
     * @deprecated
     */
    private void sendImplicitBroadcast(Intent i) {
        PackageManager pm = getPackageManager();
        List<ResolveInfo> matches = pm.queryBroadcastReceivers(i, 0);

        for (ResolveInfo resolveInfo : matches) {
            Intent explicit = new Intent(i);
            ComponentName cn =
                    new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName,
                            resolveInfo.activityInfo.name);

            explicit.setComponent(cn);
            sendBroadcast(explicit);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private int data[] = {
                0, 1, 1, 1, 2, 4, 1, 6, 8, 1, 3, 7
        };

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(A1.this).inflate(R.layout.item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.title.setText(getString(R.string.app_name));
            holder.details.setText(getString(R.string.details_format, position));

            if (data[position] % 2 == 0) {
                holder.icon.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                holder.icon.setImageResource(android.R.drawable.btn_star_big_off);
            }
        }

        @Override
        public int getItemCount() {
            return data.length;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;
        TextView details;

        MyViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
            details = itemView.findViewById(R.id.details);
        }
    }
}
