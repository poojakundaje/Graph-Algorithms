/**
 * A simple definition of a Vertex to be used with
 * the Graph class.
 *
 */

public class Vertex { 
	
	/**
	 * label for Vertex
	 */
	private String name;  
	
	public String color;
	public String predecessor;

	public Vertex(String v) {
		name = v;	
		color="white";
		predecessor=null;
	}
	
	public String toString() { 
		return name;
	}
	
	public String getString() {
		return name;
	}
	
	public void setGray() {
		color="gray";
	}
	
	public void setWhite() {
		color="white";
	}
	
	public void setBlack() {
		color="black";
	}
	
	public String getColor() {
		return color;
	}
	
	public String getPred() {
		return predecessor;
	}
	
	public void setPred(String s) {
		predecessor=s;
	}
	

}