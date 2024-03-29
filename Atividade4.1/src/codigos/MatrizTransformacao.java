package codigos;

import java.util.ArrayList;

public class MatrizTransformacao {

		private double matriz[][] = new double[3][3];
		
		private ArrayList<Pixel> lista_pixels;
		private Pixel centro_massa;

		private double rad(double angulo){	// converterá de graus para radianos
			return angulo*3.141592/180;
		}

		private void reset(){
			for(int i = 0; i<3; i++){
				for(int j = 0; j<3; j++){
					if(i == j){
						matriz[i][j] = 1.0;
					}
					else{
						matriz[i][j] = 0.0;
					}
				}
			}
		}

		private void aplicar_transformacao(){
			
			for(int linha = 0; linha < lista_pixels.size(); linha++){	// para cada ponto do conjunto
					
				Pixel pixel_atual = lista_pixels.get(linha);

				double matriz_mult[] = {pixel_atual.get_x(),pixel_atual.get_y(), 1};			
				
				matriz_mult[0] -= centro_massa.get_x();
				matriz_mult[1] -= centro_massa.get_y();

				double x_final = 0.0;
				for(int i_matriz = 0; i_matriz < 3; i_matriz++){
					//System.out.println(matriz[0][i_matriz]);
					x_final+= matriz[0][i_matriz] * matriz_mult[i_matriz];
				}

				double y_final = 0.0;
				for(int i_matriz = 0; i_matriz < 3; i_matriz++){
					y_final+= matriz[1][i_matriz] * matriz_mult[i_matriz];
				}

				x_final += centro_massa.get_x();
				y_final += centro_massa.get_y();
				
				pixel_atual.set_coordenadas(Math.round(x_final), Math.round(y_final));
			}


			reset();
		}

		MatrizTransformacao(ArrayList<Pixel> pontos, Pixel centro_massa){
			this.centro_massa = centro_massa;
			this.lista_pixels = pontos;
			
			reset();
		}

		public void translacao(double delta_x, double delta_y){
			matriz[0][2] = delta_x;
			matriz[1][2] = delta_y;
			aplicar_transformacao();
		}

		public void rotacao(double angulo){
			/*
			matriz[0][0] = Math.cos(rad(angulo));
			matriz[0][1] = -Math.sin(rad(angulo));
			matriz[1][0] = Math.sin(rad(angulo));
			matriz[1][1] = Math.cos(rad(angulo));
			
			aplicar_transformacao();*/
			
			matriz[0][0] = 1;
			matriz[0][1] = -Math.tan(rad(angulo/2));
			matriz[1][0] = 0;
			matriz[1][1] = 1;
			
			aplicar_transformacao();
			
			matriz[0][0] = 1;
			matriz[0][1] = 0;
			matriz[1][0] = Math.sin(rad(angulo));
			matriz[1][1] = 1;
			
			aplicar_transformacao();
			
			matriz[0][0] = 1;
			matriz[0][1] = -Math.tan(rad(angulo/2));
			matriz[1][0] = 0;
			matriz[1][1] = 1;
			
			aplicar_transformacao();
			
		}

		public void atualizar_matriz(){
			reset();
		}
}

