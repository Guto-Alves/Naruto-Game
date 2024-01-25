package com.gutotech.narutogame.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.narutogame.R;
import com.gutotech.narutogame.data.firebase.StorageUtils;
import com.gutotech.narutogame.data.model.Character;

import java.util.List;

public class CharacterSelectAdapter extends RecyclerView.Adapter<CharacterSelectAdapter.ViewHolder> {

    public interface CharacterSelecetedListener {
        void onCharacterSelected(Character character);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.profileImageView);
        }
    }

    private List<Character> mCharactersList;
    private CharacterSelecetedListener mListener;
    private int mCharacterSelectedPosition;

    public CharacterSelectAdapter(CharacterSelecetedListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_small_profile, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mCharactersList != null) {
            Character character = mCharactersList.get(position);

            StorageUtils.loadSmallProfile(holder.profileImageView.getContext(),
                    holder.profileImageView, character.getNinja().getId());

            holder.profileImageView.setOnClickListener(v -> {
                mListener.onCharacterSelected(character);
                mCharacterSelectedPosition = position;
                notifyDataSetChanged();
            });

            if (position == mCharacterSelectedPosition) {
                holder.profileImageView.setAlpha(1f);
            } else {
                holder.profileImageView.setAlpha(0.5f);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCharactersList != null ? mCharactersList.size() : 0;
    }

    public void setCharactersList(List<Character> charactersList) {
        mCharactersList = charactersList;
        mCharacterSelectedPosition = 0;
        notifyDataSetChanged();
    }
}
