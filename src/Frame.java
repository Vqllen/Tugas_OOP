import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame {
    private final int RESIZE_MARGIN = 8;
    private Cursor defaultCursor = Cursor.getDefaultCursor();
    private Point clickPoint = null;
    private int cursorType = Cursor.DEFAULT_CURSOR;

    public Frame(String title) {
        super(title);
        setUndecorated(true);
        setSize(1400, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        MouseAdapter mouseHandler = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                cursorType = getCursorType(e.getPoint());
                setCursor(Cursor.getPredefinedCursor(cursorType));
            }

            public void mousePressed(MouseEvent e) {
                clickPoint = e.getPoint();
            }

            public void mouseDragged(MouseEvent e) {
                if (cursorType != Cursor.DEFAULT_CURSOR) {
                    resizeWindow(e);
                } else {
                    // drag the window (if not resizing)
                    Point dragPoint = e.getLocationOnScreen();
                    setLocation(dragPoint.x - clickPoint.x, dragPoint.y - clickPoint.y);
                }
            }
        };

        JComponent glass = (JComponent) getGlassPane();
        glass.setVisible(true);
        glass.setOpaque(false);
        glass.addMouseListener(mouseHandler);
        glass.addMouseMotionListener(mouseHandler);
    }

    private int getCursorType(Point p) {
        int w = getWidth();
        int h = getHeight();
        int x = p.x;
        int y = p.y;

        if (x < RESIZE_MARGIN && y < RESIZE_MARGIN)
            return Cursor.NW_RESIZE_CURSOR;
        if (x > w - RESIZE_MARGIN && y < RESIZE_MARGIN)
            return Cursor.NE_RESIZE_CURSOR;
        if (x < RESIZE_MARGIN && y > h - RESIZE_MARGIN)
            return Cursor.SW_RESIZE_CURSOR;
        if (x > w - RESIZE_MARGIN && y > h - RESIZE_MARGIN)
            return Cursor.SE_RESIZE_CURSOR;
        if (x < RESIZE_MARGIN)
            return Cursor.W_RESIZE_CURSOR;
        if (x > w - RESIZE_MARGIN)
            return Cursor.E_RESIZE_CURSOR;
        if (y < RESIZE_MARGIN)
            return Cursor.N_RESIZE_CURSOR;
        if (y > h - RESIZE_MARGIN)
            return Cursor.S_RESIZE_CURSOR;

        return Cursor.DEFAULT_CURSOR;
    }

    private void resizeWindow(MouseEvent e) {
        Point dragPoint = e.getLocationOnScreen();
        Point windowLoc = getLocation();

        int dx = dragPoint.x - windowLoc.x;
        int dy = dragPoint.y - windowLoc.y;

        int minWidth = 400;
        int minHeight = 300;

        Rectangle bounds = getBounds();

        switch (cursorType) {
            case Cursor.NW_RESIZE_CURSOR:
                bounds.x += dx - clickPoint.x;
                bounds.y += dy - clickPoint.y;
                bounds.width -= dx - clickPoint.x;
                bounds.height -= dy - clickPoint.y;
                break;
            case Cursor.NE_RESIZE_CURSOR:
                bounds.y += dy - clickPoint.y;
                bounds.width = dx;
                bounds.height -= dy - clickPoint.y;
                break;
            case Cursor.SW_RESIZE_CURSOR:
                bounds.x += dx - clickPoint.x;
                bounds.width -= dx - clickPoint.x;
                bounds.height = dy;
                break;
            case Cursor.SE_RESIZE_CURSOR:
                bounds.width = dx;
                bounds.height = dy;
                break;
            case Cursor.W_RESIZE_CURSOR:
                bounds.x += dx - clickPoint.x;
                bounds.width -= dx - clickPoint.x;
                break;
            case Cursor.E_RESIZE_CURSOR:
                bounds.width = dx;
                break;
            case Cursor.N_RESIZE_CURSOR:
                bounds.y += dy - clickPoint.y;
                bounds.height -= dy - clickPoint.y;
                break;
            case Cursor.S_RESIZE_CURSOR:
                bounds.height = dy;
                break;
        }

        if (bounds.width < minWidth)
            bounds.width = minWidth;
        if (bounds.height < minHeight)
            bounds.height = minHeight;

        setBounds(bounds);
    }
}
