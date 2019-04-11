package codigos;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import telas.Janela;

public class Funcoes {
	private String filename;
	private Mat img_entrada;
	
	public Funcoes(String filename) {
		this.filename = filename;
		
		// irá pegar o caminho da imagem original e usará a função do openCV pra armazenar no Mat
		img_entrada = Imgcodecs.imread(getClass().getClassLoader().getResource("resources/"+filename).getPath().substring(1));
	
	}
	
	public void separar_canais() {
		System.out.println("\nSeparando canais...");
		
		Mat img_r = new Mat(img_entrada.rows(),img_entrada.cols(),img_entrada.type());
		Mat img_g = new Mat(img_entrada.rows(),img_entrada.cols(),img_entrada.type());
		Mat img_b = new Mat(img_entrada.rows(),img_entrada.cols(),img_entrada.type());
		
		for(int linha = 0; linha < img_entrada.rows(); linha++) {
			for(int coluna = 0; coluna < img_entrada.cols(); coluna++) {
				
				double[] pixel_atual = img_entrada.get(linha, coluna);
				
				double[][] canais = {
						{pixel_atual[0],pixel_atual[0],pixel_atual[0]},
						{pixel_atual[1],pixel_atual[1],pixel_atual[1]},
						{pixel_atual[2],pixel_atual[2],pixel_atual[2]}
				};
				
				img_r.put(linha, coluna, canais[0]);
				img_g.put(linha, coluna, canais[1]);
				img_b.put(linha, coluna, canais[2]);
				
			}
		}
		
		new Janela(filename,img_entrada, img_r, img_g, img_b);
		
		/*
		
		EntradaSaida.escrever_arquivo("resultado.png", img_saida);
		
		EntradaSaida.abrir_arquivo("resultado.png", img_saida);
		
		*/
		
		
	}
		
}