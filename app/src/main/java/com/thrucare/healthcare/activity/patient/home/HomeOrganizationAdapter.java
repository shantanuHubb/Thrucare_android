package com.thrucare.healthcare.activity.patient.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.thrucare.healthcare.R;
import com.thrucare.healthcare.pojo.OriganzationTypeInternalModel;

import java.util.List;

public class HomeOrganizationAdapter extends RecyclerView.Adapter<HomeOrganizationAdapter.MyViewHolder> {


    List<OriganzationTypeInternalModel> list;
    HomeFragment obj;
    SetOnClickOrganzation setOnClickOrganzation;
    Context ctx;


    public HomeOrganizationAdapter(List<OriganzationTypeInternalModel> resObjList , HomeFragment obj , Context ctx){
       this.list  = resObjList;
       this.obj  = obj;
       this.setOnClickOrganzation  = (SetOnClickOrganzation) obj;
       this.ctx  = ctx;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.organization_type_item, parent, false);

        return new HomeOrganizationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(list.get(position).isSelected==1){
            holder.tvOrganizationType.setTextColor(ctx.getColor(R.color.white));
            holder.cardOrganizationType.setCardBackgroundColor(ctx.getColor(R.color.black));
            holder.tvOrganizationType.setText(list.get(position).getCode());
        }else {
            holder.tvOrganizationType.setTextColor(ctx.getColor(R.color.black));
            holder.cardOrganizationType.setCardBackgroundColor(ctx.getColor(R.color.white));
            holder.tvOrganizationType.setText(list.get(position).getCode());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvOrganizationType;
        CardView cardOrganizationType;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrganizationType  =itemView.findViewById(R.id.tv_organizations_type);
            cardOrganizationType  =itemView.findViewById(R.id.card_origanzation);
            cardOrganizationType.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.card_origanzation:
                    int positionAdapter  = getAdapterPosition();
                    for(int i =0 ; i<list.size() ; i++){
                        list.get(i).setIsSelected(0);
                    }
                    list.get(positionAdapter).setIsSelected(1);

                    setOnClickOrganzation.onClick(getAdapterPosition());
                    notifyDataSetChanged();
                    break;
            }
        }
    }

    public interface SetOnClickOrganzation{
       void  onClick(int adapterPostion);
    }
}
