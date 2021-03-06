package com.example.shop.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * from Benutzer ORDER BY name ASC")
    LiveData<List<User>> getAll();

    @Query(("SELECT * from Benutzer where personalNummer = :id Limit 1"))
    LiveData<User> getUserByID(int id);

    @Update
    void update(User user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(User... users);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("DELETE from Benutzer where personalNummer = :id")
    void deleteUserByID(int id);

    @Query("DELETE FROM Benutzer")
    void deleteAll();

}
