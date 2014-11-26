package com.example.usuario.editor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Principal extends Activity {

    String enlace;
    EditText etTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        etTexto = (EditText)findViewById(R.id.etTexto);
        String fuente = "fonts/manuscrita.ttf";
        // Cargamos la fuente:
        Typeface tf = Typeface.createFromAsset(getAssets(), fuente);
        // Aplicamos la fuente:
        etTexto.setTypeface(tf);
        leer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void leer(){
        Intent i = getIntent();
        Uri data = i.getData();
        enlace = data.getPath();

        if(i.getType().equals("text/plain")){
            File archivo=new File(enlace);
            try {
                BufferedReader in = new BufferedReader(new FileReader(archivo));
                String linea;
                StringBuilder texto = new StringBuilder("");
                while ((linea = in.readLine()) != null) {
                    texto.append(linea+'\n');
                }
                in.close();
                etTexto.setText(texto.toString());
            }catch(IOException e){
            }
        }
    }

    public void guardar(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.guardar);
        alert.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            String texto = etTexto.getText().toString();
                            File archivo =new File(enlace);
                            OutputStreamWriter escribir = new OutputStreamWriter(new FileOutputStream(archivo));
                            escribir.write(texto);
                            escribir.flush();
                            escribir.close();
                            finish();
                            tostada(getString(R.string.guardado));
                        } catch(IOException e){}
                    }
                });
        alert.setNegativeButton(android.R.string.no, null);
        alert.show();
    }

    private void tostada(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
