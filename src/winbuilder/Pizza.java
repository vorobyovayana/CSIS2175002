package winbuilder;

public class Pizza {
	public String pName;
	public double price;
	
	public Pizza() {
		this("", 0.0);
	}
	public Pizza(String name, double p) {
		this.setpName(name);
		this.setPrice(p);
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

}
