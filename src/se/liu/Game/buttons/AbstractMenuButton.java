import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Abstract class that all menu button classes will extend, contains mainly the code for how the button will be showed on the screen.
 * Function is handled by subclasses.
 */

public abstract class AbstractMenuButton extends JComponent implements MouseListener
{

    protected MenuComponent menuComponent;
    protected String name;
    protected Color color;
    protected Font font = null;

    @Override protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
       // this.setSize(menuComponent.getWidth()/2, menuComponent.getHeight()/2);
        if(font == null) font = g.getFont().deriveFont(25.0f);
        g.setFont(font);

        g.setColor(color);
        g.fillRect(0,0, getWidth(), getHeight());

        g.setColor(Color.white);
        // Numbers here are used to position the text in an okay position
        g.drawString(name,getWidth()/4, getHeight()/2);
    }


    // Since implementing an interface requires all methods, we keep the unused ones here in the Abstract class, ignore
    @Override public void mouseClicked(final MouseEvent e){

    }

    @Override public void mouseReleased(final MouseEvent e) {

    }

    @Override public void mouseEntered(final MouseEvent e) {

    }

    @Override public void mouseExited(final MouseEvent e) {

    }


    protected AbstractMenuButton(MenuComponent menuComponent, String name, Color color){
        this.menuComponent = menuComponent;
        this.name = name;
        this.color = color;
        this.addMouseListener(this);
    }
}
