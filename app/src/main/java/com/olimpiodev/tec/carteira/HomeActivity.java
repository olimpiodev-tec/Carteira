package com.olimpiodev.tec.carteira;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.olimpiodev.tec.carteira.dao.LancamentoDAO;
import com.olimpiodev.tec.carteira.model.Lancamento;
import com.olimpiodev.tect.carteira.R;

import java.util.ArrayList;
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
        handleClickFiltro();
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
                            .concat(lancamento.getLancamentoCategoria().getDescricao())
                            .concat(" - ").concat(lancamento.getData()));
        }

        ListView listViewLancamentos = findViewById(R.id.listViewLancamentos);
        listViewLancamentos.setAdapter(adapter);
    }

    private void handleClickFiltro() {
        ImageButton imageButtonFiltro = findViewById(R.id.imageButtonFiltro);
        imageButtonFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Selecione Ano / Mês");

                LinearLayout linearLayout = new LinearLayout(HomeActivity.this);
                ArrayList<String> meses = new ArrayList<String>();
                meses.add("Janeiro");
                meses.add("Fevereiro");
                meses.add("Março");
                meses.add("Abril");
                meses.add("Maio");
                meses.add("Junho");
                meses.add("Julho");
                meses.add("Agosto");
                meses.add("Setembro");
                meses.add("Outubro");
                meses.add("Novembro");
                meses.add("Dezembro");

                Spinner spinnerMeses = new Spinner(HomeActivity.this);
                ArrayAdapter<String> mesesAdapter = new ArrayAdapter<String>(
                    HomeActivity.this,
                    android.R.layout.simple_spinner_dropdown_item,
                    meses
                );
                spinnerMeses.setAdapter(mesesAdapter);

                linearLayout.addView(spinnerMeses);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}