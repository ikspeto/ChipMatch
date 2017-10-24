package com.denkovski.mihail.les.quatres.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.denkovski.mihail.les.quatres.ChipMatch.GameType;
import com.denkovski.mihail.les.quatres.screen.ActionScreen;
import com.denkovski.mihail.les.quatres.screen.MainMenuScreen;
import com.denkovski.mihail.les.quatres.screen.PuzzleScreen;
import com.denkovski.mihail.les.quatres.screen.TutorialScreen;

public class UIButton {

    ChipMatch game;
	
	public float x, y, width, height;
	public Rectangle bounds;
	public String text;
	
	private SpriteBatch spriteBatch;
	private boolean buttonClicked;
	private BitmapFont font;
	private boolean bclicked, bpressed;
	private PuzzleScreen puzzleScreen;
	private ActionScreen actionScreen;
    private TutorialScreen tutorialScreen;
	
	private TextureRegion normal, pressed;

	public UIButton(ChipMatch game, SpriteBatch spriteBatch, float x, float y, float width, float height, String text, TextureRegion normal, TextureRegion pressed) {
		this.game = game;
		this.normal = normal;
		this.pressed = pressed;
		
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.text = text;
		this.spriteBatch = spriteBatch;
		
		font = new BitmapFont();
		font.setScale(3);
		
		bounds = new Rectangle();
		bounds.x = x;
		bounds.y = y;
		if(text == "Zen") {
			bounds.width = width-45*game.menuRatio;
			bounds.height = height-45*game.menuRatio;
			bounds.y += 45*game.menuRatio;
		} else if(text == "Action") {
			bounds.width = width-45*game.menuRatio;
			bounds.height = height-45*game.menuRatio;
			bounds.y += 45*game.menuRatio;
		} else if(text == "Options") {
			bounds.width = width-45*game.menuRatio;
			bounds.height = height-45*game.menuRatio;
			bounds.y += 45*game.menuRatio;
		} else {
			bounds.width = width;
			bounds.height = height;
		}
		bpressed = false;
		bclicked = false;
        game.prefs.remove("tutorialFinished");
        //if(!game.prefs.contains("tutorialFinished")) {
        if(text == "tutorialFinished") {
            tutorialScreen = new TutorialScreen(game);
        } else {
            if (text == "Zen") {
                puzzleScreen = new PuzzleScreen(game);
            } else if (text == "Action") {
                actionScreen = new ActionScreen(game);
            }
        }
	}
	
	public UIButton() {
		// TODO Auto-generated constructor stub
	}

	public boolean render(boolean buttonClicked, boolean check) {
		this.buttonClicked = buttonClicked;
		if(!buttonClicked || (buttonClicked && bpressed)) {
			if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				bclicked = false;
				buttonClicked = false;
			}
			if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !bclicked && bpressed) {
				if(Gdx.input.getX() >= bounds.x && Gdx.input.getX() <= bounds.x + bounds.width && (Gdx.graphics.getHeight() - Gdx.input.getY()) >= bounds.y && (Gdx.graphics.getHeight() - Gdx.input.getY()) <= bounds.y + bounds.height) {
//					pressed = false;
//					buttonClicked = false;
				} else {
					bpressed = false;
					buttonClicked = false;
//					clicked = false;
				}
			}
			
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !bclicked) {
				if(Gdx.input.getX() >= bounds.x && Gdx.input.getX() <= bounds.x + bounds.width && (Gdx.graphics.getHeight() - Gdx.input.getY()) >= bounds.y && (Gdx.graphics.getHeight() - Gdx.input.getY()) <= bounds.y + bounds.height) {
					bpressed = true;
					buttonClicked = true;
//					clicked = true;
				}
			}
			
			if(!bpressed) {
//				spriteBatch.draw(normal,  x,  y, normal.getWidth() * game.menuRatio, normal.getHeight() * game.menuRatio);
				spriteBatch.draw(normal,  x,  y, width, height);
			} else { 
//				spriteBatch.draw(pressed,  x,  y, pressed.getWidth() * game.menuRatio, pressed.getHeight() * game.menuRatio);
				spriteBatch.draw(pressed,  x,  y, width, height);
				if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT))
					onClick(check);
			}
		} else {
//			spriteBatch.draw(normal,  x,  y, normal.getWidth() * game.menuRatio, normal.getHeight() * game.menuRatio);
			spriteBatch.draw(normal,  x,  y, width, height);
		}
		
		return this.buttonClicked;
	}
	
	public void onClick(boolean check) {
		if(!check) {
			return;
		}

		if(text == "Zen") {
            game.gameType = GameType.ZEN;
            game.prefs.remove("tutorialFinished");
            if(game.prefs.contains("tutorialFinished")) {
                game.setScreen(puzzleScreen);
            } else {
                tutorialScreen = new TutorialScreen(game);
                game.setScreen(tutorialScreen);
            }
		} else if(text == "Action") {
			game.gameType = GameType.ACTION;
            game.prefs.remove("tutorialFinished");
            if(game.prefs.contains("tutorialFinished")) {
                game.setScreen(actionScreen);
            } else {
                tutorialScreen = new TutorialScreen(game);
                game.setScreen(tutorialScreen);
            }
		} else if(text == "Exit") {
			if(game.gameScore > game.prefs.getInteger("HighScore"))
				game.prefs.putInteger("HighScore", game.gameScore);
			
			game.prefs.putInteger("Level"+game.currentLevel+"PrevScore", game.prefs.getInteger("Level"+game.currentLevel+"Score"));
			game.prefs.putInteger("Level"+game.currentLevel+"Score", game.gameScore);
			recordScore();
			if(game.gameScore > game.prefs.getInteger("Level"+game.currentLevel+"HighScore"))
				game.prefs.putInteger("Level"+game.currentLevel+"HighScore", game.gameScore);
			game.gameScore = 0;
			game.matchesNumber = 0;
			game.paused = false;
			game.setScreen(new MainMenuScreen(game));
		} else if(text == "Retry") {
			if(game.gameScore > game.prefs.getInteger("HighScore"))
				game.prefs.putInteger("HighScore", game.gameScore);
			
			game.prefs.putInteger("Level"+game.currentLevel+"PrevScore", game.prefs.getInteger("Level"+game.currentLevel+"Score"));
			game.prefs.putInteger("Level"+game.currentLevel+"Score", game.gameScore);
			
			if(game.gameScore > game.prefs.getInteger("Level"+game.currentLevel+"HighScore"))
				game.prefs.putInteger("Level"+game.currentLevel+"HighScore", game.gameScore);
			recordScore();
			game.gameScore = 0;
			game.matchesNumber = 0;
			game.paused = false;
			game.gameScore = 0;
			if(game.gameType == GameType.ACTION) {
				game.setScreen(new ActionScreen(game));
			} else {
				game.setScreen(new PuzzleScreen(game));
			}
		} else if(text == "Continue") {
			game.paused = false;
			game.levelEnd = false;
			recordScore();
		} else if(text == "Leaderboards") {
			if(game.actionResolver.getSignedInGPGS())
                game.paused = true;
			else {
				game.actionResolver.loginGPGS();
			}
		} else if(text == "Rate") {
			game.actionResolver.rateLink();
		} else if(text == "Share") {
			game.actionResolver.shareLink("https://play.google.com/store/apps/details?id=com.denkovski.mihail.les.quatres.android");
		} else if(text == "MoreGames") {
			game.revMobResolver.openLinkAd(true);
		} else if(text == "AchievementsB") {
			game.actionResolver.getAchievementsGPGS();
		} else if(text == "LeaderboardB") {
			game.actionResolver.getLeaderboardGPGS();
		}
		bpressed = false;
		buttonClicked = false;
	}
	
	private void recordScore() {
		game.actionResolver.submitScoreGPGS(game.gameScore);
	}
	
	public void updateCoordinates() {
		bounds.x += this.x;
		bounds.y += this.y;
	}
}
