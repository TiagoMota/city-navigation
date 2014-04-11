package Data;
import java.util.Vector;



public class Vertex {

	private int id;
	private String name;
	private float x;
	private float y;
	private Vector<Edge> connections;
	/*
	 * tipos a atribuir: inicio, fim, paragem ou bomba
	 */
	private String type;
	
	public Vertex(int id, String name, String type, float x, float y)
	{
		this.id=id;
		this.name=name;
		this.type=type;
		this.x=x;
		this.y=y;
		connections = new Vector<Edge>();
	}
	
	
	//Inicio de getters e setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Vector<Edge> getConnections() {
		return connections;
	}

	public void setConnections(Vector<Edge> connections) {
		this.connections = connections;
	}


	public double getTime(int id) {
		for(int i=0; i<connections.size(); i++)
		{
			if(connections.elementAt(i).getDest()==id)
			{
				double dist= connections.elementAt(i).getDistance();
				double vel= connections.elementAt(i).getMaxSpeed();
				return dist/vel;
			}
		}
		return -1;
	}


	public double getDistance(int id2) {
		for(int i=0; i< connections.size(); i++)
		{
			if(connections.get(i).getDest()==id2)
				return connections.get(i).getDistance();
		}
		return -1;
	}


	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}
	
	
	
	//Fim de getters e setters
}
