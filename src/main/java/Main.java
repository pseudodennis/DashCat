import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main
{
	public static void main(String[] args)
	{
		System.out.println("Hello World");

		System.out.println(getKey());

		/*
		new ClarifaiBuilder(getKey())
				// .client(new OkHttpClient()) // OPTIONAL. Allows customization of OkHttp by the user
				.buildSync(); // or use .build() to get a Future<ClarifaiClient>
*/

		final ClarifaiClient client = new ClarifaiBuilder("ff4e4cbe32354fea96afbfe46bf3acba").buildSync();

		Model<Concept> generalModel = client.getDefaultModels().generalModel();

		PredictRequest<Concept> request = generalModel.predict().withInputs(
				ClarifaiInput.forImage("https://samples.clarifai.com/metro-north.jpg")
		);
		List<ClarifaiOutput<Concept>> result = request.executeSync().get();

		System.out.println(result.get(0));





		/*
		GrabPhoto gs = new GrabPhoto();
		gs.run();
		*/

	}

	public static String getKey()
	{
		String key;
		Key APIkey = new Key();
		return APIkey.getKey();
	}
}
