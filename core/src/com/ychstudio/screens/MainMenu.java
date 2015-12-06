package com.ychstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.ychstudio.Tutorial;

public class MainMenu implements Screen {

    public enum Lesson {
        NONE, LESSON_1, LESSON_2, LESSON_3, LESSON_4, LESSON_4B
    }

    private final Tutorial tutorial;
    private final SpriteBatch batch;

    private FitViewport fitViewport;
    private Stage stage;
    private Skin skin;

    private Lesson nextLesson = Lesson.NONE;

    public MainMenu(Tutorial tutorial) {
        this.tutorial = tutorial;
        batch = tutorial.getSpriteBatch();
    }

    @Override
    public void show() {
        fitViewport = new FitViewport(640f, 480f);
        stage = new Stage(fitViewport, batch);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        LabelStyle labelStyle = new LabelStyle(new BitmapFont(), Color.WHITE);
        Label titleLabel = new Label("Shader Tutorial", labelStyle);

        TextButton lesson1Btn = new TextButton("Lesson1", skin);
        lesson1Btn.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextLesson = Lesson.LESSON_1;
            }

        });

        TextButton lesson2Btn = new TextButton("Lesson2", skin);
        lesson2Btn.addListener(new ClickListener() {
            
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextLesson = Lesson.LESSON_2;
            }
        });
        
        TextButton lesson3Btn = new TextButton("Lesson3", skin);
        lesson3Btn.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextLesson = Lesson.LESSON_3;
            }

        });
        
        TextButton lesson4Btn = new TextButton("Lesson4", skin);
        lesson4Btn.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextLesson = Lesson.LESSON_4;
            }
            
        });
        
        TextButton lesson4bBtn = new TextButton("Lesson4B", skin);
        lesson4bBtn.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextLesson = Lesson.LESSON_4B;
            }
            
        });

        Table table = new Table();
        table.setFillParent(true);
        table.center().padTop(20f).top();
        table.add(titleLabel).padBottom(20f);
        table.row();
        table.add(lesson1Btn).padBottom(6f);
        table.row();
        table.add(lesson2Btn).padBottom(6f);
        table.row();
        table.add(lesson3Btn).padBottom(6f);
        table.row();
        table.add(lesson4Btn).padBottom(6f);
        table.row();
        table.add(lesson4bBtn).padBottom(6f);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

        switch (nextLesson) {
            case LESSON_1:
                tutorial.setScreen(new Lesson1(tutorial));
                break;
            case LESSON_2:
                tutorial.setScreen(new Lesson2(tutorial));
                break;
            case LESSON_3:
                tutorial.setScreen(new Lesson3(tutorial));
                break;
            case LESSON_4:
                tutorial.setScreen(new Lesson4(tutorial));
                break;
            case LESSON_4B:
                tutorial.setScreen(new Lesson4B(tutorial));
                break;
            case NONE:
            default:
                break;
        }
    }

    @Override
    public void resize(int width, int height) {
        fitViewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

}
