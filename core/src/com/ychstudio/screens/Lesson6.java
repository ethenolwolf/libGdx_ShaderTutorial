package com.ychstudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.ychstudio.Tutorial;

import utils.FileUtils;

public class Lesson6 implements Screen {

    private final Tutorial tutorial;
    
    private final SpriteBatch batch;
    
    private LessonStage lessonStage;
    
    private Texture texture0;
    private Texture normalMap;
    
    private ShaderProgram shader;
    
    private static final float DEFAULT_LIGHT_Z = 0.075f;
    private static final float LIGHT_INTENSITY = 1f;
    private static final float AMBIENT_INTENSITY = 0.2f;
    private static final Vector3 LIGHT_COLOR = new Vector3(1f, 0.8f, 0.6f);
    private static final Vector3 AMBIENT_COLOR = new Vector3(0.6f, 0.6f, 1.0f);
    private static final Vector3 FALLOFF = new Vector3(0.4f, 3f, 20f);
    
    private final Vector3 lightPos;
    
    private final String VERT_SHADER = "basic.vert";
    private final String FRAG_SHADER = "lesson6.frag";
    
    public Lesson6(Tutorial tutorial) {
        this.tutorial = tutorial;
        batch = tutorial.getSpriteBatch();
        
        lightPos = new Vector3(0, 0, DEFAULT_LIGHT_Z);
    }

    @Override
    public void show() {
        texture0 = new Texture("rock.png");
        normalMap = new Texture("rock_n.png");
        
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
        shader.setUniformi("u_normal", 1); // GL_TEXTURE1
        shader.setUniformf("u_lightColor", LIGHT_COLOR.x, LIGHT_COLOR.y, LIGHT_COLOR.z, LIGHT_INTENSITY);
        shader.setUniformf("u_ambientColor", AMBIENT_COLOR.x, AMBIENT_COLOR.y, AMBIENT_COLOR.z, AMBIENT_INTENSITY);
        shader.setUniformf("u_fallOff", FALLOFF);
        shader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shader.end();
        
        
      lessonStage = new LessonStage(tutorial);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        float x = Gdx.input.getX() / (float) Gdx.graphics.getWidth();
        float y = Gdx.input.getY() / (float) Gdx.graphics.getHeight();
        lightPos.set(x, 1 - y, DEFAULT_LIGHT_Z);
        
        shader.begin();
        shader.setUniformf("u_lightPos", lightPos);
        shader.end();
     
        batch.setShader(shader);
        batch.begin();
        //bind normal map to texture unit 1
        normalMap.bind(1);
        //bind diffuse color to texture unit 0
        //important that we specify 0 otherwise we'll still be bound to glActiveTexture(GL_TEXTURE1)
        texture0.bind(0);
        batch.draw(texture0, 0, 0, 640f, 480f);
        batch.end();
        batch.setShader(null);
        
        lessonStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        shader.begin();
        shader.setUniformf("u_resolution", width, height);
        shader.end();
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
