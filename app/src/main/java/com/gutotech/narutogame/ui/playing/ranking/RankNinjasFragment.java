package com.gutotech.narutogame.ui.playing.ranking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.ads.AdRequest;
import com.gutotech.narutogame.R;
import com.gutotech.narutogame.databinding.FragmentRankNinjasBinding;
import com.gutotech.narutogame.ui.SectionFragment;
import com.gutotech.narutogame.ui.adapter.RankingNinjasAdapter;
import com.gutotech.narutogame.utils.FragmentUtils;

public class RankNinjasFragment extends Fragment implements SectionFragment {

    public RankNinjasFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentRankNinjasBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_rank_ninjas, container, false);

        RankNinjasViewModel viewModel = new ViewModelProvider(this)
                .get(RankNinjasViewModel.class);
        binding.setViewModel(viewModel);

        binding.nameEditText.setOnEditorActionListener((v, actionId, event) -> {
            viewModel.onFilterClick();
            return true;
        });

        binding.rankNinjasRecyclerView.setHasFixedSize(true);
        RankingNinjasAdapter adapter = new RankingNinjasAdapter(getContext());
        binding.rankNinjasRecyclerView.setAdapter(adapter);

        viewModel.getNinjas().observe(getViewLifecycleOwner(), adapter::setNinjas);

        viewModel.getProgressBarEvent().observe(getViewLifecycleOwner(), aVoid -> {
            int visibility = binding.progressBar.getVisibility();
            if (visibility == View.VISIBLE) {
                binding.progressBar.setVisibility(View.GONE);
            } else {
                binding.progressBar.setVisibility(View.VISIBLE);
            }
        });

        FragmentUtils.setSectionTitle(getActivity(), R.string.section_ninjas_ranking);

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        return binding.getRoot();
    }

    @Override
    public int getDescription() {
        return R.string.ninjas;
    }
}
