package codigos;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class Funcoes {
	private String filename;
	private Mat img_entrada;
	
	public Funcoes(String filename) {
		this.filename = filename;
		
		// irá pegar o caminho da imagem original e usará a função do openCV pra armazenar no Mat
		img_entrada = Imgcodecs.imread(getClass().getClassLoader().getResource("resources/"+filename).getPath().substring(1));
	
	}
	
	// abre a imagem original no desktop pra comparação
	public void abrir_img_original() {
		
		abrir_arquivo(filename, img_entrada);
		
	}
	
	// função para abrir no desktop o arquivo especificado
	private void abrir_arquivo(String filename, Mat imagem) {
		
		javax.swing.JFrame frame = new javax.swing.JFrame(filename);
		frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(new javax.swing.JLabel(new javax.swing.ImageIcon(HighGui.toBufferedImage(imagem))));
		frame.pack();
		frame.setVisible(true);
		
	}
	
	private void escrever_arquivo(String prefixo, Mat imagem) {
		System.out.println(String.format("Escrevendo "+ prefixo + filename));
		Imgcodecs.imwrite(prefixo+filename, imagem);
	}
	
	private double[] inverter_pixel(double[] pixel_entrada) {
		double[] pixel_saida = new double[3];
		
		for(int i = 0; i<3; i++) {
			pixel_saida[i] = 255 - pixel_entrada[i];
		}
		
		return pixel_saida;
		
	}
	
	public void inverter_rgb() {
		
		String filename_prefixo = "inverso_";
		
		System.out.println("\nAplicando Inversão em RGB");
		
		Mat img_saida = new Mat(img_entrada.rows(),img_entrada.cols(),img_entrada.type());

		for(int linha = 0; linha < img_entrada.rows(); linha++) {
			for(int coluna = 0; coluna < img_entrada.cols(); coluna++) {
				
				double[] pixel_atual = img_entrada.get(linha, coluna);
				
				img_saida.put(linha, coluna, inverter_pixel(pixel_atual));

			}
		}
		
		escrever_arquivo(filename_prefixo, img_saida);
		
		abrir_arquivo(filename_prefixo+filename, img_saida);
	}
}