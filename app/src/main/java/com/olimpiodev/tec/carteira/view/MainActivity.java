package com.olimpiodev.tec.carteira.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.olimpiodev.tec.carteira.R;
import com.olimpiodev.tec.carteira.util.Constantes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonEntrar = findViewById(R.id.buttonEntrar);
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateLogin();
            }
        });

        TextView textViewCadastro = findViewById(R.id.textViewCadastro);
        textViewCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telaCadastro = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(telaCadastro);
            }
        });
    }

    private void validateLogin() {
        EditText editTextUsuario = findViewById(R.id.editTextUsuario);
        EditText editTextSenha = findViewById(R.id.editTextSenha);

        SharedPreferences prefs = getSharedPreferences(Constantes.PREFS_NAME, MODE_PRIVATE);
        String usuarioSP = prefs.getString(Constantes.USUARIO_NOME, null);
        String senhaSP = prefs.getString(Constantes.USUARIO_SENHA, null);

        if (usuarioSP != null && senhaSP != null) {
            if (editTextUsuario.getText().toString().equals(usuarioSP)) {
                if (editTextSenha.getText().toString().equals(senhaSP)) {
                    Intent telaHome = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(telaHome);
                    finish();
                } else {
                    Toast.makeText(this, "Senha inválida", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Usuário inválido", Toast.LENGTH_LONG).show();
            }
        }

    }
}