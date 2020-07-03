package com.example.shop.Data;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {
    private UserDAO userDAO;
    private LiveData<List<User>> allUser;

    UserRepository(Application application){
        UserDatabase database = UserDatabase.getDatabase(application);
        userDAO = database.userDAO();
        allUser = userDAO.getAll();
    }

    LiveData<List<User>> getAllUser() {
        return allUser;
    }

    LiveData<User> getUserByID(int id){
        return userDAO.getUserByID(id);
    }

    void update(final User user){
        UserDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.update(user);
        });
    }

    void insert(final User user){
        UserDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insertAll(user);
        });
    }
}
