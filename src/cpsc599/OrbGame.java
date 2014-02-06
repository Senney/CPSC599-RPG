package cpsc599;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.assets.AssetManager;

import cpsc599.util.Logger;

public class OrbGame implements ApplicationListener {
	AssetManager assetManager;
	
	public OrbGame() {
		Logger.debug("OrbGame class constructed.");
		
		assetManager = new AssetManager();
	}

	@Override
	public void dispose() {
		Logger.debug("OrbGame class disposing.");
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		Logger.debug("OrbGame class create method run.");
	}
	
}
