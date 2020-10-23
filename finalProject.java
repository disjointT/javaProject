package FinalProject;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;




public class finalProject {
	//function for signing up
	public static void signup(registration_sys s) throws IOException {
	     BufferedReader stdin2 = new BufferedReader(new InputStreamReader(System.in));

	     System.out.println("First name: ");
	   	 String first=stdin2.readLine();
	   	 System.out.println("Last name: ");
	   	 String second=stdin2.readLine();
	   	 Student New=new Student();
	   	 New.setname(first,second);
	   	 s.current_student_pool.put(New.name(),New);
	   	 System.out.println("Sign-up Sucessful!");

	}


	//open the server
	public static void openServer(registration_sys v) throws IOException {

	     BufferedReader stdin2 = new BufferedReader(new InputStreamReader(System.in));
		 registration_sys s=v;

	     String shutdown="";

	     while (!shutdown.contentEquals("\\Q")) {
	    	 	 System.out.println("–––––––––––––––––––––––––––––––––––––");
		    	 System.out.println("Zoom University Server"
		    	 		+ " Home Page \n –––––––––––––––––\n log in | sign Up \n type L | type "
		    	 		+ "S");
	    	 	 System.out.println("–––––––––––––––––––––––––––––––––––––");
		    	 String option=stdin2.readLine();

		    	 if (option.contentEquals("S")) {
			    	 signup(s);
			    	 System.out.println("Type L to log in");
		    	 }

		    	 while (!option.contentEquals("L")) {
		    		 option=stdin2.readLine();
		    	 }

		    	 System.out.println("User ID (first name):");

		    	 String rd1 = stdin2.readLine();
		    	 int count=0;

		    	 while (!s.current_student_pool.containsKey(rd1)) {
		    		 	System.out.println("try again! ");
			    		rd1=stdin2.readLine();
			    		count++;
			    		//If user tried more than 5 times suggest user sign up with a new account instead
			    		if (count>5) {
				    		System.out.println("Wanted to sign up instead? Y/N");
				    		String answer=stdin2.readLine();

				    		while (!answer.contentEquals("Y")&&!answer.contentEquals("N")) {
				    			answer=stdin2.readLine();
				    		}
				    		if (answer.contentEquals("Y")) {
				    			signup(s);
				    			System.out.print("Now use your first name to ");
				    			count=0;
				    		}
				    		else count=0;
			    		}
		    	 }

			 (s.current_student_pool.get(rd1)).Welcome(0,s);
			 System.out.println("Turn off server: \\Q ; type anything else to go back to homepage");
			 shutdown=stdin2.readLine();
	 	 	 System.out.println("••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••");
	     }
	}

	//main function
	public static void main (String[]args) throws IOException {
        registration_sys s=new registration_sys();
        //initialize course
        s.current_course_pool=registration_sys.course_pool();
        s.current_student_pool=registration_sys.student_pool();
        openServer(s);
   }
}

//the interface through which the registration system and the student are able to communciate with each other.
interface communicate {
	Stack<String> messages=null;
	public default void sendMessage(communicate c,String q) {
		c.collectMessage(this,q);
	}

	public default String collectMessage(communicate c,String q) {
		return null;
	}

	public default void readMessage()throws IOException {
	}
	String name();
}


class course implements communicate{
	static int max_registration;
	static int registration=0;
	String professor="";
	String department="";

	String course_name;
	static ArrayList<Student> roster;
	static ArrayList<Student> waitlist;



	public course(String s,int max,String prof,String dpt) {
		max_registration=max;
		course_name=s;
		registration=0;
		roster=new ArrayList<Student>();
		waitlist=new ArrayList<Student>();
		professor=prof;
		department=dpt;
	}

	public String name(){
		return professor;
	}

	public static void sortNames(String[] v) {
		for (int i = 0; i < v.length; i++)
        {
            for (int j = i + 1; j < v.length; j++) {
                if (v[i].compareTo(v[j])>0)
                {
                    String temp = v[i];
                    v[i] = v[j];
                    v[j] = temp;
                }
            }
        }

	}

	public void register(Student e) {
    		roster.add(e);
    		registration++;
    		System.out.println(registration);
	}

	public void drop(Student e) {
		roster.remove(e);
		if (!waitlist.isEmpty()) {
			Student v=waitlist.remove(0);
			roster.add(v);
			String q="You have been put off the waitlist of "+course_name+"!";
			sendMessage(v,q);
		}
	}

	public void add_waitlist(Student e) {
		waitlist.add(e);
		String q="You have been added to the waitlist of "+course_name+"!";
		sendMessage(e,q);
	}

	public boolean available() {
		return registration<max_registration;
	}

}

class registration_sys {
	public HashMap <String,course> current_course_pool;
	public HashMap <String,Student> current_student_pool;

	//function to initialize a new course_pool with 0 registrations/0 waitlists
	public static HashMap <String,course> course_pool(){
		HashMap <String,course> res= new HashMap<String,course>();
		//Initialize a bunch of classes
		course a=new course("calc101",10,"J.Mackey","MTH");
		res.put(a.course_name,a);
		course b=new course("bio100",9,"M.Boyd","BIO");
		res.put(b.course_name,b);
		course c=new course("calc102",15,"D.Cash","MTH");
		res.put(c.course_name,c);
		course d=new course("concepts128",100,"M.Johnson","MTH");
		res.put(d.course_name,d);
		course e=new course("spanish400",2,"C.Maddox","ML");
		res.put(e.course_name,e);
		course f=new course("physics403",1,"B.Jackson","PHY");
		res.put(f.course_name,f);
		course g=new course("statistics350",2,"P.Freeman","STA");
		res.put(g.course_name,g);
		course h=new course("statistics315",2,"Z.Branson","STA");
		res.put(h.course_name,h);
		course i=new course("spanish402",2,"T.Tardio","ML");
		res.put(i.course_name,i);
		course j=new course("french200",2,"K.Devon","ML");
		res.put(j.course_name,j);
		course k=new course("diffeq100",2,"S.Bergs","MTH");
		res.put(k.course_name,k);

		return res;
	}


	//function to initilize a student pool with three users: John Doe, Andrew Carnegie and Lynn Aniston.
	public static HashMap <String,Student> student_pool(){
		HashMap <String,Student> res= new HashMap<String,Student>();
		Student one=new Student();
		one.setname("John","Doe");
		Student two=new Student();
		two.setname("Andrew","Carnegie");
		Student three=new Student();
		three.setname("Lynn","Aniston");
		res.put(one.name(), one);
		res.put(two.name(), two);
		res.put(three.name(), three);
		return res;
	}
}

class Student implements communicate{
	private String first_name;
	private String last_name;
	private ArrayList<String> classes=new ArrayList<String>();
	private ArrayList<String> waitlisted=new ArrayList<String>();
	private String pw="";
	private Stack<String> messages=new Stack<String>();
	public HashMap<String,course> course_pool;

	Student self=this;

	//boolean messageStack;

	public String name() {
		return first_name;
	}


	public void setname(String first_name,String last_name) {
		this.first_name = first_name;
		this.last_name=last_name;
	}

	public void setPassword() throws IOException {
        BufferedReader stdin0 = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Set your password:");
        pw=stdin0.readLine();
        System.out.println("Password saved!");
	}

	public void changePassword() throws IOException {
		  BufferedReader stdin0 = new BufferedReader(new InputStreamReader(System.in));
	      System.out.println("Previous Password:");
	      String password=stdin0.readLine();
	      while (!password.equals(pw)) {
	    	  System.out.println("Try again");
	    	  password=stdin0.readLine();
	      }

	      System.out.println("Updated Password:");
		  pw=stdin0.readLine();

	      System.out.println("Password saved!");
	}

	//this is not secure. Figuring out a method later.
	public void getPassword() throws IOException {
        System.out.println(pw);
	}

	private void Register(int j,registration_sys s) throws NumberFormatException, IOException {


        BufferedReader stdin2 = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Number of classes to register:");
        int n=0;
        while(n==0) {
        try {

        	n = Integer.parseInt(stdin2.readLine());
        }
        catch(Exception e) {
        	System.out.println("Error!"+e);
        }
        }
        while (n>5-classes.size()) {
        	System.out.println("Can't register for more than "+(int)(5-classes.size())+" classes at the time! Try again: ");
            n = Integer.parseInt(stdin2.readLine());
        }


        for (int i=0;i<n;i++) {
        	registration_action(i,s);
        }
        CheckStatus(j,s);
	}

	// one single registration action for the i-th class. Wrote this as an individual method because we need it when a
	//student decides to be on the wait-list for one of their classes.
	public void registration_action(int i,registration_sys s) throws IOException {

		BufferedReader stdin2 = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Class "+(int)(i+1)+" :");
	    String l=stdin2.readLine();
	    while(!registration_sys.course_pool().containsKey(l)||classes.contains(l))	{
	    	while (classes.contains(l)) {
	    		System.out.println("You are already in this class :)");
	       	 	l=stdin2.readLine();
	    	}

	    	while(!registration_sys.course_pool().containsKey(l)) {
	    		System.out.println("Course doesn't exist! see listed courses Y/N");
	    		String answer=stdin2.readLine().toUpperCase();
	    		while (!answer.contentEquals("Y")&&!answer.contentEquals("N")) {
	                answer=stdin2.readLine();
	           }
	    		if (answer.contentEquals("Y")){
		    		System.out.println("Courses offered:");
		        	Set<Entry<String, course>> entries2 = registration_sys.course_pool().entrySet();
		    		for (Map.Entry<String, course> entry : entries2) {
		    			System.out.println("----------------");
		    		   System.out.println(entry.getKey());

		    		}
	    		}
				System.out.println("____________________");

	    		System.out.println("Class "+(int)(i+1)+": ");
	    		l=stdin2.readLine();

	    	}

	    }

	    	if (course_pool.get(l).available()) {
				classes.add(l);
				//course_pool.get(l).register(self);
				s.current_course_pool.get(l).register(self);
				course_pool=s.current_course_pool;
				//s.current_course_pool=course_pool;
	    	}
	    	else {
	    		Waitlist(l,i,s);
	    	}

	    	return ;
	}

	public void Waitlist(String s,int i,registration_sys S) throws IOException {
        BufferedReader stdin2 = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Class is full! Do you want to be on the waitlist? Y/N");
   	 	String answer=stdin2.readLine().toUpperCase();
       	while (!answer.contentEquals("Y")&&!answer.contentEquals("N")) {
             answer=stdin2.readLine();
        }
        if (answer.contentEquals("Y")) {
        	course_pool.get(s).add_waitlist(self);
        	waitlisted.add(s);
        }
        else {
        		registration_action(i,S);
        }
        return ;
	}

	public void Drop() throws IOException {
    	System.out.println("Hello "+name()+"! Please indicate the course you wished to drop:");
	    BufferedReader stdin2 = new BufferedReader(new InputStreamReader(System.in));
		String Course=stdin2.readLine();
		 if (classes.contains(Course)) {
			 System.out.println("Are you sure you want to drop this class? Y/N");
		     String answer=stdin2.readLine();
	           	while (!answer.contentEquals("Y")&&!answer.contentEquals("N")) {
	                 answer=stdin2.readLine();
	            }
	            if (answer.contentEquals("Y")) {
	            	classes.remove(Course);
	            	course_pool.get(Course).drop(self);
	            }
		 }
		 else {
			 System.out.println("You were not registered for this class.");
		 }
	}

	private static void sort( ArrayList<String> v) {
		for (int i = 0; i < v.size(); i++)
        {
            for (int j = i + 1; j < v.size(); j++) {
                if (v.get(i).compareTo(v.get(j))>0)
                {
                    String temp = v.get(i);
                    v.set(i,v.get(j));
                    v.set(j,temp);
                }
            }
        }
	}


	private void accessClasses(int i,registration_sys s) throws IOException {
        BufferedReader stdin3 = new BufferedReader(new InputStreamReader(System.in));
        if (pw!="") {
        	System.out.println("Please type in your password in order to access:");
            String password=stdin3.readLine();
            int counter=0;
					loop1:
	        while(!password.contentEquals(pw)) {
	        	System.out.println("Wrong password. Try again");
			    counter++;
			    if (counter>10) {
			    	System.out.println("Too Many attempts! Try Later");
			    	break loop1;
			    }
			    password=stdin3.readLine();
	        }

				if(password.contentEquals(pw)){
					sort(classes);
	        sort(waitlisted);
	        System.out.println("Your current registered classes are:"+classes);
	        System.out.println("Your current waitlisted classes are:"+waitlisted);
	        System.out.println("-----------------------------------");
				}
			}

	}

	private void CheckStatus(int i,registration_sys s) throws IOException{
        BufferedReader stdin1 = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("You're all set! Do you want to see your current registration status? Y/N");
        String answer=stdin1.readLine();
        while (!answer.contentEquals("Y")&&!answer.contentEquals("N")) {
            answer=stdin1.readLine();

        }
	    if (answer.contentEquals("Y")) {
	        accessClasses(i,s);
	    }
	    else return;
	}

	private void CourseInfo(course c) {
		   System.out.print(c.course_name);
		   System.out.print("| Professor: "+c.professor);
		   System.out.print("| Department: "+c.department);
		   if (c.available()) {
			   System.out.println("| status: available ");
		   }
		   else {
			   System.out.println("| status: "
			   		+ "full ");
		   }
	}

	private void registerWelcomeProcess(int j,registration_sys s) throws IOException {
		System.out.println("Do you want to proceed ? Y/N");
        BufferedReader stdin1 = new BufferedReader(new InputStreamReader(System.in));
        String answer=stdin1.readLine().toUpperCase();
        while (!answer.contentEquals("Y")&&!answer.contentEquals("N")) {
             answer=stdin1.readLine().toUpperCase();
        }

        if (answer.contentEquals("Y")) {

        	if (pw=="") {
        		//check whether has password
            	System.out.println("You don't have a password yet. Set up password? Y/N");
                answer=stdin1.readLine();
                while (!answer.contentEquals("Y")&&!answer.contentEquals("N")) {
                    answer=stdin1.readLine().toUpperCase();
               }
                if (answer.contentEquals("Y")) {
                	setPassword();
                }
        	}
        	Register(j,s);

	        System.out.println("Do you want to keep registering? Y/N");
	        answer=stdin1.readLine().toUpperCase();
	        while (!answer.contentEquals("Y")&&!answer.contentEquals("N")) {
	            answer=stdin1.readLine().toUpperCase();
	        }
		    while (answer.contentEquals("Y")) {
		        Register(j,s);
		        System.out.println("Do you want to keep registering? Y/N");
	            answer=stdin1.readLine().toUpperCase();
	            while (!answer.contentEquals("Y")&&!answer.contentEquals("N")) {
	                answer=stdin1.readLine().toUpperCase();
	            }
		    }
        }

        else {
        		Welcome(j+1,s);
        }
	}

	public String collectMessage(communicate c,String q) {
		String msg=q+"\n from"+": "+c.name();
		this.messages.push(msg);
		return msg;
	}

	public void readMessage() throws IOException {
        BufferedReader stdin1 = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("You have "+messages.size()+" "
				+ "new messages");
		while (messages.size()>0) {
			System.out.println("Read the latest message? Y/N");
			String read=stdin1.readLine().toUpperCase();
			while (!read.contentEquals("Y")&&!read.contentEquals("N")) {
	             read=stdin1.readLine().toUpperCase();
	         }
	     	if (read.contentEquals("Y")) {

	     	    System.out.println(messages.pop());
	     	}
	     	else return;
		}
     	return;
	}

	public void sendMessage(communicate s, String q) {
		s.collectMessage(self, q);
	}

	public void Welcome(int i,registration_sys s) throws IOException {
        BufferedReader stdin1 = new BufferedReader(new InputStreamReader(System.in));

		if (pw!="") {
	        	System.out.println("Please type in your password in order to access:");
	            String password=stdin1.readLine();
	            int counter=0;
		        while(!password.contentEquals(pw)) {
		        	System.out.println("Wrong password. Try again");
				    counter++;
				    if (counter>10) {
				    	System.out.println("Too Many attempts! Try Later");
				    	Welcome(i+1,s);
				    }
				    password=stdin1.readLine();
		        }
	    }


		//first time log-in greetings
        if (i==0) {
        		System.out.println("Hello "+name()+"! Welcome to Zoom University! How can I help you? (type 0 to quit) ");
        }

        //safety control: excess amount of visit
        if (i>100) {
	        	System.out.print("You've visited the service too frequently. Come back later!");
	        	return ;
        }

        System.out.println("–––––––––––––––––––––––––––––––––––––");
		if (i>0){ System.out.println(name()+"	(type 0 to quit)");}
        	System.out.println("\n  "
        			+ "Register for classes : type 1 \n  Drop classes: type 2 \n "
        			+ " Course Info: type 3 \n  "
        			+ "Forgot my password: type 4\n"
        			+ "  Access Message Box :type 5"
        			+ "\n"
        			+ "  Send message : type 6");
   	 	System.out.println("–––––––––––––––––––––––––––––––––––––");
    	course_pool=s.current_course_pool;

        int answer=-1;
        while(answer==-1) {
            try {
            	answer = Integer.parseInt(stdin1.readLine());
            	if (answer!=1&&answer!=2&&answer!=3&&answer!=4&&answer!=5&&answer!=6&&answer!=0) {
            		System.out.println("No such option! Try again");
            		answer=-1;
            	}

            }
            catch(Exception e) {
            	System.out.println("Error!"+e);
            }
        }


        //register a class in the system as the Student.
        if (answer==1) {
        		registerWelcomeProcess(i,s);
        }


        //Drop a course
        if (answer==2) {
        		Drop();
        		s.current_course_pool=course_pool;
        }

        //provide course information
        if(answer==3) {
        	System.out.println("Courses offered:");
        	Set<Entry<String, course>> entries2 = s.current_course_pool.entrySet();
    		for (Map.Entry<String, course> entry : entries2) {
    		   System.out.println(entry.getKey());
	    	 	 System.out.println("––––––––––––––––––––––––");
    		}
    		System.out.println("Course name to search: (type \\q to quit)");
    		String coursename=stdin1.readLine();

    		//keep asking for input until user quits
    		loop1:
    		while (!coursename.contentEquals("\\q")) {

	    		while(!s.current_course_pool.containsKey(coursename)) {
	    			if (!coursename.contentEquals("\\q")) {
	    				System.out.println("Course doesn't exist");
		    			coursename=stdin1.readLine();
	    			}
	    			else {
		    			break loop1;

	    			}
	    		}
	    		CourseInfo(s.current_course_pool.get(coursename));
	    		System.out.println("Course name to search: (type \\q to quit)");
    			coursename=stdin1.readLine();
    		}
        }

        // Retrieve user password through their last name
        if (answer==4) {
        	System.out.println("please confirm your last name:");
        	BufferedReader stdin2 = new BufferedReader(new InputStreamReader(System.in));
            String name=stdin2.readLine();
						int count=0;
						loop1:
            while (!name.contentEquals(last_name)) {
            		System.out.println("Wrong last name");
            		name=stdin2.readLine();
								count++;

							if (count==10){
								System.out.println("Too many attempts! Try later. ");
								break loop1;
							}
					}
					if (name.contentEquals(last_name)){
							System.out.print("Password "
	            			+ ":");
	            getPassword();
	            System.out.println("Reset Password? Y/N");
	            String reset=stdin2.readLine();
	            while (!reset.contentEquals("Y")&&!reset.contentEquals("N")) {
	                reset=stdin2.readLine();
	            }
			    		if (reset.contentEquals("Y")) {
			        	changePassword();
		    			}
					}


        }

        //Open message box => go to the function readMessage()
        if (answer==5) {
        		readMessage();
        }

        //Write a message to a Student (they could write a message to themselves too, in this scenario, which will be a memo)
        if (answer==6) {
        		System.out.println("Send message to :");
            	BufferedReader stdin4 = new BufferedReader(new InputStreamReader(System.in));
            	String q=stdin4.readLine();
							loop1:
            	while(!registration_sys.student_pool().containsKey(q)) {
            		System.out.println("Student doesn't exist! See students in the system: Y/N");
            		String see=stdin4.readLine();
            		while (!see.contentEquals("Y")&&!see.contentEquals("N")) {
                        see=stdin4.readLine();
                    }
        		    if (see.contentEquals("Y")) {
        		        System.out.println(s.current_student_pool.keySet());
										q=stdin4.readLine();
        		    }
								if (see.contentEquals("N")){
									break loop1;
								}
            	}
							if (registration_sys.student_pool().containsKey(q)){
	            	System.out.println("Draft your message: ");
	            	String msg=stdin4.readLine();
	            	sendMessage(s.current_student_pool.get(q),msg);
	            	System.out.println("Sent an message to "+q);
						}
        }

        if(answer==0) {
        		return ;
        }


        System.out.println("go back to main user page? Y/N");
        String cont=stdin1.readLine();
        while (!cont.contentEquals("Y")&&!cont.contentEquals("N")) {
            cont=stdin1.readLine();
        }
	    if (cont.contentEquals("N")) {
	    	System.out.println("Log out? Y/N");
	        String quit=stdin1.readLine();
	        while (!quit.contentEquals("Y")&&!quit.contentEquals("N")) {
	            quit=stdin1.readLine();
	        }
	        if (quit.contentEquals("Y")) {
		        System.out.println("Thank you!");
		        System.out.println("==============");
		        return ;
	        }
	        else {
	        		Welcome(i+1,s);
	        }
	    }
	    else {
	    	 	Welcome(i+1,s);
	    }
	}
}
