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

public class LevelEndMenuRenderer {
private ChipMatch game;
	
	private OrthographicCamera  cam;
	private SpriteBatch spriteBatch;
	private UIButton continueButton;
	private boolean buttonClicked, animDone = false;
	private float animTime = 0, totalAnimTime = 2.0f;
	private Color color;
	private float oldAlpha;
	private BitmapFont ghost60, ghost70;
	
	public LevelEndMenuRenderer(ChipMatch game) {
		if(game.gameScore >= game.rankTargets[game.currentRank]) {
			game.currentRank++;
			game.actionResolver.unlockAchievementGPGS(game.achievementId[game.currentRank-1]);
		}
		this.game = game;
		
		ghost60 = new BitmapFont(Gdx.files.internal("fonts/ghost_35.fnt"));
//        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		ghost60.setColor(Color.WHITE);
		ghost70 = new BitmapFont(Gdx.files.internal("fonts/ghost_70.fnt"));
		ghost70.setColor(Color.WHITE);
		ghost60.setColor(Color.WHITE);
		ghost60.setScale(game.fontSize*game.pauseRatio);
		ghost60.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		ghost70.setScale(game.fontSize*game.pauseRatio);
		
		ghost70.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch = new SpriteBatch();
		
		continueButton = new UIButton(game, spriteBatch, 0, 0, game.assetsLoader.continue_button_normal.getRegionWidth()*game.pauseRatio, game.assetsLoader.continue_button_normal.getRegionHeight()*game.pauseRatio, "Continue", game.assetsLoader.continue_button_normal, game.assetsLoader.continue_button_pressed);
		
		continueButton.x = (Gdx.graphics.getWidth() - game.assetsLoader.continue_button_normal.getRegionWidth()*game.pauseRatio)/2;
		continueButton.y = (Gdx.graphics.getHeight() - game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio)/2 + game.frame_padding;
		continueButton.updateCoordinates();
		
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
		
		
		spriteBatch.draw(game.assetsLoader.level_finished_banner, (Gdx.graphics.getWidth()-game.assetsLoader.level_finished_banner.getRegionWidth()*game.pauseRatio)/2,
				(Gdx.graphics.getHeight()-game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio)/2+game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio-game.frame_padding-game.assetsLoader.level_finished_banner.getRegionHeight()*game.pauseRatio,
				game.assetsLoader.level_finished_banner.getRegionWidth()*game.pauseRatio, game.assetsLoader.level_finished_banner.getRegionHeight()*game.pauseRatio);
		buttonClicked = continueButton.render(buttonClicked, game.paused);
		
//		ghost60.draw(spriteBatch, "Your score is", (Gdx.graphics.getWidth() - ghost60.getBounds("Your score is").width)/2, 445);//495
		ghost60.draw(spriteBatch, "Your score is " + (game.gameScore) , 
				(Gdx.graphics.getWidth() - game.assetsLoader.yellow_frame.getRegionWidth()*game.pauseRatio)/2 + game.text_padding_left, 
				ghost60.getCapHeight()+continueButton.y+game.assetsLoader.continue_button_normal.getRegionHeight()*game.pauseRatio+game.continue_button_padding_top + (game.frame_row_height-ghost60.getCapHeight()*0.8f)/2 + game.frame_row_height * 3 + game.frame_separator_height * 4); 
		ghost60.draw(spriteBatch, "Current rank is "+game.rankNames[game.currentRank], 
				(Gdx.graphics.getWidth() - game.assetsLoader.yellow_frame.getRegionWidth()*game.pauseRatio)/2 + game.text_padding_left, 
				ghost60.getCapHeight()+continueButton.y+game.assetsLoader.continue_button_normal.getRegionHeight()*game.pauseRatio+game.continue_button_padding_top + (game.frame_row_height-ghost60.getCapHeight()*0.8f)/2 + game.frame_row_height * 2 + game.frame_separator_height * 3); //396
		ghost60.draw(spriteBatch, "Next rank at " + (game.rankTargets[game.currentRank]) + " pts", 
				(Gdx.graphics.getWidth() - game.assetsLoader.yellow_frame.getRegionWidth()*game.pauseRatio)/2 + game.text_padding_left, 
				ghost60.getCapHeight()+continueButton.y+game.assetsLoader.continue_button_normal.getRegionHeight()*game.pauseRatio+game.continue_button_padding_top + (game.frame_row_height-ghost60.getCapHeight()*0.8f)/2 + game.frame_row_height * 1 + game.frame_separator_height * 2); //396
		ghost60.draw(spriteBatch, "Level " + (1 + game.currentLevel) + " after " + game.levelTargets[game.currentLevel] + " matches", 
				(Gdx.graphics.getWidth() - game.assetsLoader.yellow_frame.getRegionWidth()*game.pauseRatio)/2 + game.text_padding_left, 
				ghost60.getCapHeight()+continueButton.y+game.assetsLoader.continue_button_normal.getRegionHeight()*game.pauseRatio+game.continue_button_padding_top + (game.frame_row_height-ghost60.getCapHeight()*0.8f)/2 + game.frame_row_height * 0 + game.frame_separator_height * 1); //396
		
		spriteBatch.draw(game.assetsLoader.yellow_frame, 
				(Gdx.graphics.getWidth() - game.assetsLoader.yellow_frame.getRegionWidth()*game.pauseRatio)/2, 
				continueButton.y+game.assetsLoader.continue_button_normal.getRegionHeight()*game.pauseRatio+game.continue_button_padding_top,
				game.assetsLoader.yellow_frame.getRegionWidth()*game.pauseRatio, game.assetsLoader.yellow_frame.getRegionHeight()*game.pauseRatio); 
		
		
		
		spriteBatch.end();
		cam.update();
	}
}