package com.example.pruebaexamen;

class Producto {
    private int _id;
    private String nombreProducto, cantidad;

    public Producto(int _id, String nombreProducto, String cantidad) {
        this._id = _id;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreTarea(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
