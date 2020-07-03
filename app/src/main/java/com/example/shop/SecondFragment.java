package com.example.shop;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shop.Data.User;
import com.example.shop.Data.UserViewModel;

import java.util.List;

public class SecondFragment extends Fragment {

    private UserViewModel userViewModel;
    private List<User> userList;
    private int currentUserID;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentUserID = getArguments().getInt("ID");

        TextView textViewCount = (TextView) view.findViewById(R.id.textview_second);
        TextView textViewID = (TextView) view.findViewById(R.id.textView_ID);
        TextView textViewName = (TextView) view.findViewById(R.id.textView_name);

        hideKeyboard(this.getActivity());

        textViewID.setText(String.valueOf(currentUserID));





        userViewModel.getAllUser().observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                textViewCount.setText(String.valueOf(users.size()));
                userList = users;
            }
        });

        userViewModel.getUserByID(currentUserID).observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                textViewName.setText(user.getName());
            }
        });





        view.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
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
}