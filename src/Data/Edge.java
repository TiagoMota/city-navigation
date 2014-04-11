package Data;

public class Edge {

	private int id;
	private int source;
	private int dest;
	private double distance;
	private int maxSpeed;
	
	public Edge(int id, int source, int dest, double distance,int maxSpeed)
	{
		this.id=id;
		this.source=source;
		this.dest=dest;
		this.distance=distance;
		this.maxSpeed=maxSpeed;
	}

	
	//Inicio de getters e setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getDest() {
		return dest;
	}

	public void setDest(int dest) {
		this.dest = dest;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	//Fim de getters e setters
	
	
}
