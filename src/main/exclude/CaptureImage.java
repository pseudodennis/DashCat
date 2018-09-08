//import com.googlecode.javacv.OpenCVFrameGrabber;
//import com.googlecode.javacv.cpp.opencv_core.IplImage;
//import static com.googlecode.javacv.cpp.opencv_highgui.*;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;

public class CaptureImage
{
	private static void captureFrame()
	{
		// 0-default camera, 1 - next...so on
		final OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
		try
		{
			grabber.start();
			Frame frame = grabber.grab();
			OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

			opencv_core.IplImage img;
			img = converter.convert(frame);


			if (img != null)
			{
				cvSaveImage("image.jpg", img);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
