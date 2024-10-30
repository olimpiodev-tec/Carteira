package com.olimpiodev.tec.carteira.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.olimpiodev.tec.carteira.R;
import com.olimpiodev.tec.carteira.dao.CategoriaDAO;
import com.olimpiodev.tec.carteira.dao.LancamentoDAO;
import com.olimpiodev.tec.carteira.enumeracao.TipoLancamento;
import com.olimpiodev.tec.carteira.model.Categoria;
import com.olimpiodev.tec.carteira.model.Lancamento;

import java.util.List;

public class MoneyBagActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerCategoria;
    private ArrayAdapter<Categoria> categoriaArrayAdapter;
    private CategoriaDAO categoriaDAO;
    private String tipoLancamento;
    private String categoriaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_money_bag);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        categoriaDAO = new CategoriaDAO(this);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);

        ImageButton imageButtonLancamentos = findViewById(R.id.imageButtonLancamentos);
        imageButtonLancamentos.setOnClickListener(this);

        loadCategorias();
        handleAddCategoria();
        handleRadioButton();
        handleCadastrar();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imageButtonLancamentos) {
            finish();
        }
    }

    private void loadCategorias() {

        categoriaDAO = new CategoriaDAO(this);
        List<Categoria> categorias = categoriaDAO.listarCategorias();

        categoriaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        categoriaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(categoriaArrayAdapter);

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoriaSelecionada = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void handleAddCategoria() {
        ImageButton imageButtonAddCategoria = findViewById(R.id.imageButtonAddCategoria);
        imageButtonAddCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MoneyBagActivity.this);
                builder.setTitle("Cadastrar Categoria");

                LinearLayout linearLayout = new LinearLayout(MoneyBagActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setPadding(32, 32, 32, 32);

                LinearLayout.LayoutParams paramsEditText = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                int marginLeftInPixels = (int) getResources().getDisplayMetrics().density * 5;
                paramsEditText.setMargins(marginLeftInPixels, 16, 0, 16);

                EditText editTextCategoria = new EditText(MoneyBagActivity.this);
                editTextCategoria.setLayoutParams(paramsEditText);
                editTextCategoria.setHint("Nome da Categoria");
                editTextCategoria.setBackgroundResource(R.drawable.input_rounded);
                int paddingInPixels = (int) (10 * getResources().getDisplayMetrics().density);
                editTextCategoria.setPadding(paddingInPixels, 20, paddingInPixels, 20);

                linearLayout.addView(editTextCategoria);
                builder.setView(linearLayout);

                builder.setPositiveButton(R.string.cadastrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        categoriaDAO.inserirCategoria(new Categoria(0, editTextCategoria.getText().toString()));
                        Toast.makeText(MoneyBagActivity.this, "Categoria cadastrada", Toast.LENGTH_LONG).show();
                        reloadSpinnerCategorias();
                    }
                });

                builder.setNegativeButton("Cancelar", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    
    private void reloadSpinnerCategorias() {
        categoriaArrayAdapter.clear();
        List<Categoria> categorias = categoriaDAO.listarCategorias();
        for (Categoria categoria: categorias) {
            categoriaArrayAdapter.add(categoria);
        }
        categoriaArrayAdapter.notifyDataSetChanged();
    }

    private void handleRadioButton() {
        RadioButton radioButtonEntrada = findViewById(R.id.radioButtonEntrada);
        radioButtonEntrada.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tipoLancamento = TipoLancamento.ENTRADA.toString();
            }
        });

        RadioButton radioButtonSaida = findViewById(R.id.radioButtonSaida);
        radioButtonSaida.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tipoLancamento = TipoLancamento.SAIDA.toString();
            }
        });
    }

    private void handleCadastrar() {
        EditText editTextDescricao = findViewById(R.id.editTextDescricao);
        EditText editTextValor = findViewById(R.id.editTextValor);

        Button buttonCadastrar = findViewById(R.id.buttonCadastrar);
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descricao = editTextDescricao.getText().toString();
                double valor = Double.parseDouble(editTextValor.getText().toString());

                Lancamento lancamento = new Lancamento(0, categoriaSelecionada, descricao, tipoLancamento, valor);
                LancamentoDAO lancamentoDAO = new LancamentoDAO(MoneyBagActivity.this);
                lancamentoDAO.inserirLancamento(lancamento);
                Toast.makeText(MoneyBagActivity.this, "Lançamento cadatrado", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

}