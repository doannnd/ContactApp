package com.nguyendinhdoan.contactapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.nguyendinhdoan.contactapp.R;
import com.nguyendinhdoan.contactapp.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {

    private static final String TAG = ContactFragment.class.getSimpleName();

    private static final int APP_BAR_STANDARD = 0;
    private static final int APP_BAR_SEARCH = 1;

    private int appBarState;
    private AppBarLayout searchAppBar, viewContactAppBar;
    private EditText searchContactEditText;

    private RecyclerView contactListRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started.");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: started.");
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: started.");

        // app bar
        viewContactAppBar = view.findViewById(R.id.app_bar_view_contact);
        searchAppBar = view.findViewById(R.id.search_app_bar);
        searchContactEditText = view.findViewById(R.id.et_search_contacts);
        contactListRecyclerView = view.findViewById(R.id.rv_contact_list);

        // navigate to add contacts fragment
        FloatingActionButton addContactFloatingActionButton = view.findViewById(R.id.fab_add_contact);
        addContactFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked fab");
            }
        });

        // toogle search app bar
        ImageView searchIconImageView = view.findViewById(R.id.iv_search_icon);
        searchIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked search icon");
                toogleToolbarState();
            }
        });
        
        // back arrow to the contact fragment
        ImageView backArrowImageView = view.findViewById(R.id.iv_back_arrow);
        backArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow");
                toogleToolbarState();
            }
        });

        setupContactList();
        
    }

    private void setupContactList() {
        Log.d(TAG, "setupContactList: started.");
        contactListRecyclerView.setHasFixedSize(true);
        contactListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // test contact list
        List<Contact> contactList = new ArrayList<>();
        contactList.add(new Contact("Kai", "0395616595", "android", "nguyendinhdoanjdk@gmail.com", "https://i.redd.it/tpsnoz5bzo501.jpg"));
        contactList.add(new Contact("Kudo", "0395616595", "android", "nguyendinhdoanjdk@gmail.com", "https://i.redd.it/tpsnoz5bzo501.jpg"));
        contactList.add(new Contact("Kenz", "0395616595", "android", "nguyendinhdoanjdk@gmail.com", "https://i.redd.it/tpsnoz5bzo501.jpg"));
        contactList.add(new Contact("Conan", "0395616595", "android", "nguyendinhdoanjdk@gmail.com", "https://i.redd.it/tpsnoz5bzo501.jpg"));
        contactList.add(new Contact("Mori", "0395616595", "android", "nguyendinhdoanjdk@gmail.com", "https://i.redd.it/tpsnoz5bzo501.jpg"));

        // get contact adapter
        ContactAdapter contactAdapter = new ContactAdapter(getActivity(), contactList);
        contactListRecyclerView.setAdapter(contactAdapter);
    }

    /**
     * Initial the app bar state tootle
     */
    private void toogleToolbarState() {
        Log.d(TAG, "toogleToolbarState: started.");
        if (appBarState == APP_BAR_STANDARD) {
            setAppBarState(APP_BAR_SEARCH);
        } else {
            setAppBarState(APP_BAR_STANDARD);
        }
    }

    /**
     * Set app bar state for either search mode or view contacts mode
     * @param state: 0 - app bar standard or 1 - app bar search
     */
    private void setAppBarState(int state) {
        Log.d(TAG, "setAppBarState: change appbar state to: " + state);

        appBarState = state;

        if (appBarState == APP_BAR_STANDARD) {
            viewContactAppBar.setVisibility(View.VISIBLE);
            searchAppBar.setVisibility(View.GONE);

            // hide the keyboard
            hideKeyboard();

        } else if (appBarState == APP_BAR_SEARCH) {
            viewContactAppBar.setVisibility(View.GONE);
            searchAppBar.setVisibility(View.VISIBLE);

            // show the keyboard and focus to the edit text
            showKeyboard();
            searchContactEditText.requestFocus();
        }
        
    }

    /**
     * show the soft key board auto
     */
    private void showKeyboard() {
        try {
            Context context = getActivity();
            if (context != null) {
                InputMethodManager manager = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (manager != null) {
                    manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "setAppBarState: show the keyboard error");
            e.printStackTrace();
        }
    }

    /**
     * hide the key board auto
     */
    private void hideKeyboard() {
        try {
            Context context = getActivity();
            if (context != null) {
                View view = getView();
                InputMethodManager manager = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (manager != null && view != null) {
                    manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "setAppBarState: hide keyboard error");
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setAppBarState(APP_BAR_STANDARD);
    }

}
