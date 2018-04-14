package omy.boston.measurebook;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by LosFrancisco on 08-Apr-17.
 */
public class DotView extends View {
    private final Dots dots;
    public DotView(Context context, Dots dots) {
        super(context);
        this.dots = dots;
        setMinimumWidth(180);
        setMinimumHeight(200);
        setFocusable(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getSuggestedMinimumWidth(),
                getHorizontalScrollbarHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(hasFocus() ? Color.BLUE : Color.GRAY);
        canvas.drawRect(0, 0, getWidth() - 1, getHeight() - 1, paint);
        paint.setStyle(Paint.Style.FILL);

        for (Dot dot : dots.getDots()) {
            paint.setColor(dot.getColor());
            canvas.drawCircle(
                    dot.getX(),
                    dot.getY(),
                    dot.getDiameter(),
                    paint);
        }
    }
}

/**DOT*/
class Dot {
        private final float x, y;
        private final int color;
        private final int diameter;

        public Dot(float x, float y, int color, int diameter) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.diameter = diameter;
        }


        public float getX() {return x;}

        public float getY() {return y;}

        public int getColor() {return color;}

        public int getDiameter() {return diameter;}
    }

/**DOTS*/
class Dots {
    public interface DotsChangeListener{
        void onDotChange(Dots dots);
        }

    private final LinkedList<Dot> dots = new LinkedList<Dot>();
    private final List<Dot> safeDots = Collections.unmodifiableList(dots);
    private int dotsChangeListener;
    public void setDotsChangeListener(DotsChangeListener 1){
        dotsChangeListener = 1;

    }

    public Dot getLastDot(){
        return (dots.size() <= 0) ? null : dots.getLast();
    }
    public List<Dot> getDots() {return safeDots;}
    public void addDot(float x, float y, int color, int diameter){
        dots.add(new Dot(x, y, color, diameter));
    }

    public void clearDots(){
        dots.clear();
        notifyListener();
    }

    private void notifyListener(){
        if (null != dotsChangeListener){
            dotsChangeListener.onDotChange(this);
        }
    }
}
