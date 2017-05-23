
public class Fruta extends Ingrediente {



    public Fruta(String nombre, int cantidadDisponible, int precioCompra){
        super(nombre,cantidadDisponible,precioCompra);
    }
    public boolean equals(Object obj) {
        boolean equal = false;
        if(obj.getClass()==this.getClass()){
            equal=true;
        }
        return equal;
    }


}
