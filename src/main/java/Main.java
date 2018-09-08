import clarifai2.api.ClarifaiBuilder;

public class Main
{
	public static void main(String[] args)
	{
		System.out.println("Hello World");

		System.out.println(getKey());

		new ClarifaiBuilder(getKey())
				// .client(new OkHttpClient()) // OPTIONAL. Allows customization of OkHttp by the user
				.buildSync(); // or use .build() to get a Future<ClarifaiClient>

		GrabPhoto gs = new GrabPhoto();
		gs.run();


	}

	public static String getKey()
	{
		String key;
		Key APIkey = new Key();
		return APIkey.getKey();
	}
}
