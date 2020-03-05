package com.example.pruebaexamen;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private OnNoteListener myOnNoteListener;
    private ArrayList<Producto> listaProductos;
    private Context context;
    private Cursor cursor;

    public RecyclerAdapter(ArrayList<Producto> listaProductos, Context context, Cursor cursor, OnNoteListener onl) {
        this.listaProductos = listaProductos;
        this.context = context;
        this.cursor = cursor;
        this.myOnNoteListener = onl;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater ly = LayoutInflater.from(context);
        View v = ly.inflate(R.layout.row, parent, false); // hace una view que se vera por pantalla en la mainactivity del contexto, usando la row.xml como layout
        return new ViewHolder(v, myOnNoteListener); //devuele una única fila.
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) { //Prepara lo que va a mostrarse en pantalla los objetos.
        holder.bind(listaProductos.get(position)); //rellena cada fila con este hueco de la arraylist
    }

    @Override
    public int getItemCount() { //devuelve la cuenta de cuantos huecos hay en el cursor

        return this.cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnNoteListener onNoteListener;
        private TextView nombre;
        private ImageButton delete, edit;

        public ViewHolder(@NonNull View itemView, OnNoteListener onl) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.nombreProducto);
            this.edit = itemView.findViewById(R.id.guardarBoton);
            this.delete = itemView.findViewById(R.id.cancelarBoton);
            this.onNoteListener = onl;
            itemView.setOnClickListener(this);

        }

        public void bind(Producto t) { // Asocia cada linea con el contenido de los objetos de la arraylist
            this.nombre.setText(t.getNombreProducto());
            ((MainActivity) context).actualizarAdapter(getAdapterPosition());
            this.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).borrarProducto(getAdapterPosition());
                    }
                }
            });
        }





        @Override
        public void onClick(View v) {

        }



    }
    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}


