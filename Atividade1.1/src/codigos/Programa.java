package codigos;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

class Funcoes {
	  public void EscalaCinza() {
	    System.out.println("\nRodando Escala de Cinza");
	    
	    String filename = "lena.png";

	    Mat image = Imgcodecs.imread(getClass().getResource(filename).getPath().substring(1));
	    
	    // declaração da matriz da imagem de saída
	    int[][][] saida = new int[image.cols()][image.rows()][3];
	    
	    // percorrerá cada pixel da matriz
	    for(int x = 0; x< image.cols(); x++) {
	    	for(int y = 0; y< image.rows(); y++) {
	    		int media = 0;
	    		
	    		// irá somar os valores RGB para obter a média
	    		for(int i = 0; i< 3; i++) {
    				media += image.get(x, y)[i];
	    		}
	    		
	    		media /= 3;	    		
	    		
	    		saida[x][y][0] = media;		// R
	    		saida[x][y][1] = media;		// G
	    		saida[x][y][2] = media;		// B
	    		
	    		image.put(x, y, saida[x][y]);
	    	}
	    }
	    
	    System.out.println(String.format("Escrevendo %s", filename));
	    Imgcodecs.imwrite(filename, image);
	  }
	  
	  public void Binarizador() {
		    System.out.println("\nRodando Binarizador");
		    
		    String filename = "lena.png";

		    Mat image = Imgcodecs.imread(getClass().getResource(filename).getPath().substring(1));
		    
		    // declaração da matriz da imagem de saída
		    int[][][] saida = new int[image.cols()][image.rows()][3];
		    
		    // percorrerá cada pixel da matriz
		    for(int x = 0; x< image.cols(); x++) {
		    	for(int y = 0; y< image.rows(); y++) {
		    		int media = 0;
		    		
		    		// irá somar os valores RGB para obter a média
		    		for(int i = 0; i< 3; i++) {
	    				media += image.get(x, y)[i];
		    		}
		    		
		    		media /= 3;
		    		
		    		if(media < 127) {
		    			saida[x][y][0] = 0;		// R
			    		saida[x][y][1] = 0;		// G
			    		saida[x][y][2] = 0;		// B	
		    		} else {
		    			saida[x][y][0] = 255;		// R
			    		saida[x][y][1] = 255;		// G
			    		saida[x][y][2] = 255;		// B
		    		}
		    		
		    		image.put(x, y, saida[x][y]);
		    	}
		    }
		    
		    System.out.println(String.format("Escrevendo %s", filename));
		    Imgcodecs.imwrite(filename, image);
		  }
	}
	
public class Programa {
	public static void main(String[] args) {
		System.load("C:/opencv_java401/libopencv_java401.dll");
		
		new Funcoes().EscalaCinza();
	}

}
