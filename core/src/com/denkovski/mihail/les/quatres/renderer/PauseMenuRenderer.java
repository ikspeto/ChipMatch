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

public class PauseMenuRenderer {

	private ChipMatch game;

	private OrthographicCamera  cam;
	private SpriteBatch spriteBatch;
	private UIButton exitButton, retryButton;
	private boolean buttonClicked, animDone = false;
	private float animTime = 0, totalAnimTime = 2.0f;
	private Color color;
	private float oldAlpha;
	private BitmapFont ghost60, ghost125;
	
	public PauseMenuRenderer(ChipMatch game) {
		this.game = game;
		
		if(game.gameScore >= game.rankTargets[game.currentRank]) {
			game.currentRank++;
			game.actionResolver.unlockAchievementGPGS(game.achievementId[game.currentRank-1]);
		}
		
		
//		game.pauseRatio = (Gdx.graphics.getWidth()*0.95f) / game.assetsLoader.pause_menu_wood_frame.getWidth();
//		System.out.println(game.screenSize);
//		System.out.println(game.fontSize);
//		System.out.println(game.pauseRatio);
		ghost60 = new BitmapFont(Gdx.files.internal("fonts/ghost_60.fnt"));
//        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		ghost60.setColor(Color.WHITE);
		ghost125 = new BitmapFont(Gdx.files.internal("fonts/ghost_125.fnt"));
		ghost125.setColor(Color.WHITE);
		ghost60.setColor(Color.WHITE);
		ghost60.setScale(game.fontSize*game.pauseRatio);
		ghost60.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);    
		ghost125.setScale(game.fontSize*game.pauseRatio);
		ghost125.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//        font.setScale(2);
//        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);    
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch = new SpriteBatch();
		
		exitButton = new UIButton(game, spriteBatch, 0, 0, game.assetsLoader.exit_button_normal.getRegionWidth()*game.pauseRatio, game.assetsLoader.exit_button_normal.getRegionHeight()*game.pauseRatio, "Exit", game.assetsLoader.exit_button_normal, game.assetsLoader.exit_button_pressed);
		retryButton = new UIButton(game, spriteBatch, 0, 0, game.assetsLoader.retry_button_normal.getRegionWidth()*game.pauseRatio, game.assetsLoader.retry_button_normal.getRegionHeight()*game.pauseRatio, "Retry", game.assetsLoader.retry_button_normal, game.assetsLoader.retry_button_pressed);
		
		retryButton.x = (Gdx.graphics.getWidth()/2) + 5;
		retryButton.y = Gdx.graphics.getHeight()/2 - game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio/2 + game.frame_padding;
		retryButton.updateCoordinates();
		
		exitButton.x = (Gdx.graphics.getWidth()/2) - 5 - game.assetsLoader.exit_button_normal.getRegionWidth()*game.pauseRatio;
		exitButton.y = Gdx.graphics.getHeight()/2 - game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio/2 + game.frame_padding;
		exitButton.updateCoordinates();
		
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
		
//		spriteBatch.setColor(tint);
		spriteBatch.draw(game.assetsLoader.dark_tint, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		spriteBatch.draw(game.assetsLoader.pause_menu_wood_frame, Gdx.graphics.getWidth()/2-game.assetsLoader.pause_menu_wood_frame.getWidth()*game.pauseRatio/2, 
//															 (Gdx.graphics.getHeight()-game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio)/2,
//															 game.assetsLoader.pause_menu_wood_frame.getWidth()*game.pauseRatio, game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio);
		spriteBatch.draw(game.assetsLoader.pause_menu_wood_frame, Gdx.graphics.getWidth()/2-game.assetsLoader.pause_menu_wood_frame.getWidth()*game.pauseRatio/2, 
				 (Gdx.graphics.getHeight()-game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio)/2,
				 game.assetsLoader.pause_menu_wood_frame.getWidth()*game.pauseRatio, game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio);
		spriteBatch.draw(game.assetsLoader.game_over_banner, (Gdx.graphics.getWidth()-game.assetsLoader.game_over_banner.getRegionWidth()*game.pauseRatio)/2,
				(Gdx.graphics.getHeight()-game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio)/2+game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio-game.frame_padding-game.assetsLoader.game_over_banner.getRegionHeight()*game.pauseRatio,
				game.assetsLoader.game_over_banner.getRegionWidth()*game.pauseRatio, game.assetsLoader.game_over_banner.getRegionHeight()*game.pauseRatio);
//		spriteBatch.draw(game.assetsLoader.game_over_banner, (Gdx.graphics.getWidth()-game.assetsLoader.game_over_banner.getWidth()*game.pauseRatio)/2,
//				Gdx.graphics.getHeight()/2+game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio/2-220);
		buttonClicked = retryButton.render(buttonClicked, game.paused);
		buttonClicked = exitButton.render(buttonClicked, game.paused);

		ghost60.draw(spriteBatch, "Your score is", (Gdx.graphics.getWidth() - ghost60.getBounds("Your score is").width)/2, (Gdx.graphics.getHeight()-game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio)/2+game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio-game.frame_padding-game.assetsLoader.game_over_banner.getRegionHeight()*game.pauseRatio-game.game_over_banner_padding_bottom);//495
		ghost125.draw(spriteBatch, Integer.toString(game.gameScore), 
				(Gdx.graphics.getWidth() - ghost125.getBounds(Integer.toString(game.gameScore)).width)/2, 
				exitButton.y+game.assetsLoader.exit_button_normal.getRegionHeight()*game.pauseRatio+game.exit_button_padding_top+ghost125.getCapHeight()); //396
		
		
		
		spriteBatch.end();
		cam.update();
	}
	
//	private void recordScore() {
//		game.actionResolver.submitScoreGPGS(game.gameScore);
//	}
}