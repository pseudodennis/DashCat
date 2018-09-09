import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.input.SearchClause;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import org.bytedeco.javacv.FrameGrabber;

import org.bytedeco.javacv.FFmpegFrameGrabber;
// import org.bytedeco.javacv.FrameGrabber;
//import org.bytedeco.javacv.avutil;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



//import org.opencv.videoio.VideoCapture;

/*
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;
*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main
{
	public static void main(String[] args) throws IOException, FrameGrabber.Exception
	{
		System.out.println("Hello World");

		framed();

		// get a clip from the mac webcam
		runApplescript();

		// send video file to Clarifi
		clarifySend();

	} // end main()

	public static void clarifySend()
	{
		System.out.println(getKey());

		/*
		new ClarifaiBuilder(getKey())
				// .client(new OkHttpClient()) // OPTIONAL. Allows customization of OkHttp by the user
				.buildSync(); // or use .build() to get a Future<ClarifaiClient>
		*/

		final ClarifaiClient client = new ClarifaiBuilder(getKey()).buildSync();

		Model<Concept> generalModel = client.getDefaultModels().generalModel();

		PredictRequest<Concept> request = generalModel.predict().withInputs(
				ClarifaiInput.forImage("https://samples.clarifai.com/metro-north.jpg")
		);
		List<ClarifaiOutput<Concept>> result = request.executeSync().get();


		System.out.println(result.get(0));

		client.addInputs()
				.plus(
						ClarifaiInput.forImage("https://samples.clarifai.com/metro-north.jpg"),
						ClarifaiInput.forImage("https://samples.clarifai.com/wedding.jpg")
				)
				.executeSync();

		System.out.println(client.searchInputs(SearchClause.matchConcept(Concept.forName("people")))
				.getPage(1)
				.executeSync().get());
	}


	public static void framed() throws FrameGrabber.Exception, IOException {

		FFmpegFrameGrabber g = new FFmpegFrameGrabber("/Users/dtrate/Desktop/DashCat/DubstepCat.mp4");
		g.start();



		for (int i = 0 ; i < 100 ; i++) {
			if (i%30==0)
			{
				ImageIO.write(g.grab().getBufferedImage(), "png", new File("/Users/dtrate/Desktop/DashCat/frames/" +
						System.currentTimeMillis() + ".png"));
			}
			else
			{
				g.grab().getBufferedImage();
			}

			}

		g.stop();
	}


	// record a clip using the mac webcam
	public static void runApplescript() throws IOException
	{
		Runtime runtime = Runtime.getRuntime();
		String applescriptCommand =
				"set theFilePath to \"/Users/dtrate/Desktop/dashCat.mov\"\n" +
				"tell application \"QuickTime Player\"\n" +
				"set newMovieRecording to new movie recording\n" +
				"tell newMovieRecording\n" +
				"start\n" +
				"delay 8\n" +
				"pause\n" +
				"save newMovieRecording in POSIX file theFilePath\n" +
				"stop\n" +
				"close newMovieRecording\n" +
				"end tell\n" +
				"end tell";
		String[] args = { "osascript", "-e", applescriptCommand };
		Process process = runtime.exec(args);
	}

	public static String getKey()
	{
		String key;
		Key APIkey = new Key();
		return APIkey.getKey();
	}


} // end Main
