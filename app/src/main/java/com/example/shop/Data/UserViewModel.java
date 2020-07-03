package com.example.shop.Data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;
    private LiveData<List<User>> allUser;

    public UserViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
        allUser = repository.getAllUser();
    }

    public LiveData<User> getUserByID(int id){
        return repository.getUserByID(id);
    }

    public LiveData<List<User>> getAllUser() {
        return allUser;
    }

    public void insert(User user){
        repository.insert(user);
    }
}
