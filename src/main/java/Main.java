import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.input.SearchClause;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameGrabber;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.*;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
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
	public static void main(String[] args) throws IOException, FrameGrabber.Exception, InterruptedException, FrameRecorder.Exception

	{
		String timeNow = System.currentTimeMillis() + "";
		String tempMov = "/Users/dtrate/Desktop/dashCatTEMP.mov";

		// get a clip from the mac webcam
		runApplescript();

		Thread.sleep(45000);

		// extract frames from video
		framed();

		// send the frames to Clarifi
		boolean matchesKeyword = false;
		String score = clarifySend();
		//System.out.println(score);

		if (score.contains("score="))
		{
			int scoreStart = (score.indexOf("score=")) + 6;
			int scoreEnd = scoreStart + 5;
			String scoreNew = score.substring(scoreStart, scoreEnd);
			System.out.println(scoreNew);
			double scoreReal = Double.parseDouble(scoreNew);
			if (scoreReal > 0.5)
			{
				matchesKeyword = true;
			}

		}
		else
			System.out.println(score);

		// rename the file if a match
		//String oldName = "/Users/dtrate/Desktop/dashCatTEMP.mov";
		String oldName = "/Users/dtrate/Desktop/temp.mov";
		String newName = "/Users/dtrate/Desktop/keeper-" + timeNow + ".mov";
		if (matchesKeyword)
		{
			rename(oldName, newName);
		}
		else
		{
			System.out.println("object not found in clip");
		}

	} // end main()

	public static void rename(String oldName, String newName)
	{
		//absolute path rename file
		// File file = new File("/Users/pankaj/java.txt");
		File file = new File(oldName);
		File newFile = new File(newName);
		if (file.renameTo(newFile))
		{
			System.out.println("File rename success");
			;
		}
		else
		{
			System.out.println("File rename failed");
		}
	} // end rename()


	public static String clarifySend()
	{
		final ClarifaiClient client = new ClarifaiBuilder(getKey()).buildSync();

		Model<Concept> generalModel = client.getDefaultModels().generalModel();

		PredictRequest<Concept> request = generalModel.predict().withInputs(
				ClarifaiInput.forImage("https://samples.clarifai.com/metro-north.jpg")
		);
		List<ClarifaiOutput<Concept>> result = request.executeSync().get();


		//System.out.println(result.get(0));

		client.addInputs()
				.plus(
						ClarifaiInput.forImage(new File("/Users/dtrate/Desktop/DashCat/frames/10.png")),
						ClarifaiInput.forImage(new File("/Users/dtrate/Desktop/DashCat/frames/11.png")),
						ClarifaiInput.forImage(new File("/Users/dtrate/Desktop/DashCat/frames/12.png")),
						ClarifaiInput.forImage(new File("/Users/dtrate/Desktop/DashCat/frames/13.png")),
						ClarifaiInput.forImage(new File("/Users/dtrate/Desktop/DashCat/frames/14.png")),
						ClarifaiInput.forImage(new File("/Users/dtrate/Desktop/DashCat/frames/15.png"))
				)
				.executeSync();
/*
		System.out.println(client.searchInputs(SearchClause.matchConcept(Concept.forName("cat")))
				.getPage(1)
				.executeSync().get());
		*/

		return client.searchInputs(SearchClause.matchConcept(Concept.forName("apple")))
				.getPage(1)
				.executeSync().get().toString();

	}


	public static void framed() throws FrameGrabber.Exception, IOException
	{

		// FFmpegFrameGrabber g = new FFmpegFrameGrabber("/Users/dtrate/Desktop/DashCat/DubstepCat.mp4");
		// g.start();

		FFmpegFrameGrabber g = new FFmpegFrameGrabber("/Users/dtrate/Desktop/temp.mov");

		//g.setFormat("mov");
		g.start();
		int frameCount = g.getLengthInFrames();
		System.out.println(frameCount + " frames in video file");
		// 3600 = 2 mins; 300 = 10 secs
		for (int i = 0, filename=10 ; i < frameCount ; i++)
		{
			BufferedImage bi = g.grab().getBufferedImage();
			if (i%20==0)
			{
				ImageIO.write(bi, "png", new File("/Users/dtrate/Desktop/DashCat/frames/" + filename + ".png"));
				filename++;
			}

		}



		g.stop();
	}

	// record a clip using the mac webcam
	public static boolean runApplescript() throws IOException
	{
		Runtime runtime = Runtime.getRuntime();
		String applescriptCommand =
				"set theFilePath to \"/Users/dtrate/Desktop/temp.mov\"\n" +
				"tell application \"QuickTime Player\"\n" +
				"set newMovieRecording to new movie recording\n" +
				"tell newMovieRecording\n" +
				"start\n" +
				"delay 35\n" +
				"pause\n" +
				"save newMovieRecording in POSIX file theFilePath\n" +
				"stop\n" +
				"close newMovieRecording\n" +
				"end tell\n" +
				"end tell";
		String[] args = { "osascript", "-e", applescriptCommand };
		Process process = runtime.exec(args);
		return true;
	}

	public static String getKey()
	{
		String key;
		Key APIkey = new Key();
		return APIkey.getKey();
	}


} // end Main
