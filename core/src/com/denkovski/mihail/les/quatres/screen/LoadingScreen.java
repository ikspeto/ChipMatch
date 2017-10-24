package com.denkovski.mihail.les.quatres.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.denkovski.mihail.les.quatres.ChipMatch;

/**
 * Created by Mihail Denkovski on 3.10.14.
 */
public class LoadingScreen implements Screen {

    private static ChipMatch game;

    private OrthographicCamera cam;
    private SpriteBatch spriteBatch;
    private int tileCount;

    private Sprite progressbar;
    private float deg = 0, ratio;
    private Texture yellow_circle, background_pattern, banner, loading, main_menu_wood_frame;
    private float animTime, totalAnimTime;

    private boolean started = false;
    private String density;

    public LoadingScreen(ChipMatch game) {
        this.game = game;
        game.paused = false;
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch = new SpriteBatch();

//        tileCount = (int)(Gdx.graphics.getHeight()/game.assetsLoader.background_pattern.getHeight())+1;

        if(game.screenSize == ChipMatch.ScreenSize.mdpi) {
            density = "hdpi";
        }
        if(game.screenSize == ChipMatch.ScreenSize.hdpi) {
            density = "hdpi";
        } else if(game.screenSize == ChipMatch.ScreenSize.xhdpi) {
            density = "xhdpi";
        } else if(game.screenSize == ChipMatch.ScreenSize.xxhdpi) {
            density = "xxhdpi";
        }

        totalAnimTime = 2.0f;
        animTime = 0.0f;

        yellow_circle = new Texture(Gdx.files.internal("loadingscreen/"+density+"/loading_circle.png"));
        yellow_circle.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        background_pattern = new Texture(Gdx.files.internal("green_felt_pattern.png"));
        background_pattern.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        banner = new Texture(Gdx.files.internal(density+"/textures/chip_match_heading.png"));
        banner.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        loading = new Texture(Gdx.files.internal("loadingscreen/"+density+"/loading.png"));
        loading.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        progressbar = new Sprite(yellow_circle);
        if(game.screenRatio == ChipMatch.ScreenRatio.r4t3) {
            progressbar.setPosition((Gdx.graphics.getWidth() - yellow_circle.getWidth())/2,
                    yellow_circle.getWidth()/4);
        } else {
            progressbar.setPosition((Gdx.graphics.getWidth() - yellow_circle.getWidth())/2,
                    yellow_circle.getWidth()/2);
        }



        if(game.screenSize == ChipMatch.ScreenSize.mdpi) {
            main_menu_wood_frame = new Texture(Gdx.files.internal(("mdpi/textures/main_menu/4to3/wood_frame.png")));
        } else if(game.screenSize == ChipMatch.ScreenSize.hdpi) {
            if(game.screenRatio == ChipMatch.ScreenRatio.r16t9)
                main_menu_wood_frame = new Texture(Gdx.files.internal(("loadingscreen/frame/hdpi/16to9/wood_frame.png")));
            else if(game.screenRatio == ChipMatch.ScreenRatio.r4t3)
                main_menu_wood_frame = new Texture(Gdx.files.internal(("loadingscreen/frame/hdpi/4to3/wood_frame.png")));
        } else if(game.screenSize == ChipMatch.ScreenSize.xhdpi) {
            if(game.screenRatio == ChipMatch.ScreenRatio.r16t9)
                main_menu_wood_frame = new Texture(Gdx.files.internal(("loadingscreen/frame/xhdpi/16to9/wood_frame.png")));
            else if(game.screenRatio == ChipMatch.ScreenRatio.r4t3)
                main_menu_wood_frame = new Texture(Gdx.files.internal(("loadingscreen/frame/xhdpi/4to3/wood_frame.png")));
        } else if(game.screenSize == ChipMatch.ScreenSize.xxhdpi) {
            main_menu_wood_frame = new Texture(Gdx.files.internal(("loadingscreen/frame/xxhdpi/16t9/wood_frame.png")));
        }

        ratio = (float)(Gdx.graphics.getWidth()*0.9f) / (float)(banner.getWidth());

        progressbar.setScale(ratio);

        tileCount = (int)(Gdx.graphics.getHeight()/background_pattern.getHeight())+1;
    }


    @Override
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(0.0f, 0.0f, 0.0f, 1);

        spriteBatch.begin();
        if(!started) {
            game.assetsLoader.loadAssets();
            started = true;
        }

        spriteBatch.draw(background_pattern, 0, 0,
                background_pattern.getWidth() * tileCount,
                background_pattern.getHeight() * tileCount,
                0, tileCount,
                tileCount, 0);

        spriteBatch.draw(main_menu_wood_frame, 0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//        spriteBatch.draw(banner,
//                (Gdx.graphics.getWidth()*0.05f),
//                Gdx.graphics.getHeight()*0.9f-((Gdx.graphics.getWidth()*0.9f)/banner.getWidth()*ratio)*banner.getHeight()*ratio,
//                Gdx.graphics.getWidth()*0.9f,
//                ((Gdx.graphics.getWidth()*0.9f)/banner.getWidth())*banner.getHeight());

        spriteBatch.draw(banner,
                (Gdx.graphics.getWidth() - banner.getWidth()*ratio)/2,
                Gdx.graphics.getHeight()*0.9f - banner.getHeight()*ratio,
                banner.getWidth()*ratio,
                banner.getHeight()*ratio);



//        spriteBatch.draw(game.assetsLoader.background_pattern, 0, 0,
//                game.assetsLoader.background_pattern.getWidth() * tileCount,
//                game.assetsLoader.background_pattern.getHeight() * tileCount,
//                0, tileCount,
//                tileCount, 0);

//        spriteBatch.draw(game.assetsLoader.main_menu_wood_frame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//
//        spriteBatch.draw(game.assetsLoader.main_menu_heading,
//                (Gdx.graphics.getWidth()-game.assetsLoader.main_menu_heading.getWidth()*game.menuRatio)/2,
//                Gdx.graphics.getHeight()- game.menu_heading_padding_top - game.assetsLoader.main_menu_heading.getHeight()*game.menuRatio,
//                game.assetsLoader.main_menu_heading.getWidth()*game.menuRatio, game.assetsLoader.main_menu_heading.getHeight()*game.menuRatio);

        if(!game.assetsLoader.assetManager.update()) {
            progressbar.setRotation(deg);
            deg += 60.0f * delta;
            if(deg > 360.0f)
                deg = 0.0f;
            progressbar.draw(spriteBatch);
//                spriteBatch.draw(progressbar,
//                        (Gdx.graphics.getWidth() - Gdx.graphics.getWidth()*0.7f)/2,
//                        (Gdx.graphics.getHeight() - Gdx.graphics.getWidth()*0.7f)/2,
//                        Gdx.graphics.getWidth()*0.7f,
//                        Gdx.graphics.getWidth()*0.7f);

        } else {
            game.assetsLoader.getAssets();
            game.afterLoadingAssets();
            MainMenuScreen mms = new MainMenuScreen(game);
            game.setScreen(mms);
        }


        spriteBatch.draw(loading,
                        (Gdx.graphics.getWidth() - loading.getWidth()*ratio)/2,
                        progressbar.getY()+(float)yellow_circle.getHeight()*ratio+(((float)yellow_circle.getWidth()*ratio)/4.0f+loading.getHeight()*ratio),
                        loading.getWidth()*ratio,
                        loading.getHeight()*ratio);

        spriteBatch.end();
        cam.update();
    }

    @Override
    public void resize(int width, int height) {
        cam.setToOrtho(false, width, height);
    }

    @Override
    public void show() {
//        game.revMobResolver.showBannerAd(false);
//        game.revMobResolver.loadLink();
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
//        game.revMobResolver.showBannerAd(false);
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
