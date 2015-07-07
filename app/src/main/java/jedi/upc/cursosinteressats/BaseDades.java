package jedi.upc.cursosinteressats;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by albert on 23/03/15.
 */
public class BaseDades extends SQLiteOpenHelper {

    private Context context;
    private static int    BD_VERSION   = 1;
    private static String BD_NAME      = "bdInteressats";
    private static String USER_TABLE   = "users";
    private static String CREATE_QUERY =
            "CREATE TABLE " + USER_TABLE + " (" +
            "mail varchar PRIMARY KEY," +
            "name varchar NOT NULL," +
            "surnames varchar," +
            "course_id varchar NOT NULL," +
            "date timestamp NOT NULL" + ")";

    public BaseDades(Context con) {
        super(con, BD_NAME, null, BD_VERSION); //crea BD si no existe
        context = con;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_QUERY);
    }


    public void addUser(Usuari user) {
        String query = "INSERT INTO " + USER_TABLE + " VALUES("
                + "'" + user.email + "','" + user.name + "','" + user.surnames + "',CURRENT_TIMESTAMP)";

        this.getWritableDatabase().execSQL(query);
    }

    public Cursor getUsers() {
        String query = "SELECT mail _id, name FROM " + USER_TABLE;
        return this.getReadableDatabase().rawQuery(query, null);
    }

}
