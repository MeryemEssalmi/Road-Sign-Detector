package INTERFACE;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import Basic.MaBibliothequeTraitementImage;
import Basic.MaBibliothequeTraitementImageEtendue;

import org.opencv.imgproc.Imgproc;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import Basic.AnalyseVideo;
import Basic.MaBibliothequeTraitementImage;
import Basic.MaBibliothequeTraitementImageEtendue;
import Basic.Principale;
import DBase.ConnectDB;
import DBase.PRINCIPALDB;


import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.Label;
import javax.swing.SwingConstants;
public class MainMenu {
	//JLabel vidpanel = new JLabel();
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	static JLabel label;
	static String path;
	static String refpann;
	int running=1;
	JLabel video= new JLabel();
	int choice;
	int DB=10;
	static JLabel label_2;
	private JFrame frame;
	private final Action action_1 = new SwingAction_1();
	private final Action action_2 = new SwingAction_2();
	static JTextField textField;
	String password1;
	String User;

	/**
	 * Launch the application.
	 */
	class Multi extends Thread{  
		public void run(){  
		System.out.println("thread is running...");
		int s=0;
		Mat Jframe = new Mat();
		VideoCapture camera = new VideoCapture(path);
		Mat PanneauAAnalyser = null;
		while (camera.read(Jframe)&&running==1) {
			s++;
		if (s>5){
			Mat transformee = MaBibliothequeTraitementImageEtendue.transformeBGRversHSV(Jframe);
			//la methode seuillage est ici extraite de l'archivage jar du meme nom 
			Mat saturee = MaBibliothequeTraitementImage.seuillage(transformee, 6, 170, 110);
			
			//MaBibliothequeTraitementImageEtendue.afficheImage("Image testee", saturee);	
			Mat objetrond = null;
			//Création d'une liste des contours à partir de l'image saturée
			List<MatOfPoint> ListeContours = MaBibliothequeTraitementImageEtendue .ExtractContours(saturee);
			//Pour tous les contours de la liste
			
			for (MatOfPoint contour: ListeContours  ){
				// isole la forme
				objetrond=MaBibliothequeTraitementImageEtendue.DetectForm(Jframe,contour);
		        int indexemax = identifiepanneau(objetrond) ;
				
				if (objetrond!=null){
			    Image img;
				Image dimg;
				ImageIcon imageIcon;
				//MaBibliothequeTraitementImageEtendue.afficheImage("Image testee", Jframe);				
				switch(indexemax /*Change toindexemax*/){
				case -1:break;
				case 0:
					refpann="ref30.jpg";
					textField.setText("C'est le panneau 30!");
					try {
						img = ImageIO.read(new File(refpann));
						dimg = img.getScaledInstance(label_2.getWidth(), label_2.getHeight(),
			                    Image.SCALE_SMOOTH);
			            imageIcon = new ImageIcon(dimg);
			            label_2.setIcon(imageIcon);//MaBibliothequeTraitementImage.afficheImage("Objet rond detécté", objetrond);
						break;
					} catch (IOException e5) {
						// TODO Auto-generated catch block
						e5.printStackTrace();
					}
			           
				case 1:
					refpann="ref50.jpg";
					textField.setText("C'est le panneau 50!");
					  try {
						img = ImageIO.read(new File(refpann));
						dimg = img.getScaledInstance(label_2.getWidth(), label_2.getHeight(),
					            Image.SCALE_SMOOTH);
					            imageIcon = new ImageIcon(dimg);
					            label_2.setIcon(imageIcon);//MaBibliothequeTraitementImage.afficheImage("Objet rond detécté", objetrond);
					            break;
					} catch (IOException e4) {
						// TODO Auto-generated catch block
						e4.printStackTrace();
					}
			           
				case 2:
					refpann="ref70.jpg";
					textField.setText("C'est le panneau 70!");
					 try {
						img = ImageIO.read(new File(refpann));
						  dimg = img.getScaledInstance(label_2.getWidth(), label_2.getHeight(),
						            Image.SCALE_SMOOTH);
						          imageIcon = new ImageIcon(dimg);
						            label_2.setIcon(imageIcon);
						            break;
					} catch (IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
			           
				case 3:
					refpann="ref90.jpg";
					textField.setText("C'est le panneau 90!");
					 try {
						img = ImageIO.read(new File(refpann));
						dimg = img.getScaledInstance(label_2.getWidth(), label_2.getHeight(),
					            Image.SCALE_SMOOTH);
					           imageIcon = new ImageIcon(dimg);
					            label_2.setIcon(imageIcon);
					            break;
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
			           
					

				case 4:
					refpann="ref110.jpg";
					textField.setText("C'est le panneau 110!");
					 try {
						img = ImageIO.read(new File(refpann));
						dimg = img.getScaledInstance(label_2.getWidth(), label_2.getHeight(),
					            Image.SCALE_SMOOTH);
					           imageIcon = new ImageIcon(dimg);
					            label_2.setIcon(imageIcon);
					            break;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			           
				case 5:
					refpann="refdouble.jpg";
					textField.setText("C'est le panneau de l'interdiction de dépasser!");
					 try {
						img = ImageIO.read(new File(refpann));
						 dimg = img.getScaledInstance(label_2.getWidth(), label_2.getHeight(),
						            Image.SCALE_SMOOTH);
						            imageIcon = new ImageIcon(dimg);
						            label_2.setIcon(imageIcon);
						            break;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			          
				}} }
		
		s=0;
		}
		
				ImageIcon image = new ImageIcon(Mat2bufferedImage(Jframe));
			
				video.setIcon(image);
				video.repaint();
			
				//super.setContentPane(contentPane);
				Image image1 = new ImageIcon(Mat2bufferedImage(Jframe)).getImage();
				frame.setIconImage(image1);
			
		}
	
		}  
		
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}

	private void initialize() {
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100,1200, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JLabel lblNewLabel = new JLabel("||Welcome to panel detector||");
		lblNewLabel.setFont(new Font("Source Serif Pro", Font.BOLD, 36));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(405, 16, 708, 68);
		frame.getContentPane().add(lblNewLabel);
		  label = new JLabel();
		   label.setBounds(101,147,447,368);
		   frame.getContentPane().add(label);
		   JLabel label_1 = new JLabel("Panneau détécté :");
		   label_1.setFont(new Font("Source Serif Pro Black", Font.PLAIN, 21));
		   label_1.setForeground(Color.WHITE);
		   label_1.setBackground(Color.BLACK);
			label_1.setBounds(996, 194, 177, 68);
			frame.getContentPane().add(label_1);
			label_2 = new JLabel();
			label_2.setBounds(1006, 259, 167, 136);
			frame.getContentPane().add(label_2);
			textField = new JTextField();
			textField.setHorizontalAlignment(SwingConstants.LEFT);
			textField.setFont(new Font("Miriam Libre", Font.PLAIN, 16));
			textField.setForeground(Color.WHITE);
			textField.setBackground(Color.BLACK);
			textField.setBounds(25, 548, 708, 54);
			frame.getContentPane().add(textField);
			textField.setColumns(10);
			
		Button btnNewButton = new Button("Telecharger la photo");
		btnNewButton.setActionCommand("Telecharger la photo");
		btnNewButton.setFont(new Font("Showcard Gothic", Font.BOLD, 16));
		btnNewButton.setBackground(Color.PINK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 running=0;
				 label_2.setIcon(null);
				 textField.setText("");
				//label=new JLabel();
				  JFileChooser file = new JFileChooser();
		          file.setCurrentDirectory(new File(System.getProperty("user.home")));
		          //filter the files
		          FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
		          file.addChoosableFileFilter(filter);
		          int result = file.showSaveDialog(null);
		          label.setIcon(null) ;
		          video.setIcon(null) ;
		           //if the user click on save in Jfilechooser
		          if(result == JFileChooser.APPROVE_OPTION){
		              File selectedFile = file.getSelectedFile();
		              path = selectedFile.getAbsolutePath();
		              label.setIcon(ResizeImage(path));
		          
		              choice=1;
		   
		          }
		           //if the user click on save in Jfilechooser


		          else if(result == JFileChooser.CANCEL_OPTION){
		              System.out.println("No File Select");
		          }
		        }
		    });
		btnNewButton.setBounds(207, 90, 232, 39);
		frame.getContentPane().add(btnNewButton);
		
		Button btnNewButton_1 = new Button("Telecharger la video");
		btnNewButton_1.setActionCommand("Telecharger la video");
		btnNewButton_1.setFont(new Font("Snap ITC", Font.BOLD, 16));
		btnNewButton_1.setBackground(Color.PINK);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					  JFileChooser file = new JFileChooser();
					  running=0;
					  label.setIcon(null) ;
					  video.setIcon(null) ;
					  label_2.setIcon(null);
					  textField.setText("");
			          file.setCurrentDirectory(new File(System.getProperty("user.home")));
			          //filter the files
			          FileNameExtensionFilter filter = new FileNameExtensionFilter("video","mp4");
			          file.addChoosableFileFilter(filter);
			          int result = file.showSaveDialog(null);
			           //if the user click on save in Jfilechooser
			          if(result == JFileChooser.APPROVE_OPTION){
			              File selectedFile = file.getSelectedFile();
			              path = selectedFile.getAbsolutePath();
			              System.out.println(path);
			             choice=2;
			          }
				
			}
		});
		btnNewButton_1.setBounds(530, 90, 232, 39);
		frame.getContentPane().add(btnNewButton_1);
		
		Button btnNewButton_2 = new Button("Detecter Panneau");
		btnNewButton_2.setBackground(Color.ORANGE);
		btnNewButton_2.setFont(new Font("Elephant", Font.BOLD, 16));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (choice==1 && DB==0 ) {
				try {
					
					String panref;
					String first="le panneau détectee est:\n";
		            panref=Imageref.main(path);
		            BufferedImage img = null;
		            img = ImageIO.read(new File(panref));
		            Image dimg = img.getScaledInstance(label_2.getWidth(), label_2.getHeight(),
		                    Image.SCALE_SMOOTH);
		            ImageIcon imageIcon = new ImageIcon(dimg);
		            label_2.setIcon(imageIcon);
		           // textField.setText(first+ panref+"\n le nombre de keypoints est :"+Imageref.nbr);
		            
		            
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
				else if (choice==1 && DB==1 ) {
					try {
						new password().setVisible(true);
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
				}
				
				else if (choice==2 ) {
					try {
						Multi t1=new Multi(); 
						 running=1;
						 t1.start();
						//String[] args = null;
						//AnalyseVideo.main(args);
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
					
				}
				else {
					test window = new test();
					window.frame.setVisible(true);
					System.out.println("Vous n'avez pas séléctionné un fichier!");
				}
				}
			
		});
		btnNewButton_2.setBounds(933, 548, 212, 54);
		frame.getContentPane().add(btnNewButton_2);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton rdbtnNewRadioButton = new JRadioButton("recalculer");
		rdbtnNewRadioButton.setForeground(new Color(255, 255, 255));
		rdbtnNewRadioButton.setEnabled(false);
		rdbtnNewRadioButton.setBackground(new Color(0, 0, 0));
		rdbtnNewRadioButton.setFont(new Font("Source Code Pro Semibold", Font.BOLD, 20));
		rdbtnNewRadioButton.setAction(action_1);
		rdbtnNewRadioButton.setBounds(999, 468, 155, 29);
		frame.getContentPane().add(rdbtnNewRadioButton);
		buttonGroup.add(rdbtnNewRadioButton);
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("DataBase");
		rdbtnNewRadioButton_1.setForeground(new Color(255, 255, 255));
		rdbtnNewRadioButton_1.setBackground(new Color(0, 0, 0));
		rdbtnNewRadioButton_1.setFont(new Font("Source Code Pro Semibold", Font.BOLD, 20));
		rdbtnNewRadioButton_1.setAction(action_2);
		rdbtnNewRadioButton_1.setBounds(999, 513, 155, 34);
		frame.getContentPane().add(rdbtnNewRadioButton_1);
		buttonGroup.add(rdbtnNewRadioButton_1);
		
		
		video.setBounds(25, 147, 970, 385);
		frame.getContentPane().add(video);
		
		JButton btnNewButton_3 = new JButton("Stop");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				running=0;
				 label.setIcon(null) ;
				  video.setIcon(null) ;
				  label_2.setIcon(null);
				  textField.setText("");
			}
		});
		btnNewButton_3.setBounds(996, 620, 115, 29);
		frame.getContentPane().add(btnNewButton_3);
	}
	 // Methode to resize imageIcon with the same size of a Jlabel
    public ImageIcon ResizeImage(String ImagePath)
    {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }
    private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "Recalculer");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			DB=0;
		}
    }
    private class SwingAction_2 extends AbstractAction {
		public SwingAction_2() {
			putValue(NAME, "Data base");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			DB=1;
			
			
		}
    }

    public static BufferedImage Mat2bufferedImage(Mat image) {
		MatOfByte bytemat = new MatOfByte();
		Imgcodecs.imencode(".jpg", image, bytemat);
		byte[] bytes = bytemat.toArray();
		InputStream in = new ByteArrayInputStream(bytes);
		BufferedImage img = null;
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}



	public static int identifiepanneau(Mat objetrond){
		double [] scores=new double [6];
		 
		
		int indexmax=-1;
		if (objetrond!=null){
			scores[0]=MaBibliothequeTraitementImageEtendue.Similitude(objetrond,"ref30.jpg"/*,r,c*/);
			scores[1]=MaBibliothequeTraitementImageEtendue.Similitude(objetrond,"ref50.jpg");
			scores[2]=MaBibliothequeTraitementImageEtendue.Similitude(objetrond,"ref70.jpg");
			scores[3]=MaBibliothequeTraitementImageEtendue.Similitude(objetrond,"ref90.jpg");
			scores[4]=MaBibliothequeTraitementImageEtendue.Similitude(objetrond,"ref110.jpg");
			scores[5]=MaBibliothequeTraitementImageEtendue.Similitude(objetrond,"refdouble.jpg");

			double scoremax=-1;

			for(int j=1;j<scores.length;j++){
				if (scores[j]>scoremax){scoremax=scores[j];indexmax=j;}}	


		}
		return indexmax;
	}

	
	public static BufferedImage Mat2BufferedImage(Mat m) {
		// Method converts a Mat to a Buffered Image
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = m.channels() * m.cols() * m.rows();
		byte[] b = new byte[bufferSize];
		m.get(0, 0, b); // get all the pixels
		BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);
		return image;
	}
}

