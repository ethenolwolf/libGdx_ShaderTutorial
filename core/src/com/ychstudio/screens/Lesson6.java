package com.ychstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.ychstudio.Tutorial;

import utils.FileUtils;

public class Lesson6 implements Screen {

    private final Tutorial tutorial;
    
    private final SpriteBatch batch;
    
    private LessonStage lessonStage;
    
    private Texture texture0;
    private Texture normalMap;
    
    private ShaderProgram shader;
    
    private final String VERT_SHADER = "basic.vert";
    private final String FRAG_SHADER = "lesson6.frag";
    
    public Lesson6(Tutorial tutorial) {
        this.tutorial = tutorial;
        batch = tutorial.getSpriteBatch();
    }

    @Override
    public void show() {
        texture0 = new Texture("suzanne.png");
        normalMap = new Texture("suzanne_n.png");
        
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
        shader.setUniformi("u_normal", 1);
        shader.end();
        
        normalMap.bind(1);
        
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        
        lessonStage = new LessonStage(tutorial);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
     
        
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
        normalMap.dispose();
        shader.dispose();
        lessonStage.dispose();
    }

}
