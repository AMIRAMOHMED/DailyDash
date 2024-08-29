package com.example.dailydash.planer.views.adpoters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dailydash.R;

import com.example.dailydash.planer.data.database.MealPlan;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
public class PlanedMealAdapter extends RecyclerView.Adapter<PlanedMealAdapter.PlanedMealViewHolder> {

    private List<MealPlan> mealPlanList;
    private OnDeleteClickListener deleteClickListener;


    public PlanedMealAdapter(List<MealPlan> mealPlanList, OnDeleteClickListener deleteClickListener) {
        this.mealPlanList = mealPlanList;
        this.deleteClickListener = deleteClickListener;
    }



    @NonNull
    @Override
    public PlanedMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.planedmealcard, parent, false);
        return new PlanedMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanedMealViewHolder holder, int position) {
        MealPlan mealPlan = mealPlanList.get(position);
        holder.mealName.setText(mealPlan.getMealName());
        holder.dateMeal.setText(mealPlan.getDate());
        holder.deleteMealPlan.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClicked(mealPlan);
            }
        });
        Glide.with(holder.itemView.getContext())
                .load(mealPlan.getMealImg())
                .placeholder(R.drawable.placeholderimage)
                .error(R.drawable.onerorr)
                .into(holder.mealImage);
    }

    @Override
    public int getItemCount() {
        return mealPlanList.size();
    }

    public static class PlanedMealViewHolder extends RecyclerView.ViewHolder {

        TextView mealName,dateMeal;
        ImageButton deleteMealPlan;
        CircleImageView mealImage;

        public PlanedMealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealname);
            deleteMealPlan = itemView.findViewById(R.id.deleteMealPlan);
            mealImage = itemView.findViewById(R.id.mealImage);
            dateMeal= itemView.findViewById(R.id.dateMeal);
        }
    }

    public void updateData(List<MealPlan> newMealPlanList) {
        this.mealPlanList.clear();
        this.mealPlanList.addAll(newMealPlanList);
        notifyDataSetChanged();
    }

    public interface OnDeleteClickListener {
        void onDeleteClicked(MealPlan mealPlan);
    }
}
