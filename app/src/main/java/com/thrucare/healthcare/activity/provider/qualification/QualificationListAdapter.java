package com.thrucare.healthcare.activity.provider.qualification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.patient.patientQuestionnaire.QuestionListAdapter;
import com.thrucare.healthcare.adapter.AllergiesReactionAdapter;
import com.thrucare.healthcare.pojo.QualificationList;
import com.thrucare.healthcare.pojo.modelClasses.ProviderQualificationList;

import java.util.List;

public class QualificationListAdapter extends RecyclerView.Adapter<QualificationListAdapter.MyViewHolder> {
     private List<ProviderQualificationList.Item> itemList;
     private Context ctx;
     private QualificationAttachmentAdapter mAdapter;

    public QualificationListAdapter(List<ProviderQualificationList.Item> itemList,
                                    QualificationListActivity qualificationListActivity) {
       this.itemList = itemList;
       this.ctx   = qualificationListActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.qualification_list_item, parent, false);

        return new QualificationListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int positionHeading  = position+1;
        holder.tvQualificationHeading.setText("Qualification "+positionHeading);
        holder.tvTypeValue.setText(itemList.get(position).getType().getDisplay());
        holder.tvValue1.setText(itemList.get(position).getValue().getDisplay());
        holder.tvValue1.setText(itemList.get(position).getValue().getDisplay());
        if(itemList.get(position).getPeriod().getStart()!= null)
        {
            holder.tvStartDate.setText(itemList.get(position).getPeriod().getStart().substring(0 , 10) );
        }
        else
            holder.tvStartDate.setText(" ");
        if(itemList.get(position).getPeriod().getEnd() != null)
        {
            holder.tvEndDate.setText(itemList.get(position).getPeriod().getEnd().substring(0 , 10));
        }
        else
        holder.tvEndDate.setText(" ");
        holder.tvIssuerName.setText(itemList.get(position).getIssuer());

        List<ProviderQualificationList.Attachment> attachmentList  = itemList.get(position).getAttachments();
        mAdapter = new QualificationAttachmentAdapter(attachmentList,
                ctx);
        holder.recyclerViewAttachment.setLayoutManager(holder.layoutManager);
        holder.recyclerViewAttachment.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{
           TextView tvQualificationHeading, tvTypeValue , tvValue1, tvStartDate , tvEndDate , tvIssuerName ;
           RecyclerView recyclerViewAttachment ;
           LinearLayoutManager layoutManager;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQualificationHeading  = itemView.findViewById(R.id.tv_qualification_item_heading);
            tvTypeValue  = itemView.findViewById(R.id.tv_type_value_qualification);
            tvValue1  = itemView.findViewById(R.id.tv_value_qualification);
            tvStartDate  = itemView.findViewById(R.id.tv_start_date);
            tvEndDate  = itemView.findViewById(R.id.tv_end_date);
            tvIssuerName  = itemView.findViewById(R.id.tv_issuer_name_qualification);
            recyclerViewAttachment  = itemView.findViewById(R.id.recyclyer_attachment_qualification);
            layoutManager = new LinearLayoutManager(ctx);

        }
    }

}
