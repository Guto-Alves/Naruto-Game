package com.gutotech.narutogame.ui.playing.academy;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.gutotech.narutogame.R;
import com.gutotech.narutogame.databinding.FragmentAcademyTrainningBinding;
import com.gutotech.narutogame.ui.SectionFragment;
import com.gutotech.narutogame.ui.adapter.DistributedPointsAdapter;
import com.gutotech.narutogame.ui.playing.user.FormulasFragment;
import com.gutotech.narutogame.utils.FragmentUtils;
import com.gutotech.narutogame.utils.SoundUtil;
import com.gutotech.narutogame.utils.SpannableStringBuilderCustom;

public class AcademyTrainingFragment extends Fragment implements SectionFragment {
    private FragmentAcademyTrainningBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AcademyTrainingViewModel viewModel = new ViewModelProvider(this)
                .get(AcademyTrainingViewModel.class);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_academy_trainning,
                container, false);
        mBinding.setViewModel(viewModel);

        mBinding.msgLayout.titleTextView.setText(R.string.attribute_training_title);
        SpannableStringBuilderCustom builder = new SpannableStringBuilderCustom(getContext());
        builder.append(R.string.attribute_training_description);
        builder.append();
        builder.append(R.string.game_formulas,
                new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        FragmentUtils.goTo(getActivity(), new FormulasFragment());
                    }
                }, new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.colorGreen)));
        builder.append(".");
        mBinding.msgLayout.descriptionTextView.setText(builder.getString());
        mBinding.msgLayout.descriptionTextView.setMovementMethod(LinkMovementMethod.getInstance());

        mBinding.trainingPointsLayout.titleTextView.setText(R.string.training_points);

        viewModel.getFreePoints().observe(getViewLifecycleOwner(), freePoints -> {
            if (freePoints == 0) {
                mBinding.trainingPointsLayout.msgConstraintLayout.setVisibility(View.GONE);
            } else {
                SpannableStringBuilderCustom stringBuilder = new SpannableStringBuilderCustom(getContext());
                stringBuilder.append(R.string.you_still_have);
                stringBuilder.append(getString(R.string.total_free_points, freePoints), R.color.colorGreen);
                stringBuilder.append(R.string.to_distribute_in_their_attributes);
                mBinding.trainingPointsLayout.descriptionTextView.setText(stringBuilder.getString());
                mBinding.trainingPointsLayout.msgConstraintLayout.setVisibility(View.VISIBLE);

                mBinding.scrollView.post(() ->
                        mBinding.scrollView.smoothScrollTo(0, mBinding.trainingPointsLayout.getRoot().getTop())
                );

                YoYo.with(Techniques.Bounce)
                        .duration(1200)
                        .playOn(mBinding.trainingPointsLayout.msgConstraintLayout);
            }
        });

        mBinding.distributedPointsRecyclerView.setHasFixedSize(true);
        DistributedPointsAdapter adapter = new DistributedPointsAdapter(getContext(), viewModel);
        mBinding.distributedPointsRecyclerView.setAdapter(adapter);

        viewModel.getDistributedPoints().observe(
                getViewLifecycleOwner(), adapter::setDistributedPoints
        );

        viewModel.getPlaySound().observe(getViewLifecycleOwner(), aVoid ->
                SoundUtil.play(getContext(), R.raw.aim)
        );

        FragmentUtils.setSectionTitle(getActivity(), R.string.section_attribute_training);

        mBinding.adView.loadAd(new AdRequest.Builder().build());

        return mBinding.getRoot();
    }

    @Override
    public int getDescription() {
        return R.string.attribute_training;
    }
}
