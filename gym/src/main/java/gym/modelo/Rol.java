package gym.modelo;

public enum Rol {
	
    ADMINISTRADOR("Administrador"),
    USUARIO_REGULAR("Usuario Regular");

    private final String nombre;

    Rol(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
}
