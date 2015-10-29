//package edu.rosehulman.wordcrossingshelper;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Queries Merriam Webster's online dictionary service for this word's definition.
 * Network tasks must be asynchronous, so we use a task for this.
 *
 * Created by Matt Boutell
 */
class GetDefinitionTask extends AsyncTask<String, Void, String> {
    private String mWord;
    //private DefinitionConsumer mActivity;

//    public GetDefinitionTask(DefinitionConsumer activity) {
//        mActivity = activity;
//    }

    @Override
    protected String doInBackground(String... words) {
        URL url = null;
        String s = "Not found";
        mWord = words[0];
        try {
            String urlString = String.format("http://www.dictionaryapi.com/api/v1/references/collegiate/xml/%s?key=(removed for security)", mWord);
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            s = parseXml(in);
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return s;
    }

    @Override
    protected void onPostExecute(String definition) {
        super.onPostExecute(definition);
        //mActivity.useDefinition(mWord, definition);
    }

    private String parseXml(InputStream in) {

        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        InputStream is;
        Document dom = null;
        try {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            dom = builder.parse(in);
            Log.d("WCH", "Got dom in try " + dom);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("WCH", "Got dom " + dom);

        if (dom != null) {
            NodeList nodes = dom.getElementsByTagName("dt");
            Log.d("WCH", "Got nodes " + nodes);
            if (nodes == null) {
                return "not found";
            }
            String def = nodes.item(0).getTextContent();
            return def;
        }
        return "not found";
    }

}

