package com.example.shop;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.Data.User;
import com.example.shop.Data.UserViewModel;
import com.example.shop.Data.utilities.UserDataCollector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AdminFragment extends Fragment implements UserDataCollector {

    private RecyclerView recyclerView;
    private List<User> userList;
    private UserViewModel userViewModel;
    private List<User> selectedUsers;

    private TextView textViewSelectedUser;

    public AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        selectedUsers = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin, container, false);
        hideKeyboard(this.getActivity());

        //Setup RecyclerView
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Button buttonBezahlen = rootView.findViewById(R.id.button_zahlen);
        Button buttonBack = rootView.findViewById(R.id.button_back);
        textViewSelectedUser = rootView.findViewById(R.id.textView_selectedUser);

        textViewSelectedUser.setText(String.valueOf(selectedUsers.size()));

        //navigate back
        buttonBack.setOnClickListener(view -> NavHostFragment.findNavController(AdminFragment.this)
                .navigate(R.id.action_adminFragment_to_FirstFragment));

        buttonBezahlen.setOnClickListener(view ->{
            for (User selectedUser : selectedUsers) {
                selectedUser.resetCurrentSchulden();
                userViewModel.update(selectedUser);
            }
        });

        //observe userList and update recyclerView on change
        userViewModel.getAllUser().observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users != null) {
                    userList = users;
                    recyclerView.setAdapter(new UserCardAdapter(getActivity(),userList, AdminFragment.this));
                }
            }
        });
        return rootView;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void selectedUser(List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
        textViewSelectedUser.setText(String.valueOf(selectedUsers.size()));
    }
}