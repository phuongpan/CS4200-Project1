
public class Node {
	private int matrx[] = new int[9];
	private int pos, hVal, gVal;
	private Node parent;
	public Node (int []matrx, int pos,int newPos, int gVal, Node parent)
	{
		copy(matrx);
		swap(pos,newPos);
		this.pos = newPos;
	
		this.gVal = gVal;
		this.parent= parent;
	}
	public void copy(int []m)
	{
		for(int i = 0; i < m.length; i++)
		    this.matrx[i] = m[i];
	}
	public void swap(int pos, int newPos)
	{
		int temp = matrx[pos];
		matrx[pos] = matrx[newPos];
		matrx[newPos] = temp;	
	}
	public void sethVal(int hVal)
	{
		this.hVal = hVal;
	}
	
	// Getters
	public int gethVal()
	{
		return this.hVal;
	}
	public int getPos()
	{
		return this.pos;
	}
	public int getFn()
	{
		return this.getgVal()+ this.gethVal();
	}
	public int getgVal()
	{
		return this.gVal;
	}
	public int[] getMatrix()
	{
		return this.matrx;
	}
	public Node getParent()
	{
		return this.parent;
	}
}
