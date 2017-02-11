package com.example.alex.meucarro;

/**
 * Created by alex on 11/02/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maiko on 07/05/16.
 */
public class CarrosService {

    private SQLiteDatabase db;
    private CriaBanco banco;

    public CarrosService(Context context) {
        banco = new CriaBanco(context);
    }

    public boolean salvar(Carro carro) {
        ContentValues valores;
        long resultado = -1;

        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(CriaBanco.NOME, carro.getNome());
        valores.put(CriaBanco.MARCA, carro.getMarca());



        if (carro.getId() != null && carro.getId() != 0){
            String where = CriaBanco.ID + " = " + carro.getId();
            resultado = db.update(CriaBanco.TABELA, valores, where, null);
        } else {
            resultado = db.insert(CriaBanco.TABELA, null, valores);
        }

        db.close();
        return resultado != -1;
    }

    public boolean remover(Integer id){
        String where = CriaBanco.ID + " = " + id;
        db = banco.getReadableDatabase();
        int resultado = db.delete(CriaBanco.TABELA, where, null);
        db.close();
        return resultado != -1;
    }

    public List<Carro> buscar(){
        Cursor dados;
        List<Carro> carros = new ArrayList<>();
        String[] campos =  {CriaBanco.ID,CriaBanco.NOME, CriaBanco.MARCA};

        db = banco.getReadableDatabase();
        dados = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        if(dados!=null && dados.moveToFirst()){
            do {
                carros.add(new Carro(dados.getInt(0), dados.getString(1), dados.getString(2)));
            } while (dados.moveToNext());
        }

        db.close();
        return carros;
    }

    public Carro getCarro(int id){
        Cursor dados;
        Carro resultado = new Carro();
        String[] campos =  {CriaBanco.ID,CriaBanco.NOME, CriaBanco.MARCA};

        String where = CriaBanco.ID+" = "+id;

        db = banco.getReadableDatabase();
        dados = db.query(banco.TABELA, campos, where, null, null, null, null, null);

        if(dados!=null && dados.moveToFirst()){
            do {
                resultado = new Carro(dados.getInt(0), dados.getString(1), dados.getString(2));
            } while (dados.moveToNext());
        }

        db.close();
        return resultado;
    }
}
