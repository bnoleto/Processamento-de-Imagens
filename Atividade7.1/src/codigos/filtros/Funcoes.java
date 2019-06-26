package codigos.filtros;

import java.util.Random;

import org.opencv.core.Mat;

public class Funcoes {
	Mat img_entrada;
	private boolean[][] matriz_preenchimento;
	private double[] cor_original;

	public Funcoes() {
		
		// irá pegar o caminho da imagem original e usará a função do openCV pra armazenar no Mat
		
	}
	
	public double[] random_rgb() {
		Random random = new Random();
		
		double[] bgr = new double[3];
		
		bgr[0] = random.nextInt(256);
		bgr[1] = random.nextInt(256);
		bgr[2] = random.nextInt(256);
		
		return bgr;
	}
	
	private void preencher(int x, int y) {
		
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
			
			
			/*
			buffer.add(new Coordenada(x,y));
			buffer.add(new Coordenada(x-1,y));
			buffer.add(new Coordenada(x+1,y));
			buffer.add(new Coordenada(x,y-1));
			buffer.add(new Coordenada(x,y+1));*/
		}
	}

	
	public void pintar(Mat imagem, int x, int y, double[] cor) {
		
		System.out.println("X = " + x+", Y = " + y);
		
		img_entrada = imagem;
		
		
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
		for(int i =0; i< 3; i++) {
			if(pixel1[i] != pixel2[i]) {
				return false;
			}
		}
		return true;
	}
	
}