package com.example.pruebaexamen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class InterTareaActivity extends AppCompatActivity {

    private String nombre, cantidad, tipo;

    private EditText nombreProducto;
    private EditText editCantidad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter_tarea);
        editCantidad = findViewById(R.id.editCantidad);
        nombreProducto = findViewById(R.id.editNombreProducto);
        if (getIntent().getExtras() != null){
            this.nombre = getIntent().getStringExtra("nombre");
            this.cantidad = getIntent().getStringExtra("cantidad");
            nombreProducto.setText(nombre);
            this.tipo = "editar";
        }else{
            this.tipo = "crear";
        }
    }
    public void cerrar(MenuItem mi){
        onBackPressed();
    }
    public void guardar(MenuItem mi){
        nombre = nombreProducto.getText().toString();
        if (nombre.equals("")){
            Toast.makeText(this, "Faltan datos.", Toast.LENGTH_SHORT).show();
        }else{
            Intent resultIntent = new Intent();
            resultIntent.putExtra("nombre",nombre);
            if (tipo.equals("editar")){

                resultIntent.putExtra("id", getIntent().getIntExtra("id",0));   //esto es para poder sacar la id otra vez afuera
            }

            setResult(RESULT_OK,resultIntent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tarea,menu);
        return true;
    }
}
