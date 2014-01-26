package Chord_Algorithm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;

public class hf {
	private static hf hashFunction = null;
	static hf getHashFunction() {

		if (hashFunction == null) {
			try {
				hashFunction = new hf(MessageDigest
						.getInstance("SHA-1"));
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("No hash function available!", e);
			}
		}
		return hashFunction;
	}

	private static MessageDigest messageDigest;
	hf(MessageDigest digest1) {
		if (digest1 == null) {
			throw new NullPointerException("Parameter may not be null!");
		}
		this.messageDigest = digest1;
	}
	final int getLengthOfIDsInBytes() {
		return this.messageDigest.getDigestLength();
	}


	//get hashed id in string format---------------------------------------------------------------------------
	final ID getHashKey(String entry) {

		if (entry == null) {
			throw new IllegalArgumentException(
			"Parameter entry must not be null!");
		}
		if (entry.getBytes() == null || entry.getBytes().length == 0) {
			throw new IllegalArgumentException(
			"Byte representation of Parameter must not be null or have length 0!");
		}

		byte[] testBytes = entry.getBytes();
		return this.createID(testBytes);
	}

	private final ID createID(byte[] testBytes) {
		synchronized (this.messageDigest) {
			this.messageDigest.reset();
			this.messageDigest.update(testBytes);
			return new ID(this.messageDigest.digest());
		}
	}
	//---------------------------------------------------------------------------


	//get hashed id in integer format----------------------------------------------------------
	final static byte[] kgetHashKey(String entry) {

		if (entry == null) {
			throw new IllegalArgumentException(
			"Parameter entry must not be null!");
		}
		if (entry.getBytes() == null || entry.getBytes().length == 0) {
			throw new IllegalArgumentException(
			"Byte representation of Parameter must not be null or have length 0!");
		}

		byte[] testBytes = entry.getBytes();
		//return this.createID(testBytes);

		return kcreateID(testBytes);
	}

	private static byte[] kcreateID(byte[] testBytes) {
		synchronized (messageDigest) {
			messageDigest.reset();
			messageDigest.update(testBytes);
			return bb(messageDigest.digest());
		}
	}

	private static byte[] id;

	public static byte[] bb(byte[] id1) {
		id = new byte[id1.length];
		System.arraycopy(id1, 0, id, 0, id1.length);
		return id;	
	}

	public static int byteArrayToInt(byte[] b) 
	{
		int value = 0;
		for (int i = 0; i < 4; i++) {
			value = (value << 8) | (b[i] & 0xFF);
		}
		return value;
	}

	public static String getipv4address()
	{
		try {
			java.net.InetAddress  i = java.net.InetAddress .getLocalHost();
			String ipv4address = i.getHostAddress();  // IP address only
			return(ipv4address); 

		}
		catch(Exception  e){e.printStackTrace();}
		return null;
	}

	//----------------------------------------------------------

	public static void main (String[] args) throws MalformedURLException,IOException {

		hf h=getHashFunction();
		int nodes_hashid[] = new int [100];
		String nodes_ipaddress[] = new String [100];		
		System.out.println ("Enter number of nodes");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

		String number = br.readLine();
		int n = Integer.parseInt(number);
		for(int i=0;i<n;i++)
		{
			String input = br.readLine();
			nodes_ipaddress[i] = input;
			//nodes [i] = h.kgetHashKey(input).toString();
			nodes_hashid[i] = byteArrayToInt( h.kgetHashKey(input));
			nodes_hashid[i] = Math.abs(nodes_hashid[i]);
			//you can use h.getHashKey(input) to get string hash code
		}
		System.out.println("Before Sorting");
		for(int i=0;i<n;i++)
		{
			System.out.println(nodes_ipaddress[i] +" = "+nodes_hashid[i]);
		}


		//Sorting----------------------------------------------------------------------
		int temp;
		String stemp;
		for (int c = 0 ; c < ( n - 1 ); c++)
		{
			for (int d = 0 ; d < n - c - 1; d++)
			{
				if (nodes_hashid[d] > nodes_hashid[d+1])
				{
					/* Swapping */

					temp = nodes_hashid[d];
					nodes_hashid[d] = nodes_hashid[d+1];
					nodes_hashid[d+1] = temp;

					stemp = nodes_ipaddress[d];
					nodes_ipaddress[d] = nodes_ipaddress[d+1];
					nodes_ipaddress[d+1] = stemp;
				}
			}
		}
		//-----------------------------------------------------------------------------------

		//Arrays.sort(nodes);
		System.out.println("After Sorting");
		for(int i=0;i<n;i++)
		{
			System.out.println(nodes_ipaddress[i]+" = "+nodes_hashid[i]);
		}

		//Finger Table Entry--------------------------------------------
		String finger_table_ip[] = new String[200];
		long finger_table_id[] = new long[200];
		String ipv4address = hf.getipv4address();
		int ipv4_hashid;
		ipv4_hashid = byteArrayToInt( h.kgetHashKey(ipv4address));
		ipv4_hashid = Math.abs(ipv4_hashid);
		System.out.println("My private ip adress = "+ipv4address);
		System.out.println("Private ip adress's hashed id = "+ipv4_hashid);

		long target;
		System.out.println("Finger Table Entry");
		for(int b=0;b<60;b++)
		{
			target = (ipv4_hashid + (long) Math.pow(2, b)) % (nodes_hashid[n-1]);
			//System.out.println(ipv4_hashid+" + 2^"+b+" = "+target);
			for(int i=0;i<n;i++)
			{
				if(nodes_hashid[i]>=target)
				{	finger_table_ip[b] = nodes_ipaddress[i];
					finger_table_id[b] = nodes_hashid[i];
					break;
				}
			}
			System.out.println(ipv4_hashid+" + 2^"+b+" = "+target+" finger = "+finger_table_ip[b]);	
		}

		//Server Side Code-----------------------------------------------
		
		
		ServerSocket serverSocket = null;

		try {
			System.out.println("Server side Listening.........");
			serverSocket = new ServerSocket(1239);    // Listen on the Port 
		} catch (IOException e) {
			System.err.println("not able to listen on port: 4321.");
			System.exit(1);
		}
		Socket clientSocket = null;
		try {
			clientSocket = serverSocket.accept();  
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}

		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);  // Out is  Outputstream  is used to write to the Client .
		BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream())); // in is used to read the Client's input. 
		String inputLine, outputLine;


		String outputid = null;
		String get = in.readLine();
		int found=0;
		int searchid = Integer.parseInt(get);
		for(int b=0;b<60;b++)
		{
			if(finger_table_id[0] > searchid)
			{
				outputid = finger_table_ip[b];
				found=1;
				break;
			}
			if(finger_table_id[b] == searchid)
			{
				outputid = finger_table_ip[b];
				found=1;
				break;
			}
			if(finger_table_id[b] < searchid)
			{
				outputid = finger_table_ip[b];
			}
			if(finger_table_id[b] > searchid)
			{
				break;
			}
		}
		
		for(int i=0;i<n;i++)
		{
			if(nodes_hashid[i]>=searchid)
			{
				outputid = nodes_ipaddress[i];
				break;
			}
				
				//System.out.println(nodes_ipaddress[i]+" = "+nodes_hashid[i]);
		}
		
		out.println(outputid);
		//out.println(found);
		
		//---------------------------------------------------------------

		
		
		
		
		
		
	

		//Old Stuff---------------------------------------------------------------


		/*HashFunction h=getHashFunction();
	System.out.println ("Enter the Key");
	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
String key=br.readLine();
Key k=new Key(key);
System.out.println ("The hashed key obtained is :"+h.getHashKey(k));
System.out.println ("Enter the URL");
String url= br.readLine();
URL u=new URL(url);
System.out.println ("The hashed ID obtained is: "+h.createUniqueNodeID(u));
		 */	}
}