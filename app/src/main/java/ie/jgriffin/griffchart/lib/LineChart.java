package ie.jgriffin.griffchart.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by JGriffin on 10/11/2014.
 */
public class LineChart extends Chart {

    private Paint linePaint;
    private float lineThickness = 10f, pointCircleRadius = 16f;
    private int pointCount;
    private float linePointMargin = 10f;
    private float lineSectionWidth, halfLineSectionWidth;
    private float lineDrawingStartPoint, lineDrawingEndPoint, lineDrawingWidth;
    private boolean drawCirclePoints = true;

    public LineChart(Context context) {
        super(context);
        initLinePaint();
    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLinePaint();
    }

    public LineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLinePaint();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!chartPoints.isEmpty()) {
            initLineBounds();
            float[][] points = calcPathPoints();
            drawLine(canvas, points);
            if(drawCirclePoints){
                drawPoints(canvas, points);
            }
        }
    }

    private void initLineBounds() {
        pointCount = chartPoints.size();
        lineDrawingStartPoint = chartLeftBound + linePointMargin;
        lineDrawingEndPoint = chartRightBound - linePointMargin;
        lineDrawingWidth = lineDrawingEndPoint - lineDrawingStartPoint;
        lineSectionWidth = lineDrawingWidth / pointCount;
        halfLineSectionWidth = lineSectionWidth / 2;
    }

    private void initLinePaint() {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.GREEN);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(lineThickness);
    }

    private void drawPoints(Canvas canvas, float[][] points){
        for(int i = 0; i < points.length; i++){
            canvas.drawCircle(points[i][0], points[i][1], pointCircleRadius, chartPoints.get(i).getPaint());
        }
    }

    private void drawLine(Canvas canvas, float[][] pathPoints) {
        Path path = new Path();
        if (pathPoints.length > 0) {
            path.moveTo(pathPoints[0][0], pathPoints[0][1]);

            for (int i = 1; i < pathPoints.length; i++) {
                path.lineTo(pathPoints[i][0], pathPoints[i][1]);
            }
        }
        canvas.drawPath(path, linePaint);
    }

    private float[][] calcPathPoints() {
        float[][] pathPoints = new float[pointCount][2];
        for (int i = 0; i < pointCount; i++) {
            pathPoints[i] = calcLinePoint(i, chartPoints.get(i).getValue());
        }
        return pathPoints;
    }

    private float[] calcLinePoint(int i, int value) {
        float y = chartBottomBound - (value * dataScalingFactor);
        float x = lineDrawingStartPoint + (lineSectionWidth * i) + halfLineSectionWidth;
        return new float[]{x, y,};
    }

    public Paint getLinePaint() {
        return linePaint;
    }

    public void setLinePaint(Paint linePaint) {
        this.linePaint = linePaint;
        redraw();
    }

    public float getLineThickness() {
        return lineThickness;
    }

    public void setLineThickness(float lineThickness) {
        this.lineThickness = lineThickness;
        this.linePaint.setStrokeWidth(this.lineThickness);
        redraw();
    }

    public float getLinePointMargin() {
        return linePointMargin;
    }

    public void setLinePointMargin(float linePointMargin) {
        this.linePointMargin = linePointMargin;
    }

    public boolean isDrawCirclePoints() {
        return drawCirclePoints;
    }

    public void setDrawCirclePoints(boolean drawCirclePoints) {
        this.drawCirclePoints = drawCirclePoints;
    }

    public float getPointCircleRadius() {
        return pointCircleRadius;
    }

    public void setPointCircleRadius(float pointCircleRadius) {
        this.pointCircleRadius = pointCircleRadius;
    }
}
