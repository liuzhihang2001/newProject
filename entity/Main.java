package com.yonyou.iuap.annex.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/*import org.apache.commons.lang.ObjectUtils;*/
import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.HttpResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

class SSLClient extends DefaultHttpClient {
    public SSLClient() throws Exception {
        super();
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        ctx.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        ClientConnectionManager ccm = this.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", 443, ssf));
    }
}

public class Main {
    static final String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7JL0DcaMUHumSdhxXTxqiABBC\n" +
            "DERhRJIsAPB++zx1INgSEKPGbexDt1ojcNAc0fI+G/yTuQcgH1EW8posgUni0mcT\n" +
            "E6CnjkVbv8ILgCuhy+4eu+2lApDwQPD9Tr6J8k21Ruu2sWV5Z1VRuQFqGm/c5vaT\n" +
            "OQE5VFOIXPVTaa25mQIDAQAB";

    // 服务器配置和帐号信息
    private String ip;

    // 保存userid和tokenid鉴权信息，用来发送后续的请求
    private String userid;
    private String tokenid;

    private static String RSAEncode(String pass) throws IOException, GeneralSecurityException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(pubKey)));

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return new BASE64Encoder().encode(cipher.doFinal(pass.getBytes("UTF-8"))).replace("\r\n", "\n");
    }

    public void SetServerIP(String ip) {
        this.ip = ip;
    }

    public void Ping() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost method = new HttpPost("http://" + this.ip + ":9998/v1/ping");
        CloseableHttpResponse response = httpclient.execute(method);

        System.out.println(response.toString());
    }

    public void LogIn(String username, String password) throws IOException, GeneralSecurityException {
        // 接口格式（包含URL请求格式、参数说明及请求示例）请参见《1-AnyShare5.0访问控制开放API接口文档 - 对外.docx》5.3 节
        // token有过期机制，需定期刷新，详情参见《1-AnyShare5.0访问控制开放API接口文档 - 对外.docx》第五章相关接口
        String url = "http://" + this.ip + ":9998/v1/auth1?method=getnew";
        String encopassword = RSAEncode(password);
        JSONObject user = new JSONObject();
        user.put("account", username);
        user.put("password", encopassword);
        String user2 = user.toString();

        // create http post
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost methodd = new HttpPost(url);
        StringEntity se = new StringEntity(user2, "UTF-8");
        methodd.addHeader("content-type", "application/json");
        methodd.setEntity(se);

        // get http post response
        CloseableHttpResponse response = client.execute(methodd);
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity);
        methodd.releaseConnection();

        // Get userid and tokenid from response json
        JSONObject retunjson = JSONObject.fromObject(strResult);
        this.userid = (String) retunjson.get("userid");
        this.tokenid = (String) retunjson.get("tokenid");
        
        System.out.println("111111111111111+useid="+this.userid);
        System.out.println("111111111111111+tokenid="+this.tokenid);

        System.out.println("Login response json: " + strResult);
    }

    public ArrayList<ASEntryDoc> GetEntryDoc() throws IOException {
        // 接口格式（包含URL请求格式、参数说明及请求示例）请参见《1-AnyShare5.0访问控制开放API接口文档 - 对外.docx》 10.1 节
        // userid、tokenid 由 auth1?method=getnew 接口返回
        String url = "http://" + this.ip + ":9998/v1/entrydoc?method=get&userid=" + this.userid + "&tokenid=" + this.tokenid;

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost methodd = new HttpPost(url);
        System.out.println("reques url: " + url);

        CloseableHttpResponse response = client.execute(methodd);
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity);
        methodd.releaseConnection();

        System.out.println("reponse json: " + strResult);

        // 解析返回值
        JSONObject jsonObj = JSONObject.fromObject(strResult);
        JSONArray jsonDocInfos = jsonObj.getJSONArray("docinfos");

        ArrayList<ASEntryDoc> entryDocs = new ArrayList<ASEntryDoc>();
        for (int i = 0; i < jsonDocInfos.size(); i++)
        {
            JSONObject docObj = jsonDocInfos.getJSONObject(i);

            ASEntryDoc tmpDoc = new ASEntryDoc();
            tmpDoc.docId = docObj.getString("docid");
            tmpDoc.docName = docObj.getString("docname");
            tmpDoc.docType = docObj.getString("doctype");

            entryDocs.add(tmpDoc);
        }

        return entryDocs;
    }

    public void CreateDir(String parentgns, String name) throws IOException {
        // 接口格式（包含URL请求格式、参数说明及请求示例）请参见《2-AnyShare5.0文档访问开放API接口文档 - 对外.docx》 3.1 节
        // userid、tokenid 由 auth1?method=getnew 接口返回
        String url = "http://" + this.ip + ":9123/v1/dir?method=create&userid=" + this.userid + "&tokenid=" + this.tokenid;

        JSONObject dir = new JSONObject();
        // docid 可由任意可获取到文件夹ID的接口处获取 （如 entrydoc?method=get dir?method=create dir?method=list dir?method=createmultileveldir 等等。。）
        dir.put("docid", parentgns);
        dir.put("name", name);
        dir.put("ondup", 2);
        String strdir = dir.toString();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost methodd = new HttpPost(url);
        System.out.println("reques url: " + url);
        StringEntity se = new StringEntity(strdir, "UTF-8");
        methodd.setEntity(se);

        CloseableHttpResponse response = client.execute(methodd);
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity);
        methodd.releaseConnection();

        System.out.println("CreadDirReponse json: " + strResult);
    }

    public void CreateMultiDir(String parentgns, String path) throws IOException {
        // 接口格式（包含URL请求格式、参数说明及请求示例）请参见《2-AnyShare5.0文档访问开放API接口文档 - 对外.docx》 3.2 节
        // userid、tokenid 由 auth1?method=getnew 接口返回
        String url = "http://" + this.ip + ":9123/v1/dir?method=createmultileveldir&userid=" + this.userid + "&tokenid=" + this.tokenid;

        JSONObject dir = new JSONObject();
        // docid 可由任意可获取到文件夹ID的接口处获取 （如 entrydoc?method=get dir?method=create dir?method=list dir?method=createmultileveldir 等等。。）
        dir.put("docid", parentgns);
        dir.put("path", path);
        String strdir = dir.toString();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost methodd = new HttpPost(url);
        System.out.println("reques url: " + url);
        StringEntity se = new StringEntity(strdir, "UTF-8");
        methodd.setEntity(se);

        CloseableHttpResponse response = client.execute(methodd);
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity);
        methodd.releaseConnection();

        System.out.println("CreateMultiDirReponse json: " + strResult);
    }

    public void ListDir(String parentgns) throws IOException {
        // 接口格式（包含URL请求格式、参数说明及请求示例）请参见《2-AnyShare5.0文档访问开放API接口文档 - 对外.docx》 3.5 节
        // userid、tokenid 由 auth1?method=getnew 接口返回
        String url = "http://" + this.ip + ":9123/v1/dir?method=list&userid=" + this.userid + "&tokenid=" + this.tokenid;
        JSONObject dir = new JSONObject();
        // docid 可由任意可获取到文件夹ID的接口处获取 （如 entrydoc?method=get dir?method=create dir?method=list dir?method=createmultileveldir 等等。。）
        dir.put("docid", parentgns);
        String strdir = dir.toString();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost methodd = new HttpPost(url);
        System.out.println("reques url: " + url);
        StringEntity se = new StringEntity(strdir, "UTF-8");
        methodd.setEntity(se);

        CloseableHttpResponse response = client.execute(methodd);
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity);
        methodd.releaseConnection();
        System.out.println("ListDirReponse json: " + strResult);
    }

    public void DeleteFile(String parentgns) throws IOException {
        // 接口格式（包含URL请求格式、参数说明及请求示例）请参见《2-AnyShare5.0文档访问开放API接口文档 - 对外.docx》 5.27 节
        String url = "http://" + this.ip + ":9123/v1/file?method=delete&userid=" + this.userid + "&tokenid=" + this.tokenid;

        JSONObject dir = new JSONObject();
        dir.put("docid", parentgns);
        String strdir = dir.toString();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost methodd = new HttpPost(url);
        System.out.println("reques url: " + url);
        StringEntity se = new StringEntity(strdir, "UTF-8");
        methodd.setEntity(se);

        CloseableHttpResponse response = client.execute(methodd);
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity);
        methodd.releaseConnection();

        System.out.println("DeleteDirReponse json: " + strResult);
    }

    public void DeleteDir(String parentgns) throws IOException {
        // 接口格式（包含URL请求格式、参数说明及请求示例）请参见《2-AnyShare5.0文档访问开放API接口文档 - 对外.docx》 3.3 节
        String url = "http://" + this.ip + ":9123/v1/dir?method=delete&userid=" + this.userid + "&tokenid=" + this.tokenid;

        JSONObject dir = new JSONObject();
        dir.put("docid", parentgns);
        String strdir = dir.toString();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost methodd = new HttpPost(url);
        System.out.println("reques url: " + url);
        StringEntity se = new StringEntity(strdir, "UTF-8");
        methodd.setEntity(se);

        CloseableHttpResponse response = client.execute(methodd);
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity);
        methodd.releaseConnection();

        System.out.println("DeleteDirReponse json: " + strResult);
    }

    public JSONObject Osoption() throws IOException {
        String url = "http://" + this.ip + ":9123/v1/file?method=osoption&userid=" + this.userid + "&tokenid=" + this.tokenid;
        System.out.println("reques url: " + url);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost method = new HttpPost(url);

        CloseableHttpResponse response = client.execute(method);
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity);
        System.out.println(strResult);
        //System.out.println(response.toString());
        method.releaseConnection();
        JSONObject retunjson = JSONObject.fromObject(strResult);
        return retunjson;
    }

    public ASBeginUploadResult OSBeginupload(String gns, long length, String name, int ondup) throws IOException {
        // 接口格式（包含URL请求格式、参数说明及请求示例）请参见《2-AnyShare5.0文档访问开放API接口文档 - 对外.docx》 4.2 节
        // PS: 上传文件流程图可参见《2-AnyShare5.0文档访问开放API接口文档 - 对外.docx》第四章开始的说明，建议看一遍后再看接口
        // userid、tokenid 由 auth1?method=getnew 接口返回
        String url = "http://" + this.ip + ":9123/v1/file?method=osbeginupload&userid=" + this.userid + "&tokenid=" + this.tokenid;

        // 构造请求body
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("docid", gns);
        jsonObj.put("length", length);
        jsonObj.put("name", name);
        jsonObj.put("ondup", ondup);

        String bodyStr = jsonObj.toString();

        // 发送请求
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        System.out.println("reques url: " + url);
        StringEntity se = new StringEntity(bodyStr, "UTF-8");
        post.setEntity(se);

        CloseableHttpResponse response = client.execute(post);

        // 解析回复
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity);
        post.releaseConnection();

        System.out.println("OSBeginupload: " + strResult);

        // 解析返回值
        JSONObject resJson = JSONObject.fromObject(strResult);
        ASBeginUploadResult result = new ASBeginUploadResult();

        JSONArray authRequestArray = resJson.getJSONArray("authrequest");
        result.httpMethod = authRequestArray.getString(0);
        result.url = authRequestArray.getString(1);

        result.headers = new HashMap<String, String>();
        for(int i = 2; i <= 4; ++i) {
            String[] stringArray = authRequestArray.getString(i).split(":", 2);

            String key = stringArray[0].trim();
            String value = stringArray[1].trim();
            result.headers.put(key, value);
        }

        result.docId = resJson.getString("docid");
        result.name = resJson.getString("name");
        result.rev = resJson.getString("rev");

        return result;
    }

    public String UploadPut(String url, HashMap<String, String> headers, String uploadPath) throws Exception {
        // 构造httpsclient
        HttpClient client = new SSLClient();
        HttpPut method = new HttpPut(url);
        System.out.println("UploadPut url: " + url);

        // 打开文件读流
        File file = new File(uploadPath);
        InputStreamEntity entity = new InputStreamEntity(new FileInputStream(file), file.length());

        // 设置http body内容和headers
        HttpPut put = new HttpPut(url);
        put.setEntity(entity);

        for (String key: headers.keySet()) {
            put.addHeader(key, headers.get(key));
        }

        HttpResponse response = client.execute(put);

        // 解析回复
        HttpEntity resEntity = response.getEntity();
        String strResult = EntityUtils.toString(resEntity);
        put.releaseConnection();

        System.out.println("UploadPut successfully.");
        return strResult;
    }

    public String MultiUploadPut(String url, String conttype, String data, String auth, byte[] body, int len, String parts, String strbody) throws Exception {
        HttpClient client = null;
        client = new SSLClient();
        String re = "";

        // HttpPut methodd = new HttpPut(url);
        if (body != null) {
            HttpPut methodd = new HttpPut(url);
            ByteArrayEntity se = new ByteArrayEntity(body, 0, len);

            methodd.setEntity(se);
            methodd.addHeader("content-type", "application/octet-stream");
            methodd.addHeader("X-Eoss-Date", data);
            methodd.addHeader("Authorization", auth);
            HttpResponse response = client.execute(methodd);
            HttpEntity entity = response.getEntity();
            String strResult = EntityUtils.toString(entity);
            re = strResult;
            if (conttype == null) {
                re = response.getFirstHeader("Etag").toString().substring(6);
            }
        } else {
            HttpPost methodd = new HttpPost(url);
            methodd.addHeader("content-type", conttype);
            methodd.addHeader("X-Eoss-Date", data);
            methodd.addHeader("Authorization", auth);
            StringEntity se = new StringEntity(strbody, "UTF-8");
            methodd.setEntity(se);
            HttpResponse response = client.execute(methodd);
            HttpEntity entity = response.getEntity();
            String strResult = EntityUtils.toString(entity);
            re = strResult;
        }
        //methodd.setEntity(se);

        //methodd.releaseConnection();
        return re;
    }

    public JSONObject OSEndUpload(String gns, String rev) throws IOException {
        String url = "http://" + this.ip + ":9123/v1/file?method=osendupload&userid=" + this.userid + "&tokenid=" + this.tokenid;
        //uploadjson = {'docid' : gns, 'length' : length, 'name':name, 'client_mtime':clienttime, 'ondup':ondup}
        JSONObject dir = new JSONObject();
        dir.put("docid", gns);
        dir.put("rev", rev);
        //dir.put("md5", name);
        //dir.put("clienttime", clienttime);
        //dir.put("ondup", 2);
        String strdir = dir.toString();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost methodd = new HttpPost(url);
        System.out.println("reques url: " + url);
        StringEntity se = new StringEntity(strdir, "UTF-8");
        methodd.setEntity(se);

        CloseableHttpResponse response = client.execute(methodd);
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity);
        JSONObject retunjson = JSONObject.fromObject(strResult);
        methodd.releaseConnection();

        System.out.println("OSEndUpload: " + strResult);
        return retunjson;
    }

    public JSONObject Osdownload(String gns, String savepath) throws Exception {
        // 接口格式（包含URL请求格式、参数说明及请求示例）请参见《2-AnyShare5.0文档访问开放API接口文档 - 对外.docx》 4.4 节
        String url = "http://" + this.ip + ":9123/v1/file?method=osdownload&userid=" + this.userid + "&tokenid=" + this.tokenid;
        JSONObject dir = new JSONObject();
        dir.put("docid", gns);
        String strdir = dir.toString();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost methodd = new HttpPost(url);
        System.out.println("reques url: " + url);
        StringEntity se = new StringEntity(strdir, "UTF-8");
        methodd.setEntity(se);

        CloseableHttpResponse closeableHttpResponse = client.execute(methodd);
        HttpEntity entity = closeableHttpResponse.getEntity();
        String strResult = EntityUtils.toString(entity);
        JSONObject retunjson = JSONObject.fromObject(strResult);
        methodd.releaseConnection();

        System.out.println("Osdownload: " + strResult);
        String downurl = (String) retunjson.getJSONArray("authrequest").get(1);
        String date = ((String) retunjson.getJSONArray("authrequest").get(2)).substring(12);
        String length = ((String) retunjson.getJSONArray("authrequest").get(3)).substring(14);
        String auth = ((String) retunjson.getJSONArray("authrequest").get(4)).substring(14);
        DownloadGet(downurl, date, length, auth, savepath);
        return retunjson;
    }

    public String DownloadGet(String url, String date, String length, String auth, String savepath) throws Exception {
        HttpClient client = null;
        client = new SSLClient();
        HttpGet methodd = new HttpGet(url);
        methodd.addHeader("X-Eoss-Date", date);
        methodd.addHeader("X-Eoss-Length", length);
        methodd.addHeader("Authorization", auth);
        HttpResponse httpResponse = client.execute(methodd);
        HttpEntity ba = httpResponse.getEntity();
        File mysavepath = new File(savepath);
        mysavepath.createNewFile();;
        FileOutputStream out = new FileOutputStream(mysavepath, false);
        out.write(EntityUtils.toByteArray(ba));
        out.close();
        HttpEntity entity = httpResponse.getEntity();
        methodd.releaseConnection();
        
        return "download path:" + savepath;
    }

    public JSONObject Osinitmultiupload(String gns, long length, String name, int ondup) throws IOException {
        String url = "http://" + this.ip + ":9123/v1/file?method=osinitmultiupload&userid=" + this.userid + "&tokenid=" + this.tokenid;
        JSONObject dir = new JSONObject();
        dir.put("docid", gns);
        dir.put("length", length);
        dir.put("name", name);
        //dir.put("clienttime", clienttime);
        dir.put("ondup", 2);
        String strdir = dir.toString();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost methodd = new HttpPost(url);
        System.out.println("reques url: " + url);
        StringEntity se = new StringEntity(strdir, "UTF-8");
        methodd.setEntity(se);

        CloseableHttpResponse response = client.execute(methodd);
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity);
        JSONObject retunjson = JSONObject.fromObject(strResult);
        methodd.releaseConnection();

        System.out.println("Osbeginupload: " + strResult);
        return retunjson;
    }

    public String[] Oscompleteupload(String gns, String rev, String uploadid, JSONObject partinfo) throws IOException {
        String url = "http://" + this.ip + ":9123/v1/file?method=oscompleteupload&userid=" + this.userid + "&tokenid=" + this.tokenid;
        JSONObject dir = new JSONObject();
        dir.put("docid", gns);
        dir.put("rev", rev);
        dir.put("uploadid", uploadid);
        dir.put("partinfo", partinfo);
        String strdir = dir.toString();
        System.out.println("oscompleteuploadjson: " + strdir);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost methodd = new HttpPost(url);
        System.out.println("reques url: " + url);
        StringEntity se = new StringEntity(strdir, "UTF-8");
        methodd.setEntity(se);

        CloseableHttpResponse response = client.execute(methodd);
        System.out.println("Osbeginuploadcont: " + response);

        String boundary = response.getFirstHeader("Content-Type").toString().substring(43);
        HttpEntity entity = response.getEntity();
        String sss = EntityUtils.toString(entity);
        System.out.println("Osbeginuploadcont: " + boundary);

        String[] strResult = sss.split("--" + boundary);
        //String [] strResult = sss.split("--");
        methodd.releaseConnection();
        //JSONObject retunjson = JSONObject.fromObject(strResult);
        return strResult;
    }

    public JSONObject Osuploadpart(String gns, String rev, String uploadid, String parts) throws IOException {
        String url = "http://" + this.ip + ":9123/v1/file?method=osuploadpart&userid=" + this.userid + "&tokenid=" + this.tokenid;
        JSONObject dir = new JSONObject();
        dir.put("docid", gns);
        dir.put("rev", rev);
        dir.put("uploadid", uploadid);
        //dir.put("clienttime", clienttime);
        dir.put("parts", parts);
        String strdir = dir.toString();
        System.out.println("reques json: " + strdir);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost methodd = new HttpPost(url);
        System.out.println("reques url: " + url);
        StringEntity se = new StringEntity(strdir, "UTF-8");
        methodd.setEntity(se);

        CloseableHttpResponse response = client.execute(methodd);
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity);
        JSONObject retunjson = JSONObject.fromObject(strResult);
        methodd.releaseConnection();

        System.out.println("osuploadpart: " + strResult);
        return retunjson;
    }

    public String UploadBigFile(String docgns, String uploadpath) throws IOException {
        try {
            File f = new File(uploadpath);
            FileInputStream ipt = new FileInputStream(f);
            byte[] buf = new byte[4 * 1024 * 1024];
            long remain = f.length();
            int part = 1;
            int parts = (int) remain / (4 * 1024 * 1024) + 1;
            String divparts = "1-" + Integer.toString(parts);
            String lastgns = docgns, lastver = "";
            // 接口格式（包含URL请求格式、参数说明及请求示例）请参见《2-AnyShare5.0文档访问开放API接口文档 - 对外.docx》 4.5 节
            JSONObject multres = Osinitmultiupload(docgns, remain, "test.rar", 2);
            // 接口格式（包含URL请求格式、参数说明及请求示例）请参见《2-AnyShare5.0文档访问开放API接口文档 - 对外.docx》 4.6 节
            JSONObject multpart = Osuploadpart((String) multres.get("docid"), (String) multres.get("rev"), (String) multres.get("uploadid"), divparts);


            JSONObject partinfos = new JSONObject();
            do {
                int len = ipt.read(buf);
                System.out.println("len: " + len);
                String url = (String) multpart.getJSONObject("authrequests").getJSONArray(Integer.toString(part)).get(1);
                String date = ((String) multpart.getJSONObject("authrequests").getJSONArray(Integer.toString(part)).get(3)).substring(12);
                String auth = ((String) multpart.getJSONObject("authrequests").getJSONArray(Integer.toString(part)).get(4)).substring(13);

                String multiuploadre = MultiUploadPut(url, null, date, auth, buf, len, Integer.toString(part++), null);
                JSONArray partinflist = new JSONArray();
                partinflist.add(multiuploadre);
                partinflist.add(len);


                partinfos.put(Integer.toString(part - 1), partinflist);
                remain -= len;

            } while (part <= parts);

            // compltest uplad mutlit
            // 接口格式（包含URL请求格式、参数说明及请求示例）请参见《2-AnyShare5.0文档访问开放API接口文档 - 对外.docx》 4.7 节
            String[] multiuploaddone = Oscompleteupload((String) multres.get("docid"), (String) multres.get("rev"), (String) multres.get("uploadid"), partinfos);
            String strbody = multiuploaddone[1];

            String res = multiuploaddone[2];
            JSONObject retunjson = JSONObject.fromObject(res);
            String url = (String) retunjson.getJSONArray("authrequest").get(1);
            String contentype = ((String) retunjson.getJSONArray("authrequest").get(2)).substring(13);
            String date = ((String) retunjson.getJSONArray("authrequest").get(3)).substring(12);
            String auth = ((String) retunjson.getJSONArray("authrequest").get(4)).substring(14);
            // System.out.println("partinfos: " + date);

            //create index
            String re = MultiUploadPut(url, contentype, date, auth, null, 1, "1", strbody);

            // end uplaoad
            // 接口格式（包含URL请求格式、参数说明及请求示例）请参见《2-AnyShare5.0文档访问开放API接口文档 - 对外.docx》 4.3 节
            JSONObject endupload = OSEndUpload((String) multres.get("docid"), (String) multres.get("rev"));
            return lastgns;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 创建多层目录， 如果存在则返回多层目录的gns
    public String createMultiLevelDir(String parentgns, String serverpath) throws Exception {
        String url = "http://" + this.ip + ":9123/v1/dir?method=createmultileveldir&userid=" + this.userid + "&tokenid=" + this.tokenid;
        JSONObject reqbody = new JSONObject();
        reqbody.put("docid",  parentgns);
        reqbody.put("path", serverpath);
        String reqstr =reqbody.toString();
        System.out.println("reques json: " + reqstr);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost methodd = new HttpPost(url);
        System.out.println("reques url: " + url);
        StringEntity se = new StringEntity(reqstr, "UTF-8");
        methodd.setEntity(se);

        CloseableHttpResponse response = client.execute(methodd);
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity);
        JSONObject retunjson = JSONObject.fromObject(strResult);
        methodd.releaseConnection();

        System.out.println("createmultileveldir: " + strResult);
        return retunjson.getString("docid");
    }

    // 根据文件路径获取对应gns
    public String getInfoByPath(String namepath) throws Exception {
        String url = "http://" + this.ip + ":9123/v1/file?method=getinfobypath&userid=" + this.userid + "&tokenid=" + this.tokenid;
        JSONObject reqbody = new JSONObject();
        reqbody.put("namepath",  namepath);
        String reqstr =reqbody.toString();
        System.out.println("reques json: " + reqstr);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost methodd = new HttpPost(url);
        System.out.println("reques url: " + url);
        StringEntity se = new StringEntity(reqstr, "UTF-8");
        methodd.setEntity(se);

        CloseableHttpResponse response = client.execute(methodd);
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity);
        JSONObject retunjson = JSONObject.fromObject(strResult);
        methodd.releaseConnection();

        System.out.println("getinfobypath: " + strResult);
        return retunjson.getString("docid");
    }
    
    
    // 根据目录gns获取对应的link
    public String getLink(String gns) throws Exception {
        String url = "http://" + this.ip + ":9123/v1/link?method=open&userid=" + this.userid + "&tokenid=" + this.tokenid;
        JSONObject reqbody = new JSONObject();
        reqbody.put("docid",  gns);
        String reqstr =reqbody.toString();
        System.out.println("reques json: " + reqstr);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost methodd = new HttpPost(url);
        System.out.println("reques url: " + url);
        StringEntity se = new StringEntity(reqstr, "UTF-8");
        methodd.setEntity(se);

        CloseableHttpResponse response = client.execute(methodd);
        HttpEntity entity = response.getEntity();
        String strResult = EntityUtils.toString(entity);
        JSONObject retunjson = JSONObject.fromObject(strResult);
        methodd.releaseConnection();

        System.out.println("getinfobypath: " + strResult);
        return retunjson.getString("link");
    }

}
