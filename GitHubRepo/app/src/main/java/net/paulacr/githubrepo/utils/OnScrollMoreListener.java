package net.paulacr.githubrepo.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class OnScrollMoreListener extends RecyclerView.OnScrollListener {

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    private int currentPage = 1;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private LinearLayoutManager linearLayoutManager;
    private OnScrollMore listener;

    public interface OnScrollMore {

        void onScrollMorePages(int page);
    }


    public OnScrollMoreListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    public void setListener(OnScrollMore instance) {
        this.listener = instance;
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = linearLayoutManager.getItemCount();
        firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            currentPage++;
            listener.onScrollMorePages(currentPage);
            loading = true;
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);


    }
}
