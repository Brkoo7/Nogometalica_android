package ibrkovic.fesb.hr.nogometalica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Ivan on 27.1.2017..
 */
public class UserDataSource {
    private SQLiteDatabase database;
    private	MySQLiteHelper	dbHelper;

    public UserDataSource(Context context)	{
        dbHelper = new MySQLiteHelper(context);

        try {
            this.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void	open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void	close()	{
        database.close();
    }

    public void addUserToDb(String name, String rezultat) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("rezultat", rezultat);
        database.insert("User", null, values);
    }





    public ArrayList<User> getAllUser()	{
        ArrayList<User> User = new	ArrayList<User>();

        Cursor cursor =	database.rawQuery("SELECT *	FROM user", null);
        cursor.moveToFirst();

        while(!	cursor.isAfterLast()) {
            User korisnik =	new User();
            korisnik.setId(cursor.getInt(0));
            korisnik.setName(cursor.getString(1));
            korisnik.setRezultat(cursor.getString(2));
            User.add(korisnik);
            cursor.moveToNext();
        }

        cursor.close();

        Log.d("Broj korisnika", "Ready " + String.valueOf(User.size()) + " Usera");
        return User;
    }




}
