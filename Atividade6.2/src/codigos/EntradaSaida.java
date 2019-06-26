package codigos;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class EntradaSaida {
	
	// fun��o para abrir no desktop o arquivo especificado
	static void abrir_arquivo(String filename, Mat imagem) {
		
		javax.swing.JFrame frame = new javax.swing.JFrame(filename);
		frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(new javax.swing.JLabel(new javax.swing.ImageIcon(HighGui.toBufferedImage(imagem))));
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
	}
	
	static void escrever_arquivo(String filename, Mat imagem) {
		System.out.println(String.format("Escrevendo "+ filename));
		Imgcodecs.imwrite(filename, imagem);
	}
}
