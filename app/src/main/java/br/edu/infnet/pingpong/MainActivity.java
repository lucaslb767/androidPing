package br.edu.infnet.pingpong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;              // FIQUE À VONTADE PARA MEXER NO CÓDIGO POIS TEM O ARQUIVO ZIP DE BACK-UP
import okhttp3.OkHttpClient;         // MANIFEST E GRADLE JÁ ESTÃO CONFIGURADOS PARA ACESSAR A INTERNET E IMPLEMENTAR ESSE API 'OKHTTP3'
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextoResultado;
    private EditText mLinkUrl;
    private Button mCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextoResultado = findViewById(R.id.texto_resultado);
        mLinkUrl = findViewById(R.id.texto_linkurl);
        mCheck = findViewById(R.id.botao_Checar);

        mCheck.setOnClickListener(checar_servidor);

    }
        public View.OnClickListener checar_servidor = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient();

                String url = (mLinkUrl.getText().toString()); /* "https://google.com" */

                Request request = new Request.Builder() // AQUI ELE IRÁ ENVIAR O REQUEST PRO SERVIDOR DO SITE RESPONDER
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() { // AQUI ELE REQUESITA O RETORNO DO REQUEST POR PARTE DO SERVIDOR DO SITE
                    @Override
                    public void onFailure(Call call, IOException e) { // AQUI É O CASO NAO TENHA RESPOSTA OU LINK INVALIDO
                        mTextoResultado.setText(R.string.text_website_invalido); // AQUI APARECE WEBSITE INVALIDO CASO DÊ INSUCESSO OU ESCREVER QUALQUER COISA
                        mLinkUrl.setText(R.string.text_https); // AQUI ELE ''LIMPA'' O CAMPO DO LINK E DIGITA ''HTTPS://'' NOVAMENTE LA, TODAVIA O CURSOR DE DIGITACAO VOLTA PARA O INICIO DO CAMPO ANTES DO HTTPS E COMPLICARÁ A VIDA DO USUARIO
                        e.printStackTrace(); // ESSA LINHA É PARTE DO CODIGO PRONTO NAO SEI PRA QUE SERVE MAS REFERENCIA ALGUM ERRO QUE O SISTEMA RETORNA PELO QUE PESQUISEI
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException { // AQUI É O CASO DE SUCESSO E O SERVIDOR RESPONDA
                        if (response.isSuccessful()) {
                            //final String myResponse = response.body().string(); COMENTEI ESSA LINHA ( E A LINHA 64) PORQUE ERA PARTE DO CÓDIGO PRONTO DO ''OKHTTP3''

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //mTextoResultado.setText(myResponse); // AQUI APARECE NA TELA AQUELE MONTE DE TEXTO DO HTML DO SITE
                                    mTextoResultado.setText(R.string.text_website_online); // IRÁ IMPRIMIR ''PING-PONG'' QUANDO TIVER RESPOSTA, CONFIRMANDO ASSIM QUE ELE ESTÁ ON-LINE
                                }
                            });
                        }
                    }
                });
            }
        };


    }