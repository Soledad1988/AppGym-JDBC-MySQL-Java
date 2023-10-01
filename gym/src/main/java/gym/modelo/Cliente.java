package gym.modelo;

public class Cliente {

	Integer id;
	String nombre;
	String apellido;
	String direccion;
	
	public Cliente(Integer id, String nombre, String apellido, String direccion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
	}

	public Cliente(String nombre, String apellido, String direccion) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}


	public String getDireccion() {
		return direccion;
	}

	
	@Override
	public String toString() {
		return String.format(
				"{id: %s, nombre: %s, apellido: %s, direccion: %d}",
				this.id,
				this.nombre,
				this.apellido,
				this.direccion);
		}
}
