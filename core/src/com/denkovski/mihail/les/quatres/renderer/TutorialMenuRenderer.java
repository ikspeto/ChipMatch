package com.denkovski.mihail.les.quatres.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.denkovski.mihail.les.quatres.controller.UIButton;
import com.denkovski.mihail.les.quatres.utility.Animation;

public class TutorialMenuRenderer {
private ChipMatch game;

	private OrthographicCamera  cam;
	private SpriteBatch spriteBatch;
	private UIButton continueButton;
	private boolean buttonClicked, animDone = false;
	private float animTime = 0, totalAnimTime = 2.0f;
	private Color color;
	private float oldAlpha;
	private BitmapFont arial;

	public TutorialMenuRenderer(ChipMatch game) {
		if(game.gameScore >= game.rankTargets[game.currentRank]) {
			game.currentRank++;
			game.actionResolver.unlockAchievementGPGS(game.achievementId[game.currentRank-1]);
		}
		this.game = game;

        if(game.assetsLoader.density == "hdpi") {
            arial = new BitmapFont(Gdx.files.internal("fonts/arial_35.fnt"));
        } else {
            arial = new BitmapFont(Gdx.files.internal("fonts/arial_40.fnt"));
        }
        arial.setColor(Color.WHITE);
        arial.setScale(game.fontSize*game.pauseRatio);
        arial.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
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
		spriteBatch.draw(game.assetsLoader.pause_menu_wood_frame,
                Gdx.graphics.getWidth()/2-game.assetsLoader.pause_menu_wood_frame.getWidth()*game.pauseRatio/2,
				 (Gdx.graphics.getHeight()-game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio)/2,
				 game.assetsLoader.pause_menu_wood_frame.getWidth()*game.pauseRatio,
                game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio);
		
		
		spriteBatch.draw(game.assetsLoader.tutorial_banner,
                (Gdx.graphics.getWidth()-game.assetsLoader.tutorial_banner.getWidth()*game.pauseRatio)/2,
				(Gdx.graphics.getHeight()-game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio)/2+game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio-game.frame_padding-game.assetsLoader.tutorial_banner.getHeight()*game.pauseRatio,
				game.assetsLoader.tutorial_banner.getWidth()*game.pauseRatio,
                game.assetsLoader.tutorial_banner.getHeight()*game.pauseRatio);
		buttonClicked = continueButton.render(buttonClicked, game.paused);


        arial.drawWrapped(spriteBatch, game.tutorial_texts[0], Gdx.graphics.getWidth()/2-game.assetsLoader.pause_menu_wood_frame.getWidth()*game.pauseRatio/2+30 * game.pauseRatio,
                (Gdx.graphics.getHeight()-game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio)/2+game.assetsLoader.pause_menu_wood_frame.getHeight()*game.pauseRatio-game.frame_padding-game.assetsLoader.tutorial_banner.getHeight()*game.pauseRatio-20,
                Gdx.graphics.getWidth() - (Gdx.graphics.getWidth()/2-game.assetsLoader.pause_menu_wood_frame.getWidth()*game.pauseRatio/2) - 30 * game.pauseRatio * 2);

		
		spriteBatch.end();
		cam.update();
	}
}