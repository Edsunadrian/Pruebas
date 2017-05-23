
import java.util.ArrayList;

public class Restaurante {

    private ArrayList<Ingrediente> ingredientes;

    private int egresosPorCompras;

    private int ingresosPorVentas;

    public Restaurante() {
        ingredientes = new ArrayList<Ingrediente>();
    }

    public Ingrediente buscarIngrediente(String nombre) {
        Ingrediente ingrediente = null;
        for (int i = 0; i < ingredientes.size(); i++) {
            if (ingredientes.get(i).getNombre() == nombre) {
                ingrediente = ingredientes.get(i);
            }

        }
        return ingrediente;
    }

    public String[] getNombresFrutas() {
        int contador = 0;
        String[] s;
        for (int i = 0; i < ingredientes.size(); i++) {
            if (ingredientes.get(i) instanceof Fruta) {
                contador++;
            }
        }
        s = new String[contador];
        contador = 0;
        for (int i = 0; i < ingredientes.size(); i++) {
            if (ingredientes.get(i) instanceof Fruta) {
                s[contador] = ingredientes.get(i).getNombre();
                contador++;
            }
        }
        return s;
    }

    public String[] getNombresProteinas() {
        int contador = 0;
        String[] s;
        for (int i = 0; i < ingredientes.size(); i++) {
            if (ingredientes.get(i) instanceof Proteina) {
                contador++;
            }
        }
        s = new String[contador];
        contador = 0;
        for (int i = 0; i < ingredientes.size(); i++) {
            if (ingredientes.get(i) instanceof Proteina) {
                s[contador] = ingredientes.get(i).getNombre();
                contador++;
            }
        }
        return s;
    }

    public String[] getNombresGranos() {
        int contador = 0;
        String[] s;
        for (int i = 0; i < ingredientes.size(); i++) {
            if (ingredientes.get(i) instanceof Grano) {
                contador++;
            }
        }
        s = new String[contador];
        contador = 0;
        for (int i = 0; i < ingredientes.size(); i++) {
            if (ingredientes.get(i) instanceof Grano) {
                s[contador] = ingredientes.get(i).getNombre();
                contador++;
            }
        }
        return s;
    }

    public String[] getNombresAlmidones() {
        int contador = 0;
        String[] s;
        for (int i = 0; i < ingredientes.size(); i++) {
            if (ingredientes.get(i) instanceof Almidon) {
                contador++;
            }
        }
        s = new String[contador];
        contador = 0;
        for (int i = 0; i < ingredientes.size(); i++) {
            if (ingredientes.get(i) instanceof Almidon) {
                s[contador] = ingredientes.get(i).getNombre();
                contador++;
            }
        }
        return s;
    }

    public String[] getNombresIngredientes() {
        String[] s = new String[ingredientes.size()];
        for (int i = 0; i < ingredientes.size(); i++) {
            s[i] = ingredientes.get(i).getNombre();
        }
        return s;
    }

    public void registrarCompraIngrediente(String nombre, int cantidad, int precioCompra, int tipo) {

        int ingresosDisponibles = ingresosPorVentas - getIVA();

        if (ingresosDisponibles >= precioCompra) {
            egresosPorCompras += precioCompra * cantidad;
            switch (tipo) {
                case 0:
                    buscarIngrediente(nombre).registrarCompra(cantidad, precioCompra);
                    break;
                case 1:
                    buscarIngrediente(nombre).registrarCompra(cantidad, precioCompra);
                    break;
                case 2:
                    buscarIngrediente(nombre).registrarCompra(cantidad, precioCompra);
                    break;
                case 3:
                    buscarIngrediente(nombre).registrarCompra(cantidad, precioCompra);
                    break;
            }
        }
    }

    public int consultarPrecioVenta(String fruta, String proteina, String grano, String almidon) {
        int precio = 0;
        Almuerzo a = new Almuerzo(this, fruta, proteina, grano, almidon);
        precio = a.getPrecioVenta();
        return precio;
    }

    public boolean sePuedePreparar(String fruta, String proteina, String grano, String almidon) {
        Almuerzo a = new Almuerzo(this, fruta, proteina, grano, almidon);
        return a.sePuedePreparar();
    }

    public void registrarVenta(String fruta, String proteina, String grano, String almidon) {
        Almuerzo a = new Almuerzo(this, fruta, proteina, grano, almidon);
        if (a.sePuedePreparar()) {
            this.ingresosPorVentas += a.getPrecioVenta();
            a.registrarVenta();
        } else {
            throw new RuntimeException("No hay suficientes ingredientes...");
        }
    }

    public int getIVA() {
        return this.ingresosPorVentas * 19 / 100;
    }

    public int getGananciasNetas() {
        return this.ingresosPorVentas - this.egresosPorCompras - this.getIVA();
    }

    /**
     * @return the egresosPorCompras
     */
    public int getEgresosPorCompras() {
        return egresosPorCompras;
    }

    /**
     * @param egresosPorCompras the egresosPorCompras to set
     */
    public void setEgresosPorCompras(int egresosPorCompras) {
        this.egresosPorCompras = egresosPorCompras;
    }

    /**
     * @return the ingresosPorVentas
     */
    public int getIngresosPorVentas() {
        return ingresosPorVentas;
    }

    /**
     * @param ingresosPorVentas the ingresosPorVentas to set
     */
    public void setIngresosPorVentas(int ingresosPorVentas) {
        this.ingresosPorVentas = ingresosPorVentas;
    }

    /**
     * @return the ingredientes
     */
    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    /**
     * @param ingredientes the ingredientes to set
     */
    public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
