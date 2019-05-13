package de.himalaya.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HimalayaAPI {

	private String key;
	
	public static enum Modul{

		PRODUKTE (0),
		NUTZER (1),
		CART (2);
		
		private int id;
		private Modul(int id) {
			this.id = id;
		}
		public int getId() {
			return id;
		}
	}

	public static enum Aktion{
		GET,
		SET,
		CREATE,
		DELETE;
	}
	
	public HimalayaAPI(String key) {
		this.setKey(key);
	}
	
	/**
	 * 
	 * @param mod Modul der Anfrage
	 * @param aktion Aktion der Anfrage
	 * @param parameter Parameter als Array
	 * 
	 * @return Antwort als Text (Meist JSON)
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public String performRequest(Modul mod, Aktion aktion, String... parameter) throws MalformedURLException, IOException {
		// URL Zusammenstellen
		String url = "http://api.himalaya.lukas-10100.de/?key=" + this.getKey();
		url += "&mod=" + mod.getId();
		url += "&action=" + URLEncoder.encode(aktion.name().toLowerCase(), "UTF-8");
		url += "&prm=";
		// Jeden Eintrag des "parameter"-Arrays durchgegen und mit einem Semikolon getrennt an die URL anfuegen
		for(int i = 0; i < parameter.length; i++) {
			if(i==parameter.length-1) {
				url += URLEncoder.encode(parameter[i], "UTF-8");
			}else {
				url += URLEncoder.encode(parameter[i] + ";", "UTF-8");
			}
		}
		System.out.println(url);
		
		// Neue HTTP-Verbindung aufbauen
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		
		// Parameter der Anfrage setzen (GET-Methoden nutzen, User-Agent)
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Official API");
		
		// Neuen BufferedReader aus dem InputStreams der HTTP-Verbindung erzeugen
		BufferedReader responseReader = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
		String lines="";
		String line;
		// Solange eine weitere Zeile in der Antwort existiert, diese lesen und an einen gesamten String anfuegen
		while((line=responseReader.readLine())!=null) {
			lines+=line.trim();
		}
		// BufferedReader schließen und Antworttext zurueckgeben
		responseReader.close();
		return lines;
	}
	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
