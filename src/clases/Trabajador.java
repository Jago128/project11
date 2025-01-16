package clases;

public class Trabajador extends Agente {
	private String post;
	private Tipo type;
	private static boolean active=true;
	
	public Trabajador(String code, String dni, String name, String email, String post, Tipo type) {
		super(code, dni, name, email);
		this.post = post;
		this.type = type;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Tipo getType() {
		return type;
	}

	public void setType(Tipo type) {
		this.type = type;
	}

	public static boolean isActive() {
		return active;
	}

	public static void setActive(boolean active) {
		Trabajador.active = active;
	}

	@Override
	public String toString() {
		return "Trabajador [post=" + post + ", type=" + type + ", toString()=" + super.toString() + "]";
	}
	
}
