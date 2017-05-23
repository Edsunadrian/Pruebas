
public abstract class Ingrediente {

    protected String nombre;

    protected int precioCompra;

    protected int cantidadDisponible;

    public Ingrediente() {

    }

    public Ingrediente(String nombre) {
        this.nombre = nombre;
    }

    public Ingrediente(String nombre, int cantidadDisponible, int precioCompra) {
        this.nombre = nombre;
        this.cantidadDisponible = cantidadDisponible;
        this.precioCompra = precioCompra;

    }

    public void registrarCompra(int cantidad, int precioCompra) {
        if (precioCompra > this.precioCompra) {
            this.precioCompra = precioCompra;
        }
        this.cantidadDisponible += cantidad;
    }

    public void registrarVenta(int cantidad) {
        this.cantidadDisponible -= cantidad;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the precioCompra
     */
    public int getPrecioCompra() {
        return precioCompra;
    }

    /**
     * @param precioCompra the precioCompra to set
     */
    public void setPrecioCompra(int precioCompra) {
        this.precioCompra = precioCompra;
    }

    /**
     * @return the cantidadDisponible
     */
    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    /**
     * @param cantidadDisponible the cantidadDisponible to set
     */
    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public boolean equals(Object obj) {
        boolean equal = false;
        if (this.nombre == ((Ingrediente) obj).nombre && this.cantidadDisponible == ((Ingrediente) obj).cantidadDisponible && this.precioCompra == ((Ingrediente) obj).precioCompra) {
            equal = true;
        }
        return equal;
    }

    public String toString() {
        String str = "";
        return str;
    }
}
