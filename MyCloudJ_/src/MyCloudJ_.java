import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import com.dropbox.core.*;
import ij.IJ;
import ij.gui.*;
import ij.plugin.*;
import DbxUtils.*;


public class MyCloudJ_ implements PlugIn {
	String code;
	boolean dialogCanceled = false;
	
	public void run(String arg) {
		IJ.error("Plugin started");
		mainProgram();
	}
	
	private void mainProgram(){ 
		DbxUtility obj = new DbxUtility();
		try {
			String authorizeUrl = obj.DbxLogin();
			IJ.error("MyCloudJ", "1. Go to: " +authorizeUrl +"\n 2. Click \"Allow\" (you might have to log in first)" +"\n 3. Copy the authorization code.");
			openDefaultBrowser(authorizeUrl);
	        String code = doDialog();
	        obj.DbxLinkUser(code);
	        IJ.error("Linked account: " + obj.client.getAccountInfo().displayName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Function to generate dialog box for entering the Access code from the user.
	private String doDialog() {
		GenericDialog gd = new GenericDialog("MyCloudJj");
		gd.addMessage("Enter the Dropbox Access Code here");
		gd.addStringField("Access Code: ", null, 20);
		gd.showDialog();
		if(gd.wasCanceled()) {
			dialogCanceled = true;
		}
		String code = gd.getNextString();
		return code;
	}
	
	// Function to open Dropbox APP URL in the default browser for user authentication 
	private void openDefaultBrowser(String url) {
		if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
	}
	
	
}

