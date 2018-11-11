package Clase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class Login {	
	
	private  static Scanner input; 
	private  static String user, password;
	private  static String filepath = "TXTFile/Client.txt"; 	
	private  static Client client;
    static ArrayList<String> array = new ArrayList<>(); 
    static List<String> list = new LinkedList<String>();
	public Login() {
		
	}
	
	public void Decizie()
	{
		int ok; Scanner x = new Scanner(System.in);
		System.out.println("Apasati tasta 1 pentru a va loga in cont");
		System.out.println("Apasati tasta 2 pentru a vizualiza contactele de urgenta ale angajatilor nostri");
		ok = Integer.parseInt(x.nextLine());
		if ( ok == 1)
		{
			Logare();
		}else if ( ok == 2)
		{				  
		    String InputLine = "";		   
		    try
		    {	    	
		        x = new Scanner ( new BufferedReader(new FileReader("TXTFile/Contact.txt")));           
		        while(x.hasNextLine())
		        {


		            InputLine = x.nextLine();
		            String[] Contact = InputLine.split(",");
		            list.add(Contact[0]+ ","+ Contact[1]);	          

		        }
		       for ( String i: list)
		       {
		    	   System.out.println(i);
		       }
		    }catch (Exception e)
		    {
		        System.out.println("Error");
		    }
		    int dec;
		    System.out.print("Apasa 1 Daca doresti sa te intorci la meniu: ");
		    x = new Scanner(System.in);
		    dec = Integer.parseInt(x.nextLine());
		    if ( dec == 1) {
		    Decizie();
		    }else {
		    	System.out.println("Sa aveti o zi frumoasa!");
		    }
		}
	}
	
	public static void Logare() {	
	input = new Scanner(System.in);
	System.out.print("Enter your username: ");
	user = input.nextLine();
	System.out.print("Enter your password: ");
	password = input.nextLine();
	verifyLogin(user, password, filepath);
	}
	
	
	public static void verifyLogin(String username, String password, String filepath) {
		
		
		boolean found = false;
		String tempUsername = "";
		String tempPassword = "";
		float  tempSold = 0;
		Scanner a = new Scanner(System.in);
		String optiune_client;			
		try {
			input = new Scanner (new File(filepath));
			input.useDelimiter("[,\n]");			
			while(input.hasNext() && !found) {
				tempUsername = input.next();
				tempPassword = input.next();
				tempSold = Float.parseFloat(input.next());
				if(tempUsername.trim().equals(username.trim()) && tempPassword.trim().equals(password.trim())) {
					found = true;
					client = new Client(tempSold, user, password);
					
				}
			}
			input.close();				
			if (found == true) {
				 System.out.println("Selecteaza o tranzactie");
				 System.out.println("Apasa 1 pentru InterogareSold");
				 System.out.println("Apasa 2 pentru Plata Facturi");
				 System.out.println("Apasa 3 pentru Transfer Bancar");
				 System.out.println("Apasa 4 pentru a vizualiza tranzactiile efectuate");
				 System.out.println("Apasa 5 pentru a schimba parola contului");
				 System.out.println("Apasa 6 pentru a vizualiza ce tranzactii ai efectuat acum");
				 optiune_client = a.nextLine();
				 if (Integer.parseInt(optiune_client) == 1)
				 {		
					 array.add("InterogareSold");
					 System.out.println("Sold-ul contului curent este: " + client.getSold() + "RON");					 
					 client.Actualizare_Tranzactii("InterogareSold", client.getSold());
					 client.Retry(1, 2);					 
				 }else if(Integer.parseInt(optiune_client) == 2)
				 {
					array.add("PlataFactura");
					client.Transfer_Factura( "TXTFile/Factura.txt", true , 0);					
					
				 }else if (Integer.parseInt(optiune_client) == 3)
				 {
					 array.add("TransferBancar");
					 client.Transfer_Factura( "TXTFile/TransferBancar.txt", false , 1);					
			 }else if (Integer.parseInt(optiune_client) == 4)
				 {
					 client.Vizualizare_Tranzactii("TXTFile/Tranzactii.txt");
				 }else if(Integer.parseInt(optiune_client) == 5)
				 {
					 array.add("SchimbareParola");
					 client.Change_Password(filepath);					 
				 }else if (Integer.parseInt(optiune_client) == 6)
				 {
					 System.out.println("Tranzactii efectuate acum:");
					 for ( int i=0; i < array.size(); i++)
					 {
						 System.out.println(array.get(i));
					 }
					 client.Retry(1, 2);
				 }else
				 {
					 System.out.println("Sa aveti o zi frumoasa!");
				 }
			}else {
				 System.out.println("Username/Password wrong");	
				 System.out.print("Apasa 2 daca doresti sa introduci din nou username-ul si parola: ");
				 optiune_client = a.nextLine();
				 if (Integer.parseInt(optiune_client) == 2)
				 {
					 Logare();
				 }	
			}					
			
		}catch (Exception e) {
			System.out.println("Error");
			
		}
	}

	 
}
