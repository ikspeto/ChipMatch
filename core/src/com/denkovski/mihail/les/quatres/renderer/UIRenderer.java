package com.denkovski.mihail.les.quatres.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.denkovski.mihail.les.quatres.ChipMatch.ScreenRatio;
import com.denkovski.mihail.les.quatres.controller.BackButton;
import com.denkovski.mihail.les.quatres.screen.MainMenuScreen;

public class UIRenderer {
	
	private ChipMatch game;
	private SpriteBatch batch;
    private BitmapFont font, ghost60, fontHeading;
    private BackButton backButton;
//    private WildCard[] wildCards = new WildCard[5];
//    private boolean allowedSelect;
//    private int selectedCard;
    
//	private Preferences prefs;
    private float gameScore, targetScore, uiRatio;
    private int tileCount;
    
    
    public UIRenderer(ChipMatch game) {
    	this.game = game;
//    	prefs = Gdx.app.getPreferences("GameScore Preferences");
    	
    	batch = new SpriteBatch();    
    	
    	ghost60 = new BitmapFont(Gdx.files.internal("fonts/ghost_60.fnt"));
    	ghost60.setColor(Color.WHITE);
    	if(game.screenRatio == ScreenRatio.r4t3) {
    		ghost60.setScale(1.0f * game.gameRatio);
    	} else {
    		ghost60.setScale(1.0f * game.gameRatio);
    	}
    	ghost60.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//    	fontHeading = new BitmapFont(Gdx.files.internal("fonts/ghost_70.fnt"));
    	font = new BitmapFont(Gdx.files.internal("fonts/arial_40.fnt"));
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.setScale(1*game.gameRatio);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);    
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
     
        
        backButton = new BackButton(batch, game.back_button_padding_left, Gdx.graphics.getHeight() - game.back_button_padding_top - game.assetsLoader.back_button_normal.getRegionHeight()*game.gameRatio, game);
        tileCount = (int)(Gdx.graphics.getHeight()/game.assetsLoader.background_pattern.getHeight())+1;
        
        uiRatio = (Gdx.graphics.getWidth()*0.95f)/game.assetsLoader.puzzle_grid.getRegionHeight();
    }
    
 
    String temp;

    public void render(float delta) {    
        batch.begin();

        drawLoadingBar(delta);
        font.setScale(1*game.gameRatio);
        temp = game.matchesNumber + "/"+game.levelTargets[game.currentLevel];
        font.draw(batch, game.matchesNumber + "/"+game.levelTargets[game.currentLevel], 
        		(Gdx.graphics.getWidth()-game.assetsLoader.progress_bar_slot.getWidth()*game.gameRatio)/2 + game.assetsLoader.progress_bar_slot.getWidth()*game.gameRatio - game.score_padding_right - font.getBounds(temp).width, 
        		Gdx.graphics.getHeight()-game.progress_bar_padding_top - (game.assetsLoader.progress_bar_slot.getHeight()*game.gameRatio-font.getCapHeight())/2);
        font.setScale(2*game.gameRatio);
        
        ghost60.draw(batch, "Level "+(1+game.currentLevel), (Gdx.graphics.getWidth()-ghost60.getBounds("Level "+(1+game.currentLevel)).width)/2, Gdx.graphics.getHeight() - (game.action_bar_height - ghost60.getCapHeight())/2);
        
        batch.end();
    }
    
    private void drawLoadingBar(float delta) {
    	//stateTime += delta;
    	
    	gameScore = game.matchesNumber;
    	targetScore = game.levelTargets[game.currentLevel];
    	

        batch.draw(game.assetsLoader.background_pattern, 0, 0,
    			game.assetsLoader.background_pattern.getWidth() * tileCount, 
    			game.assetsLoader.background_pattern.getHeight() * tileCount, 
    					0, tileCount, 
    					tileCount, 0); 
    	
    	batch.draw(game.assetsLoader.wood_back, 
    			-(game.assetsLoader.wood_back.getWidth()*game.gameRatio-Gdx.graphics.getWidth())/2, 
    			Gdx.graphics.getHeight()-game.assetsLoader.wood_back.getHeight()*game.gameRatio, 
    			game.assetsLoader.wood_back.getWidth()*game.gameRatio, 
    			game.assetsLoader.wood_back.getHeight()*game.gameRatio);
    	
//    	batch.draw(game.assetsLoader.progress_bar_slot, (Gdx.graphics.getWidth()-game.gameRatio * game.assetsLoader.progress_bar_slot.getWidth())/2, 
//    													Gdx.graphics.getHeight()-game.progress_bar_padding_top-game.gameRatio *game.assetsLoader.progress_bar_slot.getHeight(), game.assetsLoader.progress_bar_slot.getWidth()*game.gameRatio, game.assetsLoader.progress_bar_slot.getHeight()*game.gameRatio);
    	batch.draw(game.assetsLoader.progress_bar_slot, 
    			(Gdx.graphics.getWidth()-game.assetsLoader.progress_bar_slot.getWidth()*game.gameRatio)/2,
    			Gdx.graphics.getHeight()-game.assetsLoader.progress_bar_slot.getHeight()*game.gameRatio-game.progress_bar_padding_top, 
    			game.assetsLoader.progress_bar_slot.getWidth()*game.gameRatio, game.assetsLoader.progress_bar_slot.getHeight()*game.gameRatio);
    	

    			
    	batch.draw(game.assetsLoader.progress_bar, 
    			(Gdx.graphics.getWidth()-game.assetsLoader.progress_bar.getWidth()*game.gameRatio)/2 - (1.0f-gameScore/targetScore) * (game.assetsLoader.progress_bar.getWidth()*game.gameRatio),
    			Gdx.graphics.getHeight()-game.assetsLoader.progress_bar.getHeight()*game.gameRatio-game.progress_bar_padding_top, 
    			game.assetsLoader.progress_bar.getWidth()*game.gameRatio, game.assetsLoader.progress_bar.getHeight()*game.gameRatio);
    
    	
    	
  
    	
//    	batch.draw(game.assetsLoader.puzzle_grid,  (Gdx.graphics.getWidth()-game.gameRatio * game.assetsLoader.puzzle_grid.getWidth())/2,  game.grid_padding_bottom, game.gameRatio * game.assetsLoader.puzzle_grid.getWidth(), game.gameRatio * game.assetsLoader.puzzle_grid.getWidth());// Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
    	batch.draw(game.assetsLoader.puzzle_grid,  (Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()*0.95f))/2,  
                game.grid_padding_bottom, 
                Gdx.graphics.getWidth()*0.95f, 
                Gdx.graphics.getWidth()*0.95f);// Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
    	
    	batch.draw(game.assetsLoader.wood_frame_top, -(game.assetsLoader.wood_frame_top.getWidth()*game.gameRatio-Gdx.graphics.getWidth())/2, Gdx.graphics.getHeight()-game.assetsLoader.wood_frame_top.getHeight()*game.gameRatio, 
    			game.assetsLoader.wood_frame_top.getWidth()*game.gameRatio, game.assetsLoader.wood_frame_top.getHeight()*game.gameRatio);
    	
    	batch.draw(game.assetsLoader.wood_frame_bottom, 
    			-(game.assetsLoader.wood_frame_bottom.getWidth()*game.gameRatio-Gdx.graphics.getWidth())/2, 
    			0, 
    			game.assetsLoader.wood_frame_bottom.getWidth()*game.gameRatio, 
    			game.assetsLoader.wood_frame_bottom.getHeight()*game.gameRatio);
    	if(backButton.render())
    		game.setScreen(new MainMenuScreen(game));
    	batch.draw(game.assetsLoader.menu_text, game.back_button_padding_left, Gdx.graphics.getHeight() - game.back_button_padding_top - game.assetsLoader.back_button_normal.getRegionHeight()*game.gameRatio, game.assetsLoader.menu_text.getRegionWidth()*game.gameRatio, game.assetsLoader.menu_text.getRegionHeight()*game.gameRatio);

    }
    
    
    public void resize() {

    }
    
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
    
}
