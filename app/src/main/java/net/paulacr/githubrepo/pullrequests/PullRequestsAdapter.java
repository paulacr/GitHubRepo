package net.paulacr.githubrepo.pullrequests;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.paulacr.githubrepo.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PullRequestsAdapter extends RecyclerView.Adapter<PullRequestsAdapter.ViewHolder> {


    private List<PullRequests> pullRequestList;
    private Context context;

    public PullRequestsAdapter(Context context, List<PullRequests> pullRequest) {
        this.context = context;
        this.pullRequestList = pullRequest;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView prName;
        TextView prDescritption;
        TextView prDate;
        TextView prUsername;
        CircleImageView prProfilePicture;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            prName = (TextView) itemView.findViewById(R.id.prName);
            prDescritption = (TextView) itemView.findViewById(R.id.prDescription);
            prDate = (TextView) itemView.findViewById(R.id.prDate);
            prUsername = (TextView) itemView.findViewById(R.id.prUsername);
            prProfilePicture = (CircleImageView) itemView.findViewById(R.id.prProfilePicture);

        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pullrequest, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PullRequests pullRequests = pullRequestList.get(position);

        holder.prName.setText(pullRequests.getTitle());
        holder.prDescritption.setText(pullRequests.getBody());
        holder.prDate.setText(pullRequests.getDate());
        holder.prUsername.setText(pullRequests.getUser().getLogin());

        //Picasso implementation
        Picasso.with(context).load(pullRequests.getUser().getAvatarUrl())
                .placeholder(R.drawable.user_placeholder).into(holder.prProfilePicture);
    }

    @Override
    public int getItemCount() {
        return pullRequestList.size();
    }


}
