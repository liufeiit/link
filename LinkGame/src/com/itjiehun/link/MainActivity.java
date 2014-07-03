package com.itjiehun.link;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.plter.lib.android.java.controls.ArrayAdapter;

public class MainActivity extends ListActivity {
	
	private String TAG = MainActivity.class.getName();

	private ArrayAdapter<GameListCellData> adapter;
	private ProgressDialog dialog = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (Build.VERSION.SDK_INT >= 11) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
					WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		}
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		boolean isOrientationEnabled = false;
		try {
			isOrientationEnabled = Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION) == 1;
		} catch (SettingNotFoundException e) {
			Log.e(TAG, "System Setting Error.", e);
		}
		int screenLayout = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
		if ((screenLayout == Configuration.SCREENLAYOUT_SIZE_LARGE || screenLayout == Configuration.SCREENLAYOUT_SIZE_XLARGE)
				&& isOrientationEnabled) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		}
		setContentView(R.layout.main_activity);
		adapter = new ArrayAdapter<MainActivity.GameListCellData>(this, R.layout.game_list_cell) {
			@Override
			public void initListCell(int position, View listCell, ViewGroup parent) {
				ImageView iconIv = (ImageView) listCell.findViewById(R.id.iconIv);
				TextView labelTv = (TextView) listCell.findViewById(R.id.labelTv);
				GameListCellData data = getItem(position);
				iconIv.setImageResource(data.iconResId);
				labelTv.setText(data.label);
			}
		};
		setListAdapter(adapter);
		adapter.add(new GameListCellData("水果连连看", R.drawable.sg_icon, "sg_config.json"));
		adapter.add(new GameListCellData("蔬菜连连看", R.drawable.sc_icon, "sc_config.json"));
		adapter.add(new GameListCellData("动物连连看", R.drawable.dw_icon, "dw_config.json"));
		adapter.add(new GameListCellData("爱心连连看", R.drawable.love_icon, "love_config.json"));
		adapter.add(new GameListCellData("宝石连连看", R.drawable.coin_icon, "coin_config.json"));
	}

	@Override
	protected void onPause() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
		super.onPause();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		dialog = ProgressDialog.show(this, "请稍候", "正在加载游戏资源");
		GameListCellData data = adapter.getItem(position);
		Intent i = new Intent(this, LinkGameActivity.class);
		i.putExtra(Constants.GAME_TYPE_JSON_CONFIG_EXTRA, data.gameJsonConfig);
		startActivity(i);
		super.onListItemClick(l, v, position, id);
	}

	public static class GameListCellData {
		public final String label;
		public final int iconResId;
		public final String gameJsonConfig;
		public GameListCellData(String label, int iconResId, String gameConfigFile) {
			this.label = label;
			this.iconResId = iconResId;
			this.gameJsonConfig = gameConfigFile;
		}
	}
}
