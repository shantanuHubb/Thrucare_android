package com.thrucare.healthcare.activity.patient.familyHistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.pojo.FamilyHistory;

import java.util.List;

public class FamilyHistoryAdapter  extends RecyclerView.Adapter<FamilyHistoryAdapter.MyViewHolder>{
     List<FamilyHistory.Item> items;
     FamilyHistoryFragment ctx;
     SetOnClickInterface setOnClickInterface;

    public FamilyHistoryAdapter(List<FamilyHistory.Item> itemList  , FamilyHistoryFragment context){
      this.ctx = context;
      this.items = itemList;
      this.setOnClickInterface  = (SetOnClickInterface ) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.family_history_item, parent, false);

        return new FamilyHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvProblemName.setText("Evidence "+items.get(position).getConditions().get(0).getProblem()+" " +"( on Age "+items.get(position).getConditions().get(0).getOnsetAge()+")");
        holder.tvAge.setText(items.get(position).getConditions().get(1).getProblem()+" " +"(on Age "+items.get(position).getConditions().get(1).getOnsetAge()+")");
        holder.tvRelationName.setText(items.get(position).getRelation().getDisplay());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvRelationName, tvProblemName, tvAge ;
        ImageView imgDelete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRelationName  = itemView.findViewById(R.id.tv_relation_name);
            tvProblemName  = itemView.findViewById(R.id.tv_problem_name);
            tvAge  = itemView.findViewById(R.id.tv_age);
            imgDelete  = itemView.findViewById(R.id.img_delete);
            imgDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.img_delete:
                    int position  = getAdapterPosition();
                    setOnClickInterface.onClickOnDeleteFamilyHistory(position);

                    break;

            }
        }
    }


    interface SetOnClickInterface{
        void onClickOnDeleteFamilyHistory(int position);
    }
}
