# AttestationServlet
A Java Servlet for generating Attestations for Snapchat™ logins.

This currently runs on a Tomcat7 server, and requires Java8.

##Seting up your own Attestation Servlet:

###Install Tomcat7

`sudo apt-get update`

`sudo apt-get install tomcat7`

Tomcat will now be running at http://localhost:8080

###Install Tomcat7 Admin Panel

`sudo apt-get install tomcat7-admin`

Add an admin panel user by editing this file:

`sudo nano /etc/tomcat7/tomcat-users.xml`

Change the password in the example below, and save it.

```
<tomcat-users>
    <user username="admin" password="password" roles="manager-gui,admin-gui"/>
</tomcat-users>
```

Now restart:

`sudo service tomcat7 restart`

###Install Java8

If you're running Ubuntu 14.04 you can [follow this guide](http://tecadmin.net/install-oracle-java-8-jdk-8-ubuntu-via-ppa/)

Once Java8 is installed you'll need to modify the Tomcat config to use it.

`nano /etc/default/tomcat7`

Change the JAVA_HOME to the location of Java8, in my case:

`JAVA_HOME=/usr/lib/jvm/java-8-oracle`

Now restart Tomcat7 again:

`sudo service tomcat7 restart`

Tomcat7 is now running with Java8, we can now deploy our Attestation Servlet

Compile the servlet with your IDE, I use IntelliJIDEA.

Create the .war artifact, rename it to `ROOT.war` and upload and deploy it via the Tomcat Admin panel.

`http://localhost:8080/manager/html`

You can now make requests to the Attestation Servlet at `http://localhost:8080/attestation`

###Making an Attestation Servlet Request

Make a `POST` request to `http://localhost:8080/attestation`

####Required Params

#####nonce

This is generated like so, and then base64'd

```
sha256(username|password|timestamp|endpoint)
```

`username` is the Snapchat™ username you are attempting to login with.
`password` is the Snapchat™ password you are attempting to login with.
`timestamp` is the same timestamp you are going to send in the `/loq/login` request.
`endpoint` is at this stage always `/loq/login`

#####timestamp

The same timestamp you are going to send in the `/loq/login` request.

####Optional Params

#####app_name

This is the package name of the Snapchat™ APK.

#####apk_digest

This is the sha256 digest (base64'd) of the Snapchat™ APK.
Default is `JJShKOLH4YYjWZlJQ71A2dPTcmxbaMboyfo0nsKYayE=` which is for version `9.10.0.0`

#####apk_certificate_digest

This is the sha256 digest (base64'd) of the certificate used to sign the Snapchat™ APK.
It should never change, and the default is `Lxyq/KHtMNC044hj7vq+oOgVcR+kz3m4IlGaglnZWlg=`

#####droid_guard

This is the droidguard data, which you can find by sniffing a request when your Android device makes a normal Attestation request.

#####gms_version

Google Play Services version used to make the request as.
Default is `7329038`

#####user_agent

Android User Agent to make the request as.
Default is `SafetyNet/7329000 (klte KOT49H); gzip`

####Responses

If your request was successful, you'll get a response with a code of 200 in the JSON.

```
{
    "code": 200,
    "signedAttestation": "eyJhbGci...."
}
```

Grab the signedAttestation value, and send it in the Snapchat™ login request as the `attestation` param.
Any errors will be output like so:

```
{
    "code": xxx,
    "message": "Error Message"
}
```