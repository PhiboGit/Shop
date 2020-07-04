package com.example.shop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.util.StringUtil;

import com.example.shop.Data.User;
import com.example.shop.Data.UserViewModel;

import java.util.List;

public class FirstFragment extends Fragment {

    final int andminID = 00000000;

    UserViewModel viewModel;
    Button buttonLogin;
    private List<User> userList;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonLogin = (Button) view.findViewById(R.id.button_login);
        EditText inputText = view.findViewById(R.id.textInputField);

        viewModel.getAllUser().observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userList = users;
            }
        });

        //visible password
        inputText.setTransformationMethod(null);

        buttonLogin.setOnClickListener(view1 -> {

            //add new user
            int newUserId = Integer.parseInt(inputText.getText().toString());

            if (newUserId == andminID){
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_adminFragment);
            }
            //if user does not already exists open dialog to enter name
            else if (!isUserByIDinList(newUserId)){
                openDialog(newUserId);
            }
            else {
                navToSecondFragment(newUserId);
            }
        });

        disableButton();
        //disable enable button on text length
        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = inputText.getText().length();
                if (length == 8) enableButton();
                else disableButton();
            }
        });
    }

    private void disableButton(){
        buttonLogin.setAlpha(.25f);
        buttonLogin.setClickable(false);
    }

    private void enableButton(){
        buttonLogin.setAlpha(1f);
        buttonLogin.setClickable(true);
    }

    private void navToSecondFragment(int userID){
        Bundle bundle = new Bundle();
        bundle.putInt("ID", userID);
        NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
    }

    private String nachname;

    private void openDialog(int userID){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Nachname");
        final EditText input = new EditText(getContext());
        input.setFilters(new InputFilter[] {
                (cs, start, end, spanned, dStart, dEnd) -> {
                    // TODO Auto-generated method stub
                    if(cs.equals("")){ // for backspace
                        return cs;
                    }
                    if(cs.toString().matches("[a-zA-ZÖÜÄß]+")){
                        return cs;
                    }
                    return "";
                }
        });
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nachname= input.getText().toString().toLowerCase();
                nachname = nachname.substring(0,1).toUpperCase() + nachname.substring(1);
                openDialog2(userID);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void openDialog2(int userID){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Vorname");
        final EditText input = new EditText(getContext());
        input.setFilters(new InputFilter[] {
                (cs, start, end, spanned, dStart, dEnd) -> {
                    // TODO Auto-generated method stub
                    if(cs.equals("")){ // for backspace
                        return cs;
                    }
                    if(cs.toString().matches("[a-zA-ZÖÜÄß]+")){
                        return cs;
                    }
                    return "";
                }
        });
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String vorname = input.getText().toString().toLowerCase();
                vorname = vorname.substring(0,1).toUpperCase() + vorname.substring(1);
                String fullname = nachname + ", " + vorname;
                viewModel.insert(new User(userID, fullname));
                navToSecondFragment(userID);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private boolean isUserByIDinList(int id){
        for (User user: userList) {
            if ( id == user.getPersonalNummer()) return true;
        }
        return false;
    }

}