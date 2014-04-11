package Genetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;



public class Cromossoma {
	private ArrayList<Gene> cromossoma;
	private double fitness;
	
	public Cromossoma(ArrayList<Integer> paragens)
	{
		Integer n = Collections.max(paragens);
		String s= Integer.toBinaryString(n);
		cromossoma= new ArrayList<Gene>();
		for(int i=0; i < paragens.size()-1; i++)
		{
			Random r= new Random();
			int bomba=r.nextInt(2);
			cromossoma.add(new Gene(paragens.get(i), s.length()));
			cromossoma.add(new Gene(bomba, s.length()));
		}
		cromossoma.add(new Gene(paragens.get(paragens.size()-1), s.length()));
	}
	
	public Cromossoma(ArrayList<Gene> c, int n)
	{
		this.cromossoma=c;
	}

	public void setFitness(double k) {
		this.fitness=k*60;
	}
	
	public double getFitness()
	{
		return fitness;
	}
	
	public int size()
	{
		return cromossoma.size();
	}
	
	public Gene get(int index)
	{
		return cromossoma.get(index);
	}

	public void print() {
		System.out.print('[');
		for(int i=0; i < cromossoma.size()-1; i++)
		{
			cromossoma.get(i).print();
			System.out.print(',');
		}
		cromossoma.get(cromossoma.size()-1).print();
		System.out.print(']');
	}

	public ArrayList<Gene> getCromossoma() {
		return cromossoma;
	}

	public void setCromossoma(ArrayList<Gene> cromossoma) {
		this.cromossoma = cromossoma;
	}
	

}
