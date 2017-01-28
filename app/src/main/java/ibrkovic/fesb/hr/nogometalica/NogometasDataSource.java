package ibrkovic.fesb.hr.nogometalica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by adagelic on 09/01/17.
 */
public class NogometasDataSource {

    private SQLiteDatabase database;
    private	MySQLiteHelper	dbHelper;

    public NogometasDataSource(Context context)	{
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

    public void addNogometasToDb(String name, String url) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("url", url);
        database.insert("nogometas", null, values);
    }

    public Nogometas getNogometasById(int id) {
        Nogometas footballerToReturn = new Nogometas();

        Cursor cursor = database.rawQuery("SELECT * FROM nogometas WHERE id = '" + String.valueOf(id) + "'", null);

        cursor.moveToFirst();

        if (! cursor.isAfterLast()) {
            footballerToReturn.setId(cursor.getInt(0));
            footballerToReturn.setName(cursor.getString(1));
            footballerToReturn.setUrl(cursor.getString(2));
        }

        return footballerToReturn;
    }

    public int getDatabaseCount() {
        String count = "SELECT count(*) FROM nogometas";
        Cursor mcursor = database.rawQuery(count, null);
        mcursor.moveToFirst();

        int icount = mcursor.getInt(0);

        return icount;
    }

    public ArrayList<Nogometas> getAllNogometasi()	{
        ArrayList<Nogometas> nogometasi = new	ArrayList<Nogometas>();

        Cursor cursor =	database.rawQuery("SELECT *	FROM nogometas", null);
        cursor.moveToFirst();

        while(!	cursor.isAfterLast()) {
            Nogometas footballer =	new Nogometas();
            footballer.setId(cursor.getInt(0));
            footballer.setName(cursor.getString(1));
            footballer.setUrl(cursor.getString(2));
            nogometasi.add(footballer);
            cursor.moveToNext();
        }

        cursor.close();

        Log.d("Broj nogometasa", "Ready " + String.valueOf(nogometasi.size()) + " nogometasa");
        return nogometasi;
    }

}
