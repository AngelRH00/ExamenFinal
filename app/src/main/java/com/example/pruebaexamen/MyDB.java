package com.example.pruebaexamen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

public class MyDB {

    private DatabaseHelper dbHelper;

    private SQLiteDatabase database;

    public final static String TABLENAME = "prueba"; // name of table

    public final static String _id = "_id"; //
    public final static String PRODUCTNAME = "productName";  //
    public final static String QUANTITY = "cantidad";

    /**
     * @param context
     */
    public MyDB(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_id, 0);
        values.put(PRODUCTNAME, "Prueba a añadir un producto");
        values.put(QUANTITY, 0);
        database.insert(TABLENAME,null,values);
    }
    public int getCount() {
        int count = (int) DatabaseUtils.queryNumEntries(database, TABLENAME);
        return count;
    }
//
//    private String manejarDiaMes(String entrada, Boolean esDia) {
//        String salida = "?¿?";
//        if (esDia){
//            switch(entrada){
//                case "Mon":
//                    salida="Lunes";
//                    break;
//
//                case "Tue":
//                    salida="Martes";
//                    break;
//
//                case "Wed":
//                    salida="Miércoles";
//                    break;
//
//                case "Thu":
//                    salida="Jueves";
//                    break;
//
//                case "Fri":
//                    salida="Viernes";
//                    break;
//
//                case "Sat":
//                    salida="Sábado";
//                    break;
//
//                case "Sun":
//                    salida="Domingo";
//                    break;
//
//                default:
//                    salida = "???";
//                    break;
//            }
//        }else{
//            switch(entrada){
//                case"Jan":
//                    salida= "Enero";
//                    break;
//
//                case"Feb":
//                    salida= "Febrero";
//                    break;
//
//                case"Mar":
//                    salida= "Marzo";
//                    break;
//
//                case"Apr":
//                    salida= "Abril";
//                    break;
//
//                case"May":
//                    salida= "Mayo";
//                    break;
//
//                case"Jun":
//                    salida= "Junio";
//                    break;
//
//                case"Aug":
//                    salida= "Agosto";
//                    break;
//
//                case"Sep":
//                    salida= "Septiembre";
//                    break;
//
//                case"Oct":
//                    salida= "Octubre";
//                    break;
//
//                case"Nov":
//                    salida= "Noviembre";
//                    break;
//
//                case"Dec":
//                    salida= "Diciembre";
//                    break;
//
//                default:
//                    salida = "¿¿¿";
//            }
//
//        }
//        return salida;
//    }

    public long createRecords(String name, String quantity) {
        int id = nextId();
        ContentValues values = new ContentValues();
        values.put(_id, id);
        values.put(PRODUCTNAME, name);
        values.put(QUANTITY, quantity);
        return database.insert(TABLENAME, null, values);
    }

    public void deleteItem(int s) {
        database.delete(TABLENAME, "_id = ?", new String[]{String.valueOf(s)});
    }

    public Cursor selectRecords() {
        database = dbHelper.getWritableDatabase();
        String[] cols = new String[]{_id, PRODUCTNAME, QUANTITY};
        Cursor mCursor;

            mCursor = database.query(TABLENAME,cols,null,null,null,null,"_id desc",null);

        return mCursor; // iterate to get each value.
    }

    private int nextId() {
        String[] cols = new String[]{_id};
        Cursor mCursor = database.query(true, TABLENAME, cols, null
                , null, null, null, "_id desc", null);
        mCursor.moveToFirst();
        int id = mCursor.getInt(0)+1;
        return id;
    }



    public long update(int _id,String name, String cantidad){
        ContentValues values = new ContentValues();
        values.put(PRODUCTNAME, name);
        values.put(QUANTITY, cantidad);
        return database.update(TABLENAME,values,"_id = ?", new String[]{String.valueOf(_id)});
    }
}