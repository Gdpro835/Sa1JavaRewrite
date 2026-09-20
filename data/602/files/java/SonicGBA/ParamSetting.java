package SonicGBA;

import GameEngine.Key;
import Lib.MyAPI;
import Lib.Record;
import com.sega.mobile.framework.device.MFGraphics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ParamSetting implements SonicDef {
   private static int DRAW_LINE;
   private static final int[] PARAM_RESET;
   private static final String[] PARAM_STR = new String[]{"平地x方向加速度:", "平地x方向反向加速度:", "平地自主跑动最大速度:", "spin x方向反向加速度:", "spin 加速_01 x初速度", "spin 加速_02 x初速度", "大跳速度:", "受伤弹开X:", "受伤弹开Y:", "空中冲刺加速值:", "重力:", "空中横向阻力为速度的XX分之一:", "斜面上伪重力（站立）:", "斜面上伪重力（球）:"};
   private static int cursol;
   private static int drawStart;
   private static int[] paramArray;

   static {
      int var6 = PlayerObject.MOVE_POWER;
      int var10 = PlayerObject.MOVE_POWER_REVERSE;
      int var13 = PlayerObject.MAX_VELOCITY;
      int var4 = PlayerObject.MOVE_POWER_REVERSE_BALL;
      int var5 = PlayerObject.SPIN_START_SPEED_1;
      int var0 = PlayerObject.SPIN_START_SPEED_2;
      int var2 = PlayerObject.JUMP_START_VELOCITY;
      int var7 = PlayerObject.HURT_POWER_X;
      int var12 = PlayerObject.HURT_POWER_Y;
      int var11 = PlayerObject.JUMP_RUSH_SPEED_PLUS;
      int var3 = PlayerObject.GRAVITY;
      int var1 = PlayerObject.JUMP_REVERSE_POWER;
      int var9 = PlayerObject.FAKE_GRAVITY_ON_WALK;
      int var8 = PlayerObject.FAKE_GRAVITY_ON_BALL;
      PARAM_RESET = new int[]{var6, var10, var13, var4, var5, var0, var2, var7, var12, var11, var3, var1, var9, var8};
      paramArray = new int[PARAM_RESET.length];
      drawStart = 0;
      DRAW_LINE = 10;
   }

   public static void draw(MFGraphics var0) {
      var0.setColor(16777215);
      MyAPI.fillRect(var0, 0, 0, 240, 320);
      var0.setColor(0);
      drawStart = cursol - DRAW_LINE + 1;
      if (drawStart < 0) {
         drawStart = 0;
      }

      for(int var1 = drawStart; var1 < drawStart + DRAW_LINE && var1 < PARAM_STR.length; ++var1) {
         String var3 = PARAM_STR[var1] + ":" + paramArray[var1];
         int var2 = drawStart;
         MyAPI.drawString(var0, var3, 40, (var1 - var2) * 24, 0);
      }

      var0.setColor(16711680);
      MyAPI.drawRect(var0, 20, (cursol - drawStart) * 24 - 2, 200, 22);
      var0.setColor(0);
      MyAPI.drawString(var0, "复位", 0, 320, 36);
      MyAPI.drawString(var0, "退出", 240, 320, 40);
   }

   public static void init() {
      cursol = 0;

      int var0;
      for(var0 = 0; var0 < PARAM_RESET.length; ++var0) {
         paramArray[var0] = PARAM_RESET[var0];
      }

      byte[] var2 = Record.loadRecord("SONIC_PARAM_RECORD");
      if (var2 != null) {
         DataInputStream var5 = new DataInputStream(new ByteArrayInputStream(var2));
         var0 = 0;

         while(true) {
            int var1;
            boolean var10001;
            try {
               var1 = paramArray.length;
            } catch (Exception var4) {
               var10001 = false;
               break;
            }

            if (var0 >= var1) {
               break;
            }

            try {
               paramArray[var0] = var5.readInt();
            } catch (Exception var3) {
               var10001 = false;
               break;
            }

            ++var0;
         }
      }

      GameObject.setNewParam(paramArray);
   }

   public static boolean logic() {
      if (Key.press(Key.B_UP)) {
         --cursol;
         cursol += PARAM_STR.length;
         cursol %= PARAM_STR.length;
      }

      if (Key.press(Key.B_DOWN)) {
         ++cursol;
         cursol += PARAM_STR.length;
         cursol %= PARAM_STR.length;
      }

      int var0;
      int[] var2;
      int var10002;
      if (Key.repeat(Key.B_LEFT)) {
         var2 = paramArray;
         var0 = cursol;
         var10002 = var2[var0]--;
      }

      if (Key.repeat(Key.B_RIGHT)) {
         var2 = paramArray;
         var0 = cursol;
         var10002 = var2[var0]++;
      }

      if (Key.press(Key.B_S1)) {
         paramArray[cursol] = PARAM_RESET[cursol];
      }

      if (Key.press(2)) {
      }

      boolean var1;
      if (Key.press(524288)) {
         setOver();
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static void setOver() {
      GameObject.setNewParam(paramArray);
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      DataOutputStream var1 = new DataOutputStream(var2);
      int var0 = 0;

      while(true) {
         try {
            if (var0 >= paramArray.length) {
               byte[] var4 = var2.toByteArray();
               Record.saveRecord("SONIC_PARAM_RECORD", var4);
               break;
            }

            var1.writeInt(paramArray[var0]);
         } catch (Exception var3) {
            break;
         }

         ++var0;
      }

   }
}
