package com.puduapps.weather.utilities;

/**
 * Created by maximomartinezgonzalez on 6/20/17.
 */

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.puduapps.weather.data.WeatherPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the weather servers.
 */
public class YahooWeatherAPIManager {

    private static final String TAG = YahooWeatherAPIManager.class.getSimpleName();

    private static final String FORECAST_BASE_URL = "http://query.yahooapis.com/v1/public/yql";

    /* The format we want our API to return */
    private static final String sFormat = "json";

    /* The units we want our API to return */
    private static final String sUnits = "metric";

    /* The query parameter allows us to provide a location string to the API */
    private static final String QUERY_PARAM = "q";

    /* The format parameter allows us to designate whether we want JSON or XML from our API */
    private static final String FORMAT_PARAM = "format";

    /* The units parameter allows us to designate whether we want metric units or imperial units */
    private static final String UNITS_PARAM = "u";

    public static YahooResponse getPlacesByCityName(String cityName){

        String locationQuery = MessageFormat.format("select * from geo.places where text = \"{0}\" limit 25",cityName);

        URL url = buildUrlWithLocationQuery(locationQuery);

        String json = null;

        try {
            json = getResponseFromHttpUrl(url);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return new Gson().fromJson(json,YahooResponse.class);

    }

    public static YahooResponse getWeatherDataByWoeid(String woeid){

        String locationQuery = MessageFormat.format("select * from weather.forecast where woeid =\"{0}\"",woeid);

        URL url = buildUrlWithLocationQuery(locationQuery);

        String json = null;

        try {
            json = getResponseFromHttpUrl(url);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return new Gson().fromJson(json,YahooResponse.class);
    }



    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param locationQuery The location that will be queried for.
     * @return The URL to use to query the weather server.
     */
    private static URL buildUrlWithLocationQuery(String locationQuery) {
        Uri weatherQueryUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, locationQuery)
                .appendQueryParameter(FORMAT_PARAM, sFormat)
                .build();

        try {
            URL weatherQueryUrl = new URL(weatherQueryUri.toString());
            Log.v(TAG, "URL: " + weatherQueryUrl);
            return weatherQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network and stream reading
     */
    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

    public class YahooResponse {
        private Query query;

        public Query getQuery() {
            return query;
        }

        public void setQuery(Query query) {
            this.query = query;
        }
    }

    public class Query {

        private Results results;

        private String count;

        private String created;

        private String lang;

        public Results getResults() {
            return results;
        }

        public void setResults(Results results) {
            this.results = results;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

    }

    public class Results {
        private Channel channel;
        private List<Place> place;

        public Channel getChannel () {
            return channel;
        }

        public void setChannel (Channel channel) {
            this.channel = channel;
        }

        public List<Place> getPlace() {
            return place;
        }

        public void setPlace(List<Place> place) {
            this.place = place;
        }
    }

    public class Channel {

        private Wind wind;

        private Location location;

        private String link;

        private Atmosphere atmosphere;

        private Image image;

        private String ttl;

        private Astronomy astronomy;

        private Units units;

        private String title;

        private String description;

        private Item item;

        private String lastBuildDate;

        private String language;

        public Wind getWind () {
            return wind;
        }

        public void setWind (Wind wind) {
            this.wind = wind;
        }

        public Location getLocation () {
            return location;
        }

        public void setLocation (Location location) {
            this.location = location;
        }

        public String getLink () {
            return link;
        }

        public void setLink (String link) {
            this.link = link;
        }

        public Atmosphere getAtmosphere () {
            return atmosphere;
        }

        public void setAtmosphere (Atmosphere atmosphere) {
            this.atmosphere = atmosphere;
        }

        public Image getImage () {
            return image;
        }

        public void setImage (Image image) {
            this.image = image;
        }

        public String getTtl () {
            return ttl;
        }

        public void setTtl (String ttl) {
            this.ttl = ttl;
        }

        public Astronomy getAstronomy () {
            return astronomy;
        }

        public void setAstronomy (Astronomy astronomy) {
            this.astronomy = astronomy;
        }

        public Units getUnits () {
            return units;
        }

        public void setUnits (Units units) {
            this.units = units;
        }

        public String getTitle () {
            return title;
        }

        public void setTitle (String title) {
            this.title = title;
        }

        public String getDescription () {
            return description;
        }

        public void setDescription (String description) {
            this.description = description;
        }

        public Item getItem () {
            return item;
        }

        public void setItem (Item item) {
            this.item = item;
        }

        public String getLastBuildDate () {
            return lastBuildDate;
        }

        public void setLastBuildDate (String lastBuildDate) {
            this.lastBuildDate = lastBuildDate;
        }

        public String getLanguage (){
            return language;
        }

        public void setLanguage (String language){
            this.language = language;
        }

    }


    public class Item{

        private Guid guid;

        private String pubDate;

        private String title;

        private Forecast[] forecast;

        private Condition condition;

        private String description;

        private String link;

        @SerializedName("long")
        private String longitude;

        @SerializedName("lat")
        private String latitude;

        public Guid getGuid (){
            return guid;
        }

        public void setGuid (Guid guid){
            this.guid = guid;
        }

        public String getPubDate (){
            return pubDate;
        }

        public void setPubDate (String pubDate){
            this.pubDate = pubDate;
        }

        public String getTitle (){
            return title;
        }

        public void setTitle (String title){
            this.title = title;
        }

        public Forecast[] getForecast (){
            return forecast;
        }

        public void setForecast (Forecast[] forecast){
            this.forecast = forecast;
        }

        public Condition getCondition (){
            return condition;
        }

        public void setCondition (Condition condition){
            this.condition = condition;
        }

        public String getDescription (){
            return description;
        }

        public void setDescription (String description){
            this.description = description;
        }

        public String getLink (){
            return link;
        }

        public void setLink (String link){
            this.link = link;
        }

        public String getLongitude (){
            return longitude;
        }

        public void setLongitude (String longitude){
            this.longitude = longitude;
        }

        public String getLatitude (){
            return latitude;
        }

        public void setLatitude (String lat){
            this.latitude = lat;
        }

    }

    public class Forecast{
        private String text;

        private String high;

        private String day;

        private String code;

        private String low;

        private String date;

        public String getText (){
            return text;
        }

        public void setText (String text){
            this.text = text;
        }

        public String getHigh (){
            return high;
        }

        public void setHigh (String high){
            this.high = high;
        }

        public String getDay (){
            return day;
        }

        public void setDay (String day){
            this.day = day;
        }

        public String getCode (){
            return code;
        }

        public void setCode (String code){
            this.code = code;
        }

        public String getLow (){
            return low;
        }

        public void setLow (String low){
            this.low = low;
        }

        public String getDate (){
            return date;
        }

        public void setDate (String date){
            this.date = date;
        }

    }

    public class Condition {
        private String text;

        private String temp;

        private String code;

        private String date;

        public String getText () {
            return text;
        }

        public void setText (String text) {
            this.text = text;
        }

        public String getTemp () {
            return temp;
        }

        public void setTemp (String temp) {
            this.temp = temp;
        }

        public String getCode () {
            return code;
        }

        public void setCode (String code) {
            this.code = code;
        }

        public String getDate () {
            return date;
        }

        public void setDate (String date) {
            this.date = date;
        }

    }

    public class Astronomy {
        private String sunset;

        private String sunrise;

        public String getSunset (){
            return sunset;
        }

        public void setSunset (String sunset){
            this.sunset = sunset;
        }

        public String getSunrise (){
            return sunrise;
        }

        public void setSunrise (String sunrise){
            this.sunrise = sunrise;
        }

    }

    public class Atmosphere {
        private String rising;

        private String humidity;

        private String pressure;

        private String visibility;

        public String getRising (){
            return rising;
        }

        public void setRising (String rising){
            this.rising = rising;
        }

        public String getHumidity () {
            return humidity;
        }

        public void setHumidity (String humidity) {
            this.humidity = humidity;
        }

        public String getPressure () {
            return pressure;
        }

        public void setPressure (String pressure) {
            this.pressure = pressure;
        }

        public String getVisibility () {
            return visibility;
        }

        public void setVisibility (String visibility) {
            this.visibility = visibility;
        }

    }

    public class Wind {

        private String speed;

        private String direction;

        private String chill;

        public String getSpeed (){
            return speed;
        }

        public void setSpeed (String speed){
            this.speed = speed;
        }

        public String getDirection (){
            return direction;
        }

        public void setDirection (String direction){
            this.direction = direction;
        }

        public String getChill (){
            return chill;
        }

        public void setChill (String chill){
            this.chill = chill;
        }

    }

    public class Location{

        private String region;

        private String country;

        private String city;

        public String getRegion (){
            return region;
        }

        public void setRegion (String region){
            this.region = region;
        }

        public String getCountry (){
            return country;
        }

        public void setCountry (String country){
            this.country = country;
        }

        public String getCity (){
            return city;
        }

        public void setCity (String city){
            this.city = city;
        }

    }

    public class Units{

        private String distance;

        private String pressure;

        private String speed;

        private String temperature;

        public String getDistance (){
            return distance;
        }

        public void setDistance (String distance){
            this.distance = distance;
        }

        public String getPressure (){
            return pressure;
        }

        public void setPressure (String pressure){
            this.pressure = pressure;
        }

        public String getSpeed (){
            return speed;
        }

        public void setSpeed (String speed){
            this.speed = speed;
        }

        public String getTemperature (){
            return temperature;
        }

        public void setTemperature (String temperature){
            this.temperature = temperature;
        }

    }

    public class Guid {
        private String isPermaLink;

        public String getIsPermaLink ()
        {
            return isPermaLink;
        }

        public void setIsPermaLink (String isPermaLink)
        {
            this.isPermaLink = isPermaLink;
        }

    }

    public class Image
    {
        private String title;

        private String height;

        private String link;

        private String width;

        private String url;

        public String getTitle ()
        {
            return title;
        }

        public void setTitle (String title)
        {
            this.title = title;
        }

        public String getHeight ()
        {
            return height;
        }

        public void setHeight (String height)
        {
            this.height = height;
        }

        public String getLink ()
        {
            return link;
        }

        public void setLink (String link)
        {
            this.link = link;
        }

        public String getWidth ()
        {
            return width;
        }

        public void setWidth (String width)
        {
            this.width = width;
        }

        public String getUrl ()
        {
            return url;
        }

        public void setUrl (String url)
        {
            this.url = url;
        }

    }

    public class Place{

        private String name;
        private String popRank;
        private String woeid;
        private LatLong boundingBox;
        private LatLong centroid;
        private Region admin1;
        private Region admin2;
        private Region country;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPopRank() {
            return popRank;
        }

        public void setPopRank(String popRank) {
            this.popRank = popRank;
        }

        public String getWoeid() {
            return woeid;
        }

        public void setWoeid(String woeid) {
            this.woeid = woeid;
        }

        public LatLong getBoundingBox() {
            return boundingBox;
        }

        public void setBoundingBox(LatLong boundingBox) {
            this.boundingBox = boundingBox;
        }

        public LatLong getCentroid() {
            return centroid;
        }

        public void setCentroid(LatLong centroid) {
            this.centroid = centroid;
        }

        public Region getAdmin1() {
            return admin1;
        }

        public void setAdmin1(Region admin1) {
            this.admin1 = admin1;
        }

        public Region getAdmin2() {
            return admin2;
        }

        public void setAdmin2(Region admin2) {
            this.admin2 = admin2;
        }

        public Region getCountry() {
            return country;
        }

        public void setCountry(Region country) {
            this.country = country;
        }
    }

    public class LatLong{
        private float latitude;
        private float longitude;
    }

    public class Region{

        private String code;
        private String content;
        private String type;
        private String woeid;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWoeid() {
            return woeid;
        }

        public void setWoeid(String woeid) {
            this.woeid = woeid;
        }
    }

}