package com.thrucare.healthcare.activity.provider.qualification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.pojo.QualificationList;
import com.thrucare.healthcare.pojo.modelClasses.ProviderQualificationList;

import java.util.List;

public class QualificationAttachmentAdapter extends RecyclerView.Adapter<QualificationAttachmentAdapter.MyViewHolder> {
    List<ProviderQualificationList.Attachment> attachments;
    Context ctx;
    public QualificationAttachmentAdapter(List<ProviderQualificationList.Attachment> attachmentList, Context ctx) {
        this.attachments  = attachmentList;
        this.ctx  = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.qualification_attachment_item, parent, false);

        return new QualificationAttachmentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      holder.tvAttachment.setText(attachments.get(position).getUrl());
    }



    @Override
    public int getItemCount() {
        return attachments.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{
          private TextView tvAttachment;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAttachment  = itemView.findViewById(R.id.tv_value_attachment_qualification);
        }
    }
}
