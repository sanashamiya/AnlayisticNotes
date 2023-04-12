package com.example.anlayisticnotes.Adaptar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anlayisticnotes.Activity.MainActivity;
import com.example.anlayisticnotes.R;
import com.example.anlayisticnotes.model.Category;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;
//Address Adaptar
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private FirebaseAnalytics FirebaseAnalytics;

    Context context ;
    private final List<Category> mData;
    private LayoutInflater mInflater;


    public CategoryAdapter(Context context, List<Category> data) {
        this.context = context;
        this.mData = data;

    }


    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_category_notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.TitleCategory.setText(mData.get(position).getTitelCategory());
        Category category = mData.get(position);
        holder.btnTitell.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("title", mData.get(position).getTitelCategory());
                context.startActivity(intent);
                btnEvent("addressNote@","click","Button");
            }
        });
    }

    @Override
    public int getItemCount() {
        return  mData.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public  TextView TitleCategory;
        public Button btnTitell;
        public CardView card;

        ViewHolder(View itemView) {
            super(itemView);
            this.TitleCategory = itemView.findViewById(R.id.TitleCategory);
            this.btnTitell = itemView.findViewById(R.id.Titelbtn);
            this.card = itemView.findViewById(R.id.noteCard);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }



    private void btnEvent(String id, String type, String content){
        Bundle bundle = new Bundle();
        bundle.putString(com.google.firebase.analytics.FirebaseAnalytics.Param.ITEM_ID,id);
        bundle.putString(com.google.firebase.analytics.FirebaseAnalytics.Param.CONTENT_TYPE,type);
        bundle.putString(com.google.firebase.analytics.FirebaseAnalytics.Param.ITEM_NAME,content);
        FirebaseAnalytics.logEvent(com.google.firebase.analytics.FirebaseAnalytics.Event.SELECT_CONTENT,bundle);//save it at firebase
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();

    }

}

