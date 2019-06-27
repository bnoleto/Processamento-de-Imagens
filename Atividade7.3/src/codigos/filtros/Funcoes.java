package codigos.filtros;

import java.util.ArrayList;
import java.util.Random;

import org.opencv.core.Mat;

public class Funcoes {
	Mat img_entrada;
	
	private ArrayList<Semente> sementes = new ArrayList<Semente>();
	
	double distancia_rgb(double[] pixel1,double[] pixel2) {
		// distancia euclidiana em 3 dimensões entre 2 cores
		
		return Math.sqrt(Math.pow(pixel1[0]-pixel2[0], 2)+Math.pow(pixel1[1]-pixel2[1], 2)+Math.pow(pixel1[2]-pixel2[2], 2));
	}
	
	public Semente semente_mais_proxima(double[] rgb) {
		
		// irá retornar a semente de menor distância ao RGB especificado
		
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
		
		// irá deslocar as sementes com base nos pixels pertencentes de cada uma
		
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
		
		// função principal do K-means
		
		System.out.println("Quantidade de sementes = " + qtd_sementes);
		
		img_entrada = imagem;
		
		// irá gerar as sementes com X e Y aleatórios
		for(int i = 0; i < qtd_sementes; i++) {
			
			Random random = new Random();
			
			double[] cor_selecionada;
			
			// garantirá que cada semente tenha uma cor diferente
			do {
				cor_selecionada = img_entrada.get(random.nextInt(imagem.rows()), random.nextInt(imagem.cols()));
			} while (existe_cor_sementes(cor_selecionada));
			
			sementes.add(new Semente(img_entrada,cor_selecionada));
		}

		int elementos_alterados;
		
		Mat img_saida;
		
		// laço repetirá enquanto existir algum elemento que tenha mudado de classe (semente)
		do {
			img_saida = img_entrada.clone();

			elementos_alterados = 0;
			
			// irá fazer a varredura na imagem associando cada pixel a sua semente mais próxima
			for(int i = 0; i < img_saida.rows(); i++) {
				for(int j = 0; j < img_saida.cols(); j++) {
					
					// se a semente do pixel atual na etapa anterior for diferente da sua semente na etapa atual
					if(!(semente_mais_proxima(img_entrada.get(i,j)).get_cor() == semente_mais_proxima(img_saida.get(i,j)).get_cor())) {
						elementos_alterados++;
					}
					
					img_saida.put(i, j, semente_mais_proxima(img_entrada.get(i,j)).get_cor());
				}
			}
			
			deslocar_sementes(img_saida);
			
			// atualizará a imagem para o próximo laço fazer novos cálculos
			img_entrada = img_saida.clone();
			
		} while(elementos_alterados > 0); 
		
		return img_saida;
		
	}
}