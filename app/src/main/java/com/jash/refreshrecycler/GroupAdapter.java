package com.jash.refreshrecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

/**
 * Created by jash
 * Date: 15-5-19
 * Time: 上午11:39
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<Object> list;
    private MyItemAnimator animator;
    private RecyclerView recyclerView;

    public GroupAdapter(Context context, List<Object> list, MyItemAnimator animator) {
        this.context = context;
        this.list = list;
        this.animator = animator;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case GROUP:
                view = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false);
                view.setOnClickListener(this);
                break;
            case CHILD:
                view = LayoutInflater.from(context).inflate(R.layout.child_item, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object o = list.get(position);
        if (o instanceof GroupEntry){
            holder.group_text.setText(((GroupEntry) o).getText());
        }
        if (o instanceof ChildEntry){
            holder.child_text.setText(((ChildEntry) o).getText());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static final int GROUP = 0;
    public static final int CHILD = 1;
    @Override
    public int getItemViewType(int position) {
        Object o = list.get(position);
        if (o instanceof GroupEntry){
            return GROUP;
        }
        if (o instanceof ChildEntry){
            return CHILD;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void onClick(View v) {
        int position = recyclerView.getChildAdapterPosition(v);
        switch (getItemViewType(position)){
            case GROUP:
                GroupEntry group = (GroupEntry) list.get(position);
                if (group.isExpand()){
                    group.setIsExpand(false);
                    animator.setXY(v.getLeft(), v.getTop());
                    list.removeAll(group.getEntries());
                    notifyItemRangeRemoved(position + 1, group.getEntries().size());
                } else {
                    group.setIsExpand(true);
                    animator.setXY(v.getLeft(), v.getTop());
                    list.addAll(position + 1, group.getEntries());
                    notifyItemRangeInserted(position + 1, group.getEntries().size());
                }
                break;
            case CHILD:

                break;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView group_text;
        private TextView child_text;

        public ViewHolder(View itemView) {
            super(itemView);
            group_text = (TextView) itemView.findViewById(R.id.group_text);
            child_text = ((TextView) itemView.findViewById(R.id.child_text));
        }
    }
}
