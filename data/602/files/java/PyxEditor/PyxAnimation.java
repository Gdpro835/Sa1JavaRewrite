package PyxEditor;

import GameEngine.time.GameTime;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import com.sega.mobile.framework.android.Graphics;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGraphics;
import java.io.DataInputStream;
import java.util.Stack;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

public class PyxAnimation {
    private static final java.util.WeakHashMap<PyxAnimation, Boolean> active =
            new java.util.WeakHashMap<PyxAnimation, Boolean>();
    public static void updateAll() {
        if (pauseFlag || AnimationDrawer.isAllPause()) return;
        for (PyxAnimation animation : active.keySet()) {
            if (animation.currentAction >= 0 && animation.actionArray != null
                    && !(animation.worldAnimation && AnimationDrawer.isWorldPaused()))
                animation.actionArray[animation.currentAction].moveOn();
        }
    }
    public static final int ZOOM = 6;
    private static boolean pauseFlag = false;
    private boolean worldAnimation = AnimationDrawer.isWorldContext();
    private Action[] actionArray;
    /* access modifiers changed from: private */
    public Animation[] animationArray;
    /* access modifiers changed from: private */
    public KeyFrame[] changeStratKeyArray;
    private int currentAction;
    /* access modifiers changed from: private */
    public boolean loop = false;
    /* access modifiers changed from: private */
    public Node[] nodeArray;
    private Stack nodeStack;
    private int rootNodeID;
    /* access modifiers changed from: private */
    public int speed = 64;

    public static void setPause(boolean z) {
        pauseFlag = z;
    }

    public PyxAnimation(String str, Animation[] animationArr) {
        active.put(this, Boolean.TRUE);
        this.animationArray = animationArr;
        this.currentAction = -1;
        this.nodeStack = new Stack();
        loadFile(str);
        calBeforeDraw();
    }

    private void loadFile(String str) {
        try {
            InputStream in = MFDevice.getResourceAsStream(str);
            if (in == null) {
                InputStream jsonIn = MFDevice.getResourceAsStream(str.replace(".pyx", ".json"));
                if (jsonIn != null) {
                    try {
                        in = PyxWriter.jsonToPyx(jsonIn);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new IOException("Error recompiling JSON", e);
                    }
                } else {
                    throw new FileNotFoundException("File not found: " + str);
                }
            }
            DataInputStream dataInputStream = new DataInputStream(in);
            int readByte = dataInputStream.readByte();
            this.nodeArray = new Node[readByte];
            this.changeStratKeyArray = new KeyFrame[readByte];
            for (int i = 0; i < readByte; i++) {
                this.nodeArray[i] = new Node(this, this, dataInputStream);
                this.changeStratKeyArray[i] = new KeyFrame(this);
            }
            for (int i2 = 0; i2 < readByte; i2++) {
                this.nodeArray[i2].linkByID();
            }
            this.rootNodeID = dataInputStream.readByte();
            byte readByte2 = dataInputStream.readByte();
            if (this.rootNodeID >= 0) {
                this.nodeArray[this.rootNodeID].setRootConnectPoint(readByte2);
            }
            int readByte3 = dataInputStream.readByte();
            this.actionArray = new Action[readByte3];
            for (int i3 = 0; i3 < readByte3; i3++) {
                this.actionArray[i3] = new Action(this, dataInputStream);
            }
        } catch (Exception e) {
        }
    }

    public void close() {
        active.remove(this);
        for (Node close : this.nodeArray) {
            close.close();
        }
        this.nodeArray = null;
        for (Action close2 : this.actionArray) {
            close2.close();
        }
        this.actionArray = null;
    }

    public void drawAction(MFGraphics mFGraphics, int i, int i2, int i3) {
        worldAnimation |= AnimationDrawer.isWorldContext();
        if (this.currentAction != i) {
            this.actionArray[i].reset();
            this.currentAction = i;
        }
        calBeforeDraw();
        for (Node draw : this.nodeArray) {
            draw.draw(mFGraphics, i2, i3);
        }

    }

    public void drawAction(MFGraphics mFGraphics, int i, int i2) {
        worldAnimation |= AnimationDrawer.isWorldContext();
        if (this.currentAction >= 0) {
            calBeforeDraw();
            for (Node draw : this.nodeArray) {
                draw.draw(mFGraphics, i, i2);
            }

        }
    }

    private void calBeforeDraw() {
        for (Node resetForDraw : this.nodeArray) {
            resetForDraw.resetForDraw();
        }
        this.nodeStack.removeAllElements();
        Node node = this.nodeArray[this.rootNodeID];
        node.doCalBeforeDraw();
        while (true) {
            if (node != null) {
                Node subNode = node.getSubNode();
                if (subNode != null) {
                    this.nodeStack.push(node);
                    subNode.doCalBeforeDraw();
                    node = subNode;
                } else {
                    node = subNode;
                }
            } else if (!this.nodeStack.empty()) {
                node = (Node) this.nodeStack.pop();
            } else {
                return;
            }
        }
    }

    public void setAction(int i) {
        this.actionArray[i].reset();
        this.currentAction = i;
    }

    public void changeToAction(int i, int i2) {
        this.actionArray[this.currentAction].saveKeyFrame(this.changeStratKeyArray);
        for (KeyFrame access$3 : this.changeStratKeyArray) {
            access$3.framePosition = -i2;
        }
        this.actionArray[i].reset(-i2);
        this.currentAction = i;
    }

    public boolean chkEnd() {
        if (this.currentAction >= 0) {
            return this.actionArray[this.currentAction].chkEnd();
        }
        return false;
    }

    public void setLoop(boolean z) {
        this.loop = z;
    }

    public int getNodeXByAnimationNamed(String str, int i, int i2) {
        for (int i3 = 0; i3 < this.nodeArray.length; i3++) {
            if (this.nodeArray[i3].label.equals(str)) {
                return MyAPI.getRelativePointX(this.nodeArray[i3].centerPoint.showX, ((this.nodeArray[i3].animationX - this.nodeArray[i3].centerPoint.x) + i) << 6, ((this.nodeArray[i3].animationY - this.nodeArray[i3].centerPoint.y) + i2) << 6, this.nodeArray[i3].calDegree) >> 6;
            }
        }
        return 0;
    }

    public int getNodeYByAnimationNamed(String str, int i, int i2) {
        for (int i3 = 0; i3 < this.nodeArray.length; i3++) {
            if (this.nodeArray[i3].label.equals(str)) {
                return MyAPI.getRelativePointY(this.nodeArray[i3].centerPoint.showY, ((this.nodeArray[i3].animationX - this.nodeArray[i3].centerPoint.x) + i) << 6, ((this.nodeArray[i3].animationY - this.nodeArray[i3].centerPoint.y) + i2) << 6, this.nodeArray[i3].calDegree) >> 6;
            }
        }
        return 0;
    }

    public void setSpeed(int i) {
        if (i > 0) {
            this.speed = i;
        }
    }

    public Node getNodeByName(String str) {
        for (int i = 0; i < this.nodeArray.length; i++) {
            if (this.nodeArray[i].label.equals(str)) {
                return this.nodeArray[i];
            }
        }
        return null;
    }

    public void changeAnimation(String str, int i, int i2) {
        Node nodeByName = getNodeByName(str);
        if (nodeByName != null) {
            nodeByName.setAnimation(i, i2);
        }
    }

    public void getNodeInfo(NodeInfo nodeInfo, String str) {
        nodeInfo.reset();
        Node nodeByName = getNodeByName(str);
        if (nodeByName != null) {
            nodeInfo.got = true;
            nodeInfo.drawer = nodeByName.drawer;
            nodeInfo.animationX = nodeByName.getAnimationPosX();
            nodeInfo.animationY = nodeByName.getAnimationPosY();
            nodeInfo.rotateX = 0;
            nodeInfo.rotateY = 0;
            if (nodeByName.centerPoint != null) {
                nodeInfo.rotateX = nodeByName.centerPoint.showX >> 6;
                nodeInfo.rotateY = nodeByName.centerPoint.showY >> 6;
            }
            nodeInfo.degree = nodeByName.calDegree;
        }
    }

    class Node {
        private int animationID;
        /* access modifiers changed from: private */
        public int animationX;
        /* access modifiers changed from: private */
        public int animationY;
        /* access modifiers changed from: private */
        public int calDegree;
        public ConnectPoint centerPoint;
        /* access modifiers changed from: private */
        public ConnectPoint[] connectPointArray;
        /* access modifiers changed from: private */
        public int degree;
        /* access modifiers changed from: private */
        public AnimationDrawer drawer;
        /* access modifiers changed from: private */
        public String label;
        private PyxAnimation pyx;
        private int returnId;
        private Node superNode;
        final PyxAnimation this$0;

        public Node(PyxAnimation pyxAnimation, PyxAnimation pyxAnimation2, DataInputStream dataInputStream) {
            this.this$0 = pyxAnimation;
            this.pyx = pyxAnimation2;
            loadStream(dataInputStream);
        }

        private void loadStream(DataInputStream dataInputStream) {
            try {
                dataInputStream.readShort();
                dataInputStream.readShort();
                dataInputStream.readShort();
                dataInputStream.readShort();
                int readByte = dataInputStream.readByte();
                this.connectPointArray = new ConnectPoint[readByte];
                for (int i = 0; i < readByte; i++) {
                    this.connectPointArray[i] = new ConnectPoint(this.this$0, this, dataInputStream);
                }
                byte[] bArr = new byte[dataInputStream.readShort()];
                dataInputStream.read(bArr);
                this.label = new String(bArr, "UTF-8");
                this.animationID = dataInputStream.readByte();
                byte readByte2 = dataInputStream.readByte();
                this.animationX = dataInputStream.readByte();
                this.animationY = dataInputStream.readByte();
                this.drawer = this.this$0.animationArray[this.animationID].getDrawer(readByte2, true, 0);
            } catch (Exception e) {
            }
        }

        public void linkByID() {
            for (ConnectPoint linkByID : this.connectPointArray) {
                linkByID.linkByID();
            }
        }

        public void setRootConnectPoint(int i) {
            this.centerPoint = this.connectPointArray[i];
        }

        public void close() {
            for (ConnectPoint close : this.connectPointArray) {
                close.close();
            }
            this.connectPointArray = null;
            this.label = null;
            this.drawer = null;
        }

        public void resetForDraw() {
            this.returnId = 0;
            this.superNode = null;
        }

        public void doCalBeforeDraw() {
            if (this.centerPoint != null) {
                this.calDegree = getDrawDegree();
                for (ConnectPoint connectPoint : this.connectPointArray) {
                    if (connectPoint != this.centerPoint) {
                        int i = (connectPoint.x - this.centerPoint.x) << 6;
                        int i2 = (connectPoint.y - this.centerPoint.y) << 6;
                        connectPoint.showX = MyAPI.getRelativePointX(this.centerPoint.showX, i, i2, this.calDegree);
                        connectPoint.showY = MyAPI.getRelativePointY(this.centerPoint.showY, i, i2, this.calDegree);
                        connectPoint.setLinkPointPosition();
                    }
                }
            }
        }

        public Node getSubNode() {
            Node var1 = null;
    
            ConnectPoint var2 = null;
            for(var2 = null; (var1 == null || var1 == this.superNode) && this.returnId < this.connectPointArray.length; ++this.returnId) {
                var1 = this.connectPointArray[this.returnId].getLinkNode();
                var2 = this.connectPointArray[this.returnId].getLinkConnectPoint();
            }
    
            if (var1 != null && var1 != this.superNode) {
                var1.setSuperNode(this);
                var1.setCenterPoint(var2);
            } else {
                var1 = null;
            }
    
            return var1;
        }

        public void setSuperNode(Node node) {
            this.superNode = node;
        }

        public void setCenterPoint(ConnectPoint connectPoint) {
            this.centerPoint = connectPoint;
        }

        public int getDrawDegree() {
            int i = this.degree;
            if (this.superNode != null) {
                i += this.superNode.getDrawDegree();
            }
            while (i < 0) {
                i += 360;
            }
            return i % 360;
        }

        public void draw(MFGraphics mFGraphics, int i, int i2) {
            Graphics graphics = (Graphics) mFGraphics.getSystemGraphics();
            graphics.save();
            graphics.translate((float) ((this.centerPoint.showX + (i << 6)) >> 6), (float) ((this.centerPoint.showY + (i2 << 6)) >> 6));
            graphics.rotate((float) this.calDegree);
            graphics.translate((float) (-this.centerPoint.x), (float) (-this.centerPoint.y));
            if (this.drawer != null) {
                this.drawer.draw(mFGraphics, this.animationX, this.animationY);
            }
            graphics.restore();
        }

        public void setAnimation(int i, int i2) {
            if (i >= 0 && i < this.this$0.animationArray.length) {
                if (this.animationID != i) {
                    this.drawer = this.this$0.animationArray[i].getDrawer(0, false, 0);
                    this.animationID = i;
                }
                this.drawer.setActionId(i2);
            }
        }

        public int getAnimationPosX() {
            int i = 0;
            int i2 = this.animationX;
            int i3 = this.animationY;
            if (this.centerPoint != null) {
                i = this.centerPoint.showX;
                int unused = this.centerPoint.showY;
                i2 -= this.centerPoint.x;
                i3 -= this.centerPoint.y;
            }
            return MyAPI.getRelativePointX(i, i2 << 6, i3 << 6, this.calDegree) >> 6;
        }

        public int getAnimationPosY() {
            int i = 0;
            int i2 = this.animationX;
            int i3 = this.animationY;
            if (this.centerPoint != null) {
                int unused = this.centerPoint.showX;
                i = this.centerPoint.showY;
                i2 -= this.centerPoint.x;
                i3 -= this.centerPoint.y;
            }
            return MyAPI.getRelativePointY(i, i2 << 6, i3 << 6, this.calDegree) >> 6;
        }
    }

    class ConnectPoint {
        public int linkNodeId;
        private ConnectPoint linkPoint = null;
        public int linkPointId;
        public Node node;
        /* access modifiers changed from: private */
        public int showX;
        /* access modifiers changed from: private */
        public int showY;
        final PyxAnimation this$0;
        public int x;
        public int y;

        public ConnectPoint(PyxAnimation pyxAnimation, Node node2, DataInputStream dataInputStream) {
            this.this$0 = pyxAnimation;
            this.node = node2;
            loadStream(dataInputStream);
        }

        private void loadStream(DataInputStream dataInputStream) {
            try {
                this.x = dataInputStream.readShort();
                this.y = dataInputStream.readShort();
                this.linkNodeId = dataInputStream.readByte();
                this.linkPointId = dataInputStream.readByte();
            } catch (Exception e) {
            }
        }

        public void linkByID() {
            if (this.linkNodeId >= 0 && this.linkPointId >= 0) {
                this.linkPoint = this.this$0.nodeArray[this.linkNodeId].connectPointArray[this.linkPointId];
            }
        }

        public void close() {
            this.node = null;
            this.linkPoint = null;
        }

        public Node getLinkNode() {
            if (this.linkPoint == null) {
                return null;
            }
            return this.linkPoint.node;
        }

        public ConnectPoint getLinkConnectPoint() {
            return this.linkPoint;
        }

        public void setLinkPointPosition() {
            if (this.linkPoint != null) {
                this.linkPoint.showX = this.showX;
                this.linkPoint.showY = this.showY;
            }
        }
    }

    class Action {
        private int frame;
        private long lastAdvanceFrame = Long.MIN_VALUE;
        private String label;
        final PyxAnimation this$0;
        private int timeLimit;
        private ActionTrack[] trackArray;

        public Action(PyxAnimation pyxAnimation, DataInputStream dataInputStream) {
            this.this$0 = pyxAnimation;
            loadStream(dataInputStream);
        }

        private void loadStream(DataInputStream dataInputStream) {
            try {
                int readByte = dataInputStream.readByte();
                this.trackArray = new ActionTrack[readByte];
                for (int i = 0; i < readByte; i++) {
                    this.trackArray[i] = new ActionTrack(this.this$0, dataInputStream);
                }
                byte[] bArr = new byte[dataInputStream.readShort()];
                dataInputStream.read(bArr);
                this.label = new String(bArr, "UTF-8");
                this.timeLimit = dataInputStream.readShort();
            } catch (Exception e) {
            }
        }

        public void close() {
            for (ActionTrack close : this.trackArray) {
                close.close();
            }
            this.trackArray = null;
            this.label = null;
        }

        public void reset() {
            this.frame = 0;
            GameTime.reset(this, "frame");
            lastAdvanceFrame = Long.MIN_VALUE;
            setNodeProperty();
        }

        public void reset(int i) {
            this.frame = i << 6;
            GameTime.reset(this, "frame");
            lastAdvanceFrame = Long.MIN_VALUE;
            setNodeProperty();
        }

        public void moveOn() {
            if (pauseFlag || AnimationDrawer.isAllPause() || (this.this$0.worldAnimation && AnimationDrawer.isWorldPaused())) return;
            if (lastAdvanceFrame == GameTime.frameId()) return;
            lastAdvanceFrame = GameTime.frameId();
            this.frame = GameTime.advance(this, "frame", this.frame, this.this$0.speed);
            if (this.frame >= (this.timeLimit << 6)) {
                if (!this.this$0.loop) {
                    this.frame = (this.timeLimit << 6) - 1;
                } else {
                    this.frame = GameTime.wrap(this, "frame", this.frame, Math.max(1, this.timeLimit << 6));
                }
            }
            setNodeProperty();
        }

        public void setNodeProperty() {
            for (ActionTrack nodeProperty : this.trackArray) {
                nodeProperty.setNodeProperty(this.frame);
            }
        }

        public void saveKeyFrame(KeyFrame[] keyFrameArr) {
            for (int i = 0; i < this.trackArray.length; i++) {
                this.trackArray[i].setNodePropertyToKeyFrame(keyFrameArr[i], this.frame);
            }
        }

        public boolean chkEnd() {
            return !this.this$0.loop && this.frame == (this.timeLimit << 6) + -1;
        }
    }

    class ActionTrack {
        private KeyFrame[] keyFrameArray;
        private int nodeID;
        final PyxAnimation this$0;

        public ActionTrack(PyxAnimation pyxAnimation, DataInputStream dataInputStream) {
            this.this$0 = pyxAnimation;
            loadStream(dataInputStream);
        }

        private void loadStream(DataInputStream dataInputStream) {
            try {
                this.nodeID = dataInputStream.readByte();
                int readByte = dataInputStream.readByte();
                this.keyFrameArray = new KeyFrame[readByte];
                for (int i = 0; i < readByte; i++) {
                    this.keyFrameArray[i] = new KeyFrame(this.this$0, dataInputStream);
                }
            } catch (Exception e) {
            }
        }

        public void close() {
            this.keyFrameArray = null;
        }

        public void setNodeProperty(int i) {
            this.this$0.nodeArray[this.nodeID].degree = getDegree(i);
        }

        public void setNodePropertyToKeyFrame(KeyFrame keyFrame, int i) {
            keyFrame.degree = getDegree(i);
        }

        public int getDegree(int i) {
            KeyFrame keyFrame;
            int i2 = 0;
            KeyFrame keyFrame2 = null;
            int i3 = i >> 6;
            if (i3 >= 0) {
                KeyFrame keyFrame3 = null;
                while (true) {
                    if (i2 < this.keyFrameArray.length) {
                        if (this.keyFrameArray[i2].framePosition > i3) {
                            keyFrame2 = this.keyFrameArray[i2];
                            keyFrame = keyFrame3;
                            break;
                        }
                        keyFrame3 = this.keyFrameArray[i2];
                        i2++;
                    } else {
                        keyFrame = keyFrame3;
                        break;
                    }
                }
            } else {
                KeyFrame keyFrame4 = this.this$0.changeStratKeyArray[this.nodeID];
                keyFrame2 = this.keyFrameArray[0];
                keyFrame = keyFrame4;
            }
            if (keyFrame2 == null) {
                return keyFrame.degree;
            }
            int access$1 = keyFrame2.framePosition;
            int access$12 = keyFrame.framePosition;
            int access$2 = keyFrame2.degree - keyFrame.degree;
            if (access$2 > 180) {
                access$2 -= 360;
            }
            if (access$2 < -180) {
                access$2 += 360;
            }
            return ((access$2 * (i3 - keyFrame.framePosition)) / (access$1 - access$12)) + keyFrame.degree;
        }
    }

    class KeyFrame {
        /* access modifiers changed from: private */
        public int degree;
        /* access modifiers changed from: private */
        public int framePosition;
        final PyxAnimation this$0;

        public KeyFrame(PyxAnimation pyxAnimation) {
            this.this$0 = pyxAnimation;
            this.framePosition = 0;
        }

        public KeyFrame(PyxAnimation pyxAnimation, DataInputStream dataInputStream) {
            this.this$0 = pyxAnimation;
            loadStream(dataInputStream);
        }

        private void loadStream(DataInputStream dataInputStream) {
            try {
                this.framePosition = dataInputStream.readShort();
                this.degree = dataInputStream.readShort();
            } catch (Exception e) {
            }
        }
    }

    public static class NodeInfo {
        public int animationX;
        public int animationY;
        public int degree;
        public AnimationDrawer drawer;
        /* access modifiers changed from: private */
        public boolean got;
        public int rotateX;
        public int rotateY;

        public void reset() {
            this.got = false;
        }

        public boolean hasNode() {
            return this.got;
        }
    }
}
