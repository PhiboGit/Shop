package com.example.shop;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shop.Data.User;
import com.example.shop.Data.UserViewModel;

import java.util.Calendar;
import java.util.List;

public class SecondFragment extends Fragment implements View.OnClickListener{

    private UserViewModel userViewModel;
    private List<User> userList;
    private int currentUserID;
    private int betrag = 0;
    private TextView textViewBuchung;
    private User currentUser;
    private User copyCurrentUser;
    private Button buttonFlat;
    int calendarWeek;

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
        hideKeyboard(this.getActivity());
        currentUserID = getArguments().getInt("ID");
        calendarWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);

        TextView textViewCount = (TextView) view.findViewById(R.id.textview_second);
        TextView textViewID = (TextView) view.findViewById(R.id.textView_ID);
        TextView textViewName = (TextView) view.findViewById(R.id.textView_name);
        textViewBuchung = (TextView) view.findViewById(R.id.textView_buchung);
        TextView textViewSchulden = (TextView) view.findViewById(R.id.textView_schulden);

        textViewID.setText(String.valueOf(currentUserID));
        textViewBuchung.setText(convertToEuro(betrag));

        Button buttonBuchen = (Button) view.findViewById(R.id.button_buchen);
        Button button30 = (Button) view.findViewById(R.id.button_30);
        Button button50 = (Button) view.findViewById(R.id.button_50);
        Button button60 = (Button) view.findViewById(R.id.button_60);
        Button button80 = (Button) view.findViewById(R.id.button_80);
        Button button100 = (Button) view.findViewById(R.id.button_100);
        Button button120 = (Button) view.findViewById(R.id.button_120);
        Button buttonKaffee = (Button) view.findViewById(R.id.button_kaffee);
        buttonFlat = (Button) view.findViewById(R.id.button_flat);
        Button buttonBack = (Button) view.findViewById(R.id.button_back);

        buttonBuchen.setOnClickListener(this);
        button30.setOnClickListener(this);
        button50.setOnClickListener(this);
        button60.setOnClickListener(this);
        button80.setOnClickListener(this);
        button100.setOnClickListener(this);
        button120.setOnClickListener(this);
        buttonKaffee.setOnClickListener(this);
        buttonFlat.setOnClickListener(this);
        buttonBack.setOnClickListener(this);




        userViewModel.getAllUser().observe(getActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users != null) {
                    textViewCount.setText(String.valueOf(users.size()));
                    userList = users;
                }
            }
        });

        userViewModel.getUserByID(currentUserID).observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null){
                    currentUser = user;
                    copyCurrentUser = new User(user);
                    textViewName.setText(user.getName());
                    textViewSchulden.setText(convertToEuro(user.getSchulden()));
                    if (user.getFlatWeek() == calendarWeek){
                        buttonFlat.setAlpha(.25f);
                        buttonFlat.setClickable(false);
                        buttonFlat.setText("FLAT AKTIV");
                    }
                }

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

    public void clickedFlat(){
        Toast.makeText(getContext(), "text", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_30:
                betrag += 30;
                copyCurrentUser.setC30(copyCurrentUser.getC30()+1);
                textViewBuchung.setText(convertToEuro(betrag));
            break;
            case R.id.button_50:
                betrag += 50;
                copyCurrentUser.setC50(copyCurrentUser.getC50()+1);
                textViewBuchung.setText(convertToEuro(betrag));
                break;
            case R.id.button_60:
                betrag += 60;
                copyCurrentUser.setC60(copyCurrentUser.getC60()+1);
                textViewBuchung.setText(convertToEuro(betrag));
                break;
            case R.id.button_80:
                betrag += 80;
                copyCurrentUser.setC80(copyCurrentUser.getC80()+1);
                textViewBuchung.setText(convertToEuro(betrag));
                break;
            case R.id.button_100:
                betrag += 100;
                copyCurrentUser.setC100(copyCurrentUser.getC100()+1);
                textViewBuchung.setText(convertToEuro(betrag));
                break;
            case R.id.button_120:
                betrag += 120;
                copyCurrentUser.setC120(copyCurrentUser.getC120()+1);
                textViewBuchung.setText(convertToEuro(betrag));
                break;
            case R.id.button_kaffee:
                betrag += 40;
                copyCurrentUser.setC40(copyCurrentUser.getC40()+1);
                textViewBuchung.setText(convertToEuro(betrag));
                break;
            case R.id.button_flat:
                betrag += 400;
                copyCurrentUser.setC400(copyCurrentUser.getC400()+1);
                textViewBuchung.setText(convertToEuro(betrag));
                copyCurrentUser.setFlatWeek(calendarWeek);
                buttonFlat.setAlpha(.25f);
                buttonFlat.setClickable(false);
                break;
            case R.id.button_buchen:
                userViewModel.update(copyCurrentUser);
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
                break;
            case R.id.button_back:
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
                break;

        }
    }

    private String convertToEuro(int value){
        int euro = value/100;
        int cent = value%100;
        String centString = "";
        if (cent == 0) centString = "00";
        else centString = String.valueOf(cent);
        String s = euro +","+centString+"€";
        return s;

    }
}