package com.example.alex.meucarro;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CarrosService carrosService;

    private EditText edId;
    private EditText edNome;
    private EditText edMarca;
    private TextView edLista;
    private Button btnSalvar;
    private Button btnDeletar;
    private Button btnListar;
    private Button btnBuscar;

    private List<Carro> carros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carrosService = new CarrosService(getBaseContext());

        edId = (EditText) findViewById(R.id.editTextId);
        edNome = (EditText) findViewById(R.id.editTextNome);
        edMarca = (EditText) findViewById(R.id.editTextMarca);
        edLista = (TextView) findViewById(R.id.textViewListaDeCarros);

        btnBuscar = (Button) findViewById(R.id.buttonBuscar);
        btnBuscar.setOnClickListener(this);
        btnDeletar = (Button) findViewById(R.id.buttonDelete);
        btnDeletar.setOnClickListener(this);
        btnListar = (Button) findViewById(R.id.buttonListar);
        btnListar.setOnClickListener(this);
        btnSalvar = (Button) findViewById(R.id.buttonSalvar);
        btnSalvar.setOnClickListener(this);

        listarCarrosNoTextView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSalvar: {
                salvarCarro();
                break;
            }
            case R.id.buttonBuscar: {
                    closeKeyboard((btnBuscar));
                    buscarCarro();
                    break;
                }
            case R.id.buttonListar: {
                listarCarrosNoTextView();
                break;
            }
            case R.id.buttonDelete: {
                deletarCarro(Integer.parseInt(edId.getText().toString()));
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            imprimeMensagem("dig din");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void salvarCarro() {
        Carro carro = new Carro();
        String id = edId.getText().toString();

        carro.setNome(edNome.getText().toString());
        carro.setMarca(edMarca.getText().toString());

        if (id.equals("")) {
            carro.setId(null);
        } else {
            carro.setId(Integer.parseInt(id));
        }

        if (carrosService.salvar(carro)) {
            imprimeMensagem("Registro salvo com sucesso !");
            limparCampos();
//            buscarDados();
        } else {
            imprimeMensagem("Não foi possível salvar o registro !");
        }
    }

    private void imprimeMensagem(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void limparCampos() {
        edId.setText("");
        edNome.setText("");
        edMarca.setText("");
    }

    private void buscarCarro(){
        String id = edId.getText().toString();
        Carro aux = carrosService.getCarro(Integer.parseInt(id));
        if (aux.getId() == null) {
            imprimeMensagem("Não existe um carro com o ID #"+id);
        } else {
            edMarca.setText(aux.getMarca());
            edNome.setText(aux.getNome());
        }
    }

    private void listarCarrosNoTextView() {
        String listaTexto = new String();
        carros = carrosService.buscar();
        if (carros.size() == 0) {
            listaTexto = "Nenhum carro foi cadastrado até o momento.";
        } else {
            for (Carro aux:carros) {
                listaTexto += "ID: #"+aux.getId()+" | Nome: "+aux.getNome()+" | Marca: "+aux.getMarca()+"\n";
            }
        }
        edLista.setText(listaTexto);
    }

    private void closeKeyboard(Button btn) {
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(btn.getWindowToken(), 0);
    }

    private void deletarCarro(int id) {
        if (carrosService.remover(id)) {
            imprimeMensagem("Registro removido com sucesso !");
            limparCampos();
            listarCarrosNoTextView();
        } else {
            imprimeMensagem("Não foi possível remover o registro !");
        }
    }
}


