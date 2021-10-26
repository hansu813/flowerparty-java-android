package com.example.flowerparty;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper {
    private static final String dbName = "myJournal";
    private static final String table1 = "JournalTable";
    private static final int dbVersion = 1;
    public static SQLiteHelper sqLiteHelper = null;

    private OpenHelper opener;
    private SQLiteDatabase db;
    private Context context;

    public static SQLiteHelper getInstance(Context context) {
        if(sqLiteHelper == null) {
            sqLiteHelper = new SQLiteHelper(context);
        }
        return sqLiteHelper;
    }

    public SQLiteHelper(Context context) {
        this.context = context;
        this.opener = new OpenHelper(context, dbName, null, dbVersion);
        db = opener.getWritableDatabase();
    }




    private class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String create = "CREATE TABLE "+ table1 +"("+
                    "seq integer PRIMARY KEY AUTOINCREMENT,"+
                    "title text,"+
                    "contents text,"+
                    "isdone integer)";
            db.execSQL(create);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+table1);
            onCreate(db);
        }
    }

    // INSERT INTO JournalTable VALUES(NULL, 'Title', 'contents', 0)
    public void insertJournal(Journal journal) {
        String sql = "INSERT INTO "+table1+" VALUES(NULL, '"+journal.title+"','"+journal.contents+"',"+journal.getIsdone()+");";
        db.execSQL(sql);
    }

    // DELETE FROM JournalTable WHERE seq = 0;
    public void deleteJournal(int position) {
        String sql = "DELETE FROM "+table1+" WHERE seq = "+position+";";
        db.execSQL(sql);
    }

    // SELECT * FROM JournalTable;
    public ArrayList<Journal> selectAll() {
        String sql = "SELECT * FROM "+table1;

        ArrayList<Journal> list = new ArrayList<>();

        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        while (!results.isAfterLast()) {
            Journal journal = new Journal(results.getInt(0), results.getString(1), results.getString(2), results.getInt(3));
            list.add(journal);
            results.moveToFirst();
        }
        results.close();
        return list;
    }
}
