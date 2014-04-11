package Genetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Random;
import Data.Car;
import Data.Vertex;


public class Populacao {
	private ArrayList<Cromossoma> populacao;
	private Car car;
	private ArrayList<Integer> paragens;
	private Fitness fitness;
	private ArrayList<Cromossoma> novaPopulacao;
	public Populacao(ArrayList<Integer> paragens)
	{
		this.paragens=paragens;
		populacao = new ArrayList<Cromossoma>();
	}
	
	public void geraPopulacaoInicial(Hashtable<Integer, Vertex> nodes, ArrayList<Vertex> listabombas)
	{
		int n=0;
		while(n<30)
		{
			ArrayList<Integer> temp=baralha(paragens);
			Cromossoma c= new Cromossoma(temp);
			if(validate(c, nodes, listabombas))
			{
				populacao.add(c);
				n++;
			}
		}
		System.out.println("Populacao inicial gerada");
	}
	
	private boolean validate(Cromossoma c, Hashtable<Integer, Vertex> nodes, ArrayList<Vertex> listabombas) {
		fitness = new Fitness(nodes);
		car=new Car(6);
		double quantidade= fitness.calculaGasolina(c,listabombas);
		if(quantidade < car.getFuel())
			return true;
		else
			return false;
	}

	public void updateFitness(Hashtable<Integer, Vertex> nodes, ArrayList<Vertex> listabombas)
	{
		fitness= new Fitness(nodes);
		for(int i=0; i < populacao.size(); i++)
		{
			Cromossoma temp=populacao.get(i);
			double k=fitness.evaluate(temp, listabombas);
			temp.setFitness(k);
		}
	}

	private ArrayList<Integer> baralha(ArrayList<Integer> paragens) {
		ArrayList<Integer> retorno= new ArrayList<Integer>();
		ArrayList<Integer> copia = (ArrayList<Integer>) paragens.clone();
		retorno.add(copia.get(0));
		copia.remove(0);
		for(int i=0; i < paragens.size()-2;i++)
		{
			Random r= new Random();
			int n;
			if(copia.size()>1)
				n= r.nextInt(copia.size()-1);
			else
				n=0;
			retorno.add(copia.get(n));
			copia.remove(n);
		}
		retorno.add(copia.get(0));
		return retorno;
	}

	
	public Cromossoma escolherCromossoma()
	{
		double totalFitness=0;
		for(int i=0; i < populacao.size(); i++)
			totalFitness+=1000/populacao.get(i).getFitness();
		Random r= new Random();
		double max = r.nextDouble()*totalFitness;
		double somatorio=0;
		for(int i=0; i < populacao.size(); i++)
		{
			somatorio+=1000/populacao.get(i).getFitness();
			if(somatorio >= max)
			{
				Cromossoma temp=populacao.get(i);
				//populacao.remove(i);
				return temp;
			}
		}
		return null;
	}
	
	public void nextGeneration(Hashtable<Integer, Vertex> nodes, ArrayList<Vertex> listabombas) {
		updateFitness(nodes, listabombas);
		Collections.sort(populacao, new Compare());
		int n=0;
		novaPopulacao = new ArrayList<Cromossoma>();
		while(n < 12)
		{
			
			Cromossoma c1=escolherCromossoma();
			Cromossoma c2=escolherCromossoma();
			cruzarCromossoma(c1, c2);
			n++;
		}
		for(int i=0; i < populacao.size(); i++)
			novaPopulacao.add(populacao.get(i));
		Collections.sort(novaPopulacao, new Compare());
		populacao= new ArrayList<Cromossoma>();
		for(int i=0; i < 30; i++)
			populacao.add(novaPopulacao.get(i));
	}

	
	private void cruzarCromossoma(Cromossoma c1, Cromossoma c2) 
	{
		Random r = new Random();
		int size=c1.size();
		int index = r.nextInt(size);
		ArrayList<Gene> gc1 = c1.getCromossoma();
		ArrayList<Gene> gc2 = c2.getCromossoma();
		ArrayList<Gene> final1= new ArrayList<Gene>();
		ArrayList<Gene> final2= new ArrayList<Gene>();
		for(int i =0; i < c1.size(); i++)
		{
			if(i < index)
				final1.add(gc1.get(i));
			else
				final1.add(gc2.get(i));
		}
		for(int i =0; i < c1.size(); i++)
		{
			if(i < index)
				final2.add(gc2.get(i));
			else
				final2.add(gc1.get(i));
		}
		mutacao(final1);
		mutacao(final2);
		novaPopulacao.add(new Cromossoma(final1, 0));
		novaPopulacao.add(new Cromossoma(final2, 0));
		//Troca os repetidos pelo que falta, escolhendo aleatoriamente um dos repetidos. Agora é necessario continuar o ciclo para gerar a proxima geracao
	}

	private void mutacao(ArrayList<Gene> c1) 
	{
		ArrayList<Integer> falta = new ArrayList<Integer>();
		for(int i=0; i < paragens.size(); i++)
		{
			if(!contem(c1, paragens.get(i)))
			{
				falta.add(paragens.get(i));
			}
		}
		if(falta.size()>0)
			substituirRepetido(c1, falta);

	}

	private void substituirRepetido(ArrayList<Gene> c1, ArrayList<Integer> falta) {
		int size =Collections.max(paragens);
		for(int k=0; k < falta.size(); k++)
		{
			substituir_aux(c1, falta.get(k), size);
		}
	}

	private void substituir_aux(ArrayList<Gene> c1, Integer integer, int size) {
		for(int i=2; i< c1.size()-1; i+=2)
		{
			for(int j=2; j < c1.size()-1; j+=2)
			{
				if(c1.get(j).equals(c1.get(i).convertToInt()) && i!=j)
				{
					Random r = new Random();
					int choice = r.nextInt(2);
					if(choice == 0)
						c1.set(j, new Gene(integer, Integer.toBinaryString(size).length()));
					else
						c1.set(i, new Gene(integer, Integer.toBinaryString(size).length()));
					return;
				}
			}
		}
		
	}

	private boolean contem(ArrayList<Gene> c1, Integer integer) {
		for(int i=0; i < c1.size(); i++)
		{
			if(c1.get(i).convertToInt() == integer)
				return true;
		}
		return false;
	}

	public void printBetterSolution(Hashtable<Integer, Vertex> nodes, ArrayList<Vertex> paragens) {
		Cromossoma better = populacao.get(0);
		Fitness f = new Fitness(nodes);
		f.printBetterSolution(better, paragens);
	}
}
