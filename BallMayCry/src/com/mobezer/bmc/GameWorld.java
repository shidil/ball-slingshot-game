package com.mobezer.bmc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.dom4j.Element;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Quart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mobezer.bmc.objects.BoxObjectManager;
import com.mobezer.bmc.objects.BoxRectObject;
import com.mobezer.bmc.objects.Disc;
import com.mobezer.bmc.objects.HeroBall;
import com.mobezer.bmc.objects.Target;
import com.mobezer.bmc.screens.GameScreen;
import com.mobezer.tween.TextureAccessor;
import com.mobezer.xml.Visitor;
import com.mobezer.xml.XLoader;

public class GameWorld {
	// World constants
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final int GAME_UNIT = 48;
	int gRadius = TextureDimensions.BALL_WIDTH / 2;
	public static OrthographicCamera camera; // camera to obtain projection
												// matrix
	// Physics
	BoxObjectManager boxManager;
	// public static BodyEditorLoader shapeLoader = new BodyEditorLoader(
	// Gdx.files.internal("shapes/shapes.json"));

	// Others
	public final Random random;
	private int state;

	// Lists
	public static List<Vector2> heroBallPoints;

	// Game Charaters and core objects
	BoxRectObject ground,box;
	public HeroBall heroBall;
	private Target target;
	public Vector2 heroOrigin;
	// a list of points that define path of the heroBall
	float stateTime;

	public Vector2 circle;
	private boolean hitTarget=false;

	public GameWorld(OrthographicCamera cam) {
		GameWorld.camera = cam;
		heroBallPoints = new ArrayList<Vector2>();
		random = new Random();
		createCollisionListener();
		this.state = WORLD_STATE_RUNNING;
		boxManager = new BoxObjectManager();
		createLevel();
	}

	private void createLevel() {
		FileHandle inputHandle = Gdx.files
				.internal("levels/level_"+GameScreen.level+".xml");
		XLoader levelLoader = new XLoader(inputHandle.read());
		levelLoader.registerVisitor(new Visitor() {

			public void visit(Element element) {
				String tag = element.getName();
				if (tag.equals("sprite")) {
					final float rotationAngle;
					float pX = Float.parseFloat(element.attributeValue("x"));
					float pY = Float.parseFloat(element.attributeValue("y"));
					final float angle = Float.parseFloat(element.attributeValue("angle"));
					float width = Float.parseFloat(element.attributeValue("width"));
					float height = Float.parseFloat(element.attributeValue("height"));
					if(element.attributeValue("rotation")!=null)
						rotationAngle = Float.parseFloat(element.attributeValue("rotation"));
					else
						rotationAngle=0;
					if (element.attributeValue("class").equals("box_object")) {
						BoxRectObject box = new BoxRectObject(boxManager.GetNewObjectIndex(),
								1, width, height, BodyType.StaticBody, 1, 0.05f, pX,
								pY, angle, Assets.box){
							@Override
							public void rotate() {
								setRotation(rotationAngle);
							}
						};
						box.SetTextureDimension((int)width, (int)height);
						boxManager.AddObject(box);
					}
					if (element.attributeValue("class").equals("disc_object")) {
						Disc disc = new Disc(
								boxManager.GetNewObjectIndex(), 1, width/2, BodyType.StaticBody, 0,
								0.05f, pX, pY, angle, Assets.disc);
						disc.SetTextureDimension((int)width, (int)width);
						boxManager.AddObject(disc);
					}
					if (element.attributeValue("class").equals("hero_object")) {
						heroOrigin = new Vector2(pX, pY);
						circle = heroOrigin;
						heroBall = new HeroBall(boxManager.GetNewObjectIndex(), 1, gRadius,
								BodyType.KinematicBody, 1, 0.86f, pX, pY,angle, Assets.hero);
						heroBall.SetTextureDimension(TextureDimensions.BALL_WIDTH,
								TextureDimensions.BALL_HEIGHT);
						boxManager.AddObject(heroBall);
					}
					if (element.attributeValue("class").equals("target_object")) {
						target = new Target(boxManager.GetNewObjectIndex(), 1,TextureDimensions.TARGET_WIDTH / 2 - 9,
								BodyType.KinematicBody,1, 0.86f, pX, pY, angle, Assets.ring);
						target.SetTextureDimension(TextureDimensions.TARGET_WIDTH,
								TextureDimensions.TARGET_HEIGHT);
						boxManager.AddObject(target);
					}
				}

			}
		});
	}

	public void update(float delta) {
		if (state == WORLD_STATE_RUNNING) {
			boxManager.Update(delta);
			updateBalls(delta);
		}
	}

	private void updateBalls(float delta) {
		if (heroBall.isFlying) {
			stateTime += delta;
			if (stateTime > 0) {
				stateTime = 0;
				heroBallPoints.add(new Vector2(heroBall.getTexture()
						.getPosition()));
			}
		}
	}

	public static OrthographicCamera getCamera() {
		return camera;
	}

	public void render(SpriteBatch batch) {

		// Draws trace pixel on the heroBall path
		int len = heroBallPoints.size();
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				batch.draw(Assets.pixel, heroBallPoints.get(i).x,
						heroBallPoints.get(i).y,3,3);
			}
		}
		boxManager.Draw(batch);
	}
	private void victory() {
		hitTarget=true;
		target.victory();
		heroBall.victory();
		WorldListner.victory();
	}
	private void createCollisionListener() {
		BoxObjectManager.GetWorld().setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
			}

			@Override
			public void endContact(Contact contact) {
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				Fixture fixtureA = contact.getFixtureA();
				Fixture fixtureB = contact.getFixtureB();
				String nameA = (String) fixtureA.getBody().getUserData();
				String nameB = (String) fixtureB.getBody().getUserData();
				Gdx.app.log("Contact", "between " + nameA + " and " + nameB);
				if (nameA.equals("hero") && nameB.equals("target")) {
					if (!target.isLocked) {
						contact.setEnabled(false);
						victory();
						TweenCallback callback = new TweenCallback() {
							@SuppressWarnings("rawtypes")
							@Override public void onEvent(int type, BaseTween source) {
								switch (type) {
									case COMPLETE:	GameScreen.LevelUp();
													break;
								}
							}
						};
						Timeline.createSequence()
								.beginParallel()
								.push(Tween
										.to(heroBall.getTexture(),
												TextureAccessor.POS_XY, 1.3f)
										.target(target.getX(), target.getY())
										.ease(Quart.OUT)).end()
										.setCallback(callback)
										.setCallbackTriggers(TweenCallback.COMPLETE)
								.start(Game.tweenManager);
					}
				} else if (nameA.equals("target") && nameB.equals("hero")) {
					if (!target.isLocked) {
						contact.setEnabled(false);
						victory();
						TweenCallback callback = new TweenCallback() {
							@SuppressWarnings("rawtypes")
							@Override public void onEvent(int type, BaseTween source) {
								switch (type) {
									case COMPLETE:	GameScreen.LevelUp();
													break;
								}
							}
						};
						Timeline.createSequence()
								.beginParallel()
								.push(Tween
										.to(heroBall.getTexture(),
												TextureAccessor.POS_XY, 1.3f)
										.target(target.getX(), target.getY())
										.ease(Quart.OUT)).end()
										.setCallback(callback)
										.setCallbackTriggers(TweenCallback.COMPLETE)
								.start(Game.tweenManager);
					}
				} else {
					// ParticleActor particle=new
					// ParticleActor("game/particles/ball_pitch.p",
					// "game/particles/", heroBall.getX(), heroBall.getY());
					// stage.addActor(particle);
					if(!hitTarget)
						WorldListner.bounce();
				}
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}

		});
	}

	public void dospose() {
		boxManager.Dispose();
	}
}
