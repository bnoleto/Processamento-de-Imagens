package telas;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tela extends JFrame {
	
	public Tela(String filename, Mat imagem) {
		
		setTitle(filename);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel label_imagem = new javax.swing.JLabel(new javax.swing.ImageIcon(HighGui.toBufferedImage(imagem)));
		
		getContentPane().add(label_imagem);
		setResizable(false);
		pack();
		setVisible(true);
	}
}
