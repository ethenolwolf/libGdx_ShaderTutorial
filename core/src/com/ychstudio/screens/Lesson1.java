package com.ychstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.ychstudio.Tutorial;

import utils.FileUtils;

public class Lesson1 implements Screen {
    
    private final Tutorial tutorial;
    private final SpriteBatch batch;
    
    private LessonStage lessonStage;
    
    private Texture img;
    
    private ShaderProgram shader;
    
    private final String VERT_SHADER = "basic.vert";
    private final String FRAG_SHADER = "lesson1.frag";

    public Lesson1(Tutorial tutorial) {
        this.tutorial = tutorial;
        batch = tutorial.getSpriteBatch();
    }

    @Override
    public void show() {
        img = new Texture("scene.png");
        
        shader = new ShaderProgram(VERT_SHADER, FRAG_SHADER);
        shader = new ShaderProgram(FileUtils.loadShaderFile(VERT_SHADER), FileUtils.loadShaderFile(FRAG_SHADER));
        
        if (shader.isCompiled() == false) {
            System.err.println("Error compiling shader: " + shader.getLog());
        }
//      ShaderProgram.pedantic = false;

        lessonStage = new LessonStage(tutorial);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setShader(shader);
        batch.begin();
        
        batch.draw(img, 0, 0, 640f, 480f);
        
        batch.end();
        batch.setShader(null);
        
        lessonStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        
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
        img.dispose();
        shader.dispose();
        lessonStage.dispose();
    }

}
