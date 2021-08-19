/*
 
 NAME:           SHARVARI SONKUSARE
 CNUM:           C22019221458
 ASSIGNMENT:     7
 ROLL NO.:       2459
 
 PROBLEM STATEMENT: Use a sequential file to maintain student information. Develop java code to add, and display
student information from the file.( you can add search and delete operations).
 
 
 */



package File_handling;
import java.io.Serializable;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.*;


class Student implements Serializable{
    int rollNo;  
    String name; 
    double mark;
    
    
    void accept(){    //accepting data for one Student
    	Scanner sc = new Scanner(System.in);
        System.out.println("Enter Roll No.:");
        rollNo = sc.nextInt();
        sc.nextLine();    //to take string input after int input
        System.out.println("Enter Name:");
        name = sc.nextLine();
        System.out.println("Enter marks:");
        mark = sc.nextDouble();
    }
    public String toString(){  //converting data of objects to string format
         return "Roll No.:"+rollNo+"\nName:"+name+"\nMarks:"+mark+"\n";    
    }
    
}
class File1{
    Vector<Student> st;//to store student objects 
    File1(){ //main file
        st = new Vector<Student>();
    }
    Scanner sc = new Scanner(System.in);
    
    
    void writeInFile(File newFile){
        int choice = 1;
		while(choice!=0){
		    System.out.println("Enter student details :-");
		    Student s1 = new Student();
		    s1.accept();
		    st.add(s1); //adding new student as obj to the vector st
		    System.out.println("Do you want to continue?(1/0):");
		    choice = sc.nextInt();
		}
		try{
		    FileOutputStream fos = new FileOutputStream(newFile);
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
		    oos.writeObject(st);//writing the vector to the file
		    oos.close();
		}catch(Exception excep){
		    System.out.println(excep);//printing exception from try-catch block
		}
    }
    
    
    void readFromFile(File newFile) {
    	try {
    		FileInputStream fis = new FileInputStream(newFile);
    		ObjectInputStream ois = new ObjectInputStream(fis);
    		st = (Vector<Student>)ois.readObject();//reading from the file
    		Iterator<Student> iter = st.iterator();//using iterator to traverse all file data 
    		while(iter.hasNext()) {
    			Student s1 = iter.next();
    			System.out.println(s1.toString());//printing all student objects
    		}
    		ois.close();
    	}catch(Exception excep) {
    		System.out.println(excep);
    	}
    	
    }
    
    
    void search(File newFile) { //searching details of a particular student
    	System.out.println("Enter Roll no. of student to be searched:");
    	int key = sc.nextInt();//roll number is used as key to search
    	try {
    		FileInputStream fis = new FileInputStream(newFile);
    		ObjectInputStream ois = new ObjectInputStream(fis);
    		st = (Vector<Student>)ois.readObject(); //reads the vector from the file
    		int flag = 0;
    		Iterator<Student> iter = st.iterator();//using iterator to search 
    		Student s1 = new Student();
    		while(iter.hasNext()) {
    			s1 = iter.next();
    			if(s1.rollNo==key) {
    				flag =1;      //record found
    				break;
    			}
    			
    		}
    		if(flag==1) {
    			System.out.println(s1.toString());
    		}
    		else {
    			System.out.println("NOT FOUND!");
    		}
    		
    		ois.close();
    		
    	}catch(Exception excep) {
    		System.out.println(excep);
    	}
    }
    
    
    
    File delete(File newFile) {
    	
    	File temp = new File("temporary.txt");
    	System.out.println("Enter Roll no. of student to be deleted:");
    	int key = sc.nextInt();
    	try {
    		st.clear();
    		FileInputStream fis = new FileInputStream(newFile);
    		ObjectInputStream ois = new ObjectInputStream(fis);
    		
    		FileOutputStream fos = new FileOutputStream(temp);
    		ObjectOutputStream oos = new ObjectOutputStream(fos);
    		
    		Vector<Student> v = (Vector<Student>)ois.readObject(); //reading vector from the original file
    		Iterator<Student> iter = v.iterator();
    		while(iter.hasNext()) {
    			Student s1 = iter.next();
    			if(key==s1.rollNo) {  //if record found
    				continue;  //skips this record and does not add to st vector
    			}
    			st.add(s1); //adds all remaining records
    		}
    		oos.writeObject(st);  //writing the vector to the temp file
    		temp.renameTo(newFile);
    		ois.close();       
    		oos.close();
    		
    		
    	}catch(Exception excep) {
    		System.out.println(excep);
    	}
    	return temp;
    }
    

}
public class file
{
	public static void main(String[] args) {
		int choice;
		Scanner sc =new Scanner (System.in);
		File1 f=new File1();
		File newFile = new File("Student Records.txt");
		do {
			System.out.println("\nMENU\n1.WRITE FILE\n2.READ FILE\n3.SEARCH RECORD\n4.DELETE RECORD\n0.EXIT\nEnter your choice");
			choice=sc.nextInt();

			switch(choice) {
			case 1:
				f.writeInFile(newFile);
			break;
			
			case 2:
				f.readFromFile(newFile);
			break;
			
			case 3:
				f.search(newFile);
			break;
			
			case 4:
				newFile = f.delete(newFile);
				f.readFromFile(newFile);
			break;
			
			case 0:
				System.out.println("EXIT");
				break;
			default:System.out.println("INVALID CHOICE");
			break;
			}
		}while(choice!=0);
	}	
}


/*
 TIME COMPLEXITY:
 
 1) Write - O(n)
 2) Read - O(n)
 3) Search - O(n)
 4) Delete - O(n)
 
 OUTPUT:
 
 
MENU
1.WRITE FILE
2.READ FILE
3.SEARCH RECORD
4.DELETE RECORD
0.EXIT
Enter your choice
1
Enter student details :-
Enter Roll No.:
1
Enter Name:
LUA
Enter marks:
66.7
Do you want to continue?(1/0):
1
Enter student details :-
Enter Roll No.:
2
Enter Name:
KIA
Enter marks:
55.8
Do you want to continue?(1/0):
1
Enter student details :-
Enter Roll No.:
3
Enter Name:
TOR
Enter marks:
88.7
Do you want to continue?(1/0):
0

MENU
1.WRITE FILE
2.READ FILE
3.SEARCH RECORD
4.DELETE RECORD
0.EXIT
Enter your choice
2
Roll No.:1
Name:LUA
Marks:66.7

Roll No.:2
Name:KIA
Marks:55.8

Roll No.:3
Name:TOR
Marks:88.7


MENU
1.WRITE FILE
2.READ FILE
3.SEARCH RECORD
4.DELETE RECORD
0.EXIT
Enter your choice
3
Enter Roll no. of student to be searched:
2
Roll No.:2
Name:KIA
Marks:55.8


MENU
1.WRITE FILE
2.READ FILE
3.SEARCH RECORD
4.DELETE RECORD
0.EXIT
Enter your choice
4
Enter Roll no. of student to be deleted:
1
Roll No.:2
Name:KIA
Marks:55.8

Roll No.:3
Name:TOR
Marks:88.7


MENU
1.WRITE FILE
2.READ FILE
3.SEARCH RECORD
4.DELETE RECORD
0.EXIT
Enter your choice
2
Roll No.:2
Name:KIA
Marks:55.8

Roll No.:3
Name:TOR
Marks:88.7


MENU
1.WRITE FILE
2.READ FILE
3.SEARCH RECORD
4.DELETE RECORD
0.EXIT
Enter your choice
0
EXIT
 
*/
