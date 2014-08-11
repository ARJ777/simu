package aj.soccer.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

/**
 * Provides generic access to GUI-based functionality.
 */
public class GenericGUIImpl implements GenericGUI {

	/** The main GUI frame. */
	protected final JFrame frame = new JFrame();

	@Override
	public <T> /*@Nullable*/ T selectOne(String title, List<T> selections) {
		JList<Object> list = new JList<>(selections.toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JOptionPane pane = new JOptionPane(new JScrollPane(list), JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		JDialog dialog = pane.createDialog(frame, title);
		dialog.setVisible(true);
		Object ret = pane.getValue();
		if (ret instanceof Integer && ((int) ret) == JOptionPane.OK_OPTION) {
			int selection = list.getSelectedIndex();
			if (selection >= 0) return selections.get(selection);
		}
		return null;
	}

	@Override
	public <T> int[] selectMany(String title, List<T> selections, int[] selected) {
		JList<Object> list = new JList<>(selections.toArray());
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setSelectedIndices(selected);
		JOptionPane pane = new JOptionPane(new JScrollPane(list), JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		JDialog dialog = pane.createDialog(frame, title);
		dialog.setVisible(true);
		Object ret = pane.getValue();
		if (ret instanceof Integer && ((int) ret) == JOptionPane.OK_OPTION) {
			return list.getSelectedIndices();
		}
		return null;
	}

	@Override
	public void displayError(String title, String message) {
		JTextArea text = new JTextArea();
		text.setEditable(false);
		text.setText(message);
		JOptionPane.showMessageDialog(frame, new JScrollPane(text), title, JOptionPane.ERROR_MESSAGE);
	}

    public void resizeFonts(int fontSizeChange, Component... roots) {
    	resizeFonts(fontSizeChange, Arrays.asList(roots));
    }
    
    public void resizeFonts(int fontSizeChange, List<Component> roots) {
        LinkedList<Component> components = new LinkedList<>(roots);
        LinkedList<Container> containers = new LinkedList<>();
        while (!components.isEmpty()) {
            Component component = components.removeLast();
            component.setFont(resizeFont(component.getFont(), fontSizeChange));
            if (component instanceof Container) {
                Container container = (Container) component;
                List<Component> children =
                    (container instanceof JMenu)
                        ? getMenuItems((JMenu) container)
                        : Arrays.asList(container.getComponents());
                components.addAll(children);
                containers.add(container);
            } else {
                component.repaint();
            }
        }
        while (!containers.isEmpty()) {
            Container container = containers.removeLast();
            container.repaint();
        }
        for (Component root : roots)
        	root.repaint();
    }

    private static Font resizeFont(Font font, int fontSizeChange) {
        Font newFont = font.deriveFont(font.getStyle(), font.getSize() + fontSizeChange);
        return newFont;
    }

    private List<Component> getMenuItems(JMenu menu) {
        final int itemCount = menu.getItemCount();
        List<Component> items = new ArrayList<>(itemCount);
        for (int i = 0; i < itemCount; i++) {
            JMenuItem item = menu.getItem(i);
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }

}
