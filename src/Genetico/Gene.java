package Genetico;
import java.util.ArrayList;

public class Gene {
	
	ArrayList<Boolean> gene= new ArrayList<Boolean>();
	
	public Gene(int value, int size)
	{
		String s=Integer.toBinaryString(value);
		int n=0;
		for(int i=size-1; i >=0; i--)
		{
			if(i>=s.length())
				gene.add(false);
			else
			{
				if(s.charAt(n)=='1')
					gene.add(true);
				else
					gene.add(false);
				n++;
			}
		}
	}
	
	public int convertToInt() {
		String temp= new String();
		for(int i=0; i < gene.size(); i++)
		{
			if(gene.get(i))
				temp+='1';
			else
				temp+='0';
		}
		return Integer.parseInt(temp, 2);
	}
	
	public boolean isBomba()
	{
		int x=convertToInt();
		if(x==1)
			return true;
		else
			return false;
	}

	public void print() {
		System.out.print(convertToInt());
	}

	@Override
	public boolean equals(Object obj) {
		int n= convertToInt();
		if((Integer)obj==n)
			return true;
		else
			return false;
	}
	
	
}
