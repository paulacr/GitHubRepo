package net.paulacr.githubrepo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.data.PullRequests;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by paularosa on 4/2/16.
 */
public class PullRequestsAdapter  extends RecyclerView.Adapter<PullRequestsAdapter.ViewHolder> {


    private List<PullRequests> pullRequestList;
    private Context context;

    public PullRequestsAdapter(Context context, List<PullRequests> pullRequest) {
        this.context = context;
        this.pullRequestList = pullRequest;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.prName)
        TextView prName;
        @Bind(R.id.prDescription)
        TextView prDescritption;
        @Bind(R.id.prDate)
        TextView prDate;
        @Bind(R.id.prUsername)
        TextView prUsername;
        @Bind(R.id.prProfilePicture)
        CircleImageView prProfilePicture;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
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
