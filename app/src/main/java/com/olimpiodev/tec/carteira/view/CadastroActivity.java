package com.olimpiodev.tec.carteira.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.olimpiodev.tec.carteira.R;
import com.olimpiodev.tec.carteira.util.Constantes;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonCadastrar = findViewById(R.id.buttonCadastrar);
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCadastrar();
            }
        });
    }

    private void handleCadastrar() {
        EditText editTextNome = findViewById(R.id.editTextNome);
        EditText editTextSobrenome = findViewById(R.id.editTextSobrenome);
        EditText editTextSenha = findViewById(R.id.editTextSenha);
        EditText editTextConfirmaSenha = findViewById(R.id.editTextConfirmaSenha);
        EditText editTextTelefone = findViewById(R.id.editTextTelefone);

        if (editTextSenha.getText().toString().equals(editTextConfirmaSenha.getText().toString())) {
            SharedPreferences prefs = getSharedPreferences(Constantes.PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(Constantes.USUARIO_NOME, editTextNome.getText().toString());
            editor.putString(Constantes.USUARIO_SOBRENOME, editTextSobrenome.getText().toString());
            editor.putString(Constantes.USUARIO_SENHA, editTextSenha.getText().toString());
            editor.putString(Constantes.USUARIO_TELEFONE, editTextTelefone.getText().toString());

            editor.apply();

            Toast.makeText(this, "Usuário cadastrado com sucesso", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Senhas não conferem", Toast.LENGTH_LONG).show();
        }
    }
}