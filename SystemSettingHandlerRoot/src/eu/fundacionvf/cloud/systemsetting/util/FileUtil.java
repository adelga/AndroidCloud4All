package eu.fundacionvf.cloud.systemsetting.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class FileUtil {

	private Context cntx;

	public FileUtil(Context c) {
		this.cntx = c;
	}

	public void copyAsset(String nameAsset, String dest) {
		AssetManager assetManager = cntx.getAssets();
		InputStream in = null;
		OutputStream out = null;
		try {
			in = assetManager.open(nameAsset);
			out = new FileOutputStream(dest);
			copyFile(in, out);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
		} catch (IOException e) {
			Log.e("tag", "Failed to copy asset file: " + nameAsset, e);
		}

	}

	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

}
