package io.casper.lib.attestations;

import com.google.protobuf.ByteString;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

public class AttestationServlet extends HttpServlet {

    public static final String DEFAULT_APP_NAME = "com.snapchat.android";
    public static final String DEFAULT_APK_DIGEST = "JJShKOLH4YYjWZlJQ71A2dPTcmxbaMboyfo0nsKYayE="; //9.10.0.0
    public static final String DEFAULT_APK_CERTIFICATE_DIGEST = "Lxyq/KHtMNC044hj7vq+oOgVcR+kz3m4IlGaglnZWlg=";
    public static final String DEFAULT_DROID_GUARD = "CgbMYHoVNWLYAQE4YRoMCMKa2IIDEKC349kCqgERCLikhu8CEKL-jf34_____wHoAfLYvrb7_____wHoAbjLt877_____wE4azhLEokMuKToh_JDOAH8CvNUrmmbUkT045BmT6P_KNQSwOPtKxfJzK43U0I8A3x9lhvIbj5rbW3EREoNXLsI4okM7eonVUPt_PNYSK-b3U-T_bCfQkhKZ6hQ2ByXyi81LMp9IbzCho9KzRi_3zn9ffewSW2UjaGj71_gm4iapZXcQCigaWJ28VW10g8aeVcXVnXMg7UfDfx7NXghb0ZtlNXu8QxSCTWzUrW5nQH_TcYVKKhO4uFkX_sPr9MVwchg5oUSfvAZ2X2ZnsrkszrUr_NtlLS3w56DnPJvcOnN7X0N70p5Hj8Uqlx-a39c6BO4zfd_TAsqiV02zLoVctaht332OHA8Ejh1tIs89d0xKryMBq3OUSKKfLuMcfohU9gLuYF5_yfh3LGt2A4KSlUF1_DKcgxhiBV-QlNJWOL45fXjPl1_h7KcxfegJy19tpZ8cZ_KAWd4W00NJb5AmSGnzIB0iAkh0iC6fwpxngWS9ew_1gQC68wHrxZ8oBsLjZyzWk1eV69Xt3FSsTRzhpaIgnveaEtRZt6KsZUy2sCgzU1BvBF-Cm9kW1taPLvhURVxljUABxyKSsEmCFkyJbKDqz6bnTmAEXoJVJ3OW0OW9bpLX7IsKSlnIKOAnET0aH0e7CLkpj2nke8h8nv5PoW-s60Cc_5SirP4hTZRJDvXJecYljQcTTX6MOKx-gRiLdvc0NWzow8yrGTuCqQ0rYBEUELh8U7dGYQk6WX92aJEWa6uZb3AN9WFGg0GxiY1Bpq9kO58mHSBqeJXCrQ4mq2GJJxhxUE13zzbqsWyPi66MbTH1-YRTrjVPj5nkZqomuCtWIyWnC573IyG37lt8eYLXLNTbwuVDmUfaXytgntNExITdf9iiN2zFEYAlKSEROMc3FEKwApy9zzeTC9ItHsXGm63vv9s_2zaKEk4kUP3ozNfIMYeImk2piUs5OhEzpdAx_xyHbQICac2IA5arJ0_kqZ42tcuIjnzw8IJuU4xTDfEK12Ju7HaK8KA2i8v5Wtv28EQdqUUXByM39t_u16yI2y_Me545HGO18r-GCH3XJPe1GqwMq_J8vJSx3ecZhEXWBOZsyW5OvZ4YjHULRBDphZpyOmVsW7pLUprr42cU1BtitQy0aJm-qzd1ud4FspjRf-bsuRvWruqdqPTG80NbB-WUPCDNTSZ49bwQ9_CW0VQbqzDRupyG-xl16DwCWvqeWVd-v2DYTKCESsKfJhUHmYyK_GbTgsEioZIKmgN6UERMYLxKRRibkT8r0vWpsqx53xB_MenVzfOmpsOCJt5ITalsMVINUnuYekYw1YG9b6HRLtR4dSZj36j6gMDwGpOEPtHeEt6Fjym0SjMNA6kL2qp8rqxSiKrc37RE7Y6f6d2RlarGZG5woPl59F4onBR3Z1SR5mc9Srzrsezn6petEl19w0GtPocw5mrbYphgmjqGKNKqgLCKQ5yhfA1FQMkaZQQSJRd_zg5DuKUr7Bi_fc0ag-iJ1YqCrSiR-AmZV2DUGcNuYe9BOKt70GpfYfyc2DIcSA6oNNnRyi5uqjA7FUvggsWh80s-1yHSl4klFmQN0X10X-FszbmP2PYOlipbG6lNe2qCdlT49XgO17625Spfu7ehpeP-wRjWxgG5A-l7HabM3BrJRvYiW4YXWmloH2C8qOiqnHtC_mDjWAChSK9unVfMLQOeHiBInGR3s44wZvgtVzn_uSHuIacbqCr8VW-efmRVJ5m3iNado9smCCn74xnkMFR4_nRTCz-uwTsQp6Vvk7A6B-avGIkSBWK26nn7p0wB-btIYVZhbHlvs7eRL4PF5sc7gDgS6fpTVOVTCUEUYDfOeyu2TD-JUv0tnNxyH8zdeMHYjtQKgHDNMoWHlA1ly_1BqbU2urn3N07I4BuoBWhSfzcsRmOXtBtylCrVRhC9MEOR-I8QGgFByZfixG4XwGQnbCx-LyAtn6ngcii79W0pA8AoG_-a0s-3aIebEpcz2_qPXqxZ1qk4ByA10VjIBTC73vY1_ChkHg6bvmOsgYcrOFmD9nrbBCgapBGOx_yWsPLLwAD-cGj";
    public static final String DEFAULT_GMS_VERSION = "7329038";
    public static final String DEFAULT_USER_AGENT = "SafetyNet/7329000 (klte KOT49H); gzip";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        JSONObject responseObject = new JSONObject();
        responseObject.put("code", 405);
        responseObject.put("message", "Method Not Allowed");
        String responseString = responseObject.toString();

        PrintWriter out = response.getWriter();
        out.print(responseString);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        //Auth Param | Limit random people really
        String authentication = request.getParameter("authentication");

        String appName = request.getParameter("app_name"); //Optional
        String apkDigest = request.getParameter("apk_digest"); //Optional
        String apkCertificateDigest = request.getParameter("apk_certificate_digest"); //Optional
        String droidGuard = request.getParameter("droid_guard"); //Optional
        String gmsVersion = request.getParameter("gms_version"); //Optional
        String userAgent = request.getParameter("user_agent"); //Optional

        String nonce = request.getParameter("nonce"); //Required
        String timestamp = request.getParameter("timestamp"); //Required

        //Check if the user is allowed to use my API
        APIAuth apiAuth = new APIAuth();
        if(!apiAuth.isAuthorized(authentication)){
            JSONObject responseObject = new JSONObject();
            responseObject.put("code", 401);
            responseObject.put("message", "You're not Authorized to use this API");
            out.print(responseObject.toString());
            return;
        }

        if(appName == null){
            appName = DEFAULT_APP_NAME;
        }

        if(apkDigest == null){
            apkDigest = DEFAULT_APK_DIGEST;
        }

        if(apkCertificateDigest == null){
            apkCertificateDigest = DEFAULT_APK_CERTIFICATE_DIGEST;
        }

        if(droidGuard == null){
            droidGuard = DEFAULT_DROID_GUARD;
        }

        if(gmsVersion == null){
            gmsVersion = DEFAULT_GMS_VERSION;
        }

        if(userAgent == null){
            userAgent = DEFAULT_USER_AGENT;
        }

        if(nonce == null){
            JSONObject responseObject = new JSONObject();
            responseObject.put("code", 400);
            responseObject.put("message", "Parameter Missing: nonce");
            out.print(responseObject.toString());
            return;
        }

        if(timestamp == null){
            JSONObject responseObject = new JSONObject();
            responseObject.put("code", 400);
            responseObject.put("message", "Parameter Missing: timestamp");
            out.print(responseObject.toString());
            return;
        }

        try {

            Map<String, String> params = new HashMap<>();

            params.put("app_name", appName);
            params.put("apk_digest", apkDigest);
            params.put("apk_certificate_digest", apkCertificateDigest);
            params.put("droid_guard", droidGuard);
            params.put("gms_version", gmsVersion);
            params.put("user_agent", userAgent);

            params.put("nonce", nonce);
            params.put("timestamp", timestamp);

            String attestation = generateAttestation(params);

            JSONObject responseObject = new JSONObject();
            responseObject.put("code", 200);
            responseObject.put("signedAttestation", attestation);
            out.print(responseObject.toString());

        } catch(Exception e){

            JSONObject responseObject = new JSONObject();
            responseObject.put("code", 500);
            responseObject.put("message", e.getCause().getMessage());
            out.print(responseObject.toString());

        }

    }

    public String generateAttestation(Map<String, String> params) throws Exception {

        String appName = params.get("app_name");
        String apkDigest = params.get("apk_digest");
        String apkCertificateDigest = params.get("apk_certificate_digest");
        String droidGuard = params.get("droid_guard");
        String gmsVersion = params.get("gms_version");
        String userAgent = params.get("user_agent");

        String nonce = params.get("nonce");
        String timestamp = params.get("timestamp");

        if(appName == null){
            throw new NullPointerException("Param Required: app_name");
        }

        if(apkDigest == null){
            throw new NullPointerException("Param Required: apk_digest");
        }

        if(apkCertificateDigest == null){
            throw new NullPointerException("Param Required: apk_certificate_digest");
        }

        if(droidGuard == null){
            throw new NullPointerException("Param Required: droidguard");
        }

        if(gmsVersion == null){
            throw new NullPointerException("Param Required: gms_version");
        }

        if(userAgent == null){
            throw new NullPointerException("Param Required: user_agent");
        }

        if(nonce == null){
            throw new NullPointerException("Param Required: nonce");
        }

        if(timestamp == null){
            throw new NullPointerException("Param Required: timestamp");
        }

        AttestationProtobuf.snet_idle.Builder snetIdle = AttestationProtobuf.snet_idle.newBuilder();
        AttestationProtobuf.dataContainer.Builder dataContainerBuilder = AttestationProtobuf.dataContainer.newBuilder();

        dataContainerBuilder.setNonce(ByteString.copyFrom(DatatypeConverter.parseBase64Binary(nonce)));
        dataContainerBuilder.setAppName(appName);
        dataContainerBuilder.setApkDigestSHA256(ByteString.copyFrom(DatatypeConverter.parseBase64Binary(apkDigest)));
        dataContainerBuilder.setApkCertificateDigestSHA256(ByteString.copyFrom(DatatypeConverter.parseBase64Binary(apkCertificateDigest)));
        dataContainerBuilder.setGmsVersion(Integer.valueOf(gmsVersion));

        AttestationProtobuf.unknownData.Builder unknownDataBuilder = AttestationProtobuf.unknownData.newBuilder();
        unknownDataBuilder.setUnknown1(true);
        unknownDataBuilder.setUnknown2(true);
        dataContainerBuilder.setUnknowndata(unknownDataBuilder.build());

        dataContainerBuilder.setTimestamp(Long.parseLong(timestamp));

        snetIdle.setDatacontainer(dataContainerBuilder);
        snetIdle.setDroidGuard(droidGuard);

        byte[] data = snetIdle.build().toByteArray();

        URL url = new URL("https://www.googleapis.com/androidcheck/v1/attestations/attest?alt=JSON&key=AIzaSyDqVnJBjE5ymo--oBJt3On7HQx9xNm1RHA");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Length", Integer.toString(data.length));
        httpURLConnection.setRequestProperty("User-Agent", userAgent);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-protobuf");
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);

        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(data);
        outputStream.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            stringBuilder.append(line);
        }

        try {
            outputStream.close();
            rd.close();
        } catch(Exception e){
            //Ignore...
        }

        JSONObject jsonObject = new JSONObject(stringBuilder.toString());
        return jsonObject.getString("signedAttestation");

    }

    /*
    public String generateNonce(String username, String password, long timestamp, String endpoint){

        byte[] nonce = sha256(username +"|"+password+"|"+timestamp+"|"+endpoint);
        if(nonce == null){
            return null;
        }

        return DatatypeConverter.printBase64Binary(nonce);

    }

    public static byte[] sha256(String data){

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(data.getBytes("UTF-8"));
        } catch(Exception e){
            e.printStackTrace();
        }

        return null;

    }
    */

}
