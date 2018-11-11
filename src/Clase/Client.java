package Clase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Client {
	
	private float sold;
	private String username;
	private String password;
	
	public float getSold() {
		return sold;
	}

	public void setSold(float sold) {
		this.sold = sold;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
	public Client()
	{
		
	}

	public Client(float sold, String username, String password) {
		super();
		this.sold = sold;
		this.username = username;
		this.password = password;	
		
	}
	public void Transfer_Factura( String filepath, boolean ok1, int d)
	{
		Scanner input; String cod; float suma_plata;
		input = new Scanner(System.in);	
		if (ok1 == true)
		{
		System.out.print("Introduceti codul facturii: ");
		cod = input.nextLine();
		System.out.print("Introduceti suma pe care doriti sa o achitati: ");
		suma_plata = Float.parseFloat(input.nextLine());	
		}else
		{
		System.out.print("Introduceti codul bancar al clientului dv.: ");
		cod = input.nextLine();
		System.out.print("Introduceti suma pe care doriti sa o achitati: ");
		suma_plata =Float.parseFloat(input.nextLine());
		}
		String InputLine = "";
		if (getSold() > suma_plata) {
		String tempFile = "TXTFile/temp.txt";
		File oldFile = new File(filepath);
		File newFile = new File(tempFile);		
		boolean ok = false;
		try{
			FileWriter fw = new FileWriter(tempFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			input = new Scanner(new BufferedReader(new FileReader(filepath)));
			while(input.hasNextLine())
			{
				InputLine = input.nextLine();
	            String[] InArray = InputLine.split(",");
				if (getUsername().equals(InArray[0]) && cod.equals(InArray[1]))
				{
					if(Float.parseFloat(InArray[2]) > suma_plata) {					
					pw.println(InArray[0] + "," + InArray[1] + "," + (Float.parseFloat(InArray[2]) - suma_plata));
					Update_Sold(suma_plata, getUsername(), "TXTFile/Client.txt");
					Actualizare_Tranzactii("PlataFactura", suma_plata);
				    ok = true;
				}else
				{
					System.out.println("Introduceti o suma mai mica");
					pw.println(InArray[0] + "," + InArray[1] + "," + InArray[2]);
					
				}
					
				}	else
				{	
					
					pw.println(InArray[0] + "," + InArray[1] + "," + InArray[2]);
				}
				
			}
			input.close();
			pw.flush();
			pw.close();
			oldFile.delete();
			File dump = new File(filepath);
			newFile.renameTo(dump);	
			if (ok == false) {
				Retry(1 ,d);
			}else
			{
				Retry(1,2);
			}
			
			
		}catch (Exception e)
		{
			System.out.println("Error");
			Retry(1 , d);
		}
		}else
		{
			System.out.println("Nu ai suficienti bani in cont");
			Retry(1, d);
		}
		
    }	
	
	public void Update_Sold(float suma_achitata, String user, String filepath)
	{
		Scanner x;
		String tempFile = "TXTFile/temp2.txt";
		File oldFile = new File(filepath);
		File newFile = new File(tempFile);
		String username = ""; String password = ""; float suma = 0;
		try
		{
			FileWriter fw = new FileWriter(tempFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			x = new Scanner(new File(filepath));
			x.useDelimiter("[,\n]");
			while(x.hasNext())
			{
				username = x.next();
				password = x.next();
				suma = Float.parseFloat(x.next());
				if(username.equals(user))
				{
					pw.println(username + "," + password + "," + (suma - suma_achitata));
					
				}else
				{
					pw.println(username + "," + password + "," + suma);
				}
			}
			x.close();
			pw.flush();
			pw.close();
			oldFile.delete();
			File dump = new File(filepath);
			newFile.renameTo(dump);	
		}			
			catch (Exception e)
		{
			System.out.println("Error");
		}
	}
	
	public void Actualizare_Tranzactii( String tranzactie, float suma)
	{
		File file = new File("TXTFile/Tranzactii.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(file , true);
		
		PrintWriter pw = new PrintWriter(fw); 
		if ( tranzactie.equals("InterogareSold"))
		{
			pw.println();
			pw.println(getUsername() + "," + tranzactie + ","+"Sold-ul curent a fost de: "+ suma+" RON");
		}else if ( tranzactie.equals("TransferBancar"))
		{
			pw.println();
			pw.println(getUsername() + "," + tranzactie + "," + "Ai transferat clientului tau urmatoarea suma: "+ suma+" RON");
		}else if (tranzactie.equals("PlataFactura"))
		{
			pw.println();
			pw.println(getUsername() + "," + tranzactie + "," + "Ai platit furnizorului tau urmatoarea suma: "+suma+" RON");
		}else if (tranzactie.equals("SchimbareParola"))
		{
			pw.println();
			pw.println(getUsername() + "," + tranzactie + "," + "Noua ta parola este: " + getPassword());
		}
		
		pw.close();
		}
		catch (Exception e) {
			System.out.println("Error");			
		}			
	}	
	
	
	public void Retry ( int z, int ok)
	{
		int x; Scanner y = new Scanner(System.in);
		
		if (ok == 0)
		{
			System.out.println("Apasa tasta 1 pentru a incerca sa introduci din nou datele ");
			x= Integer.parseInt(y.nextLine());
			if ( x == z) {			
				Transfer_Factura( "TXTFile/Factura.txt", true, 0);
			}else
			{
				System.out.println("Sa aveti o zi frumoasa in continuare!");
			}
		
		
		}else if (ok == 1)
		{
			System.out.println("Apasa tasta 1 pentru a incerca sa introduci din nou datele ");
			x= Integer.parseInt(y.nextLine());
			if ( x == z) {			
				Transfer_Factura( "TXTFile/TransferBancar.txt", false , 1);
				}else
				{
					System.out.println("Sa aveti o zi frumoasa in continuare!");
				}			
		}else if (ok == 2)
		{
			Login login = new Login();
			System.out.println("Apasa tasta 1 daca vrei sa te intorci la meniul cu tranzactii! ");
			x= Integer.parseInt(y.nextLine());
			if ( x == z) {
			login.verifyLogin(getUsername(), getPassword(), "TXTFile/Client.txt");
			}else
			{
				System.out.println("Sa aveti o zi frumoasa in continuare!");
			}
		}
	}
	
	public void Vizualizare_Tranzactii(String filepath)
	{
		Scanner scanIn = null;	    
	    String InputLine = "";		   
	    try
	    {	    	
	        scanIn = new Scanner ( new BufferedReader(new FileReader(filepath)));           
	        while(scanIn.hasNextLine())
	        {


	            InputLine = scanIn.nextLine();
	            String[] InArray = InputLine.split(",");
	            if (InArray[0].equals(getUsername()))
	            {
	            	System.out.println(InArray[1] + "," + InArray[2]);
	            }

	        }
	        Retry(1,2);
	    }catch (Exception e)
	    {
	        System.out.println("Error");
	    }
	}
	
	public void Change_Password(String filepath)
	{
		Scanner scanIn = new Scanner(System.in);	
		System.out.println("Introdu noua ta parola: ");
		String  newPassword = scanIn.nextLine();
	    String InputLine = "";	
	    String tempFile = "TXTFile/temp5.txt";
		File oldFile = new File(filepath);
		File newFile = new File(tempFile);
	    try
	    {
	    	FileWriter fw = new FileWriter(tempFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
	        scanIn = new Scanner ( new BufferedReader(new FileReader(filepath)));           
	        while(scanIn.hasNextLine())
	        {
	            InputLine = scanIn.nextLine();
	            String[] InArray = InputLine.split(",");
	            if (InArray[0].equals(getUsername()))
	            {
	            	InArray[1] = newPassword;
	            	pw.println(InArray[0] + ","+ InArray[1] + "," + InArray[2]);
	            	setPassword(InArray[1]);
	            	Actualizare_Tranzactii("SchimbareParola", 0);
	            	System.out.println("Parola a fost schimbata cu succes");	            	
	            	
	            }else
	            {
	            	pw.println(InArray[0] + ","+ InArray[1] + "," + InArray[2]);
	            }

	        }
	        scanIn.close();
			pw.flush();
			pw.close();
			oldFile.delete();
			File dump = new File(filepath);
			newFile.renameTo(dump);	
			Retry(1,2);
	    }catch (Exception e)
	    {
	        System.out.println("Error");
	    }
	}
}