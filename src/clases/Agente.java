package clases;

public class Agente {
	protected String code;
	protected String dni;
	protected String name;
	protected String email;
	
	public static int setCode=1;
	
	public Agente(String code, String dni, String name, String email) {
		this.code = code+setCode;
		this.dni = dni;
		this.name = name;
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Agente [code=" + code + ", dni=" + dni + ", name=" + name + ", email=" + email + "]";
	}
}
