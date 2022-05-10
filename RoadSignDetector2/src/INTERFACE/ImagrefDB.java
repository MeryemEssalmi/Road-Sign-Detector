package INTERFACE;
import java.sql.Connection;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgcodecs.Imgcodecs;
import Basic.MaBibliothequeTraitementImage;
import Basic.MaBibliothequeTraitementImageEtendue;
import DBase.ConnectDB;
public class ImagrefDB {
	static int nbr;
		public static String main( String args,Connection con) throws Exception
		{
			//Ouverture le l'image et saturation des rouges
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			//System.loadLibrary("opencv_java249");
			Mat m=Imgcodecs.imread(args,Imgcodecs.CV_LOAD_IMAGE_COLOR);
			//MaBibliothequeTraitementImageEtendue.afficheImage("Image testee", m);
			Mat transformee=MaBibliothequeTraitementImageEtendue.transformeBGRversHSV(m);
			//MaBibliothequeTraitementImageEtendue.afficheImage("ImageHSV", transformee);
			//la methode seuillage est ici extraite de l'archivage jar du meme nom 
			Mat saturee=MaBibliothequeTraitementImage.seuillage(transformee, 6, 170, 110);
			//MaBibliothequeTraitementImageEtendue.afficheImage("Imagesaturee", saturee);
			//	Mat saturee1=MaBibliothequeTraitementImage.seuillage_exemple(transformee, 6);
			//MaBibliothequeTraitementImageEtendue.afficheImage("Seuillageee 1 ", saturee1);
			//MaBibliothequeTraitementImageEtendue.afficheImage("Seuillage", saturee);
			Mat objetrond = null;

			//Création d'une liste des contours à partir de l'image saturée
			List<MatOfPoint> ListeContours= MaBibliothequeTraitementImageEtendue.ExtractContours(saturee);
			int i=0;
			double [] scores=new double [7];
			//Pour tous les contours de la liste
			for (MatOfPoint contour: ListeContours  ){
				i++;
				objetrond=MaBibliothequeTraitementImageEtendue.DetectFormim(m,contour);
				if (objetrond!=null){
					//MaBibliothequeTraitementImage.afficheImage("Objet rond detécté", objetrond);
					scores[0]=MaBibliothequeTraitementImageEtendue.SimilitudeDB(objetrond,"ref30.jpg",con);
					scores[1]=MaBibliothequeTraitementImageEtendue.SimilitudeDB(objetrond,"ref50.jpg",con);
					scores[2]=MaBibliothequeTraitementImageEtendue.SimilitudeDB(objetrond,"ref70.jpg",con);
					scores[3]=MaBibliothequeTraitementImageEtendue.SimilitudeDB(objetrond,"ref90.jpg",con);
					scores[4]=MaBibliothequeTraitementImageEtendue.SimilitudeDB(objetrond,"ref110.jpg",con);
					scores[5]=MaBibliothequeTraitementImageEtendue.SimilitudeDB(objetrond,"refdouble.jpg",con);
					//scores[6]=MaBibliothequeTraitementImageEtendue.Similitude(objetrond,"train1.jpg");

					//recherche de l'index du maximum et affichage du panneau detecté
					double scoremax=-1;
					int indexmax=0;
					for(int j=0;j<scores.length;j++){
						if (scores[j]>scoremax){scoremax=scores[j];indexmax=j;}}	
					nbr=(int) scoremax;
					if(scoremax<0){System.out.println("Aucun Panneau détécté");}
					else{switch(indexmax){
					case -1:;break;
					case 0:
						 MainMenu.textField.setText("C'est le panneau 30! le nombre de keypoints est :"+nbr);
						return "ref30.jpg";
					case 1:
						 MainMenu.textField.setText("C'est le panneau 50! le nombre de keypoints est :"+nbr);
						return "ref50.jpg";
					case 2:
						 MainMenu.textField.setText("C'est le panneau 70! le nombre de keypoints est :"+nbr);
						return "ref70.jpg";
					case 3:
						 MainMenu.textField.setText("C'est le panneau 90! le nombre de keypoints est :"+nbr);
						return "ref90.jpg";
					case 4:
						 MainMenu.textField.setText("C'est le panneau 110! le nombre de keypoints est :"+nbr);
						return "ref110.jpg";
					case 5:
						 MainMenu.textField.setText("C'est le panneau d'interdiction de passer! le nombre de keypoints est :"+nbr);
						return "refdouble.jpg";
					//case 6:System.out.println("Panneau passage de train");break;
					}}

				}
			}
			return null;	

		}

	}

