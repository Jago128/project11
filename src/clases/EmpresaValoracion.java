package clases;

public class EmpresaValoracion {
	private int valoracion;
	private int empresas;
	private int tutores;
	private int alumnas;
	
	public EmpresaValoracion() {
		this.valoracion = 0;
		this.empresas = 0;
		this.tutores = 0;
		this.alumnas = 0;
	}
	
	public EmpresaValoracion(int valoracion, int empresas, int tutores, int alumnas) {
		this.valoracion = valoracion;
		this.empresas = empresas;
		this.tutores = tutores;
		this.alumnas = alumnas;
	}

	public int getValoracion() {
		return valoracion;
	}

	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

	public int getEmpresas() {
		return empresas;
	}

	public void setEmpresas(int empresas) {
		this.empresas = empresas;
	}

	public int getTutores() {
		return tutores;
	}

	public void setTutores(int tutores) {
		this.tutores = tutores;
	}

	public int getAlumnas() {
		return alumnas;
	}

	public void setAlumnas(int alumnas) {
		this.alumnas = alumnas;
	}

	@Override
	public String toString() {
		return "EmpresaValoracion {Valoración: " + valoracion + ", Empresas: " + empresas + ", Tutores: " + tutores + ", Alumnas: " + alumnas + "}";
	}
}
