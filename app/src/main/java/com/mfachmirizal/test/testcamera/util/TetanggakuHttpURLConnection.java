package com.mfachmirizal.test.testcamera.util;

/**
 * Created by mfachmirizal on 19-Aug-15.
 */

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import javax.net.ssl.HttpsURLConnection;


/**
 * Created by mfachmirizal on 20-07-15.
 */
public class TetanggakuHttpURLConnection {
    static boolean isdebug = true;
    //Context context;

    public TetanggakuHttpURLConnection() {

    }

    public String  performPostXML (String requestURL,
                                   String xml ,String requestmethod ) {

        URL url;
        HttpURLConnection conn = null;
        String response = "";
        try {
            url = new URL(requestURL);
            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(60000);
            conn.setConnectTimeout(60000);
            conn.setRequestProperty("Content-Type", "text/xml"); //tambah ini
            conn.setAllowUserInteraction(false);
            conn.setDefaultUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestmethod.toUpperCase());
            conn.setDoInput(true);
            conn.setDoOutput(true);


            if (isdebug) Log.i("Test POST Data", "ISI : " + xml);
            OutputStream os = conn.getOutputStream();
            if (xml != null) {
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                writer.write(xml);
                writer.flush();
                writer.close();
            }
            if (isdebug) Log.i("ok", "ok");
            int responseCode=conn.getResponseCode();
            if (isdebug) Log.i("Test POST ResponseCode", "ResponseCode: " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                //response="";
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line=br.readLine()) != null) {
                    response += line;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null) conn.disconnect();
                if (conn.getInputStream() != null) conn.getInputStream().close();
                if (conn.getOutputStream() != null) conn.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response;
    }

  /*  public boolean  isTokenExists (String requestURL,
                                   String token) {
        HttpURLConnection conn = null;
        URL url;
        String response = "";
        boolean result = false;
        try {
            url = new URL(requestURL);

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000);
            conn.setConnectTimeout(60000);
            //conn.setRequestProperty("Content-Type", "text/xml"); //tambah ini
            conn.setAllowUserInteraction(false);
            conn.setDefaultUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");

            int responseCode=conn.getResponseCode();
            if (isdebug) Log.i("Test POST isTokenExists", "ResponseCode: " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
                //TODO: ambil spesifik node
                JSONObject jsonObj = null;
                try {
                    jsonObj = XML.toJSONObject(response);
                    if (jsonObj.getJSONObject("ob:Openbravo").isNull("Tmob_Userdata")) {
                        Log.i("TEMPDEBUG_0", "0: masuk sini tanpa respon");
                        response = "";
                    }
                    else {
                        try {
                            JSONArray jsonarr = new JSONArray(jsonObj.getJSONObject("ob:Openbravo").getString("Tmob_Userdata").toString());
                            response = "";
//                                Log.i("TEMPDEBUG_1", "1: " + jsonarr.getJSONObject(0).getString("token"));
//                                Log.i("TEMPDEBUG_2", "2: " + token);
                            for (int i = 0; i < jsonarr.length(); i++) {
                                JSONObject jsonobj = jsonarr.getJSONObject(i);
                                response += "," + jsonobj.getString("token");

                                if (jsonobj.getString("token").equals(token)) {
                                    result = true;
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            result = jsonObj.getJSONObject("ob:Openbravo").getJSONObject("Tmob_Userdata").getString("token").equals(token);
                        }
                        response = response.substring(1);
                        //response = jsonarr.toString();//jsonObj.getJSONObject("ob:Openbravo").getJSONObject("Tmob_Userdata").getString("token");
                    }
                } catch (JSONException e) {
                    Log.e("JSON exception", e.getMessage()+" ("+response+")");
                    e.printStackTrace();
                }
            }
            else {
                //response="";
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line=br.readLine()) != null) {
                    response += line;
                }
            }
        } catch (Exception e) {
            Log.e("Error nya : ",e.getMessage()+" ("+response+")");
            e.printStackTrace();
        }finally {
            try {
                if (conn != null) conn.disconnect();
                if (conn.getInputStream() != null) conn.getInputStream().close();
                if (conn.getOutputStream() != null) conn.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }*/
/*

    /**
     *
     * @param serverUrl Url Dari Server yang dituju
     * @param sourceFileUri Source File yg akan di upload
     * @return
     */
    public int uploadFile(String serverUrl, String sourceFileUri) {

        String fileName = sourceFileUri;

        String message="";
        int serverResponseCode = HttpURLConnection.HTTP_INTERNAL_ERROR;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {
            Log.e("uploadFile", "Source File not exist :" + sourceFileUri);
            return HttpURLConnection.HTTP_NO_CONTENT;
        } else {
            try {
                Authenticator.setDefault(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        String LOGIN = "Openbravo";
                        String PWD = "openbravo";
                        return new PasswordAuthentication(LOGIN, PWD.toCharArray());
                    }
                });

                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(serverUrl);
                conn = (HttpURLConnection) url.openConnection();

                conn.setAllowUserInteraction(false);
                conn.setDefaultUseCaches(false);
                conn.setInstanceFollowRedirects(true);

                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                //conn.setFixedLengthStreamingMode((int) reqEntity.getContentLength());
                //  conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);




                dos = new DataOutputStream(conn.getOutputStream());


                dos.writeBytes(twoHyphens + boundary + lineEnd);

//Adding Parameter Username

                String user="Openbravo";
                dos.writeBytes("Content-Disposition: form-data; name=\"l\"" + lineEnd);
                //dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
                //dos.writeBytes("Content-Length: " + name.length() + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(user); // name is String variable
                dos.writeBytes(lineEnd);

                dos.writeBytes(twoHyphens + boundary + lineEnd);

//Adding Parameter Password
                String pass="openbravo";
                dos.writeBytes("Content-Disposition: form-data; name=\"p\"" + lineEnd);
                //dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
                //dos.writeBytes("Content-Length: " + name.length() + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(pass); // mobile_no is String variable
                dos.writeBytes(lineEnd);


                //Json_Encoder encode=new Json_Encoder();
                //call to encode method and assigning response data to variable 'data'
                //String data=encode.encod_to_json();
                //response of encoded data
                //System.out.println(data);


                //Adding Parameter filepath
//
//                dos.writeBytes(twoHyphens + boundary + lineEnd);
//                String filepath="http://192.168.1.110/echo/uploads"+fileName;
//
//                dos.writeBytes("Content-Disposition: form-data; name=\"filepath\"" + lineEnd);
//                //dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
//                //dos.writeBytes("Content-Length: " + name.length() + lineEnd);
//                dos.writeBytes(lineEnd);
//                dos.writeBytes(filepath); // mobile_no is String variable
//                dos.writeBytes(lineEnd);


//Adding Parameter media file(audio,video and image)

                dos.writeBytes(twoHyphens + boundary + lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
                // create a buffer of maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0)
                {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();



                Log.e("uploadFile", "HTTP Response is : "+ serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == HttpURLConnection.HTTP_OK) {
                    Log.i("Image_Upload","Upload gambar berhasil !");
                }

                // close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (final Exception e) {
                Log.e("Exception_Upload_IMG","Exception : " + e.getMessage(), e);
            }
            return serverResponseCode;
        }
    }


    //////////////depre
//    private String getPostData(HashMap<String, String> params) throws UnsupportedEncodingException {
//        if (params == null || params.size() == 0) {
//            return "";
//        }
//        StringBuilder result = new StringBuilder();
//        boolean first = true;
//        for(Map.Entry<String, String> entry : params.entrySet()){
//            if (first)
//                first = false;
//            else
//                result.append("&");
//
//            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
//            result.append("=");
//            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
//        }
//
//        return result.toString();
//    }
//    //////////////////////////////////
//
//    public static String postFile(String fileName, String userName, String password, String macAddress) throws Exception {
//
//        HttpClient client = new DefaultHttpClient();
//        HttpPost post = new HttpPost(SERVER + "uploadFile");
//        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//
//        final File file = new File(fileName);
//        FileBody fb = new FileBody(file);
//
//        builder.addPart("file", fb);
//        builder.addTextBody("userName", userName);
//        builder.addTextBody("password", password);
//        builder.addTextBody("macAddress",  macAddress);
//        final HttpEntity yourEntity = builder.build();
//
//        class ProgressiveEntity implements HttpEntity {
//            @Override
//            public void consumeContent() throws IOException {
//                yourEntity.consumeContent();
//            }
//            @Override
//            public InputStream getContent() throws IOException,
//                    IllegalStateException {
//                return yourEntity.getContent();
//            }
//            @Override
//            public Header getContentEncoding() {
//                return yourEntity.getContentEncoding();
//            }
//            @Override
//            public long getContentLength() {
//                return yourEntity.getContentLength();
//            }
//            @Override
//            public Header getContentType() {
//                return yourEntity.getContentType();
//            }
//            @Override
//            public boolean isChunked() {
//                return yourEntity.isChunked();
//            }
//            @Override
//            public boolean isRepeatable() {
//                return yourEntity.isRepeatable();
//            }
//            @Override
//            public boolean isStreaming() {
//                return yourEntity.isStreaming();
//            } // CONSIDER put a _real_ delegator into here!
//
//            @Override
//            public void writeTo(OutputStream outstream) throws IOException {
//
//                class ProxyOutputStream extends FilterOutputStream {
//                    /**
//                     * @author Stephen Colebourne
//                     */
//
//                    public ProxyOutputStream(OutputStream proxy) {
//                        super(proxy);
//                    }
//                    public void write(int idx) throws IOException {
//                        out.write(idx);
//                    }
//                    public void write(byte[] bts) throws IOException {
//                        out.write(bts);
//                    }
//                    public void write(byte[] bts, int st, int end) throws IOException {
//                        out.write(bts, st, end);
//                    }
//                    public void flush() throws IOException {
//                        out.flush();
//                    }
//                    public void close() throws IOException {
//                        out.close();
//                    }
//                } // CONSIDER import this class (and risk more Jar File Hell)
//
//                class ProgressiveOutputStream extends ProxyOutputStream {
//                    public ProgressiveOutputStream(OutputStream proxy) {
//                        super(proxy);
//                    }
//                    public void write(byte[] bts, int st, int end) throws IOException {
//
//                        // FIXME  Put your progress bar stuff here!
//
//                        out.write(bts, st, end);
//                    }
//                }
//
//                yourEntity.writeTo(new ProgressiveOutputStream(outstream));
//            }
//
//        };
//        ProgressiveEntity myEntity = new ProgressiveEntity();
//
//        post.setEntity(myEntity);
//        HttpResponse response = client.execute(post);
//
//        return getContent(response);
//
//    }
//
//    public static String getContent(HttpResponse response) throws IOException {
//        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//        String body = "";
//        String content = "";
//
//        while ((body = rd.readLine()) != null)
//        {
//            content += body + "\n";
//        }
//        return content.trim();
//    }
////////////////////////////////////////////////////////////////////
//    private static String multipost(String urlString, MultipartEntityBuilder reqEntity) {
//        try {
//            URL url = new URL(urlString);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(10000);
//            conn.setConnectTimeout(15000);
//            conn.setRequestMethod("POST");
//            conn.setUseCaches(false);
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//
//            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setFixedLengthStreamingMode((int) reqEntity.getContentLength());
//            //conn.addRequestProperty("Content-length", reqEntity.getContentLength()+"");// revisi
//            conn.addRequestProperty(reqEntity.getContentType().getName(), reqEntity.getContentType().getValue());
//            OutputStream os = conn.getOutputStream();
//            //reqEntity.writeTo(conn.getOutputStream());
//            ContentBody
//            reqEntity.addPart("a", os);
//
//            os.close();
//            conn.connect();
//
//            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                return readStream(conn.getInputStream());
//            }
//
//        } catch (Exception e) {
//            Log.e(TAG, "multipart post error " + e + "(" + urlString + ")");
//        }
//        return null;
//    }
//
//    private static String readStream(InputStream in) {
//        BufferedReader reader = null;
//        StringBuilder builder = new StringBuilder();
//        try {
//            reader = new BufferedReader(new InputStreamReader(in));
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                builder.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return builder.toString();
//    }

}