package Chord_Algorithm;

import java.awt.BorderLayout;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.AncestorListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.AbstractButton;
public class Project_Interface extends JFrame{
	private JPanel panel1 = new Client_Side();
	private JPanel panel2 = new Server_side();
	JTextArea input;
	JTextArea output;
	JLabel client;
	JLabel server;
	public Project_Interface(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initMenu();
		panel1.setBackground(Color.WHITE);
		panel2.setBackground(Color.WHITE);
		setLayout(new BorderLayout());
	}

	private class MenuAction implements ActionListener {

		private JPanel panel;
		MenuAction(JPanel pnl) {
			this.panel = pnl;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			changePanel(panel);
		}
	}

	private void initMenu(){
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		JMenuItem menuItem1 = new JMenuItem("Panel1");
		JMenuItem menuItem2 = new JMenuItem("Panel2");
		menubar.add(menu);
		menu.add(menuItem1);
		menu.add(menuItem2);
		setJMenuBar(menubar);
		menuItem1.addActionListener(new MenuAction(panel1));
		menuItem2.addActionListener(new MenuAction(panel2));
	}

	private  class Client_Side extends JPanel implements ActionListener {

		public  JButton b1;
		//private  final long serialVersionUID = 1L;
		public  JRadioButton rb_add_node;
		public  JTextField t1;
		public  JLabel Show_IP;
		public  JLabel Show_Msg;

		/* Create ID ArrayList */
		public  String get_Ip_address()
		{
			try {
				java.net.InetAddress  i = java.net.InetAddress .getLocalHost();
				String s = i.getHostAddress();
				return s;
			}
			catch(Exception  e){e.printStackTrace();}
			return null;
		}

		public Client_Side() {
			t1 = new JTextField(20);
			t1.setLocation(50, 50);
			t1.setHorizontalAlignment(JTextField.RIGHT);
			t1.setActionCommand("tt1");

			b1 = new JButton("Search Data");
			b1.setVerticalTextPosition(AbstractButton.CENTER);
			b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
			b1.setMnemonic(KeyEvent.VK_D);
			b1.setActionCommand("bb");

			Show_IP = new JLabel(get_Ip_address(), JLabel.CENTER);
			Show_IP.setVerticalTextPosition(JLabel.TOP);
			Show_IP.setHorizontalTextPosition(JLabel.CENTER);

			Show_Msg = new JLabel("", JLabel.LEFT);
			Show_Msg.setVerticalTextPosition(JLabel.BOTTOM);
			Show_Msg.setHorizontalTextPosition(JLabel.CENTER);

			//Listen for actions on buttons 1 and 3.
			b1.addActionListener(this);
			b1.setToolTipText("Go");

			t1.addActionListener(this);
			t1.setToolTipText("Go"); 
			Show_IP.setBackground(Color.red);

			//Add Components to this container, using the default FlowLayout.

			add(Show_IP);
			add(b1);
			add(t1);
			add(Show_Msg);
			//add(rb_add_node);

			ButtonGroup group = new ButtonGroup();
			group.add(rb_add_node);
			createAndShowGUI(); 

		} 




		private  void createAndShowGUI() {

			//Main Function-------------------------------------------------

			/*javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI(); 
			}
		});

			 */

			/*	hf h = hf.getHashFunction();
		//Create and set up the window.
		JFrame frame = new JFrame("ButtonDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Create and set up the content pane.
		Client_Side newContentPane = new Client_Side();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);
		//Display the window.
		frame.pack();
		frame.setVisible(true);
		frame.setSize(500, 500);
			 */

			b1.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e)
				{
					//Execute when button is pressed	
					Socket kkSocket = null;
					PrintWriter out = null;
					BufferedReader in = null;
					int f=1, nkk=1236;
					String ipp = "localhost";

					//	System.out.println("Client side1................");

					try {
						kkSocket = new Socket("172.16.16.94", 1248);
						out = new PrintWriter(kkSocket.getOutputStream(), true);  // Out may be used to write to server from the client 
						in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));  // in will be used to read the lines sent by the Server.
					} catch (UnknownHostException e1) {
						System.err.println("Unidentified host.");
						System.exit(1);
					} catch (IOException e1) {
						System.err.println("Couldn't get I/O for the connection to.");
						System.exit(1);
					}
					BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
					String fromServer = null;
					String fromClient;
					String s = t1.getText();
					out.println(s);
					try {
						fromServer = in.readLine();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}


					//System.out.println("Listening");
					//	System.out.println("Server1: " + fromServer);
					System.out.println("Data in node: " + fromServer);
					Show_Msg.setText("Data in node: "+fromServer);
					//	System.out.println("Control out");

					out.close();
					try {
						in.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						kkSocket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}


					//Client Side2---------------------------------------------------------------------

					//System.out.println("Client side2................");

					try {
						kkSocket = new Socket(fromServer, 1248);
						out = new PrintWriter(kkSocket.getOutputStream(), true);  // Out may be used to write to server from the client 
						in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));  // in will be used to read the lines sent by the Server.
					} catch (UnknownHostException e1) {
						System.err.println("Unidentified host.");
						System.exit(1);
					} catch (IOException e1) {
						System.err.println("Couldn't get I/O for the connection to.");
						System.exit(1);
					}
					//	System.out.println("Data Check");
					out.println("Data_Check");
					out.println(s);
					String fromServer2 = null;
					//	System.out.println("Listening2");

					try {
						fromServer2 = in.readLine();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					//	System.out.println("Content is in :"+fromServer2+".txt");
					System.out.println("Data is in file: "+fromServer2+".txt");
					Show_Msg.setText("Data is in file: "+fromServer2+".txt");
					System.out.println("Data is copied in D://testout.txt");
					try {
						in.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						kkSocket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					//Download----------------------------------------------------

					byte[] aByte = new byte[1];
					int bytesRead;
					Socket clientSocket = null;
					InputStream is = null;
					final String fileOutput = "D:\\testout.txt";

					try {
						clientSocket = new Socket( fromServer , 1248 );
						is = clientSocket.getInputStream();
					} catch (IOException ex) {
						// Do exception handling
					}

					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					if (is != null) {

						FileOutputStream fos = null;
						BufferedOutputStream bos = null;
						try {
							fos = new FileOutputStream( fileOutput );
							bos = new BufferedOutputStream(fos);
							bytesRead = is.read(aByte, 0, aByte.length);

							do {
								baos.write(aByte);
								bytesRead = is.read(aByte);
							} while (bytesRead != -1);

							bos.write(baos.toByteArray());
							bos.flush();
							bos.close();
							clientSocket.close();
						} catch (IOException ex) {
							// Do exception handling
						}
					}
					//------------------------------------------------------------
					/*	
					try {
						fromServer2 = in.readLine();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					System.out.println(fromServer2);
					Show_Msg.setText(fromServer2);

					 */	
					//	System.out.println("Control out2");
					/*		out.close();
					try {
						in.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 */		
					//-----------------------------------------------------------------Client Side2---------------

					//--------------------------------------------------------------------------------
				}
			});      }

		//----------------------------------------------------------------------

		public void actionPerformed(ActionEvent arg0) {

		}
	}






	private class Server_side extends JPanel implements ActionListener {
		String finger_table_ip[] = new String[200];
		long finger_table_id[] = new long[200];
		public  ArrayList<Integer> nodes_hashid = new ArrayList<Integer>();
		public  ArrayList<String> nodes_ipaddress = new ArrayList<String>();
		public int i=0;
		public JButton b1;
		public JButton b_add_node;
		public JButton b_display_node;
		public JButton b_server;
		private  final long serialVersionUID = 1L;
		public  JRadioButton rb_add_node;
		public  JRadioButton rb_display_node;
		public  JRadioButton rb_server;
		public  JTextField t1;
		public  JTextArea t2;
		public  JLabel Show_IP;
		public  JLabel Show_Msg;

		/* Create ID ArrayList */
		//implement3 i=implement3.igetHashFunction();
		public  String get_Ip_address()
		{
			try {
				java.net.InetAddress  i = java.net.InetAddress .getLocalHost();
				String s = i.getHostAddress();
				return s;
			}
			catch(Exception  e){e.printStackTrace();}
			return null;
		}



		public Server_side() {

			t2 = new JTextArea(20, 20);
			t2.setLocation(50, 50);
			t2.setSize(200, 100);
			t2.setEditable(false);

			t1 = new JTextField(20);
			t1.setLocation(50, 50);
			t1.setHorizontalAlignment(JTextField.RIGHT);
			t1.setActionCommand("tt1");

			rb_add_node = new JRadioButton("Add Node");
			rb_add_node.setVerticalTextPosition(AbstractButton.CENTER);
			rb_add_node.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
			rb_add_node.setMnemonic(KeyEvent.VK_D);
			rb_add_node.setActionCommand("cc");
			rb_add_node.addActionListener(this);
			rb_add_node.setToolTipText("Go");

			rb_display_node = new JRadioButton("Display Node");
			rb_display_node.setVerticalTextPosition(AbstractButton.CENTER);
			rb_display_node.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
			rb_display_node.setMnemonic(KeyEvent.VK_D);
			rb_display_node.setActionCommand("cc");
			rb_display_node.addActionListener(this);
			rb_display_node.setToolTipText("Go");

			rb_server=new JRadioButton("Server");
			rb_server.setVerticalTextPosition(AbstractButton.CENTER);
			rb_server.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
			rb_server.setMnemonic(KeyEvent.VK_D);
			rb_server.setActionCommand("cc");
			rb_server.addActionListener(this);
			rb_server.setToolTipText("Go");

			b1 = new JButton("Click Me!");
			b1.setVerticalTextPosition(AbstractButton.CENTER);
			b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
			b1.setMnemonic(KeyEvent.VK_D);
			b1.setActionCommand("bb");

			b_add_node = new JButton("Add Node");
			b_add_node.setVerticalTextPosition(AbstractButton.CENTER);
			b_add_node.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
			b_add_node.setMnemonic(KeyEvent.VK_D);
			b_add_node.setActionCommand("bb");

			b_display_node = new JButton("Display Node");
			b_display_node.setVerticalTextPosition(AbstractButton.CENTER);
			b_display_node.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
			b_display_node.setMnemonic(KeyEvent.VK_D);
			b_display_node.setActionCommand("bb");

			b_server = new JButton("Server");
			b_server.setVerticalTextPosition(AbstractButton.CENTER);
			b_server.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
			b_server.setMnemonic(KeyEvent.VK_D);
			b_server.setActionCommand("bb");

			Show_IP = new JLabel(get_Ip_address(), JLabel.CENTER);
			Show_IP.setVerticalTextPosition(JLabel.TOP);
			Show_IP.setHorizontalTextPosition(JLabel.CENTER);

			Show_Msg = new JLabel("", JLabel.LEFT);
			Show_Msg.setVerticalTextPosition(JLabel.BOTTOM);
			Show_Msg.setHorizontalTextPosition(JLabel.CENTER);

			//Listen for actions on buttons 1 and 3.
			b1.addActionListener(this);
			b1.setToolTipText("Go");

			b_add_node.addActionListener(this);
			b_add_node.setToolTipText("Go");

			b_display_node.addActionListener(this);
			b_display_node.setToolTipText("Go");

			b_server.addActionListener(this);
			b_server.setToolTipText("Go");

			t1.addActionListener(this);
			t1.setToolTipText("Go"); 
			t2.setToolTipText("Go"); 

			//Add Components to this container, using the default FlowLayout.

			//add(t2);
			add(Show_IP);
			//add(b1);
			add(b_add_node);
			add(b_display_node);
			add(b_server);
			add(t1);
			add(Show_Msg);
			//add(rb_add_node);
			//add(rb_display_node);
			//add(rb_server);

			ButtonGroup group = new ButtonGroup();
			group.add(rb_add_node);
			group.add(rb_display_node);
			group.add(rb_server);

			createAndShowGUI(); 
		} 


		//Main Function-------------------------------------------------

		private  void createAndShowGUI() {
			hf h = hf.getHashFunction();
			//Create and set up the window.
			/*JFrame frame = new JFrame("ButtonDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Create and set up the content pane.
		Server_side newContentPane = new Server_side();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);
		//Display the window.
		frame.pack();
		frame.setVisible(true);
		frame.setSize(500, 500);
			 */

			b_add_node.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e)
				{
					String input = t1.getText();
					if(input.length()==0)
						Show_Msg.setText("Data Name not entered");
					else
					{
						nodes_ipaddress.add(input);
						int x = hf.byteArrayToInt( hf.kgetHashKey(input));
						x = Math.abs(x);
						nodes_hashid.add(x);
						Show_Msg.setText(input+" Data added");	
						i++;
						Show_Msg.setText("Added"+i);
					}
				}
			});

			b_display_node.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e)
				{

					System.out.println("Nodes Before Sorting");
					for (int c = 0 ; c < nodes_hashid.size(); c++)
					{
						System.out.println(nodes_ipaddress.get(c)+" = "+nodes_hashid.get(c));
					}

					int temp;
					String stemp;
					for (int c = 0 ; c < nodes_hashid.size()-1; c++)
					{
						for (int d = 0 ; d < nodes_hashid.size() - c - 1; d++)
						{
							if (nodes_hashid.get(d) > nodes_hashid.get(d+1))
							{
								/* Swapping */
								temp = nodes_hashid.get(d);
								nodes_hashid.set(d, nodes_hashid.get(d+1));
								nodes_hashid.set(d+1, temp);

								stemp = nodes_ipaddress.get(d);
								nodes_ipaddress.set(d, nodes_ipaddress.get(d+1));
								nodes_ipaddress.set(d+1,stemp);
							}
						}
					}

					System.out.println("Nodes After Sorting");
					for (int c = 0 ; c < nodes_hashid.size(); c++)
					{
						System.out.println(nodes_ipaddress.get(c)+" = "+nodes_hashid.get(c));
					}


					//Finger Table---------------------------------------------------

					String ipv4address = get_Ip_address();
					int ipv4_hashid;
					ipv4_hashid = hf.byteArrayToInt( hf.kgetHashKey(ipv4address));
					ipv4_hashid = Math.abs(ipv4_hashid);
					System.out.println("My private ip adress = "+ipv4address);
					System.out.println("My private ip adress's hashed id = "+ipv4_hashid);

					long target;
					System.out.println("Finger Table Entry:");
					for(int b=0;b<60;b++)
					{
						target = (ipv4_hashid + (long) Math.pow(2, b)) % (nodes_hashid.get(nodes_hashid.size()-1));
						//System.out.println(ipv4_hashid+" + 2^"+b+" = "+target);
						for(int i=0;i<nodes_hashid.size();i++)
						{
							if(nodes_hashid.get(i)>=target)
							{	finger_table_ip[b] = nodes_ipaddress.get(i);
							finger_table_id[b] = nodes_hashid.get(i);
							break;
							}
						}
						System.out.println(ipv4_hashid+" + 2^"+b+" = "+target+" finger = "+finger_table_ip[b]);	
					}
					//	System.out.println("Stop");
					//------------------------------------------------------------------------------
				}
			});

			b_server.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e)
				{
					//Server Socket Programming-----------------------------------------------------
					//Data Array---------------------------------------------------
					ServerSocket serverSocket = null;
					String ipv4address = get_Ip_address();
					int ipv4_hashid;
					ipv4_hashid = hf.byteArrayToInt( hf.kgetHashKey(ipv4address));
					ipv4_hashid = Math.abs(ipv4_hashid);
					//	System.out.println("My ip & has ID = "+ipv4address+" "+ipv4_hashid);
					int data_array[] = new int[10];
					String data_name[] = {"A","B","C","D","E","F","G","H","I","J"};
					int j=0;
					System.out.println("Data Array:");
					for(int i=ipv4_hashid-9;i<=ipv4_hashid;i++)
					{
						data_array[j] = i;
						System.out.println(i);
						System.out.println(data_name[j]);
						j++;
					}

					//------------------------------------------------------Data Array-------

					int nitu = 0;
					//		while(nitu<2)
					//		{
					try {
						System.out.println("Server side Listening.........");
						serverSocket = new ServerSocket(1248);    // Listen on the Port 

						//connectionSocket = serverSocket.accept();

					} catch (IOException e1) {
						System.err.println("not able to listen on port:");
						System.exit(1);
					}
					Socket clientSocket = null;
					try {
						clientSocket = serverSocket.accept(); 
						//	outToClient = new BufferedOutputStream(clientSocket.getOutputStream());

					} catch (IOException e1) {
						System.err.println("Accept failed.");
						System.exit(1);
					}

					PrintWriter out = null;
					try {
						out = new PrintWriter(clientSocket.getOutputStream(), true);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  // Out is  Outputstream  is used to write to the Client .
					BufferedReader in = null;
					try {
						in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // in is used to read the Client's input. 
					String inputLine, outputLine;


					String outputid = null;
					String get = null;
					try {
						get = in.readLine();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if(get.equals("Data_Check"))
					{
						try {
							get = in.readLine();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						int get_data_hashID = Integer.parseInt(get);
						j=0;
						int found_data = 0, found_at = 0;
						for(int i=ipv4_hashid-9;i<=ipv4_hashid;i++)
						{
							if(get_data_hashID == data_array[j])
							{
								found_data = 1;
								found_at = j;
							}
							j++;
						}
						if(found_data == 1)
						{
							out.println(data_name[found_at]);
							out.close();
							//Download data--------------------------------------------

							try {
								in.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								clientSocket.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							try {
								serverSocket.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}




							final String fileToSend = "D:\\"+data_name[found_at]+".txt";
							ServerSocket welcomeSocket = null;
							Socket connectionSocket = null;
							BufferedOutputStream outToClient = null;

							try {
								welcomeSocket = new ServerSocket(1248);
								connectionSocket = welcomeSocket.accept();
								outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());
							} catch (IOException ex) {
								// Do exception handling
							}

							if (outToClient != null) {
								File myFile = new File( fileToSend );
								byte[] mybytearray = new byte[(int) myFile.length()];

								FileInputStream fis = null;

								try {
									fis = new FileInputStream(myFile);
								} catch (FileNotFoundException ex) {
									// Do exception handling
								}
								BufferedInputStream bis = new BufferedInputStream(fis);

								try {
									bis.read(mybytearray, 0, mybytearray.length);
									outToClient.write(mybytearray, 0, mybytearray.length);
									outToClient.flush();
									outToClient.close();
									connectionSocket.close();

									// File sent, exit the main method
									return;
								} catch (IOException ex) {
									// Do exception handling
								}
							}


							//---------------------------------------------------------


						}
						else
							out.println("Data Out Of Range");
					}
					else
					{
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

						for(int i=0;i<nodes_hashid.size();i++)
						{
							if(nodes_hashid.get(i)>=searchid)
							{
								outputid = nodes_ipaddress.get(i);
								break;
							}

							//System.out.println(nodes_ipaddress[i]+" = "+nodes_hashid[i]);
						}
						out.println(outputid);
						out.close();
						try {
							in.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							clientSocket.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							serverSocket.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}






					}
					nitu++;

					/*	try {
						in.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						clientSocket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						serverSocket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					 */
				}
				//out.println(found);

				//---------------------------------------------------------------					}
				//		}
			});



			b1.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e)
				{
					//Execute when button is pressed	
					if(rb_add_node.isSelected())
					{
						String input = t1.getText();
						if(input.length()==0)
							Show_Msg.setText("Data Name not entered");
						else
						{
							nodes_ipaddress.add(input);
							int x = hf.byteArrayToInt( hf.kgetHashKey(input));
							x = Math.abs(x);
							nodes_hashid.add(x);
							Show_Msg.setText(input+" Data added");	
							i++;
							Show_Msg.setText("Added"+i);
						}
					}
					else if(rb_display_node.isSelected())
					{

						System.out.println("Before Sorting");
						for (int c = 0 ; c < nodes_hashid.size(); c++)
						{
							System.out.println(nodes_ipaddress.get(c)+" = "+nodes_hashid.get(c));
						}

						int temp;
						String stemp;
						for (int c = 0 ; c < nodes_hashid.size()-1; c++)
						{
							for (int d = 0 ; d < nodes_hashid.size() - c - 1; d++)
							{
								if (nodes_hashid.get(d) > nodes_hashid.get(d+1))
								{
									/* Swapping */
									temp = nodes_hashid.get(d);
									nodes_hashid.set(d, nodes_hashid.get(d+1));
									nodes_hashid.set(d+1, temp);

									stemp = nodes_ipaddress.get(d);
									nodes_ipaddress.set(d, nodes_ipaddress.get(d+1));
									nodes_ipaddress.set(d+1,stemp);
								}
							}
						}

						System.out.println("After Sorting");
						for (int c = 0 ; c < nodes_hashid.size(); c++)
						{
							System.out.println(nodes_ipaddress.get(c)+" = "+nodes_hashid.get(c));
						}


						//Finger Table---------------------------------------------------

						String ipv4address = get_Ip_address();
						int ipv4_hashid;
						ipv4_hashid = hf.byteArrayToInt( hf.kgetHashKey(ipv4address));
						ipv4_hashid = Math.abs(ipv4_hashid);
						System.out.println("My private ip adress = "+ipv4address);
						System.out.println("Private ip adress's hashed id = "+ipv4_hashid);

						long target;
						System.out.println("Finger Table Entry");
						for(int b=0;b<60;b++)
						{
							target = (ipv4_hashid + (long) Math.pow(2, b)) % (nodes_hashid.get(nodes_hashid.size()-1));
							//System.out.println(ipv4_hashid+" + 2^"+b+" = "+target);
							for(int i=0;i<nodes_hashid.size();i++)
							{
								if(nodes_hashid.get(i)>=target)
								{	finger_table_ip[b] = nodes_ipaddress.get(i);
								finger_table_id[b] = nodes_hashid.get(i);
								break;
								}
							}
							System.out.println(ipv4_hashid+" + 2^"+b+" = "+target+" finger = "+finger_table_ip[b]);	
						}
						System.out.println("Stop");
						//------------------------------------------------------------------------------
					}


					else if(rb_server.isSelected())
					{

						//Server Socket Programming-----------------------------------------------------
						ServerSocket serverSocket = null;

						try {
							System.out.println("Server side Listening....kk.....");
							serverSocket = new ServerSocket(1247);    // Listen on the Port 
						} catch (IOException e1) {
							System.err.println("not able to listen on port: 4321.");
							System.exit(1);
						}
						Socket clientSocket = null;
						try {
							clientSocket = serverSocket.accept();  
						} catch (IOException e1) {
							System.err.println("Accept failed.");
							System.exit(1);
						}

						PrintWriter out = null;
						try {
							out = new PrintWriter(clientSocket.getOutputStream(), true);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  // Out is  Outputstream  is used to write to the Client .
						BufferedReader in = null;
						try {
							in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} // in is used to read the Client's input. 
						String inputLine, outputLine;


						String outputid = null;

						String get = null;
						try {
							get = in.readLine();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						while(!get.equals("close"))
						{
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
							for(int i=0;i<nodes_hashid.size();i++)
							{
								if(nodes_hashid.get(i)>=searchid)
								{
									outputid = nodes_ipaddress.get(i);
									break;
								}

								//System.out.println(nodes_ipaddress[i]+" = "+nodes_hashid[i]);
							}

							out.println(outputid);
							//out.println(found);

						}
						if(get.equals("close"))
						{
							out.println("close");
						}
						//---------------------------------------------------------------
					}





				}
			});      }


		//----------------------------------------------------------------------

		public void actionPerformed(ActionEvent arg0) {

		}
	}

	private void changePanel(JPanel panel) {
		getContentPane().removeAll();
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().doLayout();
		//setContentPane().
		update(getGraphics());
		// upgrade(panel);
	}
	/*
private void upgrade(JPanel panel)
{
	Client_Side.createAndShowGUI();
}*/
	public static void main(String[] args) {
		Project_Interface frame = new Project_Interface();
		frame.setBounds(200, 200, 300, 200);
		frame.setVisible(true);

	}
}
