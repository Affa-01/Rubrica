package org.rubrica;

import javax.swing.*;
import javax.swing.SpringLayout;
import java.awt.*;

public class SpringUtilities {
    public static void makeCompactGrid(Container parent,
                                       int rows, int cols,
                                       int initialX, int initialY,
                                       int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout) parent.getLayout();
        } catch (ClassCastException exc) {
            throw new IllegalArgumentException("Parent must use SpringLayout.");
        }

        Spring xPadSpring = Spring.constant(xPad);
        Spring yPadSpring = Spring.constant(yPad);
        Spring initialXSpring = Spring.constant(initialX);
        Spring initialYSpring = Spring.constant(initialY);
        int max = rows * cols;

        Spring maxWidthSpring = Spring.constant(0);
        Spring maxHeightSpring = Spring.constant(0);
        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(parent.getComponent(i));
            maxWidthSpring = Spring.max(maxWidthSpring, cons.getWidth());
            maxHeightSpring = Spring.max(maxHeightSpring, cons.getHeight());
        }

        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(parent.getComponent(i));
            cons.setWidth(maxWidthSpring);
            cons.setHeight(maxHeightSpring);
        }

        SpringLayout.Constraints lastCons = null;
        SpringLayout.Constraints lastRowCons = null;
        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(parent.getComponent(i));
            if (i % cols == 0) { // start of new row
                lastRowCons = lastCons;
                cons.setX(initialXSpring);
            } else {
                cons.setX(Spring.sum(lastCons.getConstraint(SpringLayout.EAST), xPadSpring));
            }

            if (i / cols == 0) { // first row
                cons.setY(initialYSpring);
            } else {
                cons.setY(Spring.sum(lastRowCons.getConstraint(SpringLayout.SOUTH), yPadSpring));
            }
            lastCons = cons;
        }

        SpringLayout.Constraints pCons = layout.getConstraints(parent);
        pCons.setConstraint(SpringLayout.SOUTH,
            Spring.sum(
                Spring.constant(yPad),
                lastCons.getConstraint(SpringLayout.SOUTH)));
        pCons.setConstraint(SpringLayout.EAST,
            Spring.sum(
                Spring.constant(xPad),
                lastCons.getConstraint(SpringLayout.EAST)));
    }
}

