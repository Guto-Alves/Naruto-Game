package com.gutotech.narutogame.ui.loggedin.support;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gutotech.narutogame.R;
import com.gutotech.narutogame.databinding.FragmentSupportBinding;
import com.gutotech.narutogame.ui.SectionFragment;
import com.gutotech.narutogame.ui.adapter.TicketsAdapter;
import com.gutotech.narutogame.utils.FragmentUtils;

public class SupportFragment extends Fragment implements SectionFragment {

    public SupportFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSupportBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_support, container, false);

        SupportViewModel viewModel = new ViewModelProvider(this).get(SupportViewModel.class);
        binding.setViewModel(viewModel);

        binding.msgLayout.titleTextView.setText(R.string.welcome_to_support_system);
        binding.msgLayout.descriptionTextView.setText(R.string.welcome_to_support_system_description);

        if (getArguments() != null) {
            binding.ticketCreatedLayout.titleTextView.setText(R.string.congratulations);
            binding.ticketCreatedLayout.descriptionTextView.setText(R.string.ticket_created);
            binding.ticketCreatedLayout.msgConstraintLayout.setVisibility(View.VISIBLE);
        }

        binding.openSupportTicketButton.setOnClickListener(v ->
                FragmentUtils.goTo(getActivity(), new SupportNewFragment(), true)
        );

        binding.ticketsRecyclerView.setHasFixedSize(true);

        TicketsAdapter adapter = new TicketsAdapter(getActivity(), ticket -> {
            Bundle args = new Bundle();
            args.putSerializable("ticket", ticket);
            SupportTicketFragment supportTicketFragment = new SupportTicketFragment();
            supportTicketFragment.setArguments(args);
            FragmentUtils.goTo(getActivity(), supportTicketFragment, true);
        });
        binding.ticketsRecyclerView.setAdapter(adapter);

        viewModel.getTickets().observe(getViewLifecycleOwner(), adapter::setTicketList);

        FragmentUtils.setSectionTitle(getActivity(), R.string.section_support);

        return binding.getRoot();
    }

    @Override
    public int getDescription() {
        return R.string.support;
    }
}
