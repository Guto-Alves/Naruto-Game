package com.gutotech.narutogame.ui.playing.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.gutotech.narutogame.R;
import com.gutotech.narutogame.data.model.CharOn;
import com.gutotech.narutogame.data.model.Classe;
import com.gutotech.narutogame.databinding.FragmentFormulasBinding;
import com.gutotech.narutogame.ui.SectionFragment;
import com.gutotech.narutogame.utils.FragmentUtils;

public class FormulasFragment extends Fragment implements SectionFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFormulasBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_formulas, container, false);

        binding.msgLayout.titleTextView.setText(R.string.about_formulas);
        binding.msgLayout.descriptionTextView.setText(R.string.about_formulas_description);

        if (CharOn.character.getClasse() == Classe.TAI) {
            binding.taiTextView.setText("* 14 + ");
        } else if (CharOn.character.getClasse() == Classe.BUK) {
            binding.bukTextView.setText("* 14");
        } else if (CharOn.character.getClasse() == Classe.NIN) {
            binding.ninTextView.setText("* 14 + ");
        } else {
            binding.genTextView.setText("* 14");
        }

        FragmentUtils.setSectionTitle(getActivity(), R.string.section_formulas);

        binding.adView.loadAd(new AdRequest.Builder().build());

        return binding.getRoot();
    }

    @Override
    public int getDescription() {
        return R.string.game_formulas;
    }
}
