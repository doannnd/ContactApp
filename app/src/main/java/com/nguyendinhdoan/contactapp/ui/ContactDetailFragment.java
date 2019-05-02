package com.nguyendinhdoan.contactapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nguyendinhdoan.contactapp.R;
import com.nguyendinhdoan.contactapp.model.Contact;

public class ContactDetailFragment extends Fragment {

    private static final String TAG =
            ContactDetailFragment.class.getSimpleName();

    private Toolbar toolbar;
    Contact contact;

    public static ContactDetailFragment newInstance(Contact contact) {
        ContactDetailFragment fragment = new ContactDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("contact", contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started.");
        if (getArguments() != null) {
           contact  = getArguments().getParcelable("contact");
            Log.d(TAG, "onCreate: contact name: " + contact.getName());
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started.");
        return inflater.inflate(R.layout.fragment_contact_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
         * setup toolbar for contact detail fragment
         * */
        toolbar = view.findViewById(R.id.toolbar_detail);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        /*
         * remove previous fragment from back stack
         * therefore navigating back
         * */
        ImageView backArrowImageView = view.findViewById(R.id.iv_back_arrow);
        backArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on back arrow icon");
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });

        /*
         * show the contact detail fragment selected in contact list view
         * */
        ImageView editImageView = view.findViewById(R.id.iv_edit);
        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked edit icon");
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            Log.d(TAG, "onOptionsItemSelected: clicked delete icon");
        }
        return super.onOptionsItemSelected(item);
    }
}
