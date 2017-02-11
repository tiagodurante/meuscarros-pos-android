package com.example.alex.meucarro;

/**
 * Created by alex on 11/02/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CriaBanco extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "banco.db";
    public static final String TABELA = "carros";
    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String MARCA = "marca";

    private static final int VERSAO = 1;


    public CriaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "("
                + ID + " integer primary key autoincrement, "
                + NOME + " text, "
                + MARCA + " text "
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABELA);
        onCreate(db);
    }
}
