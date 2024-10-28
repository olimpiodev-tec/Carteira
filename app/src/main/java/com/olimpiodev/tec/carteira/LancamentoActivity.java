package com.olimpiodev.tec.carteira;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.olimpiodev.tec.carteira.dao.LancamentoDAO;
import com.olimpiodev.tec.carteira.enumeracao.LancamentoCategoria;
import com.olimpiodev.tec.carteira.model.Lancamento;
import com.olimpiodev.tect.carteira.R;

import java.util.Locale;
import java.util.Objects;

public class LancamentoActivity extends AppCompatActivity {

    private Lancamento lancamento = null;
    private LancamentoCategoria lancamentoCategoria = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lancamento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        confgureToolbarLancamento();
        handleRadioButton();
        salvarLancamento();
    }

    private void confgureToolbarLancamento() {
        Toolbar toolbarLancamento = findViewById(R.id.toolbarLancamento);
        setSupportActionBar(toolbarLancamento);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void handleRadioButton() {
        RadioButton radioButtonEntrada = findViewById(R.id.radioButtonEntrada);
        radioButtonEntrada.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                lancamentoCategoria = LancamentoCategoria.ENTRADA;
            }
        });

        RadioButton radioButtonSaida = findViewById(R.id.radioButtonSaida);
        radioButtonSaida.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                lancamentoCategoria = LancamentoCategoria.SAIDA;
            }
        });
    }

    private void salvarLancamento() {
        Button buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextDescricao = findViewById(R.id.editTextDescricao);
                String descricao = editTextDescricao.getText().toString().toUpperCase(Locale.ROOT);

                EditText editTextValor = findViewById(R.id.editTextValor);
                double valor = Double.parseDouble(editTextValor.getText().toString());

                lancamento = new Lancamento(0, descricao, lancamentoCategoria, valor);
                LancamentoDAO lancamentoDAO = new LancamentoDAO(LancamentoActivity.this);
                lancamentoDAO.inserirLancamento(lancamento);

                if (lancamento.isLancamentoDespesa()) {
                    Toast.makeText(LancamentoActivity.this, "Sucesso 😒", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LancamentoActivity.this, "Sucesso 😊", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });
    }
}