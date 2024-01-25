package com.gutotech.narutogame.ui.playing.academy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.gutotech.narutogame.data.model.CharOn;
import com.gutotech.narutogame.data.model.Graduation;
import com.gutotech.narutogame.data.model.Score;
import com.gutotech.narutogame.data.model.Scroll;
import com.gutotech.narutogame.data.repository.CharacterRepository;
import com.gutotech.narutogame.ui.adapter.GraduationsAdapter;
import com.gutotech.narutogame.utils.SingleLiveEvent;

public class GraduationsViewModel extends ViewModel implements GraduationsAdapter.OnGraduateClickListener {
    private SingleLiveEvent<Void> mUpdateGraduationsEvent = new SingleLiveEvent<>();

    public GraduationsViewModel() {
    }

    LiveData<Void> getUpdateGraduationsEvent() {
        return mUpdateGraduationsEvent;
    }

    @Override
    public synchronized void onGraduateClick(int graduationId, Graduation graduation) {
        CharOn.character.setGraduationId(graduationId);
        CharOn.character.addTitle(CharOn.character.getVillage().getTitleIndex(graduationId));
        CharOn.character.incrementScore(Score.GRADUATION);
        CharacterRepository.getInstance().save(CharOn.character);
        mUpdateGraduationsEvent.call();

        if (graduationId == 1) {
            CharOn.character.getBag().addScroll(
                    new Scroll(
                            String.valueOf(CharOn.character.getVillage().ordinal() + 1),
                            CharOn.character.getVillage()), 2);
        }
    }
}
