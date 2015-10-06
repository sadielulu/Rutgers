
/**
 * 
 * a trusted user class
 *
 */
public class User {

	int validN, id;
	String vote, name, lastName;
	
	public User(String n,String ln,int validNum){
		name=n;
		lastName=ln;
		validN=validNum;
	}
	
	public int getID(){
		return id;
		
	}
	
	//set ID later , before voting
	public void setID(int newId){
		id=newId;
	}
	
	//set vote later , when voting
	public String getVote(){

		return vote;
	}
	
	public void setVote(String newVote){
		vote=newVote;
	}

	public int getValidNum(){
		return validN;
		
	}
	
	public void setValidNum(int newValidNum){
		validN=newValidNum;
	} 
}
