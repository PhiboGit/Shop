package com.example.shop;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shop.Data.Converter.EuroConverter;
import com.example.shop.Data.User;
import com.example.shop.Data.UserViewModel;

import java.util.Calendar;

public class SecondFragment extends Fragment implements View.OnClickListener{

    private UserViewModel userViewModel;
    private int currentUserID;
    private int betrag = 0;
    private TextView textViewBuchung;
    private User currentUser;
    private User copyCurrentUser;
    private Button buttonFlat;
    private int calendarWeek;
    private ValueAnimator valueAnimator;
    private TextView textViewFade;

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
        //get loginUser ID
        currentUserID = getArguments().getInt("ID");
        calendarWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);

        //setup TextView and Buttons
        TextView textViewID = (TextView) view.findViewById(R.id.textView_ID);
        TextView textViewName = (TextView) view.findViewById(R.id.textView_name);
        textViewBuchung = (TextView) view.findViewById(R.id.textView_buchung);
        TextView textViewSchulden = (TextView) view.findViewById(R.id.textView_schulden);
        textViewFade = (TextView) view.findViewById(R.id.textViewFade);
        textViewFade.setAlpha(0.0f);

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

        textViewID.setText(String.valueOf(currentUserID));
        textViewBuchung.setText(EuroConverter.convertToEuro(betrag));

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

        valueAnimator = ValueAnimator.ofFloat(1f, 0f);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                textViewFade.setAlpha(alpha);
            }
        });



        //observe current User and update View with data
        userViewModel.getUserByID(currentUserID).observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null){
                    currentUser = user;
                    copyCurrentUser = new User(user);
                    textViewName.setText(user.getName());
                    textViewSchulden.setText(EuroConverter.convertToEuro(user.getCurrentSchulden()));
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_30:
                betrag += 30;
                copyCurrentUser.setC30(copyCurrentUser.getC30()+1);
                textViewBuchung.setText(EuroConverter.convertToEuro(betrag));
                textViewFade.setText("+0,30€");
                valueAnimator.start();
            break;
            case R.id.button_50:
                betrag += 50;
                copyCurrentUser.setC50(copyCurrentUser.getC50()+1);
                textViewBuchung.setText(EuroConverter.convertToEuro(betrag));
                textViewFade.setText("+0,50€");
                valueAnimator.start();
                break;
            case R.id.button_60:
                betrag += 60;
                copyCurrentUser.setC60(copyCurrentUser.getC60()+1);
                textViewBuchung.setText(EuroConverter.convertToEuro(betrag));
                textViewFade.setText("+0,60€");
                valueAnimator.start();
                break;
            case R.id.button_80:
                betrag += 80;
                copyCurrentUser.setC80(copyCurrentUser.getC80()+1);
                textViewBuchung.setText(EuroConverter.convertToEuro(betrag));
                textViewFade.setText("+0,80€");
                valueAnimator.start();
                break;
            case R.id.button_100:
                betrag += 100;
                copyCurrentUser.setC100(copyCurrentUser.getC100()+1);
                textViewBuchung.setText(EuroConverter.convertToEuro(betrag));
                textViewFade.setText("+1,00€");
                valueAnimator.start();
                break;
            case R.id.button_120:
                betrag += 120;
                copyCurrentUser.setC120(copyCurrentUser.getC120()+1);
                textViewBuchung.setText(EuroConverter.convertToEuro(betrag));
                textViewFade.setText("+1,20€");
                valueAnimator.start();
                break;
            case R.id.button_kaffee:
                betrag += 40;
                copyCurrentUser.setC40(copyCurrentUser.getC40()+1);
                textViewBuchung.setText(EuroConverter.convertToEuro(betrag));
                textViewFade.setText("+0,40€");
                valueAnimator.start();
                break;
            case R.id.button_flat:
                betrag += 400;
                copyCurrentUser.setC400(copyCurrentUser.getC400()+1);
                textViewBuchung.setText(EuroConverter.convertToEuro(betrag));
                copyCurrentUser.setFlatWeek(calendarWeek);
                buttonFlat.setAlpha(.25f);
                buttonFlat.setClickable(false);
                textViewFade.setText("+4,00€");
                valueAnimator.start();
                break;
            case R.id.button_buchen:
                copyCurrentUser.addCurrentSchulden(betrag);
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

}