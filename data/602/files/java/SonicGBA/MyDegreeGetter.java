package SonicGBA;

import com.sega.engine.action.ACCollision;
import com.sega.engine.action.ACDegreeGetter;
import com.sega.engine.action.ACParam;
import com.sega.engine.action.ACDegreeGetter.DegreeReturner;

public class MyDegreeGetter extends ACDegreeGetter implements ACParam {
   private CollisionBlock block;
   private CollisionMap world;

   public MyDegreeGetter(CollisionMap var1) {
      this.world = var1;
      this.block = (CollisionBlock)CollisionMap.getInstance().getNewCollisionBlock();
   }

   private int getDirectionByDegree(int var1) {
      while(var1 < 0) {
         var1 += 360;
      }

      var1 %= 360;
      byte var2;
      if (var1 < 315 && var1 > 45) {
         if (var1 > 225 && var1 < 315) {
            var2 = 3;
         } else if (var1 >= 135 && var1 <= 225) {
            var2 = 2;
         } else {
            var2 = 1;
         }
      } else {
         var2 = 0;
      }

      return var2;
   }

   public void getDegreeFromCollisionByPosition(DegreeReturner var1, ACCollision var2, int var3, int var4, int var5, int var6) {
   }

   public void getDegreeFromWorldByPosition(DegreeReturner var1, int var2, int var3, int var4, int var5) {
      var1.reset(var3, var4, var2);
      CollisionMap.getInstance().getCollisionBlock(this.block, var3, var4, var5);
      var1.degree = this.block.getDegree(var2, this.getDirectionByDegree(var2));
   }
}
