package clases;

import java.io.Serializable;
import java.util.HashMap;

public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String cif;
	private String name;
	private int population;
	private int telephone;
	private int valoration;
	private HashMap <String, Agente> a;
	
	public Empresa(String cif, String name, HashMap<String, Agente> a) {
		this.cif = cif;
		this.name = name;
		this.a = a;
	}
	
	public Empresa(String cif, String name, int population, int telephone, int valoration, HashMap<String, Agente> a) {
		this.cif = cif;
		this.name = name;
		this.population = population;
		this.telephone = telephone;
		this.valoration = valoration;
		this.a = a;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public int getTelephone() {
		return telephone;
	}

	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}

	public int getValoration() {
		return valoration;
	}

	public void setValoration(int valoration) {
		this.valoration = valoration;
	}

	public HashMap<String, Agente> getA() {
		return a;
	}

	public void setA(HashMap<String, Agente> a) {
		this.a = a;
	}

	@Override
	public String toString() {
		return "Empresa [cif=" + cif + ", name=" + name + ", population=" + population + ", telephone=" + telephone
				+ ", valoration=" + valoration + ", a=" + a + "]";
	}
	
}
