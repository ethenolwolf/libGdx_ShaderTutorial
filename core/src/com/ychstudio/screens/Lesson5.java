package com.ychstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.ychstudio.Tutorial;

import utils.FileUtils;

public class Lesson5 implements Screen {

    private final Tutorial tutorial;
    
    private final SpriteBatch batch;
    
    private LessonStage lessonStage;
    
    private Texture texture0;
    
    private FrameBuffer frameBufferA;
    private FrameBuffer frameBufferB;
    private TextureRegion fboRegion;
    
    private ShaderProgram shader;
    
    private final int FBO_SIZE = 1024;
    private final float MAX_BLUR = 4f;
    
    private final String VERT_SHADER = "basic.vert";
    private final String FRAG_SHADER = "lesson5.frag";
    
    public Lesson5(Tutorial tutorial) {
        this.tutorial = tutorial;
        batch = tutorial.getSpriteBatch();
    }

    @Override
    public void show() {
        texture0 = new Texture("suzanne.png");
        
        
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
        shader.setUniformf("u_dir", 0f, 0f);
        shader.setUniformf("u_resolution", FBO_SIZE);
        shader.setUniformf("u_radius", 1f);
        shader.end();
        
        frameBufferA = new FrameBuffer(Format.RGB888, FBO_SIZE, FBO_SIZE, false);
        frameBufferB = new FrameBuffer(Format.RGB888, FBO_SIZE, FBO_SIZE, false);
        
        fboRegion = new TextureRegion();

        lessonStage = new LessonStage(tutorial);
        Gdx.input.setInputProcessor(lessonStage);        
    }

    @Override
    public void render(float delta) {
        
        // render to an off-screen color buffer
        frameBufferA.begin();
        Gdx.gl.glClearColor(0.7f, 0.8f, 0.6f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setShader(null);
        batch.begin();
        batch.draw(texture0, 0, 0, 640f, 480f);
        batch.flush();
        frameBufferA.end();
        
        // start blurring the image
        batch.setShader(shader);
        shader.setUniformf("u_dir", 1f, 0f);
        // update blur amount based on touch input
        float mouseXAmount = Gdx.input.getX() / (float) Gdx.graphics.getWidth();
        shader.setUniformf("u_radius", mouseXAmount * MAX_BLUR);
        
        // first blur pass goes to frameBufferB
        frameBufferB.begin();
        
        // clear is not necessary as frameBufferA is opaque
//        Gdx.gl.glClearColor(0.7f, 0.8f, 0.6f, 1.0f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // render FBO target A to target B
        fboRegion.setRegion(frameBufferA.getColorBufferTexture());
        batch.draw(fboRegion, 0, 0, 640f, 480f);
        batch.flush();
        
        frameBufferB.end();
        
        // update the blur only along Y axis
        shader.setUniformf("u_dir", 0f, 1f);
        
        // update the Y axis blur radius
        float mouseYAmount = Gdx.input.getY() / (float) Gdx.graphics.getHeight();
        shader.setUniformf("u_radius", mouseYAmount * MAX_BLUR);
        
        // draw target B to the screen with the vertical blur effect
        fboRegion.setRegion(frameBufferB.getColorBufferTexture());
        batch.draw(fboRegion, 0, 0, 640f, 480f);
        
        batch.setShader(null);
        batch.end();
        
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
        frameBufferA.dispose();
        frameBufferB.dispose();
        shader.dispose();
        lessonStage.dispose();
    }

}
