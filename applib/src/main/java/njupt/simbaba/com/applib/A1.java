package njupt.simbaba.com.applib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class A1 extends Base {
    public static String ACTION_SHARE_PICTURE = "cn.njupt.action.share_picture";
    public static String PERMISSION_OF_SHARE = "cn.njupt.simbaba.share.permission";
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1);

        mRecyclerView = findViewById(R.id.list_view);
        mRecyclerView.setAdapter(new MyAdapter());

        mRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                rv.setTag(child);
                rv.invalidate();
                return false;
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
                RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
                if (layoutManager instanceof GridLayoutManager) {
                    drawGrid(c, (GridLayoutManager) layoutManager);
                } else {
                    drawLinear(c, (LinearLayoutManager) layoutManager);
                }
            }

            private void drawGrid(Canvas c, GridLayoutManager layoutManager) {
                View child = (View) mRecyclerView.getTag();
                if (child == null) {
                    return;
                }

                Paint paint = new Paint();
                paint.setColor(Color.GREEN);
                paint.setStrokeWidth(5);

                int width = child.getWidth();
                int left = child.getLeft();
                int b = layoutManager.getDecoratedBottom(child);
                c.drawLine(left, b, left + width, b, paint);
            }

            private void drawLinear(Canvas c, LinearLayoutManager layoutManager) {
                View child = (View) mRecyclerView.getTag();
                if (child == null) {
                    return;
                }

                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(5);

                int width = child.getWidth();
                int b = layoutManager.getDecoratedBottom(child);
                c.drawLine(0, b, width, b, paint);
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

    private static void sendImplicitBroadcast(Context ctxt, Intent i) {
        PackageManager pm = ctxt.getPackageManager();
        List<ResolveInfo> matches = pm.queryBroadcastReceivers(i, 0);

        for (ResolveInfo resolveInfo : matches) {
            Intent explicit = new Intent(i);
            ComponentName cn =
                    new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName,
                            resolveInfo.activityInfo.name);

            explicit.setComponent(cn);
            ctxt.sendBroadcast(explicit);
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
