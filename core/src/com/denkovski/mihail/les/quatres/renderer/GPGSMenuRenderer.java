package com.denkovski.mihail.les.quatres.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.denkovski.mihail.les.quatres.controller.UIButton;
import com.denkovski.mihail.les.quatres.utility.Animation;

public class GPGSMenuRenderer {

	private ChipMatch game;
	
	private OrthographicCamera  cam;
	private SpriteBatch spriteBatch;
	private UIButton achievementsButton, leaderboardButton;
	private boolean buttonClicked, animDone = false;
	private float animTime = 0, totalAnimTime = 2.0f;
	private Color color;
	private float oldAlpha;
	
	public GPGSMenuRenderer(ChipMatch game) {
		this.game = game;
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch = new SpriteBatch();
		
		achievementsButton = new UIButton(game, spriteBatch, 0, 0, game.assetsLoader.achievements_banner_button_normal.getRegionWidth()*game.pauseRatio, game.assetsLoader.achievements_banner_button_normal.getRegionHeight()*game.pauseRatio, "AchievementsB", game.assetsLoader.achievements_banner_button_normal, game.assetsLoader.achievements_banner_button_pressed);
		leaderboardButton = new UIButton(game, spriteBatch, 0, 0, game.assetsLoader.leaderboard_banner_button_normal.getRegionWidth()*game.pauseRatio, game.assetsLoader.leaderboard_banner_button_normal.getRegionHeight()*game.pauseRatio, "LeaderboardB", game.assetsLoader.leaderboard_banner_button_normal, game.assetsLoader.leaderboard_banner_button_pressed);
		
		achievementsButton.x = (Gdx.graphics.getWidth() - game.assetsLoader.achievements_banner_button_normal.getRegionWidth() * game.pauseRatio)/2;
		achievementsButton.y = Gdx.graphics.getHeight()/2 + game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio/13 - game.frame_padding;
		achievementsButton.updateCoordinates();
		
		leaderboardButton.x = (Gdx.graphics.getWidth() - game.assetsLoader.leaderboard_banner_button_normal.getRegionWidth() * game.pauseRatio)/2;
		leaderboardButton.y = Gdx.graphics.getHeight()/2 - game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio/13 + game.frame_padding - game.assetsLoader.leaderboard_banner_button_normal.getRegionHeight()*game.pauseRatio;
		leaderboardButton.updateCoordinates();
		
		color = spriteBatch.getColor();
		oldAlpha = color.a;
		
		buttonClicked = false;
	}
	
//	private Color color;
	
	public void render(float delta) {
		
		spriteBatch.begin();
		if(!animDone) {
			if(animTime < totalAnimTime) {
				color.a = Animation.easeOutQuad(animTime, 0, oldAlpha, totalAnimTime);
				spriteBatch.setColor(color);
				animTime += delta;
			} else {
				color.a = oldAlpha;
				spriteBatch.setColor(color);
				animDone = true;
			}
		}
		
		spriteBatch.draw(game.assetsLoader.dark_tint, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch.draw(game.assetsLoader.pause_menu_wood_frame, Gdx.graphics.getWidth()/2-game.assetsLoader.pause_menu_wood_frame.getWidth()*game.pauseRatio/2, 
				 (Gdx.graphics.getHeight()-game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio)/2,
				 game.assetsLoader.pause_menu_wood_frame.getWidth()*game.pauseRatio, game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio);
		
		buttonClicked = achievementsButton.render(buttonClicked, game.paused);
		buttonClicked = leaderboardButton.render(buttonClicked, game.paused);
		
		
		spriteBatch.end();
		cam.update();
	}
	
//	private void recordScore() {
//		game.actionResolver.submitScoreGPGS(game.gameScore);
//	}
}