package com.mobezer.bmc.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Quart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mobezer.bmc.Assets;
import com.mobezer.bmc.Game;
import com.mobezer.bmc.GameWorld;
import com.mobezer.bmc.GlobalSettings;
import com.mobezer.bmc.TextWrapper;
import com.mobezer.bmc.TextureDimensions;
import com.mobezer.bmc.TextureWrapper;
import com.mobezer.bmc.WorldListner;
import com.mobezer.bmc.objects.BoxObjectManager;
import com.mobezer.tween.TextureAccessor;

public class GameScreen extends BaseScreen implements InputProcessor,
		GestureListener {
	// constants
	public static final int GAME_INITIALIZE = 0;
	public static final int GAME_READY = 1;
	public static final int GAME_RUNNING = 2;
	public static final int GAME_PAUSED = 3;
	public static final int GAME_OVER = 4;
	static int state;
	public static int level = 1;
	public static boolean levelUp = false;
	Vector3 touchPoint = new Vector3(0, 0, 0);
	private static Vector2 heroOld = new Vector2(-100, -100);
	// Variables
	// Variables used for ball shooting
	float X1, Y1, X2, Y2;
	float shotPower, pullArmBack, rotNeeded, rotNeeded1;
	Vector2 vectorMade = new Vector2();
	// ////////////////////////////////////////
	private OrthographicCamera cam;
	GameWorld world;
	TextureWrapper backTexture, originTexture, heroOldTextuer, whiteMask;
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	Matrix4 debugMatrix;
	private boolean ballReady = false;
	private static ShapeRenderer shapeRenderer = new ShapeRenderer();
	FPSLogger fps=new FPSLogger();
	public GameScreen(int screenId, OrthographicCamera cam) {
		// Assets.loadGame();
		this.cam = cam;

		this.BackScreenID = screenId;
		batch.setProjectionMatrix(cam.combined);
		IsDone = false;
		state = GAME_INITIALIZE;
		TouchPoint = new Vector3(0, 0, 0);
		TouchHandler = new com.mobezer.bmc.TouchHandler();
		// Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		debugMatrix = new Matrix4(cam.combined.cpy());
		debugMatrix.scale(BoxObjectManager.BOX_TO_WORLD,
				BoxObjectManager.BOX_TO_WORLD, 1f);
		world = new GameWorld(cam);
		levelUp = false;
		Init();
	}

	private void Init() {
		backTexture = new TextureWrapper(Assets.backgroundRegion, new Vector2(
				GlobalSettings.VIRTUAL_WIDTH / 2,
				GlobalSettings.VIRTUAL_HEIGHT / 2));
		backTexture.SetDimension(cam.viewportWidth, cam.viewportHeight);

		originTexture = new TextureWrapper(Assets.origin, new Vector2(
				world.heroOrigin.x, world.heroOrigin.y));
		originTexture.SetDimension(TextureDimensions.ORIGIN_WIDTH,
				TextureDimensions.ORIGIN_HEIGHT);
		heroOldTextuer = new TextureWrapper(Assets.ballTrace, new Vector2(
				heroOld.x, heroOld.y));
		heroOldTextuer.SetDimension(TextureDimensions.BALL_WIDTH,
				TextureDimensions.BALL_WIDTH);
		fadeIn();
		Gdx.app.log(Game.LOG, "Level : " + level);
		state = GAME_RUNNING;
		cam.update();
		InputMultiplexer mux = new InputMultiplexer();
		mux.addProcessor(this);
		mux.addProcessor(new GestureDetector(0, 0, 0, 0.5f, this));
		Gdx.input.setInputProcessor(mux);
		/*if(Gdx.graphics.getWidth()>=1000)
			cam.zoom=1.1f;*/
	}

	@Override
	public void update(float delta) {
		if (delta > 0.1f)
			delta = 0.1f;

		switch (state) {
		case GAME_INITIALIZE:
			updateInit();
			break;
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(delta);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}

	private void updateInit() {
		// TODO Auto-generated method stub

	}

	private void updateGameOver() {
		// TODO Auto-generated method stub

	}

	private void updatePaused() {
		// TODO Auto-generated method stub

	}

	private void updateRunning(float delta) {
		world.update(delta);
		heroOldTextuer.setPosition(heroOld);
		// Check game over // Restart if yes
		if (levelUp) {
			state = GAME_OVER;
			restart();
			return;
		} else if (world.heroBall.getX() > GlobalSettings.VIRTUAL_WIDTH) {
			state = GAME_OVER;
			restart();
			return;
		} else if (world.heroBall.getX() < 0) {
			state = GAME_OVER;
			restart();
			return;
		} else if (world.heroBall.getY() > GlobalSettings.VIRTUAL_HEIGHT) {
			state = GAME_OVER;
			restart();
			return;
		} else if (world.heroBall.getY() < 0) {
			state = GAME_OVER;
			restart();
			return;
		}
	}

	private void fadeIn() {
		whiteMask = new TextureWrapper(Assets.test, new Vector2(
				GlobalSettings.VIRTUAL_WIDTH / 2,
				GlobalSettings.VIRTUAL_HEIGHT / 2));
		whiteMask.SetDimension(cam.viewportWidth, cam.viewportHeight);
		TweenCallback callback = new TweenCallback() {
			@SuppressWarnings("rawtypes")
			@Override
			public void onEvent(int type, BaseTween source) {
				switch (type) {
				case COMPLETE:
					whiteMask = null;
					break;
				}
			}
		};
		Timeline.createSequence()
				.push(Tween.to(whiteMask, TextureAccessor.OPACITY, 0.5f)
						.target(0).ease(Quart.INOUT)).setCallback(callback)
				.setCallbackTriggers(TweenCallback.COMPLETE)
				.start(Game.tweenManager);
	}

	private void exit() {
		BackScreenID = Game.MENUSCREEN;
		IsDone = true;
		level = 1;
		levelUp = false;
	}

	private void restart() {
		whiteMask = new TextureWrapper(Assets.test, new Vector2(
				GlobalSettings.VIRTUAL_WIDTH / 2,
				GlobalSettings.VIRTUAL_HEIGHT / 2));
		whiteMask.SetDimension(cam.viewportWidth, cam.viewportHeight);
		TweenCallback callback = new TweenCallback() {
			@SuppressWarnings("rawtypes")
			@Override
			public void onEvent(int type, BaseTween source) {
				switch (type) {
				case COMPLETE:
					BackScreenID = Game.GAMESCREEN;
					IsDone = true;
					break;
				}
			}
		};
		Timeline.createSequence()
				// .push(Tween.set(whiteMask,
				// TextureAccessor.OPACITY).target(0))
				// .push(Tween.to(whiteMask, TextureAccessor.OPACITY,
				// 0.4f).target(1).ease(Quart.INOUT))
				.setCallback(callback)
				.setCallbackTriggers(TweenCallback.COMPLETE)
				.start(Game.tweenManager);
		if (!levelUp)
			WorldListner.restart();
	}

	public static void LevelUp() {
		if (!levelUp) {
			if (level == 6)
				level = 0;
			Gdx.app.log(Game.LOG, "Level ++");
			level++;
			levelUp = true;
			heroOld.set(-150, -150);
		}
	}

	private void updateReady() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		cam.update();
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.disableBlending();
		backTexture.Draw(batch);
		batch.enableBlending();
		drawLine(X1, Y1, X2, Y2);
		drawBallOrigin();
		world.render(batch);
		 batch.disableBlending();
		// debugRenderer.render(BoxObjectManager.GetWorld(), debugMatrix);
		batch.enableBlending();
		if (whiteMask != null)
			whiteMask.Draw(batch);
		TextWrapper fp = new TextWrapper("fps "+Gdx.graphics.getFramesPerSecond(), Assets.Shemlock, new Vector2(200,80));
		fp.Draw(batch);
		batch.draw(originTexture.region, TouchPoint.x, TouchPoint.y,6,6);
		batch.end();

	}

	private void drawBallOrigin() {
		// Draws the Initial Ball Position
		originTexture.Draw(batch);
		heroOldTextuer.Draw(batch);
	}

	private void drawLine(float X1, float Y1, float X2, float Y2) {

		batch.end();
		shapeRenderer.setProjectionMatrix(GameWorld.camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.GRAY);
		shapeRenderer.line(X1, Y1, X2, Y2);
		shapeRenderer.end();
		batch.begin();

	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return IsDone;
	}

	@Override
	public void dispose() {
		world.dospose();
	}

	@Override
	public void OnPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnResume() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			exit();
			return true;
		}
		if (keycode == Keys.R || keycode == Keys.MENU) {
			restart();
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		UnProjectCamera(x, y, cam, Game.CAMSTARTX, Game.CAMSTARTY,
				Game.CAMWIDTH, Game.CAMHEIGHT);
		x = (int) TouchPoint.x;
		y = (int) TouchPoint.y;
		Gdx.app.log("", "" + TouchPoint);
		if (x < world.heroBall.getX() + TextureDimensions.BALL_WIDTH * 2
				&& x > world.heroBall.getX() - TextureDimensions.BALL_WIDTH * 2
				&& y < world.heroBall.getY() + TextureDimensions.BALL_WIDTH * 2
				&& y > world.heroBall.getY() - TextureDimensions.BALL_WIDTH * 2) {
			if (!world.heroBall.isFlying) {
				X1 = world.heroBall.getX();
				Y1 = world.heroBall.getY();
				X2 = X1;
				Y2 = Y1;
				ballReady = true;
				Gdx.app.log(Game.LOG, "Ball Touched ");
				return true;
			}
		}
		return false;

	};

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// Update the end point and redraw the line
		if (ballReady) {
			// Gdx.app.log(Game.LOG, "Ball Dragged ");
			x = (int) TouchPoint.x;
			y = (int) TouchPoint.y;
			X2 = x;
			Y2 = y;
			double angle;
			float dist_x = X2 - world.circle.x;
			float dist_y = Y2 - world.circle.y;
			double distance = Math.sqrt(dist_x * dist_x + dist_y * dist_y);
			if (distance > 100) {
				angle = Math.atan2(dist_y, dist_x);
				X2 = (float) (world.circle.x + 100 * Math.cos(angle));
				Y2 = (float) (world.circle.y + 100 * Math.sin(angle));
			}
			// Update VectorMade
			vectorMade.set(X2 - X1, Y2 - Y1);
			shotPower = vectorMade.len();
			// Store value to reduce number of calculations
			pullArmBack = shotPower / 50;
			// Normalise vector to yield only directional info
			vectorMade.nor();
			{
				rotNeeded = (float) (245 + Math.toDegrees(Math.atan2(
						vectorMade.y, vectorMade.x)
						- Math.atan2(world.heroBall.getY(),
								world.heroBall.getX())));
				if (shotPower > 10)
					world.heroBall.SetPosition(X2, Y2);
				// Gdx.app.log(Woody.LOG, "Rotation calculated " + rotNeeded);
			}
		}
		return false;
	};

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (shotPower < 10 || !ballReady) {
			ballReady = false;
		} else if (ballReady) {
			Gdx.app.log(Game.LOG, "Fire!! " + shotPower);
			world.heroBall.shoot(shotPower, vectorMade);
			shotPower = 0;
			heroOld.set(X2, Y2);
			X1 = 0;
			X2 = 0;
			Y1 = 0;
			Y2 = 0;
			ballReady = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// cam.zoom +=(distance-initialDistance)*0.005f;
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// cam.zoom=1f;
		return false;
	}

}
