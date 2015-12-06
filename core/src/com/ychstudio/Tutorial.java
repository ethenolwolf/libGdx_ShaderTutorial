package com.ychstudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ychstudio.screens.MainMenu;

public class Tutorial extends Game {
	SpriteBatch batch;
	
	private boolean changeToMainMenu = false;

	@Override
	public void create () {
	    batch = new SpriteBatch();
	    
	    setScreen(new MainMenu(this));
	}
	
	public SpriteBatch getSpriteBatch() {
	    return batch;
	}
	
	public void changeToMainMenu() {
	    changeToMainMenu = true;
	}

	@Override
	public void render () {
	    super.render();

	    if (changeToMainMenu) {
            setScreen(new MainMenu(this));
            changeToMainMenu = false;
        }
	}
	
	@Override
	public void dispose() {
	    batch.dispose();
	}
}
