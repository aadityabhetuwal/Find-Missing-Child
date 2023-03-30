package com.aaditya.findmissingperson.Dashboard.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.aaditya.findmissingperson.Dashboard.Activities.StatsDetailActivity;
import com.aaditya.findmissingperson.ModelClasses.StatsModel;
import com.aaditya.findmissingperson.R;

import java.util.List;


public class StatsAdpter extends RecyclerView.Adapter<StatsAdpter.ViewHolder> {

    private Activity mContext ;
    private List<StatsModel> mUsers ;
    private FirebaseAuth auth ;
    public StatsAdpter(Activity mContext, List<StatsModel> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }


    @NonNull
    @Override
    public StatsAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate( R.layout.statslistrecyler, parent, false);
        return new StatsAdpter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StatsAdpter.ViewHolder holder, int position) {
        final StatsModel stats = mUsers.get(position);
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        final String userid = firebaseUser.getUid();
        holder.tvyear.setText(stats.getYear());

        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, StatsDetailActivity.class );
                intent.putExtra( "description",stats.getDescription().trim());
                intent.putExtra( "tvyear",stats.getYear().trim());
                mContext.startActivity( intent );
            //    Toast.makeText( mContext,"clicked",Toast.LENGTH_SHORT ).show();
            }
        } );

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvyear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvyear  = itemView.findViewById(R.id.tvyear);

        }
    }
}
