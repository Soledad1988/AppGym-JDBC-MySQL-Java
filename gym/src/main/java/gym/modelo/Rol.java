package gym.modelo;

/**
 * Enumeración que representa roles en el sistema.
 * 
 * <p>Los roles definen diferentes niveles de acceso y permisos en la aplicación.
 * En este enum, se definen dos roles: ADMINISTRADOR y USUARIO_REGULAR.</p>
 */
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
