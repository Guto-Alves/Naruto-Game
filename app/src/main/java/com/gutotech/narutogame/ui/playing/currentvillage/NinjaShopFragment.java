package com.gutotech.narutogame.ui.playing.currentvillage;

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
import com.gutotech.narutogame.databinding.FragmentNinjaShopBinding;
import com.gutotech.narutogame.ui.SectionFragment;
import com.gutotech.narutogame.ui.WarningDialogFragment;
import com.gutotech.narutogame.ui.adapter.ItemShopAdapter;
import com.gutotech.narutogame.utils.FragmentUtils;
import com.gutotech.narutogame.utils.SoundUtil;

public class NinjaShopFragment extends Fragment implements SectionFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNinjaShopBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_ninja_shop, container, false);

        NinjaShopViewModel viewModel = new ViewModelProvider(this)
                .get(NinjaShopViewModel.class);
        binding.setViewModel(viewModel);

        ItemShopAdapter adapter = new ItemShopAdapter(getActivity(),
                getFragmentManager(), viewModel);
        binding.shopItemsRecyclerView.setHasFixedSize(true);
        binding.shopItemsRecyclerView.setAdapter(adapter);

        viewModel.getShopItems().observe(getViewLifecycleOwner(), adapter::setItemsList);

        viewModel.getShowWarningDialogEvent().observe(getViewLifecycleOwner(), resid -> {
            WarningDialogFragment warningDialog = WarningDialogFragment.newInstance(
                    getContext(), resid);
            warningDialog.setCancelable(true);
            warningDialog.openDialog(getFragmentManager());
            SoundUtil.play(getContext(), R.raw.get_item02);
        });

        FragmentUtils.setSectionTitle(getActivity(), R.string.section_ninja_shop);

        binding.adView.loadAd(new AdRequest.Builder().build());

        return binding.getRoot();
    }

    @Override
    public int getDescription() {
        return R.string.ninja_shop;
    }
}
