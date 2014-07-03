package com.itjiehun.link.reader;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.itjiehun.link.Constants;

public class InnerGameReader {

	public static GamePkg readGame(Context context, String configFile) {
		try {
			InputStream is = context.getAssets().open(configFile);
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			String content = new String(bytes, Constants.UTF_8_CHARSET_NAME);
			is.close();
			JSONObject jsonObject = new JSONObject(content);
			String name = jsonObject.getString(Constants.GAME_NAME);
			is = context.getAssets().open(jsonObject.getString(Constants.GAME_BACKGROUND));
			Bitmap backgroundBitmap = BitmapFactory.decodeStream(is);
			is.close();
			JSONArray picsJsonArr = jsonObject.getJSONArray(Constants.GAME_PICTURES);
			Picture[] pictures = new Picture[picsJsonArr.length()];
			for (int i = 0; i < pictures.length; i++) {
				is = context.getAssets().open(picsJsonArr.getString(i));
				pictures[i] = new Picture(BitmapFactory.decodeStream(is));
				is.close();
			}
			return new GamePkg(name, new Background(backgroundBitmap), pictures);
		} catch (IOException e) {
			Toast.makeText(context, "读取游戏资源失败", Toast.LENGTH_SHORT);
			e.printStackTrace();
		} catch (JSONException e) {
			Toast.makeText(context, "解析游戏资源失败", Toast.LENGTH_SHORT);
			e.printStackTrace();
		}
		return null;
	}
}
