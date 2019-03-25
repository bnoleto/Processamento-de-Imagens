package codigos;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

public class Histograma {
	
	private int[] qtd_cinza;
	private Mat img_origem;
	private Mat grafico_histograma;
	
	private int maior_cinza = 5000;
	
	public Histograma(Mat imagem) {

		qtd_cinza = new int[256];
		
		for(int linha = 0; linha < imagem.rows(); linha++) {
			for(int coluna = 0; coluna < imagem.cols(); coluna++) {
				
				double[] pixel_atual = imagem.get(linha, coluna); 
				
				qtd_cinza[intensidade(pixel_atual)]++;
				
				if(qtd_cinza[intensidade(pixel_atual)] > maior_cinza) {
					maior_cinza = qtd_cinza[intensidade(pixel_atual)];
				}
				
			}
		}
	}
	
	public Histograma(int[] histograma) {
		this.qtd_cinza = histograma;
	}
	
	public void show_histograma(String filename) {
		
		System.out.println("Gerando Histograma para " + filename);
		
		double[] preto = {0,0,0};
		double[] branco = {255,255,255};
		Mat grafico_histograma = new Mat(256,256, org.opencv.core.CvType.CV_8U);
		
//		double qtd_pixels = imagem.rows()*imagem.cols();
		
		for(int nivel_cinza = 0; nivel_cinza < 256; nivel_cinza++) {
			for(int quantidade = 0; quantidade <= grafico_histograma.rows(); quantidade++) {
				
				if(quantidade < (double)qtd_cinza[nivel_cinza]/maior_cinza*grafico_histograma.rows()) {
					grafico_histograma.put(grafico_histograma.rows()-quantidade, nivel_cinza, branco);
				} else {
					grafico_histograma.put(grafico_histograma.rows()-quantidade, nivel_cinza, preto);
				}
			}
		}
		
		System.out.println("*Maior cinza: " + maior_cinza);
		
		abrir_arquivo(filename+"(histograma)", grafico_histograma);
	}
	
	public int[] get_histograma() {
		return this.qtd_cinza;
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
