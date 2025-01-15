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
	
}
