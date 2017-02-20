/**
 * 
 */
package hello;

import java.beans.Transient;

/**
 * @author lizie
 *
 */
public class Greeting {
	public String name;
	public String salutation;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Transient
	public String getMessage(){
		return salutation+" "+name;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	

}
