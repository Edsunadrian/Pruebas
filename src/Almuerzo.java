
import java.util.ArrayList;

public class Almuerzo {

    private static final int PORCION_FRUTA = 30;
    private static final int PORCION_PROTEINA = 200;
    private static final int PORCION_GRANO = 100;
    private static final int PORCION_ALMIDON = 50;

    private Restaurante restaurante;

    private Fruta fruta;
    private Proteina proteina;
    private Grano grano;
    private Almidon almidon;

    public Almuerzo(Restaurante restaurante, String nombreFruta, String nombreProteina, String nombreGrano, String nombreAlmidon) {

        this.restaurante = restaurante;
        fruta = new Fruta(nombreFruta, restaurante.buscarIngrediente(nombreFruta).getCantidadDisponible(), restaurante.buscarIngrediente(nombreFruta).getPrecioCompra());
        grano = new Grano(nombreGrano, restaurante.buscarIngrediente(nombreGrano).getCantidadDisponible(), restaurante.buscarIngrediente(nombreGrano).getPrecioCompra());
        proteina = new Proteina(nombreProteina, restaurante.buscarIngrediente(nombreProteina).getCantidadDisponible(), restaurante.buscarIngrediente(nombreProteina).getPrecioCompra());
        almidon = new Almidon(nombreAlmidon, restaurante.buscarIngrediente(nombreAlmidon).getCantidadDisponible(), restaurante.buscarIngrediente(nombreAlmidon).getPrecioCompra());
    }

    public int getPrecioVenta() {
        int precio;
        precio = fruta.getPrecioCompra() * PORCION_FRUTA + proteina.getPrecioCompra() * PORCION_PROTEINA + grano.getPrecioCompra() * PORCION_GRANO + almidon.getPrecioCompra() * PORCION_ALMIDON;
        precio += (precio * 35) / 100;
        return precio;
    }

    public boolean sePuedePreparar() {
        boolean sePuede = false;
        sePuede = (fruta.getCantidadDisponible() >= PORCION_FRUTA)
                && (proteina.getCantidadDisponible() >= PORCION_PROTEINA)
                && (grano.getCantidadDisponible() >= PORCION_GRANO)
                && (almidon.getCantidadDisponible() >= PORCION_ALMIDON);
        return sePuede;
    }

    public void registrarVenta() {
        ArrayList<Ingrediente> currentIngredientes = new ArrayList<Ingrediente>();

        //Hay sangre en ese código :v
        for (int i = 0; i < restaurante.getIngredientes().size(); i++) {
            if (restaurante.getIngredientes().get(i).getNombre() == fruta.getNombre() || restaurante.getIngredientes().get(i).getNombre() == grano.getNombre() || restaurante.getIngredientes().get(i).getNombre() == proteina.getNombre() || restaurante.getIngredientes().get(i).getNombre() == almidon.getNombre()) {
                if (restaurante.getIngredientes().get(i) instanceof Fruta) {
                    Ingrediente fruta = new Fruta(restaurante.getIngredientes().get(i).getNombre(), restaurante.getIngredientes().get(i).cantidadDisponible, restaurante.getIngredientes().get(i).precioCompra);
                    fruta.registrarVenta(PORCION_FRUTA);
                    currentIngredientes.add(fruta);
                } else if (restaurante.getIngredientes().get(i) instanceof Proteina) {
                    Ingrediente proteina = new Proteina(restaurante.getIngredientes().get(i).getNombre(), restaurante.getIngredientes().get(i).cantidadDisponible, restaurante.getIngredientes().get(i).precioCompra);
                    proteina.registrarVenta(PORCION_PROTEINA);
                    currentIngredientes.add(proteina);
                } else if (restaurante.getIngredientes().get(i) instanceof Grano) {
                    Ingrediente grano = new Grano(restaurante.getIngredientes().get(i).getNombre(), restaurante.getIngredientes().get(i).cantidadDisponible, restaurante.getIngredientes().get(i).precioCompra);
                    grano.registrarVenta(PORCION_GRANO);
                    currentIngredientes.add(grano);
                } else if (restaurante.getIngredientes().get(i) instanceof Almidon) {
                    Ingrediente almidon = new Almidon(restaurante.getIngredientes().get(i).getNombre(), restaurante.getIngredientes().get(i).cantidadDisponible, restaurante.getIngredientes().get(i).precioCompra);
                    almidon.registrarVenta(PORCION_ALMIDON);
                    currentIngredientes.add(almidon);
                }
            } else {
                currentIngredientes.add(restaurante.getIngredientes().get(i));
            }
        }
        restaurante.setIngredientes(currentIngredientes);
    }

    public boolean equals(Object obj) {
        boolean equal = false;
        return equal;
    }
}
