package DbxUtils;

import com.dropbox.core.*;
import java.io.*;
import java.util.Locale;

public class DbxUtility {
	
	 	// Got this APP Key and Secret from the Developer's APP console
		final private String APP_KEY = "5jysg1bzg0ulli3";
		final private String APP_SECRET = "t0ln07k26pctonw";
		public DbxClient client;
		DbxWebAuthNoRedirect webAuth;
		DbxRequestConfig config;
		DbxAppInfo appInfo;
		String authorizeUrl;
		
		// Function for User Sign-in and Allow the APP 
		public String DbxLogin() throws IOException, DbxException {
			appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

	        config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());
	        webAuth = new DbxWebAuthNoRedirect(config, appInfo);

	        // Have the user sign in and authorize your APP .
	        authorizeUrl = webAuth.start();
	        return authorizeUrl;
		}

		// Function to Accept the access code and link the account
		public void DbxLinkUser(String code) throws IOException, DbxException {
			// Need to access the code
	        //String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
	        // This will fail if the user enters an invalid authorization code.
	        DbxAuthFinish authFinish = webAuth.finish(code);
	        String accessToken = authFinish.accessToken;
	        client = new DbxClient(config, accessToken);
		}

		public void sample() throws IOException, DbxException {
	        File inputFile = new File("D:\\Workspace\\DropboxClientSample\\src\\Desert.jpg");
	        FileInputStream inputStream = new FileInputStream(inputFile);
	        try {
	        	System.out.println("hello\n");
	            DbxEntry.File uploadedFile = client.uploadFile("/test/ABCD.jpg",
	                DbxWriteMode.add(), inputFile.length(), inputStream);
	            System.out.println("Uploaded: " + uploadedFile.toString());
	        } finally {
	            inputStream.close();
	        }

	        DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
	        System.out.println("Files in the root path:");
	        for (DbxEntry child : listing.children) {
	            System.out.println("	" + child.name + ": " + child.toString());
	        }

	        FileOutputStream outputStream = new FileOutputStream("ABCD.jpg");
	        try {
	            DbxEntry.File downloadedFile = client.getFile("/test/ABCD.jpg", null,
	                outputStream);
	            System.out.println("Metadata: " + downloadedFile.toString());
	        } finally {
	            outputStream.close();
	        }
		}
 
}