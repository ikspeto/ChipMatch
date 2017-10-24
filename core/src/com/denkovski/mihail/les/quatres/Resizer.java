package com.denkovski.mihail.les.quatres;

import com.badlogic.gdx.Gdx;
import com.denkovski.mihail.les.quatres.ChipMatch.ScreenRatio;
import com.denkovski.mihail.les.quatres.ChipMatch.ScreenSize;

public class Resizer {

    ChipMatch game;
	
	public Resizer(ChipMatch game) {
		this.game = game;
	}
	
	public void resize() {
		// Main Menu
		
			if(game.screenSize == ScreenSize.mdpi) {
//				game.frame_size = 5.0f * game.menuRatio;
//				game.heading_space_height = 125.0f * game.menuRatio;
//				if(game.screenRatio == ScreenRatio.r16t9) {
//					
//				} else if(game.screenRatio == ScreenRatio.r4t3) {
//					game.heading_space_height = 125;
//				}
				game.frame_size = 10.0f * game.menuRatio;
				
				if(game.screenRatio == ScreenRatio.r16t9) {
					game.heading_space_height = 210.0f * game.menuRatio;
				} else if(game.screenRatio == ScreenRatio.r4t3) {
					game.heading_space_height = 228.0f * game.menuRatio;
				}
			} else if(game.screenSize == ScreenSize.hdpi) {
				
				game.frame_size = 8.0f * game.menuRatio;
				
				if(game.screenRatio == ScreenRatio.r16t9) {
					game.heading_space_height = 210.0f * game.menuRatio;
				} else if(game.screenRatio == ScreenRatio.r4t3) {
					game.heading_space_height = 195.0f * game.menuRatio;
				}
			} else if(game.screenSize == ScreenSize.xhdpi) {
				game.frame_size = 16.0f * game.menuRatio;
				if(game.screenRatio == ScreenRatio.r16t9) {
					game.heading_space_height = 420.0f * game.menuRatio;
				} else if(game.screenRatio == ScreenRatio.r4t3) {
					game.heading_space_height = 420.0f * game.menuRatio;
				}
			} else if(game.screenSize == ScreenSize.xxhdpi) {
				game.frame_size = 24.0f * game.menuRatio;
				if(game.screenRatio == ScreenRatio.r16t9) {
					game.heading_space_height = 615.0f * game.menuRatio;
				} else if(game.screenRatio == ScreenRatio.r4t3) {
					
				}
			}
		
		if(game.screenSize == ScreenSize.mdpi) {
			game.pointDist = 71 * game.gridRatio;
			game.tileSize = 69 * game.gridRatio;
		} else if(game.screenSize == ScreenSize.hdpi) {
			game.pointDist = 71 * game.gridRatio;
			game.tileSize = 69 * game.gridRatio;
		} else if(game.screenSize == ScreenSize.xhdpi) {
			game.pointDist = 97 * game.gridRatio;
			game.tileSize = 95 * game.gridRatio;
		} else if(game.screenSize == ScreenSize.xxhdpi) {
			game.pointDist = 146 * game.gridRatio;
			game.tileSize = 144 * game.gridRatio;
		}
		if(game.screenSize == ScreenSize.mdpi) {
			if(game.screenRatio == ScreenRatio.r4t3) {
				
			} else {

			}
		} else if(game.screenSize == ScreenSize.hdpi) {
			if(game.screenRatio == ScreenRatio.r4t3) {
				
			} else {
				
				
			}
		} else if(game.screenSize == ScreenSize.xhdpi) {
			if(game.screenRatio == ScreenRatio.r4t3) {
				
			} else {
	
			}
		} else if(game.screenSize == ScreenSize.xxhdpi) {
			if(game.screenRatio == ScreenRatio.r4t3) {
				
			} else {
		
			}
		}
		if(game.screenRatio == ScreenRatio.r16t9) {
			// Main Menu
			game.menu_heading_padding_top = game.frame_size+(game.heading_space_height - game.assetsLoader.main_menu_heading.getHeight()*game.menuRatio)/2 ;
			game.menu_heading_padding_bottom = game.frame_size+(game.heading_space_height - game.assetsLoader.main_menu_heading.getHeight()*game.menuRatio);
			game.menu_button_padding_top = 0 * game.menuRatio;
			game.share_button_padding_bottom = game.frame_size * 2;
			game.rate_button_padding_bottom = game.frame_size * 2;
			game.action_button_padding_top = Gdx.graphics.getHeight() - game.menu_heading_padding_top - game.assetsLoader.main_menu_heading.getWidth()*game.menuRatio - game.menu_heading_padding_bottom - game.assetsLoader.main_menu_action_button_normal.getRegionHeight()*game.menuRatio;
			game.relax_button_padding_top = (game.action_button_padding_top - game.assetsLoader.main_menu_zen_button_normal.getRegionHeight()*game.menuRatio) + game.menu_button_padding_top;
			game.more_games_button_padding_top = (game.relax_button_padding_top - game.assetsLoader.main_menu_options_button_normal.getRegionHeight()*game.menuRatio) + game.menu_button_padding_top;
			float social_buttons_padding = (Gdx.graphics.getWidth() - game.assetsLoader.share_button_normal.getRegionWidth() * 2 * game.menuRatio - game.frame_size * 2)/4;
			game.share_button_padding_left = game.frame_size + social_buttons_padding;
			game.rate_button_padding_right = game.frame_size + social_buttons_padding;
			game.leaderboards_button_padding_bottom = (game.share_button_padding_bottom + game.assetsLoader.share_button_normal.getRegionHeight()*game.menuRatio) + game.frame_size * 2;
			game.leaderboards_button_padding_left = (Gdx.graphics.getWidth()-game.assetsLoader.leaderboards_button_normal.getRegionWidth()*game.menuRatio)/2;
			
			// PuzzleRenderer
			game.progress_bar_padding_horizontal = 15 * game.gameRatio;
			game.progress_bar_padding_top = 112 * game.gameRatio;
			game.back_button_padding_left = 18 * game.gameRatio;
			game.back_button_padding_top = 18.0f * game.gameRatio;
			game.undo_button_padding_right = 18 * game.gameRatio;
			game.undo_button_padding_top = 18 * game.gameRatio;
			game.score_padding_right = 18 * game.gameRatio;
			game.score_padding_top = 112 * game.gameRatio;
//			game.grid_padding_bottom = (Gdx.graphics.getHeight()-(Gdx.graphics.getWidth()*0.95f))/2;
			game.grid_padding_bottom = game.assetsLoader.wood_frame_bottom.getHeight() * game.gameRatio
	                   + (Gdx.graphics.getHeight() - game.assetsLoader.wood_frame_top.getHeight()*game.gameRatio 
	                   - game.assetsLoader.wood_frame_bottom.getHeight() * game.gameRatio 
	                   - Gdx.graphics.getWidth()*0.95f)/2;
						
		} else {
			// Main Menu
			game.menu_heading_padding_top = game.frame_size+(game.heading_space_height - game.assetsLoader.main_menu_heading.getHeight()*game.menuRatio)/2 ;
			game.menu_heading_padding_bottom = game.frame_size+(game.heading_space_height - game.assetsLoader.main_menu_heading.getHeight()*game.menuRatio);
			if(game.screenSize == ScreenSize.hdpi) {
				game.menu_button_padding_top = 12 * game.menuRatio;
			} else if(game.screenSize == ScreenSize.xhdpi) {
				game.menu_button_padding_top = 40 * game.menuRatio;
			} else if(game.screenSize == ScreenSize.xxhdpi) {
				game.menu_button_padding_top = 35 * game.menuRatio;
			}
			game.share_button_padding_bottom = game.frame_size * 2;
			game.rate_button_padding_bottom = game.frame_size * 2;
			game.action_button_padding_top = Gdx.graphics.getHeight() - game.menu_heading_padding_top - game.assetsLoader.main_menu_heading.getWidth()*game.menuRatio - game.menu_heading_padding_bottom - game.assetsLoader.main_menu_action_button_normal.getRegionHeight()*game.menuRatio;
			game.relax_button_padding_top = (game.action_button_padding_top - game.assetsLoader.main_menu_zen_button_normal.getRegionHeight()*game.menuRatio) + game.menu_button_padding_top;
			game.more_games_button_padding_top = (game.relax_button_padding_top - game.assetsLoader.main_menu_options_button_normal.getRegionHeight()*game.menuRatio) + game.menu_button_padding_top;
			float social_buttons_padding = (Gdx.graphics.getWidth() - game.assetsLoader.share_button_normal.getRegionWidth() * 2 * game.menuRatio - game.frame_size * 2 - game.assetsLoader.leaderboards_button_normal.getRegionWidth() * game.menuRatio)/4;
			game.share_button_padding_left = game.frame_size + social_buttons_padding;
			game.rate_button_padding_right = game.frame_size + social_buttons_padding;
			
			if(game.screenSize == ScreenSize.xhdpi) {
				game.leaderboards_button_padding_bottom = game.frame_size * 2 - 11 * game.menuRatio;
				game.leaderboards_button_padding_left = (Gdx.graphics.getWidth() - game.assetsLoader.leaderboards_button_normal.getRegionWidth() * game.menuRatio)/2;
			} else {
				game.leaderboards_button_padding_bottom = game.frame_size * 2;
				game.leaderboards_button_padding_left = (Gdx.graphics.getWidth() - game.assetsLoader.leaderboards_button_normal.getRegionWidth() * game.menuRatio)/2;
			}
			
			
			// PuzzleRenderer
			game.progress_bar_padding_horizontal = 15 * game.gameRatio;
			game.progress_bar_padding_top = 132 * game.gameRatio;
			game.back_button_padding_left = 18 * game.gameRatio;
			game.back_button_padding_top = 18 * game.gameRatio;
			game.undo_button_padding_right = 18 * game.gameRatio;
			game.undo_button_padding_top = 18 * game.gameRatio;
			game.score_padding_right = 18 * game.gameRatio;
			game.score_padding_top = 132 * game.gameRatio;
//			game.grid_padding_bottom = (Gdx.graphics.getHeight() - 432 * game.gameRatio - game.assetsLoader.puzzle_grid.getHeight()*game.gameRatio)/2 + 187*game.gameRatio;
			game.grid_padding_bottom = game.assetsLoader.wood_frame_bottom.getHeight() * game.gameRatio
					                   + (Gdx.graphics.getHeight() - game.assetsLoader.wood_frame_top.getHeight()*game.gameRatio 
					                   - game.assetsLoader.wood_frame_bottom.getHeight() * game.gameRatio 
					                   - Gdx.graphics.getWidth()*0.95f)/2;
		}

		// PauseMenuRenderer
		game.game_over_banner_padding_bottom = 17 * game.pauseRatio;
		game.exit_button_padding_top = 40 * game.pauseRatio;

		
		
		if(game.screenSize == ScreenSize.mdpi) {
			game.frame_padding = 21.0f * game.pauseRatio;
			game.continue_button_padding_top = 13.0f * game.pauseRatio;
			game.frame_row_height = 54 * game.pauseRatio;
			game.frame_separator_height = 3 * game.pauseRatio; 
			game.text_padding_left = 7 * game.pauseRatio;
			game.fontSize = 1;
		} else if(game.screenSize == ScreenSize.hdpi) {
			game.frame_padding = 21.0f * game.pauseRatio;
			game.continue_button_padding_top = 13.0f * game.pauseRatio;
			game.frame_row_height = 50 * game.pauseRatio;
			game.frame_separator_height = 3 * game.pauseRatio; 
			game.text_padding_left = 7 * game.pauseRatio;
			game.fontSize = 1;
		} else if(game.screenSize == ScreenSize.xhdpi) {
			game.frame_padding = 21.0f * game.pauseRatio;
			game.continue_button_padding_top = 13.0f * game.pauseRatio;
			game.frame_row_height = 94 * game.pauseRatio;
			game.frame_separator_height = 10 * game.pauseRatio; 
			game.text_padding_left = 11 * game.pauseRatio;
			game.fontSize = 1.8f;
		} else if(game.screenSize == ScreenSize.xxhdpi) {
			game.frame_padding = 21.0f * game.pauseRatio;
			game.continue_button_padding_top = 13.0f * game.pauseRatio;
			game.frame_row_height = 94 * game.pauseRatio;
			game.frame_separator_height = 10 * game.pauseRatio; 
			game.text_padding_left = 11 * game.pauseRatio;
			game.fontSize = 1.8f;
		}
		if(Gdx.graphics.getWidth() <= 650) { // hdpi
			game.progress_bar_padding_top = 109 * game.gameRatio;// * game.gameRatio;
			game.action_bar_height = 88 * game.gameRatio;
		} else if(Gdx.graphics.getWidth() <= 960) { //xhdpi
			game.progress_bar_padding_top = 132 * game.gameRatio;
			game.action_bar_height = 112 * game.gameRatio;
		} else if(Gdx.graphics.getWidth() <= 1080) { //xhdpi
			game.progress_bar_padding_top = 160 * game.gameRatio;
			game.action_bar_height = 128 * game.gameRatio;
		}
		
		
		
		
	}
	
}

