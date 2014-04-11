package Main;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

import javax.swing.JFrame;

import Data.Edge;
import Data.Vertex;
import Genetico.Populacao;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class Main extends JFrame{
private static final long serialVersionUID = 1L;

	private ArrayList<Vertex> listabombas = null;
	private Hashtable<Integer, Vertex> nodesTable=new Hashtable<Integer, Vertex>();
	private mxGraph graph;
	private mxGraphComponent graphComponent;
	private Hashtable<Integer, Object> vertexH;
	private ArrayList<Integer> paragens=new ArrayList<Integer>();
	private int id;
	private int idEdge;
	public Main()
	{
		id=0;
		idEdge=0;
		listabombas= new ArrayList<Vertex>();
		importDefaultData();
		creatGraph();
		this.setVisible(true);
		while(true)
		{
			System.out.println("1-Percurso");
			System.out.println("2-Adicionar");
			System.out.println("3-Remover");
			System.out.print("Opcao:");
			Scanner sc= new Scanner(System.in);
			int escolha=sc.nextInt();
			switch (escolha) {
			case 1:
				procurar();
				break;
			case 2:
				adicionar();
				break;
			case 3:
				remover();
				break;
			default:
				break;
			}
		}
	}
	
	private void adicionar() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Adicionar nó");	
		System.out.print("Nome:");
		String nome = sc.next();
		System.out.print("Tipo");
		String tipo = sc.next();
		System.out.print("Posicao X:");
		int x = sc.nextInt();
		System.out.print("Posicao Y:");
		int y = sc.nextInt();
		Vertex temp = new Vertex(id, nome, tipo, x, y);
		System.out.println("Adicionar Ligacoes:");
		Enumeration<Integer> it= nodesTable.keys();
		while(it.hasMoreElements())
		{
			int id2=(Integer) it.nextElement();
			System.out.print(id2);
			System.out.println(" " + nodesTable.get(id2).getName());
		}
		while(true)
		{
			
			System.out.println("Inserir id do nó de ligacao");
			int idf=sc.nextInt();
			System.out.println("Inserir distancia até ao nó");
			int dist=sc.nextInt();
			System.out.println("Inserir distancia do nó até ao novo nó");
			int dist2=sc.nextInt();
			System.out.println("Inserir velocidade máxima:");
			int max=sc.nextInt();
			Edge edge= new Edge(idEdge, id, idf, dist, max);
			Edge edge1= new Edge(idEdge, idf, id, dist2, max);
			temp.getConnections().add(edge);
			temp.getConnections().add(edge1);
			System.out.println("Pretende inserir mais ligacoes?(sim->1)");
			int fim=sc.nextInt();
			if(fim!=1)
				break;
		}
		
	}

	private void remover() {
		Enumeration<Integer> it= nodesTable.keys();
		while(it.hasMoreElements())
		{
			int id2=(Integer) it.nextElement();
			System.out.print(id2);
			System.out.println(" " + nodesTable.get(id2).getName());
		}
		Scanner sc = new Scanner(System.in);
		System.out.println("Indice do nó a remover:");	
		int y = sc.nextInt();
		nodesTable.remove(nodesTable.get(y));
		it= nodesTable.keys();
		while(it.hasMoreElements())
		{
			int id2=(Integer) it.nextElement();
			int size=nodesTable.get(id2).getConnections().size();
			for(int i=0; i < size; i++)
			{
				if(nodesTable.get(id2).getConnections().get(i).getDest()==y)
				{
					nodesTable.get(id2).getConnections().remove(i);
					size--;
					i--;
				}
			}
		}
		
	}

	public void procurar()
	{
		choice();
		Populacao p = new Populacao(paragens);
		System.out.println("Inicio do processo");
		p.geraPopulacaoInicial(nodesTable, listabombas);
		int i=0;
		while(i < 10000)
		{
			p.nextGeneration(nodesTable, listabombas);
			i++;
		}
		System.out.println("Fim do processo");
		p.updateFitness(nodesTable, listabombas);
		p.printBetterSolution(nodesTable, listabombas);
	}
	
	public void choice()
	{
		Scanner cin= new Scanner(System.in);
		Enumeration<Integer> it= nodesTable.keys();
		while(it.hasMoreElements())
		{
			int id2=(Integer) it.nextElement();
			System.out.print(id2);
			Vertex temp=nodesTable.get(id2);
			System.out.println(" " + temp.getName());
		}
		System.out.print("Ponto de Partida:");
		int inicio=cin.nextInt();
		paragens.add(inicio);
	
		int fim=2;
		
		System.out.print("Pontos obrigatórios de passagem (separados por virgulas):");
		String escolhas=cin.next();
		String[] listaEscolhas=escolhas.split(",");
		
		for(int i=0; i < listaEscolhas.length; i++)
			paragens.add(Integer.parseInt(listaEscolhas[i]));
		paragens.add(fim);
	}
	
	public void creatGraph()
	{
		setSize(800,600);
		setLocationRelativeTo(null);
		graph=new mxGraph();
		graphComponent = new mxGraphComponent(graph);
		graphComponent.setPreferredSize(new Dimension(400,400));
		getContentPane().add(graphComponent);
		graph.getModel().beginUpdate();
		Object parent= graph.getDefaultParent();
		vertexH = new Hashtable<Integer, Object>();
		Enumeration<Integer> it= nodesTable.keys();
		while(it.hasMoreElements())
		{
			int id=(Integer) it.nextElement();
			Vertex temp=nodesTable.get(id);
			Object tempO = graph.insertVertex(parent, null , temp.getName(), temp.getX()*50, temp.getY()*50, 100, 50);
			vertexH.put( temp.getId(), tempO);
		}
		Enumeration<Integer> itw= nodesTable.keys();
		while(itw.hasMoreElements())
		{
			int id=(Integer) itw.nextElement();
			Vertex temp=nodesTable.get(id);
			for(int j=0; j< temp.getConnections().size(); j++)
			{
				int source = temp.getConnections().elementAt(j).getSource();
				int dest = temp.getConnections().elementAt(j).getDest();
				Object t1, t2;
				t1=vertexH.get(source);
				t2=vertexH.get(dest);
				graph.insertEdge(parent, null, temp.getConnections().elementAt(j).getDistance(), t1, t2, "startArrow=none;endArrow=none;strokeWidth=4;strokeColor=#66FF00");
			}
		}
		graph.getModel().endUpdate();
	}
	
	public void importDefaultData() {
		
		File vertexs = new File("Vertex.txt");
		File edges = new File("Edges.txt");
		BufferedReader reader = null;
		BufferedReader reader2 = null;
		
		try {
			reader = new BufferedReader(new FileReader(vertexs));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String text = null;
		
		try {
			while ((text = reader.readLine()) != null) {
				String[] lineRead = text.split(" ");
				
				Vertex tmp = new Vertex(Integer.parseInt(lineRead[1]), lineRead[0], lineRead[3], Float.parseFloat(lineRead[4]), Float.parseFloat(lineRead[5]));
				if(tmp.getType().equals("bomba"))
				{
					listabombas.add(tmp);
				}
				id++;
				nodesTable.put(Integer.parseInt(lineRead[1]), tmp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//import dos edges default do grafo
		try {
			reader2 = new BufferedReader(new FileReader(edges));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String text2 = null;
		
		try {
			while ((text2 = reader2.readLine()) != null) {
				String[] lineRead = text2.split(" ");
				Edge tmp = new Edge(Integer.parseInt(lineRead[0]), Integer.parseInt(lineRead[1]), Integer.parseInt(lineRead[2]), Double.parseDouble(lineRead[3]), Integer.parseInt(lineRead[4]));
				if(nodesTable.get(Integer.parseInt(lineRead[1]))!=null){
					nodesTable.get(Integer.parseInt(lineRead[1])).getConnections().add(tmp);
				}
				idEdge++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) 
	{
		new Main();
	}
}
