package codigos;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

class Funcoes {
  
	public String filename = "lena.png";
	
	public void EscalaCinza() {
		System.out.println("\nRodando Escala de Cinza");
		
		Mat image = Imgcodecs.imread(getClass().getResource(filename).getPath().substring(1));
		
		// declaração da matriz da imagem de saída
		double[][][] saida = new double[image.rows()][image.cols()][3];
		
		// percorrerá cada pixel da matriz
		for(int x = 0; x < image.rows(); x++) {
			for(int y = 0; y< image.cols(); y++) {
				double media = 0;
				
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
		
		System.out.println(String.format("Escrevendo gray_%s", filename));
		Imgcodecs.imwrite("gray_"+filename, image);
	}
  
	public void Binarizador(int limiar) {
		System.out.println("\nRodando Binarizador com limiar " + limiar);
		
		Mat image = Imgcodecs.imread(getClass().getResource(filename).getPath().substring(1));
		
		// declaração da matriz da imagem de saída
		double[][][] saida = new double[image.rows()][image.cols()][3];
		
		// percorrerá cada pixel da matriz
		for(int x = 0; x< image.rows(); x++) {
			for(int y = 0; y< image.cols(); y++) {
				double media = 0;
				
				// irá somar os valores RGB para obter a média
				for(int i = 0; i< 3; i++) {
					media += image.get(x, y)[i];
				}
				
				media /= 3;
				
				if(media < limiar) {
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
		
		System.out.println(String.format("Escrevendo bin_%s", filename));
		Imgcodecs.imwrite("bin_"+filename, image);
	  }
}
	
public class Programa {
	public static void main(String[] args) {
		System.load("C:/opencv_java401/libopencv_java401.dll");
		
		new Funcoes().EscalaCinza();
		new Funcoes().Binarizador(127);
	}

}
