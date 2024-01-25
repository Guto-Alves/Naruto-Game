package com.gutotech.narutogame.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.narutogame.R;
import com.gutotech.narutogame.data.firebase.StorageUtils;
import com.gutotech.narutogame.data.model.CharOn;
import com.gutotech.narutogame.data.model.Reward;
import com.gutotech.narutogame.utils.SoundUtil;

import java.util.List;

public class FidelityRewardsAdapter extends RecyclerView.Adapter<FidelityRewardsAdapter.ViewModel> {

    public interface OnReceiveClickListener {
        void onReceiveClick(Reward reward);
    }

    private Context mContext;
    private List<Reward> mRewards;
    private OnReceiveClickListener mOnReceiveClickListener;

    public FidelityRewardsAdapter(Context context, OnReceiveClickListener listener) {
        mContext = context;
        mOnReceiveClickListener = listener;
    }

    public static class ViewModel extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameTextView;
        private TextView descriptionTextView;
        private Button receiveButton;

        public ViewModel(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            receiveButton = itemView.findViewById(R.id.receiveButton);
        }
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_loyalty_reward_item, parent, false);
        return new ViewModel(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {
        if (mRewards != null) {
            final Reward reward = mRewards.get(position);

            StorageUtils.downloadFidelityImage(mContext, holder.imageView, position + 1);

            holder.nameTextView.setText(mContext.getString(
                    R.string.logar_days_followed, position + 1));
            holder.descriptionTextView.setText(reward.toString(mContext));

            int daysOfFidelity = CharOn.character.getDaysOfFidelity();

            if (position == daysOfFidelity && CharOn.character.hasFidelityReward()) {
                holder.receiveButton.setText(R.string.receive);
                holder.receiveButton.setOnClickListener(v -> {
                    mOnReceiveClickListener.onReceiveClick(reward);
                    SoundUtil.play(mContext, R.raw.get_item01);
                });
            } else {
                holder.receiveButton.setOnClickListener(null);

                if (position > daysOfFidelity) {
                    holder.receiveButton.setText(R.string.button_not_received);
                    holder.receiveButton.setBackgroundResource(R.drawable.bg_red_button);
                } else {
                    holder.receiveButton.setText(R.string.received);
                    holder.receiveButton.setBackgroundResource(R.drawable.bg_green_button);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mRewards != null ? mRewards.size() : 0;
    }

    public void setRewards(List<Reward> rewards) {
        mRewards = rewards;
        notifyDataSetChanged();
    }
}
