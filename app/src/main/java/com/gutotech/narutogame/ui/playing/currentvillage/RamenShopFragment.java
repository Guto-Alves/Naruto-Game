package com.gutotech.narutogame.ui.playing.currentvillage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.ads.AdRequest;
import com.gutotech.narutogame.R;
import com.gutotech.narutogame.data.model.ShopUtils;
import com.gutotech.narutogame.databinding.FragmentRamemShopBinding;
import com.gutotech.narutogame.ui.SectionFragment;
import com.gutotech.narutogame.ui.WarningDialogFragment;
import com.gutotech.narutogame.ui.adapter.ItemShopAdapter;
import com.gutotech.narutogame.utils.FragmentUtils;
import com.gutotech.narutogame.utils.SoundUtil;

public class RamenShopFragment extends Fragment implements SectionFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentRamemShopBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_ramem_shop, container, false);

        RamenShopViewModel viewModel = new ViewModelProvider(this)
                .get(RamenShopViewModel.class);
        binding.setViewModel(viewModel);

        ItemShopAdapter adapter = new ItemShopAdapter(getActivity(),
                getFragmentManager(), viewModel);
        binding.ramensRecyclerView.setHasFixedSize(true);
        binding.ramensRecyclerView.setAdapter(adapter);
        adapter.setItemsList(ShopUtils.getRamens());

        viewModel.getShowWarningEvent().observe(getViewLifecycleOwner(), this::showWarningDialog);

        FragmentUtils.setSectionTitle(getActivity(), R.string.section_ramen_shop);

        binding.adView.loadAd(new AdRequest.Builder().build());

        return binding.getRoot();
    }

    private void showWarningDialog(@StringRes int resid) {
        WarningDialogFragment dialog = WarningDialogFragment.newInstance(getContext(), resid);
        dialog.setCancelable(true);
        dialog.openDialog(getFragmentManager());
        SoundUtil.play(getContext(), R.raw.get_item02);
    }

    @Override
    public int getDescription() {
        return R.string.ramen_shop;
    }
}
