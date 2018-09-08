import org.bytedeco.javacv.*;


import static org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvFlip;
//import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;


/**
 * Created by gtiwari on 1/3/2017.
 */

/*public class GrabPhoto implements Runnable {
	private int shutter = 100;///you may use interval
	CanvasFrame canvas = new CanvasFrame("Web Cam");

	public GrabPhoto() {
		canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

	}

	public void run() {

		FrameGrabber grabber = new VideoInputFrameGrabber(0); // 1 for next camera
		OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
		IplImage img;
		int i = 0;
		try {
			grabber.start();
			while (true && i <= 1)
			{
				Frame frame = grabber.grab();

				img = converter.convert(frame);

				//the grabbed frame will be flipped, re-flip to make it right
				cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise

				//save
				cvSaveImage("saved.jpg", img);

				canvas.showImage(converter.convert(img));

				Thread.sleep(shutter);
				i++;
			}*/

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				grabber.release();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}


}