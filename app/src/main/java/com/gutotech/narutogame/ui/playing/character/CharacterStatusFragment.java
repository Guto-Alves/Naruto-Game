package com.gutotech.narutogame.ui.playing.character;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.gutotech.narutogame.R;
import com.gutotech.narutogame.data.model.CharOn;
import com.gutotech.narutogame.databinding.FragmentCharacterStatusBinding;
import com.gutotech.narutogame.ui.SectionFragment;
import com.gutotech.narutogame.ui.adapter.AttributesStatusAdapter;
import com.gutotech.narutogame.ui.adapter.FormulasAdapter;
import com.gutotech.narutogame.utils.FragmentUtils;

public class CharacterStatusFragment extends Fragment implements SectionFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCharacterStatusBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_character_status, container, false);

        binding.setCharacter(CharOn.character);

        binding.attributesRecyclerView.setHasFixedSize(true);
        binding.attributesRecyclerView.setAdapter(
                new AttributesStatusAdapter(CharOn.character.getAttributes().asList())
        );

        binding.formulasRecyclerView.setHasFixedSize(true);
        binding.formulasRecyclerView.setAdapter(
                new FormulasAdapter(CharOn.character.getFormulas().asList())
        );

        FragmentUtils.setSectionTitle(getActivity(), R.string.section_character_status);

        return binding.getRoot();
    }

    @Override
    public int getDescription() {
        return R.string.status;
    }
}
