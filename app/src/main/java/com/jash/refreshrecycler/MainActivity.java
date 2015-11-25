package com.jash.refreshrecycler;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private PullToRefreshRecyclerView refresh;
    private RecyclerView recycler;
    private GroupAdapter.ViewHolder holder;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FrameLayout layout = (FrameLayout) findViewById(R.id.layout);
        refresh = ((PullToRefreshRecyclerView) findViewById(R.id.refresh));
        refresh.setMode(PullToRefreshBase.Mode.BOTH);
        recycler = ((RecyclerView) findViewById(R.id.recycler));
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        List<Object> list1  = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GroupEntry groupEntry = new GroupEntry(String.format("第%02d组", i));
            list1.add(groupEntry);
            for (int j = 0; j < 10; j++) {
                groupEntry.getEntries().add(new ChildEntry(String.format("第%02d组 第%02d条", i, j)));
            }
        }
        MyItemAnimator animator = new MyItemAnimator();
        final GroupAdapter adapter = new GroupAdapter(this, list1, animator);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItemViewType(position) == GroupAdapter.GROUP) {
                    return 3;
                }
                return 1;
            }
        });
        recycler.setLayoutManager(manager);
//        recycler.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.format("第%03d", i));
        }
//        recycler.setAdapter(new MyAdapter(this, list));
        holder = adapter.onCreateViewHolder(layout, GroupAdapter.GROUP);
        layout.addView(holder.itemView);
        holder.itemView.setVisibility(View.GONE);
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(animator);
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 滚动状态改变
             * @param recyclerView
             * @param newState
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            /**
             *　滚动中
             * @param recyclerView
             * @param dx
             * @param dy
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    View view = recyclerView.getChildAt(i);
                    int position = recyclerView.getChildAdapterPosition(view);
                    if (view.getTop() > holder.itemView.getHeight()) {
                        ViewCompat.setY(holder.itemView, 0);
                        break;
                    }
                    if (adapter.getItemViewType(position) == GroupAdapter.GROUP) {
                        if (view.getTop() < 0) {
                            ViewCompat.setY(holder.itemView, 0);
                        } else {
                            ViewCompat.setY(holder.itemView, view.getTop() - holder.itemView.getHeight());
                        }
                        break;
                    }
                }
                View view = recyclerView.getChildAt(0);
                int position = recyclerView.getChildAdapterPosition(view);
                for (int i = position; i >= 0; i--) {
                    if (adapter.getItemViewType(i) == GroupAdapter.GROUP) {
                        adapter.onBindViewHolder(holder, i);
                        break;
                    }
                }
            }
        });

    }

}
