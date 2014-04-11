package Genetico;

import java.util.Comparator;

public class Compare implements Comparator<Cromossoma>{

	@Override
	public int compare(Cromossoma arg0, Cromossoma arg1) {
		int k=(int) (arg0.getFitness()*10000-arg1.getFitness()*10000);
		return k;
	}
}
