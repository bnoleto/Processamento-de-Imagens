package codigos.filtros;

import java.util.Random;

import org.opencv.core.Mat;

public class Funcoes {
	Mat img_entrada;
	private boolean[][] matriz_preenchimento;
	private double[] cor_original;
	
	public double[] random_rgb() {
		
		// irá retornar um RGB aleatório
		
		Random random = new Random();
		
		double[] bgr = new double[3];
		
		bgr[0] = random.nextInt(256);
		bgr[1] = random.nextInt(256);
		bgr[2] = random.nextInt(256);
		
		return bgr;
	}
	
	private void preencher(int x, int y) {
		
		// método recursivo para preencher o pixel atual (x,y) e de seus vizinhos,
		// caso a cor deles seja a mesma cor do pixel inicialmente selecionado
		
		try {
		
			try {
				if(matriz_preenchimento[x][y]) {
					// retornará caso o pixel já tenha sido pintado por uma recursão anterior
					return;
				}	
			} catch (ArrayIndexOutOfBoundsException e) {
				
				// retornará caso a posição atual esteja fora dos limites da imagem
				return;
			}
			
			// se a cor do pixel atual for igual à cor selecionada
			if(comparar_pixels(img_entrada.get(x,y), cor_original)) {
				
				// pintará o pixel da imagem de saída com a cor caso a cor do pixel atual corresponda à cor da imagem original
				//imagem.put(x, y, cor);
				
				matriz_preenchimento[x][y] = true;
				
				// pixel 4-conectado
				preencher(x-1, y);
				preencher(x+1, y);
				preencher(x, y-1);
				preencher(x, y+1);
			}
		
		} catch (StackOverflowError e) {
			
			// comentar a linha abaixo para ver na imagem em qual momento começou a dar estouro de pilha
			e.notify();

			
		}
	}

	
	public void pintar(Mat imagem, int x, int y, double[] cor) {
		
		// *observação: a função do MouseEvent do java está passando X e Y trocados
		
		// *observação 2: ao pintar uma região grande, provavelmente vai dar stack
		// overflow por 2 vezes, mas pintará normalmente depois disso
		
		System.out.println("X = " + y+", Y = " + x);
		
		img_entrada = imagem;
		
		// foi usada uma matriz de boolean para evitar de aplicar a função de put do OpenCV diretamente
		// no Mat em funções recursivas, numa tentativa de otimizar o processamento
		matriz_preenchimento = new boolean[imagem.rows()][imagem.cols()];
		cor_original = imagem.get(x, y);
		
		preencher(x, y);
		
		for(int i = 0; i<imagem.rows(); i++) {
			for(int j = 0; j<imagem.cols(); j++) {
				if(matriz_preenchimento[i][j]) {
					imagem.put(i, j, cor);
				}
			}
		}
		
		return;
		
	}
	
	private boolean comparar_pixels (double[] pixel1, double[] pixel2) {
		
		// irá verificar se 2 pixels possuem a mesma cor
		
		for(int i =0; i< 3; i++) {
			if(pixel1[i] != pixel2[i]) {
				return false;
			}
		}
		return true;
	}
	
}