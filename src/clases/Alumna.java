package clases;

import java.time.LocalDate;
import java.time.Year;

public class Alumna extends Agente {
	private LocalDate birthDate;
	private String period;
	private String group;
	
	public Alumna(String code, String dni, String name, String email, LocalDate birthDate, String group) {
		super(code, dni, name, email);
		this.birthDate = birthDate;
		this.group = generatePeriod();
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "Alumna [birthDate=" + birthDate + ", group=" + group + ", toString()=" + super.toString() + "]";
	}
	
	public String generatePeriod() {
		return Year.now().minusYears(1)+"-"+Year.now().getValue();
		
	}
	
}
