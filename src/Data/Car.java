package Data;

public class Car {

	private double fuel;
	private double consumo;
	
	public Car(double consumo){
		
		fuel=30;
		this.consumo=consumo;
	}

	public double getFuel() {
		return fuel;
	}

	public void adicionarCombustivel(double deposito)
	{
		this.fuel+=deposito;
	}
	
	public void retirarCombustivel(double gasto)
	{
		this.fuel-=gasto;
	}
	
	public void setFuel(double fuel) {
		this.fuel = fuel;
	}

	public double getConsumo() {
		return consumo;
	}

	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}
	
	
}
