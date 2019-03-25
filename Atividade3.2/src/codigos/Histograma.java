package codigos;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

public class Histograma {
	
	int[] qtd_cinza;
	
	int maior_cinza = 0;
	
	public Histograma(Mat imagem, String filename) {
		Mat resultado = new Mat(256,256, org.opencv.core.CvType.CV_8U);
		
		System.out.println("Gerando Histograma para " + filename);
		
		qtd_cinza = new int[256];
		
		double[] preto = {0,0,0};
		double[] branco = {255,255,255};
		
		for(int linha = 0; linha < imagem.rows(); linha++) {
			for(int coluna = 0; coluna < imagem.cols(); coluna++) {
				
				double[] pixel_atual = imagem.get(linha, coluna); 
				
				qtd_cinza[intensidade(pixel_atual)]++;
				
				if(qtd_cinza[intensidade(pixel_atual)] > maior_cinza) {
					maior_cinza = qtd_cinza[intensidade(pixel_atual)];
				}
				
			}
		}

//		double qtd_pixels = imagem.rows()*imagem.cols();
		
		for(int nivel_cinza = 0; nivel_cinza < 256; nivel_cinza++) {
			for(int quantidade = 0; quantidade <= resultado.rows(); quantidade++) {
				
				if(quantidade < (double)qtd_cinza[nivel_cinza]/maior_cinza*resultado.rows()) {
					resultado.put(resultado.rows()-quantidade, nivel_cinza, branco);
				} else {
					resultado.put(resultado.rows()-quantidade, nivel_cinza, preto);
				}
			}
		}
		
		System.out.println(maior_cinza);
	
		abrir_arquivo(filename+"(histograma)", resultado);
	}
	
	private int intensidade(double[] pixel_entrada) {
		
		return (int)((pixel_entrada[0]+pixel_entrada[1]+pixel_entrada[2])/3);
		
	}
	
	private void abrir_arquivo(String filename, Mat imagem) {
		
		javax.swing.JFrame frame = new javax.swing.JFrame(filename);
		frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(new javax.swing.JLabel(new javax.swing.ImageIcon(HighGui.toBufferedImage(imagem))));
		frame.pack();
		frame.setVisible(true);
		
	}

}
