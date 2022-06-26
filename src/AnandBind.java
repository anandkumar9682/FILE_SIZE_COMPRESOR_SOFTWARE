import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter; 
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipFile;
import java.util.ArrayList;
import java.util.Iterator;

class AnandBind extends JFrame implements ActionListener{

    JTextField selectFile;
    JButton selectButton,compressButton,clean,extractButton,selectZipFile;
    JTextArea textarea;
    JProgressBar progBar;
    JPanel mainPanel;

    int min=0;
    int start=0;
    int max=100;
    int numb;

    String zipFileName="An.zip";
    String defaultPath="C:\\";
    String defaultLocation="C:\\";

    ArrayList arrayList;

    boolean browse=false;
    
    FileOutputStream fileOutputstream;
    ZipOutputStream zos;
    File fileSize;

    public AnandBind()
    {
        this.setSize(415,630);
        this.setTitle("Size Compress By Anand Bind");
        this.setLocation(100,10);
        this.setResizable(false);

        arrayList=new ArrayList();

        try 
        { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        }
        catch(Exception e){}

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mainPanel=new JPanel();
        mainPanel.setBackground(new Color(116, 133, 145));
        mainPanel.setLayout(null);
        this.add(mainPanel);

        selectFile=new JTextField(defaultLocation);
        selectFile.setEditable(false);
        selectFile.setFont(new Font("Arial",Font.BOLD,15));
        selectFile.setForeground(new Color(255,255,255));
        selectFile.setBackground(new Color(79, 97, 110));
        selectFile.setBounds(15,25,260,22);
        mainPanel.add(selectFile);
        
        selectButton=new JButton("Browse");
        selectButton.setFont(new Font("Arial",Font.BOLD,13));
        selectButton.setForeground(new Color(0,0,0));
        selectButton.setBackground(new Color(52, 60, 79));
        selectButton.setBounds(285,25,100,22);
        selectButton.addActionListener(this);
        mainPanel.add(selectButton);
        
        textarea=new JTextArea(" No Select Files "+"\n\n");
        textarea.setFont(new Font("Arial",Font.BOLD,13));
        textarea.setForeground(new Color(255,0,0));
        textarea.setBackground(new Color(255,255,255));
        textarea.setEditable(false);
        textarea.setLineWrap(true);
        JScrollPane scrollPane=new JScrollPane(textarea);
        scrollPane.setBounds(15,60,370,400);
        mainPanel.add(scrollPane);

        progBar = new JProgressBar(0,10);
        progBar.setStringPainted(true); 
        progBar.setBounds(15,470,370,22); 
        mainPanel.add(progBar);
              
        compressButton = new JButton("Compress");
        compressButton.setEnabled(false);
        compressButton.setFont(new Font("Arial",Font.BOLD,13));
        compressButton.setForeground(new Color(0,0,0));
        compressButton.setBackground(new Color(79, 97, 110));
        compressButton.setBounds(15,515,95,22);
        compressButton.addActionListener(this);
        mainPanel.add(compressButton);

        clean = new JButton("clean"); 
        clean.setEnabled(false);
        clean.setFont(new Font("Arial",Font.BOLD,13));
        clean.setForeground(new Color(0,0,0));
        clean.setBackground(new Color(79, 97, 110));
        clean.setBounds(115,515,75,22);
        clean.addActionListener(this);
        mainPanel.add(clean);

        selectZipFile = new JButton("SelectZip"); 
        selectZipFile.setFont(new Font("Arial",Font.BOLD,13));
        selectZipFile.setForeground(new Color(0,0,0));
        selectZipFile.setBackground(new Color(79, 97, 110));
        selectZipFile.setBounds(195,515,100,22);
        selectZipFile.addActionListener(this);
        mainPanel.add(selectZipFile);

        extractButton = new JButton("Extract"); 
        extractButton.setEnabled(false);
        extractButton.setFont(new Font("Arial",Font.BOLD,13));
        extractButton.setForeground(new Color(0,0,0));
        extractButton.setBackground(new Color(79, 97, 110));
        extractButton.setBounds(300,515,85,22);
        extractButton.addActionListener(this);
        mainPanel.add(extractButton);

        JLabel createrName=new JLabel("Developed By- Anand Bind");
        createrName.setBounds(55,555,350,30);
        createrName.setFont(new Font("Arial",Font.BOLD,20));
        createrName.setForeground(Color.white);
        mainPanel.add(createrName);

    }

    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getSource()==selectButton)
        { 
            progBar.setValue(0);
            fileSelector(); 
        }
        else if(evt.getSource()==compressButton)
            saveFile();

        else if(evt.getSource()==clean){
            progBar.setValue(0);
            cleanAll();
        }

        else if(evt.getSource()==selectZipFile)
        {
            extractButton.setEnabled(true);
            progBar.setValue(0);
            zipFileSelect();
        }

        else if(evt.getSource()==extractButton)
            fileSelector1();
    }

    public void zipFileSelect(){
        JFileChooser j = new JFileChooser(defaultLocation);
        j.setFileSelectionMode(JFileChooser.FILES_ONLY);
        j.setDialogTitle("Select Zip File"); 
        j.setAcceptAllFileFilterUsed(false); 
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Select only Zip file", "zip"); 
        j.addChoosableFileFilter(restrict); 
        int r = j.showOpenDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) 
        {   
            defaultLocation=j.getSelectedFile().getAbsolutePath();
            textarea.setText(defaultLocation);
            extractButton.setEnabled(true);
            clean.setEnabled(true);
        } 
        else
            JOptionPane.showMessageDialog(this,"No File Select");
    
    }

    public void cleanAll()
    {
        JOptionPane.showMessageDialog(this,"Clean SuccessFully");
        arrayList.clear();
        textarea.setText("");
        compressButton.setEnabled(false);
        browse=false;
        extractButton.setEnabled(false);
        clean.setEnabled(false);
        defaultLocation="";
    }
 
    public void fileSelector()
    {
        JFileChooser j = new JFileChooser(defaultPath);
        //j.setAcceptAllFileFilterUsed(true); 
        //j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        j.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //j.setDragEnabled(true);
        j.setDialogTitle("Select File"); 
        //j.setMultiSelectionEnabled(true);
        int r = j.showOpenDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) 
        {   
            browse=true;
            defaultPath=j.getSelectedFile().getAbsolutePath();
            arrayList.add(j);
            textarea.append(+numb+". "+j.getSelectedFile().getAbsolutePath()+"\n"); numb++;
            selectFile.setText(j.getSelectedFile().getAbsolutePath());
            compressButton.setEnabled(true);
            clean.setEnabled(true);
        } 
        else
            JOptionPane.showMessageDialog(this,"No File Select");
    
    }

    public void saveFile()
    {
        if(browse){
            JFileChooser j = new JFileChooser(defaultPath); //FileSystemView.getFileSystemView().getHomeDirectory()
            //j.setAcceptAllFileFilterUsed(false); 
            j.setDialogTitle("Compress"); 
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only Zip File", "zip"); 
            j.addChoosableFileFilter(restrict); 
            int r = j.showSaveDialog(null); 
            if (r == JFileChooser.APPROVE_OPTION) 
            { 
                zipFileName=j.getSelectedFile().getAbsolutePath(); 
                zipWriter();
            } 
            else
                JOptionPane.showMessageDialog(this,"Not Compress File"); 
        }
        else
            JOptionPane.showMessageDialog(this,"Please Select File");
    }
         
    public void zipWriter()
    {
        try
        {
            fileOutputstream=new FileOutputStream(zipFileName+".zip");
            zos=new ZipOutputStream(fileOutputstream);
            addFille();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void addFille()throws IOException
    {
        Iterator i=arrayList.iterator();
        while(i.hasNext())
        {
            JFileChooser j=(JFileChooser)i.next();
            String path=j.getSelectedFile().getAbsolutePath();
            String name=j.getSelectedFile().getName();
            addToZipFile(path,name,zos);
        }
        zos.close();
        fileOutputstream.close();
        progBar.setValue(10);
        JOptionPane.showMessageDialog(new AnandBind(),"Create Zip File SuccessFully");
    }

    public static void addToZipFile(String path,String name,ZipOutputStream zos)throws FileNotFoundException,IOException
    {
        FileInputStream fis=new FileInputStream(path);
        ZipEntry zipEntry=new ZipEntry(name);
        zos.putNextEntry(zipEntry);
        byte[] bytes=new byte[fis.available()];
        fis.read(bytes);
        zos.write(bytes);
        zos.closeEntry();
        fis.close();
    }

    public void extractFile(String pathLocation)
    {   
        try
        {   
            //JOptionPane.showMessageDialog(this,"This tab close then progress Task ","Process",JOptionPane.PLAIN_MESSAGE,new ImageIcon("pleaseWait.gif"));
            ZipFile zipfile=new ZipFile(defaultLocation);
            Enumeration enu=zipfile.entries();
            
            while(enu.hasMoreElements())
            {
                ZipEntry zipEntry=(ZipEntry) enu.nextElement();
                String fname=zipEntry.getName();
                textarea.append(fname+"\n");
                InputStream is=zipfile.getInputStream(zipEntry);
                FileOutputStream fos=new FileOutputStream(pathLocation+"\\"+fname);

/*
                byte[] bytes=new byte[is.available()];
                is.read(bytes);
                fos.write(bytes);
                is.close();
                fos.close(); */

                int length;
                while((length=is.read())!=-1)
                {
                    fos.write(length);
                }
                is.close();
                fos.close();
            }
            zipfile.close();
            progBar.setValue(10);
            JOptionPane.showMessageDialog(new AnandBind(),"Extract Zip File SuccessFully");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void fileSelector1()
    {
        JFileChooser j = new JFileChooser(defaultPath); //FileSystemView.getFileSystemView().getHomeDirectory()
        //FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only Zip File", "zip"); 
        //j.addChoosableFileFilter(restrict); 
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        j.setAcceptAllFileFilterUsed(true); 
        j.setDialogTitle("Select Location"); 
        int r = j.showOpenDialog(null); 
        if (r == JFileChooser.APPROVE_OPTION) 
           extractFile(j.getSelectedFile().getAbsolutePath());          
        else
            textarea.setText("Not select any file");
    }
        

    public static void main(String []args)
    {
        AnandBind frame=new AnandBind();
        frame.setVisible(true);
    }
    }

