package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

// Handler value: example.HandlerWeatherData
public class HandlerWeatherData implements RequestHandler<WeatherData, WeatherData>{
  Gson gson = new GsonBuilder().setPrettyPrinting().create();
  @Override
  public WeatherData handleRequest(WeatherData event, Context context)
  {
    LambdaLogger logger = context.getLogger();
    // process event
    logger.log("EVENT: " + gson.toJson(event));
    logger.log("EVENT TYPE: " + event.getClass().toString());

    event.setTemperatureK(100);

    WeatherData response = new WeatherData();
    response.setTemperatureK(200);
    logger.log("RESPONSE: " + gson.toJson(response));

    return response;
  }
}