package teste_opencv;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

class Funcoes {
	  public void DetectFaceDemo() {
	    System.out.println("\nRunning DetectFaceDemo");
	    
	    String filename = "kingsman.jpg";

	    // Create a face detector from the cascade file in the resources
	    // directory.
	    CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("lbpcascade_frontalface.xml").getPath().substring(1));
	    Mat image = Imgcodecs.imread(getClass().getResource(filename).getPath().substring(1));

	    // Detect faces in the image.
	    // MatOfRect is a special container class for Rect.
	    MatOfRect faceDetections = new MatOfRect();
	    faceDetector.detectMultiScale(image, faceDetections);

	    System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

	    // Draw a bounding box around each face.
	    for (Rect rect : faceDetections.toArray()) {
	    	
	    	Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
	    }

	    // Save the visualized detection.
	    System.out.println(String.format("Writing %s", filename));
	    Imgcodecs.imwrite(filename, image);
	  }
	}
	
public class Programa {
	public static void main(String[] args) {
		System.load("C:/opencv_java401/libopencv_java401.dll");
		
		new Funcoes().DetectFaceDemo();
	}

}
