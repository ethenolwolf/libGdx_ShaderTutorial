package com.ychstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.ychstudio.Tutorial;

import utils.FileUtils;

public class Lesson4 implements Screen {
    
    private final Tutorial tutorial;
    private final SpriteBatch batch;
    
    private LessonStage lessonStage;
    
    private Texture texture0;
    private Texture texture1;
    private Texture mask;
    
    private ShaderProgram shader;
    
    private final String VERT_SHADER = "basic.vert";
    private final String FRAG_SHADER = "lesson4.frag";
    
    public Lesson4(Tutorial tutorial) {
        this.tutorial = tutorial;
        batch = tutorial.getSpriteBatch();
    }

    @Override
    public void show() {
        texture0 = new Texture("grass.png");
        texture1 = new Texture("dirt.png");
        mask = new Texture("mask.png");
        
        shader = new ShaderProgram(VERT_SHADER, FRAG_SHADER);
        shader = new ShaderProgram(FileUtils.loadShaderFile(VERT_SHADER), FileUtils.loadShaderFile(FRAG_SHADER));
        
        if (shader.isCompiled() == false) {
            System.err.println("Error compiling shader: " + shader.getLog());
        }
        if (shader.getLog().length() != 0) {
            System.err.println(shader.getLog());
        }
//      ShaderProgram.pedantic = false;


        shader.begin();
        shader.setUniformi("u_texture1", 1);
        shader.setUniformi("u_mask", 2);
        shader.end();

        // bind mask to glActiveTexture(GL_TEXTURE2)
        mask.bind(2);
        
        // bind dirt to glActiveTexture(GL_TEXTURE1)
        texture1.bind(1);
        
        // reset glActiveTexture to GL_TEXTURE0 (SpriteBatch does not do it automatically)
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        
        lessonStage = new LessonStage(tutorial);
        Gdx.input.setInputProcessor(lessonStage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setShader(shader);
        batch.begin();
        
        batch.draw(texture0, 0, 0, 640f, 480f);
        
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
        texture0.dispose();
        texture1.dispose();
        mask.dispose();
        shader.dispose();
        lessonStage.dispose();
    }

}
