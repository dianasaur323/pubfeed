package com.pubfeed.qq.pubfeed;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.renderscript.Element;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class QueryPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_page);

        new RequestTask().execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.query_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            DocumentBuilderFactory documentBuilderFactory;
            DocumentBuilder documentBuilder;
            Document document;

            String baseURL = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/";
            String url = baseURL + "esearch.fcgi?db=pubmed&term=asthma[mesh]&usehistory=y";

            //pull relevant UIDs

            ArrayList <String> uidList=new ArrayList<String>();

            try {
                documentBuilderFactory = DocumentBuilderFactory.newInstance();
                documentBuilder = documentBuilderFactory.newDocumentBuilder();
                document = documentBuilder.parse(new URL(url).openStream());

                Log.d("Test", "what");

                NodeList nodeList = document.getElementsByTagName("Id");

                Log.d("Test",""+nodeList.getLength());

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    uidList.add(node.getTextContent());
                }

            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            ByteArrayOutputStream out=new ByteArrayOutputStream();
//
//            try {
//                response.getEntity().writeTo(out);
//                Log.d("test",out.toString());
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Log.d("test", out.toString());

//            try {
//                DocumentBuilder db=DocumentBuilderFactory.newInstance().newDocumentBuilder();
//                InputSource inputSource=new InputSource();
//                inputSource.setCharacterStream(new StringReader(out.toString()));
//
//                Document UIDs=db.parse(inputSource);
//                NodeList nodes=UIDs.getElementsByTagName("Id");
//
//                Log.d("Test","nodes are"+nodes.toString());
//
//                for(int i=0;i<nodes.getLength();i++){
//                    org.w3c.dom.Element element=(org.w3c.dom.Element) nodes.item(i);
//
//                    NodeList name=element.getElementsByTagName()
//                }
//            } catch (ParserConfigurationException e) {
//                e.printStackTrace();
//            } catch (SAXException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            return null;
        }
    }
}
