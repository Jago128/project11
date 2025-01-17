package main;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import clases.*;
import exceptions.IncorrectEmailFormatException;
import utilidades.*;

public class Main {

	public static void main(String[] args) {
		int menu=menu();

		ArrayList <Empresa> empr=new ArrayList<Empresa>();

		do {
			switch (menu) {
			case 1:
				addEmpr(empr);
				break;

			case 2:

				break;

			case 3:

				break;

			case 4:

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

	public static void addEmpr(ArrayList<Empresa> empr) {
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
					Empresa eI=new Empresa(cif, name, new HashMap<String, Agente>());
					writeEmpr(eI,empr);
				} else {
					Empresa eI=new Empresa(cif, name, new HashMap<String, Agente>());
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
							addAgent(eI,name);
							break;

						case 5:
							writeEmpr(eI,empr);
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

	public static void addAgent(Empresa eI, String emprName) {
		String dni, name, email, activity;
		boolean found=false, error=false, active;
		char choice, type;

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
				System.out.println("¿Es de tipo Contacto o Tutor? (C/T)");
				type=Utilidades.leerChar('C','T');
				break;

			case 'A':

				break;
			}
		}
	}
	
	public static void writeEmpr(Empresa eI, ArrayList<Empresa> em) {
		ObjectOutputStream oos;
		MyObjectOutputStream moos;
		File empr=new File("empresas.dat");
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
		Pattern modelo = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3,})$");
		Matcher matcher = modelo.matcher(email);
		if (!matcher.matches()) {
			throw new IncorrectEmailFormatException("Error: El email es invalido!");
		}
	}


}
