package com.jash.refreshrecycler;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jash
 * Date: 15-5-19
 * Time: 上午11:33
 */
public class GroupEntry {
    private String text;
    private List<ChildEntry> entries;
    private boolean isExpand;
    public GroupEntry(String text) {
        this.text = text;
        entries = new ArrayList<>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ChildEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ChildEntry> entries) {
        this.entries = entries;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setIsExpand(boolean isExpand) {
        this.isExpand = isExpand;
    }
}
