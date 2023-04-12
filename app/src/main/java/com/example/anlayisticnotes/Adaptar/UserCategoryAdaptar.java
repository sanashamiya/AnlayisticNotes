package com.example.anlayisticnotes.Adaptar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.anlayisticnotes.Activity.DetailsMainActivity;
import com.example.anlayisticnotes.R;
import com.example.anlayisticnotes.model.Details;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

public class UserCategoryAdaptar extends RecyclerView.Adapter<UserCategoryAdaptar.ViewHolder> {
    private FirebaseAnalytics mFirebaseAnalytics;

    Context context;
    private List<Details> mData;



    public  UserCategoryAdaptar(Context context, List<Details> data) {
        this.context = context;
        this.mData = data;

    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_details_note,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        holder.UserCategory.setText(mData.get(position).getName());
        holder.detailNsote.setText(mData.get(position).getDetails());
        holder.shMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsMainActivity.class);
                intent.putExtra("name",mData.get(holder.getAdapterPosition()).getName());
                intent.putExtra("details",mData.get(holder.getAdapterPosition()).getDetails());
                btnEvent("DetailsNote@","click","Button");
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return  mData.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView UserCategory;
        public  TextView detailNsote;
        public Button shMore;

        ViewHolder(View itemView) {
            super(itemView);
            this.UserCategory = itemView.findViewById(R.id.nameTitle);
            this.detailNsote = itemView.findViewById(R.id.details);
            this.shMore = itemView.findViewById(R.id.showMore);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
    private void btnEvent(String id, String type, String content){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID,id);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE,type);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,content);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle);//save it at firebase
        Toast.makeText(context, "clicked", Toast.LENGTH_LONG).show();

    }

}


