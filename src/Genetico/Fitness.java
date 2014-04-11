package Genetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;

import AStar.AStar;
import Data.Vertex;

public class Fitness {
	
	private Hashtable<Integer, Vertex> nodes;
	private double adicionarCombustivel=30;
	public Fitness(Hashtable<Integer, Vertex> nodes) {
		this.nodes=nodes;
	}

	public double evaluate(Cromossoma temp, ArrayList<Vertex> listabombas) {
		AStar as= new AStar(nodes);
		double result=0;
		for(int i=0; i < temp.size()-2; i+=2)
		{
			int begin = temp.get(i).convertToInt();
			int end=temp.get(i+2).convertToInt();
			if(temp.get(i+1).isBomba())
			{
				ArrayList<Double> escolhas= new ArrayList<Double>();
				for(int j=0; j < listabombas.size(); j++)
				{
					double d;
					LinkedList<Integer> x =as.calculateFitness(begin, listabombas.get(j).getId());
					d=calcular(x);
					
					x= as.calculateFitness(listabombas.get(j).getId(), end);
					d+=calcular(x);
					escolhas.add(d);
				}
				result+= Collections.min(escolhas);
			}
			else
			{
				LinkedList<Integer> x = as.calculateFitness(begin, end);
				result+= calcular(x);
			}
		}
		return result;
	}

	private double calcular(LinkedList<Integer> x) 
	{
		double soma=0;
		for(int i=0; i < x.size()-1; i++)
		{
			int index= x.get(i);
			int index2= x.get(i+1);
			Vertex temp = nodes.get(index);
			Vertex temp2 = nodes.get(index2);
			for(int k=0; k < temp.getConnections().size(); k++)
			{
				if(temp.getConnections().get(k).getDest()==temp2.getId())
				{
					double s= temp.getConnections().get(k).getDistance()/temp.getConnections().get(k).getMaxSpeed();
					soma+=s;
				}
			}
		}
		return soma;
	}
	
	
	private double calcularDistancia(LinkedList<Integer> x) 
	{
		double soma=0;
		for(int i=0; i < x.size()-1; i++)
		{
			int index= x.get(i);
			int index2= x.get(i+1);
			Vertex temp = nodes.get(index);
			Vertex temp2 = nodes.get(index2);
			for(int k=0; k < temp.getConnections().size(); k++)
			{
				if(temp.getConnections().get(k).getDest()==temp2.getId())
				{
					double s= temp.getConnections().get(k).getDistance();
					soma+=s;
				}
			}
		}
		return soma;
	}

	public double calculaGasolina(Cromossoma temp, ArrayList<Vertex> listabombas) {
		AStar as= new AStar(nodes);
		double result=0;
		for(int i=0; i < temp.size()-2; i+=2)
		{
			int begin = temp.get(i).convertToInt();
			int end=temp.get(i+2).convertToInt();
			if(temp.get(i+1).isBomba())
			{
				ArrayList<Double> escolhas= new ArrayList<Double>();
				for(int j=0; j < listabombas.size(); j++)
				{
					double d;
					LinkedList<Integer> x =as.calculateFitness(begin, listabombas.get(j).getId());
					d=calcularDistancia(x);
					
					x= as.calculateFitness(listabombas.get(j).getId(), end);
					d+=calcularDistancia(x);
					escolhas.add(d);
				}
				result+= Collections.min(escolhas);
				result-=adicionarCombustivel;
			}
			else
			{
				LinkedList<Integer> x = as.calculateFitness(begin, end);
				result+= calcularDistancia(x);
			}
		}
		return result;
	}

	public void printBetterSolution(Cromossoma better, ArrayList<Vertex> listabombas) 
	{
		AStar as = new AStar(nodes);
		for(int i=0; i < better.size()-1; i+=2)
		{
			int begin = better.get(i).convertToInt();
			int end=better.get(i+2).convertToInt();
			if(better.get(i+1).isBomba())
			{
				//ArrayList<Double> escolhas= new ArrayList<Double>();
				for(int j=0; j < listabombas.size(); j++)
				{
					as.calculateFitness(begin, listabombas.get(j).getId());
					as.print();
					//d=calcular(x);
					
					as.calculateFitness(listabombas.get(j).getId(), end);
					as.print();
					//d+=calcular(x);
					//escolhas.add(d);
				}
				//Collections.min(escolhas);
			}
			else
			{
				as.calculateFitness(begin, end);
				as.print();
			}
		}
	}

}
