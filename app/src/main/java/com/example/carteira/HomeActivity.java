package com.example.carteira;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carteira.dao.LancamentoDAO;
import com.example.carteira.model.Lancamento;

import java.util.List;
import java.util.Objects;

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

        confgureToolbarHome();
        handleClickAddLancamento();
        loadLancamentos();
    }

    private void confgureToolbarHome() {
        Toolbar toolbarHome = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbarHome);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void handleClickAddLancamento() {
        ImageButton imageButtonLancamento = findViewById(R.id.imageButtonLancamento);
        imageButtonLancamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telaLancamento = new Intent(HomeActivity.this, LancamentoActivity.class);
                startActivity(telaLancamento);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadLancamentos();
    }

    private void loadLancamentos() {
        LancamentoDAO lancamentoDAO = new LancamentoDAO(this);
        List<Lancamento> lancamentos = lancamentoDAO.listarLancamentos();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        for (Lancamento lancamento: lancamentos) {
            adapter.add(
                    lancamento.getDecricao()
                            .concat(" - ").concat("R$ ")
                            .concat(lancamento.getValor().toString())
                            .concat(" - ")
                            .concat(lancamento.getLancamentoCategoria().getDescricao()));
        }

        ListView listViewLancamentos = findViewById(R.id.listViewLancamentos);
        listViewLancamentos.setAdapter(adapter);
    }
}