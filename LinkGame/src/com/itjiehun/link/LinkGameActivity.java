package com.itjiehun.link;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.widget.RadioButton;
import android.widget.TextView;

import com.itjiehun.link.game.Config;
import com.itjiehun.link.game.GameView;
import com.itjiehun.link.source.InnerGameReader;

public class LinkGameActivity extends Activity {

	private GameView gameView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String jsonConfig = getIntent().getStringExtra(Constants.GAME_TYPE_JSON_CONFIG_EXTRA);
		if (TextUtils.isEmpty(jsonConfig)) {
			finish();
			return;
		}
		Display display = getWindowManager().getDefaultDisplay();
		Config.setScreenWidth(display.getWidth());
		Config.setScreenHeight(display.getHeight());
		setContentView(R.layout.link_game_activity);
		gameView = (GameView) findViewById(R.id.gameView);
		gameView.setTimeTv((TextView) findViewById(R.id.timeTv));
		gameView.setLevelTv((TextView) findViewById(R.id.levelTv));
		
		gameView.setBreakCardsBtn((RadioButton) findViewById(R.id.breakCardsBtn));
		gameView.setNoteBtn((RadioButton) findViewById(R.id.noteBtn));
		gameView.setPauseBtn((RadioButton) findViewById(R.id.pauseBtn));
		
		gameView.initWithGamePkg(InnerGameReader.readGame(this, jsonConfig));
		gameView.showStartGameAlert();
	}

	protected void onPause() {
		gameView.pause();
		super.onPause();
	}

	protected void onResume() {
		gameView.resume();
		super.onResume();
	}
}