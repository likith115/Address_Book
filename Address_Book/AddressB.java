import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
class PersonInfo implements Comparable<PersonInfo>{
    String name;
    String address;
    String phoneNumber;
    String email;
    PersonInfo(String name, String address, String phoneNumber,String email) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email=email;
    }
    public int compareTo(PersonInfo p){
        return this.name.compareToIgnoreCase(p.name);
    }
}
class AddressBook {
    ArrayList persons;
    AddressBook() {
        persons = new ArrayList();
        loadPersons();
    }
    void show(String s)
    {
        JOptionPane.showMessageDialog(null,s);
    }
    boolean isNumeric(String s)
    {
        long a;
        if(s=="nil"){
            return true;
        }
        else{
            if(s.length()!=10){
                show("Enter 10 digit number");
                return false;
            }
            try
            {
                a=Long.parseLong(s);
                return true;
            }
            catch(NumberFormatException e){}
            show("Enter Correct Number");
            return false;
        }
    }
    boolean isEmail(String s){
        if(s=="nil"){
            return true;
        }
        else{
            if(s.contains("@") && s.contains(".com")){
                return true;
            }
            else{
                show("Enter Correct Email");
                return false;
            }
        }
    }
    void addperson(Boolean s, Boolean t, Boolean u, Boolean v) {
        if(!s && !t && !u && !v){
            JOptionPane.showMessageDialog(null, "Select Atleat One Option");
            new frame4();
        }
        else{
            String name="nil",add="nil",Num="nil",mail="nil";
            int a=0,b=0,c=0,d=0;
            JPanel panel=new JPanel(new GridLayout(5,3));
            JTextField tname=new JTextField(10);
            JTextField tpnum=new JTextField(10);
            JLabel lpnum=new JLabel("Enter Phone Number");
            JLabel lname=new JLabel("Enter Name");
            JTextField  tadd=new JTextField(10);
            JLabel ladd=new JLabel("Enter Address");
            JTextField tmail=new JTextField(10);
            JLabel lmail=new JLabel("Enter Email");
            if(s)
            {
                panel.add(lname);
                panel.add(tname);
                a=1;
            }
            if(t)
            {
                panel.add(lpnum);
                panel.add(tpnum);
                b=1;
            }
            if(u)
            {
                panel.add(ladd);
                panel.add(tadd);
                c=1;
            }
            if(v)
            {
                panel.add(lmail);
                panel.add(tmail);
                d=1;
            }
            JOptionPane.showMessageDialog(null,panel);
            if(a==1)
            {
                name=tname.getText();
            }
            if(b==1)
            {
                Num=tpnum.getText();
            }
            if(c==1)
            {
                add=tadd.getText();
            }
            if(d==1)
            {
                mail=tmail.getText();
            }
            if(name.trim().length()!=0 && add.trim().length()!=0){
                if(isNumeric(Num) && isEmail(mail)){
                    PersonInfo p = new PersonInfo(name, add, Num,mail);
                    persons.add(p);
                    show("Contact Added");
                    savePersons();
                    new frame1();
                }
                else{
                    addperson(s,t,u,v);
                }
            }
            else{
                show("Field's can't be empty");
                addperson(s,t,u,v);
            }
        }
    }
    void searchPerson(String n) {
        new frame3(persons,n);
    }
    void showAll()
    {
        new frame2(persons);
    }
    void deletePerson(String n) {
        int i;
        JPanel pane = new JPanel(new GridLayout(5,3));
        JLabel pname = new JLabel();
        JLabel padd = new JLabel();
        JLabel pnum = new JLabel();
        JLabel pemail = new JLabel();
        pane.add(pname);
        pane.add(padd);
        pane.add(pnum);
        pane.add(pemail);
        int flag=0;
        for (i = 0; i < persons.size(); i++) {
            PersonInfo p = (PersonInfo) persons.get(i);
            if (n.compareToIgnoreCase(p.name)==0) {

                persons.remove(i);flag=1;i--;
                pname.setText("Name : "+p.name);
                padd.setText("Address : "+p.address);
                pnum.setText("Phonenumber : "+p.phoneNumber);
                pemail.setText("Email : "+p.email);
                JOptionPane.showMessageDialog(null,pane);
            }
        }
        if(flag==1)
        {

            show("Contact with name "+n+" is deleted");
        }
        else
        {
            show("Contact with name "+n+" not found");
        }
    }
    void savePersons() {
        try {
            PersonInfo p;
            String line;
            FileWriter fw = new FileWriter("persons.txt");
            PrintWriter pw = new PrintWriter(fw);
            for (int i = 0; i < persons.size(); i++) {
                p = (PersonInfo) persons.get(i);
                line = p.name + "," + p.address + "," + p.phoneNumber + "," + p.email;
                pw.println(line);
            }
            pw.flush();
            pw.close();
            fw.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    void loadPersons() {
        String tokens[] = null;
        String name, add, ph,em;
        try {
            FileReader fr = new FileReader("persons.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                tokens = line.split(",");
                name = tokens[0];
                add = tokens[1];
                ph = tokens[2];
                em=tokens[3];
                PersonInfo p = new PersonInfo(name, add, ph,em);
                persons.add(p);
                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}
class frame3 extends JFrame{
    public frame3(ArrayList persons,String str){
        setTitle("Searched Contats");
        JLabel l = new JLabel();
        add(l);
        Collections.sort(persons);
        ArrayList newpersons = new ArrayList<>();
        int m=str.length();
        for(int k=0;k<persons.size();k++){
            PersonInfo p=(PersonInfo)persons.get(k);
            int n=p.name.length()+p.address.length()+p.phoneNumber.length()+p.email.length()+3;
            String s =p.name+" "+p.address+" "+p.phoneNumber+" "+p.email;
            for(int i=0;i<=n-m;i++){
                int j=0;
                while(j<m && s.toLowerCase().charAt(i+j)==str.toLowerCase().charAt(j)){
                    j++;
                }
                if(j==m){
                    newpersons.add(p);
                    break;
                }
            }
        }
        if(newpersons.size()==0){
            l.setText("Contact Not Found");
        }
        else{
            String[][] tab = new String[newpersons.size()][4];
            for(int i=0;i<newpersons.size();i++){
                PersonInfo p;
                p = (PersonInfo)newpersons.get(i);
                tab[i][0]=p.name;
                tab[i][1]=p.address;
                tab[i][2]=p.phoneNumber;
                tab[i][3]=p.email;
            }
            String[] heading={"NAME","ADDRESS","PHONE NUMBER","EMAIL"};
            JTable table = new JTable(tab,heading);
            table.setRowHeight(20);
            JScrollPane sp = new JScrollPane(table);
            sp.setPreferredSize(new Dimension(650,250));
            add(sp);
        }
        JButton b=new JButton("Close");
        b.setBackground(Color.yellow);
        b.setForeground(Color.black);
        b.addActionListener(e->
        {
            new frame1();
            dispose();
        });
        add(b);
        setLayout(new FlowLayout(FlowLayout.CENTER,10,15));
        setVisible(true);
        setSize(700,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
class frame4 extends JFrame {
    public frame4() {
        AddressBook f = new AddressBook();
        setTitle("Select");
        Checkbox ch1 = new Checkbox("Name");
        Checkbox ch2 = new Checkbox("Phone number");
        Checkbox ch3 = new Checkbox("Address");
        Checkbox ch4 = new Checkbox("Email");
        ch1.setPreferredSize(new Dimension(200, 20));
        ch2.setPreferredSize(new Dimension(200, 20));
        ch3.setPreferredSize(new Dimension(200, 20));
        ch4.setPreferredSize(new Dimension(200, 20));
        add(ch1);
        add(ch2);
        add(ch3);
        add(ch4);
        JButton but = new JButton("Done");
        add(but);
        but.setPreferredSize(new Dimension(75, 40));
        but.setForeground(Color.black);
        but.addActionListener(e -> {
            boolean a = ch1.getState();
            boolean b = ch2.getState();
            boolean c = ch3.getState();
            boolean d = ch4.getState();
            f.addperson(a,b,c,d);
            dispose();
        });
        setLayout(new FlowLayout(FlowLayout.CENTER,10,15));
        setSize(300, 300);
        setResizable(false);
        setVisible(true);
    }
}
class frame2 extends JFrame
{
    public frame2(ArrayList persons)
    {
        setTitle("All Contacts");
        Collections.sort(persons);
        String[][] matter=new String[persons.size()][4];
        for (int i = 0; i < persons.size(); i++)
        {
            PersonInfo p;
            p = (PersonInfo) persons.get(i);
            matter[i][0]=p.name;
            matter[i][1]=p.address;
            matter[i][2]=p.phoneNumber;
            matter[i][3]=p.email;
        }
        String[] heading={"NAME","ADDRESS","PHONE NUMBER","EMAIL"};
        JTable  table=new JTable(matter,heading);
        table.setRowHeight(20);
        table.setGridColor(Color.darkGray);
        JScrollPane sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(650,250));
        add(sp);
        JButton b=new JButton("Close");
        b.setBackground(Color.yellow);
        b.setForeground(Color.black);
        b.addActionListener(e->
        {
            new frame1();
            dispose();
        });
        add(b);
        setLayout(new FlowLayout(FlowLayout.CENTER,10,15));
        setVisible(true);
        setSize(700,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
public class AddressB
{
    public static void main(String args[])
    {
        frame1 cl=new frame1();
    }
}
class frame1 extends JFrame
{
    public frame1()
    {
        AddressBook ab = new AddressBook();
        setTitle("ADDRESS BOOK");

        JLabel sub = new JLabel("Select Option");
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        p1.add(sub);
        add(p1);
        sub.setBounds(50, 50,50,50 );
        setLayout(new FlowLayout(FlowLayout.LEADING,45,15));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(580,200));
        JButton addContact=new JButton("Add Contact");
        JButton search=new JButton("Search Contact");
        JButton delete=new JButton("Delete Contact");
        JButton showAll=new JButton("Show All Contacts");
        addContact.setForeground(Color.black);
        search.setForeground(Color.black);
        delete.setForeground(Color.black);
        showAll.setForeground(Color.black);
        p2.add(addContact);
        p2.add(search);
        p2.add(delete);
        p2.add(showAll);
        add(p2);
        setResizable(false);
        addContact.addActionListener(e->
        {
            new frame4();
            dispose();
        });
        search.addActionListener(e->
        {

            String s = JOptionPane.showInputDialog("Enter Name/Address/Number/Email to search:");
            ab.searchPerson(s);
            dispose();
        });
        delete.addActionListener(e->
        {
            String s = JOptionPane.showInputDialog("Enter Name to delete:");
            ab.deletePerson(s);
            ab.savePersons();

        });
        showAll.addActionListener(e->
        {
            ab.showAll();
            dispose();
        });
    }
}
