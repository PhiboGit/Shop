package com.example.shop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.Data.User;
import com.example.shop.Data.UserViewModel;

import java.util.List;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardAdapter.CardViewHolder> {

    private UserViewModel userViewModel;
    private Activity activity;
    private List<User> userList;

    public static class CardViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewUserName;
        public TextView textViewUserID;
        public TextView textViewUserSchulden;
        public Button buttonUserDelete;


        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewUserID = itemView.findViewById(R.id.textViewUserID);
            textViewUserSchulden = itemView.findViewById(R.id.textViewUserSchulden);
            buttonUserDelete = itemView.findViewById(R.id.button_delete);
        }
    }

    public UserCardAdapter(Activity activity, List<User> userList) {
        this.userList = userList;
        this.activity = activity;
    }

    View view;

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user, parent, false);
        userViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(UserViewModel.class);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        final User currentUser = userList.get(position);

        holder.textViewUserName.setText(currentUser.getName());
        holder.textViewUserID.setText(String.valueOf(currentUser.getPersonalNummer()));
        holder.textViewUserSchulden.setText(convertToEuro(currentUser.getSchulden()));

        holder.buttonUserDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(currentUser.getPersonalNummer());
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
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

    public void openDialog(int userID){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Benutzer Löschen?");
        // Set up the buttons
        builder.setPositiveButton("Löschen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userViewModel.deleteUserByID(userID);
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
}
