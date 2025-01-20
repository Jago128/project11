package clases;

public class Trabajador extends Agente {
	private String post;
	private Tipo type;
	private boolean active;
	
	public Trabajador(String code, String dni, String name, String email, String post, Tipo type) {
		super(code, dni, name, email);
		this.post = post;
		this.type = type;
		this.active=true;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Trabajador [post=" + post + ", type=" + type + ", toString()=" + super.toString() + "]";
	}
	
}
