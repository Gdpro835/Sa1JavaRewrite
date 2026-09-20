package SonicGBA;

abstract class BossObject extends EnemyObject {
   protected int HP;

   protected BossObject(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      bossObjVec.addElement(this);
      this.HP = 8;
   }

   public void setBossHP() {
      if (GlobalResource.isEasyMode() && stageModeState == 0) {
         this.HP = 6;
      } else {
         this.HP = 8;
      }

   }
}
