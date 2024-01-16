package gym.modelo;

public enum Rol {
	
	ADMINISTRADOR("ADMINISTRADOR"),
    USUARIO_REGULAR("USUARIO_REGULAR");

    private final String nombre;

    Rol(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
     
}
