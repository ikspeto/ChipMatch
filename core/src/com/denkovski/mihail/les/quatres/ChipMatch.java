package com.denkovski.mihail.les.quatres;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.denkovski.mihail.les.quatres.screen.LoadingScreen;
import com.denkovski.mihail.les.quatres.screen.MainMenuScreen;
import com.denkovski.mihail.les.quatres.screen.TutorialScreen;

public class ChipMatch extends Game {

    public enum GameType { ZEN, ACTION };
    public enum ScreenRatio {r16t9, r4t3};
    public enum ScreenSize {mdpi, hdpi, xhdpi, xxhdpi};

    public ActionResolver actionResolver;
    public RevMobResolver revMobResolver;

    public ScreenRatio screenRatio;
    public ScreenSize screenSize;

    public final int numberOfLevels = 18, numberOfRanks = 10;
    public final int[] levelTargets = {10, 15, 20, 20, 25, 25, 31, 31, 36, 36, 40, 40, 43, 43, 48, 48, 53, 53};
    public final int[] rankTargets = {1000, 2000, 4000, 7000, 10000, 14000, 20000, 30000, 40000, 55000};
    public String achievementId[] = {
            "CgkIgsGs66MVEAIQAA",
            "CgkIgsGs66MVEAIQAQ",
            "CgkIgsGs66MVEAIQAg",
            "CgkIgsGs66MVEAIQAw",
            "CgkIgsGs66MVEAIQBA",
            "CgkIgsGs66MVEAIQBQ",
            "CgkIgsGs66MVEAIQBg",
            "CgkIgsGs66MVEAIQBw",
            "CgkIgsGs66MVEAIQCA",
            "CgkIgsGs66MVEAIQCQ",
            "CgkIgsGs66MVEAIQDQ"};

    public String tutorial_texts[] = {
            "The point of the game is to arrange poker chips into groups of four. \n\n Start by moving the chip on the side into the group to make a square",
            "Consecutive matches will bring you bonus points. \n\n See if you can pass the level, before the board gets full"
    };

    public final String[] rankNames = {"Novice", "Amateur", "Poker Face", "Pro", "Fat Cat", "Millionaire", "Tycoon", "Billionaire", "Bill Fence", "God", ""};
    // The casino ran out of chips, Bought the casino, Flingin' them chips, Millionaire, Billionaire, Bill Gates, God, Tycoon, Mogul, Howard Hughes
    public int currentLevel, matchesNumber, currentRank = 0;
    public int gameScore, targetScore = 1000;

    //    private PuzzleScreen puzzleScreen;
    public GameType gameType;
    private MainMenuScreen mainMenuScreen;

    public AssetsLoader assetsLoader;
    public Resizer resizer;

    public Preferences prefs;
    public boolean paused = false, levelEnd = false, showAds = false;// leaderboardsOpen = false;
    public float pauseRatio, gameRatio, menuRatio, gridRatio, widthCoefs, fontSize;


    // UIRenderer
    public float progress_bar_padding_horizontal, progress_bar_padding_top, back_button_padding_left, back_button_padding_top, undo_button_padding_right, undo_button_padding_top, score_padding_right, score_padding_top;
    //15                             112						18						  18					   18						  18                       18                   136
    public float action_bar_height;

    // PuzzleRenderer
    public float pointDist, tileSize, grid_padding_bottom;
    //   74         ?

    // MainMenuRenderer
    public float menu_button_padding_top = 7, menu_heading_padding_top, menu_heading_padding_bottom, share_button_padding_bottom, share_button_padding_left,
            rate_button_padding_right, rate_button_padding_bottom, leaderboards_button_padding_bottom, leaderboards_button_padding_left;
    public float frame_size, heading_space_height;
    public float action_button_padding_top, relax_button_padding_top, more_games_button_padding_top;

    // PauseMenuRenderer
    public float game_over_banner_padding_bottom, exit_button_padding_top;

    // EndLevelRenderer
    public float frame_padding, continue_button_padding_top, frame_row_height, frame_separator_height, text_padding_left;


    /*
     * prefs = {
     * 			 "HighScore" = -1        -> All time high score
     *
     * 			 "Level1Score" = -1;     -> This is the current/last score for Level 1
     * 			 "Level2Score" = -1;
     * 			  ...
     * 			 "Level1HighScore" = -1; -> This is the all time high score for Level1
     * 			 "Level2HighScore" = -1;
     * 			  ...
     * 			 "Level1PrevScore" = -1 -> This is the previous score (before current or last) for Level1
     * 			 "Level2PrevScore" = -1 ->;
     * 			  ...
     * }
     *
     */

    /*
     * mdpi < qHD
     * hdpi ~ qHD
     * xhdpi ~ 720p
     * xxhdpi ~ 1080p
     * xxxhdpi ~ QHD
     */



    public ChipMatch(ActionResolver actionResolver, RevMobResolver revMobResolver) {
        this.actionResolver = actionResolver;
        this.revMobResolver = revMobResolver;
    }


    @Override
    public void create() {

//	   actionResolver.loginGPGS();


        if(Gdx.graphics.getHeight() <= 480) {
            screenSize = ScreenSize.mdpi;
        } else if(Gdx.graphics.getHeight() <= 960) {
            screenSize = ScreenSize.hdpi;
        } else if(Gdx.graphics.getHeight() <= 1920) {
            screenSize = ScreenSize.xhdpi;
        } else {
            screenSize = ScreenSize.xxhdpi;
        }


        float height = Gdx.graphics.getHeight(), width = Gdx.graphics.getWidth();
        float r1 = height/width;
        r1 = (float) (Math.round(r1*100.0f)/100.0f);
        screenRatio = ScreenRatio.r16t9;
        if(r1 == (Math.round((16.0f/9.0f)*100.0f)/100.0f)) // 16:9
            screenRatio = ScreenRatio.r16t9;
        else if(r1 == (Math.round((4.0f/3.0f)*100.0f)/100.0f)) // 4:3
            screenRatio = ScreenRatio.r4t3;
        else if(r1 == (Math.round((5.0f/3.0f)*100.0f)/100.0f)) // 5:3
            screenRatio = ScreenRatio.r16t9;
        else if(r1 == (Math.round((15.0f/9.0f)*100.0f)/100.0f)) // 15:9
            screenRatio = ScreenRatio.r16t9;
        else if(r1 == (Math.round((16.0f/9.375f)*100.0f)/100.0f)) // 16:9.375
            screenRatio = ScreenRatio.r16t9;
        else if(r1 == (Math.round((1778.0f/1080.0f)*100.0f)/100.0f)) // 16:9 with navigation bar
            screenRatio = ScreenRatio.r16t9;
        else if(r1 == (Math.round((480.0f/320.0f)*100.0f)/100.0f)) // 1.5   not good at all
            screenRatio = ScreenRatio.r4t3;

        prefs = Gdx.app.getPreferences("gameScreen-preferences");

        if(!prefs.contains("HighScore")) {
            prefs.putInteger("HighScore", -1);
        }

        for(int i=0;i<numberOfLevels;i++) {
            if(!prefs.contains("Level"+Integer.toString(i)+"Score")) {
                prefs.putInteger("Level"+Integer.toString(i)+"Score", -1);
            }
            if(!prefs.contains("Level"+Integer.toString(i)+"HighScore")) {
                prefs.putInteger("Level"+Integer.toString(i)+"HighScore", -1);
            }
            if(!prefs.contains("Level"+Integer.toString(i)+"PrevScore")) {
                prefs.putInteger("Level"+Integer.toString(i)+"PrevScore", -1);
            }
        }

        gameScore = 0;

        if(!prefs.contains("highScore"))
            prefs.putInteger("highScore", 0);

        assetsLoader = new AssetsLoader(this);
//        assetsLoader.loadAssets();

        LoadingScreen loadingScreen = new LoadingScreen(this);

        //mainMenuScreen = new MainMenuScreen(this);
        setScreen(loadingScreen);
    }

    public void afterLoadingAssets() {

        gameRatio = (((float)(Gdx.graphics.getHeight())) * 0.2f)/assetsLoader.wood_frame_top.getHeight();
        menuRatio = (((float)(Gdx.graphics.getHeight())) / assetsLoader.main_menu_wood_frame.getHeight());
        gridRatio = (Gdx.graphics.getWidth() * 0.95f) / assetsLoader.puzzle_grid.getRegionWidth();
        pauseRatio = (Gdx.graphics.getWidth() * 0.95f) / assetsLoader.pause_menu_wood_frame.getWidth();


        resizer = new Resizer(this);
        resizer.resize();
    }
}