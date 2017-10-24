package com.denkovski.mihail.les.quatres;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.denkovski.mihail.les.quatres.ChipMatch.ScreenRatio;
import com.denkovski.mihail.les.quatres.ChipMatch.ScreenSize;

public class AssetsLoader {

	public ChipMatch game;
	// AssetManager
	
	public TextureAtlas atlas;
	
	public Texture background_pattern, dark_tint;
	
	// Puzzle Renderer
	public TextureRegion redcircle, yellowcircle, greencircle, orangecircle, jokercircle, puzzle_grid, redcircles, yellowcircles, greencircles, orangecircles, jokercircles, background, bg2;
	
	// Scores
	public Texture[] scores = new Texture[6];

    // Tutorial Menu Renderer
    public Texture tutorial_banner;
	
	
    // Main Menu	
	public TextureRegion main_menu_background, main_menu_action_button_normal, main_menu_action_button_pressed, main_menu_zen_button_normal, main_menu_zen_button_pressed, 
	main_menu_options_button_normal, main_menu_options_button_pressed, share_button_normal, share_button_pressed,
	rate_button_normal, rate_button_pressed, sampleads, leaderboards_button_normal, leaderboards_button_pressed;
	
	// Pause Menu
	public TextureRegion pause_foreground, exit_button_normal, exit_button_pressed, retry_button_normal, retry_button_pressed, game_over_banner, yourscoreis;
	
	// EndLevelMenu
	public TextureRegion yellow_frame, level_finished_banner;
    public TextureRegion continue_button_normal, continue_button_pressed;
	
	// Leaderboards and Achievements
	public TextureRegion leaderboard_banner_button_normal, leaderboard_banner_button_pressed, achievements_banner_button_normal, achievements_banner_button_pressed; 
	
	// UI Renderer
	public TextureRegion woodback, woodframe, progressbar, back_button, menu_text;
	public TextureRegion back_button_pressed, back_button_normal;
	
	
	
	public Texture main_menu_wood_frame, progress_bar_slot, wood_back, wood_frame_bottom, progress_bar, wood_frame_top, pause_menu_wood_frame, main_menu_heading;

    public AssetManager assetManager;

	public AssetsLoader(ChipMatch game) {
		this.game = game;
        assetManager = new AssetManager();
	}

	
	/*
	 * Supported densities and resolutions
	 * 
	 * mdpi {
	 * 		320 x 480   1.5     3:2
	 * 
	 * 
	 * 		480 x 640 4:3 ??
	 *      
	 * }
	 * 
	 * hdpi {
	 * 		480 x 800   1.666   15:9
	 *      600 x 800   4:3 ???
	 *      480 x 854   1.777   16:9
	 *      
	 *      540 x 960   1.777   16:9
	 *      640 x 960 ? 1.5     3:2
	 * }
	 * 
	 * xhdpi {
	 * 		600x1024    1.71
	 *      768x1024    4:3 
	 * 		720x1280    1.777   16:9
	 *      768x1280    1.666   15:9
	 * 		800x1280    1.6	    16:10
	 * }
	 * 
	 * xxhdpi {
	 * 		1080x1920   1.777   16:9
	 * 		1200x1920   1.6	    16:10
	 * }
	 * 
	 * 
	 * xxhdpi {
	 * 		2560x1440   1.777   16:9
	 * 		2560x1600   1.6	    16:10
	 * }
	 * 
	 */
	
	public String density, density2;

	public void loadAssets() {

		// Puzzle Renderer
		if(game.screenSize == ScreenSize.mdpi) {
    		density = "hdpi";
		}
		if(game.screenSize == ScreenSize.hdpi) {
    		density = "hdpi";
    	} else if(game.screenSize == ScreenSize.xhdpi) {
    		density = "xhdpi";
    	} else if(game.screenSize == ScreenSize.xxhdpi) {
    		density = "xxhdpi";
    	}

        TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
        param.minFilter = TextureFilter.Linear;

        assetManager.load(density + "/textures/atlas/" + density + ".atlas", TextureAtlas.class);
		assetManager.load("green_felt_pattern.png", Texture.class, param);

		// Assets NOT relative to aspect ratio 
		if(Gdx.graphics.getWidth() <= 650) { // mdpi
            density2 = "hdpi";
		} else
			if(Gdx.graphics.getWidth() <= 960) { // hdpi
                density2 = "xhdpi";
		} else if(Gdx.graphics.getWidth() <= 1250) { // xhdpi
                density2 = "xxhdpi";
		} else if(Gdx.graphics.getWidth() <= 1650) { // xxhdpi
            density2 = "xxhdpi";
		}

        // Assets relative to aspect ratio
        assetManager.load("relative/"+density2+"/puzzlescreen/wood_frame_top.png", Texture.class, param);
        assetManager.load("relative/"+density2+"/puzzlescreen/wood_frame_bottom.png", Texture.class, param);
        assetManager.load("relative/"+density2+"/puzzlescreen/wood_back.png", Texture.class, param);
        assetManager.load("relative/"+density2+"/puzzlescreen/progress_bar_slot.png", Texture.class, param);
        assetManager.load("relative/"+density2+"/puzzlescreen/progress_bar.png", Texture.class, param);




        assetManager.load("hdpi/textures/scores/+100.png", Texture.class, param);
        assetManager.load("hdpi/textures/scores/+100.png", Texture.class, param);
        assetManager.load("hdpi/textures/scores/+200.png", Texture.class, param);
        assetManager.load("hdpi/textures/scores/+300.png", Texture.class, param);
        assetManager.load("hdpi/textures/scores/+400.png", Texture.class, param);
        assetManager.load("hdpi/textures/scores/+500.png", Texture.class, param);
        assetManager.load("hdpi/textures/scores/+600.png", Texture.class, param);
        assetManager.load(density + "/textures/tutorial_menu_renderer/tutorial_banner.png", Texture.class, param);

        // Main Menu
    	
    	if(game.screenSize == ScreenSize.mdpi) {
    		assetManager.load("mdpi/textures/main_menu/4to3/wood_frame.png", Texture.class, param);
        } else 
        	if(game.screenSize == ScreenSize.hdpi) {
        	if(game.screenRatio == ScreenRatio.r16t9)
                assetManager.load("hdpi/textures/main_menu/16to9/wood_frame.png", Texture.class, param);
    		else if(game.screenRatio == ScreenRatio.r4t3)
                assetManager.load("hdpi/textures/main_menu/4to3/wood_frame.png", Texture.class, param);
        } else if(game.screenSize == ScreenSize.xhdpi) {
        	if(game.screenRatio == ScreenRatio.r16t9)
                assetManager.load("xhdpi/textures/main_menu/16to9/wood_frame.png", Texture.class, param);
    		else if(game.screenRatio == ScreenRatio.r4t3)
                assetManager.load("xhdpi/textures/main_menu/4to3/wood_frame.png", Texture.class, param);
        } else if(game.screenSize == ScreenSize.xxhdpi) {
                assetManager.load("xxhdpi/textures/main_menu/16t9/wood_frame.png", Texture.class, param);
        }

        assetManager.load("dark_tint.png", Texture.class);
        assetManager.load(density+"/textures/wood_frame.png", Texture.class, param);
    }

    public void getAssets()
    {
        atlas = assetManager.get(density + "/textures/atlas/" + density + ".atlas", TextureAtlas.class);

        // Scores
        puzzle_grid = atlas.findRegion("grid");
        redcircle = atlas.findRegion("red_circle");
        yellowcircle = atlas.findRegion("black_circle");
        orangecircle = atlas.findRegion("orange_circle");
        jokercircle = atlas.findRegion("joker_circle");
        greencircle = atlas.findRegion("blue_circle");
        redcircles = atlas.findRegion("red_circle_s");
        yellowcircles = atlas.findRegion("black_circle_s");
        orangecircles = atlas.findRegion("orange_circle_s");
        jokercircles = atlas.findRegion("joker_circle_s");
        greencircles = atlas.findRegion("blue_circle_s");

        menu_text = atlas.findRegion("menu_text");
        back_button_normal = atlas.findRegion("back_button");
        back_button_pressed = atlas.findRegion("back_button_pressed");


        main_menu_heading = new Texture(Gdx.files.internal(density+"/textures/chip_match_heading.png"));
        main_menu_action_button_normal = atlas.findRegion("action_button");
        main_menu_action_button_pressed = atlas.findRegion("action_button_pressed");
        main_menu_zen_button_normal = atlas.findRegion("relax_button");
        main_menu_zen_button_pressed = atlas.findRegion("relax_button_pressed");
        main_menu_options_button_normal = atlas.findRegion("more_games_button");
        main_menu_options_button_pressed = atlas.findRegion("more_games_button_pressed");
        share_button_normal = atlas.findRegion("share_button");
        share_button_pressed = atlas.findRegion("share_button_pressed");
        rate_button_normal = atlas.findRegion("rate_button");
        rate_button_pressed = atlas.findRegion("rate_button_pressed");
        leaderboards_button_normal = atlas.findRegion("leaderboard");
        leaderboards_button_pressed = atlas.findRegion("leaderboard_pressed");

        exit_button_normal = atlas.findRegion("exit_button");
        exit_button_pressed = atlas.findRegion("exit_button_pressed");
        retry_button_normal = atlas.findRegion("retry_button");
        retry_button_pressed = atlas.findRegion("retry_button_pressed");
        game_over_banner = atlas.findRegion("game_over_banner");
        yourscoreis = atlas.findRegion("YourScoreIs");

        dark_tint = assetManager.get("dark_tint.png", Texture.class);
        pause_menu_wood_frame = assetManager.get(density+"/textures/wood_frame.png", Texture.class);

        continue_button_normal = atlas.findRegion("continue_button");
        continue_button_pressed = atlas.findRegion("continue_button_pressed");
        yellow_frame = atlas.findRegion("yellow_frame");
        level_finished_banner = atlas.findRegion("level_finished_banner");

        leaderboard_banner_button_normal = atlas.findRegion("leaderboard_banner_button");
        leaderboard_banner_button_pressed = atlas.findRegion("leaderboard_banner_button_pressed");
        achievements_banner_button_normal = atlas.findRegion("achievements_banner_button");
        achievements_banner_button_pressed = atlas.findRegion("achievements_banner_button_pressed");


        background_pattern = assetManager.get("green_felt_pattern.png", Texture.class);
        background_pattern.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        wood_frame_top = assetManager.get("relative/"+density2+"/puzzlescreen/wood_frame_top.png", Texture.class);
        wood_frame_bottom = assetManager.get("relative/"+density2+"/puzzlescreen/wood_frame_bottom.png", Texture.class);
        wood_back = assetManager.get("relative/"+density2+"/puzzlescreen/wood_back.png", Texture.class);
        progress_bar_slot = assetManager.get("relative/"+density2+"/puzzlescreen/progress_bar_slot.png", Texture.class);
        progress_bar = assetManager.get("relative/"+density2+"/puzzlescreen/progress_bar.png", Texture.class);

        scores[0] = assetManager.get("hdpi/textures/scores/+100.png", Texture.class);
        scores[0] = assetManager.get("hdpi/textures/scores/+200.png", Texture.class);
        scores[0] = assetManager.get("hdpi/textures/scores/+300.png", Texture.class);
        scores[0] = assetManager.get("hdpi/textures/scores/+400.png", Texture.class);
        scores[0] = assetManager.get("hdpi/textures/scores/+500.png", Texture.class);
        scores[0] = assetManager.get("hdpi/textures/scores/+600.png", Texture.class);


        if(game.screenSize == ScreenSize.mdpi) {
            main_menu_wood_frame = assetManager.get("mdpi/textures/main_menu/4to3/wood_frame.png", Texture.class);
        } else if(game.screenSize == ScreenSize.hdpi) {
            if(game.screenRatio == ScreenRatio.r16t9)
                main_menu_wood_frame = assetManager.get("hdpi/textures/main_menu/16to9/wood_frame.png", Texture.class);
            else if(game.screenRatio == ScreenRatio.r4t3)
                main_menu_wood_frame = assetManager.get("hdpi/textures/main_menu/4to3/wood_frame.png", Texture.class);
        } else if(game.screenSize == ScreenSize.xhdpi) {
            if(game.screenRatio == ScreenRatio.r16t9)
                main_menu_wood_frame = assetManager.get("xhdpi/textures/main_menu/16to9/wood_frame.png", Texture.class);
            else if(game.screenRatio == ScreenRatio.r4t3)
                main_menu_wood_frame = assetManager.get("xhdpi/textures/main_menu/4to3/wood_frame.png", Texture.class);
        } else if(game.screenSize == ScreenSize.xxhdpi) {
            main_menu_wood_frame = assetManager.get("xxhdpi/textures/main_menu/16t9/wood_frame.png", Texture.class);
        }
        tutorial_banner = assetManager.get(density + "/textures/tutorial_menu_renderer/tutorial_banner.png", Texture.class);


        redcircle.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        yellowcircle.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        greencircle.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        orangecircle.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        jokercircle.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        puzzle_grid.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        redcircles.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        yellowcircles.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        greencircles.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        orangecircles.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        jokercircles.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        main_menu_action_button_normal.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        main_menu_action_button_pressed.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        main_menu_zen_button_normal.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        main_menu_zen_button_pressed.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        main_menu_options_button_normal.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        main_menu_options_button_pressed.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        share_button_normal.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        share_button_pressed.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        rate_button_normal.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        rate_button_pressed.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        leaderboards_button_normal.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        leaderboards_button_pressed.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        exit_button_normal.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        exit_button_pressed.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        retry_button_normal.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        retry_button_pressed.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        game_over_banner.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        continue_button_normal.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        continue_button_pressed.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        yellow_frame.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        level_finished_banner.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        leaderboard_banner_button_normal.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        leaderboard_banner_button_pressed.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        achievements_banner_button_normal.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        achievements_banner_button_pressed.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        menu_text.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        back_button_pressed.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        back_button_normal.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        tutorial_banner.setFilter(TextureFilter.Linear,TextureFilter.Linear);
    }

}
