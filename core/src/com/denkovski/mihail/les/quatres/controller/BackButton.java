package com.denkovski.mihail.les.quatres.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.denkovski.mihail.les.quatres.ChipMatch.ScreenRatio;
import com.denkovski.mihail.les.quatres.screen.MainMenuScreen;

public class BackButton {
	
	private enum ButtonState { DEFAULT, PRESSED }
	private ChipMatch game;
	
	private SpriteBatch spriteBatch;
	private ButtonState buttonState;
	private boolean clicked;
	private float x, y, gRatio;
	
	public BackButton(SpriteBatch spriteBatch, float x, float y, ChipMatch game) {
		this.spriteBatch = spriteBatch;
		this.game = game;
		this.x = x;
		this.y = y;
		buttonState = ButtonState.DEFAULT;
		clicked = false;
		gRatio = (Gdx.graphics.getHeight() * 0.2f)/game.assetsLoader.wood_frame_top.getHeight();
	}
	
	public boolean render() {
		if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) 
			clicked = false;
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !clicked) {
			if(Gdx.input.getX() >= x && Gdx.input.getX() <= x + game.assetsLoader.back_button_normal.getRegionWidth()*game.gameRatio && (Gdx.graphics.getHeight() - Gdx.input.getY()) >= y && (Gdx.graphics.getHeight() - Gdx.input.getY()) <= y + game.assetsLoader.back_button_normal.getRegionHeight()*game.gameRatio) {
				buttonState = ButtonState.PRESSED;
			}
		}
		if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !clicked && buttonState == ButtonState.PRESSED) {
			if(Gdx.input.getX() >= x && Gdx.input.getX() <= x + game.assetsLoader.back_button_normal.getRegionWidth()*game.gameRatio && (Gdx.graphics.getHeight() - Gdx.input.getY()) >= y && (Gdx.graphics.getHeight() - Gdx.input.getY()) <= y + game.assetsLoader.back_button_normal.getRegionHeight()*game.gameRatio) {
//				setScreen(new MainMenuScreen(game));
//				Gdx.app.exit();
				return true;
			} else {
				buttonState = ButtonState.DEFAULT;
			}
		}
		if(buttonState == ButtonState.DEFAULT)
			spriteBatch.draw(game.assetsLoader.back_button_normal, x, y, game.assetsLoader.back_button_normal.getRegionWidth()*gRatio, game.assetsLoader.back_button_normal.getRegionHeight()*gRatio);
		else 
			spriteBatch.draw(game.assetsLoader.back_button_pressed, x, y, game.assetsLoader.back_button_pressed.getRegionWidth()*gRatio, game.assetsLoader.back_button_pressed.getRegionHeight()*gRatio);
		return false;
	}
}
