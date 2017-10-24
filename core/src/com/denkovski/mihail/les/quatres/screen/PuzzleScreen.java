package com.denkovski.mihail.les.quatres.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.denkovski.mihail.les.quatres.ChipMatch.GameType;
import com.denkovski.mihail.les.quatres.renderer.LevelEndMenuRenderer;
import com.denkovski.mihail.les.quatres.renderer.PauseMenuRenderer;
import com.denkovski.mihail.les.quatres.renderer.PuzzleRenderer;
import com.denkovski.mihail.les.quatres.renderer.ScoreRenderer;
import com.denkovski.mihail.les.quatres.renderer.TutorialRenderer;
import com.denkovski.mihail.les.quatres.renderer.UIRenderer;

public class PuzzleScreen implements Screen, InputProcessor {

	private ChipMatch game;
	private OrthographicCamera  cam;
	private PuzzleRenderer puzzleRenderer;
	private UIRenderer uiRenderer;
	private ScoreRenderer scoreRenderer;
	private PauseMenuRenderer pauseMenuRenderer;
	private LevelEndMenuRenderer levelEndMenuRenderer;
	private boolean tutorialFinished;
	
	public AssetManager manager = new AssetManager();

	public PuzzleScreen(ChipMatch game) {
		
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		
		this.game = game;
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		scoreRenderer = new ScoreRenderer(game);
		game.gameType = GameType.ZEN;
		puzzleRenderer = new PuzzleRenderer(game, scoreRenderer);
		uiRenderer = new UIRenderer(game);
		pauseMenuRenderer = new PauseMenuRenderer(game);
		levelEndMenuRenderer = new LevelEndMenuRenderer(game);
		
		game.gameScore = 0;
		game.matchesNumber = 0;
		game.currentLevel = 0;
		game.paused = false;
		
//		if(game.prefs.contains("tutorialFinished")) {
//			tutorialFinished = true;
//            game.revMobResolver.showBannerAd(true);
//		} else {
//			tutorialFinished = false;
//            game.revMobResolver.showBannerAd(false);
//		}
	}

	public void render(float delta) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl20.glClearColor(0.0f, 0.0f, 0.0f, 1);

			uiRenderer.render(delta);
			puzzleRenderer.render(delta);
			scoreRenderer.render(delta);
			if(game.levelEnd || game.paused) {
				if(game.showAds) {
					game.revMobResolver.showFullscreenAd(true);
				} else {
					game.revMobResolver.showFullscreenAd(false);
				}
				game.showAds = !game.showAds;
			}
			if(game.levelEnd)
				levelEndMenuRenderer.render(delta);
			else if(game.paused)
				pauseMenuRenderer.render(delta);

		
		cam.update();
	}

	@Override
	public void resize(int width, int height) {
//		aspectRatio = width / height;
		cam.setToOrtho(false, width, height);
		uiRenderer.resize();
		puzzleRenderer.resize();
	}

	@Override
	public void show() {
		game.revMobResolver.showBannerAd(true);
	}

	@Override
	public void hide() {
		// called when current screen changes from this to a different screen
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		game.revMobResolver.showBannerAd(true);
	}

	@Override
	public void dispose() {
		puzzleRenderer.dispose();
		uiRenderer.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK) {
			if(!game.paused)
				game.setScreen(new MainMenuScreen(game));
            else
                game.paused = false;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}