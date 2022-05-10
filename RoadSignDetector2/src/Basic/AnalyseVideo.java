package Basic;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.opencv.imgproc.Imgproc;

public class AnalyseVideo {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	static Mat imag = null;

	public static void main(String[] args) {
		int s=0;
		JFrame jframe = new JFrame("Detection de panneaux sur un flux vidéo");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel vidpanel = new JLabel();
		jframe.setContentPane(vidpanel);
		jframe.setSize(720, 480);
		jframe.setVisible(true);

		Mat frame = new Mat();
		VideoCapture camera = new VideoCapture("video1.mp4");
		Mat PanneauAAnalyser = null;
		
		
			while (camera.read(frame)) {
				s++;
			if (s>5){
				Mat transformee = MaBibliothequeTraitementImageEtendue.transformeBGRversHSV(frame);
				//la methode seuillage est ici extraite de l'archivage jar du meme nom 
				Mat saturee = MaBibliothequeTraitementImage.seuillage(transformee, 6, 170, 110);
				
				
				Mat objetrond = null;
				//Création d'une liste des contours à partir de l'image saturée
				List<MatOfPoint> ListeContours = MaBibliothequeTraitementImageEtendue .ExtractContours(saturee);
				//Pour tous les contours de la liste
				
				for (MatOfPoint contour: ListeContours  ){
					// isole la forme
					objetrond=MaBibliothequeTraitementImageEtendue.DetectForm(frame,contour);
					//MaBibliothequeTraitementImageEtendue.afficheImage("Image testee", frame);
			        int indexemax = identifiepanneau(objetrond) ;
					
					if (objetrond!=null){
											
					switch(indexemax /*Change toindexemax*/){
					case -1:break;
					case 0:System.out.println("Panneau 30 détécté");//MaBibliothequeTraitementImage.afficheImage("Objet rond detécté", objetrond);
							break;
					case 1:System.out.println("Panneau 50 détécté");//MaBibliothequeTraitementImage.afficheImage("Objet rond detécté", objetrond);
break;
					case 2:System.out.println("Panneau 70 détécté");//MaBibliothequeTraitementImage.afficheImage("Objet rond detécté", objetrond);
break;
					case 3:System.out.println("Panneau 90 détécté");//MaBibliothequeTraitementImage.afficheImage("Objet rond detécté", objetrond);
break;

					case 4:System.out.println("Panneau 110 détécté");break;
					case 5:System.out.println("Panneau interdiction de dépasser détécté");break;
					
					}} }
			
			s=0;
			}
					ImageIcon image = new ImageIcon(Mat2bufferedImage(frame));
					vidpanel.setIcon(image);
					//vidpanel.repaint();
			
		

			
			
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