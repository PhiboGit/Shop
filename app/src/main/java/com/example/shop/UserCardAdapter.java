package com.example.shop;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.Data.Converter.EuroConverter;
import com.example.shop.Data.User;
import com.example.shop.Data.UserViewModel;

import java.util.List;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardAdapter.CardViewHolder> {

    public static class CardViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewUserName;
        public TextView textViewUserID;
        public TextView textViewUserSchulden;
        public Button buttonUserDelete;
        public ConstraintLayout layout;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewUserID = itemView.findViewById(R.id.textViewUserID);
            textViewUserSchulden = itemView.findViewById(R.id.textViewUserSchulden);
            buttonUserDelete = itemView.findViewById(R.id.button_delete);
            layout = itemView.findViewById(R.id.cardViewUserLayout);

        }
    }

    private UserViewModel userViewModel;
    private Activity activity;
    private List<User> userList;
    private View view;
    final boolean[] userSelected;


    public UserCardAdapter(Activity activity, List<User> userList) {
        this.userList = userList;
        this.activity = activity;
        userSelected = new boolean[getItemCount()];
    }

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
        holder.textViewUserSchulden.setText(EuroConverter.convertToEuro(currentUser.getSchulden()));

        holder.buttonUserDelete.setOnClickListener(view -> openDialog(currentUser.getPersonalNummer(), currentUser.getName()));

        holder.layout.setOnClickListener(view -> {
            if (!userSelected[position]) {
                userSelected[position] = true;
                holder.layout.setBackgroundColor(view.getResources().getColor(R.color.colorAccent));
            }
            else {
                userSelected[position] =false;
                holder.layout.setBackgroundColor(view.getResources().getColor(R.color.colorPrimary));
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void openDialog(int userID, String userName){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Willst du "+ userName + " löschen?");
        // Set up the buttons
        builder.setPositiveButton("Löschen", (dialog, which) -> userViewModel.deleteUserByID(userID));
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
