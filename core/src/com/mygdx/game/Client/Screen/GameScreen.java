package com.mygdx.game.Client.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Client.*;
import com.mygdx.game.Client.emitter.Emitter;
import com.mygdx.game.Client.emitter.Particle;
import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.UUID;

public class GameScreen implements Screen {
    SpriteBatch batch;
    private Player player;
    private Klaviatura klaviatura = new Klaviatura();
    private UDPClient2 nov;
    private ToPacket b;
    ToPaketEnemy e;
    DatagramPacket b2;
    RecieveFromServer rec;
    Vector2 f=new Vector2(100,100);
    private Texture krug;
    private HashMap<String,Player> enemies = new HashMap<>();
    private HashMap<String,Player> enemiesFriends = new HashMap<>();
    private HashMap<String,Player> Friends = new HashMap<>();
    String uuid;
    byte Chet=0;
    float counter = 0;
    MyGame myGame;
    String Nick ;
    int kol;
    BitmapFont font;
    private int time=90;
    Texture back;


    public GameScreen(MyGame myGame, UDPClient2 nov,String nick,int kol) {
        this.myGame=myGame;
        this.nov=nov;
        this.uuid=nov.getUuid();
        this.Nick=nick;
        this.kol=kol;
        b=new ToPacket();
        e=new ToPaketEnemy();
        rec=new RecieveFromServer();
        krug=new Texture("Puli.png");
        back=new Texture("Back.png");

    }
    World world;
    Box2DDebugRenderer debugRenderer;
    ListenerClass listenerClass;
    Body body;
    HashMap<String,Chetchik> results = new HashMap<>();
    Emitter emitter;

    @Override
    public void show() {
        Gdx.input.setInputProcessor(klaviatura);//зарегестрировали для опрашивания
        font = new BitmapFont();
        batch = new SpriteBatch();
        world=new World(new Vector2(0,0),true);
        listenerClass=new ListenerClass(world);
        world.setContactListener(listenerClass);
        debugRenderer = new Box2DDebugRenderer();
        player = new Player(world,uuid, (byte) 3,10,10);
        player.Nick(Nick);
        body = world.createBody(player.getBodyDef());
        player.Body(body,"player");
        results.put(uuid,new Chetchik(Nick,Chet));
         emitter=player.emitter;
        /*try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        while (Friends.size()<1){
            b2=b.Return(uuid,player.Position().x,player.Position().y,false, (byte) 3,Nick);
            nov.sendDate(b2);
            DatagramPacket d=nov.recPacketFriend();
            if(d!=null){
            f=rec.Direction(d);
            if(rec.WhoIs()==0) {
                String str = rec.getUuid();
                if (rec.Null() != 0) {
                    float x = f.x;
                    float y = f.y;
                    Player friend=new Player(world,str,(byte) 3,x, y);
                    friend.Nick(rec.getNick());
                    Body bodyF = world.createBody(friend.getBodyDef());
                    friend.Body(bodyF,"player");
                    Friends.put(str, friend);
                    results.put(str,new Chetchik(rec.getNick(), (byte) 0));
                }
            }}}

         for (int i=0;i<2;i++){
                    String uuid = UUID.randomUUID().toString();
                    int x=MathUtils.random(Gdx.graphics.getWidth());
                    int y=MathUtils.random(Gdx.graphics.getHeight(),600);
             Player enemy=new Player(world,uuid,(byte) 1,x, y,"Mush.png");
             Body bodyE = world.createBody(enemy.getBodyDef());
             enemy.Body(bodyE,"enemy");
                    enemies.put(uuid,enemy);}

    }

    @Override
    public void render(float delta) {
        delta = Gdx.graphics.getDeltaTime();
        boolean fire=false;

        Vector2 Dvig=klaviatura.getDirection();
        if(player.getLives()>0){
        fire=klaviatura.getFire();}
        b2=b.Return(uuid,player.Position().x+Dvig.x,player.Position().y+Dvig.y,fire,player.getLives(),Nick );
        nov.sendDate(b2);
            player.move(Dvig);//опрашивает дл движения
            player.povorot(Dvig);
        enemies.keySet().forEach(key -> {
            if(enemies.get(key).getLives()!=0){
            b2 = e.ReturnEnemy(key, enemies.get(key).Position().x, enemies.get(key).Position().y,enemies.get(key).getLives());
            nov.sendDate(b2);

        }});

        for(int i=0;i<Friends.size()+enemiesFriends.size();i++){
        DatagramPacket d=nov.recPacketFriend();
        if(d!=null){
            f = rec.Direction(d);
            String str = rec.getUuid();
            byte who= rec.WhoIs();
            byte lives=rec.Lives();
            if(who==0){
                if (Friends.containsKey(str)) {
                    if(lives!=0){
                    Friends.get(str).moveFriend(f,rec.Fire());}
                } else {
                    if(lives!=0){
                    Player friend=new Player(world,str,(byte) 3,f.x, f.y);
                    friend.Nick(rec.getNick());
                    Body bodyF = world.createBody(friend.getBodyDef());
                    friend.Body(bodyF,"player");
                    Friends.put(str, friend);
                    results.put(str,new Chetchik(rec.getNick(), (byte) 0));}
                }
            }
            else{
                if(enemiesFriends.containsKey(str)){
                    if(lives!=0){
                    enemiesFriends.get(str).moveEnemy(f);}}
                else{
                    if(lives!=0){
                    Player enemy=new Player(world,str,(byte) 1,f.x,f.y,"Mush.png");
                    Body bodyE = world.createBody(enemy.getBodyDef());
                    enemy.Body(bodyE,"enemy");
                    enemiesFriends.put(str,enemy);}
                }
            }
        }}

        ScreenUtils.clear(1, 0, 0, 1);

        batch.begin(); // Здесь происходит отрисовка!
        batch.draw(back,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        if(fire){
            emitter.getPosition().set(player.Position());
            emitter.getPovorot().set(player.Povorot());
            Body body = world.createBody(emitter.getBodyDef());
            emitter.start(delta,body);
        }
        Array<Body> remove=emitter.act(delta);
        for(Particle particle: emitter.getParticles()){
            Vector2 position=particle.getPosition();
            particle.getBody().setTransform(position.x+30+8,position.y+30+8,0);
            batch.draw(krug,position.x+30,position.y+30,16,16);
        }

        Friends.keySet().forEach(key ->{
            Friends.get(key).render(batch);
                Emitter emitterF=Friends.get(key).emitter;
            if(Friends.get(key).getFire()==(byte) 1){
                emitterF.getPosition().set(Friends.get(key).Position());
                emitterF.getPovorot().set(Friends.get(key).Povorot());
                Body body = world.createBody(emitterF.getBodyDef());
                emitterF.start(Gdx.graphics.getDeltaTime(),body);
                }
                emitterF.act(Gdx.graphics.getDeltaTime()).forEach(rev ->{
                    remove.add(rev);
                });
            for(Particle particle: emitterF.getParticles()){
                Vector2 position=particle.getPosition();
                particle.getBody().setTransform(position.x+30+8,position.y+30+8,0);
                batch.draw(krug,position.x+30,position.y+30,16,16);
            }
        });
        enemies.keySet().forEach(key ->{
            enemies.get(key).render(batch);
            enemies.get(key).moveEnemy(player.Position());
        });
        enemiesFriends.keySet().forEach(key ->{
            enemiesFriends.get(key).render(batch);
        });
        player.render(batch); //какртинка и ее положение
        font.draw(batch,"Chet: "+Chet,0,500);
        batch.end();
        debugRenderer.render(world,batch.getProjectionMatrix());
        Array<Body> isList=new Array<>();
        listenerClass.getDied().forEach(b ->{
            isList.add(b);
        });
        listenerClass.getDied().clear();
        world.step(delta, 4, 4);

        remove.forEach(rem ->{
            world.destroyBody(rem);
        });
        if(isList.size>0){
            isList.forEach(d ->{
                String str= (String) d.getUserData();
                String[] a=new String[2];
                if(str!=null){
                    a[1]="0";
                 a = str.split("\\s+");}

                if(enemies.containsKey(a[0])){
                    if(a[1].equals(uuid)){Chet++;
                        //Gdx.app.log("Chet ", String.valueOf(Chet));
                        results.get(uuid).setChet(Chet);}
                    else{if(results.containsKey(a[1])){byte r=results.get(a[1]).getChet();
                        r++;
                        //Gdx.app.log("aray ", String.valueOf(r));
                        results.get(a[1]).setChet(r);}}
                enemies.get(a[0]).Lives();
                    enemies.get(a[0]).dispose();
                    enemies.remove(a[0]);
                }
                else if(enemiesFriends.containsKey(a[0])){
                    if(results.containsKey(a[1])){byte r=results.get(a[1]).getChet();
                    r++;
                    //Gdx.app.log("aray ", String.valueOf(r));
                    results.get(a[1]).setChet(r);
                        enemiesFriends.get(a[0]).Lives();
                        enemiesFriends.get(a[0]).dispose();
                        enemiesFriends.remove(a[0]);}}
                else{
                    if(a[0]!=null && a[0].equals(uuid)){player.Lives();
                        //Gdx.app.log("PL ", String.valueOf(player.getLives()));
                    if(player.getLives()<1){
                        player.dispose();
                    }}
                    else if (Friends.containsKey(a[0])) {
                        Friends.get(a[0]).Lives();
                        //Gdx.app.log("PF ", String.valueOf(Friends.get(a[0]).getLives()));
                        if(Friends.get(a[0]).getLives()<1){
                            Friends.get(a[0]).dispose();
                        }
                    }
                }

        });
        }

        counter+= delta;
        if(counter>time){
            dispose();
            hide();
            myGame.setScreen(new GameOverScreen(myGame,results));
        }
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

    }

    @Override
    public void dispose() {
        batch.dispose();
        enemies.keySet().forEach(key ->{
            enemies.get(key).dispose();
        });
        enemiesFriends.keySet().forEach(key ->{
            enemiesFriends.get(key).dispose();
        });


    }

}
