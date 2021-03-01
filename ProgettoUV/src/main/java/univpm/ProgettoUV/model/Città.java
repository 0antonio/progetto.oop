package univpm.ProgettoUV.model;

public class Città {
	String name;
	String country;
	
	public Città(String name, String country) {
		this.name = name;
		this.country = country;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public boolean equals(Città nuova) {
		if ((this.name == nuova.getName()) &&(this.country == nuova.getCountry()))
			return true;
		return false;
	}

}
