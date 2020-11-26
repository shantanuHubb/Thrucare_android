package com.thrucare.healthcare.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.activity.provider.AddMedicalReportActivity;
import com.thrucare.healthcare.activity.provider.AllergiesSearchActivity;
import com.thrucare.healthcare.activity.provider.deviceOrder.AddNewDeviceOrderActivity;
import com.thrucare.healthcare.activity.provider.insurance.AddNewInsuranceActivity;
import com.thrucare.healthcare.activity.provider.qualification.AddNewQualificationActivity;
import com.thrucare.healthcare.pojo.ReactionModel;
import com.thrucare.healthcare.utils.ConstantsUtils;

import java.util.List;

public class AllergiesReactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ReactionModel> list;
    Context ctx;
    static AllergiesReactionAdapter obj;
    private int getAdapterForReactionPosition;
    SetOnClickAttachmentListener setOnClickAttachmentListener;
    String useFrom ;

    public static AllergiesReactionAdapter getInstance(){
        return obj;
    }

    public AllergiesReactionAdapter(List<ReactionModel> list, Activity activity , String useFrom) {
        this.list = list;
        this.ctx = activity;
        this.useFrom = useFrom;
        if(useFrom.equalsIgnoreCase(ConstantsUtils.ADD_ATTACHMENT)){
            this.setOnClickAttachmentListener  = (SetOnClickAttachmentListener ) AddMedicalReportActivity.getInstance();
        }else if(useFrom.equalsIgnoreCase(ConstantsUtils.ADD_ATTACHMENT_QUALIFICATION)){
            this.setOnClickAttachmentListener  = (SetOnClickAttachmentListener ) AddNewQualificationActivity.getInstance();
        }
        else if(useFrom.equalsIgnoreCase(ConstantsUtils.ADD_REASONS_DEVICE_ORDER)){
            this.setOnClickAttachmentListener  = (SetOnClickAttachmentListener ) AddNewDeviceOrderActivity.getInstance();
        }
        else if(useFrom.equalsIgnoreCase(ConstantsUtils.ADD_ATTACHMENT_INSURANCE)){
            this.setOnClickAttachmentListener  = (SetOnClickAttachmentListener ) AddNewInsuranceActivity.getInstance();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(useFrom.equalsIgnoreCase(ConstantsUtils.ADD_ATTACHMENT)||
                useFrom.equalsIgnoreCase(ConstantsUtils.ADD_ATTACHMENT_QUALIFICATION) ||
                useFrom.equalsIgnoreCase(ConstantsUtils.ADD_ATTACHMENT_INSURANCE) ){
            return 0;
        } else {
            return  1;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           if(useFrom.equalsIgnoreCase(ConstantsUtils.ADD_ATTACHMENT)||
                   useFrom.equalsIgnoreCase(ConstantsUtils.ADD_ATTACHMENT_QUALIFICATION) ||
                   useFrom.equalsIgnoreCase(ConstantsUtils.ADD_ATTACHMENT_INSURANCE)){
               View itemView = LayoutInflater.from(parent.getContext())
                       .inflate(R.layout.attachment_upload_item, parent, false);

               return new AllergiesReactionAdapter.MyViewAttachment(itemView);
           }else {
               View itemView = LayoutInflater.from(parent.getContext())
                       .inflate(R.layout.allergies_reaction_item, parent, false);

               return new AllergiesReactionAdapter.MyViewHolder(itemView);
           }


    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

         if(useFrom.equalsIgnoreCase(ConstantsUtils.ADD_ATTACHMENT)){
             MyViewAttachment holder1   = (MyViewAttachment) holder;
             holder1.tvAttachement.setHint(list.get(position).getReactionValue());
             if(list.get(position).getIsAdd()==1){
                 holder1.imgPlusAttachment.setImageDrawable(ctx.getDrawable(R.drawable.plus));
             }else {
                 holder1.imgPlusAttachment.setImageDrawable(ctx.getDrawable(R.drawable.minus));
             }

             holder1.tvAttachement.setText(list.get(position).getReactionValueForAPI());
         }
       else if(useFrom.equalsIgnoreCase(ConstantsUtils.ADD_ATTACHMENT_QUALIFICATION)){
            MyViewAttachment holder1   = (MyViewAttachment) holder;
            holder1.tvAttachement.setHint(list.get(position).getReactionValue());
            if(list.get(position).getIsAdd()==1){
                holder1.imgPlusAttachment.setImageDrawable(ctx.getDrawable(R.drawable.plus));
            }else {
                holder1.imgPlusAttachment.setImageDrawable(ctx.getDrawable(R.drawable.minus));
            }

            holder1.tvAttachement.setText(list.get(position).getReactionValueForAPI());
        }
         else if(useFrom.equalsIgnoreCase(ConstantsUtils.ADD_ATTACHMENT_INSURANCE)){
             MyViewAttachment holder1   = (MyViewAttachment) holder;
             holder1.tvAttachement.setHint(list.get(position).getReactionValue());
             if(list.get(position).getIsAdd()==1){
                 holder1.imgPlusAttachment.setImageDrawable(ctx.getDrawable(R.drawable.plus));
             }else {
                 holder1.imgPlusAttachment.setImageDrawable(ctx.getDrawable(R.drawable.minus));
             }

             holder1.tvAttachement.setText(list.get(position).getReactionValueForAPI());
         }else {
             MyViewHolder holder1   = (MyViewHolder) holder;
             holder1.editReaction.setHint(list.get(position).getReactionValue());
             if(list.get(position).getIsAdd()==1){
                 holder1.imgPlus.setImageDrawable(ctx.getDrawable(R.drawable.plus));
             }else {
                 holder1.imgPlus.setImageDrawable(ctx.getDrawable(R.drawable.minus));
             }

             holder1.editReaction.setText(list.get(position).getReactionValueForAPI());
         }

    }



    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        EditText editReaction;
        ImageView imgPlus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            editReaction  = itemView.findViewById(R.id.spinner_reason_allergires);
            imgPlus  = itemView.findViewById(R.id.img_plus_allergies);
            imgPlus.setOnClickListener(this);
            editReaction.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.img_plus_allergies:
                    int addOrPlus  = list.get( getAdapterPosition()).getIsAdd();
                    if(addOrPlus==1){
                        if(useFrom.equalsIgnoreCase(ConstantsUtils.MEDICATION)){
                            list.add(new ReactionModel(1, "Reason" , ""));
                        }else if(useFrom.equalsIgnoreCase(ConstantsUtils.MEDICATION_ADD)){
                            list.add(new ReactionModel(1, "Reason" , ""));
                        }
                        else if(useFrom.equalsIgnoreCase(ConstantsUtils.ADD_LAB_OTHER_ORDERS)){
                            list.add(new ReactionModel(1, "Reason" , ""));
                        }
                        else {
                            list.add(new ReactionModel(1, "Reaction" , ""));
                        }

                        list.get(getAdapterPosition()).setIsAdd(0);
                        notifyDataSetChanged();
                    }else {
                        list.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    }

                    break;


                case R.id.spinner_reason_allergires:
                    if(useFrom.equalsIgnoreCase(ConstantsUtils.MEDICATION)){
                        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putInt("ADAPTER_POSITION", getAdapterPosition()).apply();
                        ctx.startActivity(new Intent(ctx, AllergiesSearchActivity.class)
                                .putExtra("SEARCH_FOR", "MEDICATION"));
                    }else if(useFrom.equalsIgnoreCase(ConstantsUtils.REACTION)){
                        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putInt("ADAPTER_POSITION",
                                getAdapterPosition()).apply();
                        ctx.startActivity(new Intent(ctx, AllergiesSearchActivity.class)
                                .putExtra("SEARCH_FOR", "REACTION"));
                    }else if(useFrom.equalsIgnoreCase(ConstantsUtils.MEDICATION_ADD)) {
                        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putInt("ADAPTER_POSITION", getAdapterPosition()).apply();
                        ctx.startActivity(new Intent(ctx, AllergiesSearchActivity.class)
                                .putExtra("SEARCH_FOR", "MEDICATION_ADD"));
                    }
                    else if(useFrom.equalsIgnoreCase(ConstantsUtils.ADD_REASONS_DEVICE_ORDER)) {
                        PreferenceManager.getDefaultSharedPreferences(ctx).edit().putInt("ADAPTER_POSITION", getAdapterPosition()).apply();
                        ctx.startActivity(new Intent(ctx, AllergiesSearchActivity.class)
                                .putExtra("SEARCH_FOR", ConstantsUtils.ADD_REASONS_DEVICE_ORDER));
                    }
                    else {
                    PreferenceManager.getDefaultSharedPreferences(ctx).edit().putInt("ADAPTER_POSITION", getAdapterPosition()).apply();
                    ctx.startActivity(new Intent(ctx, AllergiesSearchActivity.class)
                            .putExtra("SEARCH_FOR", "ADD_LAB"));
                }



                    break;
            }
        }
    }


    public class MyViewAttachment  extends RecyclerView.ViewHolder implements View.OnClickListener {
          EditText tvAttachement ;
          ImageView imgPlusAttachment ;
          FrameLayout ffmLayoutAttachment;
          TextView tvUpload;
        public MyViewAttachment(@NonNull View itemView) {
            super(itemView);

            tvAttachement  = itemView.findViewById(R.id.spinner_reason_attachment);
            imgPlusAttachment  = itemView.findViewById(R.id.img_plus_attachment);
            ffmLayoutAttachment  = itemView.findViewById(R.id.ffm_attachment_layout);
            tvUpload  = itemView.findViewById(R.id.tv_upload);
            imgPlusAttachment.setOnClickListener(this);
            tvUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(useFrom.equalsIgnoreCase(ConstantsUtils.ADD_ATTACHMENT)){
                        setOnClickAttachmentListener.onClick(getAdapterPosition());
                    }else if(useFrom.equalsIgnoreCase(ConstantsUtils.ADD_ATTACHMENT_QUALIFICATION)){
                        setOnClickAttachmentListener.onClick(getAdapterPosition());

                    }
                    else if(useFrom.equalsIgnoreCase(ConstantsUtils.ADD_ATTACHMENT_INSURANCE)){
                        setOnClickAttachmentListener.onClick(getAdapterPosition());

                    }

                }
            });
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.img_plus_attachment:
                    int addOrPlus  = list.get( getAdapterPosition()).getIsAdd();
                    if(addOrPlus==1){
                        list.add(new ReactionModel(1, "Attachment" , ""));
                        list.get(getAdapterPosition()).setIsAdd(0);
                        notifyDataSetChanged();
                    }else {
                        list.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                    break;
            }
        }
    }


    public interface SetOnClickAttachmentListener{
        void onClick(int position);
    }
}
