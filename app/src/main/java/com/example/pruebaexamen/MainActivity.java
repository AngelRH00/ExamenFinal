package com.example.pruebaexamen;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnNoteListener {
    private boolean showCompleted;
    private MyDB dataBase;
    private RecyclerView rv;
    protected int count;
    private ArrayList<Producto> listaProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showCompleted = false;
        dataBase = new MyDB(this);
        this.rv = findViewById(R.id.rv);
        setAdapter();
        FloatingActionButton fab = findViewById(R.id.fab);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProduct();
            }
        });
    }

    public void setAdapter() { //Se asegura de imprimir toodo y gestionar que estan los datos que tocan
        Cursor cursor = dataBase.selectRecords();
        listaProductos = new ArrayList<Producto>();
        if (cursor.getCount() > 0) {
            do {
                // añade tareas a la lista de tareas del cursor.
                listaProductos.add(new Producto(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        RecyclerAdapter ra = new RecyclerAdapter(listaProductos, this, cursor, this);
        rv.setAdapter(ra);
        rv.setLayoutManager(new LinearLayoutManager(this)); // es un manager de layout, le dice si tiene que aparecer en pantalla, si no lo tienes no funciona, no olvidar.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            mostrarTodo();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void crearTarea() {
//        Intent intent = new Intent(this, InterTareaActivity.class);
//        startActivityForResult(intent, 1);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {//Result de Crear Task
            if (resultCode == RESULT_OK) {
                dataBase.createRecords(data.getStringExtra("nombre"), data.getStringExtra("cantidad"));
                Toast.makeText(this, "Producto Guardado", Toast.LENGTH_SHORT).show();//Toast per a avisar a l'usuari
                setAdapter();
            }
        }
        if (requestCode == 2) {//Result d'editar task
            if (resultCode == RESULT_OK) {
                dataBase.update(data.getIntExtra("id", 0), data.getStringExtra("nombre"), data.getStringExtra("cantidad"));
                Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show();//Toast per a avisar a l'usuari
                setAdapter();
            }
        }
    }

    public void mostrarTodo() {
        showCompleted ^= true;
        setAdapter();
    }

    public void actualizarAdapter(int pos) {
        dataBase.update(listaProductos.get(pos).get_id(), listaProductos.get(pos).getNombreProducto(), listaProductos.get(pos).getCantidad());
        setAdapter();
    }

    @Override
    public void onNoteClick(int position) {
            Intent i = new Intent(this, InterTareaActivity.class);
            i.putExtra("id", listaProductos.get(position).get_id());
            i.putExtra("nombre", listaProductos.get(position).getNombreProducto());
            i.putExtra("cantidad", listaProductos.get(position).getCantidad());
            startActivityForResult(i, 2);
    }

    public void createProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add contact");
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.popup_window, null);
        final EditText input1 = (EditText) textEntryView.findViewById(R.id.editText);
        final EditText input2 = (EditText) textEntryView.findViewById(R.id.editText2);
        builder.setView(textEntryView);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombre = input1.getText().toString();
                String cantidad = input2.getText().toString();
                if (!nombre.equals("") && !cantidad.equals("")) {
                    dataBase.createRecords(nombre, cantidad);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

    }
    public void borrarProducto(int pos){
        dataBase.deleteItem(pos);
        setAdapter();
        Toast.makeText(this,"ITEM DELETED",Toast.LENGTH_SHORT).show();
    }
}
