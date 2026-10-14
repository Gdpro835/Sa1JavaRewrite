package SonicGBA;

import GameEngine.time.GameTime;
import Lib.AnimationDrawer;
import State.GameState;
import java.util.Vector;

/** Production Fountain controller. A recording player isolates requested travel from terrain contacts. */
public final class FountainRegressionTests {
    private static int checks, interval;
    private static final double[] IRREGULAR = {0.007,0.017,0.033,0.1,0.011,0.024};
    private static final class RecordingPlayer extends PlayerSonic {
        @Override public void moveOnObject(int x, int y, boolean preserveVelocity) {
            setFootPositionX(x); setFootPositionY(y);
        }
    }
    private static void check(boolean ok, String message) { checks++; if (!ok) throw new AssertionError(message); }
    private static void load(int stage) throws Exception {
        GameTime.reset(); PlayerObject.setCharacter(0); StageManager.setStageID(stage);
        GameObject.IsGamePause=false; GameObject.stageModeState=0;
        AnimationDrawer.setAllPause(false); AnimationDrawer.setWorldPaused(false);
        GameState game=new GameState(); game.init();
        boolean loaded=false;
        for(int i=0;i<100&&!loaded;i++) loaded=StageManager.loadStageStep();
        check(loaded,"flow fixture level loads");
    }
    private static void rate(int fps, int initial) throws Exception {
        GameTime.reset();
        RecordingPlayer p=new RecordingPlayer(); GameObject.player=p; p.setPlayer(p);
        Fountain flow=new Fountain(40, 100000, 20000, 0, 0, 50, 60);
        flow.refreshCollisionRect(100000,20000);
        p.setFootPositionX(100000); p.setFootPositionY(flow.getLineY(100000)+768);
        p.collisionState=1; p.setVelX(initial); p.calPreCollisionRect();
        flow.doWhileCollision(p,0);
        check(p.outOfControl && p.outOfControlObject == flow,"actual water/sand flow contact captures player");
        double elapsed=0;
        boolean coherent=true, once=true, bounded=true;
        while(elapsed+1e-10<1.26) {
            double dt=Math.min(fps>0?1.0/fps:IRREGULAR[interval++%IRREGULAR.length],1.26-elapsed);
            GameTime.beginFrame(dt); flow.logic(); elapsed+=dt;
            int x=p.getFootPositionX(), y=p.getFootPositionY(), v=p.getVelX();
            coherent &= y==flow.getLineY(x)+768;
            bounded &= v<=1920;
            flow.logic();
            once &= x==p.getFootPositionX() && y==p.getFootPositionY() && v==p.getVelX();
        }
        double v=Math.min(1920,initial), t=20, distance=0;
        if(v<0) { double n=Math.min(t,-v/300); distance+=v*n+150*n*n; v+=300*n;t-=n; }
        double ramp=Math.min(t,(1920-v)/100);
        distance+=v*ramp+50*ramp*ramp;v+=100*ramp;distance+=v*(t-ramp);
        check(Math.abs((p.getFootPositionX()-100000)-distance)<=1,"flow distance depends on seconds, fps="+fps+", initial="+initial);
        check(Math.abs(p.getVelX()-v)<=1 && bounded,"flow acceleration/cap is frame-rate independent");
        check(coherent,"curve uses the integrated X rather than raw per-frame velocity");
        check(once,"duplicate callbacks do not double flow acceleration/travel");
        int frozen=p.posX;
        GameObject.IsGamePause=true;GameTime.beginFrame(.1);flow.logic();
        check(p.posX==frozen,"paused flow stays still");
        GameObject.IsGamePause=false;GameTime.beginFrame(0);flow.logic();
        check(p.posX==frozen,"zero-time flow stays still");
    }
    public static void main(String[] args) throws Exception {
        load(0);
        for(int fps:new int[]{10,30,60,90,120,144,-1}) for(int initial:new int[]{-600,0,1500,2200}) rate(fps,initial);
        load(9);
        int flows=0;
        for(Vector[] col:GameObject.allGameObject) for(Vector cell:col) for(Object obj:cell) if(obj instanceof Fountain) flows++;
        System.out.println("Sand-stage Fountain objects: "+flows);
        rate(60,0); rate(144,-600);
        System.out.println("PASS: "+checks+" flow motion assertions (production controller; recording movement receiver).");
    }
}
