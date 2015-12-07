package com.ychstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ychstudio.Tutorial;

public class LessonStage extends Stage {
    
    private final Tutorial tutorial;
    
    private Skin skin;
    
    
    public LessonStage(Tutorial tutorial) {
        this.tutorial = tutorial;
        
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        
        setup();
    }
    
    public void setup() {
        TextButton backBtn = new TextButton("Back to menu", skin);
        backBtn.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
//                tutorial.setScreen(new MainMenu(tutorial));
                tutorial.changeToMainMenu();
            }
        });
        
        Table table = new Table();
        table.left().bottom().padBottom(6f).padLeft(6f);
        table.add(backBtn);
        
        addActor(table);
        
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }

}
