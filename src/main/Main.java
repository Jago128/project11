package main;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.*;

import clases.*;
import exceptions.IncorrectEmailFormatException;
import utilidades.*;

public class Main {

	public static void main(String[] args) {
		ArrayList <Empresa> empr=new ArrayList<Empresa>();
		int menu=menu();
		File em=new File("empresas.dat");

		do {
			switch (menu) {
			case 1:
				addEmpr(empr,em);
				break;

			case 2:
				inactiveTrabajador(empr,em);
				break;

			case 3:
				removeAlumnas(empr,em);
				break;

			case 4:
				statEmpr(empr,em);
				break;

			case 5:
				System.out.println("Adios!");
			}

		} while (menu!=5);
	}

	public static int menu() {
		System.out.println("1. Añadir una nueva empresa");
		System.out.println("2. Dar de baja a un trabajador");
		System.out.println("3. Eliminar alumnas de un curso academico");
		System.out.println("4. Valoración de las empresas con la que hemos colaborado en el curso actual 2024-2025");
		System.out.println("5. Salir");
		return Utilidades.leerInt(1,5);
	}

	public static void addEmpr(ArrayList<Empresa> empr, File em) {
		String cif, name;
		char choice, addMore;
		int menu;
		boolean found=false;

		do {
			found=false;
			System.out.println("Introduce el CIF de la empresa:");
			cif=Utilidades.introducirCadena();

			for (Empresa e:empr) {
				if (e.getCif().equalsIgnoreCase(cif)) {
					found=true;
					System.err.println("Ya existe una empresa con el CIF introducido. Aqui tiene la informacion de la empresa:");
					System.out.println(e.toString());
				}
			}
			if (!found) {
				System.out.println("Introduce el nombre de la empresa:");
				name=Utilidades.introducirCadena();
				System.out.println("¿Quiere añadir mas datos para la empresa? (S/N)");
				choice=Utilidades.leerChar('S','N');
				if (choice=='N') {
					Empresa eI=new Empresa(cif, name, new HashMap <String, Agente>());
					writeEmpr(eI,empr,em);
				} else {
					Empresa eI=new Empresa(cif, name, new HashMap <String, Agente>());
					do {
						menu=emprMenu();
						switch (menu) {
						case 1:
							System.out.println("Introduce la poblacion:");
							eI.setPopulation(Utilidades.leerInt());
							break;

						case 2:
							System.out.println("Introduce el telefono:");
							eI.setTelephone(Utilidades.leerInt());
							break;

						case 3:
							System.out.println("Introduce una valoracion entre 1 y 10:");
							eI.setValoration(Utilidades.leerInt(1,10));
							break;

						case 4:
							addAgent(eI,name,em);
							break;

						case 5:
							writeEmpr(eI,empr,em);
						}
					} while (menu!=5);
				}
			}

			if (found) {
				System.out.println("¿Quiere añadir una nueva empresa? (S/N)");
				addMore=Utilidades.leerChar('S','N');
			} else {
				System.out.println("¿Quiere añadir otra empresa? (S/N)");
				addMore=Utilidades.leerChar('S','N');
			}
		} while (addMore=='S');
	}

	public static int emprMenu() {
		System.out.println("Aqui tiene las opciones:");
		System.out.println("1. Poblacion");
		System.out.println("2. Telefono");
		System.out.println("3. Valoracion");
		System.out.println("4. Nuevo agente");
		System.out.println("5. Salir");
		return Utilidades.leerInt("Introduce la opcion:",1,5);
	}

	public static void addAgent(Empresa eI, String emprName, File em) {
		Tipo setType=null;
		LocalDate birthDate;
		String dni, name, email=null, activity=null, type;
		boolean found=false, error=false, typeError=false;
		char choice;

		System.out.println("Introduce el DNI:");
		dni=Utilidades.introducirCadena();
		for (int i=0; i<eI.getA().size()&&!found;i++) {
			if (eI.getA().containsKey(dni)) {
				found=true;
			}
		}
		if (found) {
			System.out.println("Ya existe un agente con el DNI introducido.");
		} else {
			System.out.println("El codigo es automaticamente generado.\n");

			System.out.println("Introduce el nombre:");
			name=Utilidades.introducirCadena();
			do {
				error=false;
				try {
					System.out.println("Introduce el email:");
					email=Utilidades.introducirCadena();
					validateEmail(email);
				} catch (IncorrectEmailFormatException e) {
					error=true;
					System.out.println(e.getMessage());
				}
			} while (error);
			System.out.println("¿Quiere añadir un Trabajador o un Agente? (T/A)");
			choice=Utilidades.leerChar('T','A');

			switch (choice) {
			case 'T':
				do {
					typeError=false;
					try {
						System.out.println("¿Es de tipo Contacto o Tutor? (C/T)");
						type=Utilidades.introducirCadena();
						setType=Tipo.valueOf(type);
					} catch (IllegalArgumentException e) {
						typeError=true;
						System.out.println("El tipo introducido es invalido.");
					}
				} while (typeError);
				eI.getA().put(dni, new Trabajador(generateCode(emprName,em), dni, name, email, activity, setType));
				break;

			case 'A':
				System.out.println("El periodo es automaticamente generado.");
				System.out.println("Introduce la fecha de nacimiento: (DD/MM/AAAA)");
				birthDate=Utilidades.leerFechaDMA();
				eI.getA().put(dni, new Alumna(generateCode(emprName,em), dni, name, email, birthDate));
				break;
			}
		}
	}

	public static String generateCode(String emprName, File em) {
		for (int i=0;i<Utilidades.calculoFichero(em);i++) {
			Agente.setCode++;
		}

		return emprName.substring(0,5)+"-"+Agente.setCode;
	}

	public static void writeEmpr(Empresa eI, ArrayList<Empresa> em, File empr) {
		ObjectOutputStream oos;
		MyObjectOutputStream moos;
		if (empr.exists()) {
			try {
				moos=new MyObjectOutputStream(new FileOutputStream(empr,true));
				moos.writeObject(eI);
				moos.close();
				System.out.println("La empresa se ha añadido al fichero.");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				oos=new ObjectOutputStream(new FileOutputStream(empr));
				oos.writeObject(eI);
				oos.close();
				System.out.println("El fichero ha sido creado con la empresa.");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		em.add(eI);
	}
	public static void validateEmail(String email) throws IncorrectEmailFormatException {
		Pattern modelo=Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3,})$");
		Matcher matcher=modelo.matcher(email);
		if (!matcher.matches()) {
			throw new IncorrectEmailFormatException("Error: El email es invalido!");
		}
	}

	public static void inactiveTrabajador(ArrayList <Empresa> empr, File em) {
		String cif, dni;
		boolean found=false, inactive=false, incorrect=true;

		System.out.println("Introduce el CIF de una empresa:");
		cif=Utilidades.introducirCadena();

		for (int i=0;i<empr.size()&&!found;i++) {
			if (empr.get(i).getCif().equals(cif)) {
				found=true;
				System.out.println("Introduzca el DNI del trabajador que se quiera dar la baja:");
				dni=Utilidades.introducirCadena();
				for (Agente a:empr.get(i).getA().values()) {
					if (a.getDni().equals(dni) && a instanceof Trabajador) {
						incorrect=false;
						if (!((Trabajador)a).isActive()) {
							System.out.println("El trabajador ya esta de baja.");
						} else {
							inactive=true;
							((Trabajador)a).setActive(false);
							updateFile(empr,em);
						}
					}
				}
			}
		}

		if (!found) {
			System.out.println("La empresa no se ha podido encontrar.");
		} else if (!inactive) {
			System.out.println("El trabajador no ha sido encontrado.");
		} else if (!inactive && incorrect) {
			System.out.println("El DNI introducido no corresponde al de un trabajador.");
		}
	}

	public static void removeAlumnas(ArrayList <Empresa> empr, File em) {
		String period;
		boolean found=false;
		char deletThis;

		System.out.println("Introduce el curso academico:");
		period=Utilidades.introducirCadena();

		for (int i=0;i<empr.size();i++) {
			for (Agente a:empr.get(i).getA().values()) {
				if (((Alumna)a).getPeriod().equals(period)) {
					found=true;
					System.out.println("Listado de alumnos:");
					for (Agente aShow:empr.get(i).getA().values()) {
						System.out.println(aShow.toString());
					}
					System.out.println("¿Esta seguro de que quieras eliminar estos alumnos?");
					deletThis=Utilidades.leerChar('S','N');
					if (deletThis=='S') {
						empr.get(i).getA().clear();
					}
					if (empr.get(i).getA().isEmpty()) {
						empr.remove(i);
					}
				}
			}
		}

		if (!found) {
			System.out.println("No se ha encontrado ningun alumno para eliminar.");
		} else {
			updateFile(empr,em);
		}
	}

	public static void updateFile(ArrayList <Empresa> empr, File em) {
		ObjectOutputStream oos;
		if (em.exists()) {
			try {
				oos=new MyObjectOutputStream(new FileOutputStream(em));
				for (int i=0;i<empr.size();i++) {
					oos.writeObject(empr.get(i));
				}
				oos.close();
				System.out.println("Las empresas se han actualizado.");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void statEmpr(ArrayList <Empresa> empr, File em) {
		ObjectInputStream ois = null;
		Empresa empresa;
		ArrayList<EmpresaValoracion> valoraciones = new ArrayList<>();
		boolean continuar = true, existe = false, encontrada = false;;
		int tutores, alumnas;
		String curso = LocalDate.now().getYear() + "-" + (LocalDate.now().getYear() + 1);
		double mediaTutores, mediaAlumnas;

		try {
			ois = new ObjectInputStream(new FileInputStream(em));
			while (continuar) {
				try {
					empresa = (Empresa) ois.readObject();
					tutores = 0;
					alumnas = 0;
					existe = false;
					encontrada = false;

					for (Agente agente : empresa.getA().values()) {
						if (agente instanceof Trabajador && ((Trabajador) agente).getType() == Tipo.TUTOR && ((Trabajador) agente).isActive()) {
							tutores++;								
						} else {
							if (((Alumna) agente).getGroup().equals(curso)) {
								alumnas++;
								existe = true;
							}
						}
					}

					if (existe) {
						for (EmpresaValoracion valoracion : valoraciones) {
							if (valoracion.getValoracion() == empresa.getValoration()) {
								valoracion.setEmpresas(valoracion.getEmpresas() + 1);
								valoracion.setTutores(valoracion.getTutores() + tutores);
								valoracion.setAlumnas(valoracion.getAlumnas() + alumnas);
								encontrada = true;
								break;
							}
						}
						if (!encontrada) {
							valoraciones.add(new EmpresaValoracion(empresa.getValoration(), 1, tutores, alumnas));
						}
					}
				} catch (EOFException eof) {
					continuar = false;
				} catch (ClassNotFoundException cnf) {
					cnf.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.out.println("Error al procesar los archivos: " + e.getMessage());
		} finally {
			try {
				 ois.close();
			} catch (IOException e) {
				System.out.println("Error al cerrar el archivo: " + e.getMessage());
			}
		}

		if (valoraciones.isEmpty()) {
			System.out.println("No se han encontrado empresas con alumnaspara el curso " + curso);
		} else {
			valoraciones.sort(Comparator.comparing(EmpresaValoracion::getEmpresas).reversed());
			System.out.printf("%-25s %-25s %-25s %-25s\n", "VALORACIÓN", "Nº EMPRESAS", "MEDIA DE TUTORES", "MEDIA DE ALUMNAS");
			for (EmpresaValoracion valoracion : valoraciones) {
				mediaTutores = (double) valoracion.getTutores() / valoracion.getEmpresas();
				mediaAlumnas = (double) valoracion.getAlumnas() / valoracion.getEmpresas();
				System.out.printf("%-25d %-25d %-25.2f %-25.2f\n", valoracion.getValoracion(), valoracion.getEmpresas(), mediaTutores, mediaAlumnas);
			}
		}
	}
}
