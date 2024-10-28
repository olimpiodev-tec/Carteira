package com.example.carteira.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CarteiraDB";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_LANCAMENTOS_NAME = "lancamentos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LANCAMENTO_CATEGORIA = "lancamento_categoria";
    public static final String COLUMN_DESCRICAO = "decricao";
    public static final String COLUMN_VALOR = "valor";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_LANCAMENTOS_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_VALOR + " REAL, " +
                    COLUMN_LANCAMENTO_CATEGORIA + " TEXT, " +
                    COLUMN_DESCRICAO + " TEXT)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANCAMENTOS_NAME);
        onCreate(db);
    }
}

