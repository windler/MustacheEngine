package de.windler.game;

import de.nw90.jawi.R;
import de.windler.engine.mustacheengine.game.engine.EngineOptions;
import de.windler.engine.mustacheengine.game.engine.IGameEngine;
import de.windler.engine.mustacheengine.game.engine.policy.ResolutionPolicy;
import de.windler.engine.mustacheengine.game.font.Font;
import de.windler.engine.mustacheengine.game.ui.BaseGameActivity;
import de.windler.engine.mustacheengine.gamescreen.GameScreenManager;
import de.windler.game.GameScreen.LayerTestScreen;

public class AndroidGameActivity extends BaseGameActivity {

	@Override
	public EngineOptions onCreateGameOptions() {
		EngineOptions options = new EngineOptions(ResolutionPolicy.FILL, 800,
				480, true, true);
		return options;
	}

	@Override
	public void onCreateGameScreenManager(GameScreenManager gsm) {
		gsm.push(new LayerTestScreen(gsm));

	}

	@Override
	public void onLoadResources(IGameEngine engine) {
		Font standardFont = new Font("standard", this.getApplicationContext(),
				new int[] { 4 }, new int[] { 26 },
				new int[] { R.drawable.glyphs_black });
		standardFont.createCapitalLetters(2, 1, 0);
		standardFont.createSmallLetters(1, 1, 0);
		standardFont.createNumbers(3, 1, 0);
		standardFont.recycle();
		engine.registerFont(standardFont);

		// engine.registerMusic("intro", R.raw.intro);
		// engine.registerSound("btnClick", R.raw.btn);

	}

	@Override
	public int[] onLoadTextures() {
		int[] ids = { R.drawable.diamond, R.drawable.star };
		return ids;
	}
}