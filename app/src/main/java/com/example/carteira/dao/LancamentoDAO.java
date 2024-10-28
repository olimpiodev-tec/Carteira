package com.example.carteira.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.carteira.enumeracao.LancamentoCategoria;
import com.example.carteira.model.Lancamento;
import com.example.carteira.sqlite.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class LancamentoDAO {

    private final SQLiteDatabase db;

    public LancamentoDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void inserirLancamento(Lancamento lancamento) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_DESCRICAO, lancamento.getDecricao());
        values.put(DbHelper.COLUMN_LANCAMENTO_CATEGORIA, lancamento.getLancamentoCategoria().getDescricao());
        values.put(DbHelper.COLUMN_VALOR, lancamento.getValor());
        db.insert(DbHelper.TABLE_LANCAMENTOS_NAME, null, values);
    }
    
    public List<Lancamento> listarLancamentos() {
        List<Lancamento> lancamentos = new ArrayList<>();
        Cursor cursor = db.query(DbHelper.TABLE_LANCAMENTOS_NAME,
                new String[]{DbHelper.COLUMN_ID, DbHelper.COLUMN_DESCRICAO, DbHelper.COLUMN_VALOR, DbHelper.COLUMN_LANCAMENTO_CATEGORIA},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String descricao = cursor.getString(1);
                double valor = cursor.getDouble(2);
                String lancamentoCategoriaString = cursor.getString(3);

                if (lancamentoCategoriaString.equals(LancamentoCategoria.ENTRADA)) {
                    lancamentos.add(new Lancamento(id, descricao, LancamentoCategoria.ENTRADA , valor));
                } else {
                    lancamentos.add(new Lancamento(id, descricao, LancamentoCategoria.SAIDA , valor));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lancamentos;
    }
}
