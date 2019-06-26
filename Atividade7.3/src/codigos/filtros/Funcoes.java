package codigos.filtros;

import java.util.ArrayList;
import java.util.Random;

import org.opencv.core.Mat;

class Coordenada {
	
	private int x;
	private int y;
	private double[] cor;
	
	Coordenada(int x, int y, double[] cor){
		this.x = x;
		this.y = y;
		this.cor = cor;
	}
	
	public int get_x() {
		return this.x;
	}
	
	public int get_y() {
		return this.y;
	}
	
	public double[] get_cor() {
		return this.cor;
	}
}

class Semente {
	
	private double[] cor;
	
	private ArrayList<Coordenada> coordenadas_pertencentes = new ArrayList<Coordenada>();
	private double[] media_rgb;
	
	Semente(Mat imagem, double[] cor){
		
		this.cor = cor;
		
		calcular_rgb_medio();
	}
	
	public void set_cor(double[] rgb) {
		this.cor = rgb;
	}
	
	public double[] get_media_rgb() {
		return this.media_rgb;
	}
	
	public double[] get_cor() {
		return this.cor;
	}
	
	private void calcular_rgb_medio() {
		double[] soma = {0,0,0};
		
		for(Coordenada coord : coordenadas_pertencentes) {
			for(int i = 0; i < 3; i++) {
				soma[i] += coord.get_cor()[i];
			}
		}
		
		double[] media = {soma[0]/coordenadas_pertencentes.size(),
				soma[1]/coordenadas_pertencentes.size(),
				soma[2]/coordenadas_pertencentes.size()};
		
		this.media_rgb = media;
	}
	
	public void add(Coordenada coord) {
		coordenadas_pertencentes.add(coord);
		calcular_rgb_medio();
	}
	
	public void swap(Coordenada coord, Semente destino) {
		coordenadas_pertencentes.remove(coord);
		calcular_rgb_medio();
		
		destino.add(coord);
	}
		
}

public class Funcoes {
	Mat img_entrada;
	
	private ArrayList<Semente> sementes = new ArrayList<Semente>();
	
	double distancia_rgb(double[] pixel1,double[] pixel2) {
		// distancia euclidiana em 3 dimensões entre 2 cores
		
		return Math.sqrt(Math.pow(pixel1[0]-pixel2[0], 2)+Math.pow(pixel1[1]-pixel2[1], 2)+Math.pow(pixel1[2]-pixel2[2], 2));
	}
	
	public Semente semente_mais_proxima(double[] rgb) {
		
		double menor_distancia = distancia_rgb(rgb, sementes.get(0).get_cor());
		Semente semente_mais_prox = sementes.get(0);
		
		for(int i = 1; i < sementes.size(); i++) {
			double distancia_atual = distancia_rgb(rgb, sementes.get(i).get_cor()); 
			if(distancia_atual < menor_distancia) {
				menor_distancia = distancia_atual;
				semente_mais_prox = sementes.get(i);
			}
		}
		
		return semente_mais_prox;
	}
	
	private void deslocar_sementes(Mat img_saida) {
		
		for(Semente semente : sementes) {
			semente.set_cor(semente.get_media_rgb());
		}
	}
		
	
	private boolean comparar_pixels (double[] pixel1, double[] pixel2) {
		for(int i =0; i< 3; i++) {
			if(pixel1[i] != pixel2[i]) {
				return false;
			}
		}
		return true;
	}
	
	private boolean existe_cor_sementes(double[] bgr) {
		
		if(sementes.isEmpty()) {
			return false;
		}
		
		for(Semente atual : sementes) {
			if(comparar_pixels(atual.get_cor(),bgr)) {
				return true;
			}
		}
		
		return false;
	}
	
	public Mat k_means(Mat imagem, int qtd_sementes) {
		
		System.out.println("Quantidade de sementes = " + qtd_sementes);
		
		img_entrada = imagem;
		
		// irá gerar as sementes
		for(int i = 0; i < qtd_sementes; i++) {
			
			Random random = new Random();
			
			double[] cor_selecionada;
			
			do {
				cor_selecionada = img_entrada.get(random.nextInt(imagem.rows()), random.nextInt(imagem.cols()));
			} while (existe_cor_sementes(cor_selecionada));
			
			sementes.add(new Semente(img_entrada,cor_selecionada));
		}

		int elemento_alterou;
		
		Mat img_saida;
		
		do {
			img_saida = img_entrada.clone();

			elemento_alterou = 0;
			
			// irá fazer a varredura na imagem associando cada pixel à sua semente mais próxima
			for(int i = 0; i < img_saida.rows(); i++) {
				for(int j = 0; j < img_saida.cols(); j++) {
					
					if(!(semente_mais_proxima(img_entrada.get(i,j)).get_cor() == semente_mais_proxima(img_saida.get(i,j)).get_cor())) {
						elemento_alterou++;
					}
					
					img_saida.put(i, j, semente_mais_proxima(img_entrada.get(i,j)).get_cor());
				}
			}
			
			deslocar_sementes(img_saida);
			
			img_entrada = img_saida.clone();
		} while(elemento_alterou > 0); 
		
		return img_saida;
		
	}
}