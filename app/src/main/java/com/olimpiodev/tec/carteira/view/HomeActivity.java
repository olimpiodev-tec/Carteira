package com.olimpiodev.tec.carteira.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.olimpiodev.tec.carteira.R;
import com.olimpiodev.tec.carteira.dao.LancamentoDAO;
import com.olimpiodev.tec.carteira.model.Lancamento;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadLancamentos();
        handleClickMoney();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadLancamentos();
    }

    private void handleClickMoney() {
        ImageButton imageButtonMoney = findViewById(R.id.imageButtonMoney);
        imageButtonMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent screenMoney = new Intent(HomeActivity.this, MoneyBagActivity.class);
                startActivity(screenMoney);
            }
        });
    }

    private void loadLancamentos() {
        LancamentoDAO lancamentoDAO = new LancamentoDAO(this);
        List<Lancamento> lancamentos = lancamentoDAO.listarLancamentos();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        for (Lancamento lancamento: lancamentos) {
            adapter.add(lancamento.showLancamentoInListView());
        }

        ListView listViewLancamentos = findViewById(R.id.listViewLancamentos);
        listViewLancamentos.setAdapter(adapter);
    }
}