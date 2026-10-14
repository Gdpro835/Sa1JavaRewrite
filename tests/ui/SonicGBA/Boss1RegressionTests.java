package SonicGBA;

import GameEngine.time.GameTime;
import Lib.AnimationDrawer;
import State.GameState;
import java.lang.reflect.Field;

/** Loaded Egg Hammer and actual planted-hammer/car-vault geometry. */
public final class Boss1RegressionTests {
    private static Field field(Class<?> c, String name) throws Exception {
        while (c != null) {
            try { Field f = c.getDeclaredField(name); f.setAccessible(true); return f; }
            catch (NoSuchFieldException e) { c = c.getSuperclass(); }
        }
        throw new NoSuchFieldException(name);
    }
    private static Boss1 prepare(int direction) throws Exception {
        GameTime.reset(); AnimationDrawer.setAllPause(false); AnimationDrawer.setWorldPaused(false);
        GameObject.IsGamePause = false; GameObject.stageModeState = 0;
        PlayerObject.setCharacter(0); StageManager.setStageID(1);
        GameState game = new GameState(); game.init();
        boolean loaded = false;
        for (int i = 0; i < 100 && !loaded; i++) loaded = StageManager.loadStageStep();
        if (!loaded) throw new AssertionError("Act 2 did not load");
        Boss1 boss = null;
        for (Object obj : GameObject.bossObjVec) if (obj instanceof Boss1) boss = (Boss1)obj;
        if (boss == null) throw new AssertionError("Egg Hammer not found");
        GameObject.setPlayerPosition(32, 32);
        boss.HP = 1; // Existing low-health attack threshold; don't change encounter HP balancing.
        field(Boss1.class,"state").setInt(boss,4);
        field(Boss1.class,"velocity").setInt(boss,direction * 240);
        Boss1Arm arm = (Boss1Arm)field(Boss1.class,"arm").get(boss);
        arm.setArmState(4);
        arm.setArmAngel(direction < 0 ? 23680 - 100 : 10880 + 100);
        field(Boss1Arm.class,"plus").setInt(arm,direction < 0 ? 1 : -1);
        return boss;
    }
    private static void vault(int fps, int direction) throws Exception {
        Boss1 boss = prepare(direction);
        Boss1Arm arm = (Boss1Arm)field(Boss1.class,"arm").get(boss);
        GameTime.beginFrame(1.0/fps); boss.logic();
        if (arm.getArmState() != 3) throw new AssertionError("Hammer did not reach ground");
        int expected = direction < 0 ? 640 : 33920;
        if (arm.getArmAngel() != expected) throw new AssertionError("Full-turn angle rebase was delta-scaled: " + arm.getArmAngel() + " vs " + expected);
        int startY = boss.posY, minY = startY;
        int oldX = boss.posX, oldY = boss.posY;
        double maxStep = 0;
        for (int i = 0; i < fps * 2; i++) {
            GameTime.beginFrame(1.0/fps); boss.logic();
            minY = Math.min(minY,boss.posY);
            maxStep = Math.max(maxStep,Math.hypot(boss.posX-oldX,boss.posY-oldY)/64.0);
            oldX=boss.posX;oldY=boss.posY;
        }
        System.out.println("VAULT fps="+fps+" direction="+direction+" height="+(startY-minY)/64.0+" maxStep="+maxStep);
        if (startY-minY < 50*64) throw new AssertionError("Car skipped its vault arc");
        if (maxStep > 20) throw new AssertionError("Car teleported through the vault");
    }
    public static void main(String[] args) throws Exception {
        for(int fps:new int[]{30,60,120,144}) for(int dir:new int[]{-1,1}) vault(fps,dir);
        System.out.println("PASS: Egg Hammer vault assertions (production Boss1/Boss1Arm, headless graphics).");
    }
}
