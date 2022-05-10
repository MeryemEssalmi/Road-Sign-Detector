package Basic;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.*;
import org.opencv.highgui.*;
import org.opencv.imgcodecs.Imgcodecs;
//import utilitaireAgreg.MaBibliothequeTraitementImage;
public class Principale {

	public static void main(String args) throws Exception
	{
		//Ouverture le l'image et saturation des rouges
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		//System.loadLibrary("opencv_java249");
		Mat m=Imgcodecs.imread(args,Imgcodecs.CV_LOAD_IMAGE_COLOR);
		MaBibliothequeTraitementImageEtendue.afficheImage("Image testee", m);
		Mat transformee=MaBibliothequeTraitementImageEtendue.transformeBGRversHSV(m);
		MaBibliothequeTraitementImageEtendue.afficheImage("ImageHSV", transformee);
		//la methode seuillage est ici extraite de l'archivage jar du meme nom 
		Mat saturee=MaBibliothequeTraitementImage.seuillage(transformee, 6, 170, 110);
		MaBibliothequeTraitementImageEtendue.afficheImage("Imagesaturee", saturee);
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
				MaBibliothequeTraitementImage.afficheImage("Objet rond detécté", objetrond);
				scores[0]=MaBibliothequeTraitementImageEtendue.Similitude(objetrond,"ref30.jpg");
				scores[1]=MaBibliothequeTraitementImageEtendue.Similitude(objetrond,"ref50.jpg");
				scores[2]=MaBibliothequeTraitementImageEtendue.Similitude(objetrond,"ref70.jpg");
				scores[3]=MaBibliothequeTraitementImageEtendue.Similitude(objetrond,"ref90.jpg");
				scores[4]=MaBibliothequeTraitementImageEtendue.Similitude(objetrond,"ref110.jpg");
				scores[5]=MaBibliothequeTraitementImageEtendue.Similitude(objetrond,"refdouble.jpg");
				//scores[6]=MaBibliothequeTraitementImageEtendue.Similitude(objetrond,"train1.jpg");

				//recherche de l'index du maximum et affichage du panneau detecté
				double scoremax=-1;
				int indexmax=0;
				for(int j=0;j<scores.length;j++){
					if (scores[j]>scoremax){scoremax=scores[j];indexmax=j;}}	
				if(scoremax<0){System.out.println("Aucun Panneau détécté");}
				else{switch(indexmax){
				case -1:;break;
				case 0:System.out.println("Panneau 30 détécté");break;
				case 1:System.out.println("Panneau 50 détécté");break;
				case 2:System.out.println("Panneau 70 détécté");break;
				case 3:System.out.println("Panneau 90 détécté");break;
				case 4:System.out.println("Panneau 110 détécté");break;
				case 5:System.out.println("Panneau interdiction de dépasser détécté");break;
				//case 6:System.out.println("Panneau passage de train");break;
				}}

			}
		}	


	}
}