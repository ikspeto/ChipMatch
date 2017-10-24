package com.denkovski.mihail.les.quatres.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.denkovski.mihail.les.quatres.controller.UIButton;
import com.denkovski.mihail.les.quatres.renderer.GPGSMenuRenderer;

public class MainMenuScreen implements Screen, InputProcessor  {

	private static ChipMatch game;
	
	private OrthographicCamera  cam;
	private SpriteBatch spriteBatch;
	private UIButton actionButton, zenButton, optionsButton, shareButton, rateButton, leaderBoards;
	private boolean buttonClicked;
	private int tileCount;
	private GPGSMenuRenderer gpgsMenuRenderer;
    private boolean leaderboardsOpen;
	
	
	public MainMenuScreen(ChipMatch game) {
		this.game = game;
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		
		game.paused = false;
//        game.leaderboardsOpen = false;
		
		gpgsMenuRenderer = new GPGSMenuRenderer(game);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch = new SpriteBatch();
		
		actionButton = new UIButton(game, spriteBatch, 0, 0, game.assetsLoader.main_menu_action_button_normal.getRegionWidth()*game.menuRatio, game.assetsLoader.main_menu_action_button_normal.getRegionHeight()*game.menuRatio, "Action", game.assetsLoader.main_menu_action_button_normal, game.assetsLoader.main_menu_action_button_pressed);
		zenButton = new UIButton(game, spriteBatch, 0, 0, game.assetsLoader.main_menu_zen_button_normal.getRegionWidth()*game.menuRatio, game.assetsLoader.main_menu_zen_button_normal.getRegionHeight()*game.menuRatio, "Zen", game.assetsLoader.main_menu_zen_button_normal, game.assetsLoader.main_menu_zen_button_pressed);
		optionsButton = new UIButton(game, spriteBatch, 0, 0, game.assetsLoader.main_menu_options_button_normal.getRegionWidth()*game.menuRatio, game.assetsLoader.main_menu_options_button_normal.getRegionHeight()*game.menuRatio, "MoreGames", game.assetsLoader.main_menu_options_button_normal, game.assetsLoader.main_menu_options_button_pressed);
		shareButton = new UIButton(game, spriteBatch, 0, 0, game.assetsLoader.share_button_normal.getRegionWidth()*game.menuRatio, game.assetsLoader.share_button_normal.getRegionHeight()*game.menuRatio, "Share", game.assetsLoader.share_button_normal, game.assetsLoader.share_button_pressed);
		rateButton = new UIButton(game, spriteBatch, 0, 0, game.assetsLoader.rate_button_normal.getRegionWidth()*game.menuRatio, game.assetsLoader.rate_button_normal.getRegionHeight()*game.menuRatio, "Rate", game.assetsLoader.rate_button_normal, game.assetsLoader.rate_button_pressed);
		leaderBoards = new UIButton(game, spriteBatch, 0, 0, game.assetsLoader.leaderboards_button_normal.getRegionWidth()*game.menuRatio, game.assetsLoader.leaderboards_button_normal.getRegionHeight()*game.menuRatio, "Leaderboards", game.assetsLoader.leaderboards_button_normal, game.assetsLoader.leaderboards_button_pressed);
		
		
		actionButton.x = (Gdx.graphics.getWidth() - game.assetsLoader.main_menu_action_button_normal.getRegionWidth()*game.menuRatio)/2;
//		actionButton.y = (Gdx.graphics.getHeight() - game.menu_heading_padding_top - game.menu_heading_padding_bottom - game.assetsLoader.main_menu_heading.getHeight()*game.menuRatio) - game.assetsLoader.main_menu_action_button_normal.getHeight()*game.menuRatio;
		actionButton.y = Gdx.graphics.getHeight()-game.frame_size-game.heading_space_height-game.frame_size-(game.heading_space_height - game.assetsLoader.main_menu_heading.getHeight()*game.menuRatio)/2 - game.assetsLoader.main_menu_action_button_normal.getRegionHeight()*game.menuRatio;
		actionButton.updateCoordinates();
	
		zenButton.x = (Gdx.graphics.getWidth() - game.assetsLoader.main_menu_zen_button_normal.getRegionWidth()*game.menuRatio)/2;
		zenButton.y = (actionButton.y - game.assetsLoader.main_menu_zen_button_normal.getRegionHeight()*game.menuRatio) + game.menu_button_padding_top;
		zenButton.updateCoordinates();
		
		optionsButton.x = (Gdx.graphics.getWidth() - game.assetsLoader.main_menu_options_button_normal.getRegionWidth()*game.menuRatio)/2;
		optionsButton.y = (zenButton.y - game.assetsLoader.main_menu_options_button_normal.getRegionHeight()*game.menuRatio) + game.menu_button_padding_top;
		optionsButton.updateCoordinates();
		
		shareButton.x = game.share_button_padding_left;
		shareButton.y = game.share_button_padding_bottom;
		shareButton.updateCoordinates();
		
		rateButton.x = Gdx.graphics.getWidth() - game.rate_button_padding_right - game.assetsLoader.rate_button_normal.getRegionWidth()*game.menuRatio;
		rateButton.y = game.rate_button_padding_bottom;
		rateButton.updateCoordinates();
		
		leaderBoards.x = game.leaderboards_button_padding_left;
		leaderBoards.y = game.leaderboards_button_padding_bottom;
		leaderBoards.updateCoordinates();
		
		
		buttonClicked = false;
		tileCount = (int)(Gdx.graphics.getHeight()/game.assetsLoader.background_pattern.getHeight())+1;
		
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl20.glClearColor(0.0f, 0.0f, 0.0f, 1);
		
		spriteBatch.begin();
		

        spriteBatch.draw(game.assetsLoader.background_pattern, 0, 0,
    			game.assetsLoader.background_pattern.getWidth() * tileCount, 
    			game.assetsLoader.background_pattern.getHeight() * tileCount, 
    					0, tileCount, 
    					tileCount, 0);
		
		spriteBatch.draw(game.assetsLoader.main_menu_wood_frame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		spriteBatch.draw(game.assetsLoader.main_menu_heading, 
				(Gdx.graphics.getWidth()-game.assetsLoader.main_menu_heading.getWidth()*game.menuRatio)/2, 
				Gdx.graphics.getHeight()- game.menu_heading_padding_top - game.assetsLoader.main_menu_heading.getHeight()*game.menuRatio,
				game.assetsLoader.main_menu_heading.getWidth()*game.menuRatio, game.assetsLoader.main_menu_heading.getHeight()*game.menuRatio);
		
		buttonClicked = zenButton.render(buttonClicked, !game.paused);
		buttonClicked = actionButton.render(buttonClicked, !game.paused);
		buttonClicked = optionsButton.render(buttonClicked, !game.paused);
		buttonClicked = shareButton.render(buttonClicked, !game.paused);
		buttonClicked = rateButton.render(buttonClicked, !game.paused);
		buttonClicked = leaderBoards.render(buttonClicked, !game.paused);
		
//		spriteBatch.draw(game.assetsLoader.sampleads, 0, 0);
		
		if(game.paused) {
			gpgsMenuRenderer.render(delta);
		}
		
		spriteBatch.end();
		cam.update();
	}

	@Override
	public void resize(int width, int height) {		
		cam.setToOrtho(false, width, height);		
	}

	@Override
	public void show() {
		game.revMobResolver.showBannerAd(false);
		game.revMobResolver.loadLink();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		game.revMobResolver.showBannerAd(false);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK) {
			if(game.paused)
				game.paused = false;
			else
				game.actionResolver.exitApp();
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
