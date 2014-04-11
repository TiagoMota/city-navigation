package AStar;

import java.util.Hashtable;
import java.util.LinkedList;

import Data.Vertex;

public class AStar {

	Hashtable<Integer, Vertex> nodes;
	private float f_score;
	 LinkedList<Integer> bestPath;
	public AStar(Hashtable<Integer, Vertex> nodes)
	{
		this.nodes=nodes;
	}
	
	
	public LinkedList<Integer> calculateFitness(Integer begin, Integer end)
	{
	    LinkedList<Integer> evaluatedNodes = new LinkedList<Integer>(); // The set of nodes already evaluated.
        LinkedList<Integer> nodesToEvaluate = new LinkedList<Integer>();    // The set of tentative nodes to be evaluated
        bestPath = new LinkedList<Integer>();   // The map of navigated nodes
        int currentNode = begin;
        int previousNode = 0;

        //evaluatedNodes initially contains the start node
        nodesToEvaluate.add(begin);
        //add the start node to bestPath
        bestPath.add(begin);

        while(currentNode != end){
                LinkedList<Integer> options = new LinkedList<Integer>(); // The set of nodes already evaluated.
                options = possibleOptions(currentNode);


                //System.out.println("Options: " + options);
                if(options.size()!=1)
	                for(int i = 0; i < options.size(); i++){
	                        if(evaluatedNodes.contains(options.get(i))){
	                                options.remove(i);
	                                i--;
	                        }
	                }

                float f_scoreTemp = 100;
                previousNode = currentNode;
                for(int i = 0; i < options.size(); i++){
                        f_score = heuristic_cost_estimate(options.get(i), end, getCost(options.get(i), bestPath));

                        if(f_score < f_scoreTemp){
                                f_scoreTemp = f_score;
                                currentNode = options.get(i);
                        }
                }
           /*     if(bestPath.size()>1)
	                if(currentNode==bestPath.get(bestPath.size()-2))
	                {
	                	int size= bestPath.size();
	                	bestPath.remove(size-1);
	                	bestPath.remove(size-2);
	                }*/
                nodesToEvaluate.remove(0);
                //Add the evaluated node to the evaluated nodes list    
               evaluatedNodes.add(previousNode);
                //Now we evaluate the currentNode                       
                nodesToEvaluate.add(currentNode);               
                previousNode = currentNode;
                //Add currentNode to our path
                bestPath.add(currentNode);
        }
		retiraCiclos(bestPath);
		return bestPath;
	}
	


	
	private void retiraCiclos(LinkedList<Integer> bestPath2) {
		for(int i=1; i < bestPath2.size()-1;i++)
			for(int k=1; k < bestPath2.size()-1; k++)
			{
				if(bestPath2.get(i)==bestPath2.get(k) && i!=k)
				{
					for(int m=i; m < k; m++)
						bestPath2.remove(i);
					return;
				}
			}
		
	}


	private float getCost(Integer end, LinkedList<Integer> bestPath) {
		float soma=0;
		for(int i=0;i < bestPath.size()-1; i++)
		{
			Vertex inicio= nodes.get(bestPath.get(i));
			for(int k=0; k < inicio.getConnections().size();k++)
			{
				if(inicio.getConnections().get(k).getDest()==bestPath.get(i+1))
					soma+=inicio.getConnections().get(k).getDistance()/inicio.getConnections().get(k).getMaxSpeed();
			}
		}
		Vertex temp = nodes.get(bestPath.get(bestPath.size()-1));
		for(int i=0; i < temp.getConnections().size(); i++)
		{
			if(temp.getConnections().get(i).getDest()==end)
			{
				int n=temp.getConnections().get(i).getMaxSpeed();
				double k=temp.getConnections().get(i).getDistance();
				soma+= k/n;
				return  soma;
			}
		}
		return -1;
	}

	private float heuristic_cost_estimate(Integer option, Integer end, float f) {
		Vertex start= nodes.get(option);
		Vertex endx= nodes.get(end);
		return (float) (f + Math.sqrt(Math.pow((start.getX() - endx.getX()), 2) + Math.pow((start.getY() - endx.getY()), 2)));
	}

	private LinkedList<Integer> possibleOptions(int currentNode) {
		LinkedList<Integer> retorno = new LinkedList<Integer>();
		Vertex temp = nodes.get(currentNode);
		for(int i=0; i < temp.getConnections().size(); i++)
		{
			retorno.add(temp.getConnections().get(i).getDest());
		}
		return retorno;
	}


	public void print() {
		System.out.print("Percurso até à proxima paragem: ");
		for(int i=0; i < bestPath.size()-1; i++)
		{
			System.out.print(nodes.get(bestPath.get(i)).getName() + "->");
		}
		System.out.println(nodes.get(bestPath.get(bestPath.size()-1)).getName());
	}
}
